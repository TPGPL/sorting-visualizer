package pl.edu.pw.sortingvisualizer.animations;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static pl.edu.pw.sortingvisualizer.Properties.*;

/**
 * Reprezentacja animacji sortowania. Przechowuje elementarne zdarzenia w liście, po której można iterować.
 */
public class SortingAnimation implements Iterable<AnimationEvent> {
    /**
     * Zdarzenia zachodzące w animacji sortowania.
     */
    private final List<AnimationEvent> animations;

    /**
     * Konstruktor klasy.
     */
    public SortingAnimation() {
        animations = new ArrayList<>();
    }

    /**
     * Dodaje animację porównywania wartości elementów wektora.
     *
     * @param indices indeksy porównywanych elementów wektora
     */
    public void addComparisonAnimation(int... indices) {
        animations.add(new ColorChangeEvent(DEFAULT_COMPARISON_COLOR, indices));
        animations.add(new ColorChangeEvent(DEFAULT_BAR_COLOR, indices));
    }

    /**
     * Dodaje animację sprawdzenia poprawności sortowania wybranych elementów zakończonego niepowodzeniem.
     *
     * @param indices indeksy niepoprawnie posortowanych elementów
     */
    public void addFailedSortAnimation(int... indices) {
        animations.add(new ColorChangeEvent(DEFAULT_FAILED_COLOR, indices));
    }

    /**
     * Dodaje animację sprawdzenia poprawności sortowania wybranych elementów zakończonego pomyślnie.
     *
     * @param indices indeksy poprawnie posortowanych elementów
     */
    public void addSuccessfulSortAnimation(int... indices) {
        animations.add(new ColorChangeEvent(DEFAULT_SORTED_COLOR, indices));
    }

    /**
     * Dodaje animację nadpisania wartości elementu wektora.
     *
     * @param index    indeks modyfikowanego elementu
     * @param newValue nowa wartość elementu
     */
    public void addOverwriteAnimation(int index, double newValue) {
        animations.add(new ColorChangeEvent(DEFAULT_OVERWRITE_COLOR, index));
        animations.add(new ValueChangeEvent(new Pair<>(index, newValue)));
        animations.add(new ColorChangeEvent(DEFAULT_BAR_COLOR, index));
    }

    /**
     * Dodaje animację zamiany wartości dwóch elementów wektora.
     *
     * @param firstElem  indeks pierwszego elementu wraz z jego aktualną wartością
     * @param secondElem indeks drugiego elementu wraz z jego aktualną wartością
     */
    public void addSwapAnimation(Pair<Integer, Double> firstElem, Pair<Integer, Double> secondElem) {
        animations.add(new ColorChangeEvent(DEFAULT_SWAP_COLOR, firstElem.getKey(), secondElem.getKey()));
        animations.add(new ValueChangeEvent(new Pair<>(firstElem.getKey(), secondElem.getValue()), new Pair<>(secondElem.getKey(), firstElem.getValue())));
        animations.add(new ColorChangeEvent(DEFAULT_BAR_COLOR, firstElem.getKey(), secondElem.getKey()));
    }

    /**
     * Zwraca iterator po kolejnych elementarnych zdarzeniach w animacji.
     *
     * @return iterator po zdarzeniach animacji
     */
    @Override
    public Iterator<AnimationEvent> iterator() {
        return animations.iterator();
    }
}
