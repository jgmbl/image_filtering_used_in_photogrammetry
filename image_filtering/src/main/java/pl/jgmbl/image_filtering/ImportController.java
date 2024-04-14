package pl.jgmbl.image_filtering;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Set;

public class ImportController {

    private final String PATH = "src/main/resources/images.txt";

    @FXML
    private Label importInfo;
    @FXML
    private TextField importPath;

    @FXML
    private Label importedPhotosInfo;
    @FXML
    private ListView<String> imagesList;
    @FXML
    private TextField deletePath;
    @FXML
    private Label deleteData;
    @FXML
    private Label deletionInfo;

    private final ImportService importService = new ImportService();

    private final ManageTxtFiles manageTxtFiles = new ManageTxtFiles();

    public void initialize() throws IOException {
        importInfo.setText("Enter the absolute path to the folder to import JPG/JPEG images: ");
        importedPhotosInfo.setText("List of imported images:");
        deletionInfo.setText("Enter the absolute path to the folder from which the images are to be deleted" +
                " or select them in the list of imported images: ");
        deletionInfo.setWrapText(true);
        imagesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<String> stringObservableList = importService.refreshedObservableListFromTxtFile(PATH);
        imagesList.setItems(stringObservableList);
    }


    @FXML
    protected void onImportClick() {
        String importDirectory = importPath.getText();

        Set<String> listOfJPGFiles = importService.listOfJPGFilesFromFolder(importDirectory);

        try {
            if (ManageTxtFiles.checkIfPathExists(importDirectory)) {
                if (!importService.isFolderImported(PATH, listOfJPGFiles)) {
                    manageTxtFiles.writeListToTxtFile(listOfJPGFiles, "src/main/resources/images.txt", true);

                    ObservableList<String> stringObservableList = importService.refreshedObservableListFromTxtFile(PATH);
                    imagesList.setItems(stringObservableList);

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

    @FXML
    public void onDeleteClick() throws IOException {
        String deleteDirectory = deletePath.getText();

        if (!deleteDirectory.isEmpty()) {
            if (ManageTxtFiles.checkIfPathExists(deleteDirectory)) {

                Set<String> deletedImagesFile = manageTxtFiles.deleteImagesByFolderPath(deleteDirectory, PATH);
                manageTxtFiles.writeListToTxtFile(deletedImagesFile, PATH, false);
                deletePath.clear();

                ObservableList<String> stringObservableList = importService.refreshedObservableListFromTxtFile(PATH);
                imagesList.setItems(stringObservableList);

                deleteData.setText("Deleted imported image(s).");

                AddAlert.addInfoAlert("Deletion succeed", "Images are deleted.");
            } else {
                AddAlert.addErrorAlert("Deletion failed", "Check if the path is correct.");
            }
        } else {
            int selectedIndex = imagesList.getSelectionModel().getSelectedIndex();

            if (selectedIndex != -1) {
                ObservableList<String> selectedImages = imagesList.getSelectionModel().getSelectedItems();
                manageTxtFiles.deleteImagesByImagesPaths(selectedImages, PATH);

                ObservableList<String> stringObservableList = importService.refreshedObservableListFromTxtFile(PATH);
                imagesList.setItems(stringObservableList);

                AddAlert.addInfoAlert("Deletion succeed", "Images are deleted.");
            } else {
                AddAlert.addErrorAlert("Deletion failed", "Select images from the list.");
            }
        }

    }
}
