package common;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*
import org.apache.tomcat.dbcp.dbcp2.ConnectionFactory;
import org.apache.tomcat.dbcp.dbcp2.DriverManagerConnectionFactory;
import org.apache.tomcat.dbcp.dbcp2.PoolableConnection;
import org.apache.tomcat.dbcp.dbcp2.PoolableConnectionFactory;
import org.apache.tomcat.dbcp.dbcp2.PoolingDriver;
import org.apache.tomcat.dbcp.pool2.impl.GenericObjectPool;
import org.apache.tomcat.dbcp.pool2.impl.GenericObjectPoolConfig;
*/


public class EnvListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		String configFile = context.getInitParameter("mapperEnv");
		String configFilePath = context.getRealPath(configFile);
		Properties prop = new Properties();
		System.out.println(configFilePath);
		
		try (FileReader fis = new FileReader(configFilePath)) {
			prop.load(fis);
		} catch (IOException e) {
			throw new RuntimeException("config load fail", e);
		}
		
		Iterator<Object> keyIter = prop.keySet().iterator();
		while (keyIter.hasNext()) {
			String key = (String) keyIter.next();
			String val = prop.getProperty(key);
			System.out.printf("ENV: %s=%s\n", key, val);
			if (key.substring(0, 1).equals("#"))
				continue;
			
			try {
				Env.put(key, val);
			} catch (Exception e) {}
		}
		
		String uploadPathAbsolute = (String) Env.get("uploadPathAbsolute");
		String uploadPath = (String) Env.get("uploadPath");
		if (! uploadPathAbsolute.equals("true")) {
			Env.put("uploadPath", context.getRealPath(uploadPath));
			System.out.printf("ENV: %s=%s\n", "uploadPath", (String)Env.get("uploadPath"));
		}
	}

	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	
	
	}

}
