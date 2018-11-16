package com.rprtr258;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class GUI implements Renderable {
    private Font theFont = Font.font("Helvetica", FontWeight.BOLD, 20);
    private DoubleProperty angleProperty;

    public GUI(DoubleProperty angle) {
        angleProperty = angle;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(56, 35, 40));
        gc.fillRect(0, 0, 640, 480);

        gc.setStroke(Color.rgb(249, 89, 247));
        gc.setLineWidth(2);
        gc.strokeRect(16, 87, 610, 374);
        gc.strokeRect(20, 91, 602, 366);

        gc.setFont(theFont);

        gc.setFill(Color.rgb(170, 0, 170));
        String angleText = "Angle: ";
        gc.fillText(angleText, 15, 23);

        gc.setFill(Color.rgb(255, 255, 83));
        String angleValueText = String.format("%.0f", (180 * angleProperty.getValue() / Math.PI));
        gc.fillText(angleValueText, 80, 23);
    }
}
