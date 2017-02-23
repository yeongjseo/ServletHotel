<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>


<div class="container">
 
 	<div class="col-md-8 col-md-offset-2">
 		
 		<form class="form-horizontal" method="post" action="adminMemberDetail.do" id="formMember" onSubmit="return checkSubmit()">
	 		<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="text-center">
						<h4>회원 정보</h4>
					</div>
				</div>
				<div class="panel-body">
					<input type="hidden" id="id" name="id" value="${id}">
					<div class="form-group">
						<label class="control-label col-sm-2"  for="account">아이디:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="account" name="account" value="${account}" placeholder="계정명">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2"  for="password">비밀번호:</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="password" name="password" value="${password}" placeholder="비밀번호">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="nickname">별명:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="nickname" name="nickname" value="${nickname}" placeholder="별명">
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-2" for="email">이메일:</label>
						<div class="col-sm-10">
							<input type="email" class="form-control" id="email" name="email" value="${email}" placeholder="이메일"
								onblur="checkEmail(this)">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="pwd">전화번호:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="tel" name="tel" value="${tel}" placeholder="전화번호"
								onblur="checkTel(this)">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="birthday">생년월일:</label>
						<div class="col-sm-10">
							<div class="form-inline">
								<select class="form-control" id="birthYear" name="birthYear">
									<c:forEach var="i" begin="1950" end="2010" step="1">
					  					<option value='${i}' <c:out value="${birthYear == i ? 'selected' : '' }"/>> 
											${i}
										</option>
									</c:forEach>
								</select>
								년
								<select class="form-control" id="birthMonth" name="birthMonth">
									<c:forEach var="i" begin="1" end="12" step="1">
					  					<option value='${i}' <c:out value="${birthMonth == i ? 'selected' : '' }"/>> 
											${i}
										</option>
									</c:forEach>
								</select>
								월
								<select class="form-control" id="birthDay" name="birthDay">
									<c:forEach var="i" begin="1" end="31" step="1">
					  					<option value='${i}' <c:out value="${birthDay == i ? 'selected' : '' }"/>> 
											${i}
										</option>
									</c:forEach>
								</select>
								일
							</div>
						</div>		
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="birthday">주소:</label>
						<div class="col-sm-10">
							<div class="form-inline">
								<button type="button" class="form-control btn btn-warning" 
										onclick="execDaumPostcode(zipcode, address1, address2)">
									우편번호찾기
								</button>
								<input type="text" class="form-control" id="zipcode" name="zipcode" value="${zipcode}" placeholder="우편번호">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<div class="row">
								<div class="col-sm-8">
									<input type="text" class="form-control" id="address1" name="address1" value="${address1}" placeholder="주소">
								</div>
								<div class="col-sm-4">
									<input type="text" class="form-control" id="address2" name="address2" value="${address2}" placeholder="상세주소">
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<div class="checkbox">
								<label><input type="checkbox" name="emailConfirm" value="1" <c:if test="${emailConfirm}">checked</c:if>>
									이메일 수신 동의
								</label>
							</div>
						</div>
					</div>
				
				</div>
				<div class="panel-footer">
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-primary">수정</button>
							<button type="button" class="btn btn-danger" id="delete">회원 삭제</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<script>

function checkSubmit() {
	var frm = $("#formMember")[0];
	
	if (! checkMemberForm(frm))
		return false;
	
	return true;
	
}

function confirmPassword(password) {
	var frm = $("#formMember");

	if (password == '')
		return;
	
	//setTimeout(function() {
		$.ajax({
			url: 'memberPasswordCheck.do', 
			type: 'post',
			data: {
				account : $("#account").val(),
				password : password,
			}, 
			success: function(data, status) {
				if (data.result == "success") {
					frm.attr("action", "adminMemberDelete.do");
					frm.submit();
				}
				else {
					openModalError("에러", data.reason);
				}
	        }
	    })
	//}, 1000);
}


$("#delete").on("click", function(event) {
	var frm = $("#formMember");

	openModalInput1("비밀번호 확인", "비밀번호", confirmPassword);

	// after Id is check, do this.
	/*
	frm.attr("action", "memberDelete.do");
	frm.submit();	
	*/
})



</script>

