<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container">
	
	<div class="col-md-10 col-md-offset-1">
	
		<c:forEach items="${list}" var="dto">
	
			<div class="panel panel-primary">
	  			<div class="panel-heading">
	  				<div class="text-center">
	  					<h4>${dto.typeDesc}룸</h4>
	  				</div>
	  			</div>
	  		
	  			<div class="panel-body">
					<div class="row">
						<div class="col-sm-8">
							<div id="myCarousel_${dto.id}" class="carousel slide" data-ride="carousel">
								
								
								<!-- Indicators -->
								<ol class="carousel-indicators">
									<c:forEach items="${dto.files}" var="file" begin="0" varStatus="var">
										<c:if test="${var.index == '0'}">
											<li data-target="#myCarousel_${dto.id}" data-slide-to="${var.index}" class="active"></li>
										</c:if>
										<c:if test="${var.index != '0'}">
											<li data-target="#myCarousel_${dto.id}" data-slide-to="${var.index}"></li>
										</c:if>
									</c:forEach>
								</ol>
					
								<!-- Wrapper for slides -->
								<div class="carousel-inner" role="listbox">
									<c:forEach items="${dto.files}" var="file" begin="0" varStatus="var">
										<c:if test="${var.index == '0'}">
											<div class="item active">
												<img src="${pageContext.request.contextPath}/images/room/${file.filename}" alt="Image">
													<div class="carousel-caption">
														<h3>${dto.typeDesc}룸</h3>
														<p>${dto.maxPax}명/${dto.cost}원</p>
													</div>      
											</div>
										</c:if>
										<c:if test="${var.index != '0'}">
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
								<a class="left carousel-control" href="#myCarousel_${dto.id}" role="button" data-slide="prev">
									<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
									<span class="sr-only">Previous</span>
								</a>
								<a class="right carousel-control" href="#myCarousel_${dto.id}" role="button" data-slide="next">
									<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
									<span class="sr-only">Next</span>
								</a>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="well">
								<p>방유형: ${dto.typeDesc}</p>
							</div>
							<div class="well">
								<p>최대인원: ${dto.maxPax}</p>
							</div>
							<div class="well">
								<p>가격: ${dto.cost}원</p>
							</div>
							
							
						</div>
					</div>
				
				
				</div>
			</div>
		</c:forEach>
	</div>
</div>
