package Fill;

import rasterize.Raster;
import java.util.function.Predicate;

public class SeedFill4  implements  SeedFill{
    @Override
    public void fill(Raster img, int x, int y, int fillColor){
        int pixelColor = img.getPixel(x,y);
        if(pixelColor == fillColor){
            return;
        }
        img.setPixel(x,y, fillColor);
        fill(img,x+1,y, fillColor);
        fill(img,x,y+1, fillColor);
        fill(img,x-1,y, fillColor);
        fill(img,x,y-1, fillColor);

    }
}
