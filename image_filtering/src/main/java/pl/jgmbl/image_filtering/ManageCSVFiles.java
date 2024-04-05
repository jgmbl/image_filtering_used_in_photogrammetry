package pl.jgmbl.image_filtering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class ManageCSVFiles {
    private static final String NEW_LINE_SEPARATOR = "\n";

    public static void writeCSVFile(List<List<String>> listOfPathAndFiles, String filePath) {
//        for (List<String> pathAndFile : listOfPathAndFiles) {
//
//        }

        if (checkIfCSVFileExists(filePath)) {
            try {
                FileReader input = new FileReader("src/main/resources/images.csv");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static boolean checkIfCSVFileExists(String filePath) {
        File file = new File(filePath);

        return file.exists() && file.isFile();
    }
}
