import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;

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
	private BufferedImage img;
	private int x,y;
	private int mouseX, mouseY, clicked;
	public Canvas(int width, int height) {
		x = width / 2;
		y = height / 2;
		clicked = 0;


		frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyCode() == KeyEvent.VK_UP)
				{
					y--;
					draw();
					panel.repaint();
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					y++;
					draw();
					panel.repaint();
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT)
				{
					x--;
					draw();
					panel.repaint();
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				{
					x++;
					draw();
					panel.repaint();
				}

				draw();
				panel.repaint();
			}
		});//bitová hloubka, náročnost na paměť, co je rastr, co je pixel, bit/byte, java proměnna, atomicka, instance tříd, konstruktor, co to je, jak vytrořit instani třídy, abstraktní, interace, set,get ,private, public, přiřazování proměnných, jak rasterizovat usečku, nějakej předpis přímky, co jednozlivý písmenka v předpisu znamenají
		//uloha do neděle 15.10?,
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				/*img.setRGB(e.getX(),e.getY(),0x00ff00);*/
				clicked++;
				Graphics g = img.getGraphics();
				g.setColor(Color.YELLOW);

				if(clicked % 2 == 1)
				{
					mouseX = e.getX();
					mouseY = e.getY();

				}
				if(clicked % 2 == 0)
				{
					x = e.getX();
					y = e.getY();
					g.drawLine(mouseX,mouseY,x,y);
					panel.repaint();
				}
				panel.repaint();
			}
		});

		/*panel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);

				Graphics g = img.getGraphics();
				g.setColor(Color.YELLOW);
				g.drawLine(width/2,height/2,e.getX(),e.getY());


				img.setRGB(e.getX(),e.getY(),0x00ff00);
				img.setRGB(e.getX()+1,e.getY(),0x00ff00);
				img.setRGB(e.getX()+1,e.getY()+1,0x00ff00);
				img.setRGB(e.getX(),e.getY()+1,0x00ff00);
				panel.repaint();
			}
		});*/


	}

	public void clear() {
		Graphics gr = img.getGraphics();
		gr.setColor(new Color(0x2f2f2f));
		gr.fillRect(0, 0, img.getWidth(), img.getHeight());
	}


	public void present(Graphics graphics) {
		graphics.drawImage(img, 0, 0, null);
	}
	public void draw() {
		clear();
		img.setRGB((x), (y), 0x00ff00);
		img.setRGB((x+1), (y), 0x00ff00);
		img.setRGB((x-1), (y), 0x00ff00);
		img.setRGB((x), (y+1), 0x00ff00);
		img.setRGB((x), (y-1), 0x00ff00);

	}

	public void start() {
		//draw();
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}

}