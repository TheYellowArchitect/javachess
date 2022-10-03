
public abstract class Piece
{
	protected Color currentColor;
	Location currentLocation;
	Board currentBoard;//tfw not singleton or global instance -_-
	
	public Piece(Color newColor, Location newLocation, Board newBoard)
	{
		currentColor = newColor;
		currentLocation = newLocation;
		currentBoard = newBoard;
	}

	public Color GetPieceColor()
	{
		return currentColor;
	}
	
	public abstract String toString();
	public abstract boolean canMoveTo(Location newLocation);//cannot do static abstract reeee
	public void moveTo(Location newLocation)
	{
		//If unoccupied
		if (currentBoard.GetPieceIndexAtLocation(newLocation) == -1)
			currentBoard.MovePiece(currentLocation, newLocation);
		else//enemy pawn there
			currentBoard.MovePieceCapturing(currentLocation, newLocation);
	}
	
}
