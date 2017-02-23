<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">


	<div class="panel panel-primary">
  		<div class="panel-heading">
  			<div class="text-center">
  				<h4>멤버 리스트</h4>
  			</div>
  		</div>
	
		<div class="panel-body">
		   	<div class="centered">
			<form class="form-inline">
		   		<select name="searchKey" class="form-control">
		  			<option value="" selected="selected">전체검색</option>
		 	   	  	<option value="account" <c:if test="${searchKey eq 'account'}"> selected="selected" </c:if>>계정명검색 </option>
		 	   	  	<option value="nickname" <c:if test="${searchKey eq 'nickname'}"> selected="selected" </c:if>>사용자검색</option>
		 	   	  	<option value="tel" <c:if test="${searchKey eq 'tel'}"> selected="selected" </c:if>>연락처검색</option>
		 	   	  	<option value="address1" <c:if test="${searchKey eq 'tel'}"> selected="selected" </c:if>>주소검색</option>
		 	   	  	<option value="email" <c:if test="${searchKey eq 'tel'}"> selected="selected" </c:if>>이메일</option>
		   		</select>
		   		<input type="text"  class="form-control" id="searchVal" name="searchVal" value="${searchVal}">
		   		<input type="button" class="form-control" id="search" value="검색">
		   	</form>
		   	</div>
		   	
			<table class="table table-striped table-hover table-responsive">
				<tr>
					<th>번호</th>
					<th>고유번호</th>
					<th>계정명</th>
					<th>비밀번호</th>
					<th>사용자명</th>
					<th>생일</th>
					<th>우편번호</th>
					<th>주소</th>
					<th>상세주소</th>
					<th>이메일</th>
					<th>연락처</th>
					<th>삭제</th>
				</tr>
			
				<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.rowNum}</td>
						<td>${dto.id}</td>
						<td><a href='adminMemberDetail.do?id=${dto.id}'>${dto.account}</a></td>
						<td>${dto.password}</td>
						<td>${dto.nickname}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${dto.birthday}"/></td>
						<td>${dto.zipcode}</td>
						<td>${dto.address1}</td>
						<td>${dto.address2}</td>
						<td>${dto.email}</td>
						<td>${dto.tel}</td>
						<td><button type="button" class="btn btn-danger btn-xs delete" data-id="${dto.id}">삭제</button></td>
					</tr>
			
				</c:forEach>
			</table>
		</div>
		
		 <div class="panel-footer">
			<div class="text-center">
				<ul class="pagination">
					<c:if test="${pageStart > pageMax}">
						<li><a href="adminMemberList.do?pageNum=${pageStart - pageMax}&searchKey=${searchKey}&searchVal=${searchVal}&auth=${auth}">&laquo;</a></li>
					</c:if>
			
					<c:forEach begin="${pageStart}" end="${pageEnd}" var="i">
						<li
							<c:out value="${pageNum == i ? 'class=active' : ''}"/>>
							<a href="adminMemberList.do?pageNum=${i}&searchKey=${searchKey}&searchVal=${searchVal}&auth=${auth}">${i}</a>
						</li>
					</c:forEach>
			
					<c:if test="${pageEnd < pageCount}">
						<li><a href="adminMemberList.do?pageNum=${pageStart + pageMax}&searchKey=${searchKey}&searchVal=${searchVal}&auth=${auth}">&raquo;</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
		

</div>

<script>
$('#search').on("click", 
	function(ev) {
		self.location = "adminMemberList.do" + 
						// "?pageNum=" + ${pageNum} +
						// when first search, pageNum must be 1 or none.
						"?pageNum=1" +		
						"&searchKey=" + $("select option:selected").val() + 
						"&searchVal=" + $('#searchVal').val();
	});

$('.delete').on("click",
	function(ev) {
		/*// get method
		self.location = "memberDelete.do?id=" + $(this).attr("data-id");
		//*/
		
		/// post method
		var frm = new commonForm();
		frm.setUrl("adminMemberDelete.do");
		frm.addParam("id", $(this).attr("data-id"));
		frm.submit();
		//*/
	});

				
</script>


