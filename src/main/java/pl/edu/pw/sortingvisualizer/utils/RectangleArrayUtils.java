package pl.edu.pw.sortingvisualizer.utils;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Funkcjonalności pomocnicze wykorzystywane do manipulowania wektorami obiektów Rectangle.
 */
public class RectangleArrayUtils {
    /**
     * Przekształca wektor liczb rzeczywistych na wektor obiektów Rectangle.
     *
     * @param nums      wektor liczb rzeczywistych
     * @param areaWidth szerokość całego obszaru przeznaczonego na wizualizację wektora
     * @return wektor obiektów Rectangle
     */
    public static Rectangle[] convertDoubleToRectangleArray(double[] nums, double areaWidth) {
        if (nums == null) {
            throw new IllegalArgumentException("The array must not be null.");
        }

        Rectangle[] rectArray = new Rectangle[nums.length];
        double elemWidth = nums.length != 0 ? areaWidth / nums.length : 0;

        for (int i = 0; i < nums.length; i++) {
            rectArray[i] = new Rectangle(elemWidth, nums[i], Color.BLACK);
        }

        return rectArray;
    }
}
