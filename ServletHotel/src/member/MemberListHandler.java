package member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MiscUtil;
import common.PagingDO;
import handler.BaseHandler;

public class MemberListHandler implements BaseHandler {
	private static final String FORM_VIEW = "/member/memberList.jsp";
	private static final int rowMax = 10; /* row max per page */
	private static final int pageMax = 10; /* page max per pagination */

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberDAO dao = new MemberDAO();
		PagingDO paging = new PagingDO(rowMax, pageMax);
		String auth;

		System.out.println("MemberListHandler: doGet");
		
		try {
			auth = req.getParameter("auth");
			paging.open(req);
			
			paging.setRowCount(dao.count(0, paging));
			System.out.printf("rowCount %d\n", paging.getRowCount());
			
			List<MemberDTO> list = dao.select(paging);
			System.out.printf("list.size %d\n", list.size());
			
			paging.close(req);
			
			req.setAttribute("auth", auth);
			req.setAttribute("list", list);
			paging.setAttribute(req);
			
			return FORM_VIEW;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}


}
