package board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MiscUtil;
import handler.BaseHandler;

public class BoardDeleteHandler implements BaseHandler {
	
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return MiscUtil.errorAccess(req, res);
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		BoardDAO dao = new BoardDAO();
		String boardType;
		String boardId; 
		String pageNum; 
		String searchKey;
		String searchVal;
		
		System.out.println("MemberDeleteHandler: doPost");
		try {

			
			boardType = req.getParameter("boardType");
			boardId = req.getParameter("boardId");
			pageNum = req.getParameter("pageNum");
			searchKey = req.getParameter("searchKey");
			searchVal = req.getParameter("searchVal");
		
			req.setAttribute("boardType", boardType);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("searchKey", searchKey);
			req.setAttribute("searchVal", searchVal);
			
		
			dao.delete(boardType, boardId);
			res.sendRedirect("boardList.do?boardType=" + boardType);
			return null;
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}

}
