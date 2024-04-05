package pl.jgmbl.image_filtering;

import javafx.scene.control.Alert;

public class AddAlert {
    public static void addInfoAlert (String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
