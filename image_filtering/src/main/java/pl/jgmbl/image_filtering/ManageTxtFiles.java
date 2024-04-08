package pl.jgmbl.image_filtering;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public class ManageTxtFiles {
    public ManageTxtFiles() {
    }

    public void writeListToTxtFile(List<String> list) throws IOException {
        Path path = Paths.get("src/main/resources/images.txt");

        createTxtFileIfItDoesNotExist(path);

        //appending first line of data in separate line
        ArrayList<String> listOfImages = new ArrayList<>();
        listOfImages.add(System.lineSeparator());
        listOfImages.addAll(list);

        Files.write(path, listOfImages, StandardOpenOption.APPEND);
    }

    public List<String> readTxtFile (String filePath) throws IOException {
        ArrayList<String> listOfFiles = new ArrayList<>();

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

}
