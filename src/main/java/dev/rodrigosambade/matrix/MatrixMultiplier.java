package dev.rodrigosambade.matrix;
import java.util.*;
import java.util.concurrent.*;
public final class MatrixMultiplier{
 private MatrixMultiplier(){}
 private static int[]dimensions(int[][]m){if(m==null||m.length==0||m[0].length==0)throw new IllegalArgumentException("empty matrix");int cols=m[0].length;for(int[]row:m)if(row.length!=cols)throw new IllegalArgumentException("ragged matrix");return new int[]{m.length,cols};}
 private static void validate(int[][]a,int[][]b){int[]da=dimensions(a),db=dimensions(b);if(da[1]!=db[0])throw new IllegalArgumentException("A columns must equal B rows");}
 public static int[][]sequential(int[][]a,int[][]b){validate(a,b);int[][]c=new int[a.length][b[0].length];for(int i=0;i<a.length;i++)computeRow(a,b,c,i);return c;}
 public static int[][]parallel(int[][]a,int[][]b,int parallelism)throws InterruptedException{validate(a,b);if(parallelism<=0)throw new IllegalArgumentException("parallelism");int[][]c=new int[a.length][b[0].length];try(var executor=Executors.newFixedThreadPool(Math.min(parallelism,a.length))){List<Future<?>>futures=new ArrayList<>();for(int i=0;i<a.length;i++){int row=i;futures.add(executor.submit(()->computeRow(a,b,c,row)));}for(Future<?>f:futures)try{f.get();}catch(ExecutionException e){throw new IllegalStateException(e.getCause());}}return c;}
 private static void computeRow(int[][]a,int[][]b,int[][]c,int i){for(int j=0;j<b[0].length;j++){int sum=0;for(int k=0;k<a[0].length;k++)sum=Math.addExact(sum,Math.multiplyExact(a[i][k],b[k][j]));c[i][j]=sum;}}
}
