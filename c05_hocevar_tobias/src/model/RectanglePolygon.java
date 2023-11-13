package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;


public class RectanglePolygon extends Polygon{

    public RectanglePolygon(Point a,Point b){
        this.polygonVertices.add(a);
        calculateRemainingPoints(a,b);
    }

    public Point returnCenterPointsForElipse()
    {
        int centerX = (this.polygonVertices.get(0).x + this.polygonVertices.get(2).x) / 2;
        int centerY = (this.polygonVertices.get(0).y + this.polygonVertices.get(2).y) / 2;
        return new Point(centerX,centerY);
    }

    public ArrayList<Integer> returnRadiusOfElipse(){
        int width = (this.polygonVertices.get(0).x - this.polygonVertices.get(3).x);
        int height = (this.polygonVertices.get(0).y - this.polygonVertices.get(1).y);
        ArrayList<Integer> dimensions = new ArrayList<Integer>();
        dimensions.add(width);
        dimensions.add(height);
        return dimensions;
    }

    private void calculateRemainingPoints(Point a, Point b){
        Point pointC = new Point(b.x,a.y);
        Point pointD = new Point(a.x, b.y);
        this.polygonVertices.add(pointD);
        this.polygonVertices.add(b);
        this.polygonVertices.add(pointC);
        //a-0, d-1, b-2, c-3
    }
}
