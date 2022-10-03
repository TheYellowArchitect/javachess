
public enum Color {
	WHITE, BLACK;
	
	boolean isWhite = true;
	
	public Color nextColor()
	{
		if (isWhite)
		{
			isWhite = false;
			return BLACK;
		}
		else
		{
			isWhite = true;
			return WHITE;
		}
	}
	
	public Color currentColor()
	{
		if (isWhite)
		{
			return WHITE;
		}
		else
		{
			return BLACK;
		}
	}
}
