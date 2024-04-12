module pl.jgmbl.image_filtering {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;
    requires java.desktop;


    opens pl.jgmbl.image_filtering to javafx.fxml;
    exports pl.jgmbl.image_filtering;
}