package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class IndexController {
    @FXML
    private Label importImages;
    @FXML
    private Label gaussFilter;

    @FXML
    protected void onImportButtonClick() {
        importImages.setText("Importing images...");
    }

    @FXML
    protected void onGaussFilterButtonClick() {
        gaussFilter.setText("Filtering images...");
    }
}