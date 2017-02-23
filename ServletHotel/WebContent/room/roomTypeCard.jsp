<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">

	<div class="container-fluid text-center bg-grey">
		
		<div class="row text-center slideanim">
			<c:forEach items="${roomTypeDTOList}" var="dto" begin="0" end="2" varStatus="status">
				<div class="col-sm-4">
					<div class="thumbnail" data-toggle="modal" data-target="#lightbox">
						<c:forEach items="${dto.files}" var="file" begin="0" end="0" varStatus="status">
							<img class="img-responsive img-rounded" src="${pageContext.request.contextPath}/images/room/${file.filename}" alt="Image">
						</c:forEach>
						<p><strong>${dto.typeDesc}룸</strong></p>
						<p>최대 ${dto.maxPax}명</p>
						<p>${dto.cost}원</p>
					</div>
				</div>
			</c:forEach>
		</div>
			
		<div class="row text-center slideanim">		
			<c:forEach items="${roomTypeDTOList}" var="dto" begin="2" end="${roomTypeDTOList.size()}" varStatus="status">
				<div class="col-sm-4" data-toggle="modal" data-target="#lightbox">
					<div class="thumbnail">
						<c:forEach items="${dto.files}" var="file" begin="0" end="0" varStatus="status">
							<img class="img-responsive img-rounded" src="${pageContext.request.contextPath}/images/room/${file.filename}" alt="Image">
						</c:forEach>
						
						<p><strong>${dto.typeDesc}룸</strong></p>
						<p>최대 ${dto.maxPax}명</p>
						<p>${dto.cost}원</p>
					</div>
				</div>
			</c:forEach>
		</div>

		<div id="lightbox" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<button type="button" class="close hidden" data-dismiss="modal" aria-hidden="true">×</button>
				<div class="modal-content">
					<div class="modal-body"><img src="" alt="" /></div>
				</div>
			</div>
		</div>

		<br>
	</div>
	
</div>

<script>
var $lightbox = $('#lightbox');

$('[data-target="#lightbox"]').on('click', function(event) {
	var $img = $(this).find('img'), 
	src = $img.attr('src'),
	alt = $img.attr('alt'),
	css = {
		'maxWidth': $(window).width() - 100,
		'maxHeight': $(window).height() - 100
	};

	$lightbox.find('.close').addClass('hidden');
	$lightbox.find('img').attr('src', src);
	$lightbox.find('img').attr('alt', alt);
	$lightbox.find('img').css(css);
});

$lightbox.on('shown.bs.modal', function (e) {
	var $img = $lightbox.find('img');
        
	$lightbox.find('.modal-dialog').css({'width': $img.width()});
	$lightbox.find('.close').removeClass('hidden');
});


</script>
	