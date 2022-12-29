package tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.json.JSONArray;
import org.json.JSONObject;


public class search extends HttpServlet{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
    public search() {
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
	 

	@Override
	public  void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		Connection con=null;
		PreparedStatement pst=null;
		//int EmpId=Integer.parseInt(req.getParameter("EmpId"));
		PrintWriter out = res.getWriter();

		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
		    BufferedReader reader = req.getReader();
		    while ((line = reader.readLine()) != null)
		        jb.append(line);
		} catch (Exception e) { /*error*/ }
		String empId =jb.toString() ;
		JSONObject object = new JSONObject(empId);
		int val=object.getInt(VaribleConst.EMPID);


//		try {
//		    JSONObject jsonObject =  HTTP.toJSONObject(empId.toString());
//		  } catch (JSONException e) {
//		    // crash and burn
//		    throw new IOException("Error parsing JSON request string");
//		  }
		  //JSONObject json = new JSONObject(str.toString());
		/*
		 * JSONObject object =new JSONObject(empId); JSONObject
		 * idT=object.getJSONObject("empId"); System.out.println(idT);
		 */
		String url="jdbc:mysql://localhost:3306/manageemployees";
		String userName ="root";
		String password="poojiRoy2000!";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection(url, userName,password);
			System.out.println("connected");
			String query=ApiQueryConst.SEARCHQUERY;
			 pst = con.prepareStatement(query);
			pst.setInt(1, val);
			ResultSet rs=pst.executeQuery();
		//	JSONArray result = new JSONArray();

			while(rs.next()) {

			JSONObject obj = new JSONObject();
			  int id =rs.getInt(1);
			  String dept=rs.getString(2);
			  int pay=rs.getInt(3);
			  String type=rs.getString(4);
			  obj.put("EmpId", id);
			  obj.put("dept", dept );
			  obj.put("salary", pay);
			  obj.put("EmpType", type);

			  out.println(obj);

				/*
				 * String userData ="EmpId: "+
				 * rs.getInt(1)+" Department: "+rs.getString(2)+" Salary: "+rs.getInt(3)
				 * +" EmpType: "+rs.getString(4); JSONObject obj = new JSONObject(userData);
				 */

				//obj.put("data", userData);
				//out.println(obj);


				/*
				 * out.println("EmpId: "+rs.getInt(1));
				 * out.println("Department: "+rs.getString(2));
				 * out.println("Salary: "+rs.getInt(3));
				 * out.println("EmpType: "+rs.getString(4));
				 *
				 */
//				result.add(obj);
			}
			 //out.print("{ \"Message\": Successfully "+empId+" Employee Details Displayed from Database.}");
			 String Result= String.format("{\"Message\":Successfully Details Deleted with Id %d}", empId);
				out.print(Result);
			
		}catch(Exception e) {
			System.out.println(e);

		}finally {
			 try {
				con.close();
				 pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		//out.println("EmpId is: "+EmpId);

	}

}

