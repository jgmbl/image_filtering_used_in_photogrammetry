package pl.jgmbl.image_filtering;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class IndexController {
    @FXML
    private Label importImages;
    @FXML
    private Label gaussFilter;
    @FXML
    private Label medianFilter;

    @FXML
    protected void onImportButtonClick() {
        importImages.setText("Importing images...");
    }

    @FXML
    protected void onGaussFilterButtonClick() {
        gaussFilter.setText("Filtering images...");
    }

    @FXML
    protected void onMedianFilterButtonClick() {
        medianFilter.setText("Filtering images...");
    }
}