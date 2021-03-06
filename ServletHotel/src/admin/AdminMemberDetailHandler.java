package admin;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MessageDO;
import common.MiscUtil;
import handler.BaseHandler;
import member.MemberDAO;
import member.MemberDTO;

public class AdminMemberDetailHandler implements BaseHandler {
	private static final String FORM_VIEW = "/admin/adminMemberDetail.jsp";
		
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberDAO dao = new MemberDAO();
		MemberDTO dto;
		String id;
		String birthYear;
		String birthMonth;
		String birthDay;
		
		System.out.println("AdminMemberDetailHandler: doGet");
		try {
			id = req.getParameter("id");
			System.out.println("id:" + id);
			
			dto = dao.select(Integer.parseInt(id));

			Calendar cal = Calendar.getInstance();
			cal.setTime(dto.getBirthday());

			birthYear = Integer.toString(cal.get(Calendar.YEAR));
			birthMonth = Integer.toString(cal.get(Calendar.MONTH));
			birthDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));

			req.setAttribute("id", dto.getId());
			req.setAttribute("account", dto.getAccount());
			req.setAttribute("password", dto.getPassword());
			req.setAttribute("nickname", dto.getNickname());
			req.setAttribute("zipcode", dto.getZipcode());
			req.setAttribute("address1", dto.getAddress1());
			req.setAttribute("address2", dto.getAddress2());
			req.setAttribute("birthYear", birthYear);
			req.setAttribute("birthMonth", birthMonth);
			req.setAttribute("birthDay", birthDay);
			req.setAttribute("tel", dto.getTel());
			req.setAttribute("email", dto.getEmail());
			req.setAttribute("tel", dto.getTel());
			return FORM_VIEW;
		}
		catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberDAO dao = new MemberDAO();
		MessageDO messageDO = new MessageDO();
		MemberDTO dto;
		String account;
		String password;
		String nickname;
		String zipcode;
		String address1;
		String address2;
		String birthYear;
		String birthMonth;
		String birthDay;
		String email;
		String emailConfirm[];
		String tel;
		String id;
		
		System.out.println("AdminMemberDetailHandler: doGet");
		
		try {
			MiscUtil.printReq(req);
			
			id = req.getParameter("id");
			dto = dao.select(Integer.parseInt(id));
			
			MiscUtil.printReq(req);
			
			account = req.getParameter("account");
			password = req.getParameter("password");
			nickname = req.getParameter("nickname");
			zipcode = req.getParameter("zipcode");
			address1 = req.getParameter("address1");
			address2 = req.getParameter("address2");
			birthYear = req.getParameter("birthYear");
			birthMonth = req.getParameter("birthMonth");
			birthDay = req.getParameter("birthDay");
			tel = req.getParameter("tel");
			email = req.getParameter("email");
			emailConfirm = req.getParameterValues("emailConfirm");
			
			dto.setId(Integer.parseInt(id));
			dto.setAccount(account);
			dto.setPassword(password);
			dto.setNickname(nickname);
			dto.setZipcode(zipcode);
			dto.setAddress1(address1);
			dto.setAddress2(address2);
			dto.setBirthday(MiscUtil.parseStringToDate(birthYear, birthMonth, birthDay));
			System.out.println("birthday: " + dto.getBirthday());
			dto.setTel(tel);
			
			dto.setEmail(email);
			if (emailConfirm != null)
				dto.setEmailConfirm(1);
			else
				dto.setEmailConfirm(0);
			
			dao.update(dto);
			
			res.sendRedirect("adminMemberList.do");
			return null;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}

	}
	

}
