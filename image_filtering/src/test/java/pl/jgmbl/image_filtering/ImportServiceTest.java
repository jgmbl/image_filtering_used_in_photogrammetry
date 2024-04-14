package pl.jgmbl.image_filtering;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ImportServiceTest {

    private ImportService importService;

    @BeforeEach
    void setUp() throws IOException {
        importService = new ImportService();
        HashSet<String> setOfImagesPaths = new HashSet<>();
        setOfImagesPaths.add("src/test/resources/4.2.06.tiff");
        setOfImagesPaths.add("src/test/resources/4.1.05.jpg");
        setOfImagesPaths.add("src/test/resources/4.2.05.tiff");
        setOfImagesPaths.add("src/test/resources/4.1.07.jpg");

        Path path = Paths.get("src/test/resources/test_images.txt");
        if (!Files.exists(path)) {
            Files.createFile(path);

            if (Files.size(path) == 0) {
                Files.write(path, setOfImagesPaths, StandardOpenOption.TRUNCATE_EXISTING);
            }
        }
    }

    @AfterEach
    void tearDown() {
        File file = new File("src/test/resources/test_images.txt");

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void listOfJPGFilesFromFolder() {
    }

    @Test
    void refreshListView() {
    }

    @Test
    void isFolderImported() {
    }
}