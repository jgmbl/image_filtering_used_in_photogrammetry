package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class GaussFilterController {
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

    public void initialize() {
        gaussFilterInfo.setText("Gauss filtering is used to reduce noise and smooth the image." +
                " The value of the blur depends on the blur parameter, whose value varies from 0.5 to 5.0." +
                " The Gauss filter evenly blurs the image. \n \n" + "Value of blur parameter: ");
        gaussFilterInfo.setWrapText(true);

        exportInfo.setText("Enter the full path to the image saving folder: ");

//        if (!GaussFilterService.checkIfImagesAreImported("src/main/resources/images.txt")) {
//            AddAlert.addErrorAlert("Filtering is not possible", "Import photos before filtering.");
//        }
    }


    public void onExportClick() {
        String blurringParameterString = blurringParameter.getText();
        String exportPath = gaussExportPath.getText();

        blurringParameter.clear();
        gaussExportPath.clear();

        if (!GaussFilterService.checkIfExportPathIsCorrect(exportPath)) {
            AddAlert.addErrorAlert("Export failed", "Check if the folder path is correct.");
        }

        if (GaussFilterService.checkIfParameterIsCorrect(blurringParameterString)) {
            float blurringParameterValue = Float.parseFloat(blurringParameterString);
        } else {
            AddAlert.addErrorAlert("Export failed", "Check if the blur parameter is correct.");
        }
    }
}
