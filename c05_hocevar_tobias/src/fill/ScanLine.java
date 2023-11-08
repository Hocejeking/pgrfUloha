package fill;

import rasterize.Raster;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine {
    public static void fill(Raster img, Polygon pl, int fillColor, int canvasWidth, int canvasHeight ) {
        int min_y = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;

        for (Point point : pl.getVertices()) {
            min_y = Math.min(min_y, point.y);
            max_y = Math.max(max_y, point.y);
        }

        for (int y = min_y; y <= max_y; y++) {
            List<Integer> intersectionPoints = new ArrayList<>();

            for (int i = 0; i < pl.getVertices().size(); i++) {
                Point p1 = pl.getVertices().get(i);
                Point p2 = pl.getVertices().get((i + 1) % pl.getVertices().size());

                if (p1.y == p2.y) {
                }

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
                        img.setPixel(x, y, fillColor);
                    }
                }
            }
        }
    }
}
