package SpaceRocket;

/********************************************
 *  This the main code of the program all of*
 *the graphics, rendering and use of objects*
 *are done in here.                         *
 *                                          *
 *  Simple terms this is the "Game Engine"  *
 ********************************************/


import java.awt.image.BufferStrategy;


import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel; 

public class game {
	
	//These are the class variables of game
	public final static int WIDTH = 800, HEIGHT = 600;
	
	private String gameName ="Space Rocket";
	
	private Canvas game  = new Canvas();
	
	private Input input;
	
	//This two array list are storage for all game objects
	private ArrayList<Updatable> updatables = new ArrayList<>();
	private ArrayList<Renderable> renderables = new ArrayList<>();
	
	public void addUpdatable (Updatable u) {
		updatables.add(u);
	}
	public void removeUpdatable (Updatable u) {
		updatables.remove(u);
	}
	public void addRenderable (Renderable r) {
		renderables.add(r);
	}
	public void removeRenderable (Renderable r) {
		renderables.remove(r);
	}
	
	public void start(){
	
		//Initialize the game window
		Dimension gameSize = new Dimension(WIDTH, HEIGHT);
		JFrame gameWindow = new JFrame(gameName);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Program close and shutdown on X
		gameWindow.setSize(gameSize);
		gameWindow.setResizable(false); //Can't resize the window
		gameWindow.setVisible(true);
		game.setSize(gameSize); //Just to make sure canvas doesn't change window size
		game.setMinimumSize(gameSize);// Make sure
		game.setMaximumSize(gameSize);// same
		game.setPreferredSize(gameSize);// same
		gameWindow.add(game);
		gameWindow.setLocationRelativeTo(null); //Initialize the window in the center not top-right corner
		
		//Init input
		input = new Input();
		game.addKeyListener(input);
		
		//Game Loop
		final int FRAMES_PER_SECOND = 60; //Every second has 60 Frams
		final int TIME_PER_FRAMES = 1000 / FRAMES_PER_SECOND; //One Thousand seconds
		final int MAX_FRAMESKIPS = 5; //Whenever the game strains in updating it can skip this amount of frames
		
		long nextGameFrame = System.currentTimeMillis();
		int loops;
		float interpolation; //Is a value between the First frame skip and the last frame skip.
		                     //Making sure the character is at the correct time and correct place.
		long timeAtLastFPSCheck = 0;
		int Frames = 0; // Will be incremented for every loop till it reaches Frames Per Second 
		
		boolean running = true;
		while(running){
			//Updating
			loops = 0;
			
			//It update if current time is less the time of the next fram and 
			//If Current loops are lower than MAXSKIPS then you can Update
			while (System.currentTimeMillis() > nextGameFrame && loops < MAX_FRAMESKIPS){
				update();
				Frames++;
				//Prediction of next Frame
				nextGameFrame += TIME_PER_FRAMES;
				loops++;
			}
			//Rendering
			//Interpolation helps the programs to render the objects half second faster incase the game render objects second behind
			//Helps the character move smootly without any gitters
			interpolation = (float) (System.currentTimeMillis() + TIME_PER_FRAMES - nextGameFrame)
						  / (float) TIME_PER_FRAMES;
			render(interpolation);
			
			//FPS Check
			//The value is on the terminal (System.out.print)
			//Current time minus last Time must equal around 60FPS
			if(System.currentTimeMillis() - timeAtLastFPSCheck >= 1000) {
				System.out.println("FPS: " + Frames);
				gameWindow.setTitle(gameName + " - FPS: " + Frames);
				Frames = 0;
				timeAtLastFPSCheck = System.currentTimeMillis();
			}
			
		}
		
		//Game end
		
	}
	
	private void update() {
		for(Updatable u : updatables){
			u.update(input);
		}
		
	}
	
	private void render (float interpolation) {
		
		//BufferStrategy: Is used pretty much a pre-render of the next object for smooth transition
		//To prevent flickering
		BufferStrategy b = game.getBufferStrategy();
		if(b == null) {
			game.createBufferStrategy(2);
			return;
			
		}
		Graphics2D g = (Graphics2D) b.getDrawGraphics(); //Graphics2D is a child of Graphics (so typecast)
		g.clearRect(0,0, game.getWidth(), game.getHeight()); //clear the screen
		//render everything
		for(Renderable r : renderables){
			
			//Rendering points in Interpolation with Graphic2D
			r.render(g, interpolation);
		}
		g.dispose(); // Clear up for memory space
		b.show(); //Render every game object
	}
	

}
