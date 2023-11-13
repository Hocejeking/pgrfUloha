package model;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Polygon {
    protected ArrayList<Point> polygonVertices = new ArrayList<>();
    protected ArrayList<Line> polygonLines = new ArrayList<Line>();
    public int color;

    public Polygon(){}

    public Polygon(ArrayList<Point> polygonVertices, int color){
            this.polygonVertices = polygonVertices;
            this.color = color;
            generateLines();
    }

    public ArrayList<Point> getVertices(){return polygonVertices;}

    public void setVertices(ArrayList<Point> vertices){
        this.polygonVertices = vertices;
    }

    public ArrayList<Line> getLines() {
        generateLines(); //make sure we have the current lines.
        return polygonLines;
    }

    public void setLines(ArrayList<Line> polygonLines){
        this.polygonLines = polygonLines;
    }

    public void clearPolygon(){
        if(polygonVertices.isEmpty()){return;}
        polygonVertices.clear();
    }

    protected void generateLines(){
        Point[] arrayPoint = polygonVertices.toArray(new Point[polygonVertices.size()]);
        for(int i = 0; i < arrayPoint.length; i++){
            if(i+1 < arrayPoint.length) {
                polygonLines.add(new Line(arrayPoint[i].x, arrayPoint[i].y, arrayPoint[i + 1].x, arrayPoint[i + 1].y, color));
            }
            else{
                polygonLines.add(new Line(arrayPoint[i].x,arrayPoint[i].y, arrayPoint[0].x, arrayPoint[0].y, color));
            }
        }
    }
}
