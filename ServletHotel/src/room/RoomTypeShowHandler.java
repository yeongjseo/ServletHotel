package room;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.PagingDO;
import handler.BaseHandler;

public class RoomTypeShowHandler implements BaseHandler {
	private static final String FORM_VIEW = "/room/roomTypeShow.jsp";
	private static final int rowMax = 10; /* row max per page */
	private static final int pageMax = 10; /* page max per pagination */

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		RoomTypeDAO dao = new RoomTypeDAO();
		RoomFileDAO daoFile = new RoomFileDAO();
		PagingDO paging = new PagingDO(rowMax, pageMax);
		
		System.out.println("RoomTypeShowHandler: doGet");
		
		paging.open(req);
		paging.setRowCount(dao.count(paging));
		System.out.printf("rowCount %d\n", paging.getRowCount());
		
		List<RoomTypeDTO> list = dao.select(paging);
		System.out.printf("list.size %d\n", list.size());
		paging.close(req);

		for (RoomTypeDTO dto : list) {
			dto.setFiles(daoFile.select(dto.getId()));
		}
		
		req.setAttribute("list", list);
		paging.setAttribute(req);
		
		return FORM_VIEW;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}


}
