package pl.jgmbl.image_filtering;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ImportService {
    private ManageTxtFiles manageTxtFiles = new ManageTxtFiles();

    public ImportService() {
    }

    /** Absolute paths to JPG files */
    public Set<String> listOfJPGFilesFromFolder(String path) {

        Set<String> filesAndPathSet = new HashSet<>();

        File folder = new File(path);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (ManageTxtFiles.checkJpgJpegExtensions(file.getName())) {
                        filesAndPathSet.add(path + file.getName());
                    }
                }
            }
        }
        return filesAndPathSet;
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
