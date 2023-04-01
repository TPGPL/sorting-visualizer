package pl.edu.pw.sortingvisualizer.generators;

import java.util.Random;

public class RandomArrayGenerator implements ArrayGenerator {
    @Override
    public double[] generateArray(int length, double upperBound) {
        if (length <= 0 || upperBound <= 0) {
            throw new IllegalArgumentException("The params (length, upperBound) must be positive.");
        }

        double[] nums = new double[length];
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            nums[i] = rand.nextDouble() * upperBound;
        }

        return nums;
    }
}
