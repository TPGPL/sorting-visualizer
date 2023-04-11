package pl.edu.pw.sortingvisualizer.sorters;

import javafx.util.Pair;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

/**
 * Klasa odpowiedzialna za algorytm Odd-even sort i generowanie dla niego wizualizacji.
 */
public class OddEvenSort implements VisualizableSorter {
    /**
     * Sortuje wektor liczb algorytmem Odd-even sort i generuje dla niego wizualizację.
     *
     * @param nums wektor liczb rzeczywistych
     * @return animacja działania algorytmu Odd-even sort
     * @throws IllegalArgumentException jeżeli wektor jest nullem
     */
    @Override
    public SortingAnimation sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        SortingAnimation animations = new SortingAnimation();
        boolean hasSwapped;

        do {
            hasSwapped = false;

            for (int i = 1; i < nums.length - 1; i += 2) {
                animations.addComparisonAnimation(i, i + 1);

                if (nums[i] > nums[i + 1]) {
                    animations.addSwapAnimation(new Pair<>(i, nums[i]), new Pair<>(i + 1, nums[i + 1]));
                    swap(nums, i, i + 1);

                    hasSwapped = true;
                }
            }

            for (int i = 0; i < nums.length - 1; i += 2) {
                animations.addComparisonAnimation(i, i + 1);

                if (nums[i] > nums[i + 1]) {
                    animations.addSwapAnimation(new Pair<>(i, nums[i]), new Pair<>(i + 1, nums[i + 1]));
                    swap(nums, i, i + 1);

                    hasSwapped = true;
                }
            }
        } while (hasSwapped);

        return animations;
    }
}
