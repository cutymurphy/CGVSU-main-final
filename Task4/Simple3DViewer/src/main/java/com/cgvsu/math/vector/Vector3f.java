package com.cgvsu.math.vector;

import java.util.ArrayList;

public class Vector3f {
    public double x;
    public double y;
    public double z;
    private static double eps = 1e-4;

    public Vector3f(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f() {

    }

    public Vector3f(Vector3f vector3f) {
        this.x = vector3f.x;
        this.y = vector3f.y;
        this.z = vector3f.z;
    }

    public double get(int index) {
        switch (index){
            case 0: return x;
            case 1: return y;
            case 2: return z;
        }
        throw new IllegalArgumentException("Индекс выходит за границы");
    }

    // Сложение векторов
    public Vector3f add(Vector3f other) {
        return new Vector3f(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    // Вычитание векторов
    public Vector3f subtract(Vector3f other) {
        return new Vector3f(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    // Умножение на скаляр
    public Vector3f multiply(double scalar) {
        return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    // Деление на скаляр
    public Vector3f divide(double scalar) {
        if (Math.abs(scalar) < eps) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Vector3f(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    // Вычисление длины вектора
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    // Нормализация вектора
    public Vector3f normalize() {
        double len = length();
        if (Math.abs(len) < eps) {
            return new Vector3f(0, 0, 0);
        }
        return new Vector3f(x / len, y / len, z / len);
    }

    // Скалярное произведение векторов
    public double dotProduct(Vector3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    // Векторное произведение векторов
    public Vector3f crossProduct(Vector3f first, Vector3f other) {
        return new Vector3f(
                first.y * other.z - first.z * other.y,
                first.z * other.x - first.x * other.z,
                first.x * other.y - first.y * other.x
        );
    }

    public static Vector3f fromTwoPoints(Vector3f first, Vector3f second) {
        return new Vector3f(second.x - first.x,
                second.y - first.y,
                second.z - first.z);
    }

    // Сложение векторов
    public static Vector3f sum(ArrayList<Vector3f> vectors) {
        Vector3f sum = new Vector3f();
        for (Vector3f vector : vectors) {
            sum = sum.add(vector);
        }
        return sum;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static double getEps() {
        return eps;
    }
}
