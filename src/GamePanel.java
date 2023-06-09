// package Game;

/*
This code implements the classic game of Snake using Java Swing. It contains a GamePanel class that extends 
JPanel and implements the ActionListener interface. The GamePanel class contains the game logic and all the
necessary methods for the game to function, such as starting and restarting the game, drawing the game graphics, 
moving the snake, checking for collisions, and handling user input. 

The main method of the GamePanel class creates a JFrame, sets the preferred size, and adds a new GamePanel 
object to the JFrame. The Snake game starts automatically when the user opens the JFrame. The user controls 
the movement of the snake with the arrow keys on the keyboard.

The Snake object is represented by an array of x and y coordinates. The snake can move in four directions: 
up, down, left, and right. The game generates apples at random locations on the game board. When the snake 
eats an apple, its body grows longer, and the player's score increases.

The game includes several features such as changing the snake color every ten apples eaten, displaying the 
player's score, and restarting the game when the snake collides with the border or its own body.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
	
	final int SCREEN_WIDTH;
	final int SCREEN_HEIGHT;
	static final int UNIT_SIZE = 25;
	final int GAME_UNITS;
	static final int DELAY = 75;
	final int x[];
	final int y[];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	JFrame frame;
	MyKeyAdapter keyAdapter = new MyKeyAdapter();
	
	public GamePanel(JFrame frame, int w, int h) {
		SCREEN_WIDTH = w;
		SCREEN_HEIGHT = h;
		GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
		this.x = new int[GAME_UNITS];
		this.y = new int[GAME_UNITS];
		this.frame = frame;
		random = new Random();
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(Color.BLUE);
		setFocusable(true);
		addKeyListener(keyAdapter);
		startGame();
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void restart() {
		bodyParts = 6;
		direction = 'R';
		applesEaten = 0;
		for (int i = bodyParts; i >= 0; i--) {
			x[i] = 0;
			y[i] = 0;
		}
		startGame();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if (running) {
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			timer.setDelay(DELAY);
			for (int i = 0; i < bodyParts; i++) {
				if (applesEaten % 10 == 0 && applesEaten != 0) {
					timer.setDelay(45);
					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else if (i == 0) {
					g.setColor(new Color(200, 200, 200));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(255, 255, 255));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.RED);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		} else {
		}
	}
		
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;	
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;	
	}
	
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		
		// Checks if head collides with body
		for(int i = bodyParts; i>0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
		// Check if head touches left border
		if(x[0]<0) {
			running = false;
		}
		
		// Check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}

		// Check if head touches top border
		if (y[0] < 0) {
			running = false;
		}
		
		// Check if head touches top border
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if (!running) {
			timer.stop();
		}
	}

	public void actionPerformed(ActionEvent event) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}

