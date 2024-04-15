package pl.jgmbl.image_filtering;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputValidationService {
    public static boolean checkIfExportPathIsCorrect(String folderPath) {
        String correctPath = returnCorrectPath(folderPath);
        Path path = Paths.get(correctPath);
        return !correctPath.isBlank() && !correctPath.isEmpty() && Files.isDirectory(path) && Files.exists(path);
    }

    public static boolean checkIfParameterIsCorrect(String parameter) {
        return !parameter.isBlank() && !parameter.isEmpty() && checkIfParameterIsNumber(parameter)
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

    public static String returnCorrectPath(String folderPath) {

        if (folderPath != null && !folderPath.isEmpty()) {
            if (!folderPath.endsWith("/")) {
                return folderPath + "/";
            } else {
                return folderPath;
            }
        } else {
            return "";
        }
    }
}
