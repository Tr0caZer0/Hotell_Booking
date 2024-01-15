package hotell;

public class Main {
	
	public static void main(String[] args) {
		Main mainMenu = new Main();
		mainMenu.selectOption();
		
	}// End main method 
	
	public void selectOption() {
		HotellLogic logic = new HotellLogic();
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
	}


	private static void roomBooking() {
		HotellLogic logic = new HotellLogic();
		
		logic.addDate();
		
		logic.addCustomer();
		
		if(logic.selectRoomType("Single-Bedroom", "Double-Bedroom", "Suite-Bedroom") != null) {
			logic.addBookingToDoc("Hotell_BookingDetails.txt");
			logic.addBookingToDoc("Hotell_Booked_Rooms.txt");
		}
	}// End roomBooking method.
	
	private void getBookingDetails() {
		HotellLogic logic = new HotellLogic();
		logic.readBookingDetails("Hotell_BookingDetails.txt");
	}// End getBookingDetails method. 

	private void searchBooking() {
		HotellLogic logic = new HotellLogic();
		System.out.println("Which booking are you looking for?");
		System.out.print("Enter booking number: ");
		String bookingNumber = logic.userStringInput();
		System.out.println("");
		String userBookingDetails = logic.checkBookingNumber(bookingNumber, 1, "Hotell_BookingDetails.txt");
		if(userBookingDetails != null) {
			System.out.println("The follwoing booking could be found ");
			System.out.println(userBookingDetails + "\n");
		}else {
			System.out.println("No boking with booking number " + bookingNumber + " was found.");
		}
			
	}//End searchBooking method
	
	private void cancleBooking() {
		HotellLogic logic = new HotellLogic();
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

	public int userMenu() {
		int menuSelection = 0;
		HotellLogic logic = new HotellLogic();
		while(true) {
			System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n", "1. Book room", "2. Get booking list", "3. Search booking", 
			           "4. Cancel booking", "5. Search available room", "0. Quit");
				
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