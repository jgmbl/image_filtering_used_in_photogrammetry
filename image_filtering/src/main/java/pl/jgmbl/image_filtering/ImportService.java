package pl.jgmbl.image_filtering;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public void refreshListView(String txtFilePath, ListView<String> listView) {
        try {
            Set<String> importedImagesSet = manageTxtFiles.readTxtFile(txtFilePath);
            ObservableList<String> listOfImagesFromFile = FXCollections.observableArrayList(importedImagesSet);

            if (ManageTxtFiles.checkIfPathExists(txtFilePath)) {
                listView.setItems(listOfImagesFromFile);
            } else {
                listView.setItems(null);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isFolderImported(String filePath, Set<String> importedImages) throws IOException {
        Set<String> txtFile = manageTxtFiles.readTxtFile(filePath);

        if (txtFile.containsAll(importedImages)) {
            return true;
        } else {
            return false;
        }
    }
}
