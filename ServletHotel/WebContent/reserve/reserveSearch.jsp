<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<div class="container">
	<div class="col-sm-10 col-sm-offset-1">
		<form id="formReserveSearch" method="get" action="reserveSearch.do">
			<div class="panel panel-primary">
				<div class="panel-heading">
		
					<div class="row">
						<div class="form-group col-md-3">
							<label for="dateStart">체크인:</label>
							<input type="text" class="form-control" id="dateStart" name="dateStart" value="${dateStart}" placeholder="체크인 날짜" />
						</div>
						<div class="form-group col-md-3">
							<label for="dateEnd">체크아웃:</label>
							<input type="text" class="form-control" id="dateEnd" name="dateEnd" value="${dateEnd}"placeholder="체크아웃"/>
						</div>
						<div class="form-group col-md-2">
							<label for="dateCount">숙박일수:</label>
							<input type="text" class="form-control" id="dateCount" name="dateCount" value="${dateCount}" readonly="readonly">
						</div>
					
						<div class="form-group col-md-2">
							<label for="guestNum">투숙객수</label>
							<select class="form-control" id="guestNum" name="guestNum">
								<c:set var="options" value="<%=new int[] {1, 2, 3, 4, 5, 10}%>" />
								<c:forEach var="i" items="${options}">
									<option value='${i}' <c:out value="${guestNum == i ? 'selected' : '' }"/>> 
										${i}
									</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="form-group col-md-2">
							<label for="reserve"></label>
							<button type="submit" class="form-control btn btn-warning" id="reserveSearch" >검색</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<c:if test="${list == null || list.size() == 0}">
		<jsp:include page="../room/roomTypeCard.jsp"/>
	</c:if>

	<div class="col-sm-10 col-sm-offset-1">
		<table class="table table-hover table-striped table-responsive reserveDTO" style="display:none">
			<thead>
				<tr>
					<th>예약번호</th>
					<th>방타입</th>
					<th>체크인</th>
					<th>체크아웃</th>
					<th></th>
				</tr>
			</thead>
		</table>
	</div>

	<form id="formRoomList" method="post">
		<input type="hidden" id="dateStart" name="dateStart" value="${dateStart}">
		<input type="hidden" id="dateEnd" name="dateEnd" value="${dateEnd}">
		<input type="hidden" id="dateCount" name="dateCount" value="${dateCount}">
		<input type="hidden" id="guestNum" name="guestNum" value="${guestNum}">
		<c:forEach items="${list}" var="dto">
			<div class="col-md-8 col-md-offset-2">
				<div class="row" style="width:800px">
					<div class="col-sm-8">
						<div id="myCarousel" class="carousel slide" data-ride="carousel">
							
							
								<!-- Indicators -->
								<ol class="carousel-indicators">
									<c:forEach items="${dto.files}" var="file" begin="0" varStatus="status">
										<c:if test="${status.index == '0'}">
											<li data-target="#myCarousel" data-slide-to="${status.index}" class="active"></li>
										</c:if>
										<c:if test="${status.index != '0'}">
											<li data-target="#myCarousel" data-slide-to="${status.index}"></li>
										</c:if>
									</c:forEach>
								</ol>
					
								<!-- Wrapper for slides -->
								<div class="carousel-inner" role="listbox">
									<c:forEach items="${dto.files}" var="file" begin="0" varStatus="status">
										<c:if test="${status.index == '0'}">
											<div class="item active">
												<img src="${pageContext.request.contextPath}/images/room/${file.filename}" alt="Image">
													<div class="carousel-caption">
														<h3>${dto.typeDesc}룸</h3>
														<p>${dto.maxPax}명/${dto.cost}원</p>
													</div>      
											</div>
										</c:if>
										<c:if test="${status.index != '0'}">
											<div class="item">
												<img src="${pageContext.request.contextPath}/images/room/${file.filename}" alt="Image">
													<div class="carousel-caption">
														<h3>${dto.typeDesc}룸</h3>
														<p>${dto.maxPax}명/${dto.cost}원</p>
													</div>      
											</div>
										</c:if>
									</c:forEach>
								</div>
							
							<!-- Left and right controls -->
							<a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
								<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							</a>
							<a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
								<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							</a>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="well">
							<p>${dto.typeDesc}</p>
						</div>
						<div class="well">
							<p>최대 ${dto.maxPax}</p>
						</div>
						<div class="well">
							<p>${dto.cost}원</p>
						</div>
						<div>
							<button type="button" class="btn btn-success" id="reserveConfirm" data-roomType='${dto.type}'>예약</button>
						</div>
					</div>
				</div>
				
			</div>
			<div class="form-group col-xs-12"></div> <!-- FOR BLANK LINE -->
		</c:forEach>
	</form>
		
	
</div>

