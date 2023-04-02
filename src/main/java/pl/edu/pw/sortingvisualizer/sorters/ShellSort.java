package pl.edu.pw.sortingvisualizer.sorters;

import javafx.util.Pair;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class ShellSort implements VisualizableSorter {

    @Override
    public SortingAnimation sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        SortingAnimation animations = new SortingAnimation();

        int gap = 1;

        while (gap <= nums.length / 9) {
            gap = 3 * gap + 1;
        }

        for (; gap >= 1; gap /= 3) {
            for (int i = gap; i < nums.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    animations.addComparisonAnimation(j, j + gap);

                    if (nums[j] <= nums[j + gap])
                        break;

                    animations.addSwapAnimation(new Pair<>(j, nums[j]), new Pair<>(j + gap, nums[j + gap]));
                    swap(nums, j, j + gap);
                }
            }
        }

        return animations;
    }
}
