package member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MiscUtil;
import handler.BaseHandler;

public class MemberDeleteHandler implements BaseHandler {
	private static final String FORM_VIEW = "index.do";
		
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("MemberDeleteHandler: doGet");
		res.sendRedirect(FORM_VIEW);
		return null;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("MemberDeleteHandler: doPost");
		MemberDAO dao = new MemberDAO();
		HttpSession session;
		MemberDTO loginDTO;
		
		try {
			session = req.getSession(false);
			loginDTO = (MemberDTO)session.getAttribute("loginDTO");
			dao.delete(loginDTO.getId());
			
			session.setAttribute("loginDTO", null);
			session.invalidate();
			res.sendRedirect(FORM_VIEW);
			return null;
		}
		catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}

}
