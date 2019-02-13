/*
 * Class: HiringRecord
 * 
 * Description: This class represents a single HireRecord 
 * object for any type of item that can be hired.
 * 
 * Author: Duy Linh Nguyen - s3429599
 * 
 */
public class HiringRecord 
{
	private String Id = null;
	private String memberId =  null;
	private double rentalFee = 0;
	private double lateFee = 0;
	private DateTime borrowDate;
	private DateTime returnDate;
	private boolean isBorrowed;
	private String hireRecordId; 

	
	public HiringRecord(String movieId, String memberId, double rentalFee, double lateFee, DateTime borrowDate, DateTime returnDate, Boolean isBorrowed)
	{	
		this.Id = movieId;
		this.memberId = memberId; //Specific to the hired Item
		this.rentalFee = rentalFee; //determined by the surcharge based on Weekly Release or New Release
		this.lateFee = lateFee; // determined by the method in Movie class
		this.borrowDate = borrowDate; //entered in as formatted date
		this.returnDate = returnDate; //entered in as formatted date
		this.isBorrowed = isBorrowed;
	}
	
	public boolean isBorrowed()
	{
		return true;
	}
	
	public DateTime getBorrowDate()
	{
		return borrowDate;
	}
	
	public DateTime getExpectedReturnDate()
	{
		return returnDate;
	}
	
	
	public double returnItem(DateTime actualReturnDate, double calcLateFee) //set attributes (return items)
	{
		//validation 
		if(calcLateFee < 0)
		{
			calcLateFee = 0;
		}
		//update state attributes
			//update expectedReturnDate to actualReturnDate
			returnDate = actualReturnDate;
			//reset isBorrowed status to false
			isBorrowed = false;
			//Update HireRecordID
			hireRecordId = Id + "_" + memberId + "_" + borrowDate.toString() + "_" + returnDate.toString();
			return lateFee;
	}
	
	

	public String getDetails()
	{
		String details = null;
		if(isBorrowed == true) //it is a hired item...
		{
			String tempBorrowEightDigitDate = borrowDate.getEightDigitDate(); 
			String tempReturnEightDigitDate = returnDate.getEightDigitDate();
			hireRecordId = Id + "_" + memberId +"_"+ tempBorrowEightDigitDate+"_"+tempReturnEightDigitDate;
			details = String.format("%-25s %s\n %-25s %s\n", "Hire ID:", hireRecordId, "Borrow Date:", borrowDate); 


		}



		if(isBorrowed == false) //means returned
		{
			String tempReturnDate;
			String tempBorrowDate;
			tempBorrowDate = borrowDate.getFormattedDate();
			tempReturnDate = returnDate.getFormattedDate();
			String tempBorrowEightDigitDate = borrowDate.getEightDigitDate(); 
			String tempReturnEightDigitDate = returnDate.getEightDigitDate();
			
			hireRecordId = Id + "_" + memberId +"_"+ tempBorrowEightDigitDate+"_"+tempReturnEightDigitDate;
			//check if it is weekly or new release
			details = String.format("%-25s %s\n %-25s %s\n %-25s %s\n %-25s %s\n %-25s %s\n %-25s %s\n", " Hire ID:", hireRecordId, "Borrow Date:", tempBorrowDate, "Return Date: ", tempReturnDate,  "Fee:", rentalFee, "Late Fee:"
					, lateFee, "Total Fees:", rentalFee + lateFee);
		}

		return details;

	}

	public String toString() 
	{
		String stringBorrowDate = borrowDate.getEightDigitDate();
		String stringReturnDate = returnDate.getEightDigitDate();
		hireRecordId = Id + "_" + memberId + "_" + stringBorrowDate + "_" + stringReturnDate;
		return hireRecordId + ":" + stringBorrowDate + ":" + stringReturnDate + ":" + rentalFee + ":" + lateFee;
	}



}

