package com.cgvsu.math.matrix;

import com.cgvsu.math.vector.Vector4f;

public class Matrix4f {
    private double[][] matrix = new double[4][4];

    public Matrix4f(double[][] data) {
        if (data.length != 4 || data[0].length != 4) {
            throw new IllegalArgumentException("Матрица должна быть 4x4");
        }
        this.matrix = data;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    // Сложение матриц
    public Matrix4f add(Matrix4f other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return new Matrix4f(result);
    }

    // Вычитание матриц
    public Matrix4f subtract(Matrix4f other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return new Matrix4f(result);
    }

    // Умножение матрицы на вектор4Д (теперь вектор - столбец)
    public Vector4f multiply(Vector4f vector) {
        if (vector == null) {
            throw new NullPointerException("Вектор не может быть нулевым");
        }
        double[] result = new double[4];
        for (int i = 0; i < 4; i++) {
            result[i] = 0;
            for (int j = 0; j < 4; j++) {
                result[i] += this.matrix[j][i] * vector.get(j);
            }
        }
        return new Vector4f(result[0], result[1], result[2], result[3]);
    }

    // Умножение на матрицу
    public Matrix4f multiply(Matrix4f other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return new Matrix4f(result);
    }

    // Транспонирование
    public Matrix4f transpose() {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.matrix[j][i];
            }
        }
        return new Matrix4f(result);
    }

    // Задание единичной матрицы
    public static Matrix4f identity() {
        double[][] identityMatrix = new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4f(identityMatrix);
    }

    // Задание нулевой матрицы
    public static Matrix4f zero() {
        double[][] zeroMatrix = new double[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        return new Matrix4f(zeroMatrix);
    }
    public double determinate(){
        double[][] data1 = new double[3][3];
        double[][] data2 = new double[3][3];
        double[][] data3 = new double[3][3];
        double[][] data4 = new double[3][3];

        for(int i = 1; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if (j != 0){
                    data1[i-1][j-1] = matrix[i][j];
                }
                if (j != 1){
                    if (j==0) {
                        data2[i-1][j] = matrix[i][j];
                    }else{
                        data2[i-1][j-1] = matrix[i][j];
                    }
                }
                if (j != 2){
                    if (j==0 || j==1) {
                        data3[i-1][j] = matrix[i][j];
                    }else{
                        data3[i-1][j-1] = matrix[i][j];
                    }
                }
                if (j != 3){
                    data4[i-1][j] = matrix[i][j];
                }
            }
        }

        Matrix3f m1 = new Matrix3f(data1);
        Matrix3f m2 = new Matrix3f(data2);
        Matrix3f m3 = new Matrix3f(data3);
        Matrix3f m4 = new Matrix3f(data4);

        return (matrix[0][0]*m1.determinate()-matrix[0][1]*m2.determinate()+matrix[0][2]*m3.determinate()-matrix[0][3]*m4.determinate());
    }
}
