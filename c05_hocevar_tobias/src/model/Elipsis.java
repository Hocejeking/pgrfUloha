package model;

import rasterize.FilledLineRasterizer;
import rasterize.LineRasterizer;
import rasterize.RasterBufferedImage;

import java.awt.*;

public class Elipsis extends Polygon{
    public int centerX, centerY, radiusX, radiusY;
    public Elipsis(int centerX,int centerY,int radiusX,int radiusY){
        this.centerX =centerX;
        this.centerY =centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }
}
