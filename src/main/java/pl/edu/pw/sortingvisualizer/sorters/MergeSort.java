package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;
import pl.edu.pw.sortingvisualizer.sortingevent.SortingEventType;

import java.util.ArrayList;
import java.util.List;

public class MergeSort implements VisualizableSorter {
    private final List<SortingEvent> events;

    public MergeSort() {
        events = new ArrayList<>();
    }

    @Override
    public List<SortingEvent> sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("The nums array must not be null.");
        }

        events.clear();

        int length = nums.length;
        double[] tmp = new double[length];
        double[] src = nums;
        double[] dst = tmp;

        for (int l = 1; l < length; l *= 2) {
            for (int i = 0; i < length; i += 2 * l) {
                merge(src, i, (Math.min(i + l, length)), (Math.min(i + 2 * l, length)), dst);
            }

            tmp = src;
            src = dst;
            dst = tmp;
        }

        if (src != nums) {
            System.arraycopy(src, 0, nums, 0, length);
        }

        return events;
    }

    private void merge(double[] src, int start, int mid, int end, double[] dst) {
        int i = start;
        int j = mid;
        int k = start;

        while (i < mid && j < end) {
            events.add(new SortingEvent(SortingEventType.Comparison, i, j));

            if (src[i] <= src[j]) {
                events.add(new SortingEvent(SortingEventType.Overwrite, k, src[i]));

                dst[k++] = src[i++];
            } else {
                events.add(new SortingEvent(SortingEventType.Overwrite, k, src[j]));

                dst[k++] = src[j++];
            }
        }

        while (i < mid) {
            events.add(new SortingEvent(SortingEventType.Overwrite, k, src[i]));

            dst[k++] = src[i++];
        }

        while (j < end) {
            events.add(new SortingEvent(SortingEventType.Overwrite, k, src[j]));

            dst[k++] = src[j++];
        }
    }
}
