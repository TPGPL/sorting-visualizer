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
import pl.edu.pw.sortingvisualizer.sorters.SortingAlgorithm;
import pl.edu.pw.sortingvisualizer.sorters.VisualizableSorter;
import pl.edu.pw.sortingvisualizer.utils.KillableThread;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Iterator;

import static pl.edu.pw.sortingvisualizer.Properties.DEFAULT_ARRAY_TYPE;
import static pl.edu.pw.sortingvisualizer.Properties.DEFAULT_SORTING_ALGORITHM;
import static pl.edu.pw.sortingvisualizer.utils.RectangleArrayUtils.convertDoubleToRectangleArray;

/**
 * Kontroler aplikacji odpowiadający za obsługiwanie interakcji użytkownika z interfejsem graficznym aplikacji.
 */
public class AppController {
    /**
     * Lista rozwijana dostępnych typów wektora.
     */
    @FXML
    private ChoiceBox<GeneratorType> arrayChoiceBox;
    /**
     * Lista rozwijana dostępnych algorytmów sortowania.
     */
    @FXML
    private ChoiceBox<SortingAlgorithm> sortChoiceBox;
    /**
     * Etykieta suwaka zmiany odstępu czasowego.
     */
    @FXML
    private Label delayLabel;
    /**
     * Etykieta suwaka zmiany wielkości wektora.
     */
    @FXML
    private Label sizeLabel;
    /**
     * Suwak zmiany odstępu czasowego.
     */
    @FXML
    private Slider delaySlider;
    /**
     * Suwak zmiany wielkości wektora.
     */
    @FXML
    private Slider sizeSlider;
    /**
     * Przycisk generujący nowy wektor.
     */
    @FXML
    private Button generateButton;
    /**
     * Przycisk rozpoczynający wizualizację sortowania w trybie ciągłym.
     */
    @FXML
    private Button sortButton;
    /**
     * Przycisk zatrzymujący wizualizację sortowania w trybie ciągłym.
     */
    @FXML
    private Button stopButton;
    /**
     * Przycisk wyświetlający kolejną klatkę animacji sortowania.
     */
    @FXML
    private Button stepButton;
    /**
     * Panel wizualizacji.
     */
    @FXML
    private Canvas drawPanel;
    /**
     * Wektor liczb rzeczywistych do posortowania.
     */
    private double[] sortArray;
    /**
     * Reprezentacja wektora liczb w postaci wektora prostokątów.
     */
    private Rectangle[] drawRectangles;
    /**
     * Element panelu wizualizacji umożliwiający rysowanie po nim.
     */
    private GraphicsContext gc;
    /**
     * Wątek odpowiadający za wizualizację w trybie ciągłym.
     */
    private KillableThread runner;
    /**
     * Formatowanie liczb w etykietach.
     */
    private DecimalFormat formatter;
    /**
     * Zdarzenia elementarne animacji, które nie zostały jeszcze zwizualizowane.
     */
    private Iterator<AnimationEvent> pendingAnimations;

    /**
     * Inicjalizuje domyślne ustawienia interfejsu i ustawia nasłuchy na zmiany wartości suwaków.
     */
    @FXML
    public void initialize() {
        formatter = new DecimalFormat("0.##");
        gc = drawPanel.getGraphicsContext2D();

        updateDelayLabel();
        updateSizeLabel();
        delaySlider.valueProperty().addListener((observableValue, oldValue, newValue) -> updateDelayLabel());
        sizeSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            updateSizeLabel();

            // only generate array when sorting animation is not ongoing
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

    /**
     * Generuje nowy wektor do posortowania wraz z jego wizualizacją i przełącza stan interfejsu.
     *
     * @see AppController#togglePostGenerationUI() togglePostGenerationUI
     */
    @FXML
    public void generateArray() {
        int elemCount = (int) sizeSlider.getValue();
        ArrayGenerator generator = GeneratorType.getGeneratorFromValue(arrayChoiceBox.getValue());
        sortArray = generator.generateArray(elemCount, drawPanel.getHeight());
        drawRectangles = convertDoubleToRectangleArray(sortArray, drawPanel.getWidth());
        pendingAnimations = null;

        drawRectangleArray();
        togglePostGenerationUI();
    }

    /**
     * Uruchamia wizualizację działania algorytmu sortowania w trybie ciągłym i przełącza stan interfejsu.
     *
     * @see AppController#toggleSortingUI() toggleSortingUI
     */
    @FXML
    public void sortAction() {
        toggleSortingUI();

        prepareSortingAnimation();

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

                Platform.runLater(() -> toggleIdleUI());
            }
        };

