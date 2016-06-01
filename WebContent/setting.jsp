<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href = "./css/style.css" rel = "stylesheet" type = "text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー情報の編集</title>
</head>
<body>
<div id = "container">
<div class = "header">
	<a href = "userManagement">戻る</a>
</div><!-- header -->
<div class = "main-content">

<h1>ユーザー情報編集</h1>

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
<div class = "setting">
<form action = "setting" method = "post" enctype = "multepart/form-date"><br>
	<input type = "hidden" name = "id" value = "${editedUser.id }" />
	<label for = "name">名前</label>
	<input name = "name" value = "${editedUser.name }" id = "name" /><br>

	<label for = "account">アカウント名</label>
	<input name = "account" value = "${editedUser.account }" /><br>

	<label for = "password">パスワード</label>
	<input name = "password" type = "password" id = "editPassword" /><br>
	<label for = "checkPassword">パスワード確認用</label>
	<input name = "checkPassword" type = "password" id = "editPassword" /><br>

	<label for = "branch">所属している支店</label>
	<select name = "branch" id = "branch" >
		<c:forEach items = "${branchs }" var="branch" varStatus = "status">
			<option <c:if test= "${editedUser.branchId == status.count }" >selected</c:if> value = <c:out value = "${status.count }" />><c:out value = "${branch }" /></option>
		</c:forEach>
	</select><br>

	<label for = "position">部署・役職</label>
		<select name = "position" id = "position" >
			<c:forEach items = "${positions }" var="position" varStatus = "status">
				<option <c:if test= "${editedUser.positionId == status.count }" >selected</c:if> value = <c:out value = "${status.count }" />><c:out value = "${position }" /></option>
			</c:forEach>
		</select><br>

	<input class = "submit" type = "submit" value = "登録" /><br>
</form>
</div>
</div><!-- main -->
<div id = "footer"><div class = "copyRight">Copyright(c) Ohtsuka Jun</div></div>
</div>
</body>
</html>