package common;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

public class MiscUtil {
	public static synchronized String getParameter(HttpServletRequest req, String name, Map<String, Boolean> errors, String key) {
		String value = req.getParameter(name);
		if (value == null || value.isEmpty()) {
			System.out.println("key is null");
			errors.put(key, Boolean.TRUE);
		}
		return value;
	}

	public static synchronized Boolean hasNull(HttpServletRequest req) {
		Enumeration<String> names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = req.getParameter(name);
			if (value == null || value.isEmpty())
				return Boolean.TRUE;
		}
		return Boolean.FALSE;

	}

	public static synchronized int compareString(String s1, String s2) {
		try {
			int a = parseInt(s1);
			int b = parseInt(s1);
			return a - b;
		} catch (Exception e) {
			System.out.printf("s1 %s, s2 %s\n", s1, s2);
		}
		return 0;
	}

	public static synchronized StringBuffer parseReq(HttpServletRequest req, StringBuffer sb) {
		Enumeration<String> names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = req.getParameter(name);
			sb.append(name + " : " + value + "\n");
		}
		return sb;
	}
	
	public static synchronized void printReq(HttpServletRequest req) {
		Enumeration<String> names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = req.getParameter(name);
			System.out.println(name + " : " + value);
		}
	}

	private static synchronized StringBuffer parseException(final Exception e, StringBuffer sb) {
		final StackTraceElement[] stackTrace = e.getStackTrace();
		String headerLine = "";
		String headerTitlePortion = "";
		String newLine = "\n";
		int index = 0;
		for (StackTraceElement elem : stackTrace) {
			final String msg = "[" + elem.getClassName() + "." + elem.getMethodName() + "] " + 
					"(" + elem.getFileName() + ":" + elem.getLineNumber() + ")";
			try {
				sb.append(headerLine + newLine);
				sb.append(headerTitlePortion + index++ + newLine);
				sb.append(headerLine + newLine);
				sb.append(msg + newLine + newLine);
				sb.append("Exception.toString: " + elem.toString() + newLine);
			}
			catch (Exception ex) {}
		}
		return sb;
	}


	public static synchronized String error(HttpServletRequest req, HttpServletResponse res, Exception e) throws IOException {
		HttpSession session = req.getSession(false);
		if (session == null)
			return null;
		StringBuffer sb = new StringBuffer();
		String msg = Thread.currentThread().getStackTrace()[2].getClassName() + ":" +  
				Thread.currentThread().getStackTrace()[2].getLineNumber();
		System.out.println("[" + msg + "] " + e.toString());
		sb = parseReq(req, sb);
		session.setAttribute("errorLoc", msg);
		session.setAttribute("errorReason", e.toString());
		session.setAttribute("errorParam", sb.toString());
		res.sendRedirect("errorDebug.do");
		return null;
	}

	public static synchronized String errorCommon(HttpServletRequest req, HttpServletResponse res, 
							String reason) throws IOException {
		
		HttpSession session = req.getSession(false);
		if (session == null)
			return null;
		session.setAttribute("errorReason", reason);
		res.sendRedirect("errorCommon.do");
		return null;
	}
	
	public static synchronized String errorAccess(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.sendRedirect("errorAccess.do");
		return null;
	}
	
	public static synchronized String errorAuth(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.sendRedirect("errorAuth.do");
		return null;
	}
	
	public static synchronized String getDateString(int year, int month, int day) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

		@SuppressWarnings("deprecation")
		String date = sd.format(new Date(year - 1900, month, day));

		return date;
	}

	public static String getDateString(String year, String month, String day) {
		return year + "/" + month + "/" + day;
	}
	
	public static String getDateTimeString(java.sql.Date date) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		java.util.Date d = new java.util.Date(date.getTime()); 
		String str = sd.format(d);
		return str;
	}
	
	public static synchronized java.sql.Date parseStringToDate(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date date = sdf.parse(dateStr);
		return new java.sql.Date(date.getTime());

	}
	public static synchronized java.sql.Date parseStringToDate(String year, String month, String day) throws ParseException {
		String dateStr; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		dateStr = year + "/" + month + "/" + day;
		System.out.println(dateStr);
		java.util.Date date = sdf.parse(dateStr);
		return new java.sql.Date(date.getTime());

	}

	public static synchronized String jsonRespond(HttpServletRequest req, HttpServletResponse res, String result, String reason) 
			throws IOException {
		JSONObject jsonObjRes = new JSONObject();
		jsonObjRes.put("result", result);
		jsonObjRes.put("reason", reason);
		res.setContentType("application/x-json; charset=UTF-8");
		PrintWriter out = res.getWriter();	
		out.print(jsonObjRes);
		out.flush();
		return null;
	}

	public static synchronized String getRandomString() {
		// 32 random character
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static synchronized long toUnit(long filesize) {
		long size = 0;

		if (filesize > (1024 * 1024)) {
			size = filesize / (1024 * 1024); // MB
		} else {
			size = filesize / 1024; // KB
		}
		return size;
	}

	public static synchronized String toUnitStr(long filesize) {
		String size = "";

		if (filesize > (1024 * 1024)) {
			size = filesize / (1024 * 1024) + " MB"; // MB
		} else {
			size = filesize / 1024 + " KB"; // KB
		}
		return size;
	}

	public static synchronized String getToday() {
		String str = "";
		Calendar cal = Calendar.getInstance();
		str = "" + cal.get(Calendar.DATE);
		return str;
	}

	public static synchronized String getTodayFormatDash() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String date = sd.format(new Date());
		return date;
	}

	public static synchronized String getTodayFormat() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String date = sd.format(new Date());
		return date;
	}

	public static synchronized String getDateFormat(Date date) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String str = sd.format(date);
		return str;
	}

	public static synchronized String getTodayKor() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy년 MM월 dd일");
		String date = sd.format(new Date());
		return date;
	}

	public static synchronized String getDateYearMonthDay(int year, int month, int day) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		new Date();
		@SuppressWarnings("deprecation")
		String date = sd.format(new Date(year - 1900, month, day));
		return date;
	}

	public static synchronized String convertChar(String str) {
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("'", "&#39;");   // '
		str = str.replaceAll("\"", "&quots;"); // "
		str = str.replaceAll("\r\n", "<BR>");  // 라인 변경

		return str;
	}

	public static synchronized String convertCharTA(String str) {
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("'", "&#39;");   // '
		str = str.replaceAll("\"", "&quots;"); // "
		return str;
	}

	public static synchronized String getString(HttpServletRequest request, String variable) {
		String value = "";
		variable = request.getParameter(variable);
		if (variable != null) {
			variable = variable.trim();
			if (variable.length() > 0) {
				value = variable;
			}
		} else {
			value = variable;
		}
		return value;
	}

	public static synchronized int getInt(HttpServletRequest request, String variable) {
		int value = 0;

		variable = request.getParameter(variable);
		if (variable != null) {
			variable = variable.trim();
			if (variable.length() > 0) {
				value = Integer.parseInt(variable);
			}
		} else {
			value = 0;
		}
		return value;
	}

	// 줄 바꾸기
	public static synchronized String getConvertCharTextArea(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '&') {
				str = str.substring(0, i) + "&" + str.substring(i + 1, str.length());
			}
		}
		return str;
	}

	public static synchronized String convertToDBMSforTextArea(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '<') {
				str = str.substring(0, i) + "<" + str.substring(i + 1, str.length());
			} else if (str.charAt(i) == '>') {
				str = str.substring(0, i) + ">" + str.substring(i + 1, str.length());
			}
		}
		return str;
	}

	public static synchronized String convertToHTMLforTextArea(String str) {
		str.replaceAll("<", "<");
		str.replaceAll(">", ">");
		return str;
	}

	
	public static synchronized boolean isImageFile(String filename) {
		boolean sw = false;

		if (filename != null) {
			sw = filename.endsWith(".jpg") || filename.endsWith(".jpeg")
					|| filename.endsWith(".gif") || filename.endsWith(".png")
					|| filename.endsWith(".bmp");
		}
		return sw;
	}

	public static synchronized String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}

	public static synchronized String getDisposition(String filename, String browser) {
		String dispositionPrefix = "attachment;filename=";
		String encodedFilename = null;
		try {
			if (browser.equals("MSIE")) {
				encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll(
						"\\+", "%20");
			} else if (browser.equals("Firefox")) {
				encodedFilename =

						"\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
			} else if (browser.equals("Opera")) {
				encodedFilename =

						"\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
			} else if (browser.equals("Chrome")) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < filename.length(); i++) {
					char c = filename.charAt(i);
					if (c > '~') {
						sb.append(URLEncoder.encode("" + c, "UTF-8"));
					} else {
						sb.append(c);
					}
				}
				encodedFilename = sb.toString();
			} else {
				System.out.println("Not supported browser");
			}
		} catch (Exception e) {

		}
		return dispositionPrefix + encodedFilename;
	}

	/**
	 * 임의의 정수를 리턴합니다.
	 * 
	 * @param range
	 *          정수범위 0 ~ 범위-1
	 * @return 난수 리턴
	 */
	public static synchronized int random(int range) {
		// 0 ~ range-1까지 산출됨.
		int rnd = 0;
		Random random = new Random();
		rnd = random.nextInt(range);

		return rnd;
	}

	public static synchronized Date getDate(String date) {
		Date currentTime = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			currentTime = sd.parse(date);
		} catch (Exception e) {
		}
		return currentTime;
	}

	public static synchronized String progress(String enddate) {
		java.util.Date nowtime = new java.util.Date();
		java.util.Date endtime = getDate(enddate);

		String progress = null;

		if (nowtime.after(endtime) == true) {
			progress = "종료";
		} else {
			progress = "진행";
		}
		return progress;
	}

	public static synchronized String commaString(int price) {
		DecimalFormat comma = new DecimalFormat("###,##0");
		String cs = comma.format(price);
		return cs;
	}

	public static synchronized String commaString(long price) {
		DecimalFormat comma = new DecimalFormat("###,##0");
		String cs = comma.format(price);
		return cs;
	}

	public static synchronized String readParameters(HttpServletRequest req) throws IOException {
		StringBuilder sb = new StringBuilder();
	    BufferedReader br = req.getReader();
	    String str;
	    while ((str = br.readLine()) != null ) {
	        sb.append(str);
	    }
	    return sb.toString();
	}
	
}
