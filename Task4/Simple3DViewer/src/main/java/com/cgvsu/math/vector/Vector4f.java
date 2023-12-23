package com.cgvsu.math.vector;

public class Vector4f {
    private double x;
    private double y;
    private double z;
    private double w;
    private static double eps = 1e-4;

    public Vector4f(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double get(int index) {
        switch (index){
            case 0: return x;
            case 1: return y;
            case 2: return z;
            case 3: return w;
        }
        throw new IllegalArgumentException("Индекс выходит за границы");
    }
    // Сложение векторов
    public Vector4f add(Vector4f other) {
        return new Vector4f(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w);
    }

    // Вычитание векторов
    public Vector4f subtract(Vector4f other) {
        return new Vector4f(this.x - other.x, this.y - other.y, this.z - other.z, this.w - other.w);
    }

    // Умножение на скаляр
    public Vector4f multiply(double scalar) {
        return new Vector4f(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }

    // Деление на скаляр
    public Vector4f divide(double scalar) {
        if (Math.abs(scalar) < eps) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Vector4f(this.x / scalar, this.y / scalar, this.z / scalar, this.w / scalar);
    }

    // Вычисление длины вектора
    public double length() {
        return Math.sqrt(x * x + y * y + z * z + w * w);
    }

    // Нормализация вектора
    public Vector4f normalize() {
        double length = length();
        if (Math.abs(length) < eps) {
            return new Vector4f(0, 0, 0, 0);
        }
        return new Vector4f(x / length, y / length, z / length, w / length);
    }

    // Скалярное произведение векторов
    public double dotProduct(Vector4f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }
}
