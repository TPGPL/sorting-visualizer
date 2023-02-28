package pl.edu.pw.sortingvisualizer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pl.edu.pw.sortingvisualizer.sorters.BubbleSort;
import pl.edu.pw.sortingvisualizer.sorters.HeapSort;
import pl.edu.pw.sortingvisualizer.sorters.InsertionSort;
import pl.edu.pw.sortingvisualizer.sorters.QuickSort;
import pl.edu.pw.sortingvisualizer.sorters.SelectionSort;
import pl.edu.pw.sortingvisualizer.sorters.SortingAlgorithm;
import pl.edu.pw.sortingvisualizer.sorters.VisualizableSorter;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;

import java.util.List;

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
    private KillableThread runner;

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
    public void sortAction() {
        // takes action of a Stop button when the thread is active
        if (runner != null && runner.isAlive()) {
            runner.kill();
            enableUI();

            return;
        }

        disableUI();
        selectSortingAlgorithm();

        List<SortingEvent> events = sorter.sort(sortArray);

        runner = new KillableThread() {
            @Override
            public void run() {
                try {
                    for (SortingEvent event : events) {
                        if (isKilled) {
                            return;
                        }

                        switch (event.getType()) {
                            case Comparison ->
                                    performComparisonAnimation(event.getFirstElementIndex(), event.getSecondElementIndex());
                            case Swap ->
                                    performSwapAnimation(event.getFirstElementIndex(), event.getSecondElementIndex());
                        }

                        Thread.sleep((long) delaySlider.getValue());
                    }

                    for (int i = 0; i < drawRectangles.length; i++) {
                        recolorRectangles(Color.DARKGREEN, i);
                        Thread.sleep((long) delaySlider.getValue());
                    }
                } catch (InterruptedException ignored) {
                }

                Platform.runLater(() -> enableUI());
            }
        };

        runner.start();
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
    private void enableUI() {
        delaySlider.setDisable(false);
        sizeSlider.setDisable(false);
        generateButton.setDisable(false);
        sortChoiceBox.setDisable(false);

        sortButton.setText("Sort");
        sortButton.setDisable(true);
    }

    @FXML
    private void disableUI() {
        sizeSlider.setDisable(true);
        generateButton.setDisable(true);
        sortChoiceBox.setDisable(true);

        sortButton.setText("Stop");
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

    private void swapRectangles(int firstIndex, int secondIndex) {
        if (firstIndex != secondIndex) {
            Rectangle tmp = drawRectangles[firstIndex];
            drawRectangles[firstIndex] = drawRectangles[secondIndex];
            drawRectangles[secondIndex] = tmp;
        }
    }

    private void performComparisonAnimation(int firstIndex, int secondIndex) {
        try {
            recolorRectangles(Color.RED, firstIndex, secondIndex);
            Thread.sleep((long) delaySlider.getValue());
            recolorRectangles(Color.BLACK, firstIndex, secondIndex);
        } catch (InterruptedException ignored) {
        }
    }

    private void performSwapAnimation(int firstIndex, int secondIndex) {
        try {
            recolorRectangles(Color.TURQUOISE, firstIndex, secondIndex);
            Thread.sleep((long) delaySlider.getValue());
            swapRectangles(firstIndex, secondIndex);
            Platform.runLater(this::drawRectangleArray);
            Thread.sleep((long) delaySlider.getValue());
            recolorRectangles(Color.BLACK, firstIndex, secondIndex);
        } catch (InterruptedException ignored) {
        }
    }

    private void recolorRectangles(Color color, int... indices) {
        for (int i : indices) {
            drawRectangles[i].setFill(color);
        }

        Platform.runLater(this::drawRectangleArray);
    }
}