public class Queen extends Piece 
{

	public Queen(Color newColor, Location newLocation, Board newBoard)
	{
		super(newColor, newLocation, newBoard);
	}

	//@Override
	public boolean canMoveTo(Location newLocation)
	{
		try 
		{
			//Diagonal
			if (Bishop.IsLocationDiagonalToLocation(currentLocation, newLocation))
			{
				if (currentBoard.FreeDiagonalPath(currentLocation, newLocation))
					return true;
				else //A tile between queen and newLocation is occupied.
					throw new InvalidMoveException("Υπάρχει ένα πιόνι στην διαγώνια γραμμή, όπου εμποδίζει την βασίλισσα σου.");
			}
			//AntiDiagonal
			else if (Bishop.IsLocationAntiDiagonalToLocation(currentLocation, newLocation))
			{
				if (currentBoard.FreeAntiDiagonalPath(currentLocation, newLocation))
					return true;
				else //A tile between queen and newLocation is occupied.
					throw new InvalidMoveException("Υπάρχει ένα πιόνι στην διαγώνια γραμμή, όπου εμποδίζει την βασίλισσα σου.");
			}
			//Horizontal Line
			else if (currentLocation.sameRow(newLocation))
			{
				if (currentBoard.FreeHorizontalPath(currentLocation, newLocation))
					return true;
				else //Occupied in between
					throw new InvalidMoveException("Υπάρχει ένα πιόνι στην οριζόντια γραμμή, όπου εμποδίζει την βασίλισσα σου.");
			}
			else if (currentLocation.sameColumn(newLocation))
			{
				if (currentBoard.FreeVerticalPath(currentLocation, newLocation))
					return true;
				else //Occupied in between
					throw new InvalidMoveException("Υπάρχει ένα πιόνι στην κάθετη γραμμή, όπου εμποδίζει την βασίλισσα σου.");
			}
			else
				throw new InvalidMoveException("Η θέση αυτή δεν είναι διαγώνια της βασίλισσας, αλλά ούτε οριζόντια ή κάθετη!\nΗ βασίλισσα μπορεί να κουνηθεί μόνο προς 1 οποιαδήποτε γραμμή!");
			
		}
		catch(InvalidMoveException exception)
		{
			System.out.println(exception.getMessage());
		}
		
		return false;
	}
	
	//@Override
	public String toString()
	{
		if (currentColor == Color.WHITE)
			return "Q";
		else
			return "q";
	}

}
