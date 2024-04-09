package pl.jgmbl.image_filtering;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ManageTxtFiles {
    public ManageTxtFiles() {
    }

    public void writeListToTxtFile(Set<String> set, String pathToFile, boolean append) throws IOException {
        Path path = Paths.get(pathToFile);



        createTxtFileIfItDoesNotExist(path);

        if (append) {
            Files.write(path, set, StandardOpenOption.APPEND);
        } else {
            Files.write(path, set, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    public Set<String> readTxtFile(String filePath) throws IOException {
        HashSet<String> listOfFiles = new HashSet<>();

        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {
            if (!line.isEmpty()) {
                listOfFiles.add(line);
            }
        }

        return listOfFiles;
    }


    public void createTxtFileIfItDoesNotExist(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean checkIfPathExists(String path) {
        Path path1 = Paths.get(path);

        return Files.exists(path1);
    }


    public Set<String> deletedImagesByFolderPath(String deletePath, String txtFilePath) throws IOException {
        Set<String> imagesFromTxtFile = readTxtFile(txtFilePath);

        File file = new File(deletePath);
        String[] listOfJPGFilesInDeletePath = file.list();

        if (listOfJPGFilesInDeletePath != null) {
            Set<String> imagesToRemove = new HashSet<>();

            for (String imageName : listOfJPGFilesInDeletePath) {
                if (checkJpgJpegExtensions(imageName)) {
                    imagesToRemove.add(deletePath + imageName);
                }
            }

            imagesFromTxtFile.removeIf(imagePath -> {
                for (String imageToRemove : imagesToRemove) {
                    if (imagePath.startsWith(imageToRemove)) {
                        return true;
                    }
                }
                return false;
            });
        }

        return imagesFromTxtFile;
    }


    public static boolean checkJpgJpegExtensions(String path) {
        File file = new File(path);
        return file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg");
    }
}