        runner.start();
    }

    /**
     * Przechodzi do następnej klatki animacji (wizualizacja w trybie krokowym).
     */
    @FXML
    public void stepAction() {
        toggleSortingUI();

        prepareSortingAnimation();

        if (pendingAnimations.hasNext()) {
            performAnimation(pendingAnimations.next());
        }

        toggleIdleUI();
    }

    /**
     * Zatrzymuje wizualizację w trybie ciągłym i przełącza stan interfejsu.
     *
     * @see AppController#toggleIdleUI() toggleIdleUI
     */
    @FXML
    public void stopAction() {
        if (runner != null && runner.isAlive()) {
            runner.kill();
            toggleIdleUI();
        }
    }

    /**
     * Przygotowuje animację sortowania dla nowo wygenerowanego wektora.
     */
    private void prepareSortingAnimation() {
        if (pendingAnimations == null) {
            VisualizableSorter sorter = SortingAlgorithm.getSorterFromValue(sortChoiceBox.getValue());
            SortingAnimation animations = sorter.sort(sortArray);

            addCheckAnimation(animations);

            pendingAnimations = animations.iterator();
        }
    }

    /**
     * Dodaje animację sprawdzenia poprawności posortowania wektora.
     *
     * @param animations animacja sortowania aktualnego wektora
     */
    private void addCheckAnimation(SortingAnimation animations) {
        for (int i = 0; i < sortArray.length - 1; i++) {
            animations.addComparisonAnimation(i, i + 1);

            if (sortArray[i] <= sortArray[i + 1]) {
                animations.addSuccessfulSortAnimation(i, i + 1);
            } else {
                animations.addFailedSortAnimation(i, i + 1);
                return;
            }
        }
    }

    /**
     * Rysuje wizualizację aktualnego stanu wektora liczb w postaci prostokątów.
     */
    @FXML
    private void drawRectangleArray() {
        gc.clearRect(0, 0, drawPanel.getWidth(), drawPanel.getHeight());

        double elemWidth = drawPanel.getWidth() / drawRectangles.length;

        for (int i = 0; i < drawRectangles.length; i++) {
            gc.setFill(drawRectangles[i].getFill());
            gc.fillRect(i * elemWidth, drawPanel.getHeight() - drawRectangles[i].getHeight(), elemWidth, drawRectangles[i].getHeight());
        }
    }

    /**
     * Zmienia dostępność elementów interfejsu na stan po wygenerowaniu nowego wektora.
     */
    @FXML
    private void togglePostGenerationUI() {
        sortButton.setDisable(false);
        stepButton.setDisable(false);
        sortButton.setText("Sort");
    }

    /**
     * Zmienia dostępność elementów interfejsu na stan, gdy wizualizacja w trybie ciągłym nie jest aktywna.
     */
    @FXML
    private void toggleIdleUI() {
        generateButton.setDisable(false);
        stopButton.setDisable(true);
        sortButton.setText("Sort");

        // allows resuming sorting animation if it was stopped before finishing
        if (pendingAnimations != null && pendingAnimations.hasNext()) {
            sortButton.setText("Resume sorting");
            sortButton.setDisable(false);
            stepButton.setDisable(false);
        }
    }

    /**
     * Zmienia dostępność elementów interfejsu na stan, gdy wizualizacja w trybie ciągłym jest aktywna.
     */
    @FXML
    private void toggleSortingUI() {
        generateButton.setDisable(true);
        stepButton.setDisable(true);
        sortButton.setDisable(true);
        stopButton.setDisable(false);
    }

    /**
     * Aktualizuje tekst etykiety suwaka zmiany odstępu czasowego w oparciu o jego wartość.
     */
    @FXML
    private void updateDelayLabel() {
        delayLabel.setText(String.format("Delay [ms] (%s)", formatter.format(delaySlider.getValue())));
    }

    /**
     * Aktualizuje tekst etykiety suwaka zmiany wielkości wektora w oparciu o jego wartość.
     */
    @FXML
    private void updateSizeLabel() {
        sizeLabel.setText(String.format("Array size (%d)", (int) sizeSlider.getValue()));
    }

    /**
     * Aktualizuje model względem przekazanego zdarzenia elementarnego animacji.
     *
     * @param event zdarzenie elementarne animacji
     */
    private void performAnimation(AnimationEvent event) {
        if (event instanceof ColorChangeEvent e) {
            performColorChange(e);
        } else if (event instanceof ValueChangeEvent e) {
            performValueChange(e);
        }

        Platform.runLater(this::drawRectangleArray);
    }

    /**
     * Zmienia wartość elementów uwzględnionych w przekazanym zdarzeniu animacji.
     *
     * @param event zdarzenie reprezentujące zmianę wartości
     */
    private void performValueChange(ValueChangeEvent event) {
        for (int i : event) {
            drawRectangles[i].setHeight(event.getNewValueForIndex(i));
        }
    }

    /**
     * Zmienia kolor elementów uwzględnionych w przekazanym zdarzeniu animacji.
     *
     * @param event zdarzenie reprezentujące zmianę koloru
     */
    private void performColorChange(ColorChangeEvent event) {
        Color newColor = event.getNewColor();

        for (int i : event) {
            drawRectangles[i].setFill(newColor);
        }
    }

    /**
     * Zwraca długość odstępu czasowego między kolejnymi animacjami w nanosekundach.
     *
     * @return długość odstępu czasowego w nanosekundach
     */
    private Duration getSleepDuration() {
        return Duration.ofNanos((long) (delaySlider.getValue() * 1000000));
    }
}