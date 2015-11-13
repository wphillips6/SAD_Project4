package proj4.serverapp;

import java.sql.Connection;
import java.util.List;

import proj4.common.Course;
import proj4.common.Semester;


public class StudentEntry {

  public ServerApplication myServerApplication;

  public StudentEntry() {
  }

  public StudentEntry( Connection dbconnection, ServerApplication sa) {
  }

  public void getUsername(String studentID) {
  }

  public void setUsername(String uid) {
  }

  public void getDesiredCourses(String studentID) {
  }

  public void setDesiredCourses(String studentID, Integer n) {
  }

  public void getCoursePrefList(String studentID) {
  }

  public void setCoursePrefList(String studentID,  List<Course> c) {
  }

  public void getSemester() {
  }

  public void setSemester( Semester s) {
  }

  public void getCurrentRecommendation(String studentID) {
  }

}