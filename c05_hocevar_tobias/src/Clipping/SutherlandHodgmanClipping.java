package Clipping;
import model.ClippingPolygon;
import model.Point;
import java.util.ArrayList;
import static model.Polygon.sortVertices;

public class SutherlandHodgmanClipping {

    public static ArrayList<Point> sutherlandHodgman(ArrayList<Point> subjectPolygon, ClippingPolygon clipPolygon) {
        // Seřadíme vrcholy vstupního polygonu do protisměru hodinových ručiček
        subjectPolygon = sortVertices(subjectPolygon);

        // Vytvoříme nový ArrayList pro výsledný oříznutý polygon (outputList)
        ArrayList<Point> outputList = new ArrayList<>(subjectPolygon);

        // Seřadíme vrcholy ořezávacího polygonu (clipPolygon) do protisměru hodinových ručiček
        clipPolygon.setVertices(sortVertices(clipPolygon.getVertices()));

        // Procházíme každou hranu ořezávacího polygonu
        for (int i = 0; i < clipPolygon.getVertices().size(); i++) {
            int nexti = (i + 1) % clipPolygon.getVertices().size();

            // Vytvoříme kopii outputList (oříznutý polygon) pro další postup
            ArrayList<Point> inputList = new ArrayList<>(outputList);
            outputList.clear(); // Vyčistíme outputList pro nový oříznutý polygon

            // Definujeme aktuální hranu ořezávacího polygonu pomocí jejích počátečního (S) a koncového (E) bodu
            Point S = clipPolygon.getVertices().get(i);
            Point E = clipPolygon.getVertices().get(nexti);

            // Procházíme každou hranu inputList (oříznutého polygonu) pro ořezání
            for (int j = 0; j < inputList.size(); j++) {
                Point P = inputList.get(j);
                Point Q = inputList.get((j + 1) % inputList.size());

                // Kontrola, zda je bod Q uvnitř hrany definované body S a E
                if (isInside(S, E, Q)) {
                    // Kontrola, zda je bod P vevnitř hrany definované body S a E
                    if (!isInside(S, E, P)) {
                        // Vypočteme průsečík hran (S-E) a (P-Q) a přidáme ho do outputListu
                        outputList.add(computeIntersection(S, E, P, Q));
                    }
                    // Přidáme bod Q do outputListu
                    outputList.add(Q);
                } else if (isInside(S, E, P)) {
                    // Pokud je bod P uvnitř hrany definované body S a E, přidáme ho do outputListu
                    outputList.add(computeIntersection(S, E, P, Q));
                }
            }
        }
        return outputList; // Vrátíme výsledek
    }


    private static boolean isInside(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x) >= 0;
    }

    private static Point computeIntersection(Point a, Point b, Point p, Point q) {
        double A1 = b.y - a.y;
        double B1 = a.x - b.x;
        double C1 = A1 * a.x + B1 * a.y;

        double A2 = q.y - p.y;
        double B2 = p.x - q.x;
        double C2 = A2 * p.x + B2 * p.y;

        double det = A1 * B2 - A2 * B1;
        double intersectX = (B2 * C1 - B1 * C2) / det;
        double intersectY = (A1 * C2 - A2 * C1) / det;

        return new Point(intersectX, intersectY);
    }

}
