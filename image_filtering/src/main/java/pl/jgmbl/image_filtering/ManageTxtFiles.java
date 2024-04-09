package pl.jgmbl.image_filtering;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;


public class ManageTxtFiles {
    public ManageTxtFiles() {
    }

    public void writeListToTxtFile(HashSet<String> set, String pathToFile, boolean append) throws IOException {
        Path path = Paths.get(pathToFile);

        createTxtFileIfItDoesNotExist(path);

        //appending first line of data in separate line
        HashSet<String> listOfImages = new HashSet<>();
        listOfImages.add(System.lineSeparator());
        listOfImages.addAll(set);

        if (append) {
            Files.write(path, listOfImages, StandardOpenOption.APPEND);
        } else {
            Files.write(path, listOfImages, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    public HashSet<String> readTxtFile (String filePath) throws IOException {
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

    public void deleteImagesByFolderPath(String deletePath, String txtFilePath) throws IOException {
        HashSet<String> imagesFromTxtFile = readTxtFile(txtFilePath);

        File file = new File(deletePath);
        String[] listOfJPGFilesInDeletePath = file.list();

        if (listOfJPGFilesInDeletePath != null) {
            for (String imageName : listOfJPGFilesInDeletePath) {
                if (imageName.toLowerCase().endsWith(".jpg") || imageName.toLowerCase().endsWith(".jpeg")) {
                    imagesFromTxtFile.removeIf(imagePath -> imagePath.startsWith(deletePath + imageName));
                }
            }
        }

        writeListToTxtFile(imagesFromTxtFile, txtFilePath, false);
        System.out.println(imagesFromTxtFile.toString());
    }
}
