package unswstudyclub.model;

import java.sql.Timestamp;
import java.util.List;

public class Tutor {
    private Person person;
    private List<Course> courses;
    private int likes = 0;

    public Tutor(Person person) {
        this.person = person;
    }

    public void addCourse(Course c){
        courses.add(c);
    }

    public Person getPerson() {
        return person;
    }

    public int getLikes() {
        return likes;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }


}
