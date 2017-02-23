<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container">

	<div class="panel panel-primary">
  		<div class="panel-heading">
  			<div class="text-center">
  				<h4>룸 리스트 관리</h4>
  			</div>
  		</div>
  		
  		<div class="panel-body">
		   	<div class="row">
		   		<div class="col-sm-6">
	   				<button type="button" class="btn btn-primary" id="write">룸 만들기</button>
		   		</div>
		   	
				<div class="col-sm-6" >
					<form class="form-inline pull-right">
				   		<select name="searchKey" class="form-control">
				  			<option value="" selected="selected">전체검색</option>
				 	   	  	<option value="writer" <c:if test="${searchKey eq 'writer'}"> selected="selected" </c:if>>작성자검색 </option>
				 	   	  	<option value="title" <c:if test="${searchKey eq 'title'}"> selected="selected" </c:if>>제목검색</option>
				 	   	  	<option value="content" <c:if test="${searchKey eq 'content'}"> selected="selected" </c:if>>내용검색</option>
				 	   	 
				   		</select>
				   	
				   	
			   			<input type="text"  class="form-control" id="searchVal" name="searchVal" value="${searchVal}">
	
			   			<input type="button" class="form-control btn btn-warning" id="search" value="검색">
	
			 	  	</form>
				</div>
			</div>
	 
		
			<table class="table table-striped table-hover table-responsive">
				<tr>
					<th style="width:30px">번호</th>
					<th>ID</th>
					<th>호수</th>
					<th>룸타입</th>
					<th>최대인원</th>
					<th>가격</th>
				</tr>
			
				
				<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.rowNum}</td>
						<td><a href='#'>${dto.id}</a></td>
						<td>${dto.roomNum}</td>
						<c:if test="${dto.roomTypeDTO != null}">
							<td>${dto.roomTypeDTO.typeDesc}</td>
							<td>${dto.roomTypeDTO.maxPax}명</td>
							<td>${dto.roomTypeDTO.cost}원</td>
						</c:if>
						<c:if test="${dto.roomTypeDTO == null}">
							<td>-</td>
							<td>-</td>
							<td>-</td>
						</c:if>
					</tr>
			
				</c:forEach>
			</table>
		</div>
	
		<div class="panel-footer">
			<div class="text-center">
				<ul class="pagination">
					<c:if test="${pageStart > pageMax}">
						<li><a href="adminRoomList.do?pageNum=${pageStart - pageMax}&searchKey=${searchKey}&searchVal=${searchVal}">&laquo;</a></li>
					</c:if>
			
					<c:forEach begin="${pageStart}" end="${pageEnd}" var="i">
						<li
							<c:out value="${pageNum == i ? 'class=active' : ''}"/>>
							<a href="adminRoomList.do?pageNum=${i}&searchKey=${searchKey}&searchVal=${searchVal}" class="page" >${i}</a>
						</li>
					</c:forEach>
			
					<c:if test="${pageEnd < pageCount}">
						<li><a href="adminRoomList.do?pageNum=${pageStart + pageMax}&searchKey=${searchKey}&searchVal=${searchVal}" class="page" >&raquo;</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>

</div>

<script>

$('#search').on("click", 
	function(event) {
		self.location = "adminRoomList.do" + 
						// "?pageNum=" + ${pageNum} + 
						// when first search, pageNum must be 1 or none.
						"?pageNum=1" +		
						"&searchKey=" + $("select option:selected").val() + 
						"&searchVal=" + $('#searchVal').val();
	});

</script>


