package pl.edu.pw.sortingvisualizer.sorters;

import javafx.util.Pair;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

/**
 * Klasa odpowiedzialna za sortowanie bąbelkowe i generowanie dla niego wizualizacji.
 */
public class BubbleSort implements VisualizableSorter {
    /**
     * Sortuje wektor liczb algorytmem sortowania bąbelkowego i generuje dla niego wizualizację.
     *
     * @param nums wektor liczb rzeczywistych
     * @return animacja sortowania bąbelkowego
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

            for (int i = 0; i < nums.length - 1; i++) {
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
