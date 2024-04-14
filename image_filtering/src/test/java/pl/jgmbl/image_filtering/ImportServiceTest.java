package pl.jgmbl.image_filtering;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ImportServiceTest {
    String TXT_IMAGE_TEST_PATH = "src/test/resources/test_images.txt";

    private ImportService importService;

    @BeforeEach
    void setUp() throws IOException {
        importService = new ImportService();

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
    void listOfJPGFilesFromFolder() throws IOException {
        Set<String> listOfJPGImagesFolder = importService.listOfJPGFilesFromFolder("src/test/resources/");
        Set<Object> listOfJPGImagesFile = new HashSet<>();
        System.out.println(listOfJPGImagesFile);

        Path path = Paths.get(TXT_IMAGE_TEST_PATH);
        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {
            if (!line.isEmpty() && line.toLowerCase().endsWith("jpg") || line.toLowerCase().endsWith("jpeg")) {
                listOfJPGImagesFile.add(line);
            }
        }

        Assertions.assertEquals(listOfJPGImagesFolder, listOfJPGImagesFile);
    }

    @Test
    void refreshListView() {
        ObservableList<String> observableListJpgTest = importService.refreshedObservableListFromTxtFile(TXT_IMAGE_TEST_PATH);
        Set<String> allImages = setOfImagesAllPaths();
        ObservableList<String> allImagesObservable = FXCollections.observableArrayList(allImages);

        Assertions.assertEquals(observableListJpgTest.size(), allImagesObservable.size());
        Assertions.assertEquals(observableListJpgTest, allImagesObservable);
    }

    @Test
    void isFolderImported() {
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