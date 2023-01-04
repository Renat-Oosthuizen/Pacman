import java.awt.Color;
import java.awt.Graphics;

public class BackgroundCell {
	
	int UNIT_SIZE = 40;
	int x;
	int y;
	boolean wall;
	boolean point;
	Color colour;
	
	//Constructor
	BackgroundCell(int type, int x, int y)
	{
		this.x = x;
		this.y = y;
		
		if (type == 1)
		{
			wall = true;
			point = false;
			colour = Color.blue;
				
		}
		else if (type == 0)
		{
			wall = false;
			point = true;
			colour = Color.yellow;
		}
	}
	
	//Used to draw the cell on the panel (each tick).
	public void draw(Graphics g) 
	{
		if (wall == true)
		{
			g.setColor(colour);
			g.fillRect(x, y, UNIT_SIZE, UNIT_SIZE);
		}
		if (point == true)
		{
			g.setColor(colour);
			g.fillOval(x + UNIT_SIZE/4, y + UNIT_SIZE/4, UNIT_SIZE/2, UNIT_SIZE/2);
		}

		
	}

	public boolean isWall() {
		return wall;
	}

	public boolean isPoint() {
		return point;
	}

	public void setWall(boolean wall) {
		this.wall = wall;
	}

	public void setPoint(boolean point) {
		this.point = point;
	}
}
