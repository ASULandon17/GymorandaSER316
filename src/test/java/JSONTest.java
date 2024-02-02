import java.io.File;

public class JSONTest {

    public void deleteJSONFiles() {
        try{
            File doomedClasses = new File("classes.json");
            File doomedUsers = new File("users.json");

            if(doomedClasses.delete() && doomedUsers.delete()) {
                System.out.println("JSON files deleted succesfully for testing");
            } else{
                System.out.println("JSON files were not deleted");
            }


        }catch (SecurityException | NullPointerException e) {
            System.out.println("Program does not have access to delete this file");
        }


    }
}
