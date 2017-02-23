<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/common/include.jspf" %>


<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#myNavbar">
				<span class="sr-only">토글</span>
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.do">심플호텔</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">소개<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="companyIntro.do">소개</a></li>
						<li><a href="companyMap.do">위치</a></li>
						<li><a href="boardList.do?boardType=notice">공지사항</a></li>
						<li><a href="boardList.do?boardType=event">이벤트</a></li>
						
					</ul>
				</li>
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">룸/예약<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="roomTypeShow.do">룸소개</a></li>
						<li><a href="reserveSearch.do">예약</a></li>
					</ul>
				</li>
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">고객지원<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">자주하는 질문</a></li>
						<li><a href="companyContact.do">문의하기</a></li>
					</ul>
				</li>
				<li><a href="boardList.do?boardType=guest">방명록</a></li>
			</ul>
		 	<form class="nav navbar-nav navbar-right navbar-form">
				<c:if test="${loginDTO == null}">
					<div class="form-group">
						<input type="text" name="account" id="account" class="form-control"  placeholder="아이디"> 
						<input type="text" name="password" id="password" class="form-control" placeholder="비밀번호">
						<button id="login" type="button" class="btn btn-default navbar-btn">로그인</button>
					</div>
						
					<ul class="nav navbar-nav navbar-right">
						<li><a href="memberJoin.do"><span class="glyphicon glyphicon-user"></span>회원가입</a></li>
					</ul>
				</c:if>
				<c:if test="${loginDTO != null}"> 				
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">${loginDTO.account}<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="memberDetail.do">회원정보</a></li>	
							<li><a href="memberReserveDetail.do">예약정보</a></li>
						</ul>
					</li>
				
					<c:if test="${loginDTO.isAdmin}">
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#">관리자메뉴<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="adminMemberList.do">회원관리</a></li>
								<li><a href="#">문의관리</a></li>
								<li><a href="adminReserveList.do">예약관리</a></li>
								<li><a href="adminRoomList.do">룸관리</a></li>
								<li><a href="adminRoomReserveList.do">룸별예약관리</a></li>
							</ul>
						</li>
					</c:if>
					<li><a href="memberLogout.do"><span class="glyphicon glyphicon-log-out"></span>로그아웃</a></li>
				</c:if>
			</form>
		</div>
	</div>
</nav>



<script>


$('#login').click(function() {
	if ($('#account').val() == '') {
		openModalInfo("확인", "아이디를 입력하시오");
		return;
	}
	
	if ($('#password').val() == '') {
		openModalInfo("확인", "비밀번호를 입력하시오");
		return;
	}
	
	$.post('./memberLogin.do', 
		{
       		account: $('#account').val(),
       		password: $('#password').val(),
        },
        function(data, status) {
			if (data.result == "success") {
				<c:if test="${lastPage != null}">
					<c:if test="${lastPage.contains('memberLogin.do')}">
						self.location = "index.do";
					</c:if>
					<c:if test="${! lastPage.contains('memberLogin.do')}">
						self.location = '${lastPage}';
					</c:if>
				</c:if>
				<c:if test="${lastPage == null}">
					self.location = "index.do";
				</c:if>
			}
			else {
				openModalError("로그인 실패", data.reason);
			}
        }
	);
})


</script>
