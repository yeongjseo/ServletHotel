<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">

	<div class="col-md-8 col-md-offset-2">
	
		<h2 class="text-center">예약 정보</h2>
	
		<div class="panel panel-primary">
		
			<div class="panel-heading">예약 정보</div>
	
			<div class="panel-body">
				<div class="row">	
					<div class="col-sm-3">
						<label for="account">예약번호:</label>
						<input type="text" class="form-control" id="reserveId" value="${reserveDTO.id}" readonly>
					</div>
					<div class="col-sm-3">
						<label for="account">예약자:</label>
						<input type="text" class="form-control" value="${reserveDTO.memberDTO.account}" readonly>
					</div>
					<div class="col-sm-3">
						<label for="roomType">룸타입:</label>
						<input type="text" class="form-control" value="${reserveDTO.roomTypeDTO.typeDesc}" readonly>
					</div>
					<div class="col-sm-3">
						<label for="roomNum">룸번호:</label>
						<input type="text" class="form-control" value="${reserveDTO.roomDTO.roomNum}" readonly>
					</div>
				</div>
				
				
				<div class="row">
					<div class="col-sm-3">
						<label for="checkin">체크인:</label>
						<input type="text" class="form-control" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${reserveDTO.dateStart}'/>" readonly> 
					</div>
			
					<div class="col-sm-3">
						<label for="checkout">체크아웃:</label>
						<input type="text" class="form-control" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${reserveDTO.dateEnd}'/>" readonly>
					</div>
			
					<div class="col-sm-3">
						<label for="dateReserve">예약일:</label>
						<input type="text" class="form-control" value="<fmt:formatDate pattern='yyyy-MM-dd hh:mm:ss' value='${reserveDTO.dateReserve}'/>" readonly> 
					</div>
					
					<div class="col-sm-3">
						<label for="pax">숙박인원</label>
						<input type="text" class="form-control" value="${reserveDTO.pax}" readonly>
					</div>
				</div>
					
				<div class="row">
					<div class="col-sm-3 pull-right">
						<label for="blank"></label>
						<c:if test="${reserveDTO.memberDTO.id == loginDTO.id}">			
							<button type="button" class="form-control btn btn-danger" id="delete">삭제</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>

<script>

$('#delete').on("click", function(event) {
	
	var jsonObj = {
		reserveId : $("#reserveId").val()
	}
	var jsonWrap = {jsonData: JSON.stringify(jsonObj)};
	$.post('reserveDelete.do', jsonWrap, function(jsonDataRes) {
		if (jsonDataRes.result == "success") {
			openModalInfo("알림", "예약을 삭제하였습니다.");
			
			self.location = "reserveList.do";
			
		}
		else {
			openModalInfo("예약 삭제 실패", jsonDataRes.reason);
		}
	})



}); 

</script>
