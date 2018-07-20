package SpaceRocket;


/*********************************************************
 * This the main class of the Game, press the run button
 * in order to run the game.
 * 
 * This class only uses the objects Pipes and Rocket to
 * renders them in the game.
 *********************************************************/

public class Main {
	public static void main (String[] args) {
	game g = new game();
	
	//Initialise game objects
	
	Pipes p = new Pipes();
	Rocket b = new Rocket (p);
	 
	//Add Renderable and Updatable
	g.addRenderable(p);
	g.addUpdatable(p);
	
	g.addRenderable(b);
	g.addUpdatable(b);
	
	//Start!
	
	g.start();
	
	
	}
	
}
