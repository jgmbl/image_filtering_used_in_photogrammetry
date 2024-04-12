package pl.jgmbl.image_filtering;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProcessImagesService {
    static {
        nu.pattern.OpenCV.loadLocally();
    }

    private final ManageTxtFiles manageTxtFiles = new ManageTxtFiles();

    /**
     * Absolute paths to images are stored in txt file
     */
    public void filtering(String type, String txtFilePath, String outputFolderPath, Integer kernelSize) throws IOException {
        Set<String> setOfImagesPaths = manageTxtFiles.readTxtFile(txtFilePath);


        for (String imagePath : setOfImagesPaths) {
            Mat src = Imgcodecs.imread(imagePath);
            Mat destinationMatrix = new Mat(src.rows(), src.cols(), src.type());

            if (type.equals("gaussian")) {
                gaussianFiltering(src, destinationMatrix, (double) kernelSize);
                saveFilteredImage("gaussian", imagePath, outputFolderPath, destinationMatrix);
            } else if (type.equals("median")) {
                medianFiltering(src, destinationMatrix, kernelSize);
                saveFilteredImage("median", imagePath, outputFolderPath, destinationMatrix);
            }
        }
    }

    public void filtering(String type, String txtFilePath, String outputFolderPath) throws IOException {
        Set<String> setOfImagesPaths = manageTxtFiles.readTxtFile(txtFilePath);


        for (String imagePath : setOfImagesPaths) {
            Mat src = Imgcodecs.imread(imagePath);
            Mat destinationMatrix = new Mat(src.rows(), src.cols(), src.type());

            if (type.equals("sharpening")) {
                sharpeningFilter(src, destinationMatrix);
                saveFilteredImage("sharpening", imagePath, outputFolderPath, destinationMatrix);
            }
        }
    }


    private void gaussianFiltering(Mat source, Mat dst, Double kernelSize) {
        Imgproc.GaussianBlur(source, dst, new Size(kernelSize, kernelSize), 0);
    }

    private void medianFiltering(Mat source, Mat dst, int kernelSize) {
        Imgproc.medianBlur(source, dst, kernelSize);
    }

    public void sharpeningFilter (Mat source, Mat dst) {
        int[][] kernelArray = {
                {0, -1, 0},
                {-1, 4, -1},
                {0, -1, 0}
        };

        Mat kernel = new Mat(3, 3, CvType.CV_32F);

        for (int i = 0; i < kernelArray.length; i++) {
            for (int j = 0; j < kernelArray[i].length; j++) {
                kernel.put(i, j, kernelArray[i][j]);
            }
        }

        Mat filteredImage = new Mat();
        Imgproc.filter2D(source, filteredImage, -1, kernel);

        Core.addWeighted(source, 1.0, filteredImage, 1.0, 0.0, dst);
    }


    private void saveFilteredImage(String type, String imagePath, String outputFolderPath, Mat destinationMatrix) {
        String imageName = imagePath.substring(imagePath.lastIndexOf('/') + 1);
        boolean imrite = Imgcodecs.imwrite(outputFolderPath + type + "_" + imageName, destinationMatrix);
    }

    public List<String> listOfFilteredImages(String folderPath, String prefix) {
        ArrayList<String> listOfImages = new ArrayList<>();

        File file = new File(folderPath);
        File[] files = file.listFiles();

        if (files != null) {
            for (File file1 : files) {
                if (file1.isFile()) {
                    if (file1.getName().contains(prefix)) {
                        listOfImages.add(file1.getName());
                    }
                }
            }
        }

        return listOfImages;
    }

    public static String returnFirstFilteredImage(String folderPath, String prefix) {
        String pathToFile = "";
        File directory = new File(folderPath);
        File[] files = directory.listFiles();
        String absolutePath = directory.getAbsolutePath();

        if (files != null) {
            for (File file : files) {
                String image = file.getName();
                if (ManageTxtFiles.checkJpgJpegExtensions(image) && image.contains(prefix)) {
                    pathToFile = absolutePath + '/' + image;
                    break;
                }
            }
        }
        return pathToFile;
    }
}
