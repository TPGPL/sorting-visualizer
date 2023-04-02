package pl.edu.pw.sortingvisualizer.sorters;

public enum SortingAlgorithm {
    MergeSort,
    HeapSort,
    QuickSort,
    InsertionSort,
    SelectionSort,
    BubbleSort,
    CocktailShakerSort,
    ShellSort,
    OddEvenSort,
    CombSort;

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
