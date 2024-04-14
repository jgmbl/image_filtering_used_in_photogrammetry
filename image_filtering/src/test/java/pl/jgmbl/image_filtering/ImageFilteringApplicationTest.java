package pl.jgmbl.image_filtering;

import org.junit.After;
import org.junit.Before;
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

class ImageFilteringApplicationTest {
    private ImageFilteringApplication imageFilteringApplication;

    @BeforeEach
    public void setUp() throws IOException {
        imageFilteringApplication = new ImageFilteringApplication();
        HashSet<String> setOfImagesPaths = new HashSet<>();
        setOfImagesPaths.add("src/test/resources/1.1.12.tiff");
        setOfImagesPaths.add("src/test/resources/4.1.05.jpg");
        setOfImagesPaths.add("src/test/resources/4.2.05.tiff");
        setOfImagesPaths.add("src/test/resources/5.1.13.jpg");

        Path path = Paths.get("src/test/resources/test_images.txt");
        if (!Files.exists(path)) {
            Files.createFile(path);

            if (Files.size(path) == 0) {
                Files.write(path, setOfImagesPaths, StandardOpenOption.TRUNCATE_EXISTING);
            }
        }
    }

    @After
    public void tearDown() {
        File file = new File("src/test/resources/test_images.txt");

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void start() {
    }

    @Test
    void stop() throws Exception {
        File file = new File("src/main/resources/images.txt");

        imageFilteringApplication.stop();
        Assertions.assertFalse(file.exists());
    }

    @Test
    void main() {
    }
}