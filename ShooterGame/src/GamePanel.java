
public class GamePanel {

	//Fields
	
	private BufferedImage image;
	private Graphics2D g;
	
	public static GameBack backgrpound;
	public static Player player;
	
	
	//Constructor
	public GamePanel() {
		super;
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		stFocusable(true;
		requestFocus();
		
		
	}
	
	//Functions
	
	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		
		
	}

}
