package com.cgvsu.math.matrix;

import com.cgvsu.math.vector.Vector3f;

public class Matrix3f {

    private double[][] matrix = new double[3][3];

    public Matrix3f(double[][] data) {
        if (data.length != 3 || data[0].length != 3) {
            throw new IllegalArgumentException("Матрица должна быть 3x3");
        }
        this.matrix = data;
    }

    public double[][] getMatrix() {
        return matrix;
    }
    // Сложение матриц
    public Matrix3f add(Matrix3f other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return new Matrix3f(result);
    }

    // Вычитание матриц
    public Matrix3f subtract(Matrix3f other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return new Matrix3f(result);
    }


    // Умножение матрицы на матрицу (теперь векторы - столбцы)
    public Matrix3f multiply(Matrix3f other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    result[i][j] += this.matrix[k][j] * other.matrix[i][k];
                }
            }
        }
        return new Matrix3f(result);
    }

    // Транспонирование
    public Matrix3f transpose() {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[j][i];
            }
        }
        return new Matrix3f(result);
    }

    // Задание единичной матрицы
    public static Matrix3f identity() {
        double[][] identityMatrix = new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        return new Matrix3f(identityMatrix);
    }

    // Задание нулевой матрицы
    public static Matrix3f zero() {
        double[][] zeroMatrix = new double[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        return new Matrix3f(zeroMatrix);
    }
    public double determinate(){
        return (matrix[0][0]*matrix[1][1]*matrix[2][2]-(matrix[0][2]*matrix[1][1]*matrix[2][0]) +matrix[0][1]*matrix[1][2]*matrix[2][0]-(matrix[0][1]*matrix[1][0]*matrix[2][2]) +matrix[0][2]*matrix[1][0]*matrix[2][1]-(matrix[0][0]*matrix[1][2]*matrix[2][1]));
    }
}
