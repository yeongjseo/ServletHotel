<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container">

	<%-- 
   	<div class="form-center">
	<form class="form-inline">
   		<select name="searchKey" class="form-control">
  			<option value="" selected="selected">전체검색</option>
 	   	  	<option value="writer" <c:if test="${searchKey eq 'writer'}"> selected="selected" </c:if>>작성자검색 </option>
 	   	  	<option value="title" <c:if test="${searchKey eq 'title'}"> selected="selected" </c:if>>제목검색</option>
 	   	  	<option value="content" <c:if test="${searchKey eq 'content'}"> selected="selected" </c:if>>내용검색</option>
   		</select>
   		<input type="text"  class="form-control" id="searchVal" name="searchVal" value="${searchVal}">
   		<input type="button" class="form-control" id="search" value="검색">
   	</form>
   	</div> 
   	--%>
	<h2 class="text-center">룸 타입 리스트</h2><br><br>

	<table class="table table-striped table-hover table-responsive">
		<tr>
			<th style="width:30px">번호</th>
			<th>유형</th>
			<th>인원</th>
			<th>요금</th>
		</tr>
	
		<c:forEach items="${list}" var="dto">
			<tr>
				<td><a href='roomDetail.do&id=${dto.id}'>${dto.id}</a></td>
				<td>${dto.typeDesc}</td>
				<td>${dto.maxPax}</td>
				<td>${dto.cost}</td>
			</tr>
			<tr>
				<td colspan="4">
					<c:forEach items="${dto.files}" var="file">
						<img src="${pageContext.request.contextPath}/images/room/${file.filename}" class="img-rounded">
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
	</table>


	<div class="text-center">
		<ul class="pagination">
			<c:if test="${pageStart > pageMax}">
				<li><a href="boardList.do?pageNum=${pageStart - pageMax}&searchKey=${searchKey}&searchVal=${searchVal}">&laquo;</a></li>
			</c:if>
	
			<c:forEach begin="${pageStart}" end="${pageEnd}" var="i">
				<li
					<c:out value="${pageNum == i ? 'class=active' : ''}"/>>
					<a href="boardList.do?pageNum=${i}&searchKey=${searchKey}&searchVal=${searchVal}" class="page" >${i}</a>
				</li>
			</c:forEach>
	
			<c:if test="${pageEnd < pageCount}">
				<li><a href="boardList.do?pageNum=${pageStart + pageMax}&searchKey=${searchKey}&searchVal=${searchVal}" class="page" >&raquo;</a></li>
			</c:if>
		</ul>
	</div>


</div>

<script>

$('#search').on("click", 
	function(event) {
		self.location = "roomTypeList.do" + 
						// "?pageNum=" + ${pageNum} + 
						// when first search, pageNum must be 1 or none.
						"?pageNum=1" +		
						"&searchKey=" + $("select option:selected").val() + 
						"&searchVal=" + $('#searchVal').val();
	});

</script>


