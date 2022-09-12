<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
	
	<h1>${principal.id}번째 ${principal.username}님정말탈퇴하시겠습니까?</h1>
	<div class="d-flex justify-content-start">
		<form action="/users/${principal.id}/delete" method="post">
			<button type="submit" class="btn btn-danger">예</button>
		</form>
		
		<form action="/" method="get">
			<button type="submit" class="btn btn-warning">아니오</button>
		</form>
	</div>

</div>

<%@ include file="../layout/footer.jsp"%>
