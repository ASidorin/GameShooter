import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	//Fields
	public static int WIDTH = 400;
	public static int HEIGHT = 400;
	
	
	
	private BufferedImage image;
	private Graphics2D g;
	private Thread thread;
	
	private static GameBack background;
	public static Player player;
	public static ArrayList<Bullet> bullets;
	
	//Constructor
	public GamePanel() {
		super();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		
		addKeyListener(new Listeners());
		
	
	}
	
	
	
	//Methods
	
	public void start() {
		thread = new  Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		background = new GameBack();
		player = new Player();
		bullets = new ArrayList<Bullet>();
		
		
		
		while(true) {      //TODO State
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			
			try {
				Thread.sleep(33);   // TODO FPS
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void gameUpdate() {
		
		background.update();
		player.update();
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update();
		}
	}
	
	
	public void gameRender() {
		
		background.draw(g);
		player.draw(g);
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
		
	}
	
	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		
	}
	
	

}
