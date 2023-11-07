package fill;
import rasterize.*;

public interface SeedFill {
    void fill(Raster img, int c, int r, int color, int borderColor);
}
