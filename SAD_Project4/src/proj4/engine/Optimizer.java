package proj4.engine;

import java.util.ArrayList;
import java.util.List;

import proj4.common.Course;
import proj4.common.CourseCatalog;
import proj4.common.Professor;
import proj4.common.Recommendation;
import proj4.common.Student;
import proj4.common.TeacherAssistant;

import gurobi.GRB;
import gurobi.GRB.DoubleAttr;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class Optimizer {
		
	private CourseCatalog cCatalog = new CourseCatalog();
    private List<Student> students = new ArrayList<Student>();
    private List<Professor> professors = new ArrayList<Professor>();
    private List<TeacherAssistant> teacherAssistants = new ArrayList<TeacherAssistant>();

	public Optimizer() {
		
	}
	
	public Optimizer(CourseCatalog cc, List<Student> s, List<Professor> p, List<TeacherAssistant> t) {
		cCatalog = cc;
		students = s;
		professors = p;
		teacherAssistants = t;
	}

	//Calculate the optimal schedule for student
	public List<Recommendation> Calculate() {
		
		GRBEnv env;
		List<Recommendation> recommendations = null;
		try {
			env = new GRBEnv("mip1.log");
			GRBModel model = new GRBModel(env);
			
			int nStudents = students.size();
			int nProfessors = professors.size();
			int nTAs = teacherAssistants.size();
			int nCourses = cCatalog.getCourseCatalogSize();
			
			// Hard code Constraints
			int maxProfessorCourses = 2; 				// change to 1 or 2
            int maxTACourses = 2; 						// change to 1 or 2
			
            // Create Student History variables for [student][course]
            GRBVar[][] studentHistoryVar = new GRBVar[nStudents][nCourses];
            for (int i = 0; i < nStudents; i++) {
                for (int j = 0; j < nCourses; j++) {
                    studentHistoryVar[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "sh_" + (i + 1) + "_" + (j + 1));
                }
            }
			
			// Create Student Variables for [student][course]	
			GRBVar[][] studentVar = new GRBVar[nStudents][nCourses];
			for (int i = 0; i < nStudents; i++) {
				for (int j = 0; j < nCourses; j++) {
					studentVar[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "s_" + (i + 1) + "_" + (j + 1));
				}
			}
			
            // Create Professor Variables for [professor][course]
            GRBVar[][] professorVar = new GRBVar[nProfessors][nCourses];
            for (int i = 0; i < nProfessors; i++) {
                for (int j = 0; j < nCourses; j++) {
                    professorVar[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "p_" + (i + 1) + "_" + (j + 1));
                }
            }

            // Create TA Variables for [ta][course]
            GRBVar[][] taVariables = new GRBVar[nTAs][nCourses];
            for (int i = 0; i < nTAs; i++) {
                for (int j = 0; j < nCourses; j++) {
                	taVariables[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "t_" + (i + 1) + "_" + (j + 1));
                }
            }

			// Integrate new variables
			model.update();
						
			// *** Course Constraints ***

			// Student Desired Course Constraint
			// Makes sure each student takes their preferred courses once.
			for (int i = 0; i < nStudents; i++) {
				int numCourses = students.get(i).getNumDesiredCourses();
				List<Course> desiredCourses = students.get(i).getDesiredCourses();
				GRBLinExpr expr = new GRBLinExpr();
				for (int d = 0; d < numCourses; d++) {
					int j = (Integer.parseInt(desiredCourses.get(d).getNumber()) - 1);
					expr.addTerm(1.0, studentVar[i][j]);
				}
				model.addConstr(expr, GRB.EQUAL, 1.0, "student_" + (i + 1) + "_DesiredCourses");
			}

			// Capacity Limit Constraint - Does not appear to affect anything
			// Makes sure the number of students does not exceed
			// the course's capacity limit.
			for (int j = 0; j < nCourses; j++) {
				Course course = cCatalog.getCourse(j);
				int limit = course.getEnrollLim();
				GRBLinExpr expr = new GRBLinExpr();
				for (int i = 0; i < nStudents; i++) {
					expr.addTerm(1.0, studentVar[i][j]);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, limit, "CapLimit_course_"	+ (j + 1));
			}

			// Student number of courses for next semester Constraint
			// Makes sure that the student is not exceeding the number
			// of courses they want to take next semester.
			for (int i = 0; i < nStudents; i++) {
				int nMax = students.get(i).getNumDesiredCourses();
				GRBLinExpr expr = new GRBLinExpr();
				for (int j = 0; j < nCourses; j++) {
					expr.addTerm(1.0, studentVar[i][j]);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, nMax,	"Max_Courses_For_student_" + (i + 1));
			}

			// Student can take course once Constraint
			// Makes sure a student does not retake a class.
			for (int i = 0; i < nStudents; i++) {
				List<Course> completedCourses = students.get(i).getCompletedCourses();
				GRBLinExpr expr = new GRBLinExpr();
				for (int c = 0; c < completedCourses.size(); c++) {
					int j = Integer.parseInt(completedCourses.get(c).getNumber());
					expr.addTerm(1.0, studentHistoryVar[i][j]);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, 1.0, "TakenOnce_student_"	+ (i + 1));
			}

			// Course needs 1 student Constraint
			// Makes sure there is at least 1 student in each course.
			for (int j = 0; j < nCourses; j++) {
				GRBLinExpr expr = new GRBLinExpr();
				for (int i = 0; i < nStudents; i++) {
					expr.addTerm(1.0, studentVar[i][j]);
				}
				model.addConstr(expr, GRB.GREATER_EQUAL, 1.0, "NeedOneStudent_course_" + (j + 1));
			}

            // *** Faculty Constraint ***
            
			// Professor Course Competences Constraint
			// Makes sure each Professor is assigned to their competences.
			for (int i = 0; i < nProfessors; i++) {
				List<Course> comps = professors.get(i).getCompetencies();
				GRBLinExpr expr = new GRBLinExpr();
				for (int c = 0; c < comps.size(); c++) {
					int j = (Integer.parseInt(comps.get(c).getNumber()) - 1);
					expr.addTerm(1.0, professorVar[i][j]);
				}
				model.addConstr(expr, GRB.EQUAL, 1.0, "professor_" + (i + 1) + "_Competencies");
			}
			 

			// TA Course Competences Constraint
			// Makes sure each TA is assigned to their competences.
			for (int i = 0; i < nTAs; i++) {
				List<Course> comps = teacherAssistants.get(i).getTeachableCourses();
				GRBLinExpr expr = new GRBLinExpr();
				for (int c = 0; c < comps.size(); c++) {
					int j = (Integer.parseInt(comps.get(c).getNumber()) - 1);
					expr.addTerm(1.0, taVariables[i][j]);
				}
				model.addConstr(expr, GRB.EQUAL, 1.0, "ta" + (i + 1) + "_Competencies");
			}
			
            // Max courses per professor and TA Constraint
            // Makes sure that the number of courses a professor
            // or TA has does not exceed the number they are allowed.
			for (int i = 0; i < nProfessors; i++) {
				GRBLinExpr expr = new GRBLinExpr();
				for (int j = 0; j < nCourses; j++) {
					expr.addTerm(1.0, professorVar[i][j]);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, maxProfessorCourses, "One_professor_" + (i + 1));
			}
			for (int i = 0; i < nTAs; i++) {
				GRBLinExpr expr = new GRBLinExpr();
				for (int j = 0; j < nCourses; j++) {
					expr.addTerm(1.0, taVariables[i][j]);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, maxTACourses, "One_ta_" + (i + 1));
			}

			// Each course has 1 Professor Constraint
			// Makes sure that there is at least 1 professor for each course.			
			for (int j = 0; j < nCourses; j++) {
				GRBLinExpr expr = new GRBLinExpr();
				for (int i = 0; i < nProfessors; i++) {
					expr.addTerm(1.0, professorVar[i][j]);
				}
				model.addConstr(expr, GRB.GREATER_EQUAL, 1.0, "NeedOneTA_course_" + (j + 1));
			}

			// Each course has 1 TA Constraint
			// Makes sure that there is at least 1 TA for each course.		
			for (int j = 0; j < nCourses; j++) {
				GRBLinExpr expr = new GRBLinExpr();
				for (int i = 0; i < nTAs; i++) {
					expr.addTerm(1.0, taVariables[i][j]);
				}
				model.addConstr(expr, GRB.GREATER_EQUAL, 1.0, "NeedOneTA_course_" + (j + 1));
			}
            
			// Set the objective as the sum of all student-courses
			GRBLinExpr obj = new GRBLinExpr();
			for (int i = 0; i < nStudents; i++) {
				int priority = 1;
				for (int j = 0; j < nCourses; j++) {
					double coefficient = students.get(i).getCreditsCompleted() + (1.0 / priority * 10);
					obj.addTerm(coefficient, studentVar[i][j]);
					priority++;
					obj.addTerm(1.0, studentVar[i][j]);
				}
			}
			model.setObjective(obj, GRB.MAXIMIZE);

			// Optimize the model
			model.optimize();
			
            // *** Solution ***
            
            recommendations  = new ArrayList<Recommendation>();
            for (int j = 0; j < nCourses; j++) {
                Recommendation rec = null;
                for (int i = 0; i < nStudents; i++) {
                    if (studentVar[i][j].get(DoubleAttr.X) == 1) {
                        if (rec == null) {
                        	rec = new Recommendation(cCatalog.getCourse(j));
                        }
                        Student stud = students.get(i);
                        rec.enrollStudent(stud);
                    }
                }
                if (rec != null) {
                    for (int i = 0; i < nProfessors; i++) {
                        if (professorVar[i][j].get(DoubleAttr.X) == 1) {
                        	Professor prof = professors.get(i);
                        	rec.setProfessor(prof);
                            break;
                        }
                    }
                    List<TeacherAssistant> tas = new ArrayList<TeacherAssistant>();
                    int count = 0;
                    for (int i = 0; i < nTAs; i++) {
                        if (taVariables[i][j].get(DoubleAttr.X) == 1) {
                        	TeacherAssistant ta = teacherAssistants.get(i); 
                            tas.add(ta);
                            if (count == (rec.getStudents().size() / 10))
                            	break;
                            count++;
                        }
                    }
                    rec.setTeacherAssistants(tas);
                    recommendations.add(rec);
                }
            }
            
            for (Recommendation rec : recommendations) {
            	System.out.printf("Course " + rec.getCourse().getID() + " " + rec.getCourse().getDescription() 
            			+ " with professor " + rec.getProfessor().getName() + " with TA ");
                for (TeacherAssistant ta : rec.getTeacherAssistants()) {
                	System.out.printf(ta.getName() + " ");
                }
                System.out.printf("\nStudents:");
                for (Student student : rec.getStudents()) {
                	System.out.printf("\t" + student.getName());
                }
                System.out.printf("\n\n");
            }
            
            model.dispose();
            env.dispose();
			
		} catch (GRBException e) {
			e.printStackTrace();
		}
		return recommendations;
	}
}
