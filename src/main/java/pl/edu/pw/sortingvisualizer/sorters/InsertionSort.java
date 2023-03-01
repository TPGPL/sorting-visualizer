package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEventType;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class InsertionSort implements VisualizableSorter {
    @Override
    public List<SortingEvent> sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        List<SortingEvent> events = new ArrayList<>();

        for (int i = 1; i < nums.length; i++) {
            int j = i - 1;

            while (j >= 0) {
                events.add(new SortingEvent(SortingEventType.Comparison, j, j + 1));

                if (nums[j] <= nums[j + 1])
                    break;

                events.add(new SortingEvent(SortingEventType.Swap, j, j + 1));
                swap(nums, j, j + 1);

                j--;
            }
        }

        return events;
    }
}
