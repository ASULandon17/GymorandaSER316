package main.java.memoranda;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * This class provides the backend for interacting with Classes within Gymoranda.
 */
public class PersistentClass {

    private static final List<Course> courses = new ArrayList<>();


    static {
        loadClassesFromFile();
    }

    /**
     * Provide updated courses.
     *
     * @return updated course list
     */
    public static List<Course> getListOfCourses() {
        loadClassesFromFile();
        return courses;
    }

    /**
     * Clears the ArrayList and the json file.
     */
    public static void clearCourses() {
        courses.clear();
        clearCourseFile();
    }

    private static void clearCourseFile() {

        try (FileWriter file = new FileWriter("classes.json")) {

            file.write(new JSONArray().toString()); // Write an empty array to the file

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the classes to the arraylist from the JSON file.
     */
    public static void loadClassesFromFile() {

        try {

            String content = new String(Files.readAllBytes(Paths.get("classes.json")));
            JSONArray jsonArray = new JSONArray(content);
            courses.clear(); // Clear existing rooms before loading

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                courses.add(new Course(jsonObject));
            }

        } catch (IOException e) {

            System.out.println("No existing classes.json found. A new one will be created.");
        }
    }


    /**
     * Saves the Room list to the json file.
     */
    public static void saveClassesToFile() {

        JSONArray jsonArray = new JSONArray();

        for (Course course : courses) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("className", course.getClassName());
            jsonObject.put("instructorName", course.getInstructorName());
            jsonObject.put("classLength", course.getClassLength());
            jsonObject.put("maxClassSize", course.getMaxClassSize());
            jsonObject.put("currentClassSize", course.getCurrentClassSize());
            jsonObject.put("classID", course.getClassId());
            jsonObject.put("isPublic", course.getPublic());
            jsonObject.put("roster", course.getRoster());
            jsonObject.put("year", course.getClassYear());
            jsonObject.put("month", course.getClassMonth());
            jsonObject.put("day", course.getClassDay());
            jsonObject.put("hour", course.getClassHour());
            jsonObject.put("isAdvanced", course.isCourseAdvanced());

            jsonArray.put(jsonObject);
        }

        try (FileWriter file = new FileWriter("classes.json")) {

            file.write(jsonArray.toString(4)); // Pretty print

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    /**
     * Ensures JVM doesn't make a default constructor for utility class.
     */
    private PersistentClass() {
        // No objects here!
    }

    /**
     * Helper method to look up a course by its unique ID.
     *
     * @param classId unique class id
     * @return Course object
     */
    public static Course getCourseById(int classId) {

        for (Course course : courses) {

            if (course.getClassId() == classId) {
                return course;
            }
        }

        return null; // no matching course found
    }

    /**
     * This method allows the owner to add a new class if they also do not know which instructor
     * will teach it yet.
     *
     * @param className    name of the class
     * @param classLength  length of the class in hours
     * @param maxClassSize max class size
     * @param classId      unique class ID (int)
     * @param year         year of date class is scheduled
     * @param month        month of date class is scheduled
     * @param day          day of date class is scheduled
     * @param hour         hour of date class is scheduled
     */
    public static void addNewClass(String className, int classLength, int maxClassSize,
                                   int classId, boolean classIsPublic, int year, int month,
                                   int day, int hour, boolean isAdvanced) {

        // check if class already exists:
        if (getCourseById(classId) == null) {
            courses.add(new Course(className, classLength, maxClassSize, classId,
                    classIsPublic, year, month, day, hour, isAdvanced));
            saveClassesToFile();

        } else {
            System.out.println("Class already exists");
        }

    }

    /**
     * This method allows the owner to add a new class if they do know the instructor that will be
     * teaching.
     *
     * @param className          name of the class
     * @param classLength        length of the class in hours
     * @param maxClassSize       max class size
     * @param classId            unique class ID (int)
     * @param instructorUserName name of instructor teaching the course
     */
    public static void addNewClass(String className, int classLength, int maxClassSize,
                                   int classId, boolean classIsPublic, String instructorUserName,
                                   int year, int month, int day, int hour, boolean isAdvanced) {

        // check if class already exists:
        if (getCourseById(classId) == null) {
            courses.add(new Course(className, classLength, maxClassSize, classId, classIsPublic,
                    instructorUserName, year, month, day, hour, isAdvanced));
            saveClassesToFile();

        } else {
            System.out.println("Class already exists");
        }

    }


    /**
     * This method adds an instructor to a specific class.
     *
     * @param instructorUserName username of instructor
     * @param classId            classID
     */
    public static void addInstructorToCourse(String instructorUserName, int classId) {

        for (Course course : courses) {
            if (course.getClassId() == classId) {

                // check if there's already an instructor
                if (course.getInstructorName().isEmpty()) {

                    course.setInstructorName(instructorUserName);

                } else {

                    System.out.println("There is already an instructor assigned to this course!");
                }
            }
        }
    }

    /**
     * addStudentToCourse() allows user to register for a course as long as the course isn't full
     * AND they aren't already registered.
     *
     * @param studentUserName username of student registering
     * @param classId         classId for the course they want to register for
     */
    public static void addStudentToCourse(String studentUserName, int classId) {

        for (Course course : courses) {

            // make sure course exists
            if (course.getClassId() == classId) {

                // see if student is already registered
                if (!course.isStudentRegistered(studentUserName)) {

                    course.addStudentToRoster(studentUserName);
                }
            }
        }
        saveClassesToFile();
    }

    /**
     * removeStudentFromCourse() removes student from specified course.
     *
     * @param studentUserName username of student dropping course
     * @param classId         classId for the course they will be removed from
     */
    public static void removeStudentFromCourse(String studentUserName, int classId) {

        for (Course course : courses) {

            // make sure course exists
            if (course.getClassId() == classId) {

                // see if student is already registered
                if (course.isStudentRegistered(studentUserName)) {

                    course.removeStudentFromRoster(studentUserName);
                }
            }
        }
        saveClassesToFile();
    }

    /**
     * deleteCourseById() removes course with specified id from class.json
     *
     * @param classId classId for the course they want to register for
     */
    public static void deleteCourseById(int classId) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getClassId() == classId) {
                courses.remove(i);
                saveClassesToFile();
                return;
            }
        }
    }

    /**
     * Returns the next 5 classes that are soonest to happen based on the current date and time, or
     * null if there are no upcoming classes.
     *
     * @return ArrayList of the next 5 upcoming Course objects, or null if no classes are scheduled.
     */
    public static ArrayList<Course> getNext5Classes() {
        LocalDateTime now = LocalDateTime.now();

        ArrayList<Course> upcomingCourses = PersistentClass.getListOfCourses().stream()
                .filter(course -> {
                    LocalDateTime courseDateTime = LocalDateTime.of(course.getClassYear(),
                            course.getClassMonth(), course.getClassDay(), course.getClassHour(),
                            0);
                    return courseDateTime.isAfter(now);

                }).sorted((course1, course2) -> {

                    LocalDateTime courseDateTime1 = LocalDateTime.of(course1.getClassYear(),
                            course1.getClassMonth(), course1.getClassDay(),
                            course1.getClassHour(), 0);

                    LocalDateTime courseDateTime2 = LocalDateTime.of(course2.getClassYear(),
                            course2.getClassMonth(), course2.getClassDay(),
                            course2.getClassHour(), 0);

                    return courseDateTime1.compareTo(courseDateTime2);
                }).collect(Collectors.toCollection(ArrayList::new));

        // Check if there are no upcoming courses and return null
        if (upcomingCourses.isEmpty()) {
            return null;
        }

        // Return the top 5 or fewer if less than 5 upcoming classes are available
        return upcomingCourses.size() > 5 ? new ArrayList<>(upcomingCourses.subList(0, 5))
                : upcomingCourses;
    }

    /**
     * Returns the next 5 enrolled classes for a given username.
     *
     * @param username unique username
     * @return user's next 5 courses
     */
    public static ArrayList<Course> getNext5EnrolledClasses(String username) {
        LocalDateTime now = LocalDateTime.now();

        ArrayList<Course> enrolledUpcomingCourses = PersistentClass.getListOfCourses()
                .stream().filter(course -> {
                    LocalDateTime courseDateTime = LocalDateTime.of(course.getClassYear(),
                            course.getClassMonth(), course.getClassDay(), course.getClassHour(),
                            0);
                    boolean isFutureCourse = courseDateTime.isAfter(now);

                    // New check for user enrollment in the updated roster format
                    boolean isUserEnrolled = false;
                    JSONArray roster = course.getRoster();
                    for (int j = 0; j < roster.length(); j++) {
                        JSONObject enrolledUser = roster.getJSONObject(j);
                        if (enrolledUser.getString("userName").equals(username)) {
                            isUserEnrolled = true;
                            break;
                        }
                    }
                    return isFutureCourse && isUserEnrolled;
                }).sorted((course1, course2) -> {
                    LocalDateTime courseDateTime1 = LocalDateTime.of(course1.getClassYear(),
                            course1.getClassMonth(), course1.getClassDay(), course1.getClassHour(),
                            0);

                    LocalDateTime courseDateTime2 = LocalDateTime.of(course2.getClassYear(),
                            course2.getClassMonth(), course2.getClassDay(), course2.getClassHour(),
                            0);
                    return courseDateTime1.compareTo(courseDateTime2);
                }).collect(Collectors.toCollection(ArrayList::new));

        // Return null if there are no upcoming courses the user is enrolled in
        if (enrolledUpcomingCourses.isEmpty()) {
            return null;
        }

        // Return the top 5 or fewer if less than 5 upcoming classes are available and the user
        // is enrolled in them
        return enrolledUpcomingCourses.size() > 5
                ?
                new ArrayList<>(enrolledUpcomingCourses.subList(0, 5)) : enrolledUpcomingCourses;
    }
}
