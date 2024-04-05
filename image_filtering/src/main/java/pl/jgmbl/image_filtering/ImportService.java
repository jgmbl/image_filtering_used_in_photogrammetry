package pl.jgmbl.image_filtering;

import javafx.scene.control.Alert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportService {

    /** Absolute paths to JPG files */
    public List<List<String>> listOfJPGFiles(String path) {
        ArrayList<List<String>> filesAndPathList = new ArrayList<>();

        File folder = new File(path);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (checkJpgJpegExtensions(file)) {
                        ArrayList<String> pathAndFile = new ArrayList<>();

                        pathAndFile.add(path);
                        pathAndFile.add(file.getName());
                        filesAndPathList.add(pathAndFile);
                    }
                }
            }
        }
        return filesAndPathList;
    }


    public boolean checkJpgJpegExtensions(File file) {
        return file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg");
    }
}
