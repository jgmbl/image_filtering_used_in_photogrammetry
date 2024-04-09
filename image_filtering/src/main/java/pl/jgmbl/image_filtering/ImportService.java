package pl.jgmbl.image_filtering;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;

public class ImportService {
    private ManageTxtFiles manageTxtFiles = new ManageTxtFiles();

    public ImportService() {
    }

    /** Absolute paths to JPG files */
    public HashSet<String> listOfJPGFiles(String path) {

        HashSet<String> filesAndPathSet = new HashSet<>();

        File folder = new File(path);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (checkJpgJpegExtensions(file)) {
                        filesAndPathSet.add(path + file.getName());
                    }
                }
            }
        }
        return filesAndPathSet;
    }


    private boolean checkJpgJpegExtensions(File file) {
        return file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg");
    }
}
