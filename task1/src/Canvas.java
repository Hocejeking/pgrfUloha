import rasterize.FilledLineRasterizer;
import rasterize.LineRasterizer;
import rasterize.RasterBufferedImage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 * 
 * @author PGRF FIM UHK
 * @version 2020
 */

public class Canvas {

	private JFrame frame;
	private JPanel panel;
	private RasterBufferedImage img;
	private LineRasterizer lineRasterizer;
	private int x,y;
	private int mouseX, mouseY, clicked;
	private boolean controlDown = false;
	private ArrayList<model.Point> PolygonPoints = new ArrayList();

	public Canvas(int width, int height) {
		x = width / 2;
		y = height / 2;

		frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		img = new RasterBufferedImage(width, height);
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

		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		panel.requestFocus();
		panel.requestFocusInWindow();
		//bitová hloubka, náročnost na paměť, co je rastr, co je pixel, bit/byte, java proměnna, atomicka, instance tříd, konstruktor, co to je, jak vytrořit instani třídy, abstraktní, interace, set,get ,private, public, přiřazování proměnných, jak rasterizovat usečku, nějakej předpis přímky, co jednozlivý písmenka v předpisu znamenají
		//uloha do neděle 15.10?,

		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e){
				if(e.isControlDown()){
					System.out.println("pressing with control");
					mouseX = e.getX();
					mouseY = e.getY();
					img.setPixel(mouseX,mouseY, 150);
					PolygonPoints.add(new model.Point(mouseX,mouseY));
				}

				System.out.println("pressing");
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e){
				System.out.println("releasing");
				x = e.getX();
				y = e.getY();
				//clear();
				draw(mouseX,x,mouseY,y);
			}
		});

		panel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e){
				System.out.println("dragging");
				clear();
				draw(mouseX,e.getX(),mouseY,e.getY());
				panel.repaint();
			}
		});

		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){

			}

			@Override
			public void keyReleased(KeyEvent e){
				System.out.println("event trigg");
				if(e.getKeyCode() == KeyEvent.VK_CONTROL){
					System.out.println("control up");
					drawPolygon(PolygonPoints);
					PolygonPoints.clear();
					panel.repaint();
				}
			}
		});
	}

	public void clear() {
		Graphics gr = img.getGraphics();
		gr.setColor(new Color(0x2f2f2f));
		gr.fillRect(0, 0, img.getWidth(), img.getHeight());
	}

	public void present(Graphics graphics) {
		graphics.drawImage(img.getImg(), 0, 0, null);
	}

	public void draw(int x1, int x2, int y1, int y2) {
		lineRasterizer.rasterize(x1, y1, x2,y2, 150);
	}

	public void drawPolygon(ArrayList<model.Point> PolygonPoints){
		model.Point[] arrayPoint = PolygonPoints.toArray(new model.Point[PolygonPoints.size()]);
		for(int i = 0; i < arrayPoint.length; i++){
			if(i+1 < arrayPoint.length) {
				System.out.println("rasterizing");
				lineRasterizer.rasterize(arrayPoint[i].x, arrayPoint[i].y, arrayPoint[i + 1].x, arrayPoint[i + 1].y, 250);
			}
			else{
				System.out.println("rasterizing last point");
				lineRasterizer.rasterize(arrayPoint[i].x,arrayPoint[i].y, arrayPoint[0].x, arrayPoint[0].y,250);
			}
		}
	}

	public void start() {
		//draw();
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}
}