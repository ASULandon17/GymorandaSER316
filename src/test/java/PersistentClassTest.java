import memoranda.PersistentClass;
import org.junit.*;
import static org.junit.Assert.*;

public class PersistentClassTest {

    @Test
    public void testAddNewClassWithoutTrainer() {
        assertTrue(PersistentClass.addNewClass("spin class", 2, 10, 1));
        assertTrue(PersistentClass.addNewClass("swim class", 3, 10, 2));


    }

    @Test
    public void testAddInstructorToClass() {
        assertEquals(0, PersistentClass.addInstructorToCourse("test", 1));
        assertEquals(0, PersistentClass.addInstructorToCourse("test", 2));

    }

    @Test
    public void testAddStudentToClass() {
        PersistentClass.addStudentToCourse("test", 1);
        PersistentClass.addStudentToCourse("test2", 1);
    }
}
