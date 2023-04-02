package pl.edu.pw.sortingvisualizer.animations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AnimationEvent implements Iterable<Integer> {
    protected final List<Integer> indices;

    protected AnimationEvent() {
        this.indices = new ArrayList<>();
    }

    public AnimationEvent(int... indices) {
        this();

        for (int i : indices) {
            this.indices.add(i);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return indices.iterator();
    }
}
