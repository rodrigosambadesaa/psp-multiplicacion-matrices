package dev.rodrigosambade.matrix;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
class MatrixMultiplierTest{@Test void handlesRectangularMatrices()throws Exception{int[][]a={{1,2,3},{4,5,6}},b={{7,8},{9,10},{11,12}},expected={{58,64},{139,154}};assertArrayEquals(expected,MatrixMultiplier.sequential(a,b));assertArrayEquals(expected,MatrixMultiplier.parallel(a,b,2));}}
