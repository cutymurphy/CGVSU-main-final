package com.cgvsu.triangulation;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;

public class TriangulatedModel {
    private final Model initialModel;
    private final ArrayList<Polygon> triangulatedPolygons;

    public TriangulatedModel(Model initialModel) {
        this.initialModel = initialModel;
        this.triangulatedPolygons = triangulatePolygons(initialModel.polygons);
    }

    public Model getInitialModel() {
        return initialModel;
    }

    public ArrayList<Polygon> getTriangulatedPolygons() {
        return triangulatedPolygons;
    }

    public static ArrayList<Polygon> triangulatePolygons(ArrayList<Polygon> initialPolygons) {
        ArrayList<Polygon> triangulationPolygons = new ArrayList<>();

        for (Polygon polygon : initialPolygons) {
            triangulationPolygons.addAll(Triangulation.triangulation(polygon));
        }

        return triangulationPolygons;
    }
}
