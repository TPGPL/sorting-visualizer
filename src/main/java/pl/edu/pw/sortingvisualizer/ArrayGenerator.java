package pl.edu.pw.sortingvisualizer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class ArrayGenerator {
    public static double[] generateArray(int length, double upperBound) {
        if (length <= 0 || upperBound <= 0) {
            throw new IllegalArgumentException("The params (length, upperBound) must be positive.");
        }

        double[] nums = new double[length];
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            nums[i] = rand.nextDouble() * upperBound;
        }

        return nums;
    }

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
