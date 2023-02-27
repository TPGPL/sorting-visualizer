package pl.edu.pw.sortingvisualizer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class ArrayGenerator {
    public static double[] generateArray(int count, double upperBound) {
        if (count <= 0 || upperBound <= 0) {
            throw new IllegalArgumentException("The params (count, upperBound) must be positive");
        }

        double[] arr = new double[count];
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            arr[i] = rand.nextDouble() * upperBound;
        }

        return arr;
    }

    public static Rectangle[] convertDoubleToRectangleArray(double[] arr, double maxWidth) {
        if (arr == null) {
            throw new IllegalArgumentException("The array must not be null.");
        }

        Rectangle[] rectArray = new Rectangle[arr.length];
        double width = arr.length != 0 ? maxWidth / arr.length : 0;

        for (int i = 0; i < arr.length; i++) {
            rectArray[i] = new Rectangle(width, arr[i], Color.BLACK);
        }

        return rectArray;
    }
}
