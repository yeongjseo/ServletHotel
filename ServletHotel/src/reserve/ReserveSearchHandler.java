package reserve;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.PagingDO;
import handler.BaseHandler;
import room.RoomFileDAO;
import room.RoomTypeDAO;
import room.RoomTypeDTO;


public class ReserveSearchHandler implements BaseHandler {
	private static final String FORM_VIEW = "/reserve/reserveSearch.jsp";
	private static final int rowMax = 10;
	private static final int pageMax = 10;
	
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ReserveDAO dao = new ReserveDAO();
		RoomFileDAO roomFileDAO = new RoomFileDAO();
		RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
		PagingDO paging = new PagingDO(rowMax, pageMax);
		List<RoomTypeDTO> list = null;
		String dateStart = req.getParameter("dateStart");
		String dateEnd = req.getParameter("dateEnd");
		String dateCount = req.getParameter("dateCount");
		String guestNum = req.getParameter("guestNum");
		
		System.out.println("CompanyMainHandler: doGet");
		
		paging.open(req);
		paging.setRowCount(roomTypeDAO.count(paging));
		
		List<RoomTypeDTO> roomTypeDTOList = roomTypeDAO.select(paging);
		paging.close(req);

		for (RoomTypeDTO roomTypeDTO : roomTypeDTOList) {
			roomTypeDTO.setFiles(roomFileDAO.select(roomTypeDTO.getId()));
		}
		
		req.setAttribute("roomTypeDTOList", roomTypeDTOList);
		paging.setAttribute(req);
		
		if (dateStart == null) {
			System.out.println("dateStart null");
			return FORM_VIEW;
		}
		if (dateEnd == null) {
			System.out.println("dateEnd null");
			return FORM_VIEW;
		}
		
		System.out.printf("dateStart %s, dateEnd %s, dateCount %s, guestNum %s\n", dateStart, dateEnd, dateCount, guestNum);
		
		list = dao.selectVacantRoomType(dateStart, dateEnd);
		
		for (RoomTypeDTO dto : list) {
			dto.setFiles(roomFileDAO.select(dto.getId()));
		}
		
		req.setAttribute("list", list);
		req.setAttribute("dateStart", dateStart);
		req.setAttribute("dateEnd", dateEnd);
		req.setAttribute("dateCount", dateCount);
		req.setAttribute("guestNum", guestNum);
		return FORM_VIEW;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}


}
