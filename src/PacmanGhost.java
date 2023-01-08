import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;

//This class is responsible for drawing the Ghost and the AI for Ghost navigation
public class PacmanGhost {

	private int UNIT_SIZE = 40; //The size of each cell
	String direction = "W"; //Direction that the Ghost is travelling
	private int x = 1200; //Holds x coordinates of the ghost
	private int y = 640; //Holds y coordinates of the ghost
	private Color color = Color.white; //Colour of Ghost
	private LinkedList<String> validDirections = new LinkedList<String>(); //This are the directions that the ghost can travel in. Ghost cannot do 180 degree turns
	
	//Used to draw Ghost on the panel (each tick)
	public void draw(Graphics g) 
	{
		g.setColor(color); //Set the colour of the Graphics class that will be used to draw the Ghost
		g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE); //Draw the Ghost (an oval at the provided x and y coordinates (upper left corner) with provided width and height)	
	}
	
	//Method used to move the Ghost (coordinate change only) left, right, up and down
	public void move()
	{
		if (direction.equals("W")) //Move left
		{
			x = x - UNIT_SIZE;
		}
		else if (direction.equals("E"))  //Move right
		{
			x = x + UNIT_SIZE;
		}
		else if (direction.equals("N")) //Move up
		{
			y = y - UNIT_SIZE;
		}
		else if (direction.equals("S")) //Move down
		{
			y = y + UNIT_SIZE;
		}	
	}
	
	//Method will direct the Ghost towards the player via valid routes. Parameters are the player x and y coordinates and the game map that the Ghost must navigate
	//It would be better to use A* to make sure that the Ghost always takes the best path but I didn't know how to use A* when I wrote this (I do now)
	public void pathfindToPlayer(int playerX, int playerY, HashMap<String, BackgroundCell> backgroundMap)
	{
		LinkedList<Integer> distanceToPlayer = new LinkedList<Integer>(); //Stores distance from ghost to player, order is the same as validDirections
		int xDistance; //Temporarily stores the x distance between ghost and player
		int yDistance; //Temporarily stores the y distance between ghost and player
		int smallestDistance = 1000; //Temporarily stores the shortest distance to the player. Starts at 1000 as all real distance are smaller and will therefore overwrite this value
		int smallestDistanceIndex = 0; //Temporarily stores the index of the smallest index in the distanceToPlayer LinkedList
		
		validDirections.clear(); //Clear the valid movement directions as this must be recalculated each tick
		
		//Look at the four cells around the ghost to see which are not walls. Add those to array of valid directions
		if (backgroundMap.get(Integer.toString(x + UNIT_SIZE) + Integer.toString(y)).isWall() ==  false)
		{
			validDirections.add("E");
		}
		
		if (backgroundMap.get(Integer.toString(x - UNIT_SIZE) + Integer.toString(y)).isWall() ==  false)
		{
			validDirections.add("W");
		}
		
		if (backgroundMap.get(Integer.toString(x) + Integer.toString(y + UNIT_SIZE)).isWall() ==  false)
		{
			validDirections.add("S");
		}
		
		if (backgroundMap.get(Integer.toString(x) + Integer.toString(y - UNIT_SIZE)).isWall() ==  false)
		{
			validDirections.add("N");
		}
		
		//Remove opposite direction of current travel from list of valid directions
		for (int i = 0; i < validDirections.size(); i++)
		{
			
			if (direction.equals("N") && validDirections.get(i).equals("S"))
			{
				validDirections.remove(i);
			}
			
			else if (direction.equals("S") && validDirections.get(i).equals("N"))
			{
				validDirections.remove(i);
			}
			
			else if (direction.equals("W") && validDirections.get(i).equals("E"))
			{
				validDirections.remove(i);
			}
			
			else if (direction.equals("E") && validDirections.get(i).equals("W"))
			{
				validDirections.remove(i);
			}
		}
		
		//Check valid directions to decide which direction will get the ghost closer to the player. Direction distances are stored in the same order as the validDirections list.
		for (int i = 0; i < validDirections.size(); i++)
		{
			//Calculate distance to the player from the cell below the ghost
			if (validDirections.get(i).equals("S"))
			{
				//Calculate the xDistance from new position and convert it to a positive integer if it is negative
				xDistance = (playerX - x)/UNIT_SIZE;
				if (xDistance < 0) 
				{
					xDistance = xDistance * (-1);
				};
				
				//Calculate the yDistance from new position and convert it to a positive integer if it is negative
				yDistance = (playerY - (y + UNIT_SIZE))/UNIT_SIZE;
				if (yDistance < 0) 
				{
					yDistance = yDistance * (-1);
				};
				
				distanceToPlayer.add(xDistance + yDistance); //Add distance to player to the linked list (in the same order as the validDirections linked list)
			}
			
			//Calculate distance to the player from the cell above the ghost
			if (validDirections.get(i).equals("N"))
			{
				//Calculate the xDistance from new position and convert it to a positive integer if it is negative
				xDistance = (playerX - x)/UNIT_SIZE;
				if (xDistance < 0) 
				{
					xDistance = xDistance * (-1);
				};
				
				//Calculate the yDistance from new position and convert it to a positive integer if it is negative
				yDistance = (playerY - (y - UNIT_SIZE))/UNIT_SIZE;
				if (yDistance < 0) 
				{
					yDistance = yDistance * (-1);
				};
				
				distanceToPlayer.add(xDistance + yDistance); //Add distance to player to the linked list (in the same order as the validDirections linked list)
			}
			
			//Calculate distance to the player from the cell right of the ghost
			if (validDirections.get(i).equals("E"))
			{
				//Calculate the xDistance from new position and convert it to a positive integer if it is negative
				xDistance = (playerX - (x + UNIT_SIZE))/UNIT_SIZE;
				if (xDistance < 0) 
				{
					xDistance = xDistance * (-1);
				};
				
				//Calculate the yDistance from new position and convert it to a positive integer if it is negative
				yDistance = (playerY - y)/UNIT_SIZE;
				if (yDistance < 0) 
				{
					yDistance = yDistance * (-1);
				};
				
				distanceToPlayer.add(xDistance + yDistance); //Add distance to player to the linked list (in the same order as the validDirections linked list)
			}
			
			//Calculate distance to the player from the cell left of the ghost
			if (validDirections.get(i).equals("W"))
			{
				//Calculate the xDistance from new position and convert it to a positive integer if it is negative
				xDistance = (playerX - (x - UNIT_SIZE))/UNIT_SIZE;
				if (xDistance < 0) 
				{
					xDistance = xDistance * (-1);
				};
				
				//Calculate the yDistance from new position and convert it to a positive integer if it is negative
				yDistance = (playerY - y)/UNIT_SIZE;
				if (yDistance < 0) 
				{
					yDistance = yDistance * (-1);
				};
				
				distanceToPlayer.add(xDistance + yDistance); //Add distance to player to the linked list (in the same order as the validDirections linked list)
			}
		}
		
		//Look for the valid direction with the shortest Manhattan distance to the player
		for (int i = 0; i < distanceToPlayer.size(); i++)
		{
			
			if (distanceToPlayer.get(i) < smallestDistance) //If this cell has a smaller shorter distance to the player then...
			{
				smallestDistance = distanceToPlayer.get(i); //The is the shortest know distance to the player
				smallestDistanceIndex = i; //This is the index of the shortest distance to the player
				
			}
			else if ((distanceToPlayer.get(i) == smallestDistance) && (Math.random() < 0.5)) //When two paths have an equal distance to target, ghost will make a random decision with 50% chance
			{
				smallestDistance = distanceToPlayer.get(i); //The is the shortest know distance to the player
				smallestDistanceIndex = i; //The is the shortest know distance to the player
			}
			
		}
		direction = validDirections.get(smallestDistanceIndex); //Select the direction to travel in
	}

	//-------------------------------------------GETTERS-----------------------------------
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	//-------------------------------------------SETTERS-----------------------------------
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
