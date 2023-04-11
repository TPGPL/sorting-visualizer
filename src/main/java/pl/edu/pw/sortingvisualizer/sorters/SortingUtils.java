package pl.edu.pw.sortingvisualizer.sorters;

/**
 * Funkcjonalności pomocnicze wykorzystywane przy sortowaniach.
 */
class SortingUtils {
    /**
     * Zamienia dwa elementy wektora liczb rzeczywistych miejscami.
     *
     * @param nums        wektor liczb rzeczywistych
     * @param firstIndex  indeks pierwszego elementu
     * @param secondIndex indeks drugiego elementu
     */
    public static void swap(double[] nums, int firstIndex, int secondIndex) {
        if (nums != null && firstIndex != secondIndex && isIndexInRange(nums.length, firstIndex) && isIndexInRange(nums.length, secondIndex)) {
            double tmp = nums[firstIndex];
            nums[firstIndex] = nums[secondIndex];
            nums[secondIndex] = tmp;
        }
    }

    /**
     * Sprawdza, czy podany indeks jest poprawnym indeksem elementu wektora o okreslonej długości.
     *
     * @param length długość wektora
     * @param index  indeks elementu
     * @return czy podany indeks jest poprawnym indeksem dla wektora
     */
    private static boolean isIndexInRange(int length, int index) {
        return index >= 0 && index < length;
    }
}
