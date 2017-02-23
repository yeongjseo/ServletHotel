package member;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MessageDO;
import handler.BaseHandler;
import net.sf.json.JSONObject;

public class MemberLoginHandler implements BaseHandler {
	private static final String FORM_VIEW = "/member/memberLogin.jsp";
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("MemberLoginHandler: doGet");
		return FORM_VIEW;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MessageDO messageDO = new MessageDO();
		PrintWriter out;
		String account = null;
		String password;
		String result = "fail";
		String reason = "";
		
		System.out.println("MemberLoginHandler: doPost");
		
		try {
			// MUST:
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/xml;charset=UTF-8");
			
			out = res.getWriter();
			account = req.getParameter("account");
			password = req.getParameter("password");
			if (account == null || password == null) {
				System.out.printf("account %s, pw %s\n", account, password);
				throw new Exception();
			}
			
			System.out.printf("account %s, pw %s\n", account, password);
			
			MemberDAO dao = new MemberDAO();
			MemberDTO dto = new MemberDTO();
			
			dto = dao.selectByAccount(account);
			if (dto == null) {
				reason = "없는 아이디입니다.";
			}
			else if (! dto.getPassword().equals(password)) {
				reason = "패스워드가 틀립니다.";
			}
			else {
				result = "success";
				reason = account;
				HttpSession session = req.getSession();
				session.setAttribute("loginDTO", dto);

				/*
				 * to display welcome message after redirect
				 */
				messageDO.setRedirect(true);
				messageDO.setResult("login");
				messageDO.setReason(reason + "님, 로그인되었습니다.");
				session.setAttribute("messageDO", messageDO);

			}
			
			System.out.printf("result %s, reason %s\n", result, reason);
			
			// XML
			/*
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<item><result>" + result + "</result><reason>" + reason + "</reason></item>");
			out.print(sb.toString());
			out.flush();
			*/

			// JSON
			JSONObject jsonObjRes = new JSONObject();
			jsonObjRes.put("result", result);
			jsonObjRes.put("reason", reason);
			jsonObjRes.put("account", account);
			res.setContentType("application/x-json; charset=UTF-8");
			out.print(jsonObjRes);
			out.flush();
			
		} catch (Exception ex) {
			ex.toString();
		}
		return null;
	}
}
