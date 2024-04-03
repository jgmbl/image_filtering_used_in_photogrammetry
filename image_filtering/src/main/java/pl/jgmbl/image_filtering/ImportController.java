package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ImportController {
    @FXML
    private Label importData;
    @FXML
    private TextField path;

    @FXML
    protected void onImportButtonClick() {

        java.lang.String importPath = path.getText();
        importData.setText(importPath);


    }
}
