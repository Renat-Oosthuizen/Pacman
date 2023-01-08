import java.awt.Color;
import java.awt.Graphics;

//This is a square cell that the screen is divided into. Cells can be walls which cannot be travelled into. The can also contain points for increasing player score
public class BackgroundCell {
	
	int UNIT_SIZE = 40; //Size of the square cell (width and height in pixels)
	int x; //X coordinate of the cell
	int y; //Y coordinate of the cell
	boolean wall; //Tracks if the cell is a wall
	boolean point; //Tracks if the cell contains a point that the player can get
	Color colour; //Colour of the cell
	
	//Constructor
	BackgroundCell(int type, int x, int y)
	{
		this.x = x; //Assign the x coordinate
		this.y = y; //Assign the y coordinate
		
		if (type == 1) //If the cell is a wall based on PacmanMap then...
		{
			wall = true; //Is a wall
			point = false; //Does not hold a point
			colour = Color.blue; //Is blue in colour
				
		}
		else if (type == 0) //If the cell is not a wall based on PacmanMap then...
		{
			wall = false; //Not a wall
			point = true; //Holds a point
			colour = Color.yellow; //Is yellow in colour
		}
	}
	
	//Used to draw the cell on the panel (each tick).
	public void draw(Graphics g) 
	{
		if (wall == true) //If the cell is a wall...
		{
			g.setColor(colour); //Set the colour of the Graphics class that will be used to draw the particle
			g.fillRect(x, y, UNIT_SIZE, UNIT_SIZE); //Draw a square at the cells coordinates
		}
		if (point == true) //If the cell contains a point...
		{
			g.setColor(colour); //Set the colour of the Graphics class that will be used to draw the particle
			g.fillOval(x + UNIT_SIZE/4, y + UNIT_SIZE/4, UNIT_SIZE/2, UNIT_SIZE/2); //Draw a circle that is half the size of the cell in the middle of the cell. Note: Background has already been painted black in PacmanPanel constructor
		}
	}

	//-------------------------------------------GETTERS----------------------------------------
	public boolean isWall() {
		return wall;
	}

	public boolean isPoint() {
		return point;
	}

	//-------------------------------------------SETTERS----------------------------------------
	public void setWall(boolean wall) {
		this.wall = wall;
	}

	public void setPoint(boolean point) {
		this.point = point;
	}
}
