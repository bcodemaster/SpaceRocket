package SpaceRocket;

/*********************************************
 *  This class contains Rocket object and    *
 *included it has the score, physics, font,  *
 *and the images use for character modeling. *
 *                                           *
 ********************************************/



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Rocket implements Updatable, Renderable{
	
	private float x,y;
	private float yVel;
	private float baseYVel = -6.0f;
	private float gravity = 0.25f;
	
	private Pipes pipes;
	private int scoredPipe = 0;
	
	private int score = 0;
	
	private Font gameFont = new Font ("Arial", Font.BOLD, 30);
	
	private BufferedImage Up;
	private BufferedImage Down;

	public Rocket (Pipes pipes) {
		
		resetRocket();
		this.pipes = pipes;
		
		try {
			Up = Sprite.getSprite("Rocket_up.png");
			Down = Sprite.getSprite("Rocket_down.png");
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			System.exit(1);
		}
	}
	
	public void resetRocket(){
		x = 100;
		y = 100;
		yVel = baseYVel;
	}
	
	private void flap() {
		yVel = baseYVel;
	}
	
	public void update (Input input){
		y += yVel;
		yVel += gravity;
		
		if(y < 0) {
			y = 0;
			yVel = 0;
		}
		
		if (input.isSpacePressed()) {
			flap();
		}
		float [] pipeCoords = pipes.getCurrentPipe();
		float pipeX = pipeCoords [0];
		float pipeY = pipeCoords [1];
		
		if ((x >= pipeX && x <= pipeX + pipes.getPipeWidth() && (y <= pipeY || y >= pipeY + pipes.getPipeVerticalDistance())) || y >= game.HEIGHT) {
			
			pipes.resetPipes();
			resetRocket();
			score = 0;
			
		} else {
			int currentPipeID = pipes.getCurrentPipeID();
			score = (scoredPipe != currentPipeID) ? score +1 : score;
			scoredPipe = currentPipeID;
		}
	}

	@Override
	public void render(Graphics2D g, float interpolation) {
		// TODO Auto-generated method stub
		
		g.setColor(Color.RED);
		
		g.drawImage(yVel <= 0 ? Up : Down, (int) x, (int) (y + (yVel * interpolation)), null);
		
		g.setFont(gameFont);
		g.drawString("Score: " + score, 20, 50);
		
		
		
	}
	
	

}
