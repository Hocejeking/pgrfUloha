package model;

import java.lang.reflect.Array;

public class RectanglePolygon extends Polygon{

    public RectanglePolygon(Point a,Point b){
        this.polygonVertices.add(a);
        this.polygonVertices.add(b);
        calculateRemainingPoints(a,b);
    }

    private void calculateRemainingPoints(Point a, Point b){
        Point pointC = new Point(b.x,a.y);
        Point pointD = new Point(a.x, b.y);
        this.polygonVertices.add(pointC);
        this.polygonVertices.add(pointD);
    }
}
