package src.main.java.ru.vsu.cs.cg.rasterizerdemo.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import ru.vsu.cs.cg.rasterizerdemo.rasterizer.Triangle;
import ru.vsu.cs.cg.rasterizerdemo.rasterizer.TriangleRasterizer;

public class RasterizerController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        TriangleRasterizer rasterizer = new TriangleRasterizer(canvas.getGraphicsContext2D().getPixelWriter());
        long start = System.currentTimeMillis();
        rasterizer.rasterize(new Triangle(
                700, 100, Color.RED,
                400, 500, Color.LIME,
                1100 ,700, Color.BLUE
        ));
        System.out.println("Time=" + (System.currentTimeMillis() - start));
    }
}
