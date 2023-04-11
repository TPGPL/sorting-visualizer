package pl.edu.pw.sortingvisualizer;

import javafx.scene.paint.Color;
import pl.edu.pw.sortingvisualizer.generators.GeneratorType;
import pl.edu.pw.sortingvisualizer.sorters.SortingAlgorithm;

/**
 * Domyślna konfiguracja działania programu.
 */
public class Properties {
    /**
     * Domyślny algorytm sortowania.
     */
    public final static SortingAlgorithm DEFAULT_SORTING_ALGORITHM = SortingAlgorithm.BubbleSort;
    /**
     * Domyślny typ wektora.
     */
    public final static GeneratorType DEFAULT_ARRAY_TYPE = GeneratorType.Random;
    /**
     * Domyślny kolor prostokątów podczas wizualizacji.
     */
    public final static Color DEFAULT_BAR_COLOR = Color.BLACK;
    /**
     * Domyślny kolor porównywanych prostokątów podczas wizualizacji.
     */
    public final static Color DEFAULT_COMPARISON_COLOR = Color.RED;
    /**
     * Domyślny kolor zamienianych prostokątów podczas wizualizacji.
     */
    public final static Color DEFAULT_SWAP_COLOR = Color.TURQUOISE;
    /**
     * Domyślny kolor prostokątów o nadpisywanej wartości podczas wizualizacji.
     */
    public final static Color DEFAULT_OVERWRITE_COLOR = Color.GOLD;
    /**
     * Domyślny kolor poprawnie posortowanych prostokątów podczas sprawdzania poprawności sortowania.
     */
    public final static Color DEFAULT_SORTED_COLOR = Color.GREEN;
    /**
     * Domyślny kolor niepoprawnie posortowanych prostokątów podczas sprawdzania poprawności sortowania.
     */
    public final static Color DEFAULT_FAILED_COLOR = Color.CRIMSON;
}
