package pl.jgmbl.image_filtering;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputValidationService {
    public static boolean checkIfExportPathIsCorrect(String folderPath) {
        Path path = Paths.get(folderPath);
        return !folderPath.isBlank() && Files.isDirectory(path) && Files.exists(path);
    }

    public static boolean checkIfParameterIsCorrect (String parameter) {
        return !parameter.isBlank() && checkIfParameterIsFloatNumber(parameter);
    }

    public static boolean checkIfImagesAreImported(String importTxtFilePath) {
        Path path = Paths.get(importTxtFilePath);

        try {
            return Files.exists(path) && !Files.isDirectory(path) && Files.size(path) != 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
