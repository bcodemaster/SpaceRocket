package SpaceRocket;

/****************************************************
 * This is the class that handles controllers input *
 * in the game. Currently the game only uses the    *
 * space bar to handle the character or to make it  *
 * jump.                                            *
 *                                                  *
 ****************************************************/

import java.awt.event.*;

public class Input implements KeyListener {
	
	private boolean spacePressed = false;
	private boolean spaceReleased = true;
	
	public boolean isSpacePressed() {
		boolean s = spacePressed;
		spacePressed = false;
		return s;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE && spaceReleased) {
			spacePressed = true;
			spaceReleased = false;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			spaceReleased = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
