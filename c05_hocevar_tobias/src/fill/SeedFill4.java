package fill;

import rasterize.Raster;

public class SeedFill4  implements  SeedFill{
    @Override
    public void fill(Raster img, int x, int y, int backgroundColor, int fillColor){
        int pixelColor = img.getPixel(x,y);

        int r = (pixelColor >> 16) & 0xFF;
        int g = (pixelColor >> 8) & 0xFF;
        int b = pixelColor & 0xFF;
        int convColor = ((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);

        if(convColor != backgroundColor){
            return;
        }

        img.setPixel(x,y, fillColor);
        fill(img,x+1,y, backgroundColor, fillColor);
        fill(img,x,y+1, backgroundColor, fillColor );
        fill(img,x-1,y, backgroundColor, fillColor);
        fill(img,x,y-1, backgroundColor, fillColor);
    }
}
