package memoranda;
import main.java.memoranda.User;

import java.util.ArrayList;


public class Class {

   private String className;
    private User trainerName;
    private int classLengthInHours;

    int studentsRegistered;
    int maxStudentsAllowed;

    ArrayList<User> students = new ArrayList<>(); // stores the list of students registered for the course

    /**
     * Calculate how many slots remain on the class roster
     * @return number of open spaces
     */
    public int getRemainingStudentSlots() {

        return maxStudentsAllowed - students.size();
    }

    /**
     * Adds student to class roster if not full
     * @param student user attending the class
     */
    public void addStudentToClass(User student) {
        if (students.size() < maxStudentsAllowed) {
            students.add(student);
        } else {
            throw new IllegalArgumentException("Class is full!");
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
        this.trainerName = newInstructor;
    }

    /**
     * Constructor for adding a new class to the schedule
     * @param className name of class
     * @param instructor which instructor is teaching the class
     * @param classLengthInHours total length of the class (in hours)
     * @param maxStudentsAllowed max number of students allowed
     */
    public Class(String className, User instructor, int classLengthInHours, int maxStudentsAllowed) {
        this.className = className;
        this.trainerName = instructor;
        this.classLengthInHours = classLengthInHours;
        this.maxStudentsAllowed = maxStudentsAllowed;
    }

    /**
     * Default constructor for Class
     */
    public Class() {
        this.className = "New Class";
        this.trainerName = null;
        this.classLengthInHours = 1;
    }
}
