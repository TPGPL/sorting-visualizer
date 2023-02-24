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
            events.add(new SortingEvent(SortingEventType.Comparison, start, (start - 1) / 2));

            if (nums[start] <= nums[(start - 1) / 2]) {
                return;
            }

            events.add(new SortingEvent(SortingEventType.Swap, start, (start - 1) / 2));
            swap(nums, start, (start - 1) / 2);

            start = (start - 1) / 2;
        }
    }

    private void heap_down(double[] nums, int limit) {
        int ch = 1;

        while (ch < limit) {
            if (ch + 1 < limit) {
                events.add(new SortingEvent(SortingEventType.Comparison, ch + 1, ch));

                if (nums[ch + 1] > nums[ch]) {
                    ch++;
                }
            }

            events.add(new SortingEvent(SortingEventType.Comparison, ch, (ch - 1) / 2));

            if (nums[ch] <= nums[(ch - 1) / 2]) {
                return;
            }

            swap(nums, ch, (ch - 1) / 2);
            events.add(new SortingEvent(SortingEventType.Swap, ch, (ch - 1) / 2));

            ch = 2 * ch + 1;
        }
    }
}
