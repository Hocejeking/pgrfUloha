package fill;

import rasterize.Raster;
import model.*;

public class ScanLine {
    void fill(Raster img, Polygon pl, int fillColor, int edgeColor ) {
        //take a polygon
        //filter horizontal lines
        //orient lines in the polygon
        //find yMin, yMax

        //for r in(rMin, rMax)
            //create a list of intercepts
            //for Line in lines
                //find intercept with r and save it to the list of intercepts
            //sort intercepts in ascending order
            //draw vertical lines between even and odd intercepts

        //Rasterize the polygon using edgeColor


    }

}
