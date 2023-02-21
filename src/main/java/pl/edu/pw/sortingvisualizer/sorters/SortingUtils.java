package pl.edu.pw.sortingvisualizer.sorters;

public class SortingUtils {
    public static void swap(double[] nums, int firstIndex, int secondIndex) {
        if (nums != null && firstIndex != secondIndex && isIndexInRange(nums.length, firstIndex) && isIndexInRange(nums.length, secondIndex)) {
            double tmp = nums[firstIndex];
            nums[firstIndex] = nums[secondIndex];
            nums[secondIndex] = tmp;
        }
    }

    private static boolean isIndexInRange(int length, int index) {
        return index >= 0 && index < length;
    }
}
