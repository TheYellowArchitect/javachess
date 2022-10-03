public class Horse extends Piece 
{
	public Horse(Color newColor, Location newLocation, Board newBoard) 
	{
		super(newColor, newLocation, newBoard);
	}
	
	//@Override
	public boolean canMoveTo(Location newLocation)
	{
		Location distanceLocation = Location.GetAbsoluteDistanceFrom(currentLocation, newLocation);
		
		try
		{
			if ((distanceLocation.GetColumn() == 2 && distanceLocation.GetRow() == 1) ||(distanceLocation.GetColumn() == 1 && distanceLocation.GetRow() == 2))
			{
				//Don't throw exception lol
			}
			else
				throw new InvalidMoveException("Ο ίππος μπορεί να κινηθεί μόνο κατά μήκος 3 τετραγώνων, οπου το τρίτο βρίσκεται σε άλλη ευθεία απ'τα 2 αρχικά.\nΚοινώς γνωστώς η κίνηση του μοιάζει με το σχήμα Γ");
		}
		catch(InvalidMoveException exception)
		{
			System.out.println(exception.getMessage());
			return false;
		}
		
		return true;
	}
	
	//@Override
	public String toString()
	{
		if (currentColor == Color.WHITE)
			return "H";
		else
			return "h";
	}

}
