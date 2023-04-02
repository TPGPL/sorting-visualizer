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
import java.util.Iterator;

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
    private Button stopButton;
    @FXML
    private Button stepButton;
    @FXML
    private Canvas drawPanel;
    private double[] sortArray;
    private Rectangle[] drawRectangles;
    private GraphicsContext gc;
    private KillableThread runner;
    private DecimalFormat formatter;
    private Iterator<AnimationEvent> pendingAnimations;

    @FXML
    public void initialize() {
        formatter = new DecimalFormat("0.##");
        gc = drawPanel.getGraphicsContext2D();

        updateDelayLabel();
        updateSizeLabel();
        delaySlider.valueProperty().addListener((observableValue, oldValue, newValue) -> updateDelayLabel());
        sizeSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            updateSizeLabel();

            // only generate array when sorting animation is ongoing
            if (runner == null || !runner.isAlive()) {
                generateArray();
            }
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
        pendingAnimations = null;

        drawRectangleArray();
        sortButton.setDisable(false);
        stepButton.setDisable(false);
    }

    @FXML
    public void sortAction() {
        disableUI();

        // allows resuming sorting animation if it was stopped before finishing
        if (pendingAnimations == null || !pendingAnimations.hasNext()) {
            VisualizableSorter sorter = SortingAlgorithm.getSorterFromValue(sortChoiceBox.getValue());
            SortingAnimation animations = sorter.sort(sortArray);
            pendingAnimations = animations.iterator();
        }

        runner = new KillableThread() {
            @Override
            public void run() {
                try {
                    while (pendingAnimations.hasNext()) {
                        if (isKilled) {
                            return;
                        }

                        AnimationEvent animation = pendingAnimations.next();

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
    public void stepAction() {
        disableUI();

        if (pendingAnimations == null || !pendingAnimations.hasNext()) {
            VisualizableSorter sorter = SortingAlgorithm.getSorterFromValue(sortChoiceBox.getValue());
            SortingAnimation animations = sorter.sort(sortArray);
            pendingAnimations = animations.iterator();
        }

        performAnimation(pendingAnimations.next());

        enableUI();
    }

    @FXML
    public void stopAction() {
        if (runner != null && runner.isAlive()) {
            runner.kill();
            enableUI();
        }
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
        generateButton.setDisable(false);
        stopButton.setDisable(true);

        if (pendingAnimations != null && pendingAnimations.hasNext()) {
            sortButton.setDisable(false);
            stepButton.setDisable(false);
        }
    }

    @FXML
    private void disableUI() {
        generateButton.setDisable(true);
        stepButton.setDisable(true);
        sortButton.setDisable(true);
        stopButton.setDisable(false);
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
//  - diagram klas
//  - zaimplementuj check animation dla nowej logiki