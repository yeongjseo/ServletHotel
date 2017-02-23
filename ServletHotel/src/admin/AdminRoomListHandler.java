package admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.PagingDO;
import handler.BaseHandler;
import room.RoomDAO;
import room.RoomDTO;

public class AdminRoomListHandler implements BaseHandler {
	private static final String FORM_VIEW = "/admin/adminRoomList.jsp";
	private static final int rowMax = 10; /* row max per page */
	private static final int pageMax = 10; /* page max per pagination */

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		RoomDAO dao = new RoomDAO();
		PagingDO paging = new PagingDO(rowMax, pageMax);
		
		System.out.println("RoomListHandler: doGet");
		
		paging.open(req);
		paging.setRowCount(dao.count(paging));
		System.out.printf("rowCount %d\n", paging.getRowCount());
		
		List<RoomDTO> list = dao.selectWithRoomTypeDTO(paging);
		System.out.printf("list.size %d\n", list.size());
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
