package pl.jgmbl.image_filtering;

import java.io.File;

public class ManageImages {
    public String returnFirstFilteredImage(String folderPath) {
        String pathToFile = "";
        File directory = new File(folderPath);
        File[] files = directory.listFiles();
        String absolutePath = directory.getAbsolutePath();

        if (files != null) {
            for (File file : files) {
                String image = file.getName();
                if (ManageTxtFiles.checkJpgJpegExtensions(image)) {
                    pathToFile = absolutePath + '/' + image;
                    break;
                }
            }
        }
        return pathToFile;
    }
}
