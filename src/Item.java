/*
 * Class: Item 
 * 
 * Description: This represents a single item,
 * that can be hired. This is the parent class of items that can be Hired. (Movie & Game)
 * 
 * Author: Duy Linh Nguyen - s3429599
 * 
 * 
 */
public class Item 
{
	private String Id;
	private String title;
	private String description;
	private String genre;
	HiringRecord currentlyBorrowed;
	private Boolean extended;
	HiringRecord [] hireHistory = new HiringRecord[10];
	private final double LATE_FEE = 1.0;
	private final double SURCHARGE = 5.00;
	private double fee = 0;
	private int rentalPeriod;
	private String type;
	private boolean isBorrowed;
	
	
	protected Item(String Id, String title, String description, String genre, HiringRecord currentlyBorrowed, Boolean extended) 
			{
				this.Id = Id;
				this.title = title;
				this.description = description;
				this.genre = genre;
				this.currentlyBorrowed = currentlyBorrowed;
				this.extended = extended;
			}
	
	protected String getCurrentlyBorrowedDetails()
	{
		return currentlyBorrowed.getDetails();
	}
	
	protected HiringRecord getCurrentlyBorrowedRecord()
	{
		return currentlyBorrowed;
	}
	
	protected boolean isBorrowed()
	{
		return isBorrowed;
	}
	
	protected double getSurcharge()
		{
			return SURCHARGE;
		}
	
	protected double getFee()
	{
		return fee;
	}
		
	public double getLateFee()
		{
			return LATE_FEE;
		}
		
	protected String getBorrowStatus(){
		String temp = null;
			if(currentlyBorrowed == null)
			{
				temp = "NO";
			}
			else if(currentlyBorrowed != null)
			{
				temp = "YES";
			}
			else if(currentlyBorrowed != null && extended == true)
			{
				temp = "EXTENDED";
			}
			return temp;
		}
		
	protected int getRentalPeriod()
		{
			rentalPeriod = 5;
			return rentalPeriod;
		}
		
	protected DateTime setReturnDate(int advancedDays)

		{
			DateTime todayDate = new DateTime();
			DateTime borrowDate = new DateTime(todayDate, advancedDays);
			DateTime returnDate = new DateTime(borrowDate, 5);
			
			return returnDate;
			
		}
	
	/* ALGORITHM
	 * 		RECEIVE memberId and days borrowed in advanced
	 * 			IF the days borrows, is equal to 0
	 * 				THEN calculate Borrow Date to be the date upon execution 
	 * 			ELSE IF days borrows, is greater than 0.
	 * 			IF currentlyBorrowed is empty, it means not Borrowed
	 * 				THEN calculate standard fee for item, create item and add to item array.
	 * 			
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *
	 */
	

