import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;

public class PacmanGhost {

	private int UNIT_SIZE = 40;
	String direction = "W"; //Direction that the Ghost is travelling.
	private int x = 1200; //Holds x coordinates of the ghost.
	private int y = 640; //Holds y coordinates of the ghost.
	private Color color = Color.white; //Colour of Ghost.
	private LinkedList<String> validDirections = new LinkedList<String>(); //This are the directions that the ghost can travel in. Ghost cannot do 180 degree turns.
	
	//Used to draw Ghost on the panel (each tick).
	public void draw(Graphics g) 
	{
		g.setColor(color);
		g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);
		
	}
	
	public void move()
	{
		if (direction.equals("W"))
		{
			x = x - UNIT_SIZE;
		}
		else if (direction.equals("E"))
		{
			x = x + UNIT_SIZE;
		}
		else if (direction.equals("N"))
		{
			y = y - UNIT_SIZE;
		}
		else if (direction.equals("S"))
		{
			y = y + UNIT_SIZE;
		}
		
	}
	
	
	/** Function will direct the ghost towards the player via valid routes.
	 * 
	 * @param playerX - is the x coordinate of the player.
	 * @param playerY - is the y coordinate of the player.
	 * @param backgroundMap - is the game map that the ghost must navigate.
	 */
	public void pathfindToPlayer(int playerX, int playerY, HashMap<String, BackgroundCell> backgroundMap)
	{
		LinkedList<Integer> distanceToPlayer = new LinkedList<Integer>(); //Stores distance from ghost to player, order is the same as validDirections
		int xDistance;	//Temporarily stores the x distance between ghost and player.
		int yDistance;	//Temporarily stores the y distance between ghost and player.
		int smallestDistance = 1000;	//Temporarily stores the shortest distance to the player. Starts at 1000 as all real distance are smaller and will therefore overwrite this value.
		int smallestDistanceIndex = 0;	//Temporarily stores the index of the smallest index in the distanceToPlayer LinkedList.
		
		validDirections.clear();
		
		//Look at the four cells around the ghost to see which are not walls. Add those to array of valid directions.
		if (backgroundMap.get(Integer.toString(x + UNIT_SIZE) + Integer.toString(y)).isWall() ==  false)
		{
			validDirections.add("E");
			System.out.println("Adding: E");
		}
		
		if (backgroundMap.get(Integer.toString(x - UNIT_SIZE) + Integer.toString(y)).isWall() ==  false)
		{
			validDirections.add("W");
			System.out.println("Adding: W");
		}
		
		if (backgroundMap.get(Integer.toString(x) + Integer.toString(y + UNIT_SIZE)).isWall() ==  false)
		{
			validDirections.add("S");
			System.out.println("Adding: S");
		}
		
		if (backgroundMap.get(Integer.toString(x) + Integer.toString(y - UNIT_SIZE)).isWall() ==  false)
		{
			validDirections.add("N");
			System.out.println("Adding: N");
		}
		
		
		
		//Remove opposite direction of current travel from list of valid directions
		for (int i = 0; i < validDirections.size(); i++)
		{
			
			if (direction.equals("N") && validDirections.get(i).equals("S"))
			{
				validDirections.remove(i);
				System.out.println("Removing: S");
			}
			
			if (direction.equals("S") && validDirections.get(i).equals("N"))
			{
				validDirections.remove(i);
				System.out.println("Removing: N");
			}
			
			if (direction.equals("W") && validDirections.get(i).equals("E"))
			{
				validDirections.remove(i);
				System.out.println("Removing: E");
			}
			
			if (direction.equals("E") && validDirections.get(i).equals("W"))
			{
				validDirections.remove(i);
				System.out.println("Removing: W");
			}
		}
		
		//Check valid directions to decide which direction will get the ghost closer to the player. Direction distances are stored in the same order as the validDirections list.
		for (int i = 0; i < validDirections.size(); i++)
		{
			
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
				
				distanceToPlayer.add(xDistance + yDistance);
			}
			
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
				
				distanceToPlayer.add(xDistance + yDistance);
			}
			
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
				
				distanceToPlayer.add(xDistance + yDistance);
			}
			
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
				
				distanceToPlayer.add(xDistance + yDistance);
			}
		}
		
		for (int i = 0; i < distanceToPlayer.size(); i++)	//Look for the valid direction with the shortest Manhattan distance to the player
		{
			
			System.out.println(validDirections.get(i) + " " + distanceToPlayer.get(i));
			
			if (distanceToPlayer.get(i) < smallestDistance)
			{
				smallestDistance = distanceToPlayer.get(i);
				smallestDistanceIndex = i;
				
				//System.out.println(validDirections.get(i));
				//System.out.println(smallestDistance);
				
			}
			else if ((distanceToPlayer.get(i) == smallestDistance) && (Math.random() < 0.5)) //When two paths have an equal distance to target, ghost will make a random decision with 50% chance
			{
				smallestDistance = distanceToPlayer.get(i);
				smallestDistanceIndex = i;
			}
			
		}
		
		direction = validDirections.get(smallestDistanceIndex);
		System.out.println("Going: " + direction);
		System.out.println();
		
		

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
