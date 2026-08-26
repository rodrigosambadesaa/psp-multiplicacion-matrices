package dev.rodrigosambade.matrix;
import java.util.Arrays;
public final class MatrixDemo{public static void main(String[]args)throws Exception{int[][]a={{1,2,3},{4,5,6}},b={{7,8},{9,10},{11,12}};System.out.println(Arrays.deepToString(MatrixMultiplier.parallel(a,b,2)));}}
