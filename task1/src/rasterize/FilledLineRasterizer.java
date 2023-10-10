package rasterize;

import java.awt.*;

public class FilledLineRasterizer extends LineRasterizer {
    int color = 220;

    public FilledLineRasterizer(Raster raster) {
        super(raster);
    }

    @Override
    public void setColor(int rgb) {
        this.color = rgb;
    }

    @Override
    protected void drawPrecisionLine(int x1,int y1, int x2, int y2){
        System.out.println("drawing with precision");
            // spočítat k
            float k = (y2 - y1) / (float)(x2 - x1);
            // spočítat q
            float q = y1 - k * x1;

            if(x1 > x2) {
                int tmp = x1;
                x1 = x2;
                x2 = tmp;
            }

            for (int x = x1; x <= x2; x++) {
                int y = Math.round(k * x + q);
                raster.setPixel(x, y, 0xff0000);
            }

    }

    @Override
    protected void drawLine(int x1,int y1,int x2, int y2) {
        int width = x2 - x1;    //zjištění odvěsny(šířka) trojúhelníku
        int height = y2 - y1;   //zjištění odvěsny (výšky) trojúhelníku
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0; //rozhodovací parametry pro směr vykreslení úsečky
        /*
           Je potřeba zkontrolovat znaménko šířky a výšky aby jsme určili směr úsečky.
           Pokud je šířka nebo výška záporná, znamená to, že úsečku je třeba kreslit opačným směrem,
           takže se příslušná hodnota dx nebo dy nastaví na -1.
         */
        if (width < 0)
        {
            dx1 = -1;
        }
        else if (width > 0){
            dx1 = 1;
        }
        if (height < 0) {
            dy1 = -1;
        }
        else if (height > 0) {
            dy1 = 1;
        }
        if (width < 0) {
            dx2 = -1;
        }
        else if (width > 0) {
            dx2 = 1;
        }
        int longest = Math.abs(width);
        int shortest = Math.abs(height);
        if (longest < shortest) { // rozhodne jestli je větší šířka nebo výška abychom použili největší rozměr jako referenční proměnou
            longest = Math.abs(height);
            shortest = Math.abs(width);
            if (height < 0) {
                dy2 = -1;
            }
            else if (height > 0){
                dy2 = 1;
            }
            dx2 = 0;
        }
        int err = longest / 2; //nastavení čitatele pro sledování chyby umístění pixelu
        for (int i = 0; i <= longest; i++) { //prochazime podle nejdelšího rozměru, aby byl alg. efektivni.
            raster.setPixel(x1, y1, color);
            err += shortest;
            if (err > longest) {
                err -= longest;
                x1 += dx1;
                y1 += dy1;
            } else {
                x1 += dx2;
                y1 += dy2;
            }
        }
    }
}
