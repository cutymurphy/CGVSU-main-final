package src.main.java.ru.vsu.cs.cg.rasterizerdemo.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import ru.vsu.cs.cg.rasterizerdemo.drawers.CoordinatesDrawer;
import ru.vsu.cs.cg.rasterizerdemo.drawers.TriangleSamplesDrawer;

public class SamplesDemoController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        CoordinatesDrawer coordinatesDrawer = new CoordinatesDrawer(
                canvas.getGraphicsContext2D(), (int) canvas.getWidth(), (int) canvas.getHeight());
        coordinatesDrawer.drawCoordinates();

        TriangleSamplesDrawer triangleSamplesDrawer = new TriangleSamplesDrawer();
        triangleSamplesDrawer.drawTriangles(canvas.getGraphicsContext2D().getPixelWriter(),
                coordinatesDrawer.getLeftXIndent(), coordinatesDrawer.getTopYIndent());
    }
}