package memoranda;
import main.java.memoranda.User;
import org.json.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Class {

    private String className;
    private JSONObject trainer;
    private int classLengthInHours;

    int maxStudentsAllowed;

    public ArrayList<JSONObject> students = new ArrayList<>(); // stores the list of students registered for the course

    /**
     * Calculate how many slots remain on the class roster
     * @return number of open spaces
     */
    public int getRemainingStudentSlots() {

        return maxStudentsAllowed - students.size();
    }

    public void setClassLengthInHours(int hours) {
        this.classLengthInHours = hours;
    }
    public void changeClassName(String className) {
        this.className = className;
    }
    /**
     * Adds student to class roster if not full
     * @param studentUserName user attending the class
     */
    public boolean addStudentToClass(String studentUserName) {

        if (this.students.size() < this.maxStudentsAllowed) {

            try {

                File file = new File("users.json");

                if (!file.exists()) {
                    return false; //JSON file not found
                }

                String content = new String(Files.readAllBytes(Paths.get("users.json")));
                JSONArray usersArray = new JSONArray(content);

                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject user = usersArray.getJSONObject(i);
                    // If the user exists and isn't already signed up for the class, add to roster
                    if (user.getString("username").equals(studentUserName) && !this.students.contains(user)) {
                        this.students.add(user);
                    } else {

                        return false; // User already registered for class
                    }
                }
                return true;


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            return false;
        }








    }

    /**
     * Remove a student from the class roster
     * @param student user attending the class
     */
    public void removeStudentFromClass(User student) {
        students.remove(student);
    }

    /**
     * This method allows a manager to change the instructor teaching the course
     * @param newInstructor
     */
    public void changeInstructor(User newInstructor) {
       // this.trainerName = newInstructor;
    }

    /**
     * Constructor for adding a new class to the schedule
     * @param className name of class
     * @param instructor which instructor is teaching the class
     * @param classLengthInHours total length of the class (in hours)
     * @param maxStudentsAllowed max number of students allowed
     */
    public Class(String className, int classLengthInHours, int maxStudentsAllowed) {
        this.className = className;
        this.classLengthInHours = classLengthInHours;
        this.maxStudentsAllowed = maxStudentsAllowed;
    }

    /**
     * Default constructor for Class
     */
    public Class() {
        this.className = "New Class";
        //this.trainerName = null;
        this.classLengthInHours = 1;
    }
}
