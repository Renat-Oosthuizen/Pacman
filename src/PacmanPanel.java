import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PacmanPanel extends JPanel implements ActionListener {
	
	int UNIT_SIZE = 40; //Size of each cell in the game.
	int WIDTH = 1280;
	int HEIGHT = 720;
	static final int DELAY = 200; //Used for timer.
	boolean running = false;
	Timer timer; //Will hold an instance of the Timer class. Timer will repaint the panel every tick.
	HashMap<String, BackgroundCell> backgroundMap = new HashMap<String, BackgroundCell>();
	private PacmanPlayer player = new PacmanPlayer();
	private PacmanGhost ghost = new PacmanGhost();
	
	PacmanPanel()
	{
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		startGame();
	}

	public void startGame() {
		
		PacmanMap pacmap = new PacmanMap();
		int a = -1; //PacmanMap nested array index
		
		//Create instances of background cells and add them to backgroundMap.
		for (int y = 0; y < HEIGHT; y = y + UNIT_SIZE) //For each set of x and y coordinates that are a UNIT_WIDTH apart create a new instance of background cell. Store in HashMap.
		{
			for (int x = 0; x < WIDTH; x = x + UNIT_SIZE)
			{
				a++;
				
				BackgroundCell backgroundCell = new BackgroundCell(pacmap.map[a], x, y);
				backgroundMap.put(Integer.toString(x) + Integer.toString(y), backgroundCell);
				
			}
		}
		
		running = true; //Set variable running to true as the game is now starting;
		timer = new Timer(DELAY, this); //Create a new instance of timer with a delay of 75 milliseconds between each tick. 
		timer.start(); //Start the timer.
	}
	
	//This function called by the system to draw the screen. The screen is drawn in layers so the previous position of the particles still exists below the black background.
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		drawScreen(g);
	}
	
	//Used for drawing the graphics of the game each tick.
	public void drawScreen(Graphics g) {
		
		if (running) 
		{
			
			//Iterate through HashMap (backgroundMap) and draw background cells.
			for (String i : backgroundMap.keySet()) 
			{
				backgroundMap.get(i).draw(g);
			}
			
			player.move(backgroundMap);
			ghost.pathfindToPlayer(player.getX(), player.getY(), backgroundMap);
			ghost.move();
			player.draw(g);
			ghost.draw(g);
			
			
			if (ghost.getX() == player.getX() && ghost.getY() == player.getY())
			{
				running = false;
			}
		}
		else
		{
			GameOver(g);
		}
		
	}
	
	//This happens every tick
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) 
		{
			
		}
		repaint();
	}
	
	public void GameOver(Graphics g) {
		
		//Simulation over text
		g.setColor(Color.red);
		g.setFont( new Font("Times New Roman", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (WIDTH - metrics2.stringWidth("Game Over"))/2, HEIGHT/2);
		g.drawString("Score: " + player.getScore(), (WIDTH - metrics2.stringWidth("Score: " + player.getScore()))/2, HEIGHT/4 );
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) 
			{
			case KeyEvent.VK_LEFT: //Go left if left key is pressed.
				player.direction = "W";
				break;
			case KeyEvent.VK_RIGHT: //Go right if right key is pressed.
				player.direction = "E";
				break;
			case KeyEvent.VK_UP: //Go up if up key is pressed.
				player.direction = "N";
				break;
			case KeyEvent.VK_DOWN: //Go down if down key is pressed.
				player.direction = "S";
				break;
			/*case KeyEvent.VK_ESCAPE: //Stop simulation if ESC key is pressed.
				if (running)
					{
						running = false;
					}
				else
				{
					running = true;
				}
				break;*/
			}
		}
		
	}
}
