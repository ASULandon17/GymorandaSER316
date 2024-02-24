/*
 * AgendaGenerator.java Package: net.sf.memoranda.util Created on 13.01.2004
 * 5:52:54 @author Alex
 */

package main.java.memoranda.util;

import java.util.ArrayList;
import java.util.Collection;

import main.java.memoranda.BeltValue;
import main.java.memoranda.Course;
import main.java.memoranda.PersistentClass;
import main.java.memoranda.User;
import main.java.memoranda.UserType;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.ui.App;


/**
 *  Creates the HTML format for the Home page of Gymoranda.
 */

/*$Id: AgendaGenerator.java,v 1.12 2005/06/13 21:25:27 velhonoja Exp $*/

public class AgendaGenerator {

    static String HEADER =
            "<html><head><title></title>\n"
                    + "<style>\n"
                    + "    body, td {font: 12pt sans-serif}\n"
                    + "    h1 {font:20pt sans-serif; background-color:#6bb1d1; margin-top:0}\n"
                    + "    h2 {font:16pt sans-serif; margin-bottom:0}\n"
                    + "    li {margin-bottom:5px}\n"
                    + " a {color:black; text-decoration:none}\n"
                    + "<body style=background-color:#b9e0f3>"
                    + "</style></head>\n"
                    + "<body><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"4\""
                    + " cellspacing=\"4\">\n"
                    + "<tr>\n";
    static String FOOTER = "</td></tr></table></body></html>";

    private static String generateCourseInfo(Course course) {
        String formattedDateTime = String.format("%d/%d/%d at %02d:00", course.getClassMonth(),
                course.getClassDay(), course.getClassYear(), course.getClassHour());
        String instructorName = course.getInstructorName().isEmpty() ? "Not assigned" :
                course.getInstructorName();

        return "<p><b>Class Name:</b> " + course.getClassName()
                + "<br><b>Instructor:</b> " + instructorName
                + "<br><b>Time:</b> " + formattedDateTime
                + "</p>\n";
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
                s += generateCourseInfo(course) + "<br>";
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
                        + "<h2>"
                        + "<b>User: </b>"
                        + User.getUsername() + "<br>"
                        + "<b>Belt Rank: </b><span style=\"color: "
                        + beltColor(User.getBeltRank()) + "\">"
                        + User.getBeltRank();
        // Displays Training Rank after Belt Rank and before Change Belt buttons
        if (User.getUserType() == UserType.TRAINER) {
            s += "</span><br><b>Training Rank: </b><span style=\"color: "
                    + beltColor(User.getTrainingRank()) + "\">" + User.getTrainingRank();
        }
        s += "</span><br><br><br>"
                        + "<a href=\"memoranda:changeBelt\"><b><u>[Change Belt]</b></u></a>";
        if (User.getUserType() == UserType.TRAINER) {
            s += "<br><a href=\"memoranda:changeTraining\"><b><u>[Change Training Rank]</b></u></a>";
        }
        // Add Gymoranda image
        // Set the spacing to put the logo at the bottom right
        int spacing = 15;
        if (User.getUserType() == UserType.TRAINER) {
            spacing = 13;
        }
        for (int i = 0; i < spacing; i++) {
            s += "<br>";
        }
        s += "<img src=" + App.class.getResource("/ui/Gymoranda.png") + ">";
        return s;
    }

    /**
     * Creates the Home page with all updated values.
     * @param date Date
     * @param expandedTasks Tasks
     * @return HTML format
     */
    public static String getAgenda(CalendarDate date, Collection expandedTasks) {
        String s = HEADER;
        s += generateUpcomingClasses();
        s += generatePersonalInfo();
        return s + FOOTER;
    }

    /**
     * Method to return the Hex color of the belt for display.
     * @return The hex code for the color of the belt
     */
    public static String beltColor(BeltValue belt) {
        // No belt is red
        String hexCode = "#ca0000";
        if (belt == BeltValue.WHITE) {
            hexCode = "#FFFFFF";
        } else if (belt == BeltValue.YELLOW) {
            hexCode = "#f8fe5a";
        } else if (belt == BeltValue.ORANGE) {
            hexCode = "#FFA500";
        }  else if (belt == BeltValue.PURPLE) {
            hexCode = "#800080";
        }  else if (belt == BeltValue.BLUE || belt == BeltValue.BLUE_STRIPE) {
            hexCode = "#0000FF";
        }  else if (belt == BeltValue.GREEN || belt == BeltValue.GREEN_STRIPE) {
            hexCode = "#007e00";
        }  else if (belt == BeltValue.BROWN1 || belt == BeltValue.BROWN2
                || belt == BeltValue.BROWN3) {
            hexCode = "#964B00";
        }  else if (belt == BeltValue.BLACK1 || belt == BeltValue.BLACK2
                || belt == BeltValue.BLACK3) {
            hexCode = "#000000";
        }

        return hexCode;
    }
}
