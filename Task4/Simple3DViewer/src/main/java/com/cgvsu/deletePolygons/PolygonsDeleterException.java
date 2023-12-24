package com.cgvsu.deletePolygons;

public class PolygonsDeleterException extends RuntimeException {

    public PolygonsDeleterException(String errorMessage) {
        super ("Deleting polygons error: " + errorMessage);
    }
}
