<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/common/include.jspf" %>


<%
	session.setAttribute("loginDTO", null);
	session.invalidate(); 
%>

<script>
	// openModalInfoTimeOut("로그아웃 되었습니다.", 1000);

	location.href = "<%=request.getContextPath()%>/index.do";
</script>