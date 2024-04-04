package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ImportService {
    @FXML
    private Label exportImages;

    @FXML
    protected void onImportButtonClick() {
        exportImages.setText("Import images...");
    }
}
