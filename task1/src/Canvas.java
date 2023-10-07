import rasterize.FilledLineRasterizer;
import rasterize.LineRasterizer;
import rasterize.RasterBufferedImage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;

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
				System.out.println("pressing");
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e){
				System.out.println("releasing");
				x = e.getX();
				y = e.getY();
				clear();
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

	public void start() {
		//draw();
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}
}