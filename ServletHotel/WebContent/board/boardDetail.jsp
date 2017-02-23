<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="readonly" value="readonly"/>
<c:if test="${loginDTO != null}">
	<c:if test="${loginDTO.id == memberId}">
		<c:set var="readonly" value=""/>
	</c:if>
	<c:if test="${loginDTO.id == 1}">
		<c:set var="readonly" value=""/>
	</c:if>
</c:if>


<div class="container">
 	<div class="col-sm-10 col-sm-offset-1">
 		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="text-center">
					<h4><c:out value="${boardDTO.getBoardTypeLongDesc(boardType)}"/></h4>
				</div>
			</div>
				
			<div class="panel-body">
			
				<form class="form-horizontal" method="post" action="boardDetail.do" name="form1" id="form1" enctype="multipart/form-data">
					<input type="hidden" name="boardId" id="boardId" value="${boardId}">
					<input type="hidden" name="boardType" id="boardType" value="${boardType}">
					<input type="hidden" name="pageNum" id="pageNum" value="${pageNum}">
					<input type="hidden" name="searchKey" id="searchKey" value="${searchKey}">
					<input type="hidden" name="searchVal" id="searchVal" value="${searchVal}">
					
					<div class="form-group">
						<label class="control-label col-sm-2" for="account">작성자:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="account" name="account" value="${account}" placeholder="계정명" readonly>
						</div>
						
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-2" for="title">작성일:</label>
						<label class="control-label col-sm-4 pull-left">
							<span class="label label-primary lable-lg pull-left">
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${writeDate}"/>
							</span>
						</label>
						<label class="control-label col-sm-2" for="title">조회수:</label>
						<label class="control-label col-sm-4">
							<span class="badge bg-red pull-left">${readCount}</span>
						</label>
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-2" for="title">제목:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="title" name="title" value="${title}" <c:out value="${readonly}"/> placeholder="제목">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="content">내용:</label>
						<div class="col-sm-10">
							<textarea class="form-control" id="content" name="content" placeholder="내용" <c:out value="${readonly}"/> rows="10">${content}</textarea>
						</div>
					</div>
					<br>
				
					<div class="form-group" id="fileDiv">
						<label class="control-label col-sm-2" for="files">첨부파일:</label>
						
						<c:forEach var="file" items="${files}" begin="0" varStatus="var">
						<div>
							<input type="hidden" id="fileId_${file.id}" name="fileId_${file.id}" value="${file.id}">
							<c:if test="${var.index == '0'}">
								<div class="col-sm-5">
									<button type="button" class="btn btn-block btn-default" id="file_${var.index}" name="file_${var.index}" 
										data-id="${file.id}" data-type="${boardType}">
										${file.filename} (${file.filesize} Byte)
									</button>
								</div>
							</c:if>
							<c:if test="${var.index != '0'}">
								<div class="col-sm-offset-2 col-sm-5">
									<button type="button" class="btn btn-block btn-default" id="file_${var.index}" name="file_${var.index}" 
										data-id="${file.id}" data-type="${boardType}">
										${file.filename} (${file.filesize} Byte)
									</button>
								</div>
							</c:if>
							<div class="col-sm-5">
								<c:if test="${readonly eq '' }">
									<button type="button" class="btn btn-danger" id="delete_${var.index}" name="delete_${var.index}">삭제</button>
								</c:if>
							</div>
						</div>
						</c:forEach>
					</div>
					
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<c:if test="${readonly eq '' }">
								<button type="button" class="btn btn-success" id="addFile">파일추가</button>
								<button type="submit" class="btn btn-warning">수정</button>
								<button type="button" class="btn btn-danger" id="delete">삭제</button>			
							</c:if>
							<button type="button"  class="btn btn-info" id="back">뒤로</button>
						</div>
					</div>
					
				</form>
				
				<div class="col-sm-offset-2 col-sm-10">
					<jsp:include page="../board/boardReplyList.jsp"/>
				</div>
			</div>
		</div>		
	</div>
</div>


<script>
var form1 = $("#form1");
var fileCnt = '${fn:length(files) + 1}';

form1.submit(function(e) {
	event.preventDefault();
	if ($("#title").val() == "") {
		openModalInfo("알림", "제목을 입력하세요.")
		return;
	}
	
	if ($("#content").val() == "") {
		openModalInfo("알림", "내용을 입력하세요.")
		return;
	}
	
	$(this).get(0).submit();
});

$('#back').on("click", function(ev) {
	self.location = "boardList.do" +
					"?boardType=" + "${boardType}" +
					"&pageNum=" + "${pageNum}" + 
					"&searchKey=" + "${searchKey}" + 
					"&searchVal=" + "${searchVal}";
		
});


$("#delete").on("click", function(event) {
	
	var frm = new commonForm(); 
	frm.setUrl("boardDelete.do");
	frm.addParam("boardType", form1.find("#boardType").val());
	frm.addParam("boardId", form1.find("#boardId").val());
	frm.addParam("pageNum", form1.find("#pageNum").val());
	frm.addParam("searchKey", form1.find("#searchkey").val());
	frm.addParam("searchVal", form1.find("#searchVal").val());
	frm.submit();
	
})

$("button[name^='file']").on("click", function(e) {
	e.preventDefault();
	
	var obj = $(this);
	var idx = obj.attr("data-id");
	var type = obj.attr("data-type");

	var frm = new commonForm();
    frm.setUrl("fileDownload.do");
    frm.addParam("type", type);
    frm.addParam("fileId", idx);
    frm.submit();
	
});


$("button[name^='delete']").on("click", function(e){
	e.preventDefault();
	var isFirst = false;
	if ($("#fileDiv button[name^=delete]").first().is($(this)))
		isFirst = true;		
	
	$(this).parent().parent().remove();
	
	if (isFirst && $("#fileDiv div > div:nth-child(2)"))
		$("#fileDiv div > div:nth-child(2)").removeClass("col-sm-offset-2")
	
});


$('#addFile').on("click", function(e) {
	var offset = '';
	if ($("#fileDiv div").length != 0)
		offset = 'col-sm-offset-2 ';
	var str = 
		"<div>" + 
			"<div class='" + offset + " col-sm-5'>" + 
				"<input type='file' class='form-control' id='file_" + fileCnt + "' name='file_" + fileCnt + "'/>" + 
			"</div>" + 
			"<div class='col-sm-5'>" + 
				"<button type='button' class='btn btn-danger' id='delete_" + fileCnt + "' name='delete_" + fileCnt + "'>삭제</button>" +
			"</div>" + 
		"</div>";
		
	$("#fileDiv").append(str);
	
	$("button[name='delete_" + (fileCnt++) + "']").on("click", function(e) {
		e.preventDefault();
		
		var isFirst = false;
		if ($("#fileDiv button[name^=delete]").first().is($(this)))
			isFirst = true;		
		
		$(this).parent().parent().remove();
		
		if (isFirst && $("#fileDiv div > div:nth-child(1)"))
			$("#fileDiv div > div:nth-child(1)").removeClass("col-sm-offset-2")
		

	});

});


</script>







