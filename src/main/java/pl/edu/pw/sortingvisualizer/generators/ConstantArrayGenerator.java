package pl.edu.pw.sortingvisualizer.generators;

import java.util.Arrays;

/**
 * Generator wektorów o stałej wartości rzecziwstej w każdym polu.
 */
public class ConstantArrayGenerator implements ArrayGenerator {
    /**
     * Generuje wektor z połową dostępnego zakresu w każdym pelu.
     *
     * @param length     długość wektora liczb
     * @param upperBound zamknięta górna granica generowanych liczb
     * @return wektor ze stałą wartością w każdym polu
     * @throws IllegalArgumentException jeżeli length lub upperBound jest niedodatnie
     */
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
