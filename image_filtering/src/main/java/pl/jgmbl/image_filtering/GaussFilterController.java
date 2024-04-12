package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class GaussFilterController extends IndexFilterController {
    public final String typeOfFiltering = "gaussian";

    @FXML
    private Label filterInfo;
    @FXML
    private TextField blurringParameter;

    @FXML
    private Label exportInfo;
    @FXML
    private TextField exportPath;
    @FXML
    private Label exportData;
    @FXML
    private ListView<String> exportedImagesList;

    @FXML
    private Label sampleImageInfo;
    @FXML
    private ImageView sampleImage;


    public void initialize() {
        filterInfo.setText("Gauss filtering is used to reduce noise and evenly smooth the image." +
                " The value of the blur depends on the size of kernel. \n \n" +
                "Exporting overwrites the data. If you want to do some filtering, be sure to change the save folder." +
                " The process will end when the message is displayed. \n \n" + "Value of blur parameter [3, 5, 9, 15]: ");
        filterInfo.setWrapText(true);

        exportInfo.setText("Enter the full path to the image saving folder: ");
        sampleImageInfo.setText("Sample image:");
    }

    @FXML
    @Override
    public void onExportClick() {
        String exportPath = this.exportPath.getText();
        String blurringParameterString = blurringParameter.getText();

        this.exportPath.clear();
        blurringParameter.clear();

        try {
            exportImages(exportPath, blurringParameterString, typeOfFiltering);
            setUI(exportData, exportedImagesList, exportPath, sampleImage, typeOfFiltering);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
