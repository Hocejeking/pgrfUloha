package fill;

import rasterize.Raster;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine {
    /**
     * Metoda pro vyplnění vnitřku polygonu zadanou barvou.
     * @param img Rastr
     * @param pl Polygon pro vyplnění
     * @param fillColor Barva vyplnění
     */
    public static void fill(Raster img, Polygon pl, int fillColor) {
        // Pokud je polygon prázdný, ukončí metodu
        if (pl == null) {
            return;
        }

        // Inicializace proměnných pro minimalní a maximální y souřadnici
        int min_y = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;
        int canvasWidth = img.getWidth();
        int canvasHeight = img.getHeight();

        // Nalezení minimální a maximální y souřadnice v polygonu
        for (Point point : pl.getVertices()) {
            min_y = Math.min(min_y, point.y);
            max_y = Math.max(max_y, point.y);
        }

        // Procházení každé y souřadnice od min_y do max_y
        for (int y = min_y; y <= max_y; y++) {
            // Inicializace seznamu pro průsečíky
            List<Integer> intersectionPoints = new ArrayList<>();

            // Procházení všech hran polygonu
            for (int i = 0; i < pl.getVertices().size(); i++) {
                Point p1 = pl.getVertices().get(i);
                Point p2 = pl.getVertices().get((i + 1) % pl.getVertices().size());

                // Kontrola průsečíků na dané y souřadnici
                if ((p1.y < y && p2.y >= y) || (p2.y < y && p1.y >= y)) {
                    double x = p1.x + (double) (y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y);
                    intersectionPoints.add((int) x);
                }
            }

            // Seřazení průsečíku podle x souřadnice
            Collections.sort(intersectionPoints);

            // Procházení párů průsečíků a vyplnění pixelů mezi nimi
            for (int i = 0; i < intersectionPoints.size(); i += 2) {
                int x1 = intersectionPoints.get(i);
                int x2 = intersectionPoints.get(i + 1);

                // Vyplnění pixelů v daném rozsahu x souřadnice
                for (int x = x1; x <= x2; x++) {
                    if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight) {
                        img.setPixel(x, y, fillColor);
                    }
                }
            }
        }
    }

    public static void fillWithPattern(Raster img, Polygon pl, int fillColor, int patternColor) {
        if (pl == null) {
            return;
        }

        int min_y = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;
        int canvasWidth = img.getWidth();
        int canvasHeight = img.getHeight();

        for (Point point : pl.getVertices()) {
            min_y = Math.min(min_y, point.y);
            max_y = Math.max(max_y, point.y);
        }

        for (int y = min_y; y <= max_y; y++) {
            List<Integer> intersectionPoints = new ArrayList<>();

            for (int i = 0; i < pl.getVertices().size(); i++) {
                Point p1 = pl.getVertices().get(i);
                Point p2 = pl.getVertices().get((i + 1) % pl.getVertices().size());

                if ((p1.y < y && p2.y >= y) || (p2.y < y && p1.y >= y)) {
                    double x = p1.x + (double) (y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y);
                    intersectionPoints.add((int) x);
                }
            }

            Collections.sort(intersectionPoints);

            for (int i = 0; i < intersectionPoints.size(); i += 2) {
                int x1 = intersectionPoints.get(i);
                int x2 = intersectionPoints.get(i + 1);

                for (int x = x1; x <= x2; x++) {
                    if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight) {
                        //každý n-tý pixel obarvíme jinak pro vytvoření "rádoby" patternu
                        if ((x / 10) % 2 == 0) {
                            img.setPixel(x, y, patternColor);
                        } else {
                            img.setPixel(x, y, fillColor);
                        }
                    }
                }
            }
        }
    }

    //pozměněn vstupní parametr pro přehlednost jinde v programu..
    public static void fill(Raster img, ArrayList<Point> vertices, int fillColor) {
        if(vertices == null){return;}
        int min_y = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;
        int canvasWidth = img.getWidth();
        int canvasHeight = img.getHeight();

        for (Point point : vertices) {
            min_y = Math.min(min_y, point.y);
            max_y = Math.max(max_y, point.y);
        }

        for (int y = min_y; y <= max_y; y++) {
            List<Integer> intersectionPoints = new ArrayList<>();

            for (int i = 0; i < vertices.size(); i++) {
                Point p1 = vertices.get(i);
                Point p2 = vertices.get((i + 1) % vertices.size());

                if (p1.y == p2.y) {
                }
                else if ((p1.y < y && p2.y >= y) || (p2.y < y && p1.y >= y)) {
                    double x = p1.x + (double) (y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y);
                    intersectionPoints.add((int) x);
                }
            }

            Collections.sort(intersectionPoints);

            for (int i = 0; i < intersectionPoints.size(); i += 2) {
                int x1 = intersectionPoints.get(i);
                int x2 = intersectionPoints.get(i + 1);

                for (int x = x1; x <= x2; x++) {
                    if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight) {
                        img.setPixel(x, y, fillColor);
                    }
                }
            }
        }
    }
}
