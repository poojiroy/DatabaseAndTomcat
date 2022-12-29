package tomcat;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class display
 */
@WebServlet("/display")
public class display extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String url= "jdbc:mysql://localhost:3306/manageemployees";
	static String username = "root"; // MySQL credentials
	static String password = "poojiRoy2000!";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public display() {
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
		Statement st=null;

			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");



		try {

		Class.forName("com.mysql.cj.jdbc.Driver");//.newInstance(); // Driver name
		 conn = DriverManager.getConnection(url, username, password);

		 st = conn.createStatement();

		JSONObject jo = new JSONObject();
		JSONArray array = new JSONArray();
		String query=ApiQueryConst.DISPLAYQUERY;
		ResultSet rs=st.executeQuery(query);
		while(rs.next()) {
			JSONObject jb = new JSONObject();
			int empid=rs.getInt(VaribleConst.EMPID);
			int salary=rs.getInt(VaribleConst.SALARY);
			String department=rs.getString(VaribleConst.DEPARTMENT);


			jb.put("empid", empid);
			jb.put("salary", salary);
			jb.put("department", department);

			array.add(jb);


		}

		jo.put("Employee_Data", array);

		out.print(jo);


		}catch(Exception e) {
		System.out.println(e.getMessage());
		}finally {
			 try {
				conn.close();
				 st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


	}

}
