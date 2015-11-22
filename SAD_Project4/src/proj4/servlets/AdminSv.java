package proj4.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.codec.binary.StringUtils;
import proj4.serverapp.AdminEntry;
import proj4.serverapp.ServerApplication;

/**
* Servlet implementation class Admin
*/
@WebServlet("/Admin")
public class AdminSv extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static AdminEntry ae;
	
	/**
	* @see HttpServlet#HttpServlet()
	*/
	public AdminSv() {
		super();
	// TODO Auto-generated constructor stub
	}
	
	/**
	* @see Servlet#init(ServletConfig)
	*/
	public void init(ServletConfig config) throws ServletException {
		
	}
	
	/**
	* GET requests redirect to the homepage
	*
	* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}
	
	/**
	* @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		// Get session and check that it is valid and this person is an admin
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		
		if( session == null || ((String)session.getAttribute("isadmin")).compareTo("1") != 0) 
		{
			response.sendRedirect("index.jsp");
		}
		
		if( ae == null )
		{
			ae = (AdminEntry)((ServerApplication)session.getAttribute("srvapp")).getAdminEntry();
		}
		
		// Set response content type
	    response.setContentType("text/html");

	    // Determine which form was submitted and call appropriate methods
	    out.println("Function:  " + request.getParameter("function"));
	    String f = request.getParameter("function");
	    if( f.compareTo("setEnrollLims") == 0) {
	    	String course = request.getParameter("crsNum");
	    	String limit = request.getParameter("crslim");
	    	String shadow = request.getParameter("modelModeType");
			String error = ae.setEnrollLimit(Integer.parseInt(limit), course, shadow.equals("shadow"));
			if(error == null) response.sendRedirect("admin.jsp");
			else{
				
			} //error

	    } else if( f.compareTo("setCourse") == 0) {
	    	
	    	String course = request.getParameter("crsname");
	    	String mode = request.getParameter("courseSetType");
	    	String shadow = request.getParameter("modelModeType");
	    	
	    	String semester = request.getParameter("semester");
			String error = null;
			if(mode.equals("add")) error =  ae.addCourse(course, shadow.equals("shadow"), semester);
			else if (mode.equals("remove")) error = ae.removeCourse(course, shadow.equals("shadow"));
			else error = ae.editCourse(course, shadow.equals("shadow"), semester );
			if(error == null)
				response.sendRedirect("admin.jsp");
			else
			{
				
			
			}

	    } else if( f.compareTo("setStaffAsts")== 0){
	    	
//			<form method="POST" action="Admin">
//		<input type="hidden" name="function" value="setStaffAsts" /> 
//			Staff ID:<input name="staffname" /> <br>
//			Available:<select name="available"><option value="0">No</option><option value="1">Yes</option></select><br>
//			Competencies:<input name="competencies" /> <br>
//			Model Mode:<br>
//        <input type="radio" name="modelModeType" value="standard" checked> Standard
//		<input type="radio" name="modelModeType" value="shadow"> Shadow Mode
//		<br><br>
//		<input type="submit" value="Make Change" />
//	</form>
	
	    	String staffid = request.getParameter("staffname");
	    	int available = Integer.parseInt(((String)request.getParameter("available")));
	    	String competencies= request.getParameter("competencies");
	    	String shadow = request.getParameter("modelModeType");
	    	
	    	char role = staffid.charAt(0);
	    	if( role == 't'){
	    		ae.updateTAAvailable(staffid, available);
	    		ae.updateTACompetencies(staffid, competencies);
	    	} else if (role == 'p'){
	    		ae.updateProfAvailable(staffid, available);
	    		ae.updateProfCompetencies(staffid, competencies);
	    	}
	    	response.sendRedirect("admin.jsp");

	    } else if(f.compareTo("dispRecsPrefs") == 0){
			String student = request.getParameter("stuname");
			String dispType = request.getParameter("dispType");
			String error = ae.getStudentInfo(student, dispType.equals("prefs") );
			if (error == null) response.sendRedirect("admin.jsp");
			else{
				// err
			}

	    }
	    
	    
	    else {
	    	response.sendRedirect("logout.jsp");
	    }

	    //out.println("<a href=\"admin.jsp\">Back</a>");
	    
	    out.println("<script>setTimeout(function () {window.location.href = 'admin.jsp'; }, 2000);</script>");
	}
}