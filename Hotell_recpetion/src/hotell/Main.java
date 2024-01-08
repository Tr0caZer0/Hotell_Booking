package hotell;

import java.util.Scanner;

public class Main {
	
	public static Scanner userInput = new Scanner(System.in);
	public static HotellLogic logic = new HotellLogic();
	
	public static void main(String[] args) {
		
		while(true) {
			switch(userMenu()) {
			
			case -1:
				
				System.out.println("Program ended.");
				System.exit(0);
				
				break;
			
			case 1:
				
				roomBooking();
				break;
			case 2:
				
				getBookingDetails();
				break;
			case 3:
				searchBooking();
				break;
			case 4:
				cancleBooking();
				break;
			case 5:
				logic.checkAvailableDate("Single-Bedroom", "Double-Bedroom", "Suite-Bedroom");
				break;
			
			}
		}
	}// End main method 


	private static void roomBooking() {
		
		logic.addDate();
		
		logic.addCustomer();
		
		if(logic.selectRoomType("Single-Bedroom", "Double-Bedroom", "Suite-Bedroom") != null) {
			logic.addBookingToDoc("Hotell_BookingDetails.txt");
			logic.addBookingToDoc("Hotell_Booked_Rooms.txt");
		}
	}// End roomBooking method.
	
	private static void getBookingDetails() {
		logic.readBookingDetails("Hotell_BookingDetails.txt");
	}// End getBookingDetails method. 

	private static void searchBooking() {
		System.out.println("Which booking are you looking for?");
		System.out.print("Enter booking number: ");
		String bookingNumber = logic.userStringInput();
		System.out.println("");
		String userBookingDetails = logic.checkBookingNumber(bookingNumber, 1, null);
		if(userBookingDetails != null) {
			System.out.println("The follwoing booking could be found ");
			System.out.println(userBookingDetails + "\n");
		}else {
			System.out.println("No boking with booking number " + bookingNumber + " was found.");
		}
			
	}//End searchBooking method
	
	private static void cancleBooking() {
		System.out.println("Which booking would you like to cancel?");
		System.out.print("Enter booking number: ");
		String bookingNumber = logic.userStringInput();
		System.out.println("");
		String userBookingDetails = logic.checkBookingNumber(bookingNumber, 2, "Hotell_BookingDetails.txt");
		System.out.println(userBookingDetails + "\n");
		
		if(userBookingDetails != null) {
			System.out.println("The follwoing booking has been canceld.");
			System.out.println(userBookingDetails);
		}else {
			System.out.println("No boking with booking number " + bookingNumber + " was found.");
		}
		
		String bookedRooms = logic.checkBookingNumber(bookingNumber, 2, "Hotell_Booked_Rooms.txt");
		System.out.println(bookedRooms + "\n");
		
	}//End cancleBooking method. 

	public static int userMenu() {
		int menuSelection = 0;
		
		while(true) {
			System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n", "1. Boka rum", "2. Hämta bokningslista", "3. Sök bokning", 
			           "4. Avboka bokning", "5. Sök tillgängliga rum", "0. Avsluta");
				
				String command = logic.userStringInput();
			try {
					menuSelection = Integer.parseInt(command);
					if(menuSelection == 0) {
						return -1;
					}
					break;
			}catch(NumberFormatException e) {
				System.out.println("Ogiltig inmatning");
			}//End Try-catch
			
		}//End while-loop
		return menuSelection;
	}// End userMenu Method
	
}//End Main class