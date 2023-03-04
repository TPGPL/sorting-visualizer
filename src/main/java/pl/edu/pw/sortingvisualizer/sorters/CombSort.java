package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEventType;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class CombSort implements VisualizableSorter {
    private final static double RATIO = 1.3;

    @Override
    public List<SortingEvent> sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        List<SortingEvent> events = new ArrayList<>();
        int gap = nums.length;
        boolean hasSwapped;

        do {
            hasSwapped = false;
            gap = Math.max(1, (int) (gap / RATIO));

            for (int i = 0; i < nums.length - gap; i++) {
                events.add(new SortingEvent(SortingEventType.Comparison, i, i + gap));

                if (nums[i] > nums[i + gap]) {
                    events.add(new SortingEvent(SortingEventType.Swap, i, i + gap));
                    swap(nums, i, i + gap);

                    hasSwapped = true;
                }
            }
        } while (gap > 1 || hasSwapped);

        return events;
    }
}
