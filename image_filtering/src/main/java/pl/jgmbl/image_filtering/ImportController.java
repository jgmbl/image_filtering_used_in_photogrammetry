package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
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

        System.out.println(importDirectory);

        List<String> listOfFiles = importService.listOfFiles(importDirectory);

        for (String file : listOfFiles) {
            System.out.println(file);
        }
    }
}
