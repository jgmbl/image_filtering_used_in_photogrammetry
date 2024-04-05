package pl.jgmbl.image_filtering;

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
                    if (checkJPGExtension(file)) {
                        filesList.add(path + file.getName());
                    }
                }
            }
        }
        return filesList;
    }

    public boolean checkJPGExtension(File file) {
        return file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg");
    }
}
