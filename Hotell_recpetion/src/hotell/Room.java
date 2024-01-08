package hotell;


import java.time.LocalDate;

public class Room{
	
	protected String roomType;
	protected LocalDate visitDate;	
	protected LocalDate leaveDate;	
	
//	public List<Room> rooms = new ArrayList<>();

	
	public Room() {
//		roomList();
	}

	
	public String getRoomType() {
		return roomType;
	}
	
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	public LocalDate getVisitDate(){
		return visitDate;
	}
	
	public LocalDate getLeaveDate(){
		return leaveDate;
	}
	
	public void setVisitDates(LocalDate visitDate) {
		this.visitDate = visitDate;
	}
	
	public void setLeaveDates(LocalDate leaveDate) {
		this.leaveDate = leaveDate;
	}
	
	@Override
	public String toString() {
		
		return roomType + ", " + visitDate + "/" + leaveDate;
		
	}
	
	
}