package board;

import java.util.ArrayList;
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

public class BoardDetailHandler implements BaseHandler {
	
	private static final String FORM_VIEW = "/board/boardDetail.jsp";
	
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = new BoardDTO();
		BoardFileDAO boardFileDAO = new BoardFileDAO();
		List<BoardFileDTO> boardFileDTOList;
		MemberDAO memberDAO = new MemberDAO();
		MemberDTO memberDTO;
		
		try {
			String boardType = req.getParameter("boardType");
			String boardId = req.getParameter("boardId");
			String searchKey = req.getParameter("searchKey");
			String searchVal = req.getParameter("searchVal");
			
			System.out.println("BoardDetailHandler: doGet");
			
			if (boardType == null || boardId == null) {
				return MiscUtil.errorAccess(req, res);
			}
			
			
			System.out.printf("boardType %s", boardType);
			/*
			 * in case new board and then back
			 */
			req.setAttribute("boardType", boardType);
			req.setAttribute("searchKey", searchKey);
			req.setAttribute("searchVal", searchVal);
			
			
			boardDTO = boardDAO.select(boardType, boardId);
			boardDAO.increaseReadCount(boardType, boardId);
			
			memberDTO = memberDAO.select(boardDTO.getUserId());
			
			boardFileDTOList = boardFileDAO.selectByBoardId(boardDTO.getId());
			
			req.setAttribute("account", memberDTO.getAccount());
			req.setAttribute("memberId", memberDTO.getId());
			req.setAttribute("boardId", boardDTO.getId());
			req.setAttribute("title", boardDTO.getTitle());
			req.setAttribute("content", boardDTO.getContent());
			req.setAttribute("readCount", boardDTO.getReadCount());
			req.setAttribute("writeDate", boardDTO.getWriteDate());
			
			req.setAttribute("files", boardFileDTOList);
			req.setAttribute("boardDTO", boardDTO);
			
			System.out.printf("content %s\n", boardDTO.getContent());
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
		MessageDO messageDO = new MessageDO();
		MemberDTO memberDTO;
		BoardFileDAO boardFileDAO = new BoardFileDAO();
		BoardFileDTO boardFileDTO = new BoardFileDTO();
		List<BoardFileDTO> boardFileDTOList;
		FileUtil fileUtil = new FileUtil();
		Map<String, Object> parm;
		List<Map<String, Object>> files;
		List<String> deletedFileIdList = new ArrayList<>();
		HttpSession session;
		String boardType;
		String pageNum;
		String searchKey;
		String searchVal;
		String title;
		String content;
		String boardId;
		String fileId;
		String uploadPath;
		int bId;
		int nextVal;
		
		System.out.println("BoardDetailHandler: doPost");
		
		try {
			uploadPath = Env.get("uploadPath");
			parm = fileUtil.parse(req, uploadPath, "boardType");
	
			boardType = (String)parm.get("boardType");
			pageNum = (String)parm.get("pageNum");
			searchKey = (String)parm.get("searchKey");
			searchVal = (String)parm.get("searchVal");
			boardId = (String)parm.get("boardId");
			title = (String)parm.get("title");
			content = (String)parm.get("content");
			files = (List<Map<String, Object>>)parm.get("files");
	
			req.setAttribute("boardType", boardType);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("searchKey", searchKey);
			req.setAttribute("searchVal", searchVal);
	
			System.out.printf("boardType %s, boardId %s\n", boardType, boardId);
			
			session = req.getSession(false);
			memberDTO = (MemberDTO) session.getAttribute("loginDTO");
			if (memberDTO == null) {
				throw new Exception("No LoginDTO");
			}
			
			boardDTO = boardDAO.select(boardType, boardId);
			if (boardDTO == null) {
				throw new Exception("No Board by boardType=" + boardType + ", boardId=" + boardId);
			}
			boardDTO.setTitle(title);
			boardDTO.setContent(content);
			boardDAO.update(boardType, boardDTO);
			
			boardFileDTOList = boardFileDAO.selectByBoardId(boardDTO.getId());
			for (BoardFileDTO fileFileDTO : boardFileDTOList) {
				fileId = "fileId_" + fileFileDTO.getId();
				if (parm.get(fileId) == null)
					deletedFileIdList.add(Integer.toString(fileFileDTO.getId()));
			}
			
			for (String deleledFileId : deletedFileIdList) {
				boardFileDAO.delete(deleledFileId);
			}
			
			for (Map<String, Object> file : files) {
				boardFileDTO.setBoardId(Integer.parseInt(boardId));
				boardFileDTO.setFilename((String)file.get("filename"));
				boardFileDTO.setSavedFilename((String)file.get("savedFilename"));
				boardFileDTO.setFilesize(Math.toIntExact((Long)file.get("filesize")));
				
				boardFileDAO.insert(boardFileDTO);
			}		

			res.sendRedirect("boardList.do?boardType=" + boardType );
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
		
		
		return null;
	}


}
