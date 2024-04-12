package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class SharpeningFilterController extends IndexFilterController {
    private final String typeOfFiltering = "sharpening";

    @FXML
    private Label filterInfo;

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
    @FXML
    private Label originalImageInfo;
    @FXML
    private ImageView originalSampleImage;

    public void initialize() {
        filterInfo.setText("The Sharpening filter is used to sharpen the edges in a image while increasing the noise." +
                " The level of sharpening was automatically selected. \n \n" +
                "Exporting overwrites the data. If you want to do some filtering, be sure to change the save folder." +
                " The process will end when the message is displayed.");
        filterInfo.setWrapText(true);

        exportInfo.setText("Enter the full path to the image saving folder: ");
        sampleImageInfo.setText("Filtered sample image: ");
        originalImageInfo.setText("Original sample image: ");
    }

    @Override
    public void onExportClick() {
        String exportPath = this.exportPath.getText();

        this.exportPath.clear();

        try {
            exportImages(exportPath, typeOfFiltering);
            setUI(exportData, exportedImagesList, exportPath, sampleImage, originalSampleImage, typeOfFiltering);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
