<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
	<jsp:include page="../reserve/reserveSearchQuick.jsp"/>
	
	<jsp:include page="../company/companySlide.jsp"/>

	<jsp:include page="../room/roomTypeCard.jsp"/>

	<jsp:include page="../company/companyMap.jsp"/>		
</div>

<script>

$(function pop() {
	var eventPopupNo = getCookie("eventPopupNo");		
	if (eventPopupNo != "true") {
		window.open('popup.do?boardType=event', "event", getPopupOption(0, 0, 500, 800));
	}

});
</script>