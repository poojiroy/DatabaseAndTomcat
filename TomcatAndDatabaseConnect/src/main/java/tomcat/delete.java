package tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class delete
 */
@WebServlet("/delete")
public class delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//Logger LOGGER = Loger.getLoger(delete)
	static String url= "jdbc:mysql://localhost:3306/manageemployees";
	static String username = "root"; // MySQL credentials
	static String password = "poojiRoy2000!";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn=null;
		PreparedStatement p1=null;


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
		JSONObject jsonObject=new JSONObject(jb.toString());
		int empid=jsonObject.getInt(VaribleConst.EMPID);

		Class.forName("com.mysql.cj.jdbc.Driver");//.newInstance(); // Driver name
		 conn = DriverManager.getConnection(url, username, password);


String query=ApiQueryConst.DELETEQUERY;

 p1=conn.prepareStatement(query);
		p1.setInt(1, empid);
		p1.executeUpdate();
		//out.print("{\"Message\":\"Successfully Employee Details Deleted In Database.\"}");
		 //out.print("{ \"Message\": Successfully "+empid+" Employee Details Deleted in Database.}");
		  String result= String.format("{\"Message\":Successfully Details Deleted with Id %d}", empid);
			out.print(result);
		 

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {
				conn.close();
				 p1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		}
	}

}

