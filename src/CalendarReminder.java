import java.util.ArrayList;

public class CalendarReminder {
	//calendar class that holds array list with CalendarArrayList data type, and set and get method for the list
	 ArrayList<CalendarArrayList> list= new ArrayList<>();

	public CalendarReminder() {

	}

	public CalendarReminder(ArrayList<CalendarArrayList> list) {
		this.list = list;
	}

	public ArrayList<CalendarArrayList> getList() {
		return list;
	}

	public void setList(ArrayList<CalendarArrayList> list) {
		this.list = list;
	}

}

class CalendarArrayList {
	//class that holds variables year, month and note that we will use to store notes/reminders, and set and get methods for them
	private int year;
	private int month;
	private String note;

	public CalendarArrayList() {
	}

	public CalendarArrayList(int year, int month, String note) {
		this.year = year;
		this.month = month;
		this.note = note;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
