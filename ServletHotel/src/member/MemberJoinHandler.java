package member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MessageDO;
import common.MiscUtil;
import handler.BaseHandler;

public class MemberJoinHandler implements BaseHandler {
	private static final String FORM_VIEW = "/member/memberJoin.jsp";
		
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("MemberLoginHandler: doGet");
		return FORM_VIEW;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberDTO dto = new MemberDTO();
		MemberDTO dupDTO;
		MemberDAO dao = new MemberDAO();
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
		
		MiscUtil.printReq(req);
		
		System.out.println("MemberLoginHandler: doPost");
		
		try {
			MessageDO messageDO = new MessageDO();
			
			req.setAttribute("messageDO", messageDO);
			
			
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
			// emailConfirm = req.getParameter("emailConfirm");
			emailConfirm = req.getParameterValues("emailConfirm");
			
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
			
	
			req.setAttribute("account", account);
			req.setAttribute("password", password);
			req.setAttribute("nickname", nickname);
			req.setAttribute("zipcode", zipcode);
			req.setAttribute("address1", address1);
			req.setAttribute("address2", address2);
			req.setAttribute("birthYear", birthYear);
			req.setAttribute("birthMonth", birthMonth);
			req.setAttribute("birthDay", birthDay);
			req.setAttribute("tel", tel);
			req.setAttribute("email", email);
			req.setAttribute("tel", tel);
		

			if ((dupDTO = dao.selectByAccount(dto.getAccount())) != null) {
				messageDO.setResult("error");
				messageDO.setReason("아이디가 중복되었습니다.");
				return FORM_VIEW;
			}
			dao.insert(dto);
			
			res.sendRedirect("index.do");
			return null;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}

	}

}
