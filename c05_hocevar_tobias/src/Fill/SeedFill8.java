package Fill;

import rasterize.Raster;

import java.util.function.Predicate;

public class SeedFill8 implements SeedFill{

    @Override
    public void fill(Raster img, int x, int y,int backgroundColor ,int fillColor) {
        int pixelColor = img.getPixel(x,y);
        //raster.getpixel vrací ARGB, při konverzi do 32 bit intu se tak vytvoří negativní hodnota a je nutná konverze pomocí bitshiftů.

        int r = (pixelColor >> 16) & 0xFF;
        int g = (pixelColor >> 8) & 0xFF;
        int b = pixelColor & 0xFF;
        int convColor = ((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);

        if(convColor != backgroundColor){
            return;
        }

        img.setPixel(x,y,fillColor);
        fill(img, x+1, y, backgroundColor, fillColor);
        fill(img, x,y+1, backgroundColor, fillColor);
        fill(img, x-1,y, backgroundColor, fillColor);
        fill(img, x, y-1, backgroundColor, fillColor);
        fill(img, x-1,y-1, backgroundColor, fillColor);
        fill(img, x+1,y+1, backgroundColor, fillColor);
        fill(img, x-1, y+1, backgroundColor, fillColor);
        fill(img, x+1, y-1, backgroundColor, fillColor);
    }
}
