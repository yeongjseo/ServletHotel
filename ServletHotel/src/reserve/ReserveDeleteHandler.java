package reserve;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MiscUtil;
import handler.BaseHandler;
import member.MemberDTO;
import net.sf.json.JSONObject;
import room.RoomDAO;
import room.RoomDTO;
import room.RoomTypeDTO;


public class ReserveDeleteHandler implements BaseHandler {
	private static final String FORM_VIEW = "/reserve/reserveSearch.jsp";

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		return FORM_VIEW;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ReserveDAO reserveDAO = new ReserveDAO();
		ReserveDTO reserveDTO = new ReserveDTO();
		JSONObject jsonObjRes = new JSONObject();
		RoomDAO roomDAO = new RoomDAO();
		RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
		RoomDTO roomDTO = new RoomDTO();
		MemberDTO memberDTO = null;
		String reserveId;

		
		System.out.println("ReserveDeleteHandler: doPost");
		
		try {
			String jsonData = req.getParameter("jsonData");
			JSONObject jsonObj = JSONObject.fromObject(jsonData);
			reserveId = (String) jsonObj.get("reserveId");
	
			System.out.printf("reserveId %s\n", reserveId); 
	
			HttpSession session = req.getSession(false);
			memberDTO = (MemberDTO) session.getAttribute("loginDTO");
			if (memberDTO == null) {
				MiscUtil.jsonRespond(req, res, "fail", "로그인하세요");
				return null;
			}
		
			reserveDAO.delete(reserveId);
						
			System.out.println("boardId: " + reserveId);
		
			jsonObjRes.put("result", "success");
			jsonObjRes.put("reason", "");
			
			res.setContentType("application/x-json; charset=UTF-8");
			PrintWriter out = res.getWriter();	
			out.print(jsonObjRes);
			out.flush();
			
			return null;
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
			MiscUtil.jsonRespond(req, res, "fail", "예약 삭제를 실패했습니다.");
		}
		return null;
	}
	
	
}
