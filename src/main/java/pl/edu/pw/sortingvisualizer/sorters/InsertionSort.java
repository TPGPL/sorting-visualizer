package pl.edu.pw.sortingvisualizer.sorters;

import javafx.util.Pair;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class InsertionSort implements VisualizableSorter {
    @Override
    public SortingAnimation sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        SortingAnimation animations = new SortingAnimation();

        for (int i = 1; i < nums.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                animations.addComparisonAnimation(j, j + 1);

                if (nums[j] <= nums[j + 1])
                    break;

                animations.addSwapAnimation(new Pair<>(j, nums[j]), new Pair<>(j + 1, nums[j + 1]));
                swap(nums, j, j + 1);
            }
        }

        return animations;
    }
}
