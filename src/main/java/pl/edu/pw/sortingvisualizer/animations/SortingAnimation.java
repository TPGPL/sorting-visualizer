package pl.edu.pw.sortingvisualizer.animations;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static pl.edu.pw.sortingvisualizer.Properties.*;

public class SortingAnimation implements Iterable<AnimationEvent> {
    private final List<AnimationEvent> animations;

    public SortingAnimation() {
        animations = new ArrayList<>();
    }

    public void addComparisonAnimation(int... indices) {
        animations.add(new ColorChangeEvent(DEFAULT_COMPARISON_COLOR, indices));
        animations.add(new ColorChangeEvent(DEFAULT_BAR_COLOR, indices));
    }

    public void addFailedSortAnimation(int... indices) {
        animations.add(new ColorChangeEvent(DEFAULT_FAILED_COLOR, indices));
    }

    public void addSuccessfulSortAnimation(int... indices) {
        animations.add(new ColorChangeEvent(DEFAULT_SORTED_COLOR, indices));
    }

    public void addOverwriteAnimation(int index, double newValue) {
        animations.add(new ColorChangeEvent(DEFAULT_OVERWRITE_COLOR, index));
        animations.add(new ValueChangeEvent(new Pair<>(index, newValue)));
        animations.add(new ColorChangeEvent(DEFAULT_BAR_COLOR, index));
    }

    public void addSwapAnimation(Pair<Integer, Double> firstElem, Pair<Integer, Double> secondElem) {
        animations.add(new ColorChangeEvent(DEFAULT_SWAP_COLOR, firstElem.getKey(), secondElem.getKey()));
        animations.add(new ValueChangeEvent(new Pair<>(firstElem.getKey(), secondElem.getValue()), new Pair<>(secondElem.getKey(), firstElem.getValue())));
        animations.add(new ColorChangeEvent(DEFAULT_BAR_COLOR, firstElem.getKey(), secondElem.getKey()));
    }

    @Override
    public Iterator<AnimationEvent> iterator() {
        return animations.iterator();
    }
}
