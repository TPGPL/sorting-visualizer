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

        double[] tmp = new double[nums.length];
        double[] src = nums;
        double[] dst = tmp;

        int n = nums.length;

        for (int l = 1; l < n; l *= 2) {
            for (int i = 0; i < n; i += 2 * l) {
                merge(src, i, (Math.min(i + l, n)), (Math.min(i + 2 * l, n)), dst);
            }

            tmp = src;
            src = dst;
            dst = tmp;
        }

        if (src != nums) {
            System.arraycopy(src, 0, nums, 0, n);
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
