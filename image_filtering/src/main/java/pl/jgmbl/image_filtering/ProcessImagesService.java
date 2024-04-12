package pl.jgmbl.image_filtering;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    public void sharpeningFilter(Mat source, Mat dst) {
        int[][] kernelArray = {
                {0, -1, 0},
                {-1, 4, -1},
                {0, -1, 0}
        };

        Mat kernel = new Mat(3, 3, CvType.CV_32F);
        final float amountOfSharpening = 0.5F;

        for (int i = 0; i < kernelArray.length; i++) {
            for (int j = 0; j < kernelArray[i].length; j++) {
                kernel.put(i, j, kernelArray[i][j] * amountOfSharpening);
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


    public HashMap<String, String> returnFilteredAndNotFilteredPathToImage(String importTxtFile, String exportFolderPath, String prefix) throws IOException {
        List<String> filteredImagesList = listOfFilteredImages(exportFolderPath, prefix);
        Set<String> unfilteredImagesList = manageTxtFiles.readTxtFile(importTxtFile);
        HashMap<String, String> sampleImages = new HashMap<>();

        String sampleUnfilteredPhoto = "";

        Iterator<String> iterator = unfilteredImagesList.iterator();
        if (iterator.hasNext()) {
            sampleUnfilteredPhoto = iterator.next();
            sampleImages.put("unfiltered", sampleUnfilteredPhoto);
        }

        String nameOfFile = sampleUnfilteredPhoto.substring(sampleUnfilteredPhoto.lastIndexOf("/") + 1);
        nameOfFile = prefix + "_" + nameOfFile;

        for (String imagePath : filteredImagesList) {
            if (imagePath.contains(nameOfFile)) {
                sampleImages.put("filtered", exportFolderPath + imagePath);
            }
        }

        return sampleImages;
    }
}