<script id="templateReservedItem" type="text/x-handlebars-template">
    <tr>
        <td>{{reserveId}}</td>
        <td>{{roomTypeDesc}}</td>
        <td>{{dateStart}}</td>
		<td>{{dateEnd}}</td>
		<td><button type="input" class="btn btn-warning" id="reserveDelete" date-roomType="{{roomType}}" data-reserveId="{{reserveId}}">
			삭제</button>
		</td>
    </tr>
</script>



<script>

$(document).ready(function() {
	var dateStart = $('#dateStart');
	var dateEnd = $('#dateEnd');
	var oneDay = 1000 * 60 * 60 * 24;
	var options = {
		format : 'yyyy/mm/dd',
		startDate:'+1d',
		container : "body",
		todayHighlight : true,
		language : 'ko', 
		autoclose : true,
	};
	dateStart.datepicker(options).on('changeDate', function(e) {
		var dStart = new Date(e.format(0, "yyyy/mm/dd"));
		var dEnd = new Date(dStart.getTime() + oneDay);
		dateEnd.datepicker('setStartDate', dEnd);
	});
	dateEnd.datepicker(options).on('changeDate', function(e) {
		var dStart = new Date($('#dateStart').data('datepicker').getFormattedDate('yyyy/mm/dd'));
		var dEnd = new Date(e.format(0, "yyyy/mm/dd"));
		  
		var diff = Math.ceil((dEnd.getTime() - dStart.getTime()) / oneDay); 
		$('#dateCount').val(diff);
	});
	
	
	<c:if test="${list.size() == 0}">
		openModalInfo("알림", "정해진 구간에 룸이 모두 예약되었습니다.")
	</c:if>
	
})

var formReserveSearch = $("#formReserveSearch");
formReserveSearch.submit(function(e) {
	event.preventDefault();
	if ($("#dateStart").val() == "") {
		openModalInfo("알림", "체크인을 지정하세요.");
		return;
	}
	
	if ($("#dateEnd").val() == "") {
		openModalInfo("알림", "체크아웃을 지정하세요.");
		return;
	}
	
	var dStart = new Date($('#dateStart').data('datepicker').getFormattedDate('yyyy/mm/dd'));
	var dEnd = new Date($('#dateEnd').data('datepicker').getFormattedDate('yyyy/mm/dd'));
	if (dEnd.getTime() - dStart.getTime() < 0) {
		openModalInfo("알림", "체크아웃이 체크인보다 빠릅니다.");
		return;
	}
	
	$(this).get(0).submit();
});


function addReserveDTO(reserveDTO) {
	var template = Handlebars.compile($('#templateReservedItem').html());
	var html = template(reserveDTO);
	$(".reserveDTO tr:last").after(html);
	
	$(".reserveDTO").show();
}
	
$("#formRoomList").on("click", "#reserveConfirm", function(event) {
	var roomType = $(this).attr("data-roomType");
	var dateStart = $('#dateStart').val(); 
	var dateEnd = $('#dateEnd').val();
	var dateCount = $('#dateCount').val();
	var guestNum = $('#guestNum').val();
	
	var jsonObj = {
		dateStart : $('#dateStart').val(), 
		dateEnd : $('#dateEnd').val(),
		dateCount : $('#dateCount').val(),
		guestNum : $('#guestNum').val(),
		roomType : roomType,
	}
	var jsonWrap = {jsonData: JSON.stringify(jsonObj)};
	$.post('reserveInsert.do', jsonWrap, function(jsonDataRes) {

		// parseJSON: XML -> JSON, mine is already JSON.
		// var obj = jQuery.parseJSON(jsonDataRes);
		if (jsonDataRes.result == "success") {
			openModalInfo("알림", "예약하였습니다.");
			
			var reserveDTO = {
				dateStart:dateStart,   
				dateEnd:dateEnd,  
				dateCount:dateCount,
				guestNum:guestNum, 
				roomType:roomType,
				roomTypeDesc:jsonDataRes.roomTypeDesc,
				reserveId:jsonDataRes.reserveId
			};
			
			addReserveDTO(reserveDTO);
		}
		else {
			openModalInfo("예약 실패", jsonDataRes.reason);
		}
		
		
	})
	

})

$(".reserveDTO").on("click", "#reserveDelete", function(event) {
	var reserveId = $(this).attr("data-reserveId");
	var that = $(this);
	var jsonObj = {
		reserveId : reserveId 
	}
	var jsonWrap = {jsonData: JSON.stringify(jsonObj)};
	$.post('reserveDelete.do', jsonWrap, function(jsonDataRes) {
		if (jsonDataRes.result == "success") {
			openModalInfo("알림", "예약을 삭제하였습니다.");

			console.log($(this));
			that.closest('tr').remove();		
			//$(this).parent().remove();
			
		}
		else {
			openModalInfo("예약 삭제 실패", jsonDataRes.reason);
		}
		
	})
})
	
	

</script>


