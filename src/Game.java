import java.util.Scanner;
import java.io.*;

public class Game 
{

	Board currentBoard;
	boolean playerWhitePlaying = true;
	boolean repeatInput = false;//If true at end of inputLoop, it retries.
	String userInput;
	boolean inProgress = false;//has a game started?
	
	String validUserInputs = "";
	String loadedInputs;
		
	public Game()
	{
		currentBoard = new Board();
		System.out.println("Καλωσορίσατε σε μια νέα παρτίδα σκακιού!\n");//Context that the game is chess lol
	}
	
	public void Play()
	{
		
		//Infinite loop lol
		while(true)
		{
			System.out.println();//Much needed split for better UX
			if (playerWhitePlaying)
				System.out.println("Παίζει ο ΑΣΠΡΟΣ παίχτης.");
			else
				System.out.println("Παίζει ο ΜΑΥΡΟΣ παίχτης.");
			
			
			do
			{
				System.out.print(currentBoard);
				
				repeatInput = true;
				userInput = GetInput();
				userInput = userInput.toLowerCase().trim();
				userInput = userInput.replaceAll(" ", "");
				
				if (userInput.length() == 0)
				{
					System.out.println("Πατήσατε κενό. Παρακαλώ επιλέξτε μια κατάλληλη εντολή ή κίνηση.");
				}
				//Command/εντολη
				else if (userInput.charAt(0) == ':')
				{
					if (userInput.length() == 1)
					{
						PrintPossibleCommandMessages(false);
					}
					else if (userInput.length() == 2)
					{
						DetermineCommand(userInput.charAt(1));
					}
					else
					{
						PrintPossibleCommandMessages(true);
					}
				}
				else
					HandleMove(userInput);
			}
			while (repeatInput == true);
		}
	}
	
	private String GetInput()
	{
		Scanner myScanner = new Scanner(System.in);
	    System.out.println("Εισάγετε κίνηση (πχ \"a2a4\") ή εντολη (πχ \":x\" για έξοδο)");

	    return myScanner.nextLine();
	}
	
	private void DetermineCommand(char inputCommand)
	{
		if (inputCommand == 'x')
		{
			if (ExitGame())
				System.exit(0);
			else
				return;
		}
		else if (inputCommand == 's')
			SaveGame();
		else if (inputCommand == 'l')
			LoadGame();
		else if (inputCommand == 'h')
			PrintHelp();
		else
			PrintPossibleCommandMessages(true);
	}
	
	//Empty is for default ":", for example like typing "ffmpeg" which shows man page pretty much lol
	private void PrintPossibleCommandMessages(boolean wrongInput)
	{
		if (wrongInput)
		{
			System.out.println("Λάθος Εντολή. Η εντολή \"" + userInput + "\" δεν είναι εφικτή.");
		}
		
		System.out.println("Οι μόνες πιθανές εντολές είναι:\n  :x -> ExitGame  (Έξοδος Παιχνιδιού)\n  :s -> SaveGame  (Αποθήκευση Παιχνιδιού)\n  :l -> LoadGame  (Φόρτωση Παιχνιδιού)\n  :h -> PrintHelp (Εμφάνιση Κειμένου Βοήθειας)");
	}
	
