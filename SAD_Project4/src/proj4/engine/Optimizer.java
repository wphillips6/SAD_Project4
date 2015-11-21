package proj4.engine;

import java.util.ArrayList;
import java.util.List;

import proj4.common.Course;
import proj4.common.CourseCatalog;
import proj4.common.Professor;
import proj4.common.Student;
import proj4.common.TeacherAssistant;
import proj4.common.Semester.SemesterTerm;
import gurobi.GRB;
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
	// The Course Matrix
	Integer[][] aCourse;
	// The Prerequisite Matrix
	Integer[][] aPrereq;
	
	private CourseCatalog cCatalog = new CourseCatalog();
    private List<Student> students = new ArrayList<Student>();
    private List<Professor> professors = new ArrayList<Professor>();
    private List<TeacherAssistant> tas = new ArrayList<TeacherAssistant>();

	public Optimizer() {
		
	}
	
	public Optimizer(CourseCatalog cc, List<Student> s, List<Professor> p, List<TeacherAssistant> t) {
		cCatalog = cc;
		students = s;
		professors = p;
		tas = t;

		// Fill up the Student Matrix
		aStudent = ConvertToStudentMatrix();
		// Fill up the Student History Matrix
		aStudentHistory = ConvertToStudentHistoryMatrix();
		// Fill up the Professor Matrix
		aProfessor = ConvertToProfessorMatrix();
		// Fill up the TA Matrix
		aTA = ConvertToTAMatrix();
		// Fill up the Course Matrix
		aCourse = ConvertToCourseMatrix();
		// Fill up the Prerequisite Matrix
		aPrereq = ConvertToPrereqMatrix();
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
			List<Course> desiredCourses = stud.getDesiredCourses();
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
		Integer[][] t = new Integer[tas.size()][cCatalog.getCourseCatalogSize()];
		for (int i = 0; i < tas.size(); i++) {
			for (int j = 0; j < t[i].length; j++) {
				t[i][j] = 0;
			}

			// Set the desired course to the professor array
			TeacherAssistant ta = tas.get(i);
			List<Course> comp = ta.getCompetencies();
			for (int j = 0; j < comp.size(); j++) {
				Course c = comp.get(j);
				t[i][Integer.parseInt(c.getNum()) - 1] = 1;
			}
		}
		return t;
	}

	// Method to convert course information into the array
	public Integer[][] ConvertToCourseMatrix() {
		Integer[][] Ac = new Integer[cCatalog.getCourseCatalogSize()][3];
		List<Course> availableCourses = cCatalog.getCourses();

		// Set the course information to the course array
		for (int i = 0; i < availableCourses.size(); i++) {
			Course c = availableCourses.get(i);
			if (c.getSemester().getTerm().equals(SemesterTerm.EVERY)) { // Every - 1,1,1
				for (int j = 0; j < 3; j++) {
					Ac[i][j] = 1;
				}
			} else if (c.getSemester().getTerm().equals(SemesterTerm.SPRING)) { // Spring - 1,0,0
				Ac[i][0] = 1;
				Ac[i][1] = 0;
				Ac[i][2] = 0;
			} else if (c.getSemester().getTerm().equals(SemesterTerm.FALL)) { // Fall - 0,0,1
				Ac[i][0] = 0;
				Ac[i][1] = 0;
				Ac[i][2] = 1;
			}
		}
		return Ac;
	}

	// Method to convert prerequisite information into the array
	public Integer[][] ConvertToPrereqMatrix() {
		Integer[][] Ap = new Integer[cCatalog.getCourseCatalogSize()][cCatalog
				.getCourseCatalogSize()];
		List<Course> availableCourses = cCatalog.getCourses();

		// Set the prerequisite course to the prerequisite array
		for (int i = 0; i < availableCourses.size(); i++) {
			for (int j = 0; j < Ap.length; j++) {
				Ap[i][j] = 0;
			}

			Course c = availableCourses.get(i);
			if (Integer.parseInt(c.getPrerequisite()) != -1) {
				Ap[i][Integer.parseInt(c.getPrerequisite()) - 1] = 1;
			}
		}
		return Ap;
	}

	//Calculate the optimal schedule for student
	public void Calculate() {
		
		GRBEnv env;
		try {
			env = new GRBEnv("mip1.log");
			GRBModel model = new GRBModel(env);
			
			int nStudents = aStudent.length;			//600
			int nProfessors = aProfessor.length;
			int nTAs = aTA.length;
			int nCourses = aCourse.length;				//18
			
			// Hard code Constraints
			int maxProfessorCourses = 2; 				// change to 1 or 2
            int maxTACourses = 2; 						// change to 1 or 2
			
			//double nVariables = nStudents * nCourses * nSemesters;
			//GRBVar x = model.addVar(0.0, nVariables, 0.0, GRB.INTEGER, "X");
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
            for (int j = 0; j < cCatalog.getCourseCatalogSize(); j++) {
                GRBLinExpr expr = new GRBLinExpr();
				for (int i = 0; i < nStudents; i++) {
					expr.addTerm(1.0, studentVar[i][j]);
				}
				model.addConstr(expr, GRB.GREATER_EQUAL, 1.0, "NeedOneStudent_course_" + (j));
            }
            
            // Student Specialization Constraint - May decide to skip
            
            // *** Faculty Constraint ***
            
            // 1 Professor per Course Constraint
			for (int j = 0; j < nCourses; j++) {
				GRBLinExpr expr = new GRBLinExpr();
				for (int i = 0; i < nProfessors; i++) {
					expr.addTerm(1.0, professorVar[i][j]);
				}

				for (int i = 0; i < nStudents; i++) {
					expr.addTerm(-1.0, studentVar[i][j]);
				}
				model.addConstr(expr, GRB.GREATER_EQUAL, 0, "One_prof_offered_course_" + (j + 1));
			}

            // 1 TA per Course Constraint
			for (int j = 0; j < nCourses; j++) {
				Course course = cCatalog.getCourse(j);
				int maxCapacity = Integer.parseInt(course.getLimit());
				GRBLinExpr expr = new GRBLinExpr();
				for (int i = 0; i < nTAs; i++) {
					expr.addTerm(maxCapacity, taVariables[i][j]);
				}

				for (int i = 0; i < nStudents; i++) {
					expr.addTerm(-1.0, studentVar[i][j]);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, maxCapacity - 1, "Max_one_ta_course_" + (j + 1));
				model.addConstr(expr, GRB.GREATER_EQUAL, 0, "One_ta_offered_course_" + (j + 1));
			}
                       
            // 1 course per Professor and TA Constraint
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
					taExpr.addTerm(1, taVariables[i][j]);
				}
				model.addConstr(taExpr, GRB.LESS_EQUAL, maxTACourses, "One_ta" + (i + 1));
			}
            
            // Professor Course Competences Constraint - All non-competent course are not assigned
            for (int i = 0; i < nProfessors; i++) {
                if ( aProfessor.length > 0 && aProfessor[i].length > 0 ) {
					GRBLinExpr expr = new GRBLinExpr();
					for (int j = 0; j < nCourses; j++) {
						if (aProfessor[i][j] == 0) {
							expr.addTerm(1.0, professorVar[i][j]);
						}
					}
					model.addConstr(expr, GRB.EQUAL, 0, "professor" + (i + 1) + "_Competencies");
                }
            }
            
            // TA Course Competences Constraint - All non-competent course are not assigned
            for (int i = 0; i < nTAs; i++) {
                if (aTA.length > 0 && aTA[i].length > 0) {
					GRBLinExpr expr = new GRBLinExpr();
					for (int j = 0; j < nCourses; j++) {
						if (aTA[i][j] == 0) {
							expr.addTerm(1.0, taVariables[i][j]);
						}
						model.addConstr(expr, GRB.EQUAL, 0, "ta" + (i + 1)	+ "_Competencies");
                    }
                }
            }
            
			// Set the objective as the sum of all student-courses
			GRBLinExpr obj = new GRBLinExpr();
			//obj.addTerm(1.0, x);
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

			// Display our results
			double objectiveValue = model.get(GRB.DoubleAttr.ObjVal);            
			System.out.printf( "Ojective value = %f\n", objectiveValue );
			
		} catch (GRBException e) {
			e.printStackTrace();
		}
	}
}
