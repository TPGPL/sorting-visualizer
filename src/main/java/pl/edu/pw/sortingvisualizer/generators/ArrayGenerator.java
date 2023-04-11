package pl.edu.pw.sortingvisualizer.generators;

/**
 * Klasy implementujące ten interfejs generują wektor liczb rzeczywistych.
 */
public interface ArrayGenerator {
    /**
     * Generuje wektor liczb rzeczywistych o określonej długości.
     *
     * @param length     długość wektora liczb
     * @param upperBound zamknięta górna granica generowanych liczb
     * @return wektor liczb rzeczywistych
     */
    double[] generateArray(int length, double upperBound);
}
