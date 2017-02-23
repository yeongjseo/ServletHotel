package board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MiscUtil;
import handler.BaseHandler;
import member.MemberDTO;
import net.sf.json.JSONObject;

public class BoardReplyUpdateHandler implements BaseHandler {
	
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
		String replyId;
		String replyContent;
		
		try {
			session = req.getSession(false);
			loginDTO = (MemberDTO)session.getAttribute("loginDTO");
			jsonMap = JSONObject.fromObject(MiscUtil.readParameters(req));
			replyId = (String)jsonMap.get("replyId");
			replyContent = (String) jsonMap.get("replyContent");
	
			System.out.printf("replyId %s, replyContent %s\n",
							replyId, replyContent); 
		
			dto = dao.select(replyId);
			if (dto == null) {
				return MiscUtil.jsonRespond(req, res, "fail", "댓글 정보가 없습니다.");
			}
			
			dto.setContent(replyContent);
			dao.update(dto);
			
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


