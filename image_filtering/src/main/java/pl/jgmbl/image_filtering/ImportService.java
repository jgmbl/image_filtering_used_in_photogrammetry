package pl.jgmbl.image_filtering;

import javafx.scene.control.Alert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportService {

    /** Absolute paths to JPG files */
    public List<String> listOfJPGFiles(String path) {
        ArrayList<String> filesList = new ArrayList<>();

        File folder = new File(path);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (checkJpgJpegExtensions(file)) {
                        filesList.add(path + file.getName());
                    }
                }
            }
        }
        return filesList;
    }


    public void storeImagesPathsInMemory(List<String> paths) {

    }



    public boolean checkJpgJpegExtensions(File file) {
        return file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg");
    }

    public void addAlert (String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information");
        alert.setHeaderText("Import succeed");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
