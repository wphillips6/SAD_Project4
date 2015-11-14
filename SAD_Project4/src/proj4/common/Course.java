package proj4.common;

public class Course {

  public String id;
  public String descriptions;
  public String prerequisite;
  public Semester semester;
  public String corequisite;
  public Semester mySemester;
  public Student myStudent;
  public TeacherAssistant myTeacherAssistant;
  public Professor myProfessor;

  public Course() {
  }

  public Course( String desc) {
  }

  public void getID() {
  }

  public void setID( String i) {
  }

  public void getDescription() {
  }

  public void setDescription( String d) {
  }

  public void getPrerequisite() {
  }

  public void setPrerequisite( Course p) {
  }

  public void getSemester() {
  }

  public void setSemester( Semester s) {
  }

  public void getCorequisite() {
  }

  public void setCorequisite(Course c) {
  }

}