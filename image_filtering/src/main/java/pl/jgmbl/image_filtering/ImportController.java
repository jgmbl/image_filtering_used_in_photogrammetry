package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class ImportController {
    @FXML
    private TextField path;
    @FXML
    private ListView<String> importedImagesList;

    private final ImportService importService = new ImportService();

    private final ManageTxtFiles manageTxtFiles = new ManageTxtFiles();

    @FXML
    protected void onImportClick() {
        String importDirectory = path.getText();

        List<String> listOfJPGFiles = importService.listOfJPGFiles(importDirectory);

        try {
            if (ManageTxtFiles.checkIfPathExists(importDirectory)) {
                manageTxtFiles.writeListToTxtFile(listOfJPGFiles);
                importedImagesList.getItems().clear();
                importedImagesList.getItems().addAll(listOfJPGFiles);

                AddAlert.addInfoAlert("Import succeed", "Import more images or choose filtering option from main menu.");
            } else {
                AddAlert.addErrorAlert("Import failed", "Check if path is correct.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
