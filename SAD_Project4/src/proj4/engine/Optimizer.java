package proj4.engine;

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
	
	int nMax = 0;
	int maxCapacity = 0;
	
	public Optimizer() {
		
	}
	
	public Optimizer(Integer[][] s, Integer[][] sh, Integer[][] p, Integer[][] t, Integer[][] c, Integer[][] pc) {
		// Fill up the Student Matrix
		aStudent = s;
		// Fill up the Student History Matrix
		aStudentHistory = sh;
		// Fill up the Professor Matrix
		aProfessor = p;
		// Fill up the TA Matrix
		aTA = t;
		// Fill up the Course Matrix
		aCourse = c;
		// Fill up the Prerequisite Matrix
		aPrereq = pc;
	}
	
	public void Calculate() {
		
		GRBEnv env;
		try {
			env = new GRBEnv("mip1.log");
			GRBModel model = new GRBModel(env);
			
			int maxCapacity;
			int nStudents = aStudent.length;			//600
			int nProfessors = aProfessor.length;
			int nTAs = aTA.length;
			int nCourses = aCourse.length;				//18
			
			maxCapacity = getMaxCapacity();
			
			// Hardcoded Constraints
			int nSemesters = 12;
			int minCourseEnrollment = 1;
			int maxProfessorCourses = 2; 				// change to 1 or 2
            int maxTACourses = 2; 						// change to 1 or 2
            double nVariables = nStudents * nCourses * nStudents;
			
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
			GRBVar[][][] studentVar = new GRBVar[nStudents][nCourses][nSemesters];
			for (int i = 0; i < nStudents; i++) {
				for (int j = 0; j < nCourses; j++) {
					for (int k = 0; k < nSemesters; k++) {
						String st = "s_" + String.valueOf(i) + String.valueOf(j) + String.valueOf(k);
						studentVar[i][j][k] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, st);
					}
				}
			}
			
            // Create Professor Variables for [professor][course][semester]
            GRBVar[][][] professorVar = new GRBVar[nProfessors][nCourses][nSemesters];
            for (int i = 0; i < nProfessors; i++) {
                for (int j = 0; j < nCourses; j++) {
                    for (int k = 0; k < nSemesters; k++) {
                        professorVar[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, "p_" + (i + 1) + "_" + (j + 1) + "_" + (k + 1));
                    }
                }
            }

            // Create TA Variables for [ta][course][semester]
            GRBVar[][][] taVariables = new GRBVar[nTAs][nCourses][nSemesters];
            for (int i = 0; i < nTAs; i++) {
                for (int j = 0; j < nCourses; j++) {
                    for (int k = 0; k < nSemesters; k++) {
                        taVariables[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, "t_" + (i + 1) + "_" + (j + 1) + "_" + (k + 1));
                    }
                }
            }

			// Integrate new variables
			model.update();
						
			// *** Course Constraints ***
			
			// Max Number of Courses Constraint
			/*for (int i = 0; i < nStudents; i++) {
				for (int k = 0; k < nSemesters; k++) {
					GRBLinExpr expr = new GRBLinExpr();
					for (int j = 0; j < nCourses; j++) {
						expr.addTerm(1.0, y[i][j][k]);
					}
					String st = "MaxCourses_student" + (i + 1) + "_semester" + (k + 1);
					model.addConstr(expr, GRB.LESS_EQUAL, MAX_COURSE_NUM_SIZE, st);
				}
			}*/
			
			// Must Take Constraint
			for (int i = 0; i < nStudents; i++) {
				for (int j = 0; j < nCourses; j++) {
					GRBLinExpr expr = new GRBLinExpr();
					for (int k = 0; k < nSemesters; k++) {
						expr.addTerm(1.0, studentVar[i][j][k]);
					}
					String st = "MustTake_student" + String.valueOf(i) + "_course" + String.valueOf(j);
					model.addConstr(expr, GRB.EQUAL, aStudent[i][j], st );
				}
			}
			
			// Capacity Limit Constraint
			for (int k = 0; k < nSemesters; k++) {
				for (int j = 0; j < nCourses; j++) {
					GRBLinExpr expr = new GRBLinExpr();
					expr.addTerm(-1.0, x);
					for (int i = 0; i < nStudents; i++) {
						expr.addTerm(1.0, studentVar[i][j][k]);
					}
					String st = "CapLimit_course" + (j + 1) + "_semester" + (k + 1);
					model.addConstr(expr, GRB.LESS_EQUAL, 0.0, st);
				}
			}
			
			// Prerequisite Constraint
			for (int j1 = 0; j1 < nCourses; j1++) {
				//get current course
				Integer[] course = aPrereq[j1];
				for (int j0 = 0; j0 < course.length; j0++) {
					// get prerequisite information
					int prereq = course[j0];
					//don't add if there is no prerequisite for this course
					if (prereq != 0) {
						for (int i = 0; i < nStudents; i++) {
							for (int k = 1; k < nSemesters; k++) {
								//create left and right expressions
								GRBLinExpr leftExpr = new GRBLinExpr();
								GRBLinExpr rightExpr = new GRBLinExpr();
								for (int m = 0; m <= k; m++) {
									leftExpr.addTerm(0.0, studentVar[i][j1][m]);
									rightExpr.addTerm(0.0, studentVar[i][j0][m]);
								}
								String st = "PrereqCourses_student" + (i + 1) + "_semester" + (k + 1) + "_course"
										+ (j0 + 1) + "_before_course" + (j1 + 1);
								model.addConstr(leftExpr, GRB.LESS_EQUAL, rightExpr, st);
							}
						}
					}
				}
			}
			
			// Course Availability Constraint
			for (int j = 0; j < nCourses; j++) {
				for (int k = 0; k < nSemesters;k++) {
					if (aCourse[j][k / 4] == 0) {				// If course is not offered during semester
						GRBLinExpr expr = new GRBLinExpr();
						for (int i = 0; i < nStudents; i++) {
							expr.addTerm(1.0, studentVar[i][j][k]);
						}
						String st = "Availability_course" + (j + 1) + "_semester" + (k + 1);
						model.addConstr(expr, GRB.EQUAL, 0.0, st );
					}
				}
			}
			
            // Add Student History Constraint
            for (int i = 0; i < nStudents; i++) {
                GRBLinExpr notTaken = new GRBLinExpr();
                GRBLinExpr taken = new GRBLinExpr();
                int takenCount = 0;
                for (int j = 0; j < nCourses; j++) {
                     int takenCourse = aStudentHistory[i][j];
                     // Course not taken
                    if (takenCourse == 0) {	
                        notTaken.addTerm(1, studentHistoryVar[i][j]);
                    } else {
                        taken.addTerm(1, studentHistoryVar[i][j]);
                        takenCount++;
                    }
                }
                model.addConstr(notTaken, GRB.EQUAL, 0, "Courses_not_taken_student" + (i));
                model.addConstr(taken, GRB.EQUAL, takenCount, "Courses_taken_student" + (i));
            }
            
            // nMax courses per semester for student Constraint
            int nMax = getNMax();
            for (int i = 0; i < nStudents; i++) {
                for (int k = 0; k < nSemesters; k++) {
                    GRBLinExpr expr = new GRBLinExpr();
                    for (int j = 0; j < nCourses; j++) {
                        expr.addTerm(1, studentVar[i][j][k]);
                    }
                    model.addConstr(expr, GRB.LESS_EQUAL, nMax, "Max_courses_For_student" + (i + 1) + "_semester" + (k + 1));
                }
            }

            // Student take course once Constraint
            for (int i = 0; i < nStudents; i++) {
                for (int j = 0; j < nCourses; j++) {
                    GRBLinExpr expr = new GRBLinExpr();
                    expr.addTerm(1, studentHistoryVar[i][j]);
                    for (int k = 0; k < nSemesters; k++) {
                        expr.addTerm(1, studentVar[i][j][k]);
                    }
                    model.addConstr(expr, GRB.LESS_EQUAL, 1, "Taken_once_student" + (i + 1) + "_course" + (j + 1));
                }
            }
            
            // Course needs 1 student Constraint
            
            // Student Specialization Constraint 
            
            // *** Faculty Constraint ***
            
            // 1 Professor per Course Constraint
            for (int k = 0; k < nSemesters; k++) {
                for (int j = 0; j < nCourses; j++) {
                    GRBLinExpr expr = new GRBLinExpr();
                    for (int i = 0; i < nProfessors; i++) {
                        expr.addTerm(maxCapacity, professorVar[i][j][k]);
                    }

                    for (int i = 0; i < nStudents; i++) {
                        expr.addTerm(-1, studentVar[i][j][k]);
                    }
                    model.addConstr(expr, GRB.LESS_EQUAL, maxCapacity - 1, "Max_one_prof_course_" + (j + 1));
                    model.addConstr(expr, GRB.GREATER_EQUAL, 0, "One_prof_offered_course_" + (j + 1));
                }
            }

            // 1 TA per Course Constraint
            for (int k = 0; k < nSemesters; k++) {
                for (int j = 0; j < nCourses; j++) {
                    GRBLinExpr expr = new GRBLinExpr();
                    for (int i = 0; i < nTAs; i++) {
                        expr.addTerm(maxCapacity, taVariables[i][j][k]);
                    }

                    for (int i = 0; i < nStudents; i++) {
                        expr.addTerm(-1, studentVar[i][j][k]);
                    }
                    model.addConstr(expr, GRB.LESS_EQUAL, maxCapacity - 1, "Max_one_ta_course_" + (j + 1));
                    model.addConstr(expr, GRB.GREATER_EQUAL, 0, "One_ta_offered_course_" + (j + 1));
                }
            }
            
            // 1 course per Professor and TA Constraint
            for (int k = 0; k < nSemesters; k++) {
                for (int i = 0; i < nProfessors; i++) {
                    GRBLinExpr professorExpr = new GRBLinExpr();
                    for (int j = 0; j < nCourses; j++) {
                        professorExpr.addTerm(1, professorVar[i][j][k]);
                    }
                    model.addConstr(professorExpr, GRB.LESS_EQUAL, maxProfessorCourses, "One_professor" + (i + 1) + "_semester" + (k + 1));
                }
                for (int i = 0; i < nTAs; i++) {
                    GRBLinExpr taExpr = new GRBLinExpr();
                    for (int j = 0; j < nCourses; j++) {
                        taExpr.addTerm(1, taVariables[i][j][k]);
                    }
                    model.addConstr(taExpr, GRB.LESS_EQUAL, maxTACourses, "One_ta" + (i + 1) + "_semester" + (k + 1));
                }
            }
            
            // Professor Course Competences Constraint
            for (int i = 0; i < nProfessors; i++) {
                if ( aProfessor.length > 0 && aProfessor[i].length > 0 ) {
                    for (int k = 0; k < nSemesters; k++) {
                        GRBLinExpr expr = new GRBLinExpr();
                        for (int j = 0; j < nCourses; j++) {
                            // The constraint will be that all non-competency course are not assigned:
                            if (aProfessor[i][j] == 0) {
                                expr.addTerm(1, professorVar[i][j][k]);
                            }
                        }
                        model.addConstr(expr, GRB.EQUAL, 0, "professor" + (i + 1) + "_CompetenciesForSemester" + (k + 1));
                    }
                }
            }
            
            // TA Course Competences Constraint
            for (int i = 0; i < nTAs; i++) {
                if (aTA.length > 0 && aTA[i].length > 0) {
                    for (int k = 0; k < nSemesters; k++) {
                        GRBLinExpr expr = new GRBLinExpr();
                        for (int j = 0; j < nCourses; j++) {
                            // The constraint will be that all non-competency course are not assigned:
                        	if (aTA[i][j] == 0) {
                                expr.addTerm(1, taVariables[i][j][k]);
                            }
                        }
                        model.addConstr(expr, GRB.EQUAL, 0, "ta" + (i + 1) + "_CompetenciesForSemester" + (k + 1));
                    }
                }
            }
            
			// Set the objective as the sum of all student-courses
			GRBLinExpr obj = new GRBLinExpr();
			obj.addTerm(1.0, x);
			/*for (int i = 0; i < nStudents; i++) {
				for (int j = 0; j < nCourses; j++) {
					for (int k = 0; k < nSemesters; k++) {
						obj.addTerm(1.0, y[i][j][k]);
					}
				}
			}*/
			
			model.setObjective(obj, GRB.MINIMIZE);

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
	
	public int getNMax()
	{
		return nMax;
	}
	
	public void setNMax(int n)
	{
		nMax = n;
	}
	
	public int getMaxCapacity()
	{
		return maxCapacity;
	}
	
	public void setMaxCapacity(int n)
	{
		maxCapacity = n;
	}
}
