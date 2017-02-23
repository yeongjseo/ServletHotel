<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="container">

	<div class="row">
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>
	
			<div class="carousel-inner" role="listbox">
				<div class="item active">
					<img src="${pageContext.request.contextPath}/images/company/hotel1.jpg" alt="Image">
					<div class="carousel-caption">
					</div>      
				</div>
				<div class="item">
					<img src="${pageContext.request.contextPath}/images/company/hotel2.jpg" alt="Image">
					<div class="carousel-caption">
					</div>      
				</div>
				<div class="item">
					<img src="${pageContext.request.contextPath}/images/company/hotel3.jpg" alt="Image">
					<div class="carousel-caption">
					</div>      
				</div>
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

</div>
	