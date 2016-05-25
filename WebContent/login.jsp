<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href = "./css/style.css" rel = "stylesheet" type = "text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
</head>
<body>
<div id = "container">
<div class = "header">
	<a href = "./">戻る</a>
</div><!-- header -->
<div class = "main-content">
<h3>ログインユーザー入力</h3>

<c:if test ="${not empty errorMessages }">
	<div class = "errorMessages">
		<ul>
			<c:forEach items = "${errorMessages }" var = "message">
				<li><c:out value = "${message }" /></li>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope = "session" />
</c:if>

<form action= "login" method = "post"><br>
	<label class = "form" for = "account">アカウント：</label>
	<input name = "account" value = "${editUser.account }" id = "account" /><br>

	<label class = "form" for = "password">パスワード：</label>
	<input name = "password" type = "password" id = "password" /><br>

	<input type = "submit" value = "ログイン" /><br>
</form>
</div><!-- main -->
<div id = "footer"><div class = "copyRight">Copyright(c) Ohtsuka Jun</div></div>
</div>
</body>
</html>