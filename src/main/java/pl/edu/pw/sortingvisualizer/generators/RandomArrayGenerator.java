package pl.edu.pw.sortingvisualizer.generators;

import java.util.Random;

/**
 * Generator wektorów pseudolosowych liczb rzeczywistych.
 */
public class RandomArrayGenerator implements ArrayGenerator {
    /**
     * Generuje wektor pseudolosowych liczb rzeczywistych o określonej długości.
     *
     * @param length     długość wektora liczb
     * @param upperBound zamknięta górna granica generowanych liczb
     * @return wektor pseudolosowych liczb rzeczywistych
     * @throws IllegalArgumentException jeżeli length lub upperBound jest niedodatnie
     */
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
