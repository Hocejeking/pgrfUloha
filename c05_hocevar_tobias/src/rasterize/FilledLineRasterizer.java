package rasterize;

import model.Elipsis;
import model.Line;
import model.Point;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class FilledLineRasterizer extends LineRasterizer {
    int color;

    public FilledLineRasterizer(Raster raster) {
        super(raster);
    }

    @Override
    public void setColor(int rgb) {
        this.color = rgb;
    }


    @Override
    protected void drawPrecisionLine(int x1,int y1, int x2, int y2){
        int dx = x2 - x1; //nastavit rozdíl vzdáleností
        int dy = y2 - y1; //nastavit rozdíl vzdáleností

        if (Math.abs(dx) > Math.abs(dy)) { //zkontrolovat vzdálenost k horizontální ose
            x2 = x1 + dx;
            y2 = y1;
        } else if(Math.abs(dx) < Math.abs(dy)) { //zkontrolovat vzdálenost k vertikální ose
            x2 = x1;
            y2 = y1 + dy;
        } else{

        }

       drawLine(x1,y1,x2,y2);//volání metody drawLine pro vykreslení úsečky
    }

    @Override
    protected void drawInteractiveDottedPrecisionline(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1; //nastavit rozdíl vzdáleností
        int dy = y2 - y1; //nastavit rozdíl vzdáleností

        if (Math.abs(dx) > Math.abs(dy)) { //zkontrolovat vzdálenost k horizontální ose
            x2 = x1 + dx;
            y2 = y1;
        } else if(Math.abs(dx) < Math.abs(dy)) { //zkontrolovat vzdálenost k vertikální ose
            x2 = x1;
            y2 = y1 + dy;
        } else{

        }

        drawInteractiveDottedLine(x1,y1,x2,y2);//volání metody drawInteractiveDottedLine pro vykreslení tečkované úsečky
    }

    @Override
    protected void drawLine(int x1,int y1,int x2, int y2) {
        int dx = abs(x2 - x1), sideX = x1 < x2 ? 1 : -1; //nastavíme směr pohybu (doleva[-1], doprava[1])
        int dy = -abs(y2 - y1), sideY = y1 < y2 ? 1 : -1;//nastavíme směr pohybu (dolů[-1], nahoru[1])
        int err = dx + dy; //nastavíme chybu
        int foo;//pomocná proměnná
        while (true) {
            raster.setPixel(x1, y1, color); //obarvíme jeden pixel na souřadnicích
            if (x1 == x2 && y1 == y2) { //pokud je dosáhnuto koncové bodu, ukončíme smyčku
                break;
            }
            foo = err << 1; //násobení 2
            if (foo > dy) { //pokud se pohybujeme po ose X, zvýšíme  chybu o rozdíl souřadnic a posunume po ose x ve správném směru (sideX)
                err += dy;
                x1 += sideX;
            }
            if (foo < dx) { //pokud se pohybujeme po ose Y, zvýšíme  chybu o rozdíl souřadnic a posunume po ose y ve správném směru (sideY)
                err += dx;
                y1 += sideY;
            }
        }
    }

    @Override
    protected void drawInteractiveDottedLine(int x1, int y1, int x2, int y2) {
        int dx = abs(x2 - x1), sx = x1 < x2 ? 1 : -1;//nastavíme směr pohybu (doleva[-1], doprava[1])
        int dy = -abs(y2 - y1), sy = y1 < y2 ? 1 : -1;//nastavíme směr pohybu (dolů[-1], nahoru[1])
        int err = dx + dy, e2;//nastavíme chybu
        int count = 0;
        while (true) {
            if (count < 3) { //první 3 iterace vykreslíme bod a poté 7 iterací mezera
                raster.setPixel(x1, y1, color); //vykreslí bod
            }
            if (x1 == x2 && y1 == y2) break;// podmínka pro určení směru pohybu
            e2 = 2 * err;
            if (e2 > dy) {
                err += dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
            count = (count + 1) % 10; //Zde nám modulo vyresetuje count - tudíž každý 10 krok je count znovu 0 a to nám určuje mezeru mezi vykreslenými pixely
        }
    }

    @Override
    protected  void drawElipse(int centerX, int centerY, double radiusX, double radiusY, Elipsis elipsis){
        double px =0, py=0;

        for (int i = 0; i <= 360; i++) {
            double x, y;
            x = radiusX * Math.sin(Math.toRadians(i));
            y = radiusY * Math.cos(Math.toRadians(i));

            if (i != 0) {
                this.drawLine((int) px + centerX, (int) py + centerY, (int) x + centerX, (int) y + centerY);
                elipsis.addLine(new Line(new Point((int) px + centerX, (int) py + centerY),new Point( (int) x + centerX, (int) y + centerY),84156463));
            }

            px = x;
            py = y;
        }
    }
    }

