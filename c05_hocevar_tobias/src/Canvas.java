import Clipping.SutherlandHodgmanClipping;
import fill.ScanLine;
import fill.SeedFill4;
import fill.SeedFill8;
import model.*;
import model.Point;
import model.Polygon;
import rasterize.FilledLineRasterizer;
import rasterize.LineRasterizer;
import rasterize.RasterBufferedImage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 * 
 * @author PGRF FIM UHK
 * @version 2020
 */

public class Canvas {

	private int indexOfClosestPoint = 0;
	private JFrame frame;
	private JPanel panel;
	private JLabel label = new JLabel();
	private RasterBufferedImage img;
	private LineRasterizer lineRasterizer;
	private SeedFill4 filler = new SeedFill4();
	private ScanLine scanLine = new ScanLine();
	private int x,y;
	private int mouseX, mouseY;
	private Polygon PolygonCanvas = new Polygon();
	private RectanglePolygon rectanglePolygon;
	private ClippingPolygon  clippingPolygon;
	private Elipsis elipsisPolygon;

	public Canvas(int width, int height) {
		x = width / 2;
		y = height / 2;

		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		img = new RasterBufferedImage(width, height);
		img.setClearColor(0);
		lineRasterizer = new FilledLineRasterizer(img);

		Graphics g = img.getGraphics();
		g.setColor(Color.YELLOW);
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present(g);
			}
		};

		label.setText("Press and Hold ctrl for drawing Polygons; Press and Hold shift for precision mode; Press C to clear; Hold ctrl + r. mouse button to edit");
		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel, BorderLayout.CENTER);
		frame.add(label, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);

		panel.requestFocus();
		panel.requestFocusInWindow();

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				if(SwingUtilities.isLeftMouseButton(e)) {
					if (e.isControlDown()) {
						mouseX = e.getX();
						mouseY = e.getY();
						PolygonCanvas.getVertices().add(new model.Point(mouseX, mouseY));
					} else if (e.isShiftDown()) {
						mouseX = e.getX();
						mouseY = e.getY();
					}
					else {
						mouseX = e.getX();
						mouseY = e.getY();
						panel.repaint();
					}
				}
				else if(SwingUtilities.isRightMouseButton(e)){
					if(e.isControlDown()) {
						int rcX, rcY;
						rcX = e.getX();
						rcY = e.getY();
						Point editPoint = new Point(rcX, rcY);
						Point closestPoint = lineRasterizer.checkForClosestPoint(editPoint, PolygonCanvas.getVertices());
						indexOfClosestPoint = PolygonCanvas.getVertices().indexOf(closestPoint);
						if (indexOfClosestPoint == -1)
							indexOfClosestPoint++;
					}
				}
				else if(SwingUtilities.isMiddleMouseButton(e)){
					if(e.isControlDown()){
						if(e.isAltDown()) {
							int rcX, rcY;
							rcX = e.getX();
							rcY = e.getY();
							Point editPoint = new Point(rcX, rcY);
							Point closestPoint = lineRasterizer.checkForClosestPoint(editPoint, PolygonCanvas.getVertices());
							int indexOfClosestPointToDelete = PolygonCanvas.getVertices().indexOf(closestPoint);
							if (indexOfClosestPointToDelete == -1)
								indexOfClosestPointToDelete++;
							PolygonCanvas.getVertices().remove(indexOfClosestPointToDelete);
						}
						else{
							int rcX, rcY;
							rcX = e.getX();
							rcY = e.getY();
							Point pointToAdd = new Point(rcX,rcY);
							Point closestPoint = lineRasterizer.checkForClosestPoint(pointToAdd, PolygonCanvas.getVertices());
							Double ClosestPointBackwards=null;
							Double ClosestPointForward=null;

							if((PolygonCanvas.getVertices().indexOf(closestPoint) + 1 )>=0) {
								ClosestPointForward = lineRasterizer.checkForClosestPoint(PolygonCanvas.getVertices().get(PolygonCanvas.getVertices().indexOf(closestPoint) + 1), PolygonCanvas.getVertices(), true);
							}
							else if((PolygonCanvas.getVertices().indexOf(closestPoint)- 1)>=0) {
								ClosestPointBackwards = lineRasterizer.checkForClosestPoint(PolygonCanvas.getVertices().get(PolygonCanvas.getVertices().indexOf(closestPoint) - 1), PolygonCanvas.getVertices(), true);
							}

							if(ClosestPointBackwards == null){
								PolygonCanvas.getVertices().add(PolygonCanvas.getVertices().indexOf(closestPoint) + 1, pointToAdd);
							}
							else if(ClosestPointForward == null){
								PolygonCanvas.getVertices().add(PolygonCanvas.getVertices().indexOf(closestPoint) - 1, pointToAdd);
							}
							else {
								if (ClosestPointBackwards > ClosestPointForward) {
									PolygonCanvas.getVertices().add(PolygonCanvas.getVertices().indexOf(closestPoint) + 1, pointToAdd);
								} else if (ClosestPointBackwards < ClosestPointForward) {
									PolygonCanvas.getVertices().add(PolygonCanvas.getVertices().indexOf(closestPoint) - 1, pointToAdd);
								}
							}
						}
						clear();
						drawPolygon(PolygonCanvas.getVertices());
						panel.repaint();
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e){
				lineRasterizer.setColor(8777216);
				if(SwingUtilities.isRightMouseButton(e)){
					if(e.isShiftDown()){
						filler.fill(img,e.getX(),e.getY(),0,8777216);
						panel.repaint();
					}
					else if(e.isAltDown()){
						filler.fillWithPattern(img,e.getX(),e.getY(),0,8777216,45613859);
						panel.repaint();
					}
					else {
						clear();
						drawPolygon(PolygonCanvas.getVertices());
						panel.repaint();
					}
				}
				else if (SwingUtilities.isLeftMouseButton(e)) {
					if(e.isControlDown()){}
					else if(e.isShiftDown()){
						x = e.getX();
						y = e.getY();
						clippingPolygon = new ClippingPolygon(new Point(mouseX,mouseY), new Point(x,y));
						drawPolygon(clippingPolygon.getVertices());
						ArrayList<Point> clippedPolygon = SutherlandHodgmanClipping.sutherlandHodgman(PolygonCanvas.getVertices(), clippingPolygon);
						lineRasterizer.setColor(1400000);
						drawPolygon(clippedPolygon);
						ScanLine.fill(img,clippedPolygon,43345312);
						lineRasterizer.setColor(8777216);
						panel.repaint();
					}
					else if(e.isAltDown()){
						x = e.getX();
						y = e.getY();
						ScanLine.fill(img,PolygonCanvas,8777216);
						ScanLine.fillWithPattern(img, rectanglePolygon,8777216, 12345862);
						panel.repaint();
					}
					else {
						x = e.getX();
						y = e.getY();
						rectanglePolygon = new RectanglePolygon(new Point(mouseX,mouseY), new Point(x,y));
						drawPolygon(rectanglePolygon.getVertices());
						Point center = rectanglePolygon.returnCenterPointsForElipse();
						ArrayList<Integer> radius = rectanglePolygon.returnRadiusOfElipse();
						elipsisPolygon = new Elipsis(center.x, center.y,radius.get(0) /2,radius.get(1)/2 );
						drawElipse(center.x,center.y,radius.get(0) /2,radius.get(1)/2, elipsisPolygon );
						panel.repaint();
					}
				}
			}
		});

		panel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lineRasterizer.setColor(1400000);
				if(SwingUtilities.isLeftMouseButton(e)){
					if (e.isControlDown()) {
						clear();
						PolygonCanvas.getVertices().remove(PolygonCanvas.getVertices().get(PolygonCanvas.getVertices().size() - 1));
						PolygonCanvas.getVertices().add(new Point(e.getX(),e .getY()));
						drawInteractivePolygon(PolygonCanvas.getVertices());
						panel.repaint();
					}
					else if(e.isShiftDown()){
						clear();
						x = e.getX();
						y = e.getY();
						clippingPolygon = new ClippingPolygon(new Point(mouseX,mouseY), new Point(x,y));
						drawInteractivePolygon(clippingPolygon.getVertices());
						drawPolygon(PolygonCanvas.getVertices());
						panel.repaint();
					}
					else {
						clear();
						x = e.getX();
						y = e.getY();
						rectanglePolygon = new RectanglePolygon(new Point(mouseX,mouseY), new Point(x,y));
						ArrayList<Integer> radius = rectanglePolygon.returnRadiusOfElipse();
						Point center = rectanglePolygon.returnCenterPointsForElipse();
						elipsisPolygon = new Elipsis(center.x, center.y,radius.get(0) /2,radius.get(1)/2 );
						drawElipse(center.x,center.y,radius.get(0) /2,radius.get(1)/2, elipsisPolygon );
						drawInteractivePolygon(rectanglePolygon.getVertices());
						panel.repaint();
					}
				} else if (SwingUtilities.isRightMouseButton(e)) {
					if(e.isControlDown()) {
						clear();
						PolygonCanvas.getVertices().remove(indexOfClosestPoint);
						PolygonCanvas.getVertices().add(indexOfClosestPoint, new Point(e.getX(), e.getY()));
						drawInteractivePolygon(PolygonCanvas.getVertices());
						panel.repaint();
					}
				}
			}
		});

		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_C){
					clear();
					PolygonCanvas.clearPolygon();
					indexOfClosestPoint = 0;
					if(rectanglePolygon != null){rectanglePolygon.clearPolygon();}
					if(clippingPolygon != null){clippingPolygon.clearPolygon();}
					panel.repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent e){
				lineRasterizer.setColor(8777216);
				if(e.getKeyCode() == KeyEvent.VK_CONTROL){
					drawPolygon(PolygonCanvas.getVertices());
					panel.repaint();
				}
			}
		});
	}

	public void clear() {
		Graphics gr = img.getGraphics();
		img.clear();
		gr.setColor(new Color(0));
		gr.fillRect(0, 0, img.getWidth(), img.getHeight());
	}

	public void present(Graphics graphics) {
		graphics.drawImage(img.getImg(), 0, 0, null);
	}

	public void draw(int x1, int x2, int y1, int y2) {
		lineRasterizer.rasterize(x1, y1, x2,y2);
	}

	public void drawInteractive(int x1, int x2, int y1, int y2){
		lineRasterizer.rasterizeInteractiveLine(x1,y1,x2,y2);
	}

	public void drawPolygon(ArrayList<model.Point> PolygonPoints){
		model.Point[] arrayPoint = PolygonPoints.toArray(new model.Point[PolygonPoints.size()]);
		for(int i = 0; i < arrayPoint.length; i++){
			if(i+1 < arrayPoint.length) {
				lineRasterizer.rasterize(arrayPoint[i].x, arrayPoint[i].y, arrayPoint[i + 1].x, arrayPoint[i + 1].y);
			}
			else{
				lineRasterizer.rasterize(arrayPoint[i].x,arrayPoint[i].y, arrayPoint[0].x, arrayPoint[0].y);
			}
		}
	}
	public void drawInteractivePolygon(ArrayList<model.Point> PolygonPoints){
		model.Point[] arrayPoint = PolygonPoints.toArray(new model.Point[PolygonPoints.size()]);
		for(int i = 0; i < arrayPoint.length; i++){
			if(i+1 < arrayPoint.length) {
				lineRasterizer.rasterizeInteractiveLine(arrayPoint[i].x, arrayPoint[i].y, arrayPoint[i + 1].x, arrayPoint[i + 1].y);
			}
			else{
				lineRasterizer.rasterizeInteractiveLine(arrayPoint[i].x,arrayPoint[i].y, arrayPoint[0].x, arrayPoint[0].y);
			}
		}
	}

	public void drawPrecision(int mouseX, int x, int mouseY,int y){
		lineRasterizer.rasterizePrecisionMode(mouseX,mouseY,x,y);
	}

	public void drawInteractivePrecision(int mouseX,int x, int mouseY, int y){
		lineRasterizer.rasterizeInteractivePrecisionLine(mouseX, mouseY, x,y);
	}

	public void drawElipse(int centerX, int centerY, int radiusX, int radiusY, Elipsis elipsisPolygon){
		lineRasterizer.rasterizeElipse(centerX,centerY,radiusX,radiusY, elipsisPolygon);
	}

	public void start() {
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}
}