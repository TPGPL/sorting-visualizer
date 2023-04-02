package pl.edu.pw.sortingvisualizer;

import javafx.scene.paint.Color;
import pl.edu.pw.sortingvisualizer.generators.GeneratorType;
import pl.edu.pw.sortingvisualizer.sorters.SortingAlgorithm;

public class Properties {
    public final static SortingAlgorithm DEFAULT_SORTING_ALGORITHM = SortingAlgorithm.BubbleSort;
    public final static GeneratorType DEFAULT_ARRAY_TYPE = GeneratorType.Random;
    public final static Color DEFAULT_BAR_COLOR = Color.BLACK;
    public final static Color DEFAULT_COMPARISON_COLOR = Color.RED;
    public final static Color DEFAULT_SWAP_COLOR = Color.TURQUOISE;
    public final static Color DEFAULT_OVERWRITE_COLOR = Color.GOLD;
    public final static Color DEFAULT_SORTED_COLOR = Color.GREEN;
    public final static Color DEFAULT_FAILED_COLOR = Color.CRIMSON;
}
