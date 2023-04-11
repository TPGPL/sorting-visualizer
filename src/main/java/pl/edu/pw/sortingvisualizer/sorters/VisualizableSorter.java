package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

/**
 * Klasy implementujące ten interfejs potrafią sortować rosnąco wektor i generować wizualizację procesu sortowania w postaci animacji.
 */
public interface VisualizableSorter {
    /**
     * Sortuje rosnąco wektor liczb rzeczywistych i generuje animację procesu sortowania.
     *
     * @param nums wektor liczb rzeczywistych
     * @return animacja sortowania wektora liczb
     */
    SortingAnimation sort(double[] nums);
}
