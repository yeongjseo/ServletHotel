package handler;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BaseHandler {
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception;
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception;
	
	
}
