package pl.edu.pw.sortingvisualizer.animations;

import javafx.util.Pair;

import java.util.HashMap;

/**
 * Zdarzenie reprezentujące zmianę wartości wybranych elementów podczas animacji.
 */
public class ValueChangeEvent extends AnimationEvent {
    /**
     * Nowe wartości elementów wektora o określonych indeksach.
     */
    private final HashMap<Integer, Double> newValues;

    /**
     * Konstruktor klasy.
     *
     * @param valueChanges indeksy elementów wektora wraz z ich nowymi wartościami
     */
    @SafeVarargs
    public ValueChangeEvent(Pair<Integer, Double>... valueChanges) {
        super();

        newValues = new HashMap<>();

        for (Pair<Integer, Double> p : valueChanges) {
            indices.add(p.getKey());
            newValues.put(p.getKey(), p.getValue());
        }
    }

    /**
     * Zwraca nową wartość elementu o podanym indeksie.
     *
     * @param index indeks elementu
     * @return nowa wartośc elementu o podanym indeksie
     * @throws IllegalArgumentException jeżeli w zdarzeniu nie ma nowej wartości dla elementu o podanym indeksie
     */
    public double getNewValueForIndex(int index) {
        if (!newValues.containsKey(index)) {
            throw new IllegalArgumentException("No new value available for given index.");
        }

        return newValues.get(index);
    }
}
