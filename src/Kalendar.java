import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Kalendar {
	/**
	 * Program that displays calendar for year and month, also it shows
	 * reminders for selected month and offers to add new reminder.
	 */
	public static void main(String[] args) throws IOException {
		CalendarReminder c = new CalendarReminder();
		c.list = readingFromFile(c);									//populating list by reading from the file
		boolean spin = true;
		while (spin) {													//loop makes the program don't stop running
			userChoice(c);												//invoking the method that offers the choice to the user
			writingToFile(c);											//writing the content of the array list to the file(year, month and note)
		}
	}

	public static String whichMonth(int monthNum) {						//we get month name by passing month number to the method
		String month = "";
		switch (monthNum) {
		case 1:
			month = "January";
			break;
		case 2:
			month = "February";
			break;
		case 3:
			month = "March";
			break;
		case 4:
			month = "April";
			break;
		case 5:
			month = "May";
			break;
		case 6:
			month = "June";
			break;
		case 7:
			month = "July";
			break;
		case 8:
			month = "August";
			break;
		case 9:
			month = "September";
			break;
		case 10:
			month = "Oktober";
			break;
		case 11:
			month = "November";
			break;
		case 12:
			month = "December";
			break;
		default:
			month = "Invalid month";
			break;
		}
		return month;

	}

	public static void printMonth(int month, int year) {
		// Method that prints out month to the console.
		if (month != 0 && year != 0) {
			System.out.println(whichMonth(month) + "\t" + year + "\n");
			System.out.println("Sun\tMon\tTue\tWed\tThu\tFri\tSat");
			System.out
					.println("___________________________________________________\n");
			int firstDayInMonth = getFirstDay(year, month);	//getting first day of the month by invoking the method
			if(firstDayInMonth%7!=0){
			
			for (int i = 0; i < firstDayInMonth; i++)									//printing spaces up to first month
				System.out.print("\t");
			}
			for (int i = 1; i <= (numberOfDaysInMonth(year, firstDayInMonth)); i++) {	//printing dates for the rest of the month
				if (i < 10) {															//loop goes up to number of days in the month
					System.out.print(" " + i + "\t");
				} else {
					System.out.print(i + "\t");
				}
				if ((firstDayInMonth + i) % 7 == 0) {
					System.out.println();
				}

			}
			System.out.println();
		}

	}

	public static int numberOfDaysInMonth(int year, int month) {
		/**
		 * Method that determines how many days month have. Calculate with year and month user entered.
		 */
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12)
			return 31;

		if (month == 4 || month == 6 || month == 9 || month == 11)
			return 30;

		if (month == 2
				&& (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)))
			return 29;
		else
			return 28;
	}

	public static void userChoice(CalendarReminder c) throws IOException {
		/**
		 * Method that deals with interaction with user, offers him to enter year and month
		 */
		c.list = readingFromFile(c);
		Scanner input = new Scanner(System.in);
		int year = 0;
		int month = 0;
		System.out.println("Enter year and month:");
		try {
			year = input.nextInt();
			month = input.nextInt();
		} catch (InputMismatchException ex) {
			System.out
					.println("You didn't enter a year and month in a \"YYYY MM\" format.");
			userChoice(c);
		}
		printMonth(month, year);		//invoking method for printing month for users month and year
		System.out
				.println("\n1. See notes for current month\n2. Add notes for current month\nEnter anything else to go to year/month input");
		int num = 0;
		try {
			num = input.nextInt();
		} catch (InputMismatchException ex) {

		}
		if (num == 1) {
			findNotes(c, year, month);	//method that finds notes for current month
		} else if (num == 2) {
			c.list = noteToList(c.list, year, month); //method that adds notes to list
		}

	}

	public static ArrayList<CalendarArrayList> readingFromFile(
			//Method that reads from file and populate arraylist
			CalendarReminder c) throws FileNotFoundException {
		File file = new File("notes.txt");					//file object which we read to populate list
		Scanner in = new Scanner(file);
		ArrayList<CalendarArrayList> list = new ArrayList<>();	
		while (in.hasNextLine()) {
			String note = "";
			CalendarArrayList newNote = new CalendarArrayList();
			String[] line = in.nextLine().split(" ");		//reading from file line by line,and parsing string into tokens
			newNote.setYear(Integer.parseInt(line[0]));		//first element will be year
			newNote.setMonth(Integer.parseInt(line[1]));	//second element will be month
			for (int i = 2; i < line.length; i++) {			//rest of the elements are reminder/note
				note += line[i] + " ";
			}
			newNote.setNote(note.trim());					//setting notes and triming the string from trailing spaces
			list.add(newNote);

		}
		return list;

	}

	public static void writingToFile(CalendarReminder c) throws IOException {
		//Method that writes to content of the list to the file
		File file = new File("notes.txt");
		FileWriter fw = new FileWriter(file);
		try {
			for (int i = 0; i < c.getList().size(); i++) {
				fw.write(String.valueOf(c.list.get(i).getYear() + " "));		//first "word" will be year
				fw.write(String.valueOf(c.list.get(i).getMonth() + " "));		//second "word" will be month of the month/year user entered
				fw.write(c.list.get(i).getNote() + "\n");						//writing the note and switching to the next line
			}
		} finally {
			fw.close();
		}

	}

	public static void findNotes(CalendarReminder c, int year, int month) {
		//Method that goes through the list to find all the notes for current month
		Scanner input = new Scanner(System.in);
		int count = 0;
		if (c.list != null) {
			for (int i = 0; i < c.list.size(); i++) {			//loop checking every element of the list
				if (c.list.get(i).getYear() == year				//if month and year matches we're printing out the note
						&& c.list.get(i).getMonth() == month) {
					count++;
					System.out.println(c.list.get(i).getNote());
				}
			}
			if (count == 0) {									//if there were no notes for current month we inform the user
				System.out.println("No notes found for current month.");
			}
		}

	}

	public static ArrayList<CalendarArrayList> noteToList(
			ArrayList<CalendarArrayList> list, int year, int month) {
		//Method that adds notes to the list
		System.out.println("Enter your note:");
		Scanner input = new Scanner(System.in);
		CalendarArrayList oneInput = new CalendarArrayList();
		oneInput.setNote(input.nextLine());					//setting note with string user entered
		oneInput.setYear(year);								//setting year to the value of year user entered
		oneInput.setMonth(month);							//setting moth to the value of month user entered
		list.add(oneInput);									//adding the new object to the list 
		return list;

	}

	public static int getFirstDay(int year, int month) {
		//Method that uses YearMonth class to get first day of the month for the year and month user entered
		YearMonth ym = YearMonth.of(year, month);
		return ym.atDay(1).getDayOfWeek().getValue();		//returning value for the first day of the month in relation to first day of the week

	}

}
