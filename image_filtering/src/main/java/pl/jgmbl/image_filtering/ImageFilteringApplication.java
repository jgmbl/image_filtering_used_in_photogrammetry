package pl.jgmbl.image_filtering;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ImageFilteringApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ImageFilteringApplication.class.getResource("index-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("jgmbl - Image Filtering");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        deleteTxtFile("src/main/resources/images.txt");
    }

    private void deleteTxtFile(String path) {
        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}