
public class Pawn extends Piece 
{
	boolean hasMovedBefore = false;

	public Pawn(Color newColor, Location newLocation, Board newBoard)
	{
		super(newColor, newLocation, newBoard);
	}

	//@Override
	public boolean canMoveTo(Location newLocation)
	{
		int tempIndex;
		Location diagonalLocation;
		
		try
		{
			//If white, can only move upwards
			if (currentColor == Color.WHITE)
			{
				//Diagonal check first of all.
					//Diagonally right
					if (currentLocation.GetRow() + 1 < 8 && currentLocation.GetColumn() + 1 < 8)
					{
						diagonalLocation = new Location(currentLocation.GetRow() + 1, currentLocation.GetColumn() + 1);
						tempIndex = currentBoard.GetPieceIndexAtLocation(diagonalLocation);
						if (tempIndex != -1)//If there is a piece diagonally
						{
							if (diagonalLocation.equals(newLocation))
								return true;
						}
						else
						{
							if (diagonalLocation.equals(newLocation))
								throw new InvalidMoveException("Η κίνηση διαγώνια είναι εφικτή μόνο όταν υπάρχει αντίπαλο πιόνι διαγώνια του πιονιού " + currentLocation + "\nΔεν υπάρχει αντίπαλο πιόνι διαγώνια.");
						}
					}
					
					
					//Diagonally left
					if (currentLocation.GetRow() + 1 < 8 && currentLocation.GetColumn() - 1 > -1)
					{
						diagonalLocation = new Location(currentLocation.GetRow() + 1, currentLocation.GetColumn() - 1);
						tempIndex = currentBoard.GetPieceIndexAtLocation(diagonalLocation);
						if (tempIndex != -1)//If there is a piece diagonally
						{
							if (diagonalLocation.equals(newLocation))
								return true;
						}
						else
						{
							if (diagonalLocation.equals(newLocation))
								throw new InvalidMoveException("Η κίνηση διαγώνια είναι εφικτή μόνο όταν υπάρχει αντίπαλο πιόνι διαγώνια του πιονιού " + currentLocation + "\nΔεν υπάρχει αντίπαλο πιόνι διαγώνια.");
						}
					}
					
					
				//Simple straight movement.
					if (currentLocation.GetRow() + 1 < 8)
					{
						Location tempLocation = new Location(currentLocation.GetRow() + 1, currentLocation.GetColumn());
						tempIndex = currentBoard.GetPieceIndexAtLocation(tempLocation);
						if (tempIndex == -1)//If empty/unoccupied
						{
							if (tempLocation.equals(newLocation))
								return true;
						}
						else
						{
							if (tempLocation.equals(newLocation))
								throw new InvalidMoveException("Υπάρχει αντίπαλο πιόνι στην θέση " + tempLocation + "\nΤο πιόνι αυτό δεν μπορεί να κινηθεί κάθετα σε θέση όπου υπάρχει πιόνι.");
						}
					}
					
					
				//Double movement (start)
					if (currentLocation.GetRow() + 2 < 8)
					{
						Location doubletempLocation = new Location(currentLocation.GetRow() + 2, currentLocation.GetColumn());
						Location tempLocation = new Location(currentLocation.GetRow() + 1, currentLocation.GetColumn());
						if (hasMovedBefore == false)
						{
							int doubletempIndex = currentBoard.GetPieceIndexAtLocation(doubletempLocation);
							tempIndex = currentBoard.GetPieceIndexAtLocation(tempLocation);
							if (tempIndex == -1 && doubletempIndex == -1)//If BOTH tiles are unoccupied
							{
								if (doubletempLocation.equals(newLocation))
									return true;
							}
							else
							{
								if (doubletempLocation.equals(newLocation))
								{
									if (tempIndex != -1 && doubletempIndex != -1)
										throw new InvalidMoveException("Υπάρχουν αντίπαλα πιόνια στις θέσεις " + tempLocation + " και " + doubletempLocation + ". \nΗ διπλή κίνηση απαιτεί να μην υπάρχει πιόνι στην διαδρομή προς την θέση" + newLocation + ".");
									else if (tempIndex != -1)
										throw new InvalidMoveException("Υπάρχει αντίπαλο πιόνι ακριβώς μπροστά, στην θέση " + tempLocation + ", οπου σταματά την διπλή κίνηση.");
									else if (doubletempIndex != -1)
										throw new InvalidMoveException("Υπάρχει αντίπαλο πιόνι στην θέση " + doubletempLocation + ". Πρέπει να είναι άδεια η θέση.");
								}
							}
						}
						
						//Check if tried to move 2 tiles while its not possible
						if (hasMovedBefore)
						{
							if (doubletempLocation.equals(newLocation))
								throw new InvalidMoveException("Η κίνηση 2 τετραγώνων είναι εφικτή μόνο αν δεν έχει ξανακινηθεί το πιόνι της θέσης " + currentLocation);
						}
					}
					
					
				//Check if tried to move backwards
				if (currentLocation.GetColumn() == newLocation.GetColumn() && currentLocation.GetRow() > newLocation.GetRow())
				{
					throw new InvalidMoveException("Δεν μπορείς να πάς προς τα κάτω. Μόνο πάνω.");
				}
			}
			else//if black, can only move downwards
			{
				//Diagonal check first of all.
					//Diagonally right
					if (currentLocation.GetRow() - 1 > -1 && currentLocation.GetColumn() + 1 < 8)
					{
						diagonalLocation = new Location(currentLocation.GetRow() - 1, currentLocation.GetColumn() + 1);
						tempIndex = currentBoard.GetPieceIndexAtLocation(diagonalLocation);
						if (tempIndex != -1)//If there is a piece diagonally
						{
							if (diagonalLocation.equals(newLocation))
								return true;
						}
						else
						{
							if (diagonalLocation.equals(newLocation))
								throw new InvalidMoveException("Η κίνηση διαγώνια είναι εφικτή μόνο όταν υπάρχει αντίπαλο πιόνι διαγώνια του πιονιού " + currentLocation + "\nΔεν υπάρχει αντίπαλο πιόνι διαγώνια.");
						}
					}
					
					
					//Diagonally left
					if (currentLocation.GetRow() - 1 > -1 && currentLocation.GetColumn() - 1 > -1)
					{
						diagonalLocation = new Location(currentLocation.GetRow() - 1, currentLocation.GetColumn() - 1);
						tempIndex = currentBoard.GetPieceIndexAtLocation(diagonalLocation);
						if (tempIndex != -1)//If there is a piece diagonally
						{
							if (diagonalLocation.equals(newLocation))
								return true;
						}
						else
						{
							if (diagonalLocation.equals(newLocation))
								throw new InvalidMoveException("Η κίνηση διαγώνια είναι εφικτή μόνο όταν υπάρχει αντίπαλο πιόνι διαγώνια του πιονιού " + currentLocation + "\nΔεν υπάρχει αντίπαλο πιόνι διαγώνια.");
						}
					}
					
					
				//Simple straight movement.
					if (currentLocation.GetRow() - 1 > -1)
					{
						Location tempLocation = new Location(currentLocation.GetRow() - 1, currentLocation.GetColumn());
						tempIndex = currentBoard.GetPieceIndexAtLocation(tempLocation);
						if (tempIndex == -1)//If empty/unoccupied
						{
							if (tempLocation.equals(newLocation))
								return true;
						}
						else
						{
							if (tempLocation.equals(newLocation))
								throw new InvalidMoveException("Υπάρχει αντίπαλο πιόνι στην θέση " + tempLocation + "\nΤο πιόνι αυτό δεν μπορεί να κινηθεί κάθετα σε θέση όπου υπάρχει πιόνι.");
						}
					}
					
					
				//Double movement (start)
					if (currentLocation.GetRow() - 2 > -1)
					{
						Location doubletempLocation = new Location(currentLocation.GetRow() - 2, currentLocation.GetColumn());
						Location tempLocation = new Location(currentLocation.GetRow() - 1, currentLocation.GetColumn());
						if (hasMovedBefore == false)
						{
							int doubletempIndex = currentBoard.GetPieceIndexAtLocation(doubletempLocation);
							tempIndex = currentBoard.GetPieceIndexAtLocation(tempLocation);
							if (tempIndex == -1 && doubletempIndex == -1)//If BOTH tiles are unoccupied
							{
								if (doubletempLocation.equals(newLocation))
									return true;
							}
							else
							{
								if (doubletempLocation.equals(newLocation))
								{
									if (tempIndex != -1 && doubletempIndex != -1)
										throw new InvalidMoveException("Υπάρχουν αντίπαλα πιόνια στις θέσεις " + tempLocation + " και " + doubletempLocation + ". \nΗ διπλή κίνηση απαιτεί να μην υπάρχει πιόνι στην διαδρομή προς την θέση" + newLocation + ".");
									else if (tempIndex != -1)
										throw new InvalidMoveException("Υπάρχει αντίπαλο πιόνι ακριβώς μπροστά, στην θέση " + tempLocation + ", οπου σταματά την διπλή κίνηση.");
									else if (doubletempIndex != -1)
										throw new InvalidMoveException("Υπάρχει αντίπαλο πιόνι στην θέση " + doubletempLocation + ". Πρέπει να είναι άδεια η θέση.");
								}
									
							}
						}
						
						//Check if tried to move 2 tiles while its not possible
						if (hasMovedBefore)
						{
							if (doubletempLocation.equals(newLocation))
								throw new InvalidMoveException("Η κίνηση 2 τετραγώνων είναι εφικτή μόνο αν δεν έχει ξανακινηθεί το πιόνι της θέσης " + currentLocation);
						}
					}
					
					
				//Check if tried to move backwards
				if (currentLocation.GetColumn() == newLocation.GetColumn() && currentLocation.GetRow() < newLocation.GetRow())
				{
					throw new InvalidMoveException("Δεν μπορείς να πάς προς τα πάνω. Μόνο κάτω.");
				}
					
			}
			
			if (Location.GetAbsoluteDistanceFrom(currentLocation, newLocation).GetAddedRowAndColumn() > 2)
				throw new InvalidMoveException("Το πιόνι μπορεί να κουνηθεί μόνο 1 θέση μακριά (εκτός απ'την πρώτη κίνηση όπου επιτρέπεται 2 τετράγωνα)\nΠροσπάθησες να κινήσεις το πιόνι " + Location.GetAbsoluteDistanceFrom(currentLocation, newLocation).GetAddedRowAndColumn() + " τετράγωνα μακριά!");
		}
		catch(InvalidMoveException exception)
		{
			System.out.println("Η κίνηση αυτή του πιονιού δεν είναι εφικτή.");
			System.out.println(exception.getMessage());
		}
		
		
		return false;

	}
	
	@Override
	public void moveTo(Location newLocation)
	{
		hasMovedBefore = true;
		super.moveTo(newLocation);
	}
	
	//@Override
	public String toString()
	{
		if (currentColor == Color.WHITE)
			return "P";
		else
			return "p";
	}

}
