package pl.jgmbl.image_filtering;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.*;
import java.util.stream.Stream;

class ProcessImagesServiceTest {
    static String TXT_IMAGE_TEST_PATH = "src/test/resources/test_images.txt";
    static String GAUSSIAN_EXPORT_FOLDER_PATH = "src/test/resources/gaussian_filtering/";
    static String MEDIAN_EXPORT_FOLDER_PATH = "src/test/resources/median_filtering/";
    static String SHARPENING_EXPORT_FOLDER_PATH = "src/test/resources/sharpening_filtering/";

    private static ProcessImagesService processImagesService;

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

        createPathIfDoesNotExists(GAUSSIAN_EXPORT_FOLDER_PATH);
        createPathIfDoesNotExists(MEDIAN_EXPORT_FOLDER_PATH);
        createPathIfDoesNotExists(SHARPENING_EXPORT_FOLDER_PATH);


        nu.pattern.OpenCV.loadLocally();
    }

    @AfterEach
    void tearDown() {
        deleteDirectory(TXT_IMAGE_TEST_PATH);

        deleteFilteredFiles(GAUSSIAN_EXPORT_FOLDER_PATH);
        deleteFilteredFiles(MEDIAN_EXPORT_FOLDER_PATH);
        deleteFilteredFiles(SHARPENING_EXPORT_FOLDER_PATH);

        deleteDirectory(GAUSSIAN_EXPORT_FOLDER_PATH);
        deleteDirectory(MEDIAN_EXPORT_FOLDER_PATH);
        deleteDirectory(SHARPENING_EXPORT_FOLDER_PATH);
    }

    @Test
    void filtering() throws IOException {
        testBlur(GAUSSIAN_EXPORT_FOLDER_PATH, "gaussian");
        testBlur(MEDIAN_EXPORT_FOLDER_PATH, "median");
    }

    @Test
    void testFiltering() throws IOException {
        processImagesService.filtering("sharpening", TXT_IMAGE_TEST_PATH, SHARPENING_EXPORT_FOLDER_PATH);

        int filesLength = 0;
        File file = new File(SHARPENING_EXPORT_FOLDER_PATH + "/");
        File[] files = file.listFiles();

        if (files != null) {
            filesLength = files.length;
        }

        // sharpened image at the beginning
        String nameOfSharpenedFile = files[0].getName();
        File sharpenedImageFile = new File(SHARPENING_EXPORT_FOLDER_PATH + nameOfSharpenedFile);
        BufferedImage sharpenedImage = ImageIO.read(sharpenedImageFile);

        // original image
        String pathToOrigFiles = TXT_IMAGE_TEST_PATH.substring(0, TXT_IMAGE_TEST_PATH.lastIndexOf("/") + 1);
        String nameOfOrigFile = nameOfSharpenedFile.substring(0, nameOfSharpenedFile.lastIndexOf("/") + 1) + nameOfSharpenedFile.substring(nameOfSharpenedFile.lastIndexOf("_") + 1);
        File origImageFile = new File(pathToOrigFiles + nameOfOrigFile);
        BufferedImage origImage = ImageIO.read(origImageFile);

        // independent sharpening of original image
        Mat src = Imgcodecs.imread(pathToOrigFiles + nameOfOrigFile);
        Mat destinationMatrix = new Mat(src.rows(), src.cols(), src.type());

        processImagesService.sharpeningFilter(src, destinationMatrix);

        Imgcodecs.imwrite(SHARPENING_EXPORT_FOLDER_PATH + "test_" + "sharpening" + "_" + nameOfSharpenedFile.substring(nameOfSharpenedFile.lastIndexOf("_") + 1), destinationMatrix);

        File sharpenedOrigFile = new File(SHARPENING_EXPORT_FOLDER_PATH + "test_" + "sharpening" + nameOfSharpenedFile.substring(nameOfSharpenedFile.lastIndexOf("_")));
        BufferedImage blurredOrigImage = ImageIO.read(sharpenedOrigFile);

        // comparison of pixels on sharpening at the beginning and sharpening of original image
        for (int y = 0; y < sharpenedImage.getHeight(); y++) {
            for (int x = 0; x < sharpenedImage.getWidth(); x++) {
                int pixelSharpenedImage = sharpenedImage.getRGB(x, y);
                int pixelSharpenedOrigImage = blurredOrigImage.getRGB(x, y);
                Assertions.assertEquals(pixelSharpenedImage, pixelSharpenedOrigImage);
            }
        }

        Assertions.assertEquals(filesLength, 2);
        Assertions.assertEquals(origImage.getWidth(), sharpenedImage.getWidth());
        Assertions.assertEquals(origImage.getHeight(), sharpenedImage.getHeight());
    }

    @Test
    void listOfFilteredImages() throws IOException {
        copyImages();

        List<String> gaussianFiles = processImagesService.listOfFilteredImages(GAUSSIAN_EXPORT_FOLDER_PATH, "gaussian");
        List<String> gaussianList = returnFilesInDirectory(GAUSSIAN_EXPORT_FOLDER_PATH);

        List<String> medianFiles = processImagesService.listOfFilteredImages(MEDIAN_EXPORT_FOLDER_PATH, "median");
        List<String> medianList = returnFilesInDirectory(MEDIAN_EXPORT_FOLDER_PATH);

        List<String> sharpeningFiles = processImagesService.listOfFilteredImages(SHARPENING_EXPORT_FOLDER_PATH, "sharpening");
        List<String> sharpeningList = returnFilesInDirectory(SHARPENING_EXPORT_FOLDER_PATH);

        Assertions.assertEquals(gaussianFiles, gaussianList);
        Assertions.assertEquals(medianFiles, medianList);
        Assertions.assertEquals(sharpeningFiles, sharpeningList);

    }

    @Test
    void returnFilteredAndNotFilteredPathToImage() throws IOException {
        copyImages();

        HashMap<String, String> gaussianHashMap = processImagesService.returnFilteredAndNotFilteredPathToImage(TXT_IMAGE_TEST_PATH, GAUSSIAN_EXPORT_FOLDER_PATH, "gaussian");
        HashMap<String, String> medianHashMap = processImagesService.returnFilteredAndNotFilteredPathToImage(TXT_IMAGE_TEST_PATH, MEDIAN_EXPORT_FOLDER_PATH, "median");
        HashMap<String, String> sharpeningHashMap = processImagesService.returnFilteredAndNotFilteredPathToImage(TXT_IMAGE_TEST_PATH, SHARPENING_EXPORT_FOLDER_PATH, "sharpening");

        String gaussianImage = returnFirstJpgImageInFolder(GAUSSIAN_EXPORT_FOLDER_PATH);
        String medianImage = returnFirstJpgImageInFolder(MEDIAN_EXPORT_FOLDER_PATH);
        String sharpeningImage = returnFirstJpgImageInFolder(SHARPENING_EXPORT_FOLDER_PATH);
        String referenceImage = returnFirstJpgImageInFolder("src/test/resources/");

        HashMap<String, String> gaussianNewHashMap = new HashMap<>();
        gaussianNewHashMap.put("filtered", gaussianImage);
        gaussianNewHashMap.put("unfiltered", referenceImage);
        HashMap<String, String> medianNewHashMap = new HashMap<>();
        medianNewHashMap.put("filtered", medianImage);
        medianNewHashMap.put("unfiltered", referenceImage);
        HashMap<String, String> sharpeningNewHashMap = new HashMap<>();
        sharpeningNewHashMap.put("filtered", sharpeningImage);
        sharpeningNewHashMap.put("unfiltered", referenceImage);

        Assertions.assertEquals(gaussianHashMap, gaussianNewHashMap);
        Assertions.assertEquals(medianHashMap, medianNewHashMap);
        Assertions.assertEquals(sharpeningHashMap, sharpeningNewHashMap);
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

    private static void createPathIfDoesNotExists(String folderPath) {
        Path path = Paths.get(folderPath);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void deleteDirectory(String path) {
        Path path1 = Paths.get(path);

        if (Files.exists(path1)) {
            try {
                Files.delete(path1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static List<String> returnFilesInDirectory(String directory) {
        List<String> listOfFiles = new ArrayList<>();

        File file = new File(directory);
        File[] files = file.listFiles();

        if (files != null) {
            for (File file1 : files) {
                listOfFiles.add(file1.getName());
            }
        }

        return listOfFiles;
    }

    private static void copyImages() {
        Path image1 = Paths.get("src/test/resources/4.1.05.jpg");
        Path image2 = Paths.get("src/test/resources/4.1.07.jpg");

        Path image1Gaussian = Paths.get(GAUSSIAN_EXPORT_FOLDER_PATH + "gaussian_4.1.05.jpg");
        Path image2Gaussian = Paths.get(GAUSSIAN_EXPORT_FOLDER_PATH + "gaussian_4.1.07.jpg");
        Path image1Median = Paths.get(MEDIAN_EXPORT_FOLDER_PATH + "median_4.1.05.jpg");
        Path image2Median = Paths.get(MEDIAN_EXPORT_FOLDER_PATH + "median_4.1.07.jpg");
        Path image1Sharpening = Paths.get(SHARPENING_EXPORT_FOLDER_PATH + "sharpening_4.1.05.jpg");
        Path image2Sharpening = Paths.get(SHARPENING_EXPORT_FOLDER_PATH + "sharpening_4.1.07.jpg");

        try {
            Files.copy(image1, image1Gaussian);
            Files.copy(image2, image2Gaussian);
            Files.copy(image1, image1Median);
            Files.copy(image2, image2Median);
            Files.copy(image1, image1Sharpening);
            Files.copy(image2, image2Sharpening);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String returnFirstJpgImageInFolder(String folderPath) throws IOException {
        String jpgImage = "";
        HashSet<String> setOfJpgFiles = new HashSet<>();
        Path path = Paths.get(folderPath);

        Stream<Path> list = Files.list(path);
        list.map(Path::toString).forEach(setOfJpgFiles::add);

        Iterator<String> iterator = setOfJpgFiles.iterator();
        if (iterator.hasNext()) {
            jpgImage = iterator.next();
        }

        return jpgImage;
    }

    private static void testBlur(String filteringFolderPath, String type) throws IOException {
        processImagesService.filtering(type, TXT_IMAGE_TEST_PATH, filteringFolderPath, 5);

        int filesLength = 0;
        File file = new File(filteringFolderPath);
        File[] files = file.listFiles();

        if (files != null) {
            filesLength = files.length;
        }

        // blurred image at the beginning
        String nameOfBlurredFile = files[0].getName();
        File blurredImageFile = new File(filteringFolderPath + nameOfBlurredFile);
        BufferedImage blurredImage = ImageIO.read(blurredImageFile);

        // original image
        String pathToOrigFiles = TXT_IMAGE_TEST_PATH.substring(0, TXT_IMAGE_TEST_PATH.lastIndexOf("/") + 1);
        String nameOfOrigFile = nameOfBlurredFile.substring(0, nameOfBlurredFile.lastIndexOf("/") + 1) + nameOfBlurredFile.substring(nameOfBlurredFile.lastIndexOf("_") + 1);
        File origImageFile = new File(pathToOrigFiles + nameOfOrigFile);
        BufferedImage origImage = ImageIO.read(origImageFile);

        // independent blur of original image
        Mat src = Imgcodecs.imread(pathToOrigFiles + nameOfOrigFile);
        Mat destinationMatrix = new Mat(src.rows(), src.cols(), src.type());
        if (type.equals("gaussian")) {
            Imgproc.GaussianBlur(src, destinationMatrix, new Size(5, 5), 0);
        } else if (type.equals("median")) {
            Imgproc.medianBlur(src, destinationMatrix, 5);
        }
        Imgcodecs.imwrite(filteringFolderPath + "test_" + type + "_" + nameOfBlurredFile.substring(nameOfBlurredFile.lastIndexOf("_") + 1), destinationMatrix);

        File blurredOrigFile = new File(filteringFolderPath + "test_" + type + nameOfBlurredFile.substring(nameOfBlurredFile.lastIndexOf("_")));
        BufferedImage blurredOrigImage = ImageIO.read(blurredOrigFile);

        // comparison of pixels on blur at the beginning and blur of original image
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
}