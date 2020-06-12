package models;

import java.util.ArrayList;
import java.util.List;

public class Event {

	private List<Attendee> attendees;
	private String title;
		
	public Event(){
		attendees = new ArrayList<Attendee>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Attendee> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<Attendee> attendees) {
		this.attendees = attendees;
	}
	
	public void addAttendee(Attendee a){
		attendees.add(a);
	}
	
	public String printAttendees(){
		String list="";
		for(Attendee a: attendees){
			list+=a.getUserName() + "\r\n";
		}
		return list;
	}
	
}
