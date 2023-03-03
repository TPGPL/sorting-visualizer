package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEventType;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class OddEvenSort implements VisualizableSorter {
    @Override
    public List<SortingEvent> sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        List<SortingEvent> events = new ArrayList<>();
        boolean hasSwapped;

        do {
            hasSwapped = false;

            for (int i = 1; i < nums.length - 1; i += 2) {
                events.add(new SortingEvent(SortingEventType.Comparison, i, i + 1));

                if (nums[i] > nums[i + 1]) {
                    events.add(new SortingEvent(SortingEventType.Swap, i, i + 1));
                    swap(nums, i, i + 1);

                    hasSwapped = true;
                }
            }

            for (int i = 0; i < nums.length - 1; i += 2) {
                events.add(new SortingEvent(SortingEventType.Comparison, i, i + 1));

                if (nums[i] > nums[i + 1]) {
                    events.add(new SortingEvent(SortingEventType.Swap, i, i + 1));
                    swap(nums, i, i + 1);

                    hasSwapped = true;
                }
            }
        } while (hasSwapped);

        return events;
    }
}
