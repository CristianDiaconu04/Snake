/**
 * Cristian Diaconu
 * January 24 2022
 * Snake Game
 * This program runs the game snake
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import csta.ibm.pong.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeGame extends Game {

	public static int score=0;
	private static int highScore=0;
	public enum Direction {UP, DOWN, LEFT, RIGHT, NONE}
	public static Direction currentDirection=Direction.NONE;
	public static Direction previousDirection;
	public static Snake snake;
	public Apple apple;
	public KeyListener keyListener;
    static JLabel scoreBoard=new JLabel();
    static JLabel highScoreBoard=new JLabel();
	static JLabel pauseScreen=new JLabel();	
	static JLabel howToPlay=new JLabel();
	static JButton button;
	private int count=0;	
	static Body[] body = new Body[783];
	
	public void act() {
		// Checks the current direction of the snake, and moves it in that direction	
		switch (currentDirection) {		 
			case UP:				
				if (score==0) {
					snake.moveUp();
				}
				else if (score==1) {
					body[0].placeBody(snake.getX()/30,snake.getY()/30);
					snake.moveUp();
				}
				else {
					moveBody();
					snake.moveUp();
				}
				break;
			case DOWN:
				if (score==0) {
					snake.moveDown();
				}
				else if (score==1) {
					body[0].placeBody(snake.getX()/30,snake.getY()/30);
					snake.moveDown();
				}
				else {
					moveBody();
					snake.moveDown();
				}
				break;
			case LEFT:
				if (score==0) {
					snake.moveLeft();
				}
				else if (score==1) {
					body[0].placeBody(snake.getX()/30,snake.getY()/30);
					snake.moveLeft();
				}
				else {
					moveBody();
					snake.moveLeft();
				}
				break;
			case RIGHT:
				if (score==0) {
					snake.moveRight();
				}
				else if (score==1) {
					body[0].placeBody(snake.getX()/30,snake.getY()/30);
					snake.moveRight();
				}
				else {
					moveBody();
					snake.moveRight();
				}
				break;
		}		
		// Checks if the snake has collided with the apple, increases the score, and adds a body part
		if (snake.collides(apple)) {
			score++;
			// Adds on to the snake's tail
			body[score-1]=new Body();
			body[score-1].setSize(30,30);	
			if (score%2==0) {
				body[score-1].setColor(Color.GREEN);	
			}
			else {
				body[score-1].setColor(Color.ORANGE);
			}
				switch (currentDirection) {		
				case UP:
					body[score-1].placeBody(snake.getX()/30,snake.getY()/30+1);
					add(body[score-1]);
					break;
				case DOWN:
					body[score-1].placeBody(snake.getX()/30,snake.getY()/30-1);	
					add(body[score-1]);
					break;
				case LEFT:
					body[score-1].placeBody(snake.getX()/30+1,snake.getY()/30);	
					add(body[score-1]);
					break;
				case RIGHT:
					body[score-1].placeBody(snake.getX()/30-1,snake.getY()/30);	
					add(body[score-1]);
					break;					
				}					
			scoreBoard.setText("Score: "+score);
			apple.newApple();
		}
		// Checks all the losing conditions, which are bumping into any of the walls or the body of the snake itself
		if (snake.getY()>=getFieldHeight()-30 || snake.getY()<0 || snake.getX()>=getFieldWidth()-30 || snake.getX()<0 || ifBodyCollision()) {
			youLost();
		}
	}
	// Sets up the game 
	public void setup() {
		setDelay(400); // Sets the tickspeed so that the game is actually playable	
		setTitle("Snake");
		// Sets up the snake
		snake=new Snake();
		snake.setSize(30,30);
		snake.placeSnake(14,13);
        snake.setColor(Color.GREEN);
        add(snake);	
        // Sets up the apple
        apple=new Apple();
        apple.setSize(30,30);
        apple.placeApple(14,8);
        apple.setColor(Color.RED);
        add(apple);
        // Sets up the scoreboard
        add(scoreBoard);
        scoreBoard.setForeground(Color.white);
        scoreBoard.setBounds(390, 5, 60, 60);
        scoreBoard.setText("Score: "+score);
        // Sets up the pause screen message ("Game Paused")
        pauseScreen.setForeground(Color.white);
        pauseScreen.setBounds(390, getFieldHeight()/2, 200, 200);
        add(pauseScreen);
        pauseScreen.setText("");
        // Sets up the high score board
        highScoreBoard.setForeground(Color.white);
        highScoreBoard.setBounds(390, 5, 90, 90);
        add(highScoreBoard);
        highScoreBoard.setText("HighScore: "+highScore);
        // Sets up the game info and instructions at the very beginning of the game
 	    String text="<html> <h2>Welcome to Snake!</h2>	   <h3>How to play:</h3>	   <ul>	   <li>Use the arrow keys to control your snake</li>	   <li>Guide your snake to the apples to increase your score</li>	   <li>You lose if you crash into a wall or your tail</li>	   </ul> <h3>Have fun!</h3>	   </html>";
 	    howToPlay.setText(text);
 	    howToPlay.setForeground(Color.WHITE);
 	    howToPlay.setVisible(true);
 	    howToPlay.setBounds(310, 355, 400, 400);
        add(howToPlay);
        // Sets up the button to start playing the game
        button=new JButton("Ok");
		button.setBounds(380, 700, 90,30);
		button.setForeground(Color.black);
		add(button);
		button.setVisible(true);	
	    button.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    	           currentDirection=Direction.UP;
	    	           button.setVisible(false);
	    	           howToPlay.setVisible(false);
	    	        }  	    	
	    	    });  	   
	}	
	// Returns true if the snake has collided with a body part 
	public boolean ifBodyCollision() {
		for (int i=0; i<score; ++i) {
			if (snake.collides(body[i])) {
				return true;
			}
		} 
		return false;
	}
	// Moves all of the snake's body parts to the location of the one before it 
	public void moveBody() {
		for (int i=score-1; i>0; --i) {
			body[i].placeBody(body[i-1].getX()/30,body[i-1].getY()/30);
		}
		body[0].placeBody(snake.getX()/30,snake.getY()/30);
	}
	// Called when you lose, sets score to 0, and resets the game 
	public void youLost() {
		for (int i=0; i<=score-1; ++i) {
			body[i].placeBody(100,100);
		}
		currentDirection=Direction.NONE;
		pauseScreen.setText("You Lost! Your score was "+score+".");
		if (score>highScore) {
			highScore=score;
		}
		score=0;
		scoreBoard.setText("Score: "+score);
		highScoreBoard.setText("Highscore: "+highScore);
		snake.placeSnake(14,13);
		apple.placeApple(14,8); 	
	}
	// Called when the player pauses the game, this stops the snake and shows a message 
	public static void pauseGame() {
		previousDirection=currentDirection;
		currentDirection=Direction.NONE;
        pauseScreen.setText("Game paused.");
	}
	
	public static void main(String[] args) {
		SnakeGame s = new SnakeGame();
        s.setSize(887, 873);
        s.setVisible(true);
        s.initComponents();
		s.setLocationRelativeTo(null); // Sets the window in the middle of your screen
		s.setFocusable(true);
		s.setBackground(Color.DARK_GRAY);

		// The rest of this code makes it so that the snake is controlled by the arrow keys
		s.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {								
			}
			@Override
			public void keyPressed(KeyEvent e) {		
				int keyCode=e.getKeyCode();								
				if (!button.isVisible() ) {
					switch (keyCode) {
					case KeyEvent.VK_UP:
						if (currentDirection!=Direction.DOWN) {
							currentDirection=Direction.UP;
							pauseScreen.setText("");

						}
						button.setVisible(false);
						break;
					case KeyEvent.VK_DOWN:
						if (currentDirection!=Direction.UP) {
							currentDirection=Direction.DOWN;
							pauseScreen.setText("");
						}
						button.setVisible(false);
						break;
					case KeyEvent.VK_LEFT:
						if (currentDirection!=Direction.RIGHT) {
							currentDirection=Direction.LEFT;	
							pauseScreen.setText("");
						}
						button.setVisible(false);
						break;
					case KeyEvent.VK_RIGHT:
						if (currentDirection!=Direction.LEFT) {
							currentDirection=Direction.RIGHT;
							pauseScreen.setText("");
						}
						button.setVisible(false);
						break;
					case KeyEvent.VK_SPACE:
						if (currentDirection!=Direction.NONE) {
							pauseGame();							
						}
						else {
							if (score==0) {
								currentDirection=Direction.UP;
							}
							else {
								currentDirection=previousDirection;
							}						
							pauseScreen.setText("");
							scoreBoard.setText("Score: "+score);
							highScoreBoard.setText("Highscore: "+highScore);
							button.setVisible(false);
						}
					}	
				}				
			}
			@Override
			public void keyReleased(KeyEvent e) {								
			}
			
		});
	}
}
