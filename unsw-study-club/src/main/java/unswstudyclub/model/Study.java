package unswstudyclub.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Study {

    private Person person;
    private List<Course> courses;

    public Study(Person person) {
        this.person = person;
    }

    public void addCourse(Course c){
        courses.add(c);
    }

    public Person getPerson() {
        return person;
    }

    public List<Course> getCourses() {
        return courses;
    }



    public void setPerson(Person person) {
        this.person = person;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }


}
