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


public class ReserveInsertHandler implements BaseHandler {
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
		String dateStart = null;
		String dateEnd;
		String dateCount;
		String guestNum;
		String roomType;
		int reserveId;

		String jsonData = req.getParameter("jsonData");
		JSONObject jsonObj = JSONObject.fromObject(jsonData);
		dateStart = (String) jsonObj.get("dateStart");
		dateEnd = (String) jsonObj.get("dateEnd");
		dateCount = (String) jsonObj.get("dateCount");
		guestNum = (String) jsonObj.get("guestNum");
		roomType = (String) jsonObj.get("roomType");

		System.out.printf("dateStart %s, dateEnd %s, dateCount %s\n",dateStart, dateEnd, dateCount); 
		System.out.printf("guestNum %s, roomType %s\n",guestNum, roomType);

		HttpSession session = req.getSession(false);
		memberDTO = (MemberDTO) session.getAttribute("loginDTO");
		if (memberDTO == null) {
			MiscUtil.jsonRespond(req, res, "fail", "로그인하고 예약하세요");
			return null;
		}
		
		req.setAttribute("dateStart", dateStart);
		req.setAttribute("dateEnd", dateEnd);
		req.setAttribute("dateCount", dateCount);
		req.setAttribute("guestNum", guestNum);
		
		try {
			roomDTO = reserveDAO.selectVacantAnyRoom(roomType, dateStart, dateEnd);
			
			if (roomDTO == null) {
				MiscUtil.jsonRespond(req, res, "fail", "해당 룸이 모두 예약되었습니다.");
				return null;
			}
			
			reserveDTO.setUserId(memberDTO.getId());
			reserveDTO.setRoomId(roomDTO.getId());
			reserveDTO.setDateStart(MiscUtil.parseStringToDate(dateStart));
			reserveDTO.setDateEnd(MiscUtil.parseStringToDate(dateEnd));
			/* TODO */
			reserveDTO.setPax(0);
			reserveDTO.setBreakfast(0);
			//reserveDAO.insert(reserveDTO);
			//boardId = reserveDAO.seqNum();
			//reserveId = reserveDAO.selectLastId();
			reserveId = reserveDAO.nextVal();
			reserveDAO.insertByNextVal(reserveDTO, reserveId);
			System.out.println("reserveId: " + reserveId);
			
			
			jsonObjRes.put("result", "success");
			jsonObjRes.put("reason", "");
			jsonObjRes.put("reserveId", reserveId);
			roomTypeDTO.setType(Integer.parseInt(roomType));
			jsonObjRes.put("roomTypeDesc", roomTypeDTO.getTypeDesc());
			
			res.setContentType("application/x-json; charset=UTF-8");
			PrintWriter out = res.getWriter();	
			out.print(jsonObjRes);
			out.flush();
			
			return null;
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
			MiscUtil.jsonRespond(req, res, "fail", "예약을 실패했습니다.");
		}
		return null;
	}

}
