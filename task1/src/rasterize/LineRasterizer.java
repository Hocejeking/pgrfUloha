package rasterize;

import model.Line;
import model.Point;

import java.awt.*;
import java.util.ArrayList;

public abstract class LineRasterizer {
    Raster raster;
    Color color;

    public LineRasterizer(Raster raster){
        this.raster = raster;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(int color) {
        this.color = new Color(color);
    }

    public void rasterize(Line line) {
       drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    public void rasterize(int x1, int y1, int x2, int y2) {
        drawLine(x1, y1, x2, y2);
    }

    public void rasterizePrecisionMode(int x1, int y1, int x2, int y2){
        drawPrecisionLine(x1,y1,x2,y2);
    }

    public void rasterizeInteractiveLine(int x1, int y1, int x2, int y2) {drawInteractiveDottedLine(x1,y1,x2,y2);}

    public model.Point checkPoint(model.Point Point, ArrayList<model.Point> polygonPoints){
            Double lowestDistance = null;
            model.Point closestPoint = null;
            for (model.Point o: polygonPoints) {
                double distance = Math.hypot(o.x-Point.x, o.y-Point.y);
                if(lowestDistance == null){
                    lowestDistance = distance;
                    continue;
                }
                if(distance < lowestDistance) {
                    lowestDistance = distance;
                    closestPoint = o;
                }
            }
            return closestPoint;
    }

    protected void drawLine(int x1, int y1, int x2, int y2) {

    }


    protected void drawPrecisionLine(int x1, int y1, int x2, int y2){

    }

    protected void drawInteractiveDottedLine(int x1, int y1, int x2, int y2){

    }

}
