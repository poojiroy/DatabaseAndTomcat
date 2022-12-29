package listener;
import org.apache.log4j.*;
import java.lang.*;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;
//@WebListener("application context listener")
public class LogExp implements ServletContextListener {
	//private static final Logger LOGGER = Logger.getLogger(create.class);
    //private static  LogExp log= LogExp.getLogger(LogExp.class);
  
	Logger log= Logger.getLogger(LogExp.class);
	@Override
    public void contextDestroyed(ServletContextEvent event) {
        // do nothing
    }  
	
	@Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
         String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
         PropertyConfigurator.configure(fullPath);
         //System.out.println("log4j initiated in loggerclass");
        // System.out.println("started");
         log.info("getPrinted");
    }



    }