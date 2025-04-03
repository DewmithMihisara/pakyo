package com.hcodeSolutions.controller;

import com.hcodeSolutions.dto.ColumnMapping;
import com.hcodeSolutions.model.ExcelToSQLConverter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dewmith Mihisara
 * @date 2025-04-03
 * @since 0.0.1
 */
public class MainFormController {
    @FXML
    private TextArea createQueryText;

    @FXML
    private TextField excelFilePath;

    @FXML
    private TableView<ColumnMapping> mappingTable;

    @FXML
    private TextField saveLocation;

    @FXML
    private TextField fileName;

    @FXML
    private Label statusLabel;

    @FXML
    private Button generateButton;

    private BooleanProperty generateButtonEnabled = new SimpleBooleanProperty(false);
    private ExcelToSQLConverter converter = new ExcelToSQLConverter();
    private File excelFile;
    private File saveDirectory;

    @FXML
    public void initialize() {
        // Bind the button's disable property to our custom property
        generateButton.disableProperty().bind(generateButtonEnabled.not());

        // Add listeners to update the enabled state
        createQueryText.textProperty().addListener((obs, oldVal, newVal) -> updateGenerateButtonState());
        excelFilePath.textProperty().addListener((obs, oldVal, newVal) -> updateGenerateButtonState());
        saveLocation.textProperty().addListener((obs, oldVal, newVal) -> updateGenerateButtonState());
    }

    private void updateGenerateButtonState() {
        boolean enabled = !createQueryText.getText().isEmpty()
                && !excelFilePath.getText().isEmpty()
                && !saveLocation.getText().isEmpty();
        generateButtonEnabled.set(enabled);
    }

    @FXML
    private void handleParseQuery() {
        String createQuery = createQueryText.getText();
        if (createQuery.isEmpty()) {
            showStatus("Error: Please enter a CREATE TABLE query", true);
            return;
        }

        try {
            converter.parseCreateTableQuery(createQuery);
            mappingTable.getItems().setAll(converter.getColumnMappings());
            showStatus("Table structure parsed successfully", false);
        } catch (Exception e) {
            showStatus("Error parsing query: " + e.getMessage(), true);
        }
    }

    @FXML
    private void handleBrowseExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Excel File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls")
        );

        excelFile = fileChooser.showOpenDialog(excelFilePath.getScene().getWindow());
        if (excelFile != null) {
            excelFilePath.setText(excelFile.getAbsolutePath());

            try {
                Map<String, String> headers = converter.getExcelHeaders(excelFile.getAbsolutePath());
                // Try to auto-match columns
                Map<String, String> excelToDbMapping = new HashMap<>();
                for (ColumnMapping mapping : converter.getColumnMappings()) {
                    if (headers.containsKey(mapping.getDbColumn())) {
                        excelToDbMapping.put(mapping.getDbColumn(), mapping.getDbColumn());
                    }
                }
                converter.mapColumns(excelToDbMapping);
                mappingTable.getItems().setAll(converter.getColumnMappings());
                showStatus("Excel file loaded. Please verify column mappings.", false);
            } catch (IOException e) {
                showStatus("Error loading Excel file: " + e.getMessage(), true);
            }
        }
    }

    @FXML
    private void handleBrowseSaveLocation() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Save Location");

        saveDirectory = directoryChooser.showDialog(saveLocation.getScene().getWindow());
        if (saveDirectory != null) {
            saveLocation.setText(saveDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void handleGenerateSQL() {
        if (excelFile == null) {
            showStatus("Error: Please select an Excel file", true);
            return;
        }

        if (saveDirectory == null) {
            showStatus("Error: Please select a save location", true);
            return;
        }

        String outputFileName = fileName.getText();
        if (outputFileName.isEmpty()) {
            outputFileName = "output.sql";
        }
        if (!outputFileName.endsWith(".sql")) {
            outputFileName += ".sql";
        }

        File outputFile = new File(saveDirectory, outputFileName);

        try {
            converter.generateInsertQueries(excelFile.getAbsolutePath(), outputFile.getAbsolutePath());
            showStatus("SQL file generated successfully at: " + outputFile.getAbsolutePath(), false);
        } catch (IOException e) {
            showStatus("Error generating SQL file: " + e.getMessage(), true);
        }
    }

    private void showStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setStyle(isError ? "-fx-text-fill: #D32F2F;" : "-fx-text-fill: #388E3C;");
    }
}
