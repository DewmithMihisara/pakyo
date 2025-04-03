package com.hcodeSolutions.dto;


/**
 * @author Dewmith Mihisara
 * @date 2025-04-03
 * @since 0.0.1
 */
public class ColumnMapping {
    private String excelColumn;
    private String dbColumn;
    private String dataType;

    public ColumnMapping() {
    }

    public ColumnMapping(String excelColumn, String dbColumn, String dataType) {
        this.excelColumn = excelColumn;
        this.dbColumn = dbColumn;
        this.dataType = dataType;
    }

    public String getDbColumn() {
        return dbColumn;
    }

    public void setDbColumn(String dbColumn) {
        this.dbColumn = dbColumn;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getExcelColumn() {
        return excelColumn;
    }

    public void setExcelColumn(String excelColumn) {
        this.excelColumn = excelColumn;
    }
}
