package com.hcodeSolutions.model;

import com.hcodeSolutions.dto.ColumnMapping;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dewmith Mihisara
 * @date 2025-04-03
 * @since 0.0.1
 */
public class ExcelToSQLConverter {
    private List<ColumnMapping> columnMappings = new ArrayList<>();
    private String tableName;

    public void parseCreateTableQuery(String createQuery) {
        // Extract table name
        Pattern tablePattern = Pattern.compile("CREATE TABLE (?:IF NOT EXISTS )?`?(\\w+)`?");
        Matcher tableMatcher = tablePattern.matcher(createQuery.toUpperCase());
        if (tableMatcher.find()) {
            this.tableName = tableMatcher.group(1);
        }

        // Extract column definitions
        Pattern columnPattern = Pattern.compile("`?(\\w+)`?\\s+(\\w+)(?:\\([^)]+\\))?");
        Matcher columnMatcher = columnPattern.matcher(createQuery);

        columnMappings.clear();
        while (columnMatcher.find()) {
            String columnName = columnMatcher.group(1);
            String dataType = columnMatcher.group(2).toUpperCase();
            columnMappings.add(new ColumnMapping(null, columnName, dataType));
        }
    }

    public Map<String, String> getExcelHeaders(String excelFilePath) throws IOException {
        Map<String, String> headers = new HashMap<>();

        try (FileInputStream inputStream = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            for (Cell cell : headerRow) {
                String headerName = cell.getStringCellValue().trim();
                headers.put(headerName, headerName);
            }
        }

        return headers;
    }

    public void mapColumns(Map<String, String> excelToDbMapping) {
        for (ColumnMapping mapping : columnMappings) {
            if (excelToDbMapping.containsKey(mapping.getDbColumn())) {
                mapping.setExcelColumn(excelToDbMapping.get(mapping.getDbColumn()));
            }
        }
    }

    public void generateInsertQueries(String excelFilePath, String outputFilePath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(inputStream);
             FileWriter writer = new FileWriter(outputFilePath)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Create header index map
            Row headerRow = sheet.getRow(0);
            Map<String, Integer> headerIndexMap = new HashMap<>();
            for (Cell cell : headerRow) {
                headerIndexMap.put(cell.getStringCellValue().trim(), cell.getColumnIndex());
            }

            // Process data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                StringBuilder query = new StringBuilder("INSERT INTO ");
                query.append(tableName).append(" (");

                // Add column names
                List<String> columns = new ArrayList<>();
                List<String> values = new ArrayList<>();

                for (ColumnMapping mapping : columnMappings) {
                    if (mapping.getExcelColumn() != null && headerIndexMap.containsKey(mapping.getExcelColumn())) {
                        columns.add(mapping.getDbColumn());
                        Cell cell = row.getCell(headerIndexMap.get(mapping.getExcelColumn()), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        values.add(formatCellValue(cell, mapping.getDataType()));
                    }
                }

                query.append(String.join(", ", columns));
                query.append(") VALUES (");
                query.append(String.join(", ", values));
                query.append(");");

                writer.write(query.toString() + "\n");
            }
        }
    }

    private String formatCellValue(Cell cell, String dataType) {
        switch (cell.getCellType()) {
            case STRING:
                String stringValue = cell.getStringCellValue().trim();
                if (stringValue.isEmpty()) return "NULL";
                if (dataType.startsWith("INT") || dataType.startsWith("FLOAT") || dataType.startsWith("DOUBLE") || dataType.startsWith("DECIMAL")) {
                    try {
                        return stringValue;
                    } catch (NumberFormatException e) {
                        return "NULL";
                    }
                }
                return "'" + stringValue.replace("'", "''") + "'";
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return "'" + cell.getDateCellValue() + "'";
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return cell.getBooleanCellValue() ? "1" : "0";
            case BLANK:
                return "NULL";
            case FORMULA:
                // Handle formula cells by evaluating them first
                CellValue cellValue = cell.getSheet().getWorkbook().getCreationHelper()
                        .createFormulaEvaluator().evaluate(cell);
                return formatCellValue(cellValue, dataType);
            default:
                return "NULL";
        }
    }

    // Overloaded method to handle CellValue
    private String formatCellValue(CellValue cellValue, String dataType) {
        switch (cellValue.getCellType()) {
            case STRING:
                String stringValue = cellValue.getStringValue().trim();
                if (stringValue.isEmpty()) return "NULL";
                if (dataType.startsWith("INT") || dataType.startsWith("FLOAT") || dataType.startsWith("DOUBLE") || dataType.startsWith("DECIMAL")) {
                    try {
                        return stringValue;
                    } catch (NumberFormatException e) {
                        return "NULL";
                    }
                }
                return "'" + stringValue.replace("'", "''") + "'";
            case NUMERIC:
                // For dates, we'd need the original cell which we don't have here
                // So we'll just treat all numeric values as numbers
                return String.valueOf(cellValue.getNumberValue());
            case BOOLEAN:
                return cellValue.getBooleanValue() ? "1" : "0";
            case BLANK:
                return "NULL";
            case ERROR:
                return "NULL";
            default:
                return "NULL";
        }
    }

    public List<ColumnMapping> getColumnMappings() {
        return columnMappings;
    }
}
