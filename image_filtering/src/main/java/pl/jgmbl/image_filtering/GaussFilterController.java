package pl.jgmbl.image_filtering;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class GaussFilterController {
    String PATH = "src/main/resources/images.txt";

    @FXML
    private Label gaussFilterInfo;
    @FXML
    private TextField blurringParameter;

    @FXML
    private Label exportInfo;
    @FXML
    private TextField gaussExportPath;
    @FXML
    private Label exportData;
    @FXML
    private ListView<String> exportedImagesList;

    private final ProcessFilesService processFiles = new ProcessFilesService();


    public void initialize() {
        gaussFilterInfo.setText("Gauss filtering is used to reduce noise and smooth the image." +
                " The value of the blur depends on the blur parameter, whose value varies from 0.5 to 5.0." +
                " The Gauss filter evenly blurs the image. \n \n" + "Value of blur parameter: ");
        gaussFilterInfo.setWrapText(true);

        exportInfo.setText("Enter the full path to the image saving folder: ");
    }


    public void onExportClick() {
        String blurringParameterString = blurringParameter.getText();
        String exportPath = gaussExportPath.getText();

        blurringParameter.clear();
        gaussExportPath.clear();

        if (!InputValidationService.checkIfExportPathIsCorrect(exportPath)) {
            AddAlert.addErrorAlert("Export failed", "Check if the folder path is correct.");
        }

        if (!InputValidationService.checkIfParameterIsCorrect(blurringParameterString)) {
            AddAlert.addErrorAlert("Export failed", "Check if the blur parameter is correct.");
        }
        float blurringParameterValue = Float.parseFloat(blurringParameterString);

        try {
            processFiles.gaussianFiltering(PATH, exportPath, blurringParameterValue);

            List<String> listOfBlurredImages = processFiles.listOfFilteredImages(exportPath);

            ObservableList<String> blurredImagesObservableList = FXCollections.observableArrayList(listOfBlurredImages);


            exportedImagesList.setItems(blurredImagesObservableList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
