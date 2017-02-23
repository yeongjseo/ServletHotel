package admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MiscUtil;
import common.PagingDO;
import handler.BaseHandler;
import reserve.ReserveDAO;
import room.RoomDAO;
import room.RoomDTO;

public class AdminRoomReserveListHandler implements BaseHandler {
	private static final String FORM_VIEW = "/admin/adminRoomReserveList.jsp";
	private static final int rowMax = 100; /* row max per page */
	private static final int pageMax = 10; /* page max per pagination */

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ReserveDAO reserveDAO = new ReserveDAO();
		RoomDAO roomDAO = new RoomDAO();
		PagingDO paging = new PagingDO(rowMax, pageMax);
		String yearCur;
		String monthCur;
		String dateStart;  
		String dateEnd;
		String dateLast; 
		Calendar calendar;
		
		System.out.println("AdminRoomReserveListHandler: doGet");
		
		try {
			yearCur = req.getParameter("yearCur");
			monthCur = req.getParameter("monthCur");
			dateStart = req.getParameter("dateStart");  
			dateEnd = req.getParameter("dateEnd");

			calendar = Calendar.getInstance();
			
			
			System.out.printf("yearCur %s, monthCur %s\n", yearCur, monthCur);
			
			if (yearCur == null) {
				yearCur = new SimpleDateFormat("yyyy").format(calendar.getTime());
				req.setAttribute("yearCur", yearCur);
			}
			
			if (monthCur == null) {
				monthCur = new SimpleDateFormat("MM").format(calendar.getTime());
				req.setAttribute("monCur", monthCur);
			}
			
			 
			if (dateStart == null || dateEnd == null) {
				
				dateStart = MiscUtil.getDateString(yearCur, monthCur, "1");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date date = sdf.parse(dateStart);
				
				calendar.setTime(date);
				calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
				dateEnd =  new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
				dateLast = new SimpleDateFormat("dd").format(calendar.getTime());
				req.setAttribute("dateLast", dateLast);
			}
			
			
			paging.open(req);
			paging.setRowCount(roomDAO.count(paging));
			System.out.printf("rowCount %d\n", paging.getRowCount());
			
			List<RoomDTO> list = roomDAO.selectWithRoomTypeDTO(paging);
			System.out.printf("list.size %d\n", list.size());
			paging.close(req);
	
			for (RoomDTO roomDTO : list) {
				roomDTO.setReserveDTO(reserveDAO.selectByRoomIdandDate(roomDTO.getId(), dateStart, dateEnd)); 
			}
			
			req.setAttribute("list", list);
			req.setAttribute("yearCur", yearCur);
			req.setAttribute("monthCur", monthCur);
			paging.setAttribute(req);
			
			return FORM_VIEW;
		}
		catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}


}
