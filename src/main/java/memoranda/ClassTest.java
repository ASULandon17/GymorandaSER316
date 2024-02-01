package memoranda;
import main.java.memoranda.User;
import org.junit.Test;
import org.junit.Before;

public class ClassTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAddStudents() {

        User.signUp("test", "password", "Trainer");



    }
}
