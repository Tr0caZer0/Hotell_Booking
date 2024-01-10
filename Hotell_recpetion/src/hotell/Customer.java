package hotell;

public class Customer{
	protected String firstName;
	protected String surname;
	protected String bookingNumber;
	
	public Customer() {
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setBookingNumber() {
		this.getBookingNumber();
	}
	
	public String getBookingNumber() {
		
		int numberToBN = firstName.length() + surname.length();
		
		String start = firstName.substring(0,1);
		String end = surname.substring(0,1);
		
		return bookingNumber = start.toUpperCase().concat(Integer.toString(numberToBN)).concat(end.toUpperCase());

	}
	
	public String toString() {
		
		return   firstName + ", " + surname+ ", " + bookingNumber;
		
	}

}
