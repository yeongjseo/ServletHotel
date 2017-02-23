package board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Env;
import common.FileUtil;
import common.MessageDO;
import common.MiscUtil;
import handler.BaseHandler;
import member.MemberDAO;
import member.MemberDTO;

public class BoardInsertHandler implements BaseHandler {
	private static final String FORM_VIEW = "/board/boardInsert.jsp";
	
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String boardType = req.getParameter("boardType");
		String boardId = req.getParameter("boardId");
		String pageNum = req.getParameter("pageNum");
		String searchKey = req.getParameter("searchKey");
		String searchVal = req.getParameter("searchVal");
		BoardDAO dao = new BoardDAO();
		MemberDAO memberDAO = new MemberDAO();
		BoardDTO boardDTO = new BoardDTO();
		
		System.out.println("BoardInsertHandler: doGet");
		
		if (boardType == null) {
			return null;
		}
		
		/*
		 * in case new board and then back
		 */
		req.setAttribute("boardType", boardType);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("searchKey", searchKey);
		req.setAttribute("searchVal", searchVal);
		
		try {
			
			return FORM_VIEW;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = new BoardDTO();
		BoardFileDAO boardFileDAO = new BoardFileDAO();
		BoardFileDTO boardFileDTO = new BoardFileDTO();
		MemberDTO memberDTO;
		MessageDO messageDO = new MessageDO();
		FileUtil fileUtil = new FileUtil();
		Map<String, Object> parm;
		List<Map<String, Object>> files;
		HttpSession session;
		String boardType;
		String pageNum;
		String searchKey;
		String searchVal;
		String title;
		String content;
		String boardId;
		String uploadPath;
		int bId;
		int nextVal;

		try {
			uploadPath = Env.get("uploadPath");
			parm = fileUtil.parse(req, uploadPath, "boardType");
	
			boardType = (String)parm.get("boardType");
			pageNum = (String)parm.get("pageNum");
			searchKey = (String)parm.get("searchKey");
			searchVal = (String)parm.get("searchVal");
			title = (String)parm.get("title");
			content = (String)parm.get("content");
			files = (List<Map<String, Object>>)parm.get("files");
	
			req.setAttribute("boardType", boardType);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("searchKey", searchKey);
			req.setAttribute("searchVal", searchVal);
	
			session = req.getSession(false);
			memberDTO = (MemberDTO) session.getAttribute("loginDTO");
			if (memberDTO == null) {
				messageDO.setResult("error");
				messageDO.setCode("login");
				messageDO.setReason("로그인하세요.");
				return FORM_VIEW;
			}		
			
			System.out.printf("boardType %s, title %s\n", boardType, title);

			//boardDAO.setAutoCommit(false);
			
			boardDTO.setUserId(memberDTO.getId());
			boardDTO.setType(BoardDTO.getBoardType(boardType));
			boardDTO.setTitle(title);
			boardDTO.setContent(content);
			nextVal = boardDAO.nextVal();
			System.out.println("board nextVal" + nextVal);
			boardDAO.insertByNextVal(boardType, boardDTO, nextVal);
			
			
			for (Map<String, Object> file : files) {
				boardFileDTO.setBoardId(nextVal);
				boardFileDTO.setFilename((String)file.get("filename"));
				boardFileDTO.setSavedFilename((String)file.get("savedFilename"));
				boardFileDTO.setFilesize(Math.toIntExact((Long)file.get("filesize")));
				
				boardFileDAO.insert(boardFileDTO);
				
			}
			//boardDAO.commit();
			
			res.sendRedirect("boardList.do?boardType=" + boardType);

		} catch (Exception e) {
			//boardDAO.rollback();
			return MiscUtil.error(req, res, e);
		}
		
		
		return null;
	}


}
