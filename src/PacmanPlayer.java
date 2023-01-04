import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

public class PacmanPlayer {

	private int UNIT_SIZE = 40;
	String direction = ""; //Direction that Pacman is travelling.
	private int x = 40; //Holds x coordinates of a particle.
	private int y = 40; //Holds y coordinates of a particle.
	private Color color = Color.yellow; //Colour of Pacman.
	private int score = 0; //The final score of the player.
	
	//Used to draw Pacman on the panel (each tick).
	public void draw(Graphics g) 
	{
		g.setColor(color);
		g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);
		//System.out.println("X: " + x + " " + "Y: " + y);
		
	}
	
	public void move(HashMap<String, BackgroundCell> backgroundMap)
	{
		
		collisionCheck(backgroundMap);
		
		if (direction.equals("W"))
		{
			x = x - UNIT_SIZE;
			eatPoint(backgroundMap);
		}
		else if (direction.equals("E"))
		{
			x = x + UNIT_SIZE;
			eatPoint(backgroundMap);
		}
		else if (direction.equals("N"))
		{
			y = y - UNIT_SIZE;
			eatPoint(backgroundMap);
		}
		else if (direction.equals("S"))
		{
			y = y + UNIT_SIZE;
			eatPoint(backgroundMap);
		}
		else
		{
			//Do nothing, ghost AI activates once Packman starts moving
		}
		
	}
	
	public void collisionCheck(HashMap<String, BackgroundCell> backgroundMap)
	
	{
		
		if (direction.equals("W"))
		{
			if (backgroundMap.get(Integer.toString(x - UNIT_SIZE) + Integer.toString(y)).isWall())
			{
				direction = "";	//Collision has occurred. Player stops moving.
			}
		}
		else if (direction.equals("E"))
		{
			if (backgroundMap.get(Integer.toString(x + UNIT_SIZE) + Integer.toString(y)).isWall())
			{
				direction = "";	//Collision has occurred. Player stops moving.
			}
		}
		else if (direction.equals("N"))
		{
			if (backgroundMap.get(Integer.toString(x) + Integer.toString(y - UNIT_SIZE)).isWall())
			{
				direction = "";	//Collision has occurred. Player stops moving.
			}
		}
		else if (direction.equals("S"))
		{
			if (backgroundMap.get(Integer.toString(x) + Integer.toString(y + UNIT_SIZE)).isWall())
			{
				direction = ""; //Collision has occurred. Player stops moving.
			}
		}
		
	}
	
	//Pacman eats point on the currently occupied tile if one is available. Score increases by 1.
	public void eatPoint(HashMap<String, BackgroundCell> backgroundMap)
	{
		if (backgroundMap.get(Integer.toString(x) + Integer.toString(y)).isPoint())
		{
			backgroundMap.get(Integer.toString(x) + Integer.toString(y)).setPoint(false);
			score++;
		}
		
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
