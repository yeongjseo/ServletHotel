package handler;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.MessageDO;

public class HandlerDispatcher extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, BaseHandler> commandHandlerMap = new HashMap<>();

	Logger logger = LoggerFactory.getLogger(HandlerDispatcher.class);
	
	public void init() throws ServletException {
		
		String configFile = getInitParameter("mapperHandler");
		Properties prop = new Properties();
		String configFilePath = getServletContext().getRealPath(configFile);
		try (FileReader fis = new FileReader(configFilePath)) {
			prop.load(fis);
		} catch (IOException e) {
			throw new ServletException(e);
		}
		Iterator<Object> keyIter = prop.keySet().iterator();
		while (keyIter.hasNext()) {
			String command = (String) keyIter.next();
			String handlerClassName = prop.getProperty(command);
			if (command.contains("#"))
				continue;
			try {
				Class<?> handlerClass = Class.forName(handlerClassName);
				BaseHandler handlerInstance = (BaseHandler) handlerClass.newInstance();
				commandHandlerMap.put(command, handlerInstance);
			} catch (ClassNotFoundException | InstantiationException 
					| IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
		
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		process(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		process(req, res);
	}

	private void process(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		HttpSession session;
		String viewPage = null;
		String command = req.getRequestURI();
		String lastPage;
		String lastQuery;
		MessageDO messageDO;
		
		System.out.printf("command %s\n", command);

		System.out.printf("==============================================\n");
		if (command.indexOf(req.getContextPath()) == 0) {
			command = command.substring(req.getContextPath().length());
		}
		if (command.equals("/"))
			command = "/index.do";

		/*
		 *  to keep some of request session after redirect 
		 */
		session = req.getSession(false);
		do {
			if (session == null)
				break;
			messageDO = (MessageDO) session.getAttribute("messageDO");
			if (messageDO == null)
				break;

			if (! messageDO.getRedirect())
				break;
			
			req.setAttribute("messageDO", messageDO);
			
			session.setAttribute("messageDO", null);
			if (messageDO.getResult().equals("logout"))
				session.invalidate();

		} while (false);
		
		
		BaseHandler handler = commandHandlerMap.get(command);
		System.out.println(handler);
		if (handler == null) {
			handler = new NullHandler();
		}

		try {
			req.setCharacterEncoding("utf-8");        	
			res.setContentType("text/html;charset=UTF");

			if (req.getMethod().equalsIgnoreCase("GET")) {
				viewPage = handler.doGet(req, res);
			} 
			else if (req.getMethod().equalsIgnoreCase("POST")) {
				viewPage = handler.doPost(req, res);
			}

		} catch (Throwable e) {
			throw new ServletException(e);
		}

		/*
		 * to keep current page after login/logout
		 */
		session = req.getSession(false);
		do { 
			if (session == null)
				break;
			
			if (req.getAttribute("ignoreLastPage") != null)
				break;
			
			if (req.getMethod().equalsIgnoreCase("POST"))
				break;
			
			if (viewPage == null)
				break;
			
			lastPage = command.substring(1);
			lastQuery = req.getQueryString();
			if (lastQuery != null) {
				session.setAttribute("lastPage", lastPage + "?" + lastQuery);
			}
			else
				session.setAttribute("lastPage", lastPage);
				
		} while (false);
		
		System.out.printf("viewPage %s\n", viewPage);
		if (viewPage != null) {

			/*
        	RequestDispatcher dispatcher = req.getreqDispatcher("/index.jsp?page=" + viewPage);
        	//*/
			req.setAttribute("page", viewPage);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/common/index.jsp");
			dispatcher.forward(req, res);
		}
		System.out.printf("-------------------------------------------------------\n");
	}

}
