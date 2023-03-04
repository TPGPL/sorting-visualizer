package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEventType;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class QuickSort implements VisualizableSorter {
    private final List<SortingEvent> events;

    public QuickSort() {
        events = new ArrayList<>();
    }

    @Override
    public List<SortingEvent> sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        events.clear();

        recqsort(nums, 0, nums.length - 1);

        return events;
    }

    private void recqsort(double[] nums, int start, int end) {
        if (start < end) {
            int mid = divide(nums, start, end);

            recqsort(nums, start, mid - 1);
            recqsort(nums, mid + 1, end);
        }
    }

    private int divide(double[] nums, int start, int end) {
        int left = start + 1;
        int right = end;

        while (left < right) {
            while (left < right) {
                events.add(new SortingEvent(SortingEventType.Comparison, left, start));

                if (nums[left] >= nums[start]) {
                    break;
                }

                left++;
            }

            while (left < right) {
                events.add(new SortingEvent(SortingEventType.Comparison, right, start));

                if (nums[right] < nums[start]) {
                    break;
                }

                right--;
            }

            if (left != right) {
                events.add(new SortingEvent(SortingEventType.Swap, left, right));
                swap(nums, left, right);
            }
        }

        if (nums[left] > nums[start]) {
            left--;
        }

        events.add(new SortingEvent(SortingEventType.Swap, start, left));
        swap(nums, start, left);

        return left;
    }
}
