/*
 * Class: MovieMasterSystem 
 * 
 * Description: The user interface to access and utilise all the functions of the program.
 * Adding, Borrowing and Returning.
 * 
 * Author: Duy Linh Nguyen - s3429599
 * 
 * 
 */
import java.util.*;

public class MovieMasterSystem {

	Item[] items = new Item[100];
	String[] Id = new String[100];
	String inputAns = null;
	boolean aLoop = true;
	boolean mainLoop = true;
	boolean bLoop = true;
	String inputIDTemp = null;
	
	int numAddItem = 0;

	public void Menu() {
		while (mainLoop == true) {

			String details = String.format(
					"%s\n %-25s " + "%s\n %-25s %s\n %-25s" + " %s\n %-25s %s\n" + " %-25s %s\n " + "%-25s %s\n",
					"*** Movie Master System ***", "Add Item", "A", "Borrow Item", "B", "Return Item", "C",
					"Display Details", "D", "Seed Data", "E", "Exit Program", "X");
			
			System.out.println(details);
			System.out.print("Enter selection: ");
			
			Scanner keyboard = new Scanner(System.in);
			String selection = keyboard.nextLine();

			if (selection.equalsIgnoreCase("A")) // ADD ERROR HANDLING for DOUBLE ID
			{
				addItem();
			}

			if (selection.equalsIgnoreCase("B")) // Borrow function
			{
				borrowItem();
			}

			if (selection.equalsIgnoreCase("C")) {
				returnItem();
			}

			if (selection.equalsIgnoreCase("D")) {
				displayItem();
			}

			if (selection.equalsIgnoreCase("E")) {
				seedData();
			}

			if (selection.equalsIgnoreCase("X")) {
				
				System.out.println("Just about close and save everything, can you please enter a Filename to save to?" );
				String fileName = keyboard.nextLine();
				
				FileSave fileSave = new FileSave(getItemsArray(), fileName);
				fileSave.writeToFile();
				fileName = fileSave.getFileName();

				FileRead fileRead = new FileRead(fileName, items);
				fileRead.openFile();
				Item[] temp = fileRead.readFile();

				for (int i = 0; i < items.length; i++) {
					if (items[i] != null) {
						System.out.print(temp[i].getDetails());
					}
				}
				fileRead.closeFile();
				mainLoop = false;
				System.out.print("Closing down...");
			}
			
		
		}
		
	}

	public void addItem() {
		aLoop = true;
		while (aLoop == true) {
			Scanner keyboard = new Scanner(System.in);
			System.out.print("Is it a Game or Movie? (G or M): ");
			inputAns = keyboard.nextLine();

			if (inputAns.equalsIgnoreCase("M")) {
				addMovie();
				aLoop = false;
			} else if (inputAns.equalsIgnoreCase("G")) // Enters Game
			{
				addGame();
				aLoop = false;
			}
		}

	}

	public void borrowItem()  {
		Scanner keyboard = new Scanner(System.in);
		String inputID = null;
		bLoop = true;
		while (bLoop == true) {
			System.out.print("Enter Id: ");
			inputID = keyboard.nextLine();
			inputID = inputID.toUpperCase();
			try {
				if (inputID.length() == 3) {
					bLoop = false;
				} else if (inputID.contains("[0-9]+")) {
					throw new IdException("\n" + "Invalid entry, cannot contain numerals and special characters!");
				} else if (inputID.length() != 3) {
					throw new IdException("\n" + "ID needs to be 3 digits long!");

				}
			} catch (IdException e) {
				System.out.println(e.getMessage());
				System.out.println(e.getErrorMessage());
			}

		}

		for (int i = 0; i < items.length; i++) {
			String itemId = items[i].getId();
			itemId = itemId.substring(2, 5);
			if (itemId.equalsIgnoreCase(inputID)) {
				if (items[i].isBorrowed() == true) {
					System.out.print(inputID + " Is currently on loan. Cannot borrow.");
					break;
				} else {
					String inputMeId = null;

					while (bLoop = true) {
						System.out.println("Enter memberID: ");
						inputMeId = keyboard.nextLine();
						inputMeId = inputMeId.toUpperCase();

						if (inputMeId.length() == 3) {
							int inputAdvDays = 0;

							try {
								System.out.println("Days in advance to borrow: ");
								inputAdvDays = keyboard.nextInt();

							} catch (Exception e) {
								System.out
										.println("Invalid entry. You entered a character instead of integer..." + "\n");
								break;
							}

							if (inputAdvDays < 0) {
								break;
							}
							double fee = items[i].borrowItem(inputMeId, inputAdvDays);
							// Fee display
							HiringRecord currentBorrowRecord = items[i].getCurrentlyBorrowedRecord();
							DateTime returnDateCBR = currentBorrowRecord.getExpectedReturnDate();

							System.out.println("This item costs " + "$" + fee + " and is due: "
									+ returnDateCBR.getFormattedDate());
							bLoop = false;
							break;

						}
					}

					break;
				} 
			} 

		}
		
	}

