package rasterize;

public class LineRasterizerTrivial extends LineRasterizer{
    public LineRasterizerTrivial(Raster raster)
    {
        super(raster);
    }

    @Override
    protected void drawLine(int x1, int y1, int x2, int y2) {

        float k = (y2-y1)/(float)(x2-x1);
        float q = (y1 - k * x1);

        if(x1>x2){
            int mid;
            mid = x1;
            x1 = x2;
            x2 = mid;
        }

        for(int i = x1; i <= x2; i++)
        {
            int y = Math.round(k * i + q);
            raster.setPixel(i,y,0xff0000);
        }

    }
}
