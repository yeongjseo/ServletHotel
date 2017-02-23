<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- <link rel="stylesheet" href="/resources/demos/style.css"> -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>



<!-- TODO
-- bootStrap과 jQueryUI CSS 동시 사용시 이상동작
-- 임시로 jQueryUI 박스 사용
-->
<div id="messageBox" title="">
	<p class="body"></p>
</div>

<script>
function MsgBoxInfo(title, body) {
	var ret;
	var box = $("#messageBox");
	box.attr("title", title);
	box.find(".body").html(body);
	box.dialog({
		modal: true,
		buttons: {
			확인: function() {
				$(this).dialog("close");
      	},
		}
  });
}

var ret;
function MsgBoxConfirm(title, body) {
	var box = $("#messageBox");
	var ret;
	box.attr("title", title);
	box.find(".body").html(body);
	box.dialog({
		modal: true,
		buttons: {
			확인: function() {
				$(this).dialog("close");
      		ret = true;		
      	},
			취소: function() {
				$(this).dialog("close");
				ret = false;
			}
		}
  });
	return ret;
}


var datepicked = function() {
	var dateStart = $('#dateStart').datepicker('getDate')
	var dateEnd = $('#dateEnd').datepicker('getDate')
 
	if (dateStart && dateEnd) {
		var difference = 0;
		var oneDay = 1000*60*60*24; 
		var difference = Math.ceil((dateEnd.getTime() - dateStart.getTime()) / oneDay); 
		$('#dateCount').val(difference);
	} 
}


$(function() {
	$('#dateStart').datepicker({
		numberOfMonths: 2, 
		firstDay: 1,
		dateFormat: 'yy/mm/dd', 
		minDate: '0', 
		maxDate: '+2Y',
		onSelect: function(dateStr) {
			var min = $(this).datepicker('getDate');
			$('#to').datepicker('option', 'minDate', min || '0');
			datepicked();
		}
	});
	
	$('#dateEnd').datepicker({
		numberOfMonths: 2, 
		firstDay: 1,
		dateFormat: 'yy/mm/dd', 
		minDate: '0', 
		maxDate: '+2Y',
		onSelect: function(dateStr) {
			var max = $(this).datepicker('getDate');
			$('#dateStart').datepicker('option', 'maxDate', max || '+2Y');
			datepicked();
		}
	});
		
	
});

</script>


<div class="container">

	<br><br><br><br><br>
	
	<form id="formReserveSearch" method="get" action="reserveSearch.do">
		<div class="row">
			<div class="form-group col-md-3">
				<label for="dateStart">체크인:</label>
				<input type=text class="form-control" id="dateStart" name="dateStart" value="${dateStart}" placeholder="체크인" >
			</div>
			<div class="form-group col-md-3">
				<label for="dateEnd">체크아웃:</label>
				<input type="text" class="form-control" id="dateEnd" name="dateEnd" value="${dateEnd}" placeholder="체크아웃">
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
				<input type="submit" class="form-control btn btn-info" id="reserveSearch" value="예약검색">
			</div>
		</div>
	</form>


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
	

	<c:if test="${list.size() == 0}">
		<h2 class="text-center">선택하신 기간에 예약 가능한 방이 없습니다.</h2>
	</c:if>
	<c:if test="${list.size() != 0}">
		<h2 class="text-center">예약 가능 룸</h2>
	</c:if>

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

$("#formReserveSearch").submit(function(event) {

	event.preventDefault();
	
	if ($('#dateStart').val() == '') {
		MsgBoxInfo("확인", "체크인 날짜를 입력하세요.");
		return;
	}
	
	if ($('#dateEnd').val() == '') {
		MsgBoxInfo("확인", "체크아웃 날짜를 입력하세요.");
		return;
	}
	$(this).get(0).submit();
	
})

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
			MsgBoxInfo("알림", "예약하였습니다.");
			
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
			MsgBoxInfo("예약 실패", jsonDataRes.reason);
		}
		
		
	})
	
	/*
	var frm = $("#formRoomList");
	
	frm.attr("action", "reserveInsert.do");
	frm.append("<input type='hidden' name='roomType' value='"+$(this).attr("data-roomType") +"'>");
	frm.submit();
	*/
	
	/*
	$.ajax({
		type : 'post',
		url : 'reserveInsert.do',
		headers : {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "POST"
		},
		dataType : 'text',
		data : 'jsonData=' + JSON.stringify({
			name : 'yeong'
		}),
		success : function(jsonData) {
			$.each(jsonData, function(key,value) {
				alert (key + ":" + value + ""); 
			})
		}
	});
	*/

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
			MsgBoxInfo("알림", "예약을 삭제하였습니다.");

			console.log($(this));
			that.closest('tr').remove();		
			//$(this).parent().remove();
			
		}
		else {
			MsgBoxInfo("예약 삭제 실패", jsonDataRes.reason);
		}
		
	})
})
	
	

</script>


