package models;

public class Calendar {

	final static int ROWS=9;
	final static int COLUMNS=7;
	
	public Event[][] events;
	
	public Calendar(){
            events = new Event[ROWS][COLUMNS];
            
          /*  for(int i=0 ;i<ROWS ;i++){
          
            for(int j=0 ;j<COLUMNS ; j++)
            {
            events[i][j] =new Event();
            }
            }*/
		
	}
	
	public void scheduleEvent(int time, int day , String title ){
		Event e = new Event();
		events[time][day] = e;
                events[time][day].setTitle(title);
                 // events[time][day].
		
	}
	
	public Event[][] getEvents(){
		return events;
	}
	
	public Event getEvent(int time, int day){
		return events[time][day];
	}
	
}
