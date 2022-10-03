
public class Bishop extends Piece 
{

	public Bishop(Color newColor, Location newLocation, Board newBoard)
	{
		super(newColor, newLocation, newBoard);
	}
	
	public static boolean IsLocationDiagonalToLocation(Location originLocation, Location targetLocation)
	{
		Location tempLocation;
		
		//Iterate through all possible diagonal tiles of this location
		//Going from current location to the top right
		//Then from current location to bottom left
		
		int i = originLocation.GetRow();
		int j = originLocation.GetColumn();
		while(i < 8 && j < 8)
		{
			tempLocation = new Location(i, j);
			
			if (tempLocation.equals(targetLocation))
				return true;
				
			i++;
			j++;
		}
		
		//Now we go back, and iterate all diagonals towards bottom left
		i = originLocation.GetRow();
		j = originLocation.GetColumn();
		while (i > -1 && j > -1)
		{
			tempLocation = new Location(i, j);
			if (tempLocation.equals(targetLocation))
				return true;
				
			i--;
			j--;
		}
		
		//Checked all diagonal tiles of originLocation and didn't find targetLocation!
		return false;
	}
		
	public static boolean IsLocationAntiDiagonalToLocation(Location originLocation, Location targetLocation)
	{
		Location tempLocation;
		
		//Iterate through all possible diagonal tiles of this location
		//Going from current location to the top left
		//Then from current location to bottom right
		
		int i = originLocation.GetRow();
		int j = originLocation.GetColumn();
		while (i < 8 && j > -1)
		{
			tempLocation = new Location(i, j);
			if (tempLocation.equals(targetLocation))
				return true;
			
			i++;
			j--;
		}
		
		//Downwards towards bottom right
		i = originLocation.GetRow();
		j = originLocation.GetColumn();
		while (i > -1 && j < 8)
		{
			tempLocation = new Location(i, j);
			if (tempLocation.equals(targetLocation))
				return true;
			
			i--;
			j++;
		}
		
		//Checked all antidiagonal tiles of originLocation and didn't find targetLocation!
		return false;
	}

	//@Override
	public boolean canMoveTo(Location newLocation)
	{
		try//There is definitely a smarter way to find this instead of bruteforcing all tiles and checking if diagonal but whatever
		{
			if (Bishop.IsLocationDiagonalToLocation(currentLocation, newLocation))
			{
				if (currentBoard.FreeDiagonalPath(currentLocation, newLocation))
					return true;
				else //A tile between bishop and newLocation is occupied.
					throw new InvalidMoveException("Υπάρχει ένα πιόνι στην διαγώνια γραμμή, όπου εμποδίζει τον αξιωματικό σου.");
			}
			else if (Bishop.IsLocationAntiDiagonalToLocation(currentLocation, newLocation))
			{
				if (currentBoard.FreeAntiDiagonalPath(currentLocation, newLocation))
					return true;
				else //A tile between bishop and newLocation is occupied.
					throw new InvalidMoveException("Υπάρχει ένα πιόνι στην διαγώνια γραμμή, όπου εμποδίζει τον αξιωματικό σου.");
			}
			else
				throw new InvalidMoveException("Η θέση " + newLocation + " δεν βρίσκεται διαγώνια της θέσης " + currentLocation);
			
		}
		catch (InvalidMoveException exception)
		{
			System.out.println(exception.getMessage());
		}
		
		return false;
	}
	
	//@Override
	public String toString()
	{
		if (currentColor == Color.WHITE)
			return "B";
		else
			return "b";
	}

}
