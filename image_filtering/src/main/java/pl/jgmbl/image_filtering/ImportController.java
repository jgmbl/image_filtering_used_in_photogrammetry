package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ImportController {
    @FXML
    private Label importImages;

    @FXML
    protected void onImportButtonClick() {
        importImages.setText("Importing images...");
    }
}