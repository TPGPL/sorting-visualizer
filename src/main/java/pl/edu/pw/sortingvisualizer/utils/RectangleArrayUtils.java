package pl.edu.pw.sortingvisualizer.utils;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleArrayUtils {
    public static Rectangle[] convertDoubleToRectangleArray(double[] nums, double areaWidth) {
        if (nums == null) {
            throw new IllegalArgumentException("The array must not be null.");
        }

        Rectangle[] rectArray = new Rectangle[nums.length];
        double width = nums.length != 0 ? areaWidth / nums.length : 0;

        for (int i = 0; i < nums.length; i++) {
            rectArray[i] = new Rectangle(width, nums[i], Color.BLACK);
        }

        return rectArray;
    }
}