	private void HandleMove(String moveString)
	{
		boolean errorIgnoreInput = false;
		
		try
		{
			if (moveString.length() < 4)
				throw new InvalidLocationException("Κάτι ξέχασες. Πρέπει συνολικά να είναι 4 οι χαρακτήρες, αλλά εσύ έβαλες " + moveString.length() + ".");
			else if (moveString.length() > 4)
				throw new InvalidLocationException("Έβαλες παραπάνω χαρακτήρες. Πρέπει συνολικά να είναι 4 οι χαρακτήρες, αλλά εσύ έβαλες " + moveString.length() + ".");
			
			if (Character.isLetter(moveString.charAt(0)) == false)
				throw new InvalidLocationException("Ο αρχικός χαρακτήρας που έβαλες (" + moveString.charAt(0) + ") είναι ψηφίο ή ειδικός χαρακτήρας, ενώ απαιτείται γράμμα, πχ 'a'");
			else if (Character.isLetter(moveString.charAt(2)) == false)
				throw new InvalidLocationException("Ο τρίτος χαρακτήρας που έβαλες (" + moveString.charAt(2) + ") είναι ψηφίο ή ειδικός χαρακτήρας, ενώ απαιτείται γράμμα, πχ 'a'");
			
			if (Character.isDigit(moveString.charAt(1)) == false)
				throw new InvalidLocationException("Ο δέυτερος χαρακτήρας που έβαλες (" + moveString.charAt(1) + ") είναι γράμμα ή ειδικός χαρακτήρας, ενώ απαιτείται ψηφίο, πχ '2'");
			else if (Character.isDigit(moveString.charAt(3)) == false)
				throw new InvalidLocationException("Ο τέταρτος χαρακτήρας που έβαλες (" + moveString.charAt(3) + ") είναι γράμμα ή ειδικός χαρακτήρας, ενώ απαιτείται ψηφίο, πχ '2'");

			//Error handling is inside Location's constructor.
			Location errorCheckLocation = new Location(moveString);
		}
		catch(InvalidLocationException exception)//1+2
		{
			System.out.println(exception.getMessage());
			errorIgnoreInput = true;
			return;
		}
		
		
		//Check if first location has a VALID piece (exists + is of your color)
		//also check if targetLocation has a piece you own
		//also check if piece can pull that movement
		try
		{
			Location tempLocation;
			tempLocation = new Location(moveString.substring(0, 2));
			
			if (currentBoard.GetPieceIndexAtLocation(tempLocation) == -1)
				throw new InvalidMoveException("Δεν υπάρχει πιόνι στην θέση " + moveString.substring(0,2) + "!\nΗ Θέση " + moveString.substring(0,2) + " είναι άδεια!");
			
			Piece tempPiece = currentBoard.GetPieceAtLocation(tempLocation);
			
			Location targetLocation = new Location(moveString.substring(2,4));
			Piece targetPiece;
			if (currentBoard.GetPieceIndexAtLocation(targetLocation) != -1)
			{
				//If there is a pawn where you want to move, check if that pawn is yours, so as to not kill it.
				targetPiece = currentBoard.GetPieceAtLocation(targetLocation);
				if ((targetPiece.GetPieceColor() == Color.WHITE && playerWhitePlaying) || (targetPiece.GetPieceColor() == Color.BLACK && playerWhitePlaying == false))
					throw new InvalidMoveException("Το πιόνι που πάς να φάς στην θέση " + moveString.substring(2,4) + " είναι δικό σου!");
			}
			
			if (Character.isLowerCase(tempPiece.toString().charAt(0)) && playerWhitePlaying)
				throw new InvalidMoveException("Μπορείς να κινήσεις μόνο άσπρα πιόνια. Το πιόνι στην θέση " + moveString.substring(0,2) + " είναι μαύρο.");
			
			if (Character.isLowerCase(tempPiece.toString().charAt(0)) == false && playerWhitePlaying == false)
				throw new InvalidMoveException("Μπορείς να κινήσεις μόνο μάυρα πιόνια. Το πιόνι στην θέση " + moveString.substring(0,2) + " είναι άσπρο.");
		}
		catch(InvalidMoveException exception)//3+4+5
		{
			System.out.println(exception.getMessage());
			errorIgnoreInput = true;
			return;
		}
		
		Piece movingPiece;
		Location movingPieceLocation = new Location(moveString.substring(0, 2));
		Location targetLocation = new Location(moveString.substring(2, 4));
		
		//All is good, no errors, the move is valid.
		if (errorIgnoreInput == false)
		{
			movingPiece = currentBoard.GetPieceAtLocation(movingPieceLocation);
			if (movingPiece.canMoveTo(targetLocation))
			{
				movingPiece.moveTo(targetLocation);
				
				repeatInput = false;//So next player plays.
				validUserInputs = validUserInputs + moveString;//Save total inputs, so as to save/load
				
				//Win Condition check (king is dead)
				if (currentBoard.playerPieces[15] == null)
				{
					System.out.println("Ο Άσπρος βασιλιάς ηττήθηκε! Το παιχνίδι τελείωσε.\nΟ νικητής είναι ο ΜΑΥΡΟΣ παίχτης!");
					Game newGame = new Game();
					newGame.Play();//Recursive lol -> That's what happens when handleMove is locked to void instead of returning a flag!
				}
				else if (currentBoard.playerPieces[15+16] == null)
				{
					System.out.println("Ο Μαύρος βασιλιάς ηττήθηκε! Το παιχνίδι τελείωσε.\nΟ νικητής είναι ο ΑΣΠΡΟΣ παίχτης!");
					Game newGame = new Game();
					newGame.Play();//Recursive lol -> That's what happens when handleMove is locked to void instead of returning a flag!
				}
				
				//Changing player's turn
				playerWhitePlaying = !playerWhitePlaying;
			}
			
		}
		

		
	}
	
