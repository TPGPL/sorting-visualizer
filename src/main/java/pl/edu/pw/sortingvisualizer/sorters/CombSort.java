package pl.edu.pw.sortingvisualizer.sorters;

import javafx.util.Pair;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class CombSort implements VisualizableSorter {
    private final static double RATIO = 1.3;

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
