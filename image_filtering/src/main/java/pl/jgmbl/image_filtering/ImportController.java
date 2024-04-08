package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.List;

public class ImportController {
    @FXML
    private TextField path;

    private final ImportService importService = new ImportService();

    public ImportController() {
    }

    @FXML
    protected void onImportClick() {
        String importDirectory = path.getText();

        List<String> listOfJPGFiles = importService.listOfJPGFiles(importDirectory);

        AddAlert.addInfoAlert("Import succeed", "Choose filtering option from main menu");
    }
}
