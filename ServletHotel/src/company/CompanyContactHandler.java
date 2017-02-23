package company;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.EmailDO;
import common.EmailUtil;
import common.Env;
import common.MiscUtil;
import handler.BaseHandler;
import net.sf.json.JSONObject;


public class CompanyContactHandler implements BaseHandler {
	private static final String FORM_VIEW = "/company/companyContact.jsp";

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		EmailUtil emailUtil = new EmailUtil();
		EmailDO emailDO = new EmailDO();
		JSONObject jsonObjRes = new JSONObject();
		PrintWriter out;
		String emailAddress;
		String emailPassword;
		String result = "fail";
		String reason = "";
		
		try {
			MiscUtil.printReq(req);
			
			emailAddress = Env.get("emailAddress");
			emailPassword = Env.get("emailPassword");
			if (emailAddress == null || emailPassword == null) {
				reason = "서버 설정파일에 관리자의 이메일 정보가 없습니다.";
			}
			else {
				emailDO.setToEmailAddress(emailAddress);
				emailDO.setToEmailPassword(emailPassword);
				emailDO.setFromName(req.getParameter("name"));
				emailDO.setFromEmailAddress(req.getParameter("email"));
				emailDO.setTel(req.getParameter("tel"));
				emailDO.setTitle(req.getParameter("title"));
				emailDO.setContent(req.getParameter("content"));
				
				emailUtil.send(emailDO);
				result = "success";
			}
			
			// JSON

			jsonObjRes.put("result", result);
			jsonObjRes.put("reason", reason);
			res.setContentType("application/x-json; charset=UTF-8");
			out = res.getWriter();
			out.print(jsonObjRes);
			out.flush();
			
			return null;
		} catch (Exception e) {
			jsonObjRes.put("result", result);
			jsonObjRes.put("reason", "메일 전송이 실패하였습니다.");
			res.setContentType("application/x-json; charset=UTF-8");
			out = res.getWriter();
			out.print(jsonObjRes);
			out.flush();
			// return MiscUtil.error(req, res, e);
			return null;
		}
	}
}
