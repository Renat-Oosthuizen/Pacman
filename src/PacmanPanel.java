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

//This class is responsible for displaying the game. It is the panel that goes inside the window/JFrame
@SuppressWarnings("serial")
public class PacmanPanel extends JPanel implements ActionListener {
	
	private int UNIT_SIZE = 40; //Game screen is divided into cells. This is the size of each square cell in the game (pixels).
	private int WIDTH = 1280; //Width of the panel (pixels)
	private int HEIGHT = 720; //Height of the panel (pixels)
	private static final int DELAY = 200; //Used for timer. A frame update/tick occurs every 200ms
	private boolean running = false; //Tracks if the game is running
	private Timer timer; //Will hold an instance of the Timer class. Timer will repaint the panel every tick by firing of an ActionEvent at each interval
	private HashMap<String, BackgroundCell> backgroundMap = new HashMap<String, BackgroundCell>(); //HashMap holding cells occupied by particles as value with their top-left corner coordinates as keys
	private PacmanPlayer player = new PacmanPlayer(); //An instance of the Player class responsible for the player controlled character
	private PacmanGhost ghost = new PacmanGhost(); //An instance of the ghost class responsible for the AI controlled ghost that chases the player
	
	//Constructor
	PacmanPanel()
	{
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); //Set the panel dimensions
		this.setBackground(Color.black); //Make the panel black
		this.setFocusable(true); //Panel is focusable so that it can accept keyboard inputs (unnecessary, focusable be default)
		this.addKeyListener(new MyKeyAdapter()); //Panel has key listeners allowing it to accept keyboard inputs
		
		startGame(); //Start the game
	}

	//Create  and start the game
	public void startGame() {
		
		PacmanMap pacmap = new PacmanMap(); //Create an instance of the game map
		int mapIndex = -1; //Stores index of PacmanMap map array. Used to create cell instances for each array index. Starts at -1 as it is immediately incremented to 0
		
		//Create instances of background cells and add them to backgroundMap.
		//For each set of x and y coordinates that are a UNIT_WIDTH apart create a new instance of background cell. Store in HashMap
		for (int y = 0; y < HEIGHT; y = y + UNIT_SIZE) 
		{
			for (int x = 0; x < WIDTH; x = x + UNIT_SIZE)
			{
				mapIndex++; //Increment the index
				
				BackgroundCell backgroundCell = new BackgroundCell(pacmap.map[mapIndex], x, y); //Create an instance of BackgroundCell using PacmanMap data
				backgroundMap.put(Integer.toString(x) + Integer.toString(y), backgroundCell); //Place the BackgroundCell instance into BackgroundMap HashMap using the coordinates as the key
				
			}
		}
		
		running = true; //Set variable running to true as the game is now starting
		timer = new Timer(DELAY, this); //Create a new instance of timer with a delay of 75 milliseconds between each tick.
		timer.start(); //Start the timer
	}
	
	//Method fires every tick of the Timer. Repaint the screen
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		repaint(); //Update the JPanel. Triggers paintComponent(Graphics g)
	}
	
	//Method overriding so that it calls the drawScreen(g) method. Method is automatically called by Java Swing GUI each tick. 
	//The screen is drawn in layers so the previous position of the particles still exists below the black background.
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g); //Perform the normal painting operation
		drawScreen(g); //Call the method for drawing the graphics of the game
	}
	
	//Used for drawing the graphics of the game each tick. Calls the draw(Graphics g) method inside each particle and background cell in order to paint them
	public void drawScreen(Graphics g) {
		
		if (running) 
		{
			//Iterate through HashMap (backgroundMap) and draw background cells.
			for (String i : backgroundMap.keySet()) 
			{
				backgroundMap.get(i).draw(g); //Redraw the map painting over the ghost and the player
			}
			
			player.move(backgroundMap); //Try to move the player in their currently selected direction (coordinate change)
			
			//If the player and the ghost are occupying the same coordinates then end the game.
			//A check here is necessary as other wise the Pacman can phase through the Ghost when both are moving towards each other and the number of cells between them is even
			if (ghost.getX() == player.getX() && ghost.getY() == player.getY())
			{
				running = false;
			}
			
			ghost.pathfindToPlayer(player.getX(), player.getY(), backgroundMap); //Ghost picks a valid direction that takes it closer to the player
			ghost.move(); //Move the ghost (coordinate change)
			player.draw(g); //Draw the player
			ghost.draw(g); //Draw the ghost
			
			//If the player and the ghost are occupying the same coordinates then end the game
			if (ghost.getX() == player.getX() && ghost.getY() == player.getY())
			{
				running = false;
			}
		}
		else
		{
			GameOver(g); //Otherwise run the GameOver(g) method
		}
		
	}
	
	//Method draws the screen for when the game is not running (running = false)
	public void GameOver(Graphics g) 
	{
		//Game over text
		g.setColor(Color.red); //Text colour is red
		g.setFont( new Font("Times New Roman", Font.BOLD, 75)); //Bold text of size 75 and font = "Times New Roman"
		FontMetrics metrics2 = getFontMetrics(g.getFont()); //Data about the font that has been set above. Used below for correct positioning
		g.drawString("Game Over", (WIDTH - metrics2.stringWidth("Game Over"))/2, HEIGHT/2); //Place the "Game Over" text in the centre of the screen
		g.drawString("Score: " + player.getScore(), (WIDTH - metrics2.stringWidth("Score: " + player.getScore()))/2, HEIGHT/4 ); //Place the player score below the "Game Over" text
	}
	
	//Inner class that has access to SimulationPanel variables and methods. It is responsible for reacting to key presses
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) 
		{	
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
			}
		}
	}
	
}
