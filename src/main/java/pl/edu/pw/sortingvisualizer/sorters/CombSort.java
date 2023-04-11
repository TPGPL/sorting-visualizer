package pl.edu.pw.sortingvisualizer.sorters;

import javafx.util.Pair;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

/**
 * Klasa odpowiedzialna za sortowanie grzebieniowe i generowanie dla niego wizualizacji.
 */
public class CombSort implements VisualizableSorter {
    /**
     * Stała wykorzystywana do obliczania odległości między porównywanymi elementami.
     */
    private final static double RATIO = 1.3;

    /**
     * Sortuje wektor liczb algorytmem sortowania grzebieniowego i generuje dla niego wizualizację.
     *
     * @param nums wektor liczb rzeczywistych
     * @return animacja sortowania grzebieniowego
     * @throws IllegalArgumentException jeżeli wektor jest nullem
     */
    @Override
    public SortingAnimation sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        SortingAnimation animations = new SortingAnimation();
        int gap = nums.length;
        boolean hasSwapped;

        do {
            hasSwapped = false;
            gap = Math.max(1, (int) (gap / RATIO));

            for (int i = 0; i < nums.length - gap; i++) {
                animations.addComparisonAnimation(i, i + gap);

                if (nums[i] > nums[i + gap]) {
                    animations.addSwapAnimation(new Pair<>(i, nums[i]), new Pair<>(i + gap, nums[i + gap]));
                    swap(nums, i, i + gap);

                    hasSwapped = true;
                }
            }
        } while (gap > 1 || hasSwapped);

        return animations;
    }
}
