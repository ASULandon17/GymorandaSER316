import main.java.memoranda.User;
import memoranda.Class;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClassTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAddStudents() {

        // Test account - Username : test Password: password

        Class testClass = new Class("test", 2, 10);

        System.out.println("Current students in class: " + testClass.students.size());

        testClass.addStudentToClass("test");

        System.out.println("Current students in class: " + testClass.students.size());

        System.out.println("Student details: " + testClass.students.getFirst().getString("password"));








    }
}
