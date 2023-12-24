package com.cgvsu.deletePolygons;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.TreeSet;

public class PolygonsDeleter {

    public static void deletePolygons(Model model, int... deletedPolygonIndices) {
        if (model == null) {
            throw new IllegalArgumentException("Model is null");
        }

        ArrayList<Polygon> polygons = model.polygons;
        TreeSet<Integer> set = new TreeSet<>();

        for (int deletedPolygonIndex : deletedPolygonIndices) {
            set.add(deletedPolygonIndex);
        }

        for (int i = set.size() - 1; i >= 0; i--) {
            int index = deletedPolygonIndices[i];
            if (index < 0 || index >= polygons.size()) {
                throw new IllegalArgumentException("Incorrect polygon index");
            }
            polygons.remove(index);
        }
    }
}
