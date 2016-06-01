<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href = "./css/style.css" rel = "stylesheet" type = "text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>サインイン</title>
</head>
<body>
<div id = "container">
<div class = "header">

</div><!-- header -->
<div class = "main-content">
<div class = "login">
<h3>サインインユーザー入力</h3>

<c:if test ="${not empty messages }">
	<div class = "errorMessages">
	<ul>
		<c:forEach items = "${messages }" var = "message">
			<li><c:out value = "${message }" /></li>
		</c:forEach>
	</ul>
	</div>
	<c:remove var="messages" scope = "session" />
</c:if>

<form action= "signin" method = "post"><br>
	<label class = "form" for = "account">サインインID</label>
	<input name = "account" value = "${editUser.account }" id = "account" /><br>

	<label class = "form" for = "password">パスワード</label>
	<input name = "password" type = "password" id = "password" /><br>

	<input class = "submit" type = "submit" value = "サインイン" /><br>
</form>
</div>
</div><!-- main -->
<div id = "footer"><div class = "copyRight">Copyright(c) Ohtsuka Jun</div></div>
</div>
</body>
</html>