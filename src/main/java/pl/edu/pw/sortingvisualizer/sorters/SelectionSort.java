package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEventType;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class SelectionSort implements VisualizableSorter {
    @Override
    public List<SortingEvent> sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        List<SortingEvent> events = new ArrayList<>();

        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < nums.length; j++) {
                events.add(new SortingEvent(SortingEventType.Comparison, j, minIndex));

                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }

            if (i != minIndex) {
                events.add(new SortingEvent(SortingEventType.Swap, i, minIndex));
            }

            swap(nums, i, minIndex);
        }

        return events;
    }
}
