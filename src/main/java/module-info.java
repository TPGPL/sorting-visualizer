module pl.edu.pw.sortingvisualizer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    opens pl.edu.pw.sortingvisualizer to javafx.fxml;
    exports pl.edu.pw.sortingvisualizer;
    exports pl.edu.pw.sortingvisualizer.utils;
    opens pl.edu.pw.sortingvisualizer.utils to javafx.fxml;
}