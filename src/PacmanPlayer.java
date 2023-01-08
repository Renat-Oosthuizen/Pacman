import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

//This class is responsible for Pacman's movement, drawing Pacman and tracking Pacman's score
public class PacmanPlayer {

	private int UNIT_SIZE = 40; //The size of each cell
	String direction = ""; //Direction that Pacman is travelling
	private int x = 40; //Holds x coordinates of a particle
	private int y = 40; //Holds y coordinates of a particle
	private Color color = Color.yellow; //Colour of Pacman
	private int score = 0; //The final score of the player
	
	//Used to draw Pacman on the panel (each tick)
	public void draw(Graphics g) 
	{
		g.setColor(color); //Set the colour of the Graphics class that will be used to draw Pacman
		g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);	//Draw Pacman (an oval at the provided x and y coordinates (upper left corner) with provided width and height)
	}
	
	//Method responsible for moving Pacman (coordinate change only) left, right, up and down
	public void move(HashMap<String, BackgroundCell> backgroundMap)
	{
		collisionCheck(backgroundMap); //Check if Pacman is facing a wall. If so, stop movement
		
		if (direction.equals("W")) //Move left
		{
			x = x - UNIT_SIZE;
			eatPoint(backgroundMap); //If currently occupied cell contains a point then consume it and increase score
		}
		else if (direction.equals("E")) //Move right
		{
			x = x + UNIT_SIZE;
			eatPoint(backgroundMap); //If currently occupied cell contains a point then consume it and increase score
		}
		else if (direction.equals("N")) //Move up
		{
			y = y - UNIT_SIZE;
			eatPoint(backgroundMap); //If currently occupied cell contains a point then consume it and increase score
		}
		else if (direction.equals("S")) //Move down
		{
			y = y + UNIT_SIZE;
			eatPoint(backgroundMap); //If currently occupied cell contains a point then consume it and increase score
		}
	}
	
	//Method to check if the next cell in Pacman's direction of movement contains a wall. If it does stop Pacman from moving by removing his movement direction
	public void collisionCheck(HashMap<String, BackgroundCell> backgroundMap)
	{
		if (direction.equals("W")) //If heading West and the cell to the left is a wall then stop movement
		{
			if (backgroundMap.get(Integer.toString(x - UNIT_SIZE) + Integer.toString(y)).isWall())
			{
				direction = "";	//Collision has occurred. Player stops moving
			}
		}
		else if (direction.equals("E")) //If heading East and the cell to the right is a wall then stop movement
		{
			if (backgroundMap.get(Integer.toString(x + UNIT_SIZE) + Integer.toString(y)).isWall())
			{
				direction = "";	//Collision has occurred. Player stops moving
			}
		}
		else if (direction.equals("N")) //If heading North and the cell to above is a wall then stop movement
		{
			if (backgroundMap.get(Integer.toString(x) + Integer.toString(y - UNIT_SIZE)).isWall())
			{
				direction = "";	//Collision has occurred. Player stops moving
			}
		}
		else if (direction.equals("S")) //If heading South and the cell below is a wall then stop movement
		{
			if (backgroundMap.get(Integer.toString(x) + Integer.toString(y + UNIT_SIZE)).isWall())
			{
				direction = ""; //Collision has occurred. Player stops moving
			}
		}
		
	}
	
	//Method for Pacman to eat point on the currently occupied tile if one is available. Score increases by 1
	public void eatPoint(HashMap<String, BackgroundCell> backgroundMap)
	{
		if (backgroundMap.get(Integer.toString(x) + Integer.toString(y)).isPoint()) //If the cell occupied by Pacman contains a point...
		{
			backgroundMap.get(Integer.toString(x) + Integer.toString(y)).setPoint(false); //...remove the point from the cell
			score++; //Increase score
		}
	}

	//------------------------------------------GETTERS--------------------------------------------
	public int getScore() {
		return score;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	//------------------------------------------SETTERS--------------------------------------------
	public void setScore(int score) {
		this.score = score;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
