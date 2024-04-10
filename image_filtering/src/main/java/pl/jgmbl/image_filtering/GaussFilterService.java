package pl.jgmbl.image_filtering;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GaussFilterService {
    protected static boolean checkIfExportPathIsCorrect(String folderPath) {
        Path path = Paths.get(folderPath);
        return !folderPath.isBlank() && Files.isDirectory(path) && Files.exists(path);
    }

    protected static boolean checkIfParameterIsCorrect (String parameter) {
        return !parameter.isBlank() && checkIfParameterIsFloatNumber(parameter);
    }


    private static boolean checkIfParameterIsFloatNumber(String parameter) {
        for (char character : parameter.toCharArray()) {
            if (!Character.isDigit(character) && character != '.' && character != ',') {
                return false;
            }
        }
        return true;
    }
}
