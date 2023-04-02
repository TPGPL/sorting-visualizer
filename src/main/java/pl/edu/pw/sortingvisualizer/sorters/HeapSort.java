package pl.edu.pw.sortingvisualizer.sorters;

import javafx.util.Pair;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class HeapSort implements VisualizableSorter {
    private SortingAnimation animations;

    public HeapSort() {
        animations = new SortingAnimation();
    }

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
