package proj4.common;

import java.util.ArrayList;
import java.util.List;

public class Recommendation {

    private String id;

    private int capacity;

    private Course course;

    private Professor professor;

    private List<TeacherAssistant> teacherAssistants;
    private List<Student> students;

    public Recommendation() {
    }

    public Recommendation(Course course) {
        this.course = course;
        this.teacherAssistants = new ArrayList<TeacherAssistant>();
        this.students = new ArrayList<Student>();
        this.capacity = Integer.MAX_VALUE;
    }

    public void enrollStudent(Student s) {
        this.students.add(s);
    }

    public String getId() {
        return id;
    }

    public void setId(String i) {
        this.id = i;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course c) {
        this.course = c;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor p) {
        this.professor = p;
    }

    public List<TeacherAssistant> getTeacherAssistants() {
        return teacherAssistants;
    }

    public void setTeacherAssistants(List<TeacherAssistant> teacherAssistants) {
        this.teacherAssistants = teacherAssistants;
    }

    public int getStudentCapacity() {
        return capacity;
    }

    public void setStudentCapacity(int c) {
        this.capacity = c;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> s) {
        this.students = s;
    }
}