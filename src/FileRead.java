/*
 * Class: FileRead 
 * 
 * Description: This class reads data from a .txt file, and restores back into the item array in the program. 
 * 
 * Author: Duy Linh Nguyen - s3429599
 * 
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
public class FileRead {

	Item[] newItemArray = new Item[100];
	String[] object = new String[100];
	private Scanner inputStream;

	private String fileName;

	public FileRead(String fileName, Item[] newItemArray)
	{
		this.fileName = fileName;
		fileName = fileName + ".txt";
		this.newItemArray = newItemArray;
	}
	
	public void openFile()
	{
		try {
			inputStream = new Scanner(new File("MovieMasterFile.txt"));
		
		}
		catch(FileNotFoundException e) {
			System.out.println("Error opening the file " + "MovieMasterFile.txt");
			System.exit(0);
		}
	}
		


	public Item[] readFile() {
		
	
		while (inputStream.hasNextLine()) {
			String toString = inputStream.nextLine();
			StringTokenizer token = new StringTokenizer(toString, ":", false);
			
			String Id = token.nextToken(":");
			String Title = token.nextToken(":");
			String Genre = token.nextToken(":");
			String Description = token.nextToken(":");
			String Type = token.nextToken(":");
			String isBorrowed = token.nextToken(":");
			
			Boolean borrowStatus = false;
			Boolean isNewRelease = false;
			if(isBorrowed.equalsIgnoreCase("N"))
					{
						borrowStatus = Boolean.valueOf(false);
					}
			else
			{
				borrowStatus = Boolean.valueOf(true);
			}
			if(Type.equalsIgnoreCase("Weekly"))
					{
						isNewRelease = Boolean.valueOf(false);
					}
			else if(Type.equalsIgnoreCase("New Release"))
			{
				isNewRelease = Boolean.valueOf(true);
			}
			
			if(Id.contains("M_"))
			{
				Movie newMovie = null;
				
					newMovie = new Movie(Id, Title, Description, Genre, isNewRelease, null, borrowStatus);
				
					
					
				for(int m = 0; m < newItemArray.length; m++)
				{
					if(newItemArray[m] == null)
					{
						newItemArray[m] = newMovie;
						break;
					}
					else
					{
						newItemArray[m+1] = newMovie;
						break;
					}
				}
			}
			else if(Id.contains("G_"))
			{
				Game newGame = new Game(Id, Title, Genre, Description, Type, null, false);
				for(int g = 0; g < newItemArray.length; g++)
				{
					if(newItemArray[g] == null)
					{
						newItemArray[g] = newGame;
						break;
					}
					else
					{
						newItemArray[g+1] = newGame;
						break;
					}
				}
			}
	
		}//while
		return newItemArray;
	}
	
	
		
		
	
		
	public void closeFile()
	{
		inputStream.close();
	}
}
















