package pl.edu.pw.sortingvisualizer.sorters;

import javafx.util.Pair;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

/**
 * Klasa odpowiedzialna za sortowanie przez kopcowanie i generowanie dla niego wizualizacji.
 */
public class HeapSort implements VisualizableSorter {
    /**
     * Animacja sortowania przez kopcowanie.
     */
    private SortingAnimation animations;

    /**
     * Konstruktor klasy.
     */
    public HeapSort() {
        animations = new SortingAnimation();
    }

    /**
     * Sortuje wektor liczb algorytmem sortowania przez kopcowanie i generuje dla niego wizualizację.
     *
     * @param nums wektor liczb rzeczywistych
     * @return animacja sortowania przez kopcowanie
     * @throws IllegalArgumentException jeżeli wektor jest nullem
     */
    @Override
    public SortingAnimation sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        animations = new SortingAnimation();

        for (int i = 1; i < nums.length; i++) {
            heap_up(nums, i);
        }

        for (int i = nums.length - 1; i > 0; i--) {
            animations.addSwapAnimation(new Pair<>(0, nums[0]), new Pair<>(i, nums[i]));
            swap(nums, 0, i);
            heap_down(nums, i);
        }

        return animations;
    }

    /**
     * Kopcuje wektor od określonej pozycji w górę. .
     *
     * @param nums  wektor liczb rzeczywistych
     * @param start indeks początkowego elementu do przekopcowania w górę
     */
    private void heap_up(double[] nums, int start) {
        while (start > 0) {
            int parent = (start - 1) / 2;

            animations.addComparisonAnimation(start, parent);

            if (nums[start] <= nums[parent]) {
                return;
            }

            animations.addSwapAnimation(new Pair<>(start, nums[start]), new Pair<>(parent, nums[parent]));
            swap(nums, start, parent);

            start = parent;
        }
    }

    /**
     * Kopcuje wektor w dół do określonej pozycji.
     *
     * @param nums  wektor liczb rzeczywistych
     * @param limit indeks elementu ograniczającego kopcowanie
     */
    private void heap_down(double[] nums, int limit) {
        for (int child = 1; child < limit; child = 2 * child + 1) {
            if (child + 1 < limit) {
                animations.addComparisonAnimation(child + 1, child);

                if (nums[child + 1] > nums[child]) {
                    child++;
                }
            }

            int parent = (child - 1) / 2;

            animations.addComparisonAnimation(child, parent);

            if (nums[child] <= nums[parent]) {
                return;
            }

            swap(nums, child, parent);
            animations.addSwapAnimation(new Pair<>(child, nums[child]), new Pair<>(parent, nums[parent]));
        }
    }
}
