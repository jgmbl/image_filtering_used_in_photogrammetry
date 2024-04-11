package pl.jgmbl.image_filtering;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class GaussFilterController {
    String TXT_PATH = "src/main/resources/images.txt";

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

    @FXML
    private Label sampleImageInfo;
    @FXML
    private ImageView sampleImage;

    private final ProcessImagesService processFiles = new ProcessImagesService();


    public void initialize() {
        gaussFilterInfo.setText("Gauss filtering is used to reduce noise and evenly smooth the image." +
                " The value of the blur depends on the size of kernel. \n \n" +
                "Exporting overwrites the data. If you want to do some filtering, be sure to change the save folder." +
                " The process will end when the message is displayed. \n \n" + "Value of blur parameter [3, 5, 9, 15]: ");
        gaussFilterInfo.setWrapText(true);

        exportInfo.setText("Enter the full path to the image saving folder: ");
        sampleImageInfo.setText("Sample image:");
    }


    public void onExportClick() throws IOException {
        String exportPath = gaussExportPath.getText();
        String blurringParameterString = blurringParameter.getText();

        exportPath = InputValidationService.returnCorrectPath(exportPath);

        if (!InputValidationService.checkIfExportPathIsCorrect(exportPath)) {
            AddAlert.addErrorAlert("Export failed", "Check if the folder path is correct.");
            return;
        }

        if (!InputValidationService.checkIfParameterIsCorrect(blurringParameterString)) {
            AddAlert.addErrorAlert("Export failed", "Check if the blur parameter is correct.");
            return;
        }

        double blurringParameterValue = Double.parseDouble(blurringParameterString);

        processFiles.gaussianFiltering(TXT_PATH, exportPath, blurringParameterValue);

        List<String> listOfBlurredImages = processFiles.listOfFilteredImages(exportPath, "gaussian_");
        ObservableList<String> blurredImagesObservableList = FXCollections.observableArrayList(listOfBlurredImages);

        exportData.setText("List of filtered images in export folder: ");
        exportedImagesList.setItems(blurredImagesObservableList);

        gaussExportPath.clear();
        blurringParameter.clear();

        AddAlert.addInfoAlert("Export succeed", "Filtered images are saved. If you want to change the level of blur, just do the filtering again.");

        FileInputStream input = new FileInputStream(processFiles.returnFirstFilteredImage(exportPath, "gaussian_"));
        Image image = new Image(input);
        sampleImage.setImage(image);
    }
}
