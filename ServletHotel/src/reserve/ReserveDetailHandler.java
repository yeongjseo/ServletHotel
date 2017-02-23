package reserve;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MiscUtil;
import handler.BaseHandler;
import member.MemberDAO;
import room.RoomDAO;
import room.RoomDTO;
import room.RoomTypeDAO;


public class ReserveDetailHandler implements BaseHandler {
	private static final String FORM_VIEW = "/reserve/reserveDetail.jsp";

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return process(req, res);
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return process(req, res);
	}

	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ReserveDAO reserveDAO = new ReserveDAO();
		RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
		MemberDAO memberDAO = new MemberDAO();
		RoomDAO roomDAO = new RoomDAO();
		ReserveDTO reserveDTO;
		RoomDTO roomDTO;
		String id;

		System.out.println("ReserveDetailHandler: doGet");
		
		try {
			id = req.getParameter("reserveId");
			
			reserveDTO = reserveDAO.select(id);
			roomDTO = roomDAO.select(reserveDTO.getRoomId());
			System.out.printf("roomId %d, roomNum %d\n", reserveDTO.getRoomId(), roomDTO.getRoomNum());
			reserveDTO.setRoomDTO(roomDTO);
			reserveDTO.setRoomTypeDTO(roomTypeDAO.select(roomDTO.getRoomTypeId()));
			reserveDTO.setMemberDTO(memberDAO.select(reserveDTO.getUserId()));
			
			
			req.setAttribute("reserveDTO", reserveDTO);
			return FORM_VIEW;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}
	
}
