package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEventType;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class ShellSort implements VisualizableSorter {

    @Override
    public List<SortingEvent> sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        List<SortingEvent> events = new ArrayList<>();

        int gap = 1;

        while (gap <= nums.length / 9) {
            gap = 3 * gap + 1;
        }

        for (; gap >= 1; gap /= 3) {
            for (int i = gap; i < nums.length; i++) {
                int j = i - gap;

                while (j >= 0) {
                    events.add(new SortingEvent(SortingEventType.Comparison, j, j + gap));

                    if (nums[j] <= nums[j + gap])
                        break;

                    events.add(new SortingEvent(SortingEventType.Swap, j, j + gap));
                    swap(nums, j, j + gap);

                    j -= gap;
                }
            }
        }

        return events;
    }
}
