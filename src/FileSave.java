/*
 * Class: FileSave
 * 
 * Description: This is the class that saves all data in the items array, and saves onto a text file for later restoration.
 * 
 * Author: Duy Linh Nguyen - s3429599
 * 
 * 
 */





import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
public class FileSave {

	String itemToWrite;
	String fileName = null;
	
	
	public FileSave(String itemToWrite, String fileName)
	{
		this.itemToWrite = itemToWrite;
		this.fileName = fileName;
	
	}
	public void writeToFile()
	{
		fileName += ".txt";
		PrintWriter outputStream = null;
			try {
				outputStream = new PrintWriter(new FileOutputStream(fileName, true));
				outputStream.print(itemToWrite);
				System.out.print("File has been saved!");
				}
			catch(FileNotFoundException e)
			{
				e.getStackTrace();
			}
			
			outputStream.close();
		
	}
	public String getFileName()
	{
		return fileName;
	}
}
