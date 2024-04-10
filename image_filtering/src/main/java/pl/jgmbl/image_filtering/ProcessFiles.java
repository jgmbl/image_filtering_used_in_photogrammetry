package pl.jgmbl.image_filtering;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.Set;

public class ProcessFiles {
    static {
        nu.pattern.OpenCV.loadLocally();
    }

    private final ManageTxtFiles manageTxtFiles = new ManageTxtFiles();

    /** Absolute paths to images are stored in txt file */
    public void gaussianFiltering (String txtFilePath, String outputFolderPath, float blurParameter) throws IOException {
        Set<String> setOfImagesPaths = manageTxtFiles.readTxtFile(txtFilePath);

        double kernelSize = (double) blurParameter;

        for (String imagePath : setOfImagesPaths) {
            Mat src = Imgcodecs.imread(imagePath);
            Mat destinationMatrix = new Mat(src.rows(), src.cols(), src.type());

            Imgproc.GaussianBlur(src, destinationMatrix, new Size(kernelSize, kernelSize), 0);

            String imageName = imagePath.substring(imagePath.lastIndexOf('/') + 1);
            boolean imrite = Imgcodecs.imwrite(outputFolderPath + "gaussian_" + imageName, destinationMatrix);
        }
    }
}