	public void returnItem() {
		boolean cLoop = true;
		String inputID = null;
		Scanner keyboard = new Scanner(System.in);

		while (cLoop == true) {

			System.out.print("Enter Id: ");
			inputID = keyboard.nextLine();
			inputID = inputID.toUpperCase();
			if (inputID.length() == 3) {
				cLoop = false;
			}
		}

		for (int i = 0; i < items.length; i++) {
			String itemInArray = items[i].getId();
			itemInArray = itemInArray.substring(2, 5);
			if (inputID.equalsIgnoreCase(itemInArray)) {
				// Check if it is borrowed
				if (items[i].isBorrowed() == true) {
					int inputDay = 0;
					while (cLoop = true) {
						System.out.print("Enter number of days on loan: ");

						inputDay = keyboard.nextInt();
						if (inputDay > 0) {
							cLoop = false;
							break;
						}
					}
					HiringRecord returnBorrowRecord = items[i].getCurrentlyBorrowedRecord();
					DateTime borrowDate = returnBorrowRecord.getBorrowDate();
					DateTime actualReturnDate = new DateTime(borrowDate, inputDay);

					double test = items[i].returnItem(actualReturnDate);
					returnBorrowRecord.returnItem(actualReturnDate, test);

					System.out.println("Total payable fee: " + test);
					cLoop = false;
					break;

				}
			}
		}
		
	}

