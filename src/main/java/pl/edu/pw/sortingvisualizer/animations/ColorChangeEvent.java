package pl.edu.pw.sortingvisualizer.animations;

import javafx.scene.paint.Color;

/**
 * Zdarzenie reprezentujące zmianę koloru wybranych elementów podczas animacji.
 */
public class ColorChangeEvent extends AnimationEvent {
    /**
     * Nowy kolor elementów wektora.
     */
    private final Color newColor;

    /**
     * Konstruktor klasy.
     *
     * @param newColor nowy kolor elementów wektora
     * @param indices  indeksy elementów wektora uczestniczących w zdarzeniu
     */
    public ColorChangeEvent(Color newColor, int... indices) {
        super(indices);

        this.newColor = newColor;
    }

    /**
     * Zwraca nowy kolor elementów wektora.
     *
     * @return nowy kolor elementów wektora
     */
    public Color getNewColor() {
        return newColor;
    }
}
