package pl.jgmbl.image_filtering;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InputValidationServiceTest {
    String TXT_IMAGE_TEST_PATH = "src/test/resources/test_images.txt";

    private InputValidationService inputValidationService;

    @BeforeEach
    void setUp() throws IOException {
        inputValidationService = new InputValidationService();

        Set<String> setOfImagesPaths = setOfImagesAllPaths();

        Path path = Paths.get(TXT_IMAGE_TEST_PATH);
        if (!Files.exists(path)) {
            Files.createFile(path);

            if (Files.size(path) == 0) {
                Files.write(path, setOfImagesPaths, StandardOpenOption.TRUNCATE_EXISTING);
            }
        }
    }

    @AfterEach
    void tearDown() {
        File file = new File(TXT_IMAGE_TEST_PATH);

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void checkIfExportPathIsCorrect() {
        // src/test/resources
        boolean pathIsCorrect = InputValidationService.checkIfExportPathIsCorrect(TXT_IMAGE_TEST_PATH.substring(0, TXT_IMAGE_TEST_PATH.lastIndexOf("/")));
        // src/test/resources/
        boolean pathIsCorrect1 = InputValidationService.checkIfExportPathIsCorrect(TXT_IMAGE_TEST_PATH.substring(0, TXT_IMAGE_TEST_PATH.lastIndexOf("/") + 1));
        boolean pathIsIncorrect = InputValidationService.checkIfExportPathIsCorrect(TXT_IMAGE_TEST_PATH.substring(0, 2));
        boolean pathIsIncorrect1 = InputValidationService.checkIfExportPathIsCorrect("");
        boolean pathIsIncorrect2 = InputValidationService.checkIfExportPathIsCorrect(TXT_IMAGE_TEST_PATH + "/hellohaveagreatday");


        Assertions.assertTrue(pathIsCorrect);
        Assertions.assertTrue(pathIsCorrect1);
        Assertions.assertFalse(pathIsIncorrect);
        Assertions.assertFalse(pathIsIncorrect1);
        Assertions.assertFalse(pathIsIncorrect2);
    }

    @Test
    void checkIfParameterIsCorrect() {
        boolean correct = InputValidationService.checkIfParameterIsCorrect("15");
        boolean correct1 = InputValidationService.checkIfParameterIsCorrect("9");
        boolean correct2 = InputValidationService.checkIfParameterIsCorrect("5");
        boolean correct3 = InputValidationService.checkIfParameterIsCorrect("3");
        boolean incorrect = InputValidationService.checkIfParameterIsCorrect("");
        boolean incorrect1 = InputValidationService.checkIfParameterIsCorrect("Hello");
        boolean incorrect2 = InputValidationService.checkIfParameterIsCorrect("4");

        Assertions.assertTrue(correct);
        Assertions.assertTrue(correct1);
        Assertions.assertTrue(correct2);
        Assertions.assertTrue(correct3);
        Assertions.assertFalse(incorrect);
        Assertions.assertFalse(incorrect1);
        Assertions.assertFalse(incorrect2);
    }

    @Test
    void checkIfImagesAreImported() {
        boolean correct = InputValidationService.checkIfImagesAreImported(TXT_IMAGE_TEST_PATH);
        boolean incorrect = InputValidationService.checkIfImagesAreImported(TXT_IMAGE_TEST_PATH.substring(0, TXT_IMAGE_TEST_PATH.lastIndexOf("/") + 1) + "hello.txt");
        boolean incorrect2 = InputValidationService.checkIfImagesAreImported(TXT_IMAGE_TEST_PATH.substring(0, 2));

        tearDown();
        boolean incorrect3 = InputValidationService.checkIfImagesAreImported(TXT_IMAGE_TEST_PATH);
        boolean incorrect4 = InputValidationService.checkIfImagesAreImported("hello/");

        Assertions.assertTrue(correct);
        Assertions.assertFalse(incorrect);
        Assertions.assertFalse(incorrect2);
        Assertions.assertFalse(incorrect3);
        Assertions.assertFalse(incorrect4);

    }

    @Test
    void returnCorrectPath() {
    }

    private static Set<String> setOfImagesAllPaths() {
        HashSet<String> setOfImages = new HashSet<>();
        setOfImages.add("src/test/resources/4.2.06.tiff");
        setOfImages.add("src/test/resources/4.1.05.jpg");
        setOfImages.add("src/test/resources/4.2.05.tiff");
        setOfImages.add("src/test/resources/4.1.07.jpg");

        return setOfImages;
    }
}