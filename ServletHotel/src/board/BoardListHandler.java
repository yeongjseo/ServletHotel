package board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MiscUtil;
import common.PagingDO;
import handler.BaseHandler;

public class BoardListHandler implements BaseHandler {
	
	private static final String FORM_VIEW = "/board/boardList.jsp";
	private static final int rowMax = 10; /* row max per page */
	private static final int pageMax = 10; /* page max per pagination */

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		BoardDTO boardDTO;
		BoardDAO dao = new BoardDAO();
		PagingDO paging = new PagingDO(rowMax, pageMax);
		String boardType;
		
		System.out.println("BoardListHandler: doGet");

		try {
			boardType = req.getParameter("boardType");
			if (boardType == null) {
				return MiscUtil.errorAccess(req, res);
			}
			
			req.setAttribute("boardType", boardType);
			
			paging.open(req);
			
			paging.setRowCount(dao.count(boardType, paging));
			System.out.printf("rowCount %d\n", paging.getRowCount());
			
			List<BoardDTO> list = dao.select(boardType, paging);
			System.out.printf("list.size %d\n", list.size());
			
			paging.close(req);
			
			req.setAttribute("list", list);
			paging.setAttribute(req);
			boardDTO = new BoardDTO();
			req.setAttribute("boardDTO", boardDTO);
			
			return FORM_VIEW + "?boardType=" + boardType;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}


}
