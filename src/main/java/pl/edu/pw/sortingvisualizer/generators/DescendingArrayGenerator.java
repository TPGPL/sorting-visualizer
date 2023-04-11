package pl.edu.pw.sortingvisualizer.generators;

/**
 * Generator posortowanych malejąco wektorów liczb rzeczywistych.
 */
public class DescendingArrayGenerator implements ArrayGenerator {
    /**
     * Generuje posortowany malejąco wektor liczb rzeczywistych o określonej długości.
     *
     * @param length     długość wektora liczb
     * @param upperBound zamknięta górna granica generowanych liczb
     * @return posortowany malejąco wektor liczb rzeczywistych
     * @throws IllegalArgumentException jeżeli length lub upperBound jest niedodatnie
     */
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
