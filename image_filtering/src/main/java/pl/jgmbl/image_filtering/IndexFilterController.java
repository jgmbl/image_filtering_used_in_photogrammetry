package pl.jgmbl.image_filtering;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public abstract class IndexFilterController implements FilterController {
    final String IMAGES_TXT_PATH = "src/main/resources/images.txt";
    protected final ProcessImagesService processImagesService = new ProcessImagesService();

    @FXML
    public abstract void onExportClick();

    protected void exportImages(String exportPath, String blurringParameterString, String typeOfFiltering) throws IOException {
        exportPath = InputValidationService.returnCorrectPath(exportPath);

        if (!InputValidationService.checkIfExportPathIsCorrect(exportPath)) {
            AddAlert.addErrorAlert("Export failed", "Check if the folder path is correct.");
            return;
        }

        if (!InputValidationService.checkIfParameterIsCorrect(blurringParameterString)) {
            AddAlert.addErrorAlert("Export failed", "Check if the blur parameter is correct.");
            return;
        }

        int blurringParameterValue = Integer.parseInt(blurringParameterString);

        processImagesService.filtering(typeOfFiltering, IMAGES_TXT_PATH, exportPath, blurringParameterValue);
    }

    protected void setUI(Label exportData, ListView<String> exportedImagesList,
                         String exportPath, String typeOfFiltering) throws FileNotFoundException {
        List<String> listOfBlurredImages = processImagesService.listOfFilteredImages(exportPath, typeOfFiltering + "_");
        ObservableList<String> blurredImagesObservableList = FXCollections.observableArrayList(listOfBlurredImages);

        exportData.setText("List of filtered images in export folder: ");
        exportedImagesList.setItems(blurredImagesObservableList);

        AddAlert.addInfoAlert("Export succeed", "Filtered images are saved. If you want to change the level of blur, just do the filtering again.");

//        FileInputStream input = new FileInputStream(ProcessImagesService.returnFirstFilteredImage(exportPath, typeOfFiltering));
//        Image image = new Image(input);
//        sampleImage.setImage(image);
    }
}
