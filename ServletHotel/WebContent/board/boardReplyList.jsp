<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

				
<div class="box box-primary">
	<div class="box-header">
		<div class="text-center">
			<h4>댓글작성</h4>
		</div>
	</div>
	
	<c:if test="${loginDTO != null}">
		<div class="box-body">
			<form class="form-horizontal" method="post">
				<div class="form-group">
					<label class="control-label col-sm-2" for="account">작성자:</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="replyWriter" name="replyWriter" 
							placeholder="작성자" value="${loginDTO.account}" readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="account">내용:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="replyContent" name="replyContent" 
							placeholder="내용">
					</div>
				</div>
			</form>
		</div>
		<div class="box-footer">
			<div class="col-sm-2 col-sm-offset-10">
				<button type="submit" class="btn btn-primary" id="addReply">댓글 달기</button>
			</div>
		</div>
	</c:if>
	<c:if test="${loginDTO == null}">
		<div class="box-body">
			<div class="col-sm-offset-10 col-sm-2 ">
				<button type="button" class="btn btn-primary" id="loginReply">로그인</button>
			</div>
		</div>
	</c:if>
</div>

<ul class="timeline" id="replyList">
    <li class="time-label">
        <span class="bg-red">
            댓글리스트 <small id='replyCount'> [${replyCount}] </small>
        </span>
    </li>
</ul>

<button type="button" class="btn btn-primary" id="nextReply">다음 댓글</button>


<div id="modalModifyReply" class="modal modal-primary fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body" data-rno>
				<p>
					<input type="text" id="replyContent" class="form-control">
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" id="updateReply">수정</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>

<script id="templateReplyItem" type="text/x-handlebars-template">
	{{#each .}}
		<li class="replyItem" data-replyId={{replyId}}>
			<i class="fa fa-comments bg-blue"></i>
			<div class="timeline-item" >
				<span class="time">
					<i class="fa fa-clock-o">{{writeTime}}</i>
				</span>
                <h2 class="timeline-header"><strong>{{rowNum}}</strong>-{{account}}</h2>
                <div class="timeline-body">{{content}}</div>
				<div class="timeline-footer">
					{{#checkWriter account}}
                		<button class="btn btn-warning btn-xs" data-toggle="modal" id="modifyReply">수정</button>
						<button class="btn btn-danger btn-xs" data-toggle="modal" id="deleteReply">삭제</button>
					{{/checkWriter}}
				</div>
	        </div>
    	</li>
	{{/each}}
</script>



<script>
var pageNum = 1;

Handlebars.registerHelper("checkWriter", function(writer, block) {
	var accum = '';
	if (writer == '${loginDTO.account}') {
		accum += block.fn();
	}
	return accum;
});

function getReplyPage(page) {
	pageNum = page;
	var url = "boardReplyList.do?boardId=${boardId}&pageNum=" + pageNum;
	$.getJSON(url, function (data) {
		var template = Handlebars.compile($('#templateReplyItem').html());
		var html = template(data.list);
		
		$("#replyList li:last").after(html);
		
		$("#replyCount").html("[ " + data.replyCount + "개 ]");
		
		console.log("replycount: " + data.replyCount);
		console.log("replyList: " + $("#replyList li").length);
		if (data.replyCount > ($("#replyList li").length - 1))
			$("#nextReply").show();
		else
			$("#nextReply").hide();
	})
	
	
}

$("#addReply").on("click", function() {
	if ($("#replyWriter").val() == "") {
		openMessageInfo("알림", "작성자 이름을 입력하세요.");
		return;
	}

	if ($("#replyContent").val() == "") {
		openMessageInfo("알림", "댓글 내용을 입력하세요.");
		return;
	}

	$.ajax({
		type : "post",
		url : 'boardReplyInsert.do',
		headers : {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "POST"
		},
		data : JSON.stringify({
			boardId : '${boardId}',
			replyWriter : $("#replyWriter").val(),
			replyPassword : $("#replyPassword").val(),
			replyContent : $("#replyContent").val()
		}),
		success : function(data, status) {
			xxx = data;
			console.log(data);
			console.log(data.result);
			if (data.result == "success") {
				$(".replyItem").remove();
				getReplyPage("1");
			} else {
				openModalError("에러", "댓글 달기를 실패했습니다.");
			}
		}
	});
	$("#replyContent").val("");
	
});

$("#nextReply").on("click", function() {
	getReplyPage(++pageNum);
	
});

$("#loginReply").on("click", function() {
	location.href = "memberLogin.do";
	
});

$("#replyList").on("click", "#modifyReply", function() {
	
	replyId = $(this).closest(".replyItem").attr("data-replyId");
	content = $(this).parents(".replyItem").find(".timeline-body").text()
	console.log(replyId + ", " + content);
	
	$("#modalModifyReply .modal-title").html(replyId);
	$("#modalModifyReply #replyContent").val(content);
	
	$("#modalModifyReply").modal();
	
});

$("#updateReply").on("click", function() {
	
	id = $("#modalModifyReply .modal-title").html();
	content = $("#modalModifyReply #replyContent").val();
	
	$("#modalModifyReply").modal('hide');
	$("#modalModifyReply .modal-title").html("");
	$("#modalModifyReply #replyContent").val("");
	
	$.ajax({
		type : "post",
		url : 'boardReplyUpdate.do',
		headers : {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "POST"
		},
		data : JSON.stringify({
			replyId : id,
			replyContent : content,
		}),
		success : function(data, status) {
			if (data.result == "success") {
				$(".replyItem").remove();
				getReplyPage("1");
			} else {
				openModalError("에러", data.reason);
			}
		}
	});
});


$("#replyList").on("click", "#deleteReply", function() {
	replyId = $(this).closest(".replyItem").attr("data-replyId");
	
	$.ajax({
		type : "post",
		url : 'boardReplyDelete.do',
		headers : {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "POST"
		},
		data : JSON.stringify({
			replyId : replyId,
		}),
		success : function(data, status) {
			if (data.result == "success") {
				$(".replyItem").remove();
				getReplyPage("1");
			} else {
				openModalError("에러", data.reason);
			}
		}
	});
});



$(function() {
	getReplyPage(pageNum);
});
	
	


</script>