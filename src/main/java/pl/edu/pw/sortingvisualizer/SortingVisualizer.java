package pl.edu.pw.sortingvisualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SortingVisualizer extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SortingVisualizer.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("SortingVisualizer");
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(SortingVisualizer.class.getResourceAsStream("icon.png"))));
        stage.setScene(scene);
        stage.show();
    }
}