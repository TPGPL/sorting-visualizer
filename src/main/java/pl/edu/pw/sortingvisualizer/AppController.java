package pl.edu.pw.sortingvisualizer;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;
import pl.edu.pw.sortingvisualizer.sorters.BubbleSort;
import pl.edu.pw.sortingvisualizer.sorters.HeapSort;
import pl.edu.pw.sortingvisualizer.sorters.InsertionSort;
import pl.edu.pw.sortingvisualizer.sorters.QuickSort;
import pl.edu.pw.sortingvisualizer.sorters.SelectionSort;
import pl.edu.pw.sortingvisualizer.sorters.SortingAlgorithm;
import pl.edu.pw.sortingvisualizer.sorters.VisualizableSorter;

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
    private Canvas drawPanel;
    private double[] sortArray;
    private Rectangle[] drawRectangles;
    private GraphicsContext gc;
    private VisualizableSorter sorter;

    @FXML
    public void initialize() {
        sortChoiceBox.getItems().addAll(SortingAlgorithm.values());
        sortChoiceBox.setValue(DEFAULT_SORTING_ALGORITHM);

        gc = drawPanel.getGraphicsContext2D();
        gc.fillText("<no array generated>", 0, drawPanel.getHeight() - 10);
    }

    @FXML
    public void generateArray() {
        int elemCount = (int) sizeSlider.getValue();

        sortArray = ArrayGenerator.generateArray(elemCount, drawPanel.getHeight());
        drawRectangles = ArrayGenerator.convertDoubleToRectangleArray(sortArray, drawPanel.getWidth());

        drawRectangleArray();
        sortButton.setDisable(false);
    }

    @FXML
    private void drawRectangleArray() {
        gc.clearRect(0, 0, drawPanel.getWidth(), drawPanel.getHeight());

        double elemWidth = drawPanel.getWidth() / drawRectangles.length;

        for (int i = 0; i < drawRectangles.length; i++) {
            gc.setFill(drawRectangles[i].getFill());
            gc.fillRect(i * elemWidth, drawPanel.getHeight() - drawRectangles[i].getHeight(), elemWidth, drawRectangles[i].getHeight());
        }

    }

    @FXML
    private void redrawRectangles(int... indices) {
        double elemWidth = drawPanel.getWidth() / drawRectangles.length;

        for (int i : indices) {
            gc.clearRect(i * elemWidth, 0, elemWidth, drawPanel.getHeight());
            gc.setFill(drawRectangles[i].getFill());
            gc.fillRect(i * elemWidth, drawPanel.getHeight() - drawRectangles[i].getHeight(), elemWidth, drawRectangles[i].getHeight());
        }
    }

    @FXML
    private void enableUI() {
        // sortButton is not enabled, because a new array must be generated after sorting ends
        delaySlider.setDisable(false);
        sizeSlider.setDisable(false);
        generateButton.setDisable(false);
        sortChoiceBox.setDisable(false);
    }

    @FXML
    private void disableUI() {
        delaySlider.setDisable(true);
        sizeSlider.setDisable(true);
        generateButton.setDisable(true);
        sortButton.setDisable(true);
        sortChoiceBox.setDisable(true);
    }

    @FXML
    private void selectSortingAlgorithm() {
        sorter = switch (sortChoiceBox.getValue()) {
            case BubbleSort -> new BubbleSort();
            case HeapSort -> new HeapSort();
            case QuickSort -> new QuickSort();
            case InsertionSort -> new InsertionSort();
            case SelectionSort -> new SelectionSort();
            case MergeSort -> throw new UnsupportedOperationException("MergeSort has not been implemented yet.");
        };
    }
}