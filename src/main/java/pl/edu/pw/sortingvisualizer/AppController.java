package pl.edu.pw.sortingvisualizer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import pl.edu.pw.sortingvisualizer.sorters.SortingAlgorithm;

public class AppController {

    private final static SortingAlgorithm DEFAULT_SORTING_ALGORITHM = SortingAlgorithm.BubbleSort;
    @FXML
    private ChoiceBox<SortingAlgorithm> sortChoiceBox;
    @FXML
    private Slider delaySlider;
    @FXML
    private Slider sizeSlider;
    @FXML
    private Button generateButton;
    @FXML
    private Button sortButton;
    @FXML
    private HBox drawPanel;

    @FXML
    public void initialize() {
        sortChoiceBox.getItems().addAll(SortingAlgorithm.values());
        sortChoiceBox.setValue(DEFAULT_SORTING_ALGORITHM);

        drawPanel.getChildren().add(new Text("<no array generated>"));
    }
}