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
        return !parameter.isBlank() && checkIfParameterIsNumber(parameter)
                && isParameterCorrectNumber(parameter);
    }

    public static boolean checkIfImagesAreImported(String importTxtFilePath) {
        Path path = Paths.get(importTxtFilePath);

        try {
            return Files.exists(path) && !Files.isDirectory(path) && Files.size(path) != 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static boolean checkIfParameterIsNumber(String parameter) {
        for (char character : parameter.toCharArray()) {
            if (!Character.isDigit(character)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isParameterCorrectNumber(String parameter) {
        double parameterValue = Double.parseDouble(parameter);

        if (parameterValue == 3 || parameterValue == 5 || parameterValue == 9 || parameterValue == 15) {
            return true;
        } else {
            return false;
        }
    }
}
