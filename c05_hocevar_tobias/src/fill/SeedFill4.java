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

    public void fillWithPattern(Raster img, int x, int y, int backgroundColor, int fillColor, int patternColor) {
        int pixelColor = img.getPixel(x, y);

        int r = (pixelColor >> 16) & 0xFF;
        int g = (pixelColor >> 8) & 0xFF;
        int b = pixelColor & 0xFF;
        int convColor = ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);

        if (convColor != backgroundColor) {
            return;
        }

        img.setPixel(x, y, fillColor);

        // Define pattern-specific logic here (e.g., every nth pixel)
        if ((x / 10) % 2 == 0) {
            img.setPixel(x, y, patternColor);
        } else {
            img.setPixel(x, y, fillColor);
        }

        fillWithPattern(img, x + 1, y, backgroundColor, fillColor, patternColor);
        fillWithPattern(img, x, y + 1, backgroundColor, fillColor, patternColor);
        fillWithPattern(img, x - 1, y, backgroundColor, fillColor, patternColor);
        fillWithPattern(img, x, y - 1, backgroundColor, fillColor, patternColor);
    }
}
