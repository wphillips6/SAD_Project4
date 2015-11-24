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
import gurobi.GRBConstr;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class Optimizer {
	
	// The Student Matrix
	Integer[][] aStudent;
	// The Student History Matrix
	Integer[][] aStudentHistory;
	// The Professor Matrix
	Integer[][] aProfessor;
	// The TA Matrix
	Integer[][] aTA;
	
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

		// Fill up the Student Matrix
		aStudent = ConvertToStudentMatrix();
		// Fill up the Student History Matrix
		aStudentHistory = ConvertToStudentHistoryMatrix();
		// Fill up the Professor Matrix
		aProfessor = ConvertToProfessorMatrix();
		// Fill up the TA Matrix
		aTA = ConvertToTAMatrix();
	}

	// Method to convert Student Preferences into the array
	public Integer[][] ConvertToStudentMatrix() {
		Integer[][] As = new Integer[students.size()][cCatalog
				.getCourseCatalogSize()];
		for (int i = 0; i < students.size(); i++) {
			for (int j = 0; j < As[i].length; j++) {
				As[i][j] = 0;
			}

			// Set the desired course to the student array
			Student stud = students.get(i);
			List<Course> desiredCourses = stud.getDesiredCourses(Integer.parseInt(stud.getNumCourses()));
			for (int j = 0; j < desiredCourses.size(); j++) {
				Course c = desiredCourses.get(j);
				As[i][Integer.parseInt(c.getNum()) - 1] = 1;
			}
		}
		return As;
	}

	// Method to convert Student History information into the array
	public Integer[][] ConvertToStudentHistoryMatrix() {
		Integer[][] Ash = new Integer[students.size()][cCatalog
				.getCourseCatalogSize()];
		for (int i = 0; i < students.size(); i++) {
			for (int j = 0; j < Ash[i].length; j++) {
				Ash[i][j] = 0;
			}

			// Set the desired course to the student array
			Student stud = students.get(i);
			List<Course> completeCourses = stud.getCompletedCourses();
			for (int j = 0; j < completeCourses.size(); j++) {
				Course c = completeCourses.get(j);
				Ash[i][Integer.parseInt(c.getNum()) - 1] = 1;
			}
		}
		return Ash;
	}

	// Method to convert Professor information into the array
	public Integer[][] ConvertToProfessorMatrix() {
		Integer[][] p = new Integer[professors.size()][cCatalog
				.getCourseCatalogSize()];
		for (int i = 0; i < professors.size(); i++) {
			for (int j = 0; j < p[i].length; j++) {
				p[i][j] = 0;
			}

			// Set the desired course to the professor array
			Professor prof = professors.get(i);
			List<Course> comp = prof.getCompetencies();
			for (int j = 0; j < comp.size(); j++) {
				Course c = comp.get(j);
				p[i][Integer.parseInt(c.getNum()) - 1] = 1;
			}
		}
		return p;
	}

	// Method to convert TA information into the array
	public Integer[][] ConvertToTAMatrix() {
		Integer[][] t = new Integer[teacherAssistants.size()][cCatalog.getCourseCatalogSize()];
		for (int i = 0; i < teacherAssistants.size(); i++) {
			for (int j = 0; j < t[i].length; j++) {
				t[i][j] = 0;
			}

			// Set the desired course to the professor array
			TeacherAssistant ta = teacherAssistants.get(i);
			List<Course> comp = ta.getCompetencies();
			for (int j = 0; j < comp.size(); j++) {
				Course c = comp.get(j);
				t[i][Integer.parseInt(c.getNum()) - 1] = 1;
			}
		}
		return t;
	}

	//Calculate the optimal schedule for student
	public void Calculate() {
		
		GRBEnv env;
		try {
			env = new GRBEnv("mip1.log");
			GRBModel model = new GRBModel(env);
			
			int nStudents = aStudent.length;
			int nProfessors = aProfessor.length;
			int nTAs = aTA.length;
			int nCourses = cCatalog.getCourseCatalogSize();
			
			// Hard code Constraints
			int maxProfessorCourses = 2; 				// change to 1 or 2
            int maxTACourses = 2; 						// change to 1 or 2
			
			GRBVar x = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.INTEGER, "X");
			
            // Create Student History variables for [student][course]
            GRBVar[][] studentHistoryVar = new GRBVar[nStudents][nCourses];
            for (int i = 0; i < nStudents; i++) {
                for (int j = 0; j < nCourses; j++) {
                    studentHistoryVar[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "sh_" + (i + 1) + "_" + (j + 1));
                }
            }
			
			// Create Student Variables for [student][course][semester]			
			GRBVar[][] studentVar = new GRBVar[nStudents][nCourses];
			for (int i = 0; i < nStudents; i++) {
				for (int j = 0; j < nCourses; j++) {
					studentVar[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "s_" + (i + 1) + "_" + (j + 1));
				}
			}
			
            // Create Professor Variables for [professor][course][semester]
            GRBVar[][] professorVar = new GRBVar[nProfessors][nCourses];
            for (int i = 0; i < nProfessors; i++) {
                for (int j = 0; j < nCourses; j++) {
                    professorVar[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "p_" + (i + 1) + "_" + (j + 1));
                }
            }

            // Create TA Variables for [ta][course][semester]
            GRBVar[][] taVariables = new GRBVar[nTAs][nCourses];
            for (int i = 0; i < nTAs; i++) {
                for (int j = 0; j < nCourses; j++) {
                	taVariables[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "t_" + (i + 1) + "_" + (j + 1));
                }
            }

			// Integrate new variables
			model.update();
						
			// *** Course Constraints ***
						
			// Capacity Limit Constraint
			for (int j = 0; j < nCourses; j++) {
				GRBLinExpr expr = new GRBLinExpr();
				expr.addTerm(-1.0, x);
				for (int i = 0; i < nStudents; i++) {
					expr.addTerm(1.0, studentVar[i][j]);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, 0.0, "CapLimit_course" + (j + 1));
			}
			
            // Student number of courses for next semester Constraint
            for (int i = 0; i < nStudents; i++) {
            	Student stud = students.get(i);
            	int nMax = Integer.parseInt(stud.getNumCourses());
				GRBLinExpr expr = new GRBLinExpr();
				for (int j = 0; j < nCourses; j++) {
					expr.addTerm(1.0, studentVar[i][j]);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, nMax, "Max_courses_For_student" + (i + 1));
            }

            // Student can take course once Constraint
            for (int i = 0; i < nStudents; i++) {
                for (int j = 0; j < nCourses; j++) {
                    GRBLinExpr expr = new GRBLinExpr();
                    expr.addTerm(1.0, studentHistoryVar[i][j]);
                    expr.addTerm(1.0, studentVar[i][j]);
                    model.addConstr(expr, GRB.LESS_EQUAL, 1.0, "Taken_once_student" + (i + 1) + "_course" + (j + 1));
                }
            }
            
            // Course needs 1 student Constraint
            for (int j = 0; j < nCourses; j++) {
                GRBLinExpr expr = new GRBLinExpr();
				for (int i = 0; i < nStudents; i++) {
					expr.addTerm(1.0, studentVar[i][j]);
				}
				model.addConstr(expr, GRB.GREATER_EQUAL, 1.0, "NeedOneStudent_course_" + (j + 1));
            }
            
            // Student Specialization Constraint - May decide to skip
            
            // *** Faculty Constraint ***
            
            // Max courses per professor and TA Constraint
			for (int i = 0; i < nProfessors; i++) {
				GRBLinExpr professorExpr = new GRBLinExpr();
				for (int j = 0; j < nCourses; j++) {
					professorExpr.addTerm(1.0, professorVar[i][j]);
				}
				model.addConstr(professorExpr, GRB.LESS_EQUAL, maxProfessorCourses, "One_professor" + (i + 1));
			}
			for (int i = 0; i < nTAs; i++) {
				GRBLinExpr taExpr = new GRBLinExpr();
				for (int j = 0; j < nCourses; j++) {
					taExpr.addTerm(1.0, taVariables[i][j]);
				}
				model.addConstr(taExpr, GRB.LESS_EQUAL, maxTACourses, "One_ta" + (i + 1));
			}
            
            // Each course has 1 Professor Constraint
			for (int j = 0; j < nCourses; j++) {
				int maxCourseCapacity = Integer.parseInt(cCatalog.getCourse(j).getLimit());
				GRBLinExpr expr = new GRBLinExpr();
				for (int i = 0; i < nProfessors; i++) {
					expr.addTerm(maxCourseCapacity, professorVar[i][j]);
				}

				for (int i = 0; i < nStudents; i++) {
					expr.addTerm(-1.0, studentVar[i][j]);
				}
                model.addConstr(expr, GRB.LESS_EQUAL, maxCourseCapacity - 1, "OneProfessorIfOffering_Course_" + (j + 1) + "_atMost");
                model.addConstr(expr, GRB.GREATER_EQUAL, 0, "OneProfessorIfOffering_course_" + (j + 1) + "atLeast");
			}

            // Each course has 1 TA Constraint
			for (int j = 0; j < nCourses; j++) {
				int maxCourseCapacity = Integer.parseInt(cCatalog.getCourse(j).getLimit());
				GRBLinExpr expr = new GRBLinExpr();
				for (int i = 0; i < nTAs; i++) {
					expr.addTerm(maxCourseCapacity, taVariables[i][j]);
				}

				for (int i = 0; i < nStudents; i++) {
					expr.addTerm(-1.0, studentVar[i][j]);
				}
                model.addConstr(expr, GRB.LESS_EQUAL, maxCourseCapacity - 1, "OneTAIfOffering_Course_" + (j + 1) + "_atMost");
                model.addConstr(expr, GRB.GREATER_EQUAL, 0, "OneTAIfOffering_course_" + (j + 1) + "atLeast");
			}
            
            // Professor Course Competences Constraint - All non-competent course are not assigned
			for (int i = 0; i < nProfessors; i++) {
				GRBLinExpr expr = new GRBLinExpr();
				for (int j = 0; j < nCourses; j++) {
					if (aProfessor[i][j] == 0) {
						expr.addTerm(1.0, professorVar[i][j]);
					}
				}
				model.addConstr(expr, GRB.EQUAL, 0, "professor" + (i + 1) + "_Competencies");
			}
            
            // TA Course Competences Constraint - All non-competent course are not assigned
			for (int i = 0; i < nTAs; i++) {
				GRBLinExpr expr = new GRBLinExpr();
				for (int j = 0; j < nCourses; j++) {
					if (aTA[i][j] == 0) {
						expr.addTerm(1.0, taVariables[i][j]);
					}
					model.addConstr(expr, GRB.EQUAL, 0, "ta" + (i + 1) + "_Competencies");
				}
			}
            
			// Set the objective as the sum of all student-courses
			GRBLinExpr obj = new GRBLinExpr();
			for (int i = 0; i < nStudents; i++) {
				for (int j = 0; j < nCourses; j++) {
					obj.addTerm(1.0, studentVar[i][j]);
				}
			}
			
			//model.setObjective(obj, GRB.MINIMIZE);
			model.setObjective(obj, GRB.MAXIMIZE);

			// Optimize the model
			model.optimize();
			
            // *** Solution ***
			
			// Model is infeasible
            int status = model.get(GRB.IntAttr.Status);
            if (status == GRB.Status.INFEASIBLE) {
            	System.out.printf("Model is infeasible");
                model.computeIIS();
                // Print names of constraints in IIS
                for (GRBConstr c : model.getConstrs()) {
                    if (c.get(GRB.IntAttr.IISConstr) > 0) {
                    	System.out.printf(c.get(GRB.StringAttr.ConstrName));
                    }
                }

                // Print names of variables in IIS
                for (GRBVar v : model.getVars()) {
                    if (v.get(GRB.IntAttr.IISLB) > 0 || v.get(GRB.IntAttr.IISUB) > 0) {
                    	System.out.printf(v.get(GRB.StringAttr.VarName));
                    }
                }
            }
            
            List<Recommendation> recommendations  = new ArrayList<Recommendation>();
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
                    for (int i = 0; i < nTAs; i++) {
                        if (taVariables[i][j].get(DoubleAttr.X) == 1) {
                        	TeacherAssistant ta = teacherAssistants.get(i); 
                            tas.add(ta);
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
	}
}
