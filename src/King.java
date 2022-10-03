
public class King extends Piece 
{

	public King(Color newColor, Location newLocation, Board newBoard) 
	{
		super(newColor, newLocation, newBoard);
	}

	//@Override
	public boolean canMoveTo(Location newLocation)
	{
		Location tempLocation = Location.GetAbsoluteDistanceFrom(currentLocation, newLocation);
		
		try
		{
			if (tempLocation.GetRow() > 1 || tempLocation.GetColumn() > 1)
				throw new InvalidMoveException("Ο βασιλιάς μπορεί να κινηθεί μόνο 1 τετράγωνο μακριά\nΠροσπάθησες να κινήσεις τον βασιλιά " + Location.GetAbsoluteDistanceFrom(currentLocation, newLocation).GetAddedRowAndColumn() + " τετράγωνα μακριά!");
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
			return "K";
		else
			return "k";
	}

}
