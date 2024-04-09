package pl.jgmbl.image_filtering;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashSet;

public class ImportController {
    @FXML
    private TextField path;
    @FXML
    private ListView<String> importedImagesList;
    @FXML
    private Label importData;

    @FXML
    private TextField deletePath;
    @FXML
    private Label deleteData;

    private final ImportService importService = new ImportService();

    private final ManageTxtFiles manageTxtFiles = new ManageTxtFiles();

    @FXML
    protected void onImportClick() {
        String importDirectory = path.getText();

        HashSet<String> listOfJPGFiles = importService.listOfJPGFiles(importDirectory);

        try {
            if (ManageTxtFiles.checkIfPathExists(importDirectory)) {
                manageTxtFiles.writeListToTxtFile(listOfJPGFiles, "src/main/resources/images.txt", true);

                HashSet<String> listOfImagesFromFile = manageTxtFiles.readTxtFile("src/main/resources/images.txt");
                ObservableList<String> importedImagesListData = FXCollections.observableArrayList(listOfImagesFromFile);

                importedImagesList.setItems(importedImagesListData);
                path.clear();

                importData.setText("List of currently imported JPG/JPEG images:");
                AddAlert.addInfoAlert("Import succeed", "Import more images or choose filtering option from main menu.");
            } else {
                AddAlert.addErrorAlert("Import failed", "Check if the path is correct.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDeleteClick() throws IOException {
        String deleteDirectory = deletePath.getText();

        if (ManageTxtFiles.checkIfPathExists(deleteDirectory)) {

            manageTxtFiles.deleteImagesByFolderPath(deleteDirectory, "src/main/resources/images.txt");
            deletePath.clear();

            deleteData.setText("Deleted imported images");

            AddAlert.addInfoAlert("Deletion succeed", "Images are deleted.");
        } else {
            AddAlert.addErrorAlert("Deletion failed", "Check if the path is correct.");
        }

    }
}
