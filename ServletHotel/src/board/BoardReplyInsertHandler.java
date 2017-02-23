package board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MiscUtil;
import handler.BaseHandler;
import member.MemberDTO;
import net.sf.json.JSONObject;

public class BoardReplyInsertHandler implements BaseHandler {
	
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return MiscUtil.errorAccess(req, res);
	}

	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		BoardReplyDAO dao = new BoardReplyDAO();
		BoardReplyDTO dto = new BoardReplyDTO();
		MemberDTO loginDTO;
		JSONObject jsonReplyMap = new JSONObject();
		JSONObject jsonMap;
		HttpSession session;
		String boardId;
		String replyContent;
		
		try {
			session = req.getSession(false);
			loginDTO = (MemberDTO)session.getAttribute("loginDTO");
			jsonMap = JSONObject.fromObject(MiscUtil.readParameters(req));
			boardId = (String)jsonMap.get("boardId");
			replyContent = (String) jsonMap.get("replyContent");
	
			System.out.printf("boardId %s, replyContent %s\n", 
					boardId, replyContent); 
		
			dto.setBoardId(Integer.parseInt(boardId));
			dto.setUserId(loginDTO.getId());
			dto.setContent(replyContent);

			dao.insert(dto);
			
			jsonReplyMap.put("result", "success");
			jsonReplyMap.put("reason", "");
			
			res.setContentType("application/x-json; charset=UTF-8");
			PrintWriter out = res.getWriter();	
			out.print(jsonReplyMap);
			out.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
			MiscUtil.jsonRespond(req, res, "fail", "댓글 입력을 실패했습니다.");
			
		}
		
		return null;
	}


}


