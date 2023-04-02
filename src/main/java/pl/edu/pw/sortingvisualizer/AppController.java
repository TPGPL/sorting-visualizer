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
import pl.edu.pw.sortingvisualizer.animations.AnimationEvent;
import pl.edu.pw.sortingvisualizer.animations.ColorChangeEvent;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;
import pl.edu.pw.sortingvisualizer.animations.ValueChangeEvent;
import pl.edu.pw.sortingvisualizer.generators.ArrayGenerator;
import pl.edu.pw.sortingvisualizer.generators.GeneratorType;
import pl.edu.pw.sortingvisualizer.sorters.VisualizableSorter;
import pl.edu.pw.sortingvisualizer.sorters.SortingAlgorithm;
import pl.edu.pw.sortingvisualizer.utils.KillableThread;

import java.text.DecimalFormat;
import java.time.Duration;

import static pl.edu.pw.sortingvisualizer.utils.RectangleArrayUtils.*;
import static pl.edu.pw.sortingvisualizer.Properties.*;

public class AppController {
    @FXML
    private ChoiceBox<GeneratorType> arrayChoiceBox;
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
        arrayChoiceBox.getItems().addAll(GeneratorType.values());
        arrayChoiceBox.setValue(DEFAULT_ARRAY_TYPE);
        sortChoiceBox.getItems().addAll(SortingAlgorithm.values());
        sortChoiceBox.setValue(DEFAULT_SORTING_ALGORITHM);
        gc.fillText("<no array generated>", 0, drawPanel.getHeight() - 10);
    }

    @FXML
    public void generateArray() {
        int elemCount = (int) sizeSlider.getValue();
        ArrayGenerator generator = GeneratorType.getGeneratorFromValue(arrayChoiceBox.getValue());
        sortArray = generator.generateArray(elemCount, drawPanel.getHeight());
        drawRectangles = convertDoubleToRectangleArray(sortArray, drawPanel.getWidth());

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

        VisualizableSorter sorter = SortingAlgorithm.getSorterFromValue(sortChoiceBox.getValue());
        SortingAnimation animations = sorter.sort(sortArray);

        runner = new KillableThread() {
            @Override
            public void run() {
                try {
                    for (AnimationEvent animation : animations) {
                        if (isKilled) {
                            return;
                        }

                        performAnimation(animation);
                        Thread.sleep(getSleepDuration());
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

        // rounding causes white bars to appear, so the general element width is increased by 20% to hide them;
        for (int i = 0; i < drawRectangles.length; i++) {
            gc.setFill(drawRectangles[i].getFill());
            gc.fillRect(i * elemWidth, drawPanel.getHeight() - drawRectangles[i].getHeight(), 1.2 * elemWidth, drawRectangles[i].getHeight());
        }
    }

    @FXML
    private void enableUI() {
        delaySlider.setDisable(false);
        sizeSlider.setDisable(false);
        generateButton.setDisable(false);
        sortChoiceBox.setDisable(false);
        arrayChoiceBox.setDisable(false);
        sortButton.setText("Sort");
        sortButton.setDisable(true);
    }

    @FXML
    private void disableUI() {
        sizeSlider.setDisable(true);
        generateButton.setDisable(true);
        sortChoiceBox.setDisable(true);
        arrayChoiceBox.setDisable(true);
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

    private void performAnimation(AnimationEvent event) {
        if (event instanceof ColorChangeEvent e) {
            performColorChange(e);
        } else if (event instanceof ValueChangeEvent e) {
            performValueChange(e);
        }

        Platform.runLater(this::drawRectangleArray);
    }

    private void performValueChange(ValueChangeEvent e) {
        for (int i : e) {
            drawRectangles[i].setHeight(e.getNewValueForIndex(i));
        }
    }

    private void performColorChange(ColorChangeEvent e) {
        Color newColor = e.getNewColor();

        for (int i : e) {
            drawRectangles[i].setFill(newColor);
        }
    }

    private Duration getSleepDuration() {
        return Duration.ofNanos((long) (delaySlider.getValue() * 1000000));
    }
}
// TODO:
//  - specyfikacja funkcjonalna, implementacyjna
//  - step-by-step wizualizacja
//  - diagram klas
//  - wpisywalny array size (?)
//  - dekompozycja animacji -> rozbij animacje na pojedyńcze akcje