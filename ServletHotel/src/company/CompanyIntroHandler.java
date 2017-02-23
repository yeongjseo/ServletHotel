package company;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.BaseHandler;


public class CompanyIntroHandler implements BaseHandler {
	private static final String FORM_VIEW = "/company/companyIntro.jsp";

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}
}
