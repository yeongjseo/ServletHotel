package handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MessageDO;
import common.MiscUtil;
import common.PagingDO;
import room.RoomFileDAO;
import room.RoomTypeDAO;
import room.RoomTypeDTO;


public class MainHandler implements BaseHandler {
	private static final String FORM_VIEW = "/main/mainBody.jsp";
	private static final int rowMax = 10; /* row max per page */
	private static final int pageMax = 10; /* page max per pagination */

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
		RoomFileDAO roomFileDAO = new RoomFileDAO();
		MessageDO messageDO;
		PagingDO paging = new PagingDO(rowMax, pageMax);
		
		System.out.println("MainHandler: doGet");

		try {
			
			paging.open(req);
			paging.setRowCount(roomTypeDAO.count(paging));
			
			List<RoomTypeDTO> roomTypeDTOList = roomTypeDAO.select(paging);
			paging.close(req);
	
			for (RoomTypeDTO roomTypeDTO : roomTypeDTOList) {
				roomTypeDTO.setFiles(roomFileDAO.select(roomTypeDTO.getId()));
			}
			
			req.setAttribute("roomTypeDTOList", roomTypeDTOList);
			paging.setAttribute(req);
			
			return FORM_VIEW;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return null;
	}


}
