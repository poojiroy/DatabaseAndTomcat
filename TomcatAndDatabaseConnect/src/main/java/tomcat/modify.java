package tomcat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.Statement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.json.HTTP;
//import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class create
 */
@WebServlet("/modify")
public class modify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String url= "jdbc:mysql://localhost:3306/manageemployees"; 
	static String username = "root"; // MySQL credentials
	static String password = "poojiRoy2000!"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public modify() {
        super();
        // TODO Auto-generated constructor stub
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
		  PreparedStatement p=null;
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
				String department=jsn.getString(VaribleConst.DEPARTMENT);
				int salary=jsn.getInt(VaribleConst.SALARY);
				//String value=jsn.getString("emptype");
				String emptype=jsn.getString(VaribleConst.EMPTYPE);
				
				Class.forName("com.mysql.cj.jdbc.Driver");//.newInstance(); // Driver name
				 conn = DriverManager.getConnection(url, username, password);
				    
	
				
				String sql= ApiQueryConst.MODIFYQUERY;
			     p = conn.prepareStatement(sql);
			    p.setString(1, department);
			    p.setInt(2, salary);
			    p.setString(3, emptype);
			   p.setInt(4, empid);
			    p.executeUpdate();
			    
			   // out.print("{\"Result\":\"success\"}");
			    //out.print("{ \"Message\": Successfully "+empid+" Employee Details Modified in Database.}");
			    String result= String.format("{\"Message\":Successfully Details Modified with Id %d}", empid);
				out.print(result);

				
			  } catch (Exception e) {
			    // crash and burn
				System.out.println(e.getMessage());
			    //throw new IOException("Error parsing JSON request string");
			   
			  }finally {
				    try {
						conn.close();
						 p.close(); 
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			  }
			
		}
	}

}