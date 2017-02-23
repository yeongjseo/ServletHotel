package board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MiscUtil;
import handler.BaseHandler;
import net.sf.json.JSONObject;

public class BoardReplyDeleteHandler implements BaseHandler {
	
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return MiscUtil.errorAccess(req, res);
	}

	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		BoardReplyDAO dao = new BoardReplyDAO();
		JSONObject jsonReplyMap = new JSONObject();
		JSONObject jsonMap;
		HttpSession session;
		String replyId;
		String replyContent;
		
		try {
			session = req.getSession(false);
			jsonMap = JSONObject.fromObject(MiscUtil.readParameters(req));
			replyId = (String)jsonMap.get("replyId");
	
			System.out.printf("replyId %s, replyContent %s\n", replyId); 
		
			dao.delete(replyId);
			
			jsonReplyMap.put("result", "success");
			jsonReplyMap.put("reason", "");
			
			res.setContentType("application/x-json; charset=UTF-8");
			PrintWriter out = res.getWriter();	
			out.print(jsonReplyMap);
			out.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
			MiscUtil.jsonRespond(req, res, "fail", "댓글 삭제를 실패했습니다.");
			
		}
		
		return null;
	}


}


