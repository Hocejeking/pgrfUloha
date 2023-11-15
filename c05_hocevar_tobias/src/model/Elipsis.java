package model;

import rasterize.FilledLineRasterizer;
import rasterize.LineRasterizer;
import rasterize.RasterBufferedImage;

import java.awt.*;
import java.util.ArrayList;

public class Elipsis extends Polygon{
    public Integer centerX, centerY, radiusX, radiusY;
    private ArrayList<Line> lines;
    public Elipsis(int centerX,int centerY,int radiusX,int radiusY){
        this.centerX = centerX;
        this.centerY = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    @Override
    public void clearPolygon(){
        centerX = null;
        centerY = null;
        radiusX = null;
        radiusY = null;
        lines.clear();
    }

    @Override
    public ArrayList<Line> getLines(){
        return this.lines;
    }

    @Override
    public void setLines(ArrayList<Line> lines){
        this.lines = lines;
    }

    public void addLine(Line line){
        lines.add(line);
    }
}
