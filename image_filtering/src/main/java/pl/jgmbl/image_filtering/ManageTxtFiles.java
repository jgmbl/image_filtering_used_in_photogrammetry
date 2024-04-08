package pl.jgmbl.image_filtering;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class ManageTxtFiles {
    public ManageTxtFiles() {
    }

    public void writeListToTxtFile(List<String> list) throws IOException {
        Path path = Paths.get("src/main/resources/images.txt");

        Files.write(path, list, StandardOpenOption.APPEND);
    }
}
