package board;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MiscUtil;
import common.PagingDO;
import handler.BaseHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BoardReplyListHandler implements BaseHandler {
	private static final int rowMax = 10; /* row max per page */
	private static final int pageMax = 10; /* page max per pagination */

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("BoardListHandler: doGet");
		return process(req, res);
	}
	
	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("BoardListHandler: doPost");
		return process(req, res);
	}
	
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		BoardReplyDAO dao = new BoardReplyDAO();
		BoardReplyDTO boardDTO;
		JSONObject jsonObject;
		JSONArray jsonArray;
		PagingDO paging = new PagingDO(rowMax, pageMax);
		String boardId;
		String page;
		int replyCount;

		try {
			boardId = req.getParameter("boardId");
			paging.open(req);
			
			replyCount = dao.count(boardId, paging);
			paging.setRowCount(replyCount);
			System.out.printf("rowCount %d\n", paging.getRowCount());
			
			List<BoardReplyDTO> list = dao.select(boardId, paging);
			System.out.printf("list.size %d\n", list.size());
			
			paging.close(req);
			
			jsonObject = new JSONObject();
			jsonArray = new JSONArray();
			for (BoardReplyDTO dto : list) {
				JSONObject obj = new JSONObject();
				
				obj.put("rowNum", dto.getRowNum());
				obj.put("replyId", dto.getId());
				obj.put("boardId", dto.getBoardId());
				obj.put("userId", dto.getUserId());
				obj.put("content", dto.getContent());
				obj.put("writeTime", MiscUtil.getDateTimeString(dto.getWriteDate()));
				obj.put("account", dto.getMemberDTO().getAccount());
				
				jsonArray.add(obj);
			}
			jsonObject.put("list", jsonArray);
			jsonObject.put("replyCount", replyCount);
			
			res.setContentType("application/x-json; charset=UTF-8");
			PrintWriter out = res.getWriter();	
			out.print(jsonObject);
			out.flush();
			return null;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	
	}

}
