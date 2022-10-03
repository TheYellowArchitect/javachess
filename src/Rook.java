public class Rook extends Piece 
{

	public Rook(Color newColor, Location newLocation, Board newBoard)
	{
		super(newColor, newLocation, newBoard);
	}

	//@Override
	public boolean canMoveTo(Location newLocation)
	{
		try
		{
			//Vertical movement
			if (currentLocation.sameColumn(newLocation))
			{
				if (currentBoard.FreeVerticalPath(currentLocation, newLocation))
					return true;
				else //Occupied in between
					throw new InvalidMoveException("Υπάρχει ένα πιόνι στην κάθετη γραμμή, όπου εμποδίζει τον πύργο σου.");
			}
			//Horizontal movement
			else if (currentLocation.sameRow(newLocation))
			{
				if(currentBoard.FreeHorizontalPath(currentLocation, newLocation))
					return true;
				else //Occupied in between
					throw new InvalidMoveException("Υπάρχει ένα πιόνι στην οριζόντια γραμμή, όπου εμποδίζει τον πύργο σου.");
			}
			else//Not same in either row or column
			{
				throw new InvalidMoveException("Η θέση " + newLocation + " δεν είναι στην ίδια γραμμή με την θέση του πύργου " + currentLocation);
			}
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
			return "R";
		else
			return "r";
	}

}
