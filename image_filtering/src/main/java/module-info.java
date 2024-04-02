module pl.jgmbl.image_filtering {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.jgmbl.image_filtering to javafx.fxml;
    exports pl.jgmbl.image_filtering;
}