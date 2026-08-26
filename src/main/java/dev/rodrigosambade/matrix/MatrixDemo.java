package dev.rodrigosambade.matrix;

import java.util.Arrays;

public final class MatrixDemo {

    private MatrixDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        int[][] left = {
                {1, 2, 3},
                {4, 5, 6}
        };
        int[][] right = {
                {7, 8},
                {9, 10},
                {11, 12}
        };

        int[][] result = MatrixMultiplier.parallel(left, right, 2);
        System.out.println(Arrays.deepToString(result));
    }
}
