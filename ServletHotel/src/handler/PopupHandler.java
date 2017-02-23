/*
 * JUST TEMPLATE FOR DEVEL
 */
package handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;
import board.BoardFileDAO;
import board.BoardFileDTO;
import common.MiscUtil;
import common.PagingDO;


public class PopupHandler implements BaseHandler {
	private static final String FORM_VIEW = "/common/popup.jsp";
	private static final int rowMax = 1;
	private static final int pageMax = 1;

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		BoardDAO dao = new BoardDAO();
		BoardFileDAO boardFileDAO = new BoardFileDAO(); 
		BoardDTO boardDTO;
		List<BoardFileDTO> boardFileDTOList;
		String boardType;
		
		try {
			boardType = req.getParameter("boardType");
			
			PagingDO paging = new PagingDO(rowMax, pageMax);

			paging.open(req);
			paging.setRowCount(dao.count(boardType, paging));
			System.out.printf("rowCount %d\n", paging.getRowCount());
			
			List<BoardDTO> list = dao.select(boardType, paging);
			paging.close(req);
			
			if (list.size() == 0)
				return null;
			
			boardDTO = list.get(0);
			boardFileDTOList = boardFileDAO.selectByBoardId(boardDTO.getId());
			
			req.setAttribute("boardType", boardType);
			req.setAttribute("boardDTO", boardDTO);
			req.setAttribute("boardFileDTOList", boardFileDTOList);
			req.setAttribute("pagePopup", "true");
			paging.setAttribute(req);
			
			req.setAttribute("ignoreLastPage", true);
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

