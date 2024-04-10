package pl.jgmbl.image_filtering;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class IndexController {
    private final ManageTxtFiles manageTxtFiles = new ManageTxtFiles();
    private final String PATH = "src/main/resources/images.txt";


    @FXML
    private Label unsharpMasking;
    @FXML
    private Label importImages;
    @FXML
    private Label gaussFilter;
    @FXML
    private Label medianFilter;

    @FXML
    protected void onImportButtonClick() {

        openWindow("import-view.fxml", "Import");
    }

    @FXML
    protected void onGaussFilterButtonClick() {
        if (!InputValidationService.checkIfImagesAreImported("src/main/resources/images.txt")) {
            AddAlert.addErrorAlert("Filtering is not possible", "Import photos before filtering.");
        } else {
            openWindow("gauss-view.fxml", "Gauss filter");
        }
    }

    @FXML
    protected void onMedianFilterButtonClick() {
        medianFilter.setText("Filtering images...");
    }

    @FXML
    protected void onUnsharpMaskingButtonClick() {
        unsharpMasking.setText("Filtering images...");
    }

    private void openWindow(String resource, String title) {
        try {
            manageTxtFiles.createTxtFileIfItDoesNotExist(Paths.get(PATH));

            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = new Stage();

            fxmlLoader.setLocation(getClass().getResource(resource));

            Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}