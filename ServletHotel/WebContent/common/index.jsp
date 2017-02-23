<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:if test="${pagePopup != null}">
	<jsp:include page='../${page}'/>
</c:if>
<c:if test="${pagePopup == null}">
	<jsp:include page="../main/mainMenu.jsp" flush="false"/>	
	<c:if test="${page != null}">
		<jsp:include page='../${page}'/>
	</c:if>
	<c:if test="${page == null}">
		<jsp:include page="../main/mainBody.jsp"/>
	</c:if>
	<jsp:include page="../main/mainFooter.jsp" flush="false"/>
</c:if>