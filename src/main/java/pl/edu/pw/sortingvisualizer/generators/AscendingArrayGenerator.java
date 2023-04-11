package pl.edu.pw.sortingvisualizer.generators;

/**
 * Generator posortowanych rosnąco wektorów liczb rzeczywistych.
 */
public class AscendingArrayGenerator implements ArrayGenerator {
    /**
     * Generuje posortowany rosnąco wektor liczb rzeczywistych o określonej długości.
     *
     * @param length     długość wektora liczb
     * @param upperBound zamknięta górna granica generowanych liczb
     * @return posortowany rosnąco wektor liczb rzeczywistych
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
            nums[i] = (i + 1) * interval;
        }

        return nums;
    }
}
