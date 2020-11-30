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
	public static ArrayList<Enemy> enemies;
	public static Wave wave;
	
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
		//String name = g.getClass().getName();
		//System.out.println(name);
		
		background = new GameBack();
		player = new Player();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		wave = new Wave();
		
		//enemies.add(new Enemy(1,1));
		//enemies.add(new Enemy(1,1));
		
		
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
			boolean remov = bullets.get(i).remove();
			if(remov) {
				bullets.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
		
		
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			double ex = e.getX();
			double ey = e.getY();
			for(int j = 0; j < bullets.size(); j++) {
				Bullet b = bullets.get(j);
				
				double bx = b.getX();
				double by = b.getY();
				
				double dx = ex - bx;
				double dy = ey - by;
				double dist = Math.sqrt(dx * dx + dy * dy);
				if((int) dist < e.getR() + b.getR()) {
					e.hit();
					bullets.remove(j);
					j--;
					boolean remove = e.remove();
			        if(remove) {
				        enemies.remove(i);
				        i--;	
				        break;
			         }
				
				}
			}
			
		}
		//Player-enemies collides
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			double ex = e.getX();
			double ey = e.getY();
			
			double px = player.getX();
			double py = player.getY();
			double dx = ex - px;
			double dy = ey - py;
			double dist = Math.sqrt(dx * dx + dy * dy);
			if((int)dist <= e.getR() + player.getR()) {
				e.hit();
				player.hit();
				boolean remove = e.remove();
		        if(remove) {
			        enemies.remove(i);
			        i--;	
			        break;
		         }
				
			}
		}
		
		//Wave update
		wave.update();
	}
	
	
	public void gameRender() {
		
		background.draw(g);
		
		player.draw(g);
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		if(wave.showWave()) {
			wave.draw(g);
		}
		
	}
	
	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		
	}
	
	

}