	public void displayItem() {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				break;
			} else {
				String details = items[i].getDetails();
				System.out.print(details);
			}
		}
	}

	public void seedData() {

		DateTime borrowDate = new DateTime();
		DateTime returnDate5Day = new DateTime(borrowDate, 5);
		DateTime returnDate1Day = new DateTime(borrowDate, 1);
		DateTime returnDate3Day = new DateTime(borrowDate, 3);
		DateTime returnDate10Day = new DateTime(borrowDate, 10);
		DateTime returnDate2Day = new DateTime(borrowDate, 2);
		DateTime returnDate20Day = new DateTime(borrowDate, 20);
		DateTime returnDate19Day = new DateTime(borrowDate, 19);
		DateTime returnDate32Day = new DateTime(borrowDate, 32);

		HiringRecord movieBorrow2 = new HiringRecord("M_KIK", "AAA", 3.00, 0, borrowDate, returnDate5Day, true);
		HiringRecord movieBorrow3 = new HiringRecord("M_FAN", "AAB", 3.00, 0, borrowDate, returnDate5Day, false);
		HiringRecord movieBorrow4 = new HiringRecord("M_BOB", "ABB", 3.00, 4.5, borrowDate, returnDate10Day, false);
		HiringRecord movieBorrow5 = new HiringRecord("M_JIN", "ABC", 3.00, 4.5, borrowDate, returnDate10Day, false);

		HiringRecord movieBorrow7 = new HiringRecord("M_AVN", "LIN", 5.00, 0, borrowDate, returnDate2Day, true);
		HiringRecord movieBorrow8 = new HiringRecord("M_FAN", "AAB", 3.00, 0, borrowDate, returnDate1Day, false);
		HiringRecord movieBorrow9 = new HiringRecord("M_ABB", "JIN", 3.00, 4.5, borrowDate, returnDate3Day, false);
		HiringRecord movieBorrow10 = new HiringRecord("M_JAW", "YUU", 3.00, 4.5, borrowDate, returnDate3Day, false);
		HiringRecord movieBorrow11 = new HiringRecord("M_JAW", "JUN", 3.00, 0, borrowDate, returnDate2Day, true);

		HiringRecord gameBorrow1 = new HiringRecord("G_SCT", "TER", 5.00, 0, borrowDate, returnDate20Day, true);
		HiringRecord gameBorrow2 = new HiringRecord("G_WAR", "TER", 5.00, 0, borrowDate, returnDate19Day, false);
		HiringRecord gameBorrow3 = new HiringRecord("G_HST", "TER", 5.00, 0, borrowDate, returnDate32Day, false);

		Movie weeklyMovie1 = new Movie("SUP", "The Superman Movie",
				"Clark Kent realises his glasses doesn't actually hide his identity!", "Action", false, null, false);
		items[0] = weeklyMovie1;

		Movie weeklyMovie2 = new Movie("KIK", "Kick Ass", "A kid decides to become a vigilante", "Action", false,
				movieBorrow2, false);
		items[1] = weeklyMovie2;

		Movie weeklyMovie3 = new Movie("FAN", "Full Metal Alchemist",
				"The Elric Brothers search for the Philosophers Stone", "Animation", false, movieBorrow3, false);
		items[2] = weeklyMovie3;

		Movie weeklyMovie4 = new Movie("BOB", "Bob the Builder",
				"Bob the Builder has been laid off, and is looking for retribution...", "Horror", false, movieBorrow4,
				false);
		items[3] = weeklyMovie4;

		Movie weeklyMovie5 = new Movie("JIN", "Jack in Black", "His back, and his wearing Black!", "Action", false,
				movieBorrow5, false);

		items[4] = weeklyMovie5;
		items[4].returnItem(returnDate10Day);
		weeklyMovie5.borrowItem("ACC", 10);

		Movie newReleaseMovie1 = new Movie("FRI", "Friday the 13th", "When summer camp goes wrong...", "Horror", true,
				null, false);
		items[5] = newReleaseMovie1;
		Movie newReleaseMovie2 = new Movie("AVN", "The Avengers", "A ragtag group of super people, save the world",
				"Action", true, movieBorrow7, false);
		items[6] = newReleaseMovie2;
		Movie newReleaseMovie3 = new Movie("ABB", "Abba the Movie",
				"A music group that is now irrelevant, trying to find relevance in the 21st Century.", "Musical", true,
				movieBorrow8, false);
		items[7] = newReleaseMovie3;
		Movie newReleaseMovie4 = new Movie("NIN", "Ninja Assassin", "NINJAAAAAAAAAGOOO", "Action", true, movieBorrow9,
				false);
		items[8] = newReleaseMovie4;
		Movie newReleaseMovie5 = new Movie("JAW", "Jaws", "No one can hear you scream, in the deep blue...", "Horror",
				true, movieBorrow10, false);
		items[9] = newReleaseMovie5;
		Movie newReleaseMovie6 = new Movie("JAW", "Jaws", "No one can hear you scream, in the deep blue...", "Horror",
				true, movieBorrow11, false);
		items[10] = newReleaseMovie6;

		Game game1 = new Game("WOW", "World of Warcraft", "Make love not warcraft!", "MMORPG", "PC", null, null);
		Game game2 = new Game("SCT", "Starcraft II", "1000APM", "RTS", "PC", gameBorrow1, null);
		Game game3 = new Game("WAR", "Warcraft III", "ORCSSSSSSSS", "RTS", "PC", gameBorrow2, null);
		Game game4 = new Game("HST", "Hearthstone", "Tavern gaaames", "Card-Game", "PC", gameBorrow3, null);

		items[11] = game1;
		items[12] = game2;
		items[13] = game3;
		items[14] = game4;
	}

	public void addMovie() {
		Scanner keyboard = new Scanner(System.in);
		String inputId = null;
		aLoop = true;
		while (aLoop == true) {
			System.out.print("Enter ID: ");
			inputId = keyboard.nextLine();
			inputId = inputId.toUpperCase();
			try {
				if (inputId.length() != 3) {
					throw new IdException(
							"\n" + "Error, must be 3 characters, must not contain numerals or special characters!");
				} else if (inputId.contains("[0-9]+")) {
					throw new IdException(
							"\n" + "Error, must be 3 characters, must not contain numerals or special characters!\"");
				}
			} catch (IdException e) {
				System.out.println(e.getMessage());
				System.out.println(e.getErrorMessage() + "\n");

			}
			if (inputId.length() == 3) {
				aLoop = false;
				try {
					boolean allLetters = inputId.chars().allMatch(Character::isLetter);
					if (allLetters == false) {
						throw new IdException("\n" + "Cannot contain special characters, or numerals!");
					}
				} catch (IdException e) {
					System.out.println(e.getMessage());
					System.out.println(e.getErrorMessage());
					aLoop = true;
				}

				if (numAddItem > 0) {

					for (int k = 0; k < items.length; k++) {
						String stringItem;
						if (items[k] != null) {
							stringItem = items[k].getId();
							stringItem = stringItem.substring(2, 5);
						} else {
							break;
						}
						try {

							if (stringItem.equalsIgnoreCase(inputId)) {
								throw new IdException("This game already exists");
							}
						} catch (IdException e) {
							System.out.println(e.getMessage());
							System.out.println("Try again.");
							aLoop = true;

						}
					}
				}
			}
		}

		System.out.print("Enter Title: ");
		String inputTitle = keyboard.nextLine();

		System.out.print("Enter Genre: ");
		String inputGenre = keyboard.nextLine();

		System.out.print("Enter Description: ");
		String inputDescription = keyboard.nextLine();
		aLoop = true;
		boolean newRelease = false;
		while (aLoop == true) {
			System.out.print("Enter new Release (Y/N) : ");
			String inputNewRelease = keyboard.nextLine();

			if (inputNewRelease.equalsIgnoreCase("Y")) {
				newRelease = true;
				break;
			} else if (inputNewRelease.equalsIgnoreCase("N")) {
				numAddItem++;
				newRelease = false;
				break;
			} else {
				System.out.print("Error");
			}

		}

		HiringRecord currentlyBorrowed = null;

		Movie newMovie = new Movie(inputId, inputTitle, inputGenre, inputDescription, newRelease, currentlyBorrowed, false);

		for (int i = 0; i < items.length; i++) {

			if (items[i] == null) {
				items[i] = newMovie;
				System.out.println(newMovie.getDetails());
				aLoop = false;
				break;
			}

		}
	}

	public void addGame() {

		String inputId = null;
		aLoop = true;
		Scanner keyboard = new Scanner(System.in);
		while (aLoop == true) {
			
			System.out.print("Enter ID: ");
			inputId = keyboard.nextLine();
			inputId = inputId.toUpperCase();
			try {
				if (inputId.length() != 3) {
					throw new IdException(
							"\n" + "Error, must be 3 characters, must not contain numerals or special characters!");
				} else if (inputId.contains("[0-9]+")) {
					throw new IdException(
							"\n" + "Error, must be 3 characters, must not contain numerals or special characters!\"");
				}
			} catch (IdException e) {
				System.out.println(e.getMessage());
				System.out.println(e.getErrorMessage() + "\n");

			}
			if (inputId.length() == 3) {
				aLoop = false;
				try {
					boolean allLetters = inputId.chars().allMatch(Character::isLetter);
					if (allLetters == false) {
						throw new IdException("\n" + "Cannot contain special characters, or numerals!");
					}
				} catch (IdException e) {
					System.out.println(e.getMessage());
					System.out.println(e.getErrorMessage());
					aLoop = true;
				}

				if (numAddItem > 0) {

					for (int k = 0; k < items.length; k++) {
						String stringItem;
						if (items[k] != null) {
							stringItem = items[k].getId();
							stringItem = stringItem.substring(2, 5);
						} else {
							break;
						}
						try {

							if (stringItem.equalsIgnoreCase(inputId)) {
								throw new IdException("This game already exists");
							}
						} catch (IdException e) {
							System.out.println(e.getMessage());
							System.out.println("Try again.");
							aLoop = true;

						}
					}
				}
			}
		}

		System.out.print("Enter Title: ");
		String inputTitle = keyboard.nextLine();

		System.out.print("Enter Genre: ");
		String inputGenre = keyboard.nextLine();

		System.out.print("Enter Description: ");
		String inputDescription = keyboard.nextLine();

		HiringRecord currentlyBorrowed = null;

		String inputPlatforms;
		aLoop = true;
		while (aLoop == true) {
			System.out.print("What Platforms: XBOX, PS, PC: (Enter in, seperated by commas)");
			inputPlatforms = keyboard.nextLine();
			inputPlatforms = inputPlatforms.toUpperCase();
			String platforms = null;
			if (inputPlatforms.contains("XBOX") || inputPlatforms.contains("PS") || inputPlatforms.contains("PC")) {

				platforms = inputPlatforms;
			} else {
				break;
			}
			Game newGame = new Game(inputId, inputTitle, inputGenre, inputDescription, platforms, currentlyBorrowed, false);
			numAddItem++;
			aLoop = false;

			for (int i = 0; i < items.length; i++) {

				if (items[i] == null) {
					items[i] = newGame;

					System.out.println(items[i].getDetails());

					break;
				}

			}
			break;
		}
	}

	public String getItemsArray() {
		String stringItems = "";
		String stringFileItem = "";
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				stringItems = items[i].toString();

			} else if (items[i + 1] != null) {
				stringItems = items[i].toString();

			} else {
				break;
			}
			stringFileItem += stringItems + "\n";
		}
		return stringFileItem;
	}

}
