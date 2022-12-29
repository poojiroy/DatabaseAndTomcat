package tomcat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import listener.TestLog4jServlet;

public class Exp extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		 Logger LOGGER = Logger.getLogger(TestLog4jServlet.class);
		 LOGGER.info("reswult");
	}
	
}