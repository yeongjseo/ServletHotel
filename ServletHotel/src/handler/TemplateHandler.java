/*
 * JUST TEMPLATE FOR DEVEL
 */
package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MiscUtil;


public class TemplateHandler implements BaseHandler {
	private static final String FORM_VIEW = "/xxx/xxxXXX.jsp";

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		try {
			
			
		
			return FORM_VIEW;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			
			
			
			return FORM_VIEW;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}
}
