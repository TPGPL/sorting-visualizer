package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEventType;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.pw.sortingvisualizer.sorters.SortingUtils.swap;

public class CocktailShakerSort implements VisualizableSorter {

    @Override
    public List<SortingEvent> sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        List<SortingEvent> events = new ArrayList<>();
        int left = 0;
        int right = nums.length - 1;
        boolean hasSwapped;

        do {
            hasSwapped = false;

            for (int i = left; i < right; i++) {
                events.add(new SortingEvent(SortingEventType.Comparison, i, i + 1));

                if (nums[i] > nums[i + 1]) {
                    events.add(new SortingEvent(SortingEventType.Swap, i, i + 1));
                    swap(nums, i, i + 1);

                    hasSwapped = true;
                }
            }

            right--;

            for (int i = right; i > left; i--) {
                events.add(new SortingEvent(SortingEventType.Comparison, i, i - 1));

                if (nums[i - 1] > nums[i]) {
                    events.add(new SortingEvent(SortingEventType.Swap, i, i - 1));
                    swap(nums, i, i - 1);

                    hasSwapped = true;
                }
            }

            left++;
        } while (hasSwapped);

        return events;
    }
}
