<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
	<div class="col-md-8 col-md-offset-2">

		<div class="panel panel-danger">
			<div class="panel-heading">
				<div class="text-center">
					<h4>예약정보</h4>
				</div>
			</div>
			<div class="panel-body">
				<c:if test="${reserveDTOList == null || reserveDTOList.size() == 0}">
					<div class="text-center">
						<h5>예약된 정보가 없습니다.</h5>
					</div>		
				</c:if>
			
				<c:forEach var="reserveDTO" items="${reserveDTOList}" begin="0" varStatus="var">
					<div class="panel panel-primary" id="reserveDTO">
						<div class="panel-heading">예약</div>
				
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
		
						</div>
						
						<div class="panel-footer">					
							<div class="row">
								<div class="col-sm-3 pull-right">
									<label for="blank"></label>
									<c:if test="${reserveDTO.memberDTO.id == loginDTO.id || loginDTO.isAdmin == true}">			
										<button type="button" class="form-control btn btn-danger" id="reserveDelete_${var.index}">삭제</button>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<script>

$("button[id^='reserveDelete']").on("click", function(event) {
	var that = $(this);
	// var rid = $("#reserveId").val();
	var rid = that.closest('#reserveDTO').find('#reserveId').val();
	var jsonObj = {
		reserveId : rid
	}
	var jsonWrap = {jsonData: JSON.stringify(jsonObj)};
	$.post('reserveDelete.do', jsonWrap, function(jsonDataRes) {
		if (jsonDataRes.result == "success") {
			openModalInfo("알림", "예약 (에약번호 " + rid + ")을 삭제하였습니다.");
			
			// self.location = "reserveList.do";
			that.closest('#reserveDTO').remove();
			
		}
		else {
			openModalInfo("예약 삭제 실패", jsonDataRes.reason);
		}
	})



}); 

</script>
