
public class Item {
	private String Id;
	private String title;
	private String description;
	private String genre;
	private Boolean isNewRelease;
	HiringRecord currentlyBorrowed;
	HiringRecord [] hireHistory = new HiringRecord[10];
	private double fee;
	private int rentalPeriod;
	private String type;
	
		public Item(String Id, String title, String description, String genre,HiringRecord currentlyBorrowed)
			{
				this.Id = Id;
				this.title = title;
				this.description = description;
				this.genre = genre;
				this.currentlyBorrowed = currentlyBorrowed;
			}

		public double getFee()
		{
			return fee;
		}
		
		
		public String getBorrowStatus()
		{
			if(currentlyBorrowed == null)
			{
				return "NO";
			}
			else
			{
				return "YES";
			}
		}
		
		public int getRentalPeriod()
		{
			if(isNewRelease == true)
			{
				rentalPeriod = 2;
			}
			else if(isNewRelease == false)
			{
				rentalPeriod = 7;
			}
			else 
			{
				rentalPeriod = 10;
			}
			return rentalPeriod;
		}
		
		public double borrowItem(String memberId, int advancedDays)
			{
				int amtArray = 0;
				double currentRentFee = 0;
				double currentLateFee;
				DateTime borrowDate = new DateTime();
				DateTime returnDate = new DateTime(borrowDate, 5);
				
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
				
					if(currentlyBorrowed == null) //not borrowed, not on hire, new item.
					{
						
						currentRentFee = getFee();
						currentLateFee = 0;
						HiringRecord newRecord = new HiringRecord(Id, memberId, currentRentFee, currentLateFee, borrowDate, returnDate, true); 
						currentlyBorrowed = newRecord;
						
						for(int i = 0; i < hireHistory.length; i++)
						{
							if(hireHistory[i] == null)
							{
								hireHistory[i] = currentlyBorrowed;
								System.out.println(amtArray++);
								break;
							}
							if(hireHistory[i] != null)
							{
								if(hireHistory[i+1] == null)
								{
									hireHistory[i+1] = currentlyBorrowed;
									System.out.println(amtArray++);
									amtArray++;
									break;
								}
							}
						}//IF curre
					} //end of currentBorrow
					
				if(currentlyBorrowed != null)
					{
						currentRentFee = Double.NaN;	
					}
				
					System.out.println(amtArray);
					return currentRentFee;
			}
		
		public double returnItem(DateTime actualReturnDate)	
		{
			//double currentBorrowFee = 0;
			
		//	DateTime currentBorrowDate = currentlyBorrowed.getBorrowDate();
			DateTime expectedReturnDate = currentlyBorrowed.getExpectedReturnDate();
			double lateFee = 0;
		    int difference = 0;
			int daysOverDue = 0;
			
			//check IF currentlyBorrowed
			//check IF date is prior to borrow date
			
			if(currentlyBorrowed == null)
			{
				System.out.print("Cannot return, not a borrowed item.");
				
			}
				
			if(actualReturnDate == null)
					{
						lateFee = Double.NaN; // 
					}
			
				else if(actualReturnDate != null)
				{
					
					difference = DateTime.diffDays(actualReturnDate, expectedReturnDate);
					
					if(difference < 0) //returnDate returned prior to borrowDate
					{
						lateFee = 0;
					}
					else if(difference > 0 ) // 1 day over 
							{
								daysOverDue = difference;
								double surcharge = getFee();
								lateFee = 0.5 * (daysOverDue * surcharge);
							}
						else if (difference == 0)
							{
								lateFee = 0;
							}
					}
				
				currentlyBorrowed = null;
				return lateFee;
				
		}//end method
		public getDetailType()
		{
			return "Type";
		}
		public String getType()
		{
			return type;
		}
		
		public String getId()
		{
			return Id;
		}
		public String getDetails()
		{
			fee = getFee();
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
					String a = String.format( "%s\n %-25s %s\n %-25s %s\n", "  ***Summary***  ", "ID:", getId(),"Title:", title) + String.format(" %-25s %s\n %-25s %s\n", "Genre:", genre, "Description:", description);
					String b = String.format(" %-25s %s\n %-25s %s\n","Standard Fee:", fee, "On Loan:", getBorrowStatus()) + String.format(" %-25s %s\n %s\n", "Rental Period:", getRentalPeriod() + " days", "_________");
					String c = String.format(" %-25s %s\n", getDetailType(), getType());
					String d = String.format( "%s\n %s\n %s\n %s\n", "Borrow Record", "_________", borrowRecord, "_________");
					
				details = a + b + c + d;
			
			return details;
		}
	
	public String toString()
	{
		
		String tempType = null;
		String tempBorrow = null;
		if(isNewRelease == true)
		{
			tempType = "NR";
		}
		else if (isNewRelease == false)
		{
			tempType = "WK";
		}
		
		if(currentlyBorrowed != null)
		{
			tempBorrow = "Y";
		}
		
		if(currentlyBorrowed == null)
		{
			tempBorrow = "N";
		}
		fee = getFee();
		  return Id + ":" + title + ":" + description + ":" + genre + ":" + fee + ":" + tempType + ":" + tempBorrow;
	}
	
		
}
