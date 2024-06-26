package pl.jgmbl.image_filtering;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ImportService {
    private ManageTxtFiles manageTxtFiles = new ManageTxtFiles();

    public ImportService() {
    }

    /**
     * Absolute paths to JPG files
     */
    public Set<String> listOfJPGFilesFromFolder(String path) {

        Set<String> filesAndPathSet = new HashSet<>();

        File folder = new File(path);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (ManageTxtFiles.checkJpgJpegExtensions(file.getName())) {
                        if (!path.endsWith("/")) {
                            filesAndPathSet.add(path + '/' + file.getName());
                        } else {
                            filesAndPathSet.add(path + file.getName());
                        }
                    }
                }
            }
        }
        return filesAndPathSet;
    }

    public ObservableList<String> refreshedObservableListFromTxtFile(String txtFilePath) {
        Set<String> importedImagesSet = null;
        try {
            importedImagesSet = manageTxtFiles.readTxtFile(txtFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FXCollections.observableArrayList(importedImagesSet);
    }


    public boolean isFolderImported(String filePath, Set<String> importedImages) throws IOException {
        Set<String> txtFile = manageTxtFiles.readTxtFile(filePath);

        return txtFile.containsAll(importedImages) || importedImages.equals(txtFile);
    }
}
