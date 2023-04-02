package pl.edu.pw.sortingvisualizer.animations;

import javafx.scene.paint.Color;

public class ColorChangeEvent extends AnimationEvent {
    private final Color newColor;

    public ColorChangeEvent(Color newColor, int... indices) {
        super(indices);

        this.newColor = newColor;
    }

    public Color getNewColor() {
        return newColor;
    }
}
