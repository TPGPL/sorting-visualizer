package pl.edu.pw.sortingvisualizer.sorters;

import javafx.util.Pair;
import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

/**
 * Klasa odpowiedzialna za sortowanie szybkie i generowanie dla niego wizualizacji.
 */
public class QuickSort implements VisualizableSorter {
    /**
     * Animacja sortowania szybkiego.
     */
    private SortingAnimation animations;

    /**
     * Konstruktor klasy.
     */
    public QuickSort() {
        animations = new SortingAnimation();
    }

    /**
     * Sortuje wektor liczb algorytmem sortowania szybkiego i generuje dla niego wizualizację.
     *
     * @param nums wektor liczb rzeczywistych
     * @return animacja sortowania szybkiego
     * @throws IllegalArgumentException jeżeli wektor jest nullem
     */
    @Override
    public SortingAnimation sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        animations = new SortingAnimation();

        recqsort(nums, 0, nums.length - 1);

        return animations;
    }

    /**
     * Rekurencyjnie sortuje podwektor liczb algorytmem sortowania szybkiego.
     *
     * @param nums  wektor liczb rzeczywistych
     * @param start indeks początkowy podwektora
     * @param end   indeks końcowy podwektora
     */
    private void recqsort(double[] nums, int start, int end) {
        if (start < end) {
            int mid = divide(nums, start, end);

            recqsort(nums, start, mid - 1);
            recqsort(nums, mid + 1, end);
        }
    }

    /**
     * Wybiera element główny i dzieli wektor na 2 podwektory z niesymetryczną relacją względem elementu głównego.
     *
     * @param nums  wektor liczb rzeczywistych
     * @param start indeks początkowy podwektora
     * @param end   indeks kończowy podwektora
     * @return indeks elementu głównego
     */
    private int divide(double[] nums, int start, int end) {
        int left = start + 1;
        int right = end;

        while (left < right) {
            while (left < right) {
                animations.addComparisonAnimation(left, start);

                if (nums[left] >= nums[start]) {
                    break;
                }

                left++;
            }

            while (left < right) {
                animations.addComparisonAnimation(right, start);

                if (nums[right] < nums[start]) {
                    break;
                }

                right--;
            }

            if (left != right) {
                animations.addSwapAnimation(new Pair<>(left, nums[left]), new Pair<>(right, nums[right]));
                swap(nums, left, right);
            }
        }

        if (nums[left] > nums[start]) {
            left--;
        }

        animations.addSwapAnimation(new Pair<>(start, nums[start]), new Pair<>(left, nums[left]));
        swap(nums, start, left);

        return left;
    }
}
