/*
 * Class: Movie 
 * 
 * Description: This represents a single item of the type Movie
 * that can be hired. This is one of the child classes of the parent class "Item" (See Item.class).
 * 
 * Author: Duy Linh Nguyen - s3429599
 * 
 * 
 */

public class Movie extends Item {
	private Boolean isNewRelease;
	HiringRecord[] hireHistory = new HiringRecord[10];
	private static final double WEEKLY_FEE = 3.00;
	private static final double NEW_RELEASE_FEE = 5.00;
	private final double MOVIE_LATE_FEE = 0.5;
	private int rentalPeriod;

	public Movie(String Id, String title, String description, String genre, Boolean isNewRelease,
			HiringRecord currentlyBorrowed, Boolean extended)  {
		super(Id, title, description, genre, currentlyBorrowed, extended);
		this.isNewRelease = isNewRelease;
	}

	public String getCurrentlyBorrowedDetails() {
		return currentlyBorrowed.getDetails();
	}

	public HiringRecord getCurrentlyBorrowedRecord() {
		return currentlyBorrowed;
	}

	public String setDetailType() {
		return "Rental Type: ";
	}

	public String setType() {

		if (isNewRelease == true) {
			return "New Release";
		} else {
			return "Weekly";
		}
	}

	public String getBorrowStatus() {
		if (currentlyBorrowed == null) {
			return "NO";
		} else {
			return "YES";
		}
	}

	public boolean isBorrowed() {
		if (currentlyBorrowed != null) {
			return true;
		} else {
			return false;
		}
	}

	public double getFee() {
		double FEE = 0;
		if (isNewRelease == true) {
			FEE = NEW_RELEASE_FEE;
		} else if (isNewRelease == false) {
			FEE = WEEKLY_FEE;
		}
		return FEE;
	}

	public double getLateFee() {
		return MOVIE_LATE_FEE;
	}

	public int getRentalPeriod() {
		if (isNewRelease == true) {
			rentalPeriod = 2;
		} else if (isNewRelease == false) {
			rentalPeriod = 7;
		} else {
			rentalPeriod = 10;
		}
		return rentalPeriod;
	}

	public String getId() {
		String Id = super.getId();
		if (Id.contains("M_")) {
			Id = Id;
		} else {
			Id = "M_" + Id;
		}
		return Id;
	}

	public boolean isNewRelease() {
		return isNewRelease;
	}

	public DateTime setReturnDate(int advancedDays) {
		DateTime todayDate = new DateTime();
		DateTime borrowDate = new DateTime(todayDate, advancedDays);
		DateTime returnDate;
		if (isNewRelease == true) {
			returnDate = new DateTime(borrowDate, 2);
		} else {
			returnDate = new DateTime(borrowDate, 5);
		}
		return returnDate;
	}

	public double borrowItem(String memberId, int advancedDays) {

		double borrowFee = super.borrowItem(memberId, advancedDays);

		return borrowFee;
	}

	public double returnItem(DateTime actualReturnDate) {
		double lateFee = super.returnItem(actualReturnDate);
		return lateFee;
	}

	public String getDetails() {

		String details = super.getDetails();

		return details;
	}

	public String toString() {
		super.toString();
		return super.toString();
	}

}
