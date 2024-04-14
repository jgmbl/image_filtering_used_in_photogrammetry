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
import java.util.List;
import java.util.Set;

class ManageTxtFilesTest {

    String TXT_IMAGE_TEST_PATH = "src/test/resources/test_images.txt";

    private ManageTxtFiles manageTxtFiles;

    @BeforeEach
    void setUp() throws IOException {
        manageTxtFiles = new ManageTxtFiles();

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
    void writeListToTxtFile() throws IOException {
        Set<String> allImagesSet = setOfImagesAllPaths();
        Set<String> allImagesSet1 = setOfImagesAllPaths1();
        Set<String> emptySet = new HashSet<>();

        manageTxtFiles.writeListToTxtFile(emptySet, TXT_IMAGE_TEST_PATH, true);
        Set<String> dataFromFile = returnAllDataFromFile(TXT_IMAGE_TEST_PATH);
        Assertions.assertEquals(allImagesSet, dataFromFile);

        manageTxtFiles.writeListToTxtFile(allImagesSet1, TXT_IMAGE_TEST_PATH, false);
        dataFromFile = returnAllDataFromFile(TXT_IMAGE_TEST_PATH);
        Assertions.assertEquals(allImagesSet1, dataFromFile);
    }

    @Test
    void readTxtFile() {
    }

    @Test
    void createTxtFileIfItDoesNotExist() {
    }

    @Test
    void checkIfPathExists() {
    }

    @Test
    void deletedImagesByFolderPath() {
    }

    @Test
    void deleteImagesByImagesPaths() {
    }

    @Test
    void checkJpgJpegExtensions() {
    }

    private static Set<String> setOfImagesAllPaths() {
        HashSet<String> setOfImages = new HashSet<>();
        setOfImages.add("src/test/resources/4.2.06.tiff");
        setOfImages.add("src/test/resources/4.1.05.jpg");
        setOfImages.add("src/test/resources/4.2.05.tiff");
        setOfImages.add("src/test/resources/4.1.07.jpg");

        return setOfImages;
    }

    private static Set<String> setOfImagesAllPaths1() {
        HashSet<String> setOfImages = new HashSet<>();
        setOfImages.add("src/test/resources/hello.tiff");
        setOfImages.add("src/test/resources/gutenmorgen.jpg");
        setOfImages.add("src/test/resources/halo.tiff");
        setOfImages.add("src/test/resources/czesc.jpg");

        return setOfImages;
    }

    private static Set<String> returnAllDataFromFile (String filePath) throws IOException {
        Set<String> allImages = new HashSet<>();

        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        allImages.addAll(lines);

        return  allImages;
    }
}