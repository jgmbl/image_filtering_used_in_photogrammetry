package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SharpeningFilterController {
    private final String typeOfFiltering = "sharpening";
    private final String blurringParameter = "1";

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


}
