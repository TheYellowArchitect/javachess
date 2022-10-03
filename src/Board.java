
public class Board {
	Location[][] locationArray;
	Piece[] playerPieces;//Pawns are [0~7] and [15~31]
	//[0]~[15] is Player1/WHITE
	//[16]~[31] is Player2/BLACK
	
	public Board()
	{
		Init();
	}
	
	//This should be the Board's constructor but since exercise says otherwise what can I say
	public void Init()
	{
		locationArray = new Location[8][8];
		playerPieces = new Piece[32];
		
		CreateLocationBoard();
		
		CreateAllPieces();
	}
	
	public void CreateLocationBoard()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				locationArray[i][j] = new Location(i,j);
			}
		}
	}
	
	public void CreateAllPieces()
	{
		//PAWNS
		for (int i = 0; i < 8; i++)
		{
			playerPieces[i] = new Pawn(Color.WHITE, locationArray[1][i], this);
			playerPieces[i+16] = new Pawn(Color.BLACK, locationArray[6][i], this);
		}
		
		//ROOKS
			playerPieces[8] = new Rook(Color.WHITE, locationArray[0][0], this);
			playerPieces[9] = new Rook(Color.WHITE, locationArray[0][7], this);
			
			playerPieces[8+16] = new Rook(Color.BLACK, locationArray[7][0], this);
			playerPieces[9+16] = new Rook(Color.BLACK, locationArray[7][7], this);
			
		//KNIGHTS/HORSES
			playerPieces[10] = new Horse(Color.WHITE, locationArray[0][1], this);
			playerPieces[11] = new Horse(Color.WHITE, locationArray[0][6], this);
			
			playerPieces[10+16] = new Horse(Color.BLACK, locationArray[7][1], this);
			playerPieces[11+16] = new Horse(Color.BLACK, locationArray[7][6], this);
			
		//BISHOPS
			playerPieces[12] = new Bishop(Color.WHITE, locationArray[0][2], this);
			playerPieces[13] = new Bishop(Color.WHITE, locationArray[0][5], this);
			
			playerPieces[12+16] = new Bishop(Color.BLACK, locationArray[7][2], this);
			playerPieces[13+16] = new Bishop(Color.BLACK, locationArray[7][5], this);
			
		//QUEEN
			playerPieces[14] = new Queen(Color.WHITE, locationArray[0][3], this);
			
			playerPieces[14+16] = new Queen(Color.BLACK, locationArray[7][3], this);
			
		//KING
			playerPieces[15] = new King(Color.WHITE, locationArray[0][4], this);
			
			playerPieces[15+16] = new King(Color.BLACK, locationArray[7][4], this);
	}
	
	public int GetPieceIndexAtLocation(Location targetLocation)
	{
		for (int i = 0; i < playerPieces.length; i++)
		{
			if (playerPieces[i] != null && playerPieces[i].currentLocation.equals(targetLocation))
				return i;
		}
		
		return -1;
	}
	
	public Piece GetPieceAtLocation(Location targetLocation)
	{
		int pieceIndex = GetPieceIndexAtLocation(targetLocation);
		//Does not exist
		if (pieceIndex == -1)
		{
			System.out.println("This piece does not exist.");
			return null;
		}
		else
		{
			return playerPieces[pieceIndex];
		}
		
	}
	
	public int GetPieceIndex(Piece piece)
	{
		for (int i = 0; i < playerPieces.length; i++)
		{
			if (playerPieces[i] == piece)
				return i;
		}
		
		return -1;
	}
	
	//Never used for capturing enemy piece, just moving on unoccupied tile
	public void MovePiece(Location originLocation, Location newLocation)
	{
		Piece tempPiece = GetPieceAtLocation(originLocation);
		if (tempPiece != null)
			tempPiece.currentLocation = newLocation;
	}
	
	public void MovePieceCapturing(Location originLocation, Location newLocation)
	{
		Piece tempPiece = GetPieceAtLocation(newLocation);
		
		if (tempPiece == null)
		{
			System.out.println("The piece to be captured is null!");
			return;
		}
		
		int pieceIndex = GetPieceIndex(tempPiece);
		if (pieceIndex != -1)
		{
			playerPieces[pieceIndex] = null;
		}
		
		//Now that we have removed the piece, let's move our originLocation piece there
		MovePiece(originLocation, newLocation);
	}
	
	//Confirmation of originLocation and newLocation being on same row, is confirmed before this point
	public boolean FreeHorizontalPath(Location originLocation, Location newLocation)
	{
		int row = originLocation.GetRow();
		//tileDistance equals how many tiles to check between start location and end location
		int tileDistance = originLocation.GetColumn() - newLocation.GetColumn();
		boolean originLocationGreater = true;
		if (tileDistance < 0)
		{
			originLocationGreater = false;
			tileDistance = tileDistance * -1;
		}
				
		//Iterate as many tiles distance
		for (int i = 1; i < tileDistance; i++)
		{
			if (originLocationGreater)//going to the left
			{
				//Get if there is any piece 
				if (GetPieceIndexAtLocation(new Location(row, originLocation.GetColumn() - i)) != -1)
					return false;
			}
			else//going to the right
			{
				//Get if there is any piece
				if (GetPieceIndexAtLocation(new Location(row, originLocation.GetColumn() + i)) != -1)
					return false;
			}
		}

		return true;
	}
	
	//Confirmation of originLocation and newLocation being on same column, is confirmed before this point
		public boolean FreeVerticalPath(Location originLocation, Location newLocation)
		{
			int column = originLocation.GetColumn();
			//tileDistance equals how many tiles to check between start location and end location
			int tileDistance = originLocation.GetRow() - newLocation.GetRow();
			boolean originLocationGreater = true;
			if (tileDistance < 0)
			{
				originLocationGreater = false;
				tileDistance = tileDistance * -1;
			}
			
			//Iterate as many tiles distance
			for (int i = 1; i < tileDistance; i++)
			{
				if (originLocationGreater)//going downwards
				{
					//Get if there is any piece 
					if (GetPieceIndexAtLocation(new Location(originLocation.GetRow() - i, column)) != -1)
						return false;
				}
				else//going upwards
				{
					//Get if there is any piece
					if (GetPieceIndexAtLocation(new Location(originLocation.GetRow() + i, column)) != -1)
						return false;
				}
			}

			return true;
		}
		
		public boolean FreeDiagonalPath(Location originLocation, Location newLocation)
		{
			Location tempLocation;
			
			//Iterate through all possible diagonal tiles of this location
			//Going from current location to the top right
			//Then from current location to bottom left
			
			//We are "above" the target location, so we must check the pawns towards bottom left
			if (originLocation.GetRow() > newLocation.GetRow())
			{
				//Iterate towards the newLocation
				for (int i = 1;; i++)
				{
					tempLocation = new Location(originLocation.GetRow() - i, originLocation.GetColumn() - i);
					
					//If we reach targetLocation, don't count it and gtfo
					if (tempLocation.equals(newLocation))
						break;
					
					if (GetPieceIndexAtLocation(tempLocation) != -1)
						return false;
				}
			}
			else//Check the pawns towards top right
			{
				//Iterate towards the newLocation
				for (int i = 1;; i++)
				{
					tempLocation = new Location(originLocation.GetRow() + i, originLocation.GetColumn() + i);
					
					//If we reach targetLocation, don't count it and gtfo
					if (tempLocation.equals(newLocation))
						break;
					
					if (GetPieceIndexAtLocation(tempLocation) != -1)
						return false;
				}
			}
			
			return true;
		}
		
		public boolean FreeAntiDiagonalPath(Location originLocation, Location newLocation)
		{
			Location tempLocation;
			
			//Iterate through all possible diagonal tiles of this location
			//Going from current location to the top left
			//Then from current location to bottom right
			
			//We are "above" the target location, so we must check the pawns towards bottom right
			if (originLocation.GetRow() > newLocation.GetRow())
			{
				//Iterate towards the newLocation
				for (int i = 1;; i++)
				{
					tempLocation = new Location(originLocation.GetRow() - i, originLocation.GetColumn() + i);
					
					//If we reach targetLocation, don't count it and gtfo
					if (tempLocation.equals(newLocation))
						break;
					
					if (GetPieceIndexAtLocation(tempLocation) != -1)
						return false;
				}
			}
			else//Check the pawns towards top left
			{
				//Iterate towards the newLocation
				for (int i = 1;; i++)
				{
					tempLocation = new Location(originLocation.GetRow() + i, originLocation.GetColumn() - i);
					
					//If we reach targetLocation, don't count it and gtfo
					if (tempLocation.equals(newLocation))
						break;
					
					if (GetPieceIndexAtLocation(tempLocation) != -1)
						return false;
				}
			}
			
			return true;
		}
	
	//Printed after every user order or move completion.
	public String toString()
	{
		String startEndRowString = " abcdefgh ";
		String finalString = startEndRowString;
		int tempPieceIndex;
		
		//Bruteforcing since I cannot render the board first, then the pawns -_-
		//and ofc the Location class should not store the PieceIndex, as that is bloat and needless dependency.
		
			//Row iteration
			for (int i = 8; i > 0; i--)
			{
				finalString = finalString + "\n" + Integer.toString(i);
				for (int j = 0; j < 8; j++)
				{
					tempPieceIndex = GetPieceIndexAtLocation(new Location(i - 1, j));
					if (tempPieceIndex == -1)
						finalString = finalString + ' ';
					else
					{
						finalString = finalString + playerPieces[tempPieceIndex].toString();
					}
				}
				
				finalString = finalString + Integer.toString(i);
			}
			
			//Add the bottom row
			finalString = finalString + "\n" + startEndRowString + "\n\n";
			
		return finalString;
		
	}
}
