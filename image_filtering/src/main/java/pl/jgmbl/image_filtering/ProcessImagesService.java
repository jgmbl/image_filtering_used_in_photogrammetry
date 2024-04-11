package pl.jgmbl.image_filtering;

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

    /** Absolute paths to images are stored in txt file */
    public void gaussianFiltering (String txtFilePath, String outputFolderPath, Double kernelSize) throws IOException {
        Set<String> setOfImagesPaths = manageTxtFiles.readTxtFile(txtFilePath);


        for (String imagePath : setOfImagesPaths) {
            Mat src = Imgcodecs.imread(imagePath);
            Mat destinationMatrix = new Mat(src.rows(), src.cols(), src.type());

            Imgproc.GaussianBlur(src, destinationMatrix, new Size(kernelSize, kernelSize), 0);

            String imageName = imagePath.substring(imagePath.lastIndexOf('/') + 1);
            boolean imrite = Imgcodecs.imwrite(outputFolderPath + "gaussian_" + imageName, destinationMatrix);
        }
    }

    public List<String> listOfFilteredImages(String folderPath) {
        ArrayList<String> listOfImages = new ArrayList<>();

        File file = new File(folderPath);
        File[] files = file.listFiles();
        
        if (files != null) {
            for (File file1 : files) {
                if (file1.isFile()) {
                    listOfImages.add(file1.getName());
                }
            }
        }
        
        return listOfImages;
    }
}
