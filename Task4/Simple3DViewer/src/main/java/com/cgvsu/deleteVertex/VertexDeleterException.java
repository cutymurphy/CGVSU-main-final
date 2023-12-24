package com.cgvsu.deleteVertex;

public class VertexDeleterException extends RuntimeException {
    public VertexDeleterException(String errorMessage) {
        super ("Deleting vertices error: " + errorMessage);
    }
}
