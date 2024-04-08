package pl.jgmbl.image_filtering;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class ImportService {
    private ManageTxtFiles manageTxtFiles;

    public ImportService(ManageTxtFiles manageTxtFiles) {
        this.manageTxtFiles = manageTxtFiles;
    }

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

    public void deleteImagesByFolderPath(String path) {
        try {
            HashSet<String> imagesFromTxtFile = manageTxtFiles.readTxtFile(path);

            for (String image : imagesFromTxtFile) {
                if (image.contains(path)) {
                    imagesFromTxtFile.remove(image);
                }
            }

            manageTxtFiles.writeListToTxtFile(imagesFromTxtFile, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean checkJpgJpegExtensions(File file) {
        return file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg");
    }
}
