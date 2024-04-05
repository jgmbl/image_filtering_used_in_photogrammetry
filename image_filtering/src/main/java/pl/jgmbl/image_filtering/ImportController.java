package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class ImportController {
    @FXML
    private Label importData;
    @FXML
    private TextField path;

    private final ImportService importService = new ImportService();

    public ImportController() {
    }

    @FXML
    protected void onImportClick() {
        String importDirectory = path.getText();

        List<String> listOfFiles = importService.listOfJPGFiles(importDirectory);

        importService.addAlert("Choose filtering option");
    }
}