	//:s
	private void SaveGame()
	{
		if (validUserInputs.length() == 0)
		{
			System.out.println("Δέν έχετε εισάγει καμία εντολή, αρα δεν υπάρχει τίποτα για να αποθηκεύσετε!");
			return;
		}
		
		Scanner saveScanner = new Scanner(System.in);
		System.out.println("Επιλέξτε το όνομα του αρχείου που θέλετε να αποθηκεύσετε.\nπχ \"Παρτίδα2\"");
		
		String saveInput = saveScanner.nextLine();
		saveInput = saveInput.trim();
		
		try 
		{
			//Filename
			File savedFile = new File(saveInput + ".txt");
			if (savedFile.createNewFile())
				System.out.println("Το αρχείο " + savedFile.getName() + " αποθηκεύτηκε στην τοποθεσία:\n" + savedFile.getAbsolutePath());
			else
				System.out.println("Το αρχείο " + savedFile.getName() + " υπάρχει ήδη, και αντικαταστήθηκε!");
			
			FileWriter fileWriter = new FileWriter(saveInput + ".txt");
			fileWriter.write(validUserInputs);
			fileWriter.close();
		    System.out.println("Οι εντολές τυπώθηκαν στο αρχείο επιτυχώς!\n");
		}
		catch (IOException exception) 
		{
	      System.out.println("Αποτυχία αποθήκευσης αρχείου!");	
		}

	}
	
	private void GetLoadedInputCommands(String loadInput)
	{
		try 
		{
			File loadedFile = new File(loadInput + ".txt");			
			Scanner fileReader = new Scanner(loadedFile);
			while (fileReader.hasNextLine())
			{
				loadedInputs = fileReader.nextLine();
				//System.out.println(loadedInputs);
			}
			fileReader.close();
			System.out.println("Επιτυχία φόρτωσης του αρχείου " + loadedFile.getName() + "!\n");
			return;
		}
		catch (IOException exception) 
		{
			System.out.println("Αποτυχία φόρτωσης του αρχείου: " + loadInput + ".txt\nΔοκιμή ανοίγματος αρχείου " + loadInput);
		}
		
		try 
		{
			File loadedFile = new File(loadInput);
			Scanner fileReader = new Scanner(loadedFile);
			while (fileReader.hasNextLine())
			{
				loadedInputs = fileReader.nextLine();
				//System.out.println(loadedInputs);
			}
			fileReader.close();
			System.out.println("Επιτυχία φόρτωσης του αρχείου " + loadedFile.getName() + "!\n");
		}
		catch (IOException exception) 
		{
			System.out.println("Αποτυχία φόρτωσης του αρχείου " + loadInput);
			System.out.println("Μήπως το ονομάσατε διαφορετικά? Όσο σκέφτεστε, υπάρχει μια ατελείωτη παρτίδα...\n");
		}
	}
	
	//:l
	private void LoadGame()
	{
		Scanner loadScanner = new Scanner(System.in);
		
		if (validUserInputs.length() > 0)
		{
			System.out.println("Η παρτίδα σας δεν έχει τελειώσει. Είστε σίγουρος οτι θέλετε να την παρατήσετε?\ny\n");
			
			String loadInput = loadScanner.nextLine();
			loadInput = loadInput.toLowerCase().trim();
			
			//If no, dont continue loading.
			if (loadInput.contains("y") == false && loadInput.contains("yes") == false)
				return;
		}
		
		System.out.println("Επιλέξτε το όνομα του αρχείου που θέλετε να φορτώσετε.\nπχ \"Παρτίδα2\"");
		
		String loadInput = loadScanner.nextLine();
		loadInput = loadInput.trim();
		
		GetLoadedInputCommands(loadInput);
		if (loadedInputs.length() == 0)
			return;
		
		Game newGame = new Game();
		
		//System.out.println("Φόρτωση των εξής κινήσεων");
		//System.out.println("=========================");
		
		int amountOfCommands = loadedInputs.length() / 4;
		for (int i = 0; i < amountOfCommands; i++)
		{
			int inputStartIndex = i * 4;
			System.out.println(loadedInputs.substring(inputStartIndex, inputStartIndex + 4));
			newGame.HandleMove(loadedInputs.substring(inputStartIndex, inputStartIndex + 4));
		}
		
		newGame.Play();//Recursive lol -> That's what happens when handleMove is locked to void instead of returning a flag!
	
	}
	
	//:x
	private boolean ExitGame()
	{
		if (inProgress == false)
			return true;
		else
		{
			Scanner exitScanner = new Scanner(System.in);
		    System.out.println("Είστε σίγουρος οτι θέλετε να αποχωρίσετε?   y\n");
		    
		    String exitInput = exitScanner.nextLine();
		    exitInput = exitInput.toLowerCase().trim();
		    
		    if (exitInput.contains("y") || exitInput.contains("yes"))
		    	return true;
		    else
		    	return false;
		}
	}
	
	//:h
	private void PrintHelp()
	{
		System.out.println("Δείτε τον παραπάνω πίνακα.\nΕπιλέξτε ενα πιόνι που ελέγχετε γράφοντας την τοποθεσία του (πχ \"h2\") και την κίνηση στην τοποθεσία που επιθυμείτε (πχ \"h4\")");
		System.out.println("Άρα η τελική εντολή του παραπάνω παραδείγματος είναι: \"h2h4\"");
		System.out.println("Όσον αφορά τις εντολές...");
		PrintPossibleCommandMessages(false);
	}

}
