/*
 * AgendaGenerator.java Package: net.sf.memoranda.util Created on 13.01.2004
 * 5:52:54 @author Alex
 */
package main.java.memoranda.util;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.ArrayList;

import main.java.memoranda.*;
import main.java.memoranda.date.CalendarDate;

import java.util.Collections;

import nu.xom.Element;


/**
 *  
 */

/*$Id: AgendaGenerator.java,v 1.12 2005/06/13 21:25:27 velhonoja Exp $*/

public class AgendaGenerator {

	static String HEADER =
			"<html><head><title></title>\n"
					+ "<style>\n"
					+ "    body, td {font: 12pt sans-serif}\n"
					+ "    h1 {font:20pt sans-serif; background-color:#E0E0E0; margin-top:0}\n"
					+ "    h2 {font:16pt sans-serif; margin-bottom:0}\n"
					+ "    li {margin-bottom:5px}\n"
					+ " a {color:black; text-decoration:none}\n"             
					+ "</style></head>\n"
					+ "<body><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n"
					+ "<tr>\n";
	static String FOOTER = "</td></tr></table></body></html>";

	/**
	 *
	 * @param p
	 * @param date
	 * @param tl
	 * @param t
	 * @param level
	 * @param expandedTasks
	 * @return
	 */

	private static String generateCourseInfo(Course course) {
		String formattedDateTime = String.format("%d/%d/%d at %02d:00", course.getClassMonth(), course.getClassDay(), course.getClassYear(), course.getClassHour());
		String instructorName = course.getInstructorName().isEmpty() ? "Not assigned" : course.getInstructorName();

		return "<p><b>Class Name:</b> " + course.getClassName() +
				"<br><b>Instructor:</b> " + instructorName +
				"<br><b>Time:</b> " + formattedDateTime +
				"</p>\n";
	}

	static String generateUpcomingClasses() {
		String s =
				"<td width=\"66%\" valign=\"top\">"
						+ "<h1>"
						+ Local.getString("Upcoming Classes")
						+ "</h1>\n";

		ArrayList<Course> next5Classes = PersistentClass.getNext5Classes();

		if (next5Classes == null || next5Classes.isEmpty()) {
			s += "No upcoming classes";
		} else {
			for (Course course : next5Classes) {
				s += generateCourseInfo(course);
			}
		}

		return s + "</td>";
	}

	static String generatePersonalInfo() {
		String s =
				"<td width=\"34%\" valign=\"top\">"
						+ "<h1>"
						+ Local.getString("Personal Info")
						+ "</h1>\n"
						+"<h2>"
						+ "<b>User: </b>"
						+ User.getUsername() + "<br>"
						+ "<b>Belt Rank: </b>"
						+ User.getBeltRank() + "<br><br><br>"
						+ "<a href=\"memoranda:changebelt\"><b><u>[Change Belt]</b></u></a>";
		if(User.getUserType() == UserType.TRAINER)
		{
			s += "<b>Training Rank: </b>" + User.getTrainingRank();
		}
		return s;
	}
	
	public static String getAgenda(CalendarDate date, Collection expandedTasks) {
		String s = HEADER;
		s += generateUpcomingClasses();
		s += generatePersonalInfo();
		//        /*DEBUG*/System.out.println(s+FOOTER);
		return s + FOOTER;
	}
	/*    
    we do not need this. Tasks are sorted using the Comparable interface
    public static class TaskSorter {

        static final int BY_IMP_RATE = 0;
        static final int BY_END_DATE = 1;
        static final int BY_PRIORITY = 2;
        static final int BY_COMPLETION = 3;

        private static Vector tasks = null;
        private static CalendarDate date = null;  
        private static int mode = 0;

        public static long calcTaskRate(Task t, CalendarDate d) {
            /*
	 * A "Task rate" is an informal index of importance of the task
	 * considering priority, number of days to deadline and current
	 * progress.
	 * 
	 * rate = (100-progress) / (numOfDays+1) * (priority+1)
	 * /
            Calendar endDateCal = t.getEndDate().getCalendar();
            Calendar dateCal = d.getCalendar();
            int numOfDays = (endDateCal.get(Calendar.YEAR)*365 + endDateCal.get(Calendar.DAY_OF_YEAR)) - 
                            (dateCal.get(Calendar.YEAR)*365 + dateCal.get(Calendar.DAY_OF_YEAR));
            if (numOfDays < 0) return -1; //Something wrong ?
            return (100-t.getProgress()) / (numOfDays+1) * (t.getPriority()+1);
        }

        static long getRate(Object task) {
            Task t = (Task)task;
            switch (mode) {
                case BY_IMP_RATE: return -1*calcTaskRate(t, date);
                case BY_END_DATE: return t.getEndDate().getDate().getTime();
                case BY_PRIORITY: return 5-t.getPriority();
                case BY_COMPLETION: return 100-t.getProgress();
            }
            return -1;         
        }

        private static void doSort(int L, int R) { // Hoar's QuickSort
            int i = L;
            int j = R;
            long x = getRate(tasks.get((L + R) / 2));
            Object w = null;
            do {
                while (getRate(tasks.get(i)) < x) 
                    i++;
                while (x < getRate(tasks.get(j)) && j > 0) 
                    if (j > 0) j--;              
                if (i <= j) {
                    w = tasks.get(i);
                    tasks.set(i, tasks.get(j));
                    tasks.set(j, w);
                    i++;
                    j--;
                }
            }
            while (i <= j);
            if (L < j) 
                doSort(L, j);       
            if (i < R) 
                doSort(i, R);         
        }

        public static void sort(Vector theTasks, CalendarDate theDate, int theMode) {
            if (theTasks == null) return;
            if (theTasks.size() <= 1) return;
            tasks = theTasks; 
            date = theDate;
            mode = theMode;
            doSort(0, tasks.size() - 1);
        }

    }
	 */
}
