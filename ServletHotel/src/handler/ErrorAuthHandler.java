/*
 * JUST TEMPLATE FOR DEVEL
 */
package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ErrorAuthHandler implements BaseHandler {
	private static final String FORM_VIEW = "/error/errorAuth.jsp";

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {

		return FORM_VIEW;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}
}
