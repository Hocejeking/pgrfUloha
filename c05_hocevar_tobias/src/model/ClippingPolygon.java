package model;
import java.util.ArrayList;
import java.util.Comparator;

public class ClippingPolygon extends Polygon{

    public ClippingPolygon(Point a,Point b){
        Point pointC = new Point(b.x,a.y);
        Point pointD = new Point(a.x, b.y);
        this.polygonVertices.add(pointD);
        this.polygonVertices.add(b);
        this.polygonVertices.add(pointC);
        this.polygonVertices.add(a);
    }
}
