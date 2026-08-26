package dev.rodrigosambade.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class MatrixMultiplier {

    private MatrixMultiplier() {
    }

    public static int[][] sequential(int[][] left, int[][] right) {
        validateCompatibility(left, right);

        int[][] result = new int[left.length][right[0].length];
        for (int row = 0; row < left.length; row++) {
            computeRow(left, right, result, row);
        }
        return result;
    }

    public static int[][] parallel(int[][] left, int[][] right, int parallelism)
            throws InterruptedException {
        validateCompatibility(left, right);
        if (parallelism <= 0) {
            throw new IllegalArgumentException("parallelism must be greater than zero");
        }

        int[][] result = new int[left.length][right[0].length];
        int workerCount = Math.min(parallelism, left.length);

        try (ExecutorService executor = Executors.newFixedThreadPool(workerCount)) {
            List<Future<?>> tasks = submitRows(left, right, result, executor);
            waitForCompletion(tasks);
        }

        return result;
    }

    private static List<Future<?>> submitRows(
            int[][] left,
            int[][] right,
            int[][] result,
            ExecutorService executor) {
        List<Future<?>> tasks = new ArrayList<>(left.length);

        for (int row = 0; row < left.length; row++) {
            int rowIndex = row;
            tasks.add(executor.submit(() -> computeRow(left, right, result, rowIndex)));
        }

        return tasks;
    }

    private static void waitForCompletion(List<Future<?>> tasks) throws InterruptedException {
        for (Future<?> task : tasks) {
            try {
                task.get();
            } catch (ExecutionException exception) {
                throw new IllegalStateException("Matrix worker failed", exception.getCause());
            }
        }
    }

    private static void computeRow(int[][] left, int[][] right, int[][] result, int row) {
        for (int column = 0; column < right[0].length; column++) {
            int value = 0;

            for (int index = 0; index < left[0].length; index++) {
                int product = Math.multiplyExact(left[row][index], right[index][column]);
                value = Math.addExact(value, product);
            }

            result[row][column] = value;
        }
    }

    private static void validateCompatibility(int[][] left, int[][] right) {
        Dimensions leftDimensions = dimensionsOf(left, "left");
        Dimensions rightDimensions = dimensionsOf(right, "right");

        if (leftDimensions.columns() != rightDimensions.rows()) {
            throw new IllegalArgumentException("Left matrix columns must equal right matrix rows");
        }
    }

    private static Dimensions dimensionsOf(int[][] matrix, String name) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            throw new IllegalArgumentException(name + " matrix must not be empty");
        }

        int columns = matrix[0].length;
        for (int row = 0; row < matrix.length; row++) {
            if (matrix[row] == null || matrix[row].length != columns) {
                throw new IllegalArgumentException(name + " matrix must be rectangular");
            }
        }

        return new Dimensions(matrix.length, columns);
    }

    private record Dimensions(int rows, int columns) {
    }
}
