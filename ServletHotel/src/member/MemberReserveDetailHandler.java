package member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MiscUtil;
import handler.BaseHandler;
import reserve.ReserveDAO;
import reserve.ReserveDTO;
import room.RoomDAO;
import room.RoomDTO;
import room.RoomTypeDAO;

public class MemberReserveDetailHandler implements BaseHandler {
	private static final String FORM_VIEW = "/member/memberReserveDetail.jsp";

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ReserveDAO reserveDAO = new ReserveDAO();
		RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
		MemberDAO memberDAO = new MemberDAO();
		MemberDTO loginDTO;
		RoomDAO roomDAO = new RoomDAO();
		HttpSession session;
		List<ReserveDTO> reserveDTOList;
		RoomDTO roomDTO;
		String memberId;
		
		System.out.println("ReserveDetailHandler: doGet");
		
		try {
			
			session = req.getSession(false);
			loginDTO = (MemberDTO)session.getAttribute("loginDTO");
			reserveDTOList = reserveDAO.selectByMemberId(Integer.toString(loginDTO.getId()));
			
			req.setAttribute("reserveDTOList", reserveDTOList);
			
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	
		return FORM_VIEW;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			return null;	
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
		
	}

}
