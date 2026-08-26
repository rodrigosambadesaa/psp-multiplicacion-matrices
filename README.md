# PSP · Multiplicación de matrices

Reimplementación del ejercicio `MultiplicacionMatrices`. La práctica original comparaba versión secuencial y una implementación por hilos. Esta versión corrige el cálculo de dimensiones para matrices rectangulares y limita el paralelismo mediante un `ExecutorService` en lugar de crear un hilo sin control por fila.

```bash
mvn verify
java -cp target/classes dev.rodrigosambade.matrix.MatrixDemo
```
