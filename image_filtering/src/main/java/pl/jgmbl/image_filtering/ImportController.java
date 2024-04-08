package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ImportController {
    @FXML
    private TextField path;

    private final ImportService importService = new ImportService();

    private final ManageTxtFiles manageTxtFiles = new ManageTxtFiles();

    @FXML
    protected void onImportClick() {
        String importDirectory = path.getText();

        List<String> listOfJPGFiles = importService.listOfJPGFiles(importDirectory);

        try {
            if (checkIfPathExists(importDirectory)) {
                manageTxtFiles.writeListToTxtFile(listOfJPGFiles);
                AddAlert.addInfoAlert("Import succeed", "Choose filtering option from main menu");
            } else {
                AddAlert.addErrorAlert("Import failed", "Check if path is correct");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkIfPathExists(String path) {
        Path path1 = Paths.get(path);

        return Files.exists(path1);
    }
}
