package admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.PagingDO;
import handler.BaseHandler;
import member.MemberDAO;
import reserve.ReserveDAO;
import reserve.ReserveDTO;
import room.RoomDAO;
import room.RoomDTO;
import room.RoomTypeDAO;
import room.RoomTypeDTO;


public class AdminReserveListHandler implements BaseHandler {
	private static final String FORM_VIEW = "/admin/adminReserveList.jsp";
	private static final int rowMax = 10; /* row max per page */
	private static final int pageMax = 10; /* page max per pagination */

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ReserveDAO reserveDAO = new ReserveDAO();
		RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
		RoomDAO roomDAO = new RoomDAO();
		MemberDAO memberDAO = new MemberDAO();
		PagingDO paging = new PagingDO(rowMax, pageMax);
		RoomTypeDTO roomTypeDTO;
		RoomDTO roomDTO;
		

		System.out.println("ReserveListHandler: doGet");

		paging.open(req);
		paging.setRowCount(reserveDAO.count(0, paging));
		System.out.printf("rowCount %d\n", paging.getRowCount());
		
		List<ReserveDTO> list = reserveDAO.select(paging);
		System.out.printf("list.size %d\n", list.size());
		
		for (ReserveDTO reserveDTO: list) {
			roomDTO = roomDAO.select(reserveDTO.getRoomId());
			System.out.printf("roomId %d, roomNum %d\n", reserveDTO.getRoomId(), roomDTO.getRoomNum());
			reserveDTO.setRoomDTO(roomDTO);
			reserveDTO.setRoomTypeDTO(roomTypeDAO.select(roomDTO.getRoomTypeId()));
			reserveDTO.setMemberDTO(memberDAO.select(reserveDTO.getUserId()));
		}
		
		paging.close(req);
		
		req.setAttribute("list", list);
		paging.setAttribute(req);
		
		return FORM_VIEW;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}

}
