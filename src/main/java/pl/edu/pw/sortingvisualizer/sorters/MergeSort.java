package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

public class MergeSort implements VisualizableSorter {
    private SortingAnimation animations;

    public MergeSort() {
        animations = new SortingAnimation();
    }

    @Override
    public SortingAnimation sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        animations = new SortingAnimation();

        int length = nums.length;
        double[] tmp = new double[length];
        double[] src = nums;
        double[] dst = tmp;

        for (int l = 1; l < length; l *= 2) {
            for (int i = 0; i < length; i += 2 * l) {
                merge(src, i, (Math.min(i + l, length)), (Math.min(i + 2 * l, length)), dst);
            }

            tmp = src;
            src = dst;
            dst = tmp;
        }

        if (src != nums) {
            System.arraycopy(src, 0, nums, 0, length);
        }

        return animations;
    }

    private void merge(double[] src, int start, int mid, int end, double[] dst) {
        int i = start;
        int j = mid;
        int k = start;

        while (i < mid && j < end) {
            animations.addComparisonAnimation(i, j);

            if (src[i] <= src[j]) {
                animations.addOverwriteAnimation(k, src[i]);

                dst[k++] = src[i++];
            } else {
                animations.addOverwriteAnimation(k, src[j]);

                dst[k++] = src[j++];
            }
        }

        while (i < mid) {
            animations.addOverwriteAnimation(k, src[i]);

            dst[k++] = src[i++];
        }

        while (j < end) {
            animations.addOverwriteAnimation(k, src[j]);

            dst[k++] = src[j++];
        }
    }
}
