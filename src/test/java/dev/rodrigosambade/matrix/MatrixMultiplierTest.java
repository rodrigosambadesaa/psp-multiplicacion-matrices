package dev.rodrigosambade.matrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatrixMultiplierTest {

    @Test
    void handlesRectangularMatrices() throws InterruptedException {
        int[][] left = {
                {1, 2, 3},
                {4, 5, 6}
        };
        int[][] right = {
                {7, 8},
                {9, 10},
                {11, 12}
        };
        int[][] expected = {
                {58, 64},
                {139, 154}
        };

        assertMatrixEquals(expected, MatrixMultiplier.sequential(left, right));
        assertMatrixEquals(expected, MatrixMultiplier.parallel(left, right, 2));
    }

    @Test
    void rejectsIncompatibleDimensions() {
        int[][] left = {{1, 2}};
        int[][] right = {{1, 2}};

        assertThrows(
                IllegalArgumentException.class,
                () -> MatrixMultiplier.sequential(left, right));
    }

    private static void assertMatrixEquals(int[][] expected, int[][] actual) {
        assertArrayEquals(expected, actual);
    }
}
