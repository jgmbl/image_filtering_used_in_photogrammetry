package pl.jgmbl.image_filtering;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportService {

    public List<String> listOfFiles(String path) {
        ArrayList<String> filesList = new ArrayList<>();

        File folder = new File(path);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    filesList.add(file.getName());
                }
            }
        }
        return filesList;
    }

}
