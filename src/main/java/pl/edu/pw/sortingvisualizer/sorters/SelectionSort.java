package pl.edu.pw.sortingvisualizer.sorters;

import javafx.util.Pair;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

/**
 * Klasa odpowiedzialna za sortowanie przez wybór i generowanie dla niego wizualizacji.
 */
public class SelectionSort implements VisualizableSorter {
    /**
     * Sortuje wektor liczb algorytmem sortowania przez wybór i generuje dla niego wizualizację.
     *
     * @param nums wektor liczb rzeczywistych
     * @return animacja sortowania przez wybór
     * @throws IllegalArgumentException jeżeli wektor jest nullem
     */
    @Override
    public SortingAnimation sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        SortingAnimation animations = new SortingAnimation();

        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < nums.length; j++) {
                animations.addComparisonAnimation(j, minIndex);

                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }

            if (i != minIndex) {
                animations.addSwapAnimation(new Pair<>(i, nums[i]), new Pair<>(minIndex, nums[minIndex]));
            }

            swap(nums, i, minIndex);
        }

        return animations;
    }
}
