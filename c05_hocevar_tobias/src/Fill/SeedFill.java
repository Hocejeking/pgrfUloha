package Fill;
import rasterize.*;

import java.util.function.Predicate;

public interface SeedFill {
    void fill(Raster img, int c, int r, int color, int borderColor);
}
