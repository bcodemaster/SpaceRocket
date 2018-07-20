package SpaceRocket;

/**********************************************
 *  This class contains the pipe object       *
 *What it does is Pipe1, Pipe2, and Pipe3     *
 *revolve with each other. When Pipe1 exit    *
 *screen it becomes Pipe3. Every pipe produces*
 *random height value whenever entering the   * 
 *screen.                                     *
 *          (======)                          *
 *          |      |                          *  
 *          |      |                          * 
 *          |      |                          *
 *********************************************/


import java.awt.*;
import java.util.Random;
import javax.swing.ImageIcon;

public class Pipes implements Updatable, Renderable {
	
	private int pipeWidth = 100;
	private int pipeHorizonalDistance = 180;
	private int pipeVerticalDistance = 200;
	
	private float xVel = -7.5f;
	private float x1, x2, x3;
	private float y1, y2, y3;
	
	// The pipe that is closest to the character
	private int currentPipe;
	// An array to hold the pipes coordinates
	private float [][] pipeCoords = new float [3][2];
	
	private Random rand;
	
	public Pipes () {
		rand = new Random();
		
		resetPipes();
	}
	
	public void resetPipes () {
		currentPipe = 0;
		
		x1 = game.WIDTH*2;
		x2 = x1 + pipeWidth + pipeHorizonalDistance;
		x3 = x2 + pipeWidth + pipeHorizonalDistance;
		
		y1 = getRandomY();
		y2 = getRandomY();
		y3 = getRandomY();
	}
	
	private int getRandomY() {
		// Adding the Max possible height + Min possible height
		return rand.nextInt((int)(game.HEIGHT * 0.4f)) + (game.HEIGHT/ 10);
	}

	@Override
	public void render(Graphics2D g, float interpolation) {
		// TODO Auto-generated method stub
		
		//Handle Background
		Image img = new ImageIcon (this.getClass().getResource("background.png")).getImage();
		g.drawImage(img, 0,0, null);
		//Pipe Color
		g.setColor(Color.lightGray);
		
		//Pipe 1
		g.fillRect ((int) (x1 + (xVel * interpolation)), 0, pipeWidth, (int) y1);
		g.fillRect ((int) (x1 + (xVel * interpolation)), (int) (y1 + pipeVerticalDistance), pipeWidth, game.HEIGHT);
		
		//Pipe 2
		g.fillRect ((int) (x2 + (xVel * interpolation)), 0, pipeWidth, (int) y2);
		g.fillRect ((int) (x2 + (xVel * interpolation)), (int) (y2 + pipeVerticalDistance), pipeWidth, game.HEIGHT);
		
		//Pipe 3
		g.fillRect ((int) (x3 + (xVel * interpolation)), 0, pipeWidth, (int) y3);
		g.fillRect ((int) (x3 + (xVel * interpolation)), (int) (y3 + pipeVerticalDistance), pipeWidth, game.HEIGHT);
		
		
	}

	@Override
	public void update(Input input) {
		// TODO Auto-generated method stub
		x1 += xVel;
		x2 += xVel;
		x3 += xVel;
		
		if(x1 + pipeWidth < 0) {
			x1 = game.WIDTH;
			y1 = getRandomY();
			currentPipe = 1;
		}
		if(x2 + pipeWidth < 0) {
			x2 = game.WIDTH;
			y2 = getRandomY();
			currentPipe = 2;
		}
		if(x3 + pipeWidth < 0) {
			x3 = game.WIDTH;
			y3 = getRandomY();
			currentPipe = 0;
		}
		//Update the pipe coordinates
		switch(currentPipe){
			case 0:
				pipeCoords[0][0] = x1;
				pipeCoords[0][1] = y1;
				break;
			case 1:
				pipeCoords[1][0] = x2;
				pipeCoords[1][1] = y2;
				break;
			case 2:
				pipeCoords[2][0] = x3;
				pipeCoords[2][1] = y3;
				break;
			
		}
		
	}
	
	public float [] getCurrentPipe() {
		return pipeCoords[currentPipe];
	}
	
	public int getCurrentPipeID () {
		return currentPipe;
	}
	
	public int getPipeWidth() {
		return pipeWidth;
	}
	
	public int getPipeHorizontalDistance () {
		return pipeHorizonalDistance;
	}
	
	public int getPipeVerticalDistance () {
		return pipeVerticalDistance;	
	}

}
