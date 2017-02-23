package member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MessageDO;
import common.MiscUtil;
import handler.BaseHandler;

public class MemberLogoutHandler implements BaseHandler {
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("MemberLoginHandler: doGet");
		return process(req, res);
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("MemberLogoutHandler: doPost");
		return process(req, res);
	}
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session;
		MemberDTO memberDTO;
		MessageDO messageDO = new MessageDO();
		String lastPage;
		
		try {
			session = req.getSession(false);
			
			lastPage = (String) session.getAttribute("lastPage");
			
			memberDTO = (MemberDTO) session.getAttribute("loginDTO");
			
			if (memberDTO != null) {
				System.out.println("================");
				/*
				 * to display welcome message after redirect
				 */
				messageDO.setRedirect(true);
				messageDO.setResult("logout");
				messageDO.setReason(memberDTO.account + "님, 로그아웃 되었습니다.");
				session.setAttribute("messageDO", messageDO);
			}
			
			session.setAttribute("loginDTO", null);
			System.out.println("lastPage:" + lastPage);
			// session.invalidate();
			if (lastPage != null) {
				res.sendRedirect(lastPage);
			}
			else
				res.sendRedirect("index.do");
			return null;	
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
		

	}
}
