package pl.jgmbl.image_filtering;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Set;

public class ImportController {

    private final String PATH = "src/main/resources/images.txt";
    @FXML
    private Label importInfo;
    @FXML
    private Label importedPhotosInfo;
    @FXML
    private ListView<String> listOfImages;

    @FXML
    private TextField importPath;
    @FXML
    private ListView<String> importedImagesList;
    @FXML
    private Label importData;

    @FXML
    private TextField deletePath;
    @FXML
    private Label deleteData;
    @FXML
    private Label deletionInfo;

    private final ImportService importService = new ImportService();

    private final ManageTxtFiles manageTxtFiles = new ManageTxtFiles();

    public void initialize() {
        importInfo.setText("Enter the absolute path to the folder to import JPG/JPEG images: ");
        importedPhotosInfo.setText("List of imported images:");
        importData.setText("List of currently imported images:");
        deletionInfo.setText("Enter the absolute path to the folder from which the images are to be deleted" +
                " or select them in the list of imported images: ");
        deletionInfo.setWrapText(true);
    }

    @FXML
    protected void onRefreshClick() throws IOException {
        Set<String> listOfImagesFromTxtFile = manageTxtFiles.readTxtFile(PATH);
        ObservableList<String> listOfImagesFromFile = FXCollections.observableArrayList(listOfImagesFromTxtFile);

        if (ManageTxtFiles.checkIfPathExists(PATH)) {
            this.listOfImages.setItems(listOfImagesFromFile);
        } else {
            this.listOfImages.setItems(null);
        }

    }

    @FXML
    protected void onImportClick() {
        String importDirectory = importPath.getText();

        Set<String> listOfJPGFiles = importService.listOfJPGFilesFromFolder(importDirectory);

        try {
            if (ManageTxtFiles.checkIfPathExists(importDirectory)) {
                if (!importService.isFolderImported(PATH, listOfJPGFiles)) {
                    manageTxtFiles.writeListToTxtFile(listOfJPGFiles, "src/main/resources/images.txt", true);

                    Set<String> listOfImagesFromFile = manageTxtFiles.readTxtFile("src/main/resources/images.txt");
                    ObservableList<String> importedImagesListData = FXCollections.observableArrayList(listOfImagesFromFile);

                    importedImagesList.setItems(importedImagesListData);
                    importPath.clear();

                    AddAlert.addInfoAlert("Import succeed", "Import more images or choose filtering option from main menu.");
                } else {
                    AddAlert.addErrorAlert("Import failed", "Folder has already been imported.");
                }
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

            Set<String> deletedImagesFile = manageTxtFiles.deletedImagesByFolderPath(deleteDirectory, PATH);
            manageTxtFiles.writeListToTxtFile(deletedImagesFile, PATH, false);
            deletePath.clear();

            deleteData.setText("Deleted imported images.");

            AddAlert.addInfoAlert("Deletion succeed", "Images are deleted.");
        } else {
            AddAlert.addErrorAlert("Deletion failed", "Check if the path is correct.");
        }

    }
}
