package pl.edu.pw.sortingvisualizer.animations;

import javafx.util.Pair;

import java.util.HashMap;

public class ValueChangeEvent extends AnimationEvent {
    private final HashMap<Integer, Double> newValues;

    @SafeVarargs
    public ValueChangeEvent(Pair<Integer, Double>... valueChanges) {
        super();

        newValues = new HashMap<>();

        for (Pair<Integer, Double> p : valueChanges) {
            indices.add(p.getKey());
            newValues.put(p.getKey(), p.getValue());
        }
    }

    public double getNewValueForIndex(int index) {
        if (!newValues.containsKey(index)) {
            throw new IllegalArgumentException("No new value available for given index.");
        }

        return newValues.get(index);
    }
}
