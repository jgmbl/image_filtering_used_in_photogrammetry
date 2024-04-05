package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexController {
    @FXML
    private Label unsharpMasking;
    @FXML
    private Label importImages;
    @FXML
    private Label gaussFilter;
    @FXML
    private Label medianFilter;
    @FXML
    private Label exportImages;

    @FXML
    protected void onImportButtonClick() {

        openImportWindow();
    }

    @FXML
    protected void onGaussFilterButtonClick() {
        gaussFilter.setText("Filtering images...");
    }

    @FXML
    protected void onMedianFilterButtonClick() {
        medianFilter.setText("Filtering images...");
    }

    @FXML
    protected void onUnsharpMaskingButtonClick() {
        unsharpMasking.setText("Filtering images...");
    }

    @FXML
    protected void onExportButtonClick() {
        exportImages.setText("Saving images...");
    }

    private void openImportWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = new Stage();

            fxmlLoader.setLocation(getClass().getResource("import-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Import");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}