package admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MiscUtil;
import handler.BaseHandler;
import member.MemberDAO;

public class AdminMemberDeleteHandler implements BaseHandler {
	private static final String FORM_VIEW = "adminMemberList.do";
		
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("MemberDeleteHandler: doGet");
		res.sendRedirect(FORM_VIEW);
		return null;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberDAO dao = new MemberDAO();
		int id;
		
		System.out.println("MemberDeleteHandler: doPost");
		
		try {
			id = Integer.parseInt(req.getParameter("id"));
			dao.delete(id);
			res.sendRedirect(FORM_VIEW);
			return null;
		}
		catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}

}
