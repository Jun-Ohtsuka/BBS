<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href = "./css/style.css" rel = "stylesheet" type = "text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー登録</title>
</head>
<body>
<div id = "container">
<div class = "header">
	<a href = "userManagement">戻る</a>
</div><!-- header -->
<div class = "main-content">
<h1>ユーザー登録</h1>

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
<div id = "signup-form">
<form action = "signup" method = "post" enctype = "multepart/form-date"><br>
	<label class = "form" for = "name">名前：</label>
	<input name = "name" value = "${editUser.name }" id = "name" /><br>

	<label class = "form" for = "account">ログインID：</label>
	<input name = "account" value = "${editUser.account }"  id = "account" /><br>

	<label class = "form" for = "password">パスワード：</label>
	<input name = "password" type = "password" id = "password" /><br>
	<label class = "form" for = "checkPassword">パスワード確認用：</label>
	<input name = "checkPassword" type = "password" id = "checkPassword" /><br>

	<label class = "form" for = "branch">所属している支店：</label>
	<select name = "branch" id = "branch" >
		<c:forEach items = "${branchs }" var="branch" varStatus = "status">
			<option <c:if test= "${editUser.branchId == status.count }" >selected</c:if> value = <c:out value = "${status.count }" />><c:out value = "${branch }" /></option>
		</c:forEach>
	</select><br>

	<label class = "form" for = "position">部署・役職：</label>
		<select name = "position" id = "position" >
			<c:forEach items = "${positions }" var="position" varStatus = "status">
				<option <c:if test= "${editUser.positionId == status.count }" >selected</c:if> value = <c:out value = "${status.count }" />><c:out value = "${position }" /></option>
			</c:forEach>
		</select><br>

	<input type = "submit" value = "登録" /><br>
</form>
</div>

</div><!-- main -->
<div id = "footer"><div class = "copyRight">Copyright(c) Ohtsuka Jun</div></div>
</div>
</body>
</html>