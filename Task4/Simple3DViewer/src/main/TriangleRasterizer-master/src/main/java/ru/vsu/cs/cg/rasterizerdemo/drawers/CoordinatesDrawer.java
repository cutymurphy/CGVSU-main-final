package src.main.java.ru.vsu.cs.cg.rasterizerdemo.drawers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CoordinatesDrawer {
    private final GraphicsContext graphicsContext;

    private final int leftXIndent;
    private final int topYIndent;

    private final int coordinatesWidth;
    private final int coordinatesHeight;

    public CoordinatesDrawer(GraphicsContext graphicsContext, int canvasWidth, int canvasHeight,
                             int leftXIndent, int rightXIndent, int topYIndent, int bottomYIndent) {
        this.graphicsContext = graphicsContext;

        this.leftXIndent = leftXIndent;
        this.topYIndent = topYIndent;

        this.coordinatesWidth = canvasWidth - (leftXIndent + rightXIndent);
        this.coordinatesHeight = canvasHeight - (topYIndent + bottomYIndent);
    }

    public CoordinatesDrawer(GraphicsContext graphicsContext, int canvasWidth, int canvasHeight) {
        this(graphicsContext, canvasWidth, canvasHeight,
                50, 25, 50, 25);
    }

    public void drawCoordinates() {
        graphicsContext.strokeLine(leftXIndent, topYIndent, leftXIndent + coordinatesWidth, topYIndent);
        graphicsContext.strokeLine(leftXIndent, topYIndent, leftXIndent, topYIndent + coordinatesHeight);

        final int gap = 100;
        final int markLength = 10;
        Paint defaultStroke = graphicsContext.getStroke();
        Paint greyStroke = Color.LIGHTGRAY;
        for (int x = leftXIndent + gap; x < leftXIndent + coordinatesWidth; x += gap) {
            graphicsContext.setStroke(defaultStroke);
            graphicsContext.strokeLine(x, topYIndent, x, topYIndent - markLength);
            graphicsContext.fillText(String.valueOf(x - leftXIndent), x - 10, topYIndent - 15);

            graphicsContext.setStroke(greyStroke);
            graphicsContext.strokeLine(x, topYIndent, x, topYIndent + coordinatesHeight);
        }
        for (int y = topYIndent + gap; y < topYIndent + coordinatesHeight; y += gap) {
            graphicsContext.setStroke(defaultStroke);
            graphicsContext.strokeLine(leftXIndent, y, leftXIndent - markLength, y);
            graphicsContext.fillText(String.valueOf(y - topYIndent), leftXIndent - 40, y + 5);

            graphicsContext.setStroke(greyStroke);
            graphicsContext.strokeLine(leftXIndent, y, leftXIndent + coordinatesWidth, y);
        }
    }

    public int getLeftXIndent() {
        return leftXIndent;
    }

    public int getTopYIndent() {
        return topYIndent;
    }
}
