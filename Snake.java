/**
 * Cristian Diaconu
 * January 24 2022
 * Snake Game
 * This class has the code for the snake object
 */
import csta.ibm.pong.GameObject;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Snake extends GameObject {	
	
	public static int speed = 30;
	
	public void act() {
				
	}
	
	// Moves the snake up 
	public void moveUp() {
		setY((getY()/speed-1)*speed);			
	}
	// Moves the snake down 
	public void moveDown() {
		setY((getY()/speed+1)*speed);	
	}
	// Moves the snake left
	public void moveLeft() {
		setX((getX()/speed-1)*speed);		
	}
	// Moves the snake right
	public void moveRight() {
		setX((getX()/speed+1)*speed);					
	}
	// Places the snake at the desired location 
	public void placeSnake(int x, int y) {
		setX(x*speed);
		setY(y*speed);
	}
}
