package com.cgvsu.triangulation;

import com.cgvsu.math.vector.*;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.*;

public class Triangulation {

    public static ArrayList<Polygon> triangulation(Polygon polygon) {
        ArrayList<Polygon> triangularPolygons = new ArrayList<>();

        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
        int quantityVertexes = vertexIndices.size();

        ArrayList<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
        checkForCorrectListSize(textureVertexIndices, quantityVertexes, "текстурных координат");

        ArrayList<Integer> normalIndices = polygon.getNormalIndices();
        checkForCorrectListSize(normalIndices, quantityVertexes, "нормалей");


        for (int index = 1; index < vertexIndices.size() - 1; index++) {
            ArrayList<Integer> threeVertexIndices = getIndicesListForCurrentPolygon(vertexIndices, index);
            ArrayList<Integer> threeTextureVertexIndices = getIndicesListForCurrentPolygon(textureVertexIndices, index);
            ArrayList<Integer> threeNormalIndices = getIndicesListForCurrentPolygon(normalIndices, index);

            Polygon triangularPolygon = new Polygon();
            triangularPolygon.setVertexIndices(threeVertexIndices);
            triangularPolygon.setTextureVertexIndices(threeTextureVertexIndices);
            triangularPolygon.setNormalIndices(threeNormalIndices);

            triangularPolygons.add(triangularPolygon);
        }

        return triangularPolygons;
    }

    private static void checkForCorrectListSize(List<Integer> list, int expectedSize, String listName) {
        if (list.size() != 0 && list.size() != expectedSize) {
            throw new IllegalArgumentException("Некорректное количество " + listName + " в полигоне");
        }
    }

    private static ArrayList<Integer> getIndicesListForCurrentPolygon(ArrayList<Integer> list, int indexSecondVertex) {
        ArrayList<Integer> indices = new ArrayList<>();

        if (list.size() != 0) {
            indices.add(list.get(0));
            indices.add(list.get(indexSecondVertex));
            indices.add(list.get(indexSecondVertex + 1));
        }

        return indices;
    }

    /*public static void recalculateNormals(Model model) {
        model.normals.clear();

        for (int i = 0; i < model.vertices.size(); i++) {
            model.normals.add(calculateNormalForVertexInModel(model, i));
        }
    }*/

    protected static Vector3f calculateNormalForPolygon(final Polygon polygon, final Model model){

        List<Integer> vertexIndices = polygon.getVertexIndices();
        int verticesCount = vertexIndices.size();

        Vector3f vector1 = fromTwoPoints(model.vertices.get(vertexIndices.get(0)), model.vertices.get(vertexIndices.get(1)));
        Vector3f vector2 = fromTwoPoints(model.vertices.get(vertexIndices.get(0)), model.vertices.get(vertexIndices.get(verticesCount - 1)));

        Vector3f resultVector = new Vector3f();
        resultVector = crossProduct(vector1, vector2);
        return resultVector;
    }

    protected static Vector3f calculateNormalForVertexInModel(final Model model, final int vertexIndex) {
        ArrayList   <Vector3f> saved = new ArrayList<>();

        for (Polygon polygon : model.polygons) {
            if (polygon.getVertexIndices().contains(vertexIndex)) {
                Vector3f polygonNormal = calculateNormalForPolygon(polygon, model);
                if (polygonNormal.length() > 0) {
                    saved.add(polygonNormal);
                }
            }
        }

        if (saved.isEmpty()) {
            return new Vector3f();
        }

        return divide(sum(saved),(saved.size()));
    }

    private static double eps = 1e-4;

    public static Vector3f divide(Vector3f vector3f, float scalar) {
        if (Math.abs(scalar) < eps) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Vector3f(vector3f.x / scalar, vector3f.y / scalar, vector3f.z / scalar);
    }

    // Векторное произведение векторов
    public static Vector3f crossProduct(Vector3f first, Vector3f other) {
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
            sum = add(sum, vector);
        }
        return sum;
    }

    public static Vector3f add(Vector3f first, Vector3f other) {
        return new Vector3f(first.x + other.x, first.y + other.y, first.z + other.z);
    }


}