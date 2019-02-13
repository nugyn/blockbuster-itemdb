/*
 * Class: Game 
 * 
 * Description: This represents a single item of the type Game
 * that can be hired. This is one of the Child classes of the Parent "Item" (See Item.class).
 * 
 * Author: Duy Linh Nguyen - s3429599
 * 
 * 
 */
public class Game extends Item  {

	private boolean extended;
	private HiringRecord[] hireHistory = new HiringRecord[10];
	private final double GAME_LATE_FEE = 1;
	private final double SURCHARGE = 5.00;
	private double fee = 5.00;
	private String platforms;

	public Game(String Id, String title, String description, String genre, String platforms, HiringRecord currentlyBorrowed, Boolean extended) {
		super(Id, title, description, genre, currentlyBorrowed, extended);
		this.platforms = platforms;

	}

	public String getCurrentlyBorrowedDetails() {
		return currentlyBorrowed.getDetails();
	}

	public HiringRecord getCurrentlyBorrowedRecord() {
		return currentlyBorrowed;
	}

	public boolean isBorrowed() {
		if (currentlyBorrowed != null) {
			return true;
		} else {
			return false;
		}
	}

	public String setDetailType() {
		return "PLATFORM";
	}


	public String setType() {
		return platforms;
	}

/*
 * START
 * 		Receives 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @see Item#setReturnDate(int)
 */
	public DateTime setReturnDate(int advancedDays) {
		DateTime todayDate = new DateTime();
		DateTime borrowDate = new DateTime(todayDate, advancedDays);
		DateTime returnDate;

		returnDate = new DateTime(borrowDate, 20);

		return returnDate;
	}

	public String getBorrowStatus() {
		String response = null;
		if (currentlyBorrowed == null) {
			response = "NO";
		} else if (currentlyBorrowed != null) {
			response = "YES";
			if (extended == true) {
				response = "EXTENDED";
			}
		}
		return response;
	}

	public int getRentalPeriod() {
		int rentalPeriod = 20;
		return rentalPeriod;
	}

	public String getId() {
		String Id = super.getId();
		if (Id.contains("G_")) {
			Id = Id;
		} else {
			Id = "G_" + Id;
		}
		return Id;
	}

	public double getFee() {
		return fee;
	}

	public double getSurcharge() {
		return SURCHARGE;
	}

	public double getLateFee() {
		return GAME_LATE_FEE;
	}

	public double borrowItem(String memberId, int advancedDays) {
		double borrowFee = super.borrowItem(memberId, advancedDays);
		return borrowFee;
	}
	
	/*ALGORITHM
	 * 			
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public double returnItem(DateTime actualReturnDate) {
		double lateFee = super.returnItem(actualReturnDate);

		return lateFee;
	}

	public String getDetails() {
		return super.getDetails();
	}

	public String toString() {
		super.toString();
		return super.toString();
	}

}
