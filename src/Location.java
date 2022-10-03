//Note that internal representation is 0-7 but visual/user representation is 1-8 and a-h
public class Location //Literally Vector2i but used for valid location in the board
{
	private int row;
	private int column;
	
	static final char[] columnLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
	
	public Location(int newRow, int newColumn)
	{
		try
		{
			if (newRow > 7 || newRow < 0 || newColumn > 7 || newColumn < 0)
			{
				if (newRow > 7)
				{
					throw new InvalidLocationException("Η σειρά που επέλεξες είναι παραπάνω απο 7, δηλαδή εκτός πίνακα!");	
				}
				else if (newRow < -1)
				{
					throw new InvalidLocationException("Η σειρά που επέλεξες είναι αρνητική, δηλαδή εκτός πίνακα!");	
				}
				if (newColumn > 7)
				{
					throw new InvalidLocationException("Η στήλη που επέλεξες είναι παραπάνω απο 7, δηλαδή εκτός πίνακα!");	
				}
				else if (newColumn < -1)
				{
					throw new InvalidLocationException("Η στήλη που επέλεξες είναι αρνητική, δηλαδή εκτός πίνακα!");
				}
				
			}
			else
			{
				row = newRow;
				column = newColumn;
			}
		}
		catch(InvalidLocationException exception)
		{
			System.out.println(exception.getMessage());
			row = -1;
			column = -1;
		}
	}
	
	public Location(String loc)
	{
		char tempColumnLetter = loc.charAt(0);
		
		try
		{
			column = -1;
			for (int i = 0; i < columnLetter.length; i++)
			{
				if (columnLetter[i] == tempColumnLetter)
				{
					column = i;
				}
			}
			if (column == -1)
				throw new InvalidLocationException("Το γράμμα " + tempColumnLetter + " δεν υπάρχει στον πίνακα!");
		}
		catch(InvalidLocationException exception)
		{
			System.out.println(exception.getMessage());
		}
		
		char tempRowLetter = loc.charAt(1);
		row = Character.getNumericValue(tempRowLetter) - 1;//-1 because we start from 0 instead of 1 (visually)
		
		try
		{
			if (row > 7)
			{
				throw new InvalidLocationException("Η σειρά " + (row+1) + " που επέλεξες είναι παραπάνω απο 7, δηλαδή εκτός πίνακα!");	
			}
			else if (row < 0)
			{
				throw new InvalidLocationException("Η σειρά " + (row+1) + " που επέλεξες είναι κάτω απο 1, δηλαδή εκτός πίνακα!");	
			}
		}
		catch(InvalidLocationException exception)
		{
			System.out.println(exception.getMessage());
			row = -1;
		}
	}
	

	
	public int GetRow()
	{
		if (row < 0 || row > 7)
			return -1;//error check ;)
		else
			return row;
	}
	
	public int GetColumn()
	{
		if (column < 0 || column > 7)
			return -1;//error check ;)
		else
			return column;
	}
	
	public int GetAddedRowAndColumn()
	{
		return row + column;
	}
	
	public String toString()
	{
		Integer tempRowInteger = row + 1;//+1 cuz of visual representation is different than internal representation which starts from 0
		String tempStringInteger = tempRowInteger.toString();
		
		Character f;
		char RowLetter = (char)(97 + column);//97 is ASCII 'a'
		
		return RowLetter + tempStringInteger;
	}
	//Επιστρέφει τον συμβολισμό κειμένου της θέσης, π.χ. «e2».
	
	public boolean equals(Location comparedLocation)
	{
		if (GetRow() == comparedLocation.GetRow() && GetColumn() == comparedLocation.GetColumn())
		{
			return true;
		}
		
		return false;
	}
	
	public boolean sameRow(Location comparedLocation)
	{
		if (GetRow() == comparedLocation.GetRow())
			return true;
		else
			return false;
	}
	
	public boolean sameColumn(Location comparedLocation)
	{
		if (GetColumn() == comparedLocation.GetColumn())
			return true;
		else
			return false;
	}
	
	//Quite literally a Location storing the difference of 2 locations, and whichever member is negative is multiplied by -1 (hence absolute ;)
	public static Location GetAbsoluteDistanceFrom(Location originLocation, Location targetLocation)
	{
		int tempRowDistance;
		int tempColumnDistance;
		
		tempRowDistance = targetLocation.GetRow() - originLocation.GetRow();
		tempRowDistance = Math.abs(tempRowDistance);
		
		tempColumnDistance = targetLocation.GetColumn() - originLocation.GetColumn();
		tempColumnDistance = Math.abs(tempColumnDistance);
		
		return new Location(tempRowDistance, tempColumnDistance);
	}
}
