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

    public static void swapRectangles(Rectangle[] rectArray, int firstIndex, int secondIndex) {
        if (firstIndex != secondIndex) {
            Rectangle tmp = rectArray[firstIndex];
            rectArray[firstIndex] = rectArray[secondIndex];
            rectArray[secondIndex] = tmp;
        }
    }

    public static void recolorRectangles(Rectangle[] rectArray, Color color, int... indices) {
        for (int i : indices) {
            rectArray[i].setFill(color);
        }
    }
}
