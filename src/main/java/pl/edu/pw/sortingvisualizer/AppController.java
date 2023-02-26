package pl.edu.pw.sortingvisualizer;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import pl.edu.pw.sortingvisualizer.sorters.SortingAlgorithm;

public class AppController {

    private final static SortingAlgorithm DEFAULT_SORTING_ALGORITHM = SortingAlgorithm.BubbleSort;
    @FXML
    private ChoiceBox<SortingAlgorithm> sortChoiceBox;

    @FXML
    public void initialize() {
        sortChoiceBox.getItems().addAll(SortingAlgorithm.values());
        sortChoiceBox.setValue(DEFAULT_SORTING_ALGORITHM);
    }
}