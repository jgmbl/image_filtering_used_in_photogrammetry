package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ImportController {
    @FXML
    private Label importData;

    @FXML
    protected void onImportButtonClick() {
        importData.setText("Importing images...");
    }
}
