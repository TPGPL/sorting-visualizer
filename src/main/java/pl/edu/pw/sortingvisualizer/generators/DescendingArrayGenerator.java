package pl.edu.pw.sortingvisualizer.generators;

public class DescendingArrayGenerator implements ArrayGenerator {
    @Override
    public double[] generateArray(int length, double upperBound) {
        if (length <= 0 || upperBound <= 0) {
            throw new IllegalArgumentException("The params (length, upperBound) must be positive.");
        }

        double[] nums = new double[length];
        double interval = upperBound / length;

        for (int i = 0; i < length; i++) {
            nums[i] = upperBound - (i * interval);
        }

        return nums;
    }
}
