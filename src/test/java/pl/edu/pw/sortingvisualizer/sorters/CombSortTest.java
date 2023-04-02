package pl.edu.pw.sortingvisualizer.sorters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CombSortTest {
    private static final double DELTA = 0;
    private static final int ARRAY_SIZE = 3000;
    private static final int SEED = 1000;
    private VisualizableSorter sorter;

    @BeforeEach
    public void setup() {
        sorter = new InsertionSort();
    }

    @Test
    public void numsArrayisEmptyTest() {
        // given
        double[] nums = {};

        // when
        sorter.sort(nums);

        // then
        int expected = 0;
        assertEquals(expected, nums.length);
    }

    @Test
    public void numsArrayHasSingleElementTest() {
        // given
        double[] nums = {1};

        // when
        sorter.sort(nums);

        // then
        double[] expected = {1};
        assertArrayEquals(expected, nums, DELTA);
    }

    @Test
    public void numsArrayIsSortedAscTest() {
        // given
        double[] nums = {1, 2, 3, 4, 5};

        // when
        sorter.sort(nums);

        // then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, DELTA);
    }

    @Test
    public void numsArrayIsSortedDescTest() {
        // given
        double[] nums = {5, 4, 3, 2, 1};

        // when
        sorter.sort(nums);

        // then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, DELTA);
    }

    @Test
    public void sortUnorderedArrayTest() {
        // given
        double[] nums = {4, 14, 18, 15, 9, 1, 2, 12, 7, 17};

        // when
        sorter.sort(nums);

        // then
        double[] expected = {1, 2, 4, 7, 9, 12, 14, 15, 17, 18};
        assertArrayEquals(expected, nums, DELTA);
    }

    @Test
    public void sortBigRandomArrayTest() {
        // given
        Random r = new Random(SEED);
        double[] nums = new double[ARRAY_SIZE];

        for (int i = 0; i < ARRAY_SIZE; i++) {
            nums[i] = r.nextDouble();
        }

        // when
        sorter.sort(nums);
        boolean result = isArraySorted(nums);

        // then
        boolean expectedIsSorted = true;
        assertEquals(expectedIsSorted, result);
    }

    private boolean isArraySorted(double[] data) {
        if (data == null) {
            throw new IllegalArgumentException("The data array must not be null.");
        }

        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] > data[i + 1]) {
                return false;
            }
        }

        return true;
    }
}