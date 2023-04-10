package pl.edu.pw.sortingvisualizer.animations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Klasa abstrakcyjna reprezentująca elementarne zdarzenie zachodzące podczas animacji sortowania.
 * Umożliwia iterowanie po indeksach elementów uczestniczących w zdarzeniu.
 */
public abstract class AnimationEvent implements Iterable<Integer> {
    /**
     * Indeksy elementów wektora uczestniczących w zdarzeniu animacji.
     */
    protected final List<Integer> indices;

    /**
     * Konstruktor klasy.
     */
    protected AnimationEvent() {
        this.indices = new ArrayList<>();
    }

    /**
     * Konstruktor klasy.
     *
     * @param indices indeksy elementów wektora uczestniczących w zdarzeniu
     */
    public AnimationEvent(int... indices) {
        this();

        for (int i : indices) {
            this.indices.add(i);
        }
    }

    /**
     * Zwraca iterator po indeksach elementów uczestniczących w zdarzeniu.
     *
     * @return iterator do indeksów elementów
     */
    @Override
    public Iterator<Integer> iterator() {
        return indices.iterator();
    }
}
