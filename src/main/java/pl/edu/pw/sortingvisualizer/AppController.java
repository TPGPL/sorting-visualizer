package pl.edu.pw.sortingvisualizer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pl.edu.pw.sortingvisualizer.sorters.*;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;
import pl.edu.pw.sortingvisualizer.utils.ArrayGenerator;
import pl.edu.pw.sortingvisualizer.utils.KillableThread;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;

public class AppController {

    private final static SortingAlgorithm DEFAULT_SORTING_ALGORITHM = SortingAlgorithm.BubbleSort;
    @FXML
    private ChoiceBox<SortingAlgorithm> sortChoiceBox;
    @FXML
    private Label delayLabel;
    @FXML
    private Label sizeLabel;
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
    private DecimalFormat formatter;

    @FXML
    public void initialize() {
        formatter = new DecimalFormat("0.##");
        gc = drawPanel.getGraphicsContext2D();

        updateDelayLabel();
        updateSizeLabel();
        delaySlider.valueProperty().addListener((observableValue, oldValue, newValue) -> updateDelayLabel());
        sizeSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            updateSizeLabel();
            generateArray();
        });
        sortChoiceBox.getItems().addAll(SortingAlgorithm.values());
        sortChoiceBox.setValue(DEFAULT_SORTING_ALGORITHM);
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

                        performAnimation(event);
                        Thread.sleep(getSleepDuration());
                    }

                    performCheckAnimation();
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
    private void updateDelayLabel() {
        delayLabel.setText(String.format("Delay [ms] (%s)", formatter.format(delaySlider.getValue())));
    }

    @FXML
    private void updateSizeLabel() {
        sizeLabel.setText(String.format("Array size (%d)", (int) sizeSlider.getValue()));
    }

    @FXML
    private void selectSortingAlgorithm() {
        sorter = switch (sortChoiceBox.getValue()) {
            case BubbleSort -> new BubbleSort();
            case HeapSort -> new HeapSort();
            case QuickSort -> new QuickSort();
            case InsertionSort -> new InsertionSort();
            case SelectionSort -> new SelectionSort();
            case MergeSort -> new MergeSort();
            case CocktailShakerSort -> new CocktailShakerSort();
            case ShellSort -> new ShellSort();
            case OddEvenSort -> new OddEvenSort();
            case CombSort -> new CombSort();
        };
    }

    private void swapRectangles(int firstIndex, int secondIndex) {
        if (firstIndex != secondIndex) {
            Rectangle tmp = drawRectangles[firstIndex];
            drawRectangles[firstIndex] = drawRectangles[secondIndex];
            drawRectangles[secondIndex] = tmp;
        }
    }

    private void performAnimation(SortingEvent event) {
        switch (event.getType()) {
            case Swap -> performSwapAnimation(event.getFirstElementIndex(), event.getSecondElementIndex());
            case Comparison -> performComparisonAnimation(event.getFirstElementIndex(), event.getSecondElementIndex());
            case Overwrite -> performOverwriteAnimation(event.getFirstElementIndex(), event.getValue());
        }
    }

    private void performComparisonAnimation(int firstIndex, int secondIndex) {
        try {
            recolorRectangles(Color.RED, firstIndex, secondIndex);
            Thread.sleep(getSleepDuration());
            recolorRectangles(Color.BLACK, firstIndex, secondIndex);
        } catch (InterruptedException ignored) {
        }
    }

    private void performSwapAnimation(int firstIndex, int secondIndex) {
        try {
            recolorRectangles(Color.TURQUOISE, firstIndex, secondIndex);
            Thread.sleep(getSleepDuration());
            swapRectangles(firstIndex, secondIndex);
            Platform.runLater(this::drawRectangleArray);
            Thread.sleep(getSleepDuration());
            recolorRectangles(Color.BLACK, firstIndex, secondIndex);
        } catch (InterruptedException ignored) {
        }
    }

    private void performOverwriteAnimation(int index, double newValue) {
        try {
            recolorRectangles(Color.GOLD, index);
            Thread.sleep(getSleepDuration());
            drawRectangles[index].setHeight(newValue);
            Platform.runLater(this::drawRectangleArray);
            Thread.sleep(getSleepDuration());
            recolorRectangles(Color.BLACK, index);
        } catch (InterruptedException ignored) {
        }
    }

    private void performCheckAnimation() {
        try {
            for (int i = 0; i < drawRectangles.length - 1; i++) {
                if (runner.isKilled()) {
                    return;
                }

                performComparisonAnimation(i, i + 1);
                Thread.sleep(getSleepDuration());

                if (drawRectangles[i].getHeight() <= drawRectangles[i + 1].getHeight()) {
                    recolorRectangles(Color.GREEN, i, i + 1);
                } else {
                    recolorRectangles(Color.CRIMSON, i, i + 1);

                    return;
                }

                Thread.sleep(getSleepDuration());
            }
        } catch (InterruptedException ignored) {
        }
    }

    private void recolorRectangles(Color color, int... indices) {
        for (int i : indices) {
            drawRectangles[i].setFill(color);
        }

        Platform.runLater(this::drawRectangleArray);
    }

    private Duration getSleepDuration() {
        return Duration.ofNanos((long) (delaySlider.getValue() * 1000000));
    }
}