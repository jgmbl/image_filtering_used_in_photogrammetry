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
    private final String IMAGES_TXT_PATH = "src/main/resources/images.txt";

    @FXML
    private Label appInfo;

    @FXML
    private Label unsharpMasking;
    @FXML
    private Label importImages;
    @FXML
    private Label gaussFilter;
    @FXML
    private Label medianFilter;

    @FXML
    private Label aboutCreator;

    public void initialize() {
        appInfo.setText("The application is designed for photogrammetric purposes." +
                " It will improve the quality of your point cloud and 3D model." +
                " With it, the common points of the images will be accurately connected. \n" +
                "You can only import and save JPG/JPEG images. Remember to prepare disk space before exporting." +
                " For more information about the properties of the filters, see the individual tabs.");
        aboutCreator.setText("\n \n \n Check out my other projects at https://github.com/jgmbl");
    }

    @FXML
    protected void onImportButtonClick() {

        openWindow("import-view.fxml", "Import");
    }

    @FXML
    protected void onGaussFilterButtonClick() {
        filterButton("gauss-view.fxml", "Gauss Filter");
    }

    @FXML
    protected void onMedianFilterButtonClick() {
        filterButton("median-view.fxml", "Median Filter");
    }

    @FXML
    protected void onUnsharpMaskingButtonClick() {
        unsharpMasking.setText("Filtering images...");
    }


    private void filterButton (String resource, String title) {
        if (!InputValidationService.checkIfImagesAreImported(IMAGES_TXT_PATH)) {
            AddAlert.addErrorAlert("Filtering is not possible", "Import photos before filtering.");
        } else {
            openWindow(resource, title);
        }
    }

    private void openWindow(String resource, String title) {
        try {
            manageTxtFiles.createTxtFileIfItDoesNotExist(Paths.get(IMAGES_TXT_PATH));

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