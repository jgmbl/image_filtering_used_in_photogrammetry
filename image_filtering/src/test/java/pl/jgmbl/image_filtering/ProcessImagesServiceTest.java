package pl.jgmbl.image_filtering;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

import static java.lang.System.*;

class ProcessImagesServiceTest {
    String TXT_IMAGE_TEST_PATH = "src/test/resources/test_images.txt";
    String GAUSSIAN_EXPORT_FOLDER_PATH = "src/test/resources/gaussian_filtering/";

    private ProcessImagesService processImagesService;

    @BeforeEach
    void setUp() throws IOException {
        processImagesService = new ProcessImagesService();

        Set<String> setOfImagesPaths = setOfImagesAllPaths();

        Path path = Paths.get(TXT_IMAGE_TEST_PATH);
        if (!Files.exists(path)) {
            Files.createFile(path);

            if (Files.size(path) == 0) {
                Files.write(path, setOfImagesPaths, StandardOpenOption.TRUNCATE_EXISTING);
            }
        }

        nu.pattern.OpenCV.loadLocally();
    }

    @AfterEach
    void tearDown() {
        File file = new File(TXT_IMAGE_TEST_PATH);

        if (file.exists()) {
            file.delete();
        }

        deleteFilteredFiles(GAUSSIAN_EXPORT_FOLDER_PATH);
    }

    @Test
    void filtering() throws IOException {
        processImagesService.filtering("gaussian", TXT_IMAGE_TEST_PATH, GAUSSIAN_EXPORT_FOLDER_PATH, 5);

        int filesLength = 0;
        File file = new File(GAUSSIAN_EXPORT_FOLDER_PATH);
        File[] files = file.listFiles();

        if (files != null) {
            filesLength = files.length;
        }

        // blurred image at the beginning
        String nameOfBlurredFile = files[0].getName();
        File blurredImageFile = new File(GAUSSIAN_EXPORT_FOLDER_PATH + nameOfBlurredFile);
        BufferedImage blurredImage = ImageIO.read(blurredImageFile);

        // original image
        String pathToOrigFiles = TXT_IMAGE_TEST_PATH.substring(0, TXT_IMAGE_TEST_PATH.lastIndexOf("/") + 1);
        String nameOfOrigFile = nameOfBlurredFile.substring(0, nameOfBlurredFile.lastIndexOf("/") + 1) + nameOfBlurredFile.substring(nameOfBlurredFile.lastIndexOf("_") + 1);
        File origImageFile = new File(pathToOrigFiles + nameOfOrigFile);
        BufferedImage origImage = ImageIO.read(origImageFile);

        // independent gaussian blur of original image
        Mat src = Imgcodecs.imread(pathToOrigFiles + nameOfOrigFile);
        Mat destinationMatrix = new Mat(src.rows(), src.cols(), src.type());
        Imgproc.GaussianBlur(src, destinationMatrix, new Size(5, 5), 0);
        Imgcodecs.imwrite(GAUSSIAN_EXPORT_FOLDER_PATH + "test_gaussian_" + nameOfBlurredFile.substring(nameOfBlurredFile.lastIndexOf("_") + 1), destinationMatrix);

        File blurredOrigFile = new File(GAUSSIAN_EXPORT_FOLDER_PATH + "test_gaussian" + nameOfBlurredFile.substring(nameOfBlurredFile.lastIndexOf("_")));
        BufferedImage blurredOrigImage = ImageIO.read(blurredOrigFile);

        // comparison of pixels on blur at the beginning and gaussian blur of original image
        for (int y = 0; y < blurredImage.getHeight(); y++) {
           for (int x = 0; x < blurredImage.getWidth(); x++) {
               int pixelBlurredImage = blurredImage.getRGB(x, y);
               int pixelBlurredOrigImage = blurredOrigImage.getRGB(x, y);
               Assertions.assertEquals(pixelBlurredImage, pixelBlurredOrigImage);
           }
        }

        Assertions.assertEquals(filesLength, 2);
        Assertions.assertEquals(origImage.getWidth(), blurredImage.getWidth());
        Assertions.assertEquals(origImage.getHeight(), blurredImage.getHeight());


    }

    @Test
    void testFiltering() {
    }

    @Test
    void sharpeningFilter() {
    }

    @Test
    void listOfFilteredImages() {
    }

    @Test
    void returnFilteredAndNotFilteredPathToImage() {
    }

    private static Set<String> setOfImagesAllPaths() {
        HashSet<String> setOfImages = new HashSet<>();
        setOfImages.add("src/test/resources/4.2.06.tiff");
        setOfImages.add("src/test/resources/4.1.05.jpg");
        setOfImages.add("src/test/resources/4.2.05.tiff");
        setOfImages.add("src/test/resources/4.1.07.jpg");

        return setOfImages;
    }

    private static void deleteFilteredFiles(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }
}