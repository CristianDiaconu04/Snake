/**
 * Cristian Diaconu
 * January 24 2022
 * Snake Game
 * This class has the code for the apple object
 */
import java.util.Random;
import csta.ibm.pong.GameObject;

public class Apple extends GameObject {
private final int RANDOM_RANGE=890-0+1;
private Random rand=new Random();
	@Override
	public void act() {

	}
	// Sets a random location for the new apple, doesn't make a new apple object it just moves the current one 
	public void newApple() {
		setX(30*(rand.nextInt(28 - 0 + 1 ) + 0));
		setY(30*(rand.nextInt( 26 - 0 + 1 ) + 0));
		if (!checkLocation()) {
			newApple();
		}
		
	}
	// Places the apple at the desired coordinates 
	public void placeApple(int x, int y) {
		setX(x*30);
		setY(y*30);
	}
	// Makes sure that the apple spawns outside of the snake and its body 
	public boolean checkLocation() {
		for (int i=0; i<=SnakeGame.score-1; ++i) {
			if (SnakeGame.body[i].getX()==this.getX() && SnakeGame.body[i].getY()==this.getY()) {
				return false;
			}
		}
		return true;
	}
}
