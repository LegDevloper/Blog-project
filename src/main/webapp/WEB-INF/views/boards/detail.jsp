<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br /> <br />
	<div>
		<h3>${boardsDetail.title}</h3>
	</div>
	<hr/>

	<div>${boardsDetail.content}</div>


</div>

<%@ include file="../layout/footer.jsp"%>