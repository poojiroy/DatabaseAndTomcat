package tomcat;

public class ApiQueryConst {
public static final String QUERY="INSERT INTO employee( empid, department, salary,emptype,timing) VALUES(?,?,?,?,now())";
public static final String DELETEQUERY="delete  from employee where empid=?";
public static final String DISPLAYQUERY="Select empid,salary,department from employee";
public static final String MODIFYQUERY="update employee set department=?,salary=?,emptype=? where empid=?";;
public static final String SEARCHQUERY="select * from employee where empid=?";
}
