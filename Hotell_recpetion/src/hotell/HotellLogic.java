package hotell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotellLogic{
	public Scanner userInput = new Scanner(System.in);
	Room room = new Room();
	Customer customer = new Customer();

//	Handeling dates___________________________________________________________________________
	public void addDate() {
		
		LocalDate visitDates;
		LocalDate leaveDates;
		do {
			
			System.out.print("Enter date of visit e.g "+ LocalDate.now() +": ");
			visitDates = (formatDate(userStringInput()));
			room.setVisitDates(visitDates);
			System.out.print("Enter date of leave e.g " + LocalDate.now().plusDays(1) + ": ");
			leaveDates = (formatDate(userStringInput()));
			room.setLeaveDates(leaveDates);
			
			if(controllDateSyntax()) {
				break;
			}
		}
		
		while(true);
	}
	
	public LocalDate formatDate(String dateString) {
		LocalDate parsedDate = null;
		boolean validFormat = false;
		while(!validFormat) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				parsedDate = LocalDate.parse(dateString, formatter);
				validFormat = true;
			}catch(Exception e) {
				System.out.println("Invalid format." + e);
				validFormat = true;
			} 
			 
		}
		return parsedDate;
	}
	
	public boolean controllDateSyntax() {
		
		if(room.getLeaveDate() == null || room.getVisitDate() == null) {
		        System.out.println("Invalid date input. Please try again.");
		        return false;
		 }else if(room.getLeaveDate().isBefore(room.getVisitDate())) {
			System.out.println("You selected a leaving date before the visit date");
			return false;
		}else if(room.getLeaveDate().isBefore(LocalDate.now()) || room.getVisitDate().isBefore(LocalDate.now())) {
			System.out.println("You selected a date which is in the past");
			return false;
		}
		
		return true;
	}
	
//	Handling customer name and booking number. ________________________________________________
	public void addCustomer() {

		String firstName = "";
		String surname = "";
		while(firstName.equals(""))  {

			System.out.print("Add first name: ");
			firstName = userStringInput();
			firstName = (asciiControll(firstName));
		}
		
		while(surname.equals("")) {
			System.out.print("Add surename: ");
			surname = userStringInput();
			surname = (asciiControll(surname));
		}
		customer.setFirstName(firstName);
		customer.setSurname(surname);
		customer.setBookingNumber();
	}
	
	public String asciiControll(String asciiCheck) {
		try {

			if(asciiCheck.matches("^[a-zA-ZåäöÅÄÖ\\\\s]+$")) {
				return asciiCheck.trim();
			}
			
		}catch(Exception e) {
			System.out.println("Namn kan bara innehålla bokstäver");
		}
		return "";
	}// End asciiControll method
	
//	Handling room type________________________________________________________
	// Room selection method. 
	public int roomSelectionMenu() {
		int menuSelection = 0;
		
		while(true) {
			System.out.printf("%s%n%s%n%s%n%s%n", "1. Select Single-Bedroom", "2. Select Double-Bedroom", "3. Select Suite-Bedroom", "0. quit program");
			
				String command = userStringInput();
			try {
					menuSelection = Integer.parseInt(command);
					if(menuSelection == 0) {
						return -1;
					}
					break;
			}catch(NumberFormatException e) {
				System.out.println("Ogiltig inmatning");
			}
			
		}
		return menuSelection;
	}
	//Method selects which type of room should be used. 
	public String selectRoomType(String SingleBedroom, String DoubleBedroom, String SuiteBedroom) {
		boolean selectRoom = false;
		String checks = null;
		System.out.println("Select room for stay. ");
		while(!selectRoom) {
			switch(roomSelectionMenu()) {
			case -1:
				//Change to return
				System.out.println("No room choice made.");
				selectRoom = true;
				checks = null;
//				System.exit(0);
				
				break;
			case 1:
				System.out.println(SingleBedroom + " selected");
				if(checkAvailableRooms(SingleBedroom, 3)) {// If the room is not booked
					room.setRoomType("Single-Bedroom");
					selectRoom = true;
					checks = "";
					
				}else {
					System.out.println("Room is fully booked for the requested date.");
					selectRoom = true;
					checks = null;
				}
				break;
			case 2:
				System.out.println(DoubleBedroom + " selected");
				if(checkAvailableRooms(DoubleBedroom, 3)) {// If the room is not booked
					room.setRoomType(DoubleBedroom);
					selectRoom = true;
					checks = "";
				}else {
					System.out.println("Room is fully booked for the requested date.");
					selectRoom = true;
					checks = null;
				}
				break;
			case 3: 
				System.out.println(SuiteBedroom + " selected");
				if(checkAvailableRooms(SuiteBedroom, 3)) {// If the room is not booked
					room.setRoomType(SuiteBedroom);
					selectRoom = true;
					checks = "";
				}else {
					System.out.println("Room is fully booked for the requested date.");
					selectRoom = true;
					checks = null;
				}
				break;
			}
		}
		return checks;
	}

