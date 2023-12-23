package com.cgvsu.math.vector;

public class Vector2f {
    private double x;
    private double y;
    private static double eps = 1e-4;

    public Vector2f(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double get(int index) {
        switch (index){
            case 0: return x;
            case 1: return y;
        }
    throw new IllegalArgumentException("Индекс выходит за границы");
    }

    // Сложение векторов
    public Vector2f add(Vector2f other) {
        return new Vector2f(this.x + other.x, this.y + other.y);
    }

    // Вычитание векторов
    public Vector2f subtract(Vector2f other) {
        return new Vector2f(this.x - other.x, this.y - other.y);
    }

    // Умножение на скаляр
    public Vector2f multiply(double scalar) {
        return new Vector2f(this.x * scalar, this.y * scalar);
    }

    // Деление на скаляр
    public Vector2f divide(double scalar) {
        if (Math.abs(scalar) < eps) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Vector2f(this.x / scalar, this.y / scalar);
    }

    // Вычисление длины вектора
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    // Нормализация вектора
    public Vector2f normalize() {
        double len = length();
        if (Math.abs(len) < eps) {
            return new Vector2f(0, 0);
        }
        return new Vector2f(x / len, y / len);
    }

    // Скалярное произведение векторов
    public double dotProduct(Vector2f other) {
        return this.x * other.x + this.y * other.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static double getEps() {
        return eps;
    }
}
