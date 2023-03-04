package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEventType;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class HeapSort implements VisualizableSorter {
    private final List<SortingEvent> events;

    public HeapSort() {
        events = new ArrayList<>();
    }

    @Override
    public List<SortingEvent> sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        events.clear();

        for (int i = 1; i < nums.length; i++) {
            heap_up(nums, i);
        }

        for (int i = nums.length - 1; i > 0; i--) {
            events.add(new SortingEvent(SortingEventType.Swap, 0, i));
            swap(nums, 0, i);
            heap_down(nums, i);
        }

        return events;
    }

    private void heap_up(double[] nums, int start) {
        while (start > 0) {
            int parent = (start - 1) / 2;

            events.add(new SortingEvent(SortingEventType.Comparison, start, parent));

            if (nums[start] <= nums[parent]) {
                return;
            }

            events.add(new SortingEvent(SortingEventType.Swap, start, parent));
            swap(nums, start, parent);

            start = parent;
        }
    }

    private void heap_down(double[] nums, int limit) {
        for (int child = 1; child < limit; child = 2 * child + 1) {
            if (child + 1 < limit) {
                events.add(new SortingEvent(SortingEventType.Comparison, child + 1, child));

                if (nums[child + 1] > nums[child]) {
                    child++;
                }
            }

            int parent = (child - 1) / 2;

            events.add(new SortingEvent(SortingEventType.Comparison, child, parent));

            if (nums[child] <= nums[parent]) {
                return;
            }

            swap(nums, child, parent);
            events.add(new SortingEvent(SortingEventType.Swap, child, parent));
        }
    }
}
