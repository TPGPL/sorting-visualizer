package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.sortingevent.SortingEvent;

import java.util.List;

public interface VisualizableSorter {
    List<SortingEvent> sort(double[] nums);
}
