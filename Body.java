/**
 * Cristian Diaconu
 * January 24 2022
 * Snake Game
 * This class has the code for the body objects
 */
import csta.ibm.pong.GameObject;

public class Body extends GameObject {

	@Override
	public void act() {	}
	// Places the body at desired location 
	public void placeBody(int x, int y) {
		setX(x*30);
		setY(y*30);
	}

}
