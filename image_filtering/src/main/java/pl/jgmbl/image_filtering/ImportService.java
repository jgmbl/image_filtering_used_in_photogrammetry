package pl.jgmbl.image_filtering;

import java.io.File;
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
                    if (ManageTxtFiles.checkJpgJpegExtensions(path)) {
                        filesAndPathSet.add(path + file.getName());
                    }
                }
            }
        }
        return filesAndPathSet;
    }

    private HashSet<String> setOfNotAlreadyImportedFiles(HashSet<String> txtFileImages, HashSet<String> importedImages) {
        HashSet<String> notImportedFiles = new HashSet<>();

        for (String importedImage : importedImages) {
            if (!txtFileImages.contains(importedImage)) {
                notImportedFiles.add(importedImage);
            }
        }

        return notImportedFiles;
    }
}
