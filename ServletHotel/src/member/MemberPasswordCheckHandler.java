package member;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MessageDO;
import handler.BaseHandler;
import net.sf.json.JSONObject;

public class MemberPasswordCheckHandler implements BaseHandler {
	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("MemberPasswordCheckHandler: doGet");
		return null;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		HttpSession session;
		MemberDTO loginDTO;
		MessageDO messageDO = new MessageDO();
		JSONObject jsonObjRes = new JSONObject();
		PrintWriter out;
		String account = null;
		String password;
		String result = "fail";
		String reason = "";
		
		System.out.println("MemberPasswordCheckHandler: doPost");
		
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/x-json; charset=UTF-8");
			
			out = res.getWriter();
			account = req.getParameter("account");
			password = req.getParameter("password");
			if (account == null || password == null) {
				throw new Exception();
			}
			
			System.out.printf("account %s, pw %s\n", account, password);
			
			session = req.getSession(false);
			loginDTO = (MemberDTO) session.getAttribute("loginDTO");
			
			dto = dao.selectByAccount(account);
			if (dto == null) {
				reason = "계정이 없습니다.";
				throw new Exception();
			}
			else if (! loginDTO.getPassword().equals(password)) {
				reason = "패스워드가 틀립니다.";
			} else {
				result = "success";
				reason = account;
			}
			
			System.out.printf("result %s, reason %s\n", result, reason);
			
			// JSON
			jsonObjRes.put("result", result);
			jsonObjRes.put("reason", reason);
			jsonObjRes.put("account", account);
			out.print(jsonObjRes);
			out.flush();
			
		} catch (Exception ex) {
			jsonObjRes.put("result", result);
			jsonObjRes.put("reason", reason);
			res.setContentType("application/x-json; charset=UTF-8");
			out = res.getWriter();
			out.print(jsonObjRes);
			out.flush();
		}
		return null;
	}
}
