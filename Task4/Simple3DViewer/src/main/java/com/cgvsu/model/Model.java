package com.cgvsu.model;


import javax.vecmath.*;
import java.util.*;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    public ArrayList<Vector3f> getVertices() {
        return vertices;
    }

    public ArrayList<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public ArrayList<Vector3f> getNormals() {
        return normals;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }

    public Model getCopy() {
        Model copiedModel = new Model();
        // Копируем вершины
        for (Vector3f vertex : this.vertices) {
            copiedModel.vertices.add(new Vector3f(vertex));
        }
        // Копируем нормали
        for (Vector3f normal : this.normals) {
            copiedModel.normals.add(new Vector3f(normal));
        }
        // Копируем полигоны
        for (Polygon polygon : this.polygons) {
            Polygon copiedPolygon = new Polygon();
            copiedPolygon.setVertexIndices(new ArrayList<>(polygon.getVertexIndices()));
            copiedPolygon.setTextureVertexIndices(new ArrayList<>(polygon.getTextureVertexIndices()));
            copiedPolygon.setNormalIndices(new ArrayList<>(polygon.getNormalIndices()));
            copiedModel.polygons.add(copiedPolygon);
        }
        return copiedModel;
    }
}
