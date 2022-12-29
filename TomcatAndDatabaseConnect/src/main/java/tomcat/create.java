package tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

@WebServlet("/create")
public class create extends HttpServlet {	
	
	
	

	//private static final Logger LOGGER = Logger.getLogger(create.class);
Logger LOGGER= Logger.getLogger(create.class);
	static String url= "jdbc:mysql://localhost:3306/manageemployees"; 
	static String username = "root"; // MySQL credentials
	static String password = "poojiRoy2000!"; 

    /**
     * @see HttpServlet#HttpServlet()
     */
    public create() {
    	
    
    }
    
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	  protected void doGet(HttpServletRequest request, HttpServletResponse
	  response) throws ServletException, IOException { // TODO Auto-generated
	 
	  response.getWriter().append("Served at: ").append(request.getContextPath());
	  }

	 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn=null;
		 PreparedStatement p =null;
		 
		
		String contentType = request.getContentType();
		if(contentType.equals("application/json")) {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			StringBuffer jb = new StringBuffer();
			  String line = null;
			  try {
			    BufferedReader reader = request.getReader(); 
			    while ((line = reader.readLine()) != null)
			      jb.append(line);
			  } catch (Exception e) { 
				  System.out.println(e.getMessage());
			  }

			  try {
				JSONObject jsn=new JSONObject(jb.toString());
			    int empid=jsn.getInt(VaribleConst.EMPID);
			  //  ((Category) createLog).info(empid);
			   // createLogger.info("User Entered EmpId"+empid);
				String department=jsn.getString(VaribleConst.DEPARTMENT);
				int salary=jsn.getInt(VaribleConst.SALARY);
				//String value=jsn.getString("emptype");
				String emptype=jsn.getString(VaribleConst.EMPTYPE);
				
				Class.forName("com.mysql.cj.jdbc.Driver");//.newInstance(); // Driver name
				 conn = DriverManager.getConnection(url, username, password);
				    
	
				
				String sql=ApiQueryConst.QUERY;
			    p = conn.prepareStatement(sql);
			    p.setInt(1, empid);
			    LOGGER.info("Employee Id"+empid);
			    p.setString(2, department);
			    LOGGER.info("Employee Id"+department);
			    p.setDouble(3, salary);
			    LOGGER.info("Employee Id"+salary);
			    p.setString(4, emptype);
			    LOGGER.info("Employee Id"+emptype);
			    p.executeUpdate();
			 
			   // out.print("{\"Message\":\"Successfully Employee  Details Created In Database. \"}");
			   // out.print("{ \"Message\": Successfully "+empid+" Employee Details Created in Database.}");
			  String result= String.format("{\"Message\":Successfully Details Created with Id %d}", empid);
				out.print(result);
				
				
			  } catch (SQLException e) {
			    // crash and burn
				LOGGER.debug("dubugged");
			    //throw new IOException("Error parsing JSON request string");
			   
			  } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				  LOGGER.debug("dubugged");
				
			}
			  finally {
				  
					try {
						conn.close();
						p.close();
					} catch (SQLException e) {
						
					}
					
				
			  }
			
		}
	}

}