	protected double borrowItem(String memberId, int advancedDays) 
	{
		
				int amtArray = 0;
				double currentRentFee = 0;
				double currentLateFee;
				DateTime borrowDate = new DateTime();
				DateTime returnDate = setReturnDate(advancedDays);
			if(advancedDays == 0 )
			{
				borrowDate = new DateTime();
			}
			else if(advancedDays > 0)
			{
				DateTime currentBorrowDate = new DateTime();
						DateTime borrDate = new DateTime(currentBorrowDate, advancedDays);
						borrowDate = borrDate;
			}
			
			if(currentlyBorrowed == null) 
			{		
					currentRentFee = getFee();
					currentLateFee = 0;
					HiringRecord newRecord = new HiringRecord(getId(), memberId, currentRentFee, currentLateFee, borrowDate, returnDate, true); 
					currentlyBorrowed = newRecord;
				}
			
				System.out.println(amtArray);
				return currentRentFee;
	}

/*
 * ALGORITHM (returnItem)
 * START
 * 			IF currentlyBorrowed is empty, 
 * 				THEN print, cannot return, and it is not on loan
 * 			ELSE (it is full)
 * 				THEN CHECK IF the returnDate doesn't exists
 * 						THEN return Double.Nan
 * 						ELSE IF there is a returnDate entered
 * 							THEN Calculate the difference between the returnDate entered, and the expected ReturnDate
 * 							IF the difference is 0 or less than 0 (it means was returned on time, or before the expected returndate
 * 								THEN Late Fee is 0
 * 							ELSE IF Difference is greater than 0
 * 								THEN calculate LateFee
 * 							IF the Difference is greater then 0, and 7 days have past overDue
 * 								THEN calculate Late, with surcharge in addition
 * 			ITERATE through HireHistory array
 * 				IF the current position in the Array is empty
 * 					THEN store currentlyBorrowed HireRecord into that array position
 * 				ELSE IF it is full
 * 					THEN go to next one 	
 * 						IF empty
 * 							THEN store
 * 				IF Array is full
 * 					THEN delete the oldest record, copy the remainder, and push it back one position
 * 
 * 			Delete CurrentlyBorrowed
 * END
 */
	protected double returnItem(DateTime actualReturnDate)	
		{
			DateTime expectedReturnDate = currentlyBorrowed.getExpectedReturnDate();
			double lateFee = 0;
		    int difference = 0;
			int daysOverDue = 0;
			
			if(currentlyBorrowed == null)
			{
				System.out.print("Cannot return, not a borrowed item.");
				
			}
			else
			{
				
			if(actualReturnDate == null)
					{
						lateFee = Double.NaN; 
					}
			
				else if(actualReturnDate != null)
				{
					
					difference = DateTime.diffDays(actualReturnDate, expectedReturnDate);
					
					int weeks = difference / 7;
					
					if(difference < 0 || difference == 0) 
					{
						lateFee = 0;
					}
					else if(difference > 0 ) 
							{
								daysOverDue = difference;
								lateFee = getLateFee() * (daysOverDue);
							}
					if(difference > 0 && weeks > 0)
					{
							
								double addLateFee = weeks * getSurcharge();
								lateFee += addLateFee; 
					}		
				}
					
			
				for(int i = 0; i < hireHistory.length; i++)
				{
					if(hireHistory[i] == null)
					{
						hireHistory[i] = currentlyBorrowed;
						currentlyBorrowed = null;
						break;
					}
					if(hireHistory[i] != null && i == hireHistory.length)
					{
						hireHistory[0] = null;
						hireHistory[i-1] = hireHistory[i];
					}	
				}	
			}
				
				currentlyBorrowed = null;
				return lateFee;		
		}
		
	protected String setDetailType()
		{
			return "Type";
		}
		
	protected String setType()
		{
			return type;
		}
		
	protected String getId()
		{
			return Id;
		}
		
	protected String getDetails()
		{
			String details = null;
		String borrowRecord = null;
		if(currentlyBorrowed != null)
			{
			borrowRecord = currentlyBorrowed.getDetails();
			}
		else 
		{
			borrowRecord = "NONE";
		}
		String mostRecentHireHistory;
		if(hireHistory[0] != null)
		{
			mostRecentHireHistory = hireHistory[0].getDetails();
		}
		else
		{
			mostRecentHireHistory = "NONE";
		}
	
		
		
					String a = String.format( "%s\n %s\n %-25s %s\n %-25s %s\n", "           ", "  ***Summary***  ", "ID:", getId(),"Title:", title) + String.format(" %-25s %s\n %-25s %s\n", "Genre:", genre, "Description:", description);
					String b = String.format(" %-25s %s\n %-25s %s\n","Standard Fee:", getFee(), "On Loan:", getBorrowStatus());
					String c = String.format(" %-25s %s\n", setDetailType(), setType());
					String d = String.format(" %-25s %s\n %s\n", "Rental Period:", getRentalPeriod() + " days", "___________");
					String e = String.format( "%s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n", "**Current Hire Record**", "___________", borrowRecord, "___________","**Most recent Hire Record**", "___________", mostRecentHireHistory, "___________" );
				
		
					
				details = a + b + c + d + e;
			
			return details;
		}
	
	public String toString()
	{
			String borrowStatus = "";
			if(getBorrowStatus().equalsIgnoreCase("Yes")){
				borrowStatus = "Y";
			}
			else if(getBorrowStatus().equalsIgnoreCase("No")) {
				borrowStatus = "N";
			}
			else if(getBorrowStatus().equalsIgnoreCase("Extended")){
				borrowStatus = "E";
			}
		  return getId() + ":" + title + ":" + description + ":" + genre + ":" + getFee() + ":" + setType() + ":" + borrowStatus;
	}
	
		
}