//	Method sets the rooms which will be checked for availability.
//	Will handle with for-loop if time remains. 
	public void checkAvailableDate(String SingleBedroom, String DoubleBedroom, String SuiteBedroom){
		addDate();
		String date = room.getVisitDate() +"/" + room.getLeaveDate();
		if(checkAvailableRooms(SingleBedroom, 3)) {
			
			System.out.println(SingleBedroom + " is avaliable for the date " + date);
		}else {
			System.out.println("All "+ SingleBedroom +" is booked for the date " + date);
		}
		
		if(checkAvailableRooms(DoubleBedroom, 3)) {
			
			System.out.println(DoubleBedroom + " is avaliable for the date " + date);
		}else {
			System.out.println("All " + DoubleBedroom + " is booked for the date " + date);
		}
		
		if(checkAvailableRooms(SuiteBedroom,2)) {
			
			System.out.println(SuiteBedroom + " is avaliable for the date " + date);
		}else {
			System.out.println("All " + SuiteBedroom + " is booked for the date " + date);
		}
	}
	
	//Method checks if the certain type of room is booked for the date. The amount of available rooms is decided
	// by the int rooms, in Main. 
	public boolean checkAvailableRooms(String roomType, int rooms) {
		// List contains rooms. If all rooms with the same date
		//are booked, remove room from List. Return List
		String date = room.getVisitDate() + "/" + room.getLeaveDate();
		try(BufferedReader checkRoom = new BufferedReader(new FileReader("Hotell_Booked_Rooms.txt"))){
			String details;
			int count = 0;
			
			while((details = checkRoom.readLine()) != null) {
				String[] splitForDetails = details.split(",");
				String fetchDate = splitForDetails[1].trim();
				String[] checkForOverlap = fetchDate.split("/");
				//Sorry for this piece of ugly code... 
				if((splitForDetails[0].trim().equals(roomType) && splitForDetails[1].trim().equals(date)) || 
						(splitForDetails[0].trim().equals(roomType) && ifTrue(checkForOverlap[0], checkForOverlap[1]))) {
					count++;
					if( count >= rooms) {
//						savedRooms.remove(roomType);
//						return savedRooms;
						return false;
					}
				}
			}
			
		}catch(IOException e) {
			throw new RuntimeException("An error has occured" + e);
		}
		return true;
	}// End checkAvailableRooms method. 
	
	//Method checks that the dates don't overlap. 
	public boolean ifTrue(String visitDate, String leaveDate) {
		if((room.getVisitDate().isBefore(formatDate(leaveDate.trim())) && room.getLeaveDate().isAfter(formatDate(leaveDate.trim())))
			 || (room.getLeaveDate().isAfter(formatDate(visitDate.trim())) && room.getVisitDate().isBefore(formatDate(visitDate.trim())))){
			return true;
		}
		
		return false;
	}

	//Method check for two options in the menu. Cancel booking and Search booking. 
	public String checkBookingNumber(String bookingNumber, int choice, String fedDoc) {
		List<String> bookingDetails = new ArrayList<>();
		
		String returnRemovedBooking = null;
		
		try(BufferedReader toReadData = new BufferedReader(new FileReader(fedDoc));){
			
			String details;
			
			while((details = toReadData.readLine()) != null) {
				
				String[] splitForBookingDetails = details.split(" ");
				boolean found = false;
				
				for(String temp : splitForBookingDetails) {
					if(temp.equals(bookingNumber)) {
						if(choice == 1) { // To get booking details
							return details;
						}else if(choice == 2){ // To cancel booking
							found = true;
							returnRemovedBooking = details;
							break;
						}
					}
				}
				if(!found && (choice == 2)) {
					bookingDetails.add(details);
				}
				
			}//End While-loop
			toReadData.close();
			
			if(choice == 2) {
				try(BufferedWriter toWriteData = new BufferedWriter(new FileWriter(fedDoc));){
					for(String temp : bookingDetails) {
						if(!temp.equals(null)) {
							toWriteData.write(temp);
							toWriteData.newLine();
						}
					}	
					toWriteData.close();
				}
			}
		//Signals that some sort of I/O exception has occurred.
		}catch(IOException e) {
			throw new RuntimeException("An error has occured" + e);
		}
		if(choice == 1) {
			return null;
		}else {
			return returnRemovedBooking;
		}
	}// End checkBookingNumber method
	
	public String userStringInput() {
		String input = userInput.nextLine();
		return input;
	}// End UserString method

//	Handling docs_______________________________________________________________
	
	// Method reads for two documents. One which cerates a doc for all booking details, and one which is used for booked rooms. 
	public void addBookingToDoc(String fedDoc) {
		
		File doc = new File(fedDoc);
		
		try(BufferedWriter toAddData = new BufferedWriter(new FileWriter(doc,true))){//Store booked rooms and dates
			
			
			if(fedDoc.equals("Hotell_BookingDetails.txt")) {
				toAddData.append(room.toString() + ", " + customer.toString());
				toAddData.newLine();
			}else {
				toAddData.append(room.toString() + ", " + customer.getBookingNumber());
				toAddData.newLine();
			}
			
		}catch(IOException e) {
			System.out.println("Document could not be created" + e);
		}
	}// End addBookingToDoc method. 
	
	public void readBookingDetails(String fedDoc) {
		try (BufferedReader toReadData = new BufferedReader(new FileReader(fedDoc));){
			
			StringBuilder buildText = new StringBuilder();
			
			String toPrintTextByLine = toReadData.readLine();
			
			while(toPrintTextByLine  != null) {
				buildText.append(toPrintTextByLine);
				buildText.append(System.lineSeparator());
				toPrintTextByLine = toReadData.readLine();
			}
			System.out.println(buildText.toString());
			toReadData.close();
		//Signals that some sort of I/O exception has occurred.
		}catch(IOException e) {
			throw new RuntimeException("Ett fel har uppstått" + e);
		}
	}
}//End HotellLogic class
