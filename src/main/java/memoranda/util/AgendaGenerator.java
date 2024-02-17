/*
 * AgendaGenerator.java Package: net.sf.memoranda.util Created on 13.01.2004
 * 5:52:54 @author Alex
 */
package main.java.memoranda.util;

import java.util.Collection;
import java.util.ArrayList;

import main.java.memoranda.*;
import main.java.memoranda.date.CalendarDate;


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
						+ User.getBeltRank();
		// Displays Training Rank after Belt Rank and before Change Belt buttons
		if(User.getUserType() == UserType.TRAINER)
		{
			s += "<br><b>Training Rank: </b>" + User.getTrainingRank();
		}
		s += "<br><br><br>"
						+ "<a href=\"memoranda:changeBelt\"><b><u>[Change Belt]</b></u></a>";
		if(User.getUserType() == UserType.TRAINER)
		{
			s += "<br><a href=\"memoranda:changeTraining\"><b><u>[Change Training Rank]</b></u></a>";
		}
		return s;
	}
	
	public static String getAgenda(CalendarDate date, Collection expandedTasks) {
		String s = HEADER;
		s += generateUpcomingClasses();
		s += generatePersonalInfo();
		return s + FOOTER;
	}
}
