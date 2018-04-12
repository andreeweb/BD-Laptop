package it.uniroma2.dicii.bd.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class AdminHomeViewController {

    private Pane view;
    private GraphicsContext gc;
    private Canvas canvas;

    @FXML
    private void initialize() {

    }

    public void onCreateView(Pane view){

        this.view = view;

        canvas = new Canvas(600, 400);

        // Get the graphics context of the canvas
        gc = canvas.getGraphicsContext2D();
        // Set line width
        gc.setLineWidth(2.0);

        canvas.widthProperty().addListener(observable -> redraw());
        canvas.heightProperty().addListener(observable -> redraw());

        view.setOnScroll((ScrollEvent event) -> {

            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }

            canvas.setScaleX(canvas.getScaleX() * zoomFactor);
            canvas.setScaleY(canvas.getScaleY() * zoomFactor);

            canvas.setWidth(canvas.getWidth()+canvas.getScaleX() * zoomFactor);
            System.out.println(canvas.getWidth());
        });
    }

    private void redraw() {
        double xCoords[] = {

                13.91816,
                13.91975,
                13.92455,
                13.92614,
                13.93732,
                13.93732,
                13.94052,
                13.94052,
                13.93573,
                13.93413,
                13.93573,
                13.94052,
                13.94372,
                13.94372,
                13.92136,
                13.91657,
                13.91177,
                13.90858,
                13.90858,
                13.90538,
                13.90538,
                13.8958 ,
                13.89579,
                13.91816,};

        double yCoords[] = {
                1.26974,
                1.26654,
                1.26655,
                1.26335,
                1.26335,
                1.25537,
                1.25377,
                1.23141,
                1.23141,
                1.22981,
                1.22821,
                1.22821,
                1.22502,
                1.19946,
                1.19946,
                1.20585,
                1.20585,
                1.20904,
                1.21383,
                1.21543,
                1.233,
                1.24099,
                1.26974,
                1.26974,};

        gc.strokePolyline(xCoords, yCoords, xCoords.length);

        view.getChildren().add(canvas);

    }

}
