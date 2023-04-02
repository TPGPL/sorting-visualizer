package pl.edu.pw.sortingvisualizer.sorters;

import pl.edu.pw.sortingvisualizer.animations.SortingAnimation;

public interface VisualizableSorter {
    SortingAnimation sort(double[] nums);
}
