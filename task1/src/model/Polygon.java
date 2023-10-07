package model;

import java.awt.*;
import java.util.ArrayList;

public class Polygon {
    private ArrayList<Point> polygonVertices = new ArrayList<>();
    public int color;

    public Polygon(){

    }

    public Polygon(ArrayList<Point> polygonVertices, int color){
            this.polygonVertices = polygonVertices;
            this.color = color;
    }

    public ArrayList<Point> getVertices(){
        return polygonVertices;
    }

    public void setVertices(ArrayList<Point> vertices){
        this.polygonVertices = vertices;
    }

    public void clearPolygon(){
        polygonVertices.clear();
    }
}
