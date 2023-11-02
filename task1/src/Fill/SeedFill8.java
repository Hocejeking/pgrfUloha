package Fill;

import rasterize.Raster;

import java.util.function.Predicate;

public class SeedFill8 implements SeedFill{

    @Override
    public void fill(Raster img, int x, int y, int fillColor) {
        System.out.println("inside fill");
        int color = img.getPixel(x,y);
        if(color == fillColor){
            System.out.println("returing");
            return;
        }

        img.setPixel(x,y,fillColor);
        fill(img, x+1, y, fillColor);
        fill(img, x,y+1, fillColor);
        fill(img, x-1,y, fillColor);
        fill(img, x, y-1, fillColor);
        fill(img, x-1,y-1, fillColor);
        fill(img, x+1,y+1, fillColor);
        fill(img, x-1, y+1, fillColor);
        fill(img, x+1, y-1, fillColor);
    }
}
