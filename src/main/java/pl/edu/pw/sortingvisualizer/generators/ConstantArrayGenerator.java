package pl.edu.pw.sortingvisualizer.generators;

import java.util.Arrays;

public class ConstantArrayGenerator implements ArrayGenerator {
    @Override
    public double[] generateArray(int length, double upperBound) {
        if (length <= 0 || upperBound <= 0) {
            throw new IllegalArgumentException("The params (length, upperBound) must be positive.");
        }

        double[] nums = new double[length];
        double elem = upperBound / 2;

        Arrays.fill(nums, elem);

        return nums;
    }
}
