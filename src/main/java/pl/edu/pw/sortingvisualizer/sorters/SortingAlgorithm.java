package pl.edu.pw.sortingvisualizer.sorters;

/**
 * Obsługiwane algorytmy sortowania.
 */
public enum SortingAlgorithm {
    /**
     * Algorytm sortowania przez scalanie.
     */
    MergeSort,
    /**
     * Algorytm sortowania przez kopcowanie.
     */
    HeapSort,
    /**
     * Algorytm sortowania szybkiego.
     */
    QuickSort,
    /**
     * Algorytm sortowania przez wstawianie.
     */
    InsertionSort,
    /**
     * Algorytm sortowania przez wybieranie.
     */
    SelectionSort,
    /**
     * Algorytm sortowania bąbelkowego.
     */
    BubbleSort,
    /**
     * Algorytm sortowanie koktajlowego.
     */
    CocktailShakerSort,
    /**
     * Algorytm sortowania Shella.
     */
    ShellSort,
    /**
     * Algorytm Odd-even sort.
     */
    OddEvenSort,
    /**
     * Algorytm sortowania grzebieniowego.
     */
    CombSort;

    /**
     * Zwraca odpowiadający algorytm sortowania dla określonego typu.
     *
     * @param e rodzaj algorytmu
     * @return algorytm sortowania przyporządkowany do określonego typu
     */
    public static VisualizableSorter getSorterFromValue(SortingAlgorithm e) {
        return switch (e) {
            case MergeSort -> new MergeSort();
            case HeapSort -> new HeapSort();
            case QuickSort -> new QuickSort();
            case InsertionSort -> new InsertionSort();
            case SelectionSort -> new SelectionSort();
            case BubbleSort -> new BubbleSort();
            case CocktailShakerSort -> new CocktailShakerSort();
            case ShellSort -> new ShellSort();
            case OddEvenSort -> new OddEvenSort();
            case CombSort -> new CombSort();
        };
    }
}
