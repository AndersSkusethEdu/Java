import java.util.ArrayList;

public class Student extends Person {
    private String program;
    private ArrayList<Person> guests;

    public Student(String firstName,String lastName, String program) {
        super(firstName, lastName);
        this.program = program;
        this.guests = new ArrayList<>();
    }

}
