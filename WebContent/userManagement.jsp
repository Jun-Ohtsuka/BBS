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
<title>ユーザー管理</title>
</head>
<body>
<div id = "container">
<div class = "header">
	<a href = "signup">ユーザー新規登録</a>
	<a href = "./">HOME</a>
</div><!-- header -->
<div class = "main-content">

<h1>ユーザー一覧</h1>
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

<!-- ユーザーの一覧表示 -->
<div class = "users">
	<table class = "userManagement" border="1" cellspacing="0">
		<tr>
			<th>サインインID</th>
			<th>名前</th>
			<th>所属</th>
			<th>部署・役職</th>
			<th>状態</th>
			<th>状態編集</th>
			<th>ユーザー編集</th>
		</tr>
		<c:forEach items = "${users }" var = "user">
		<tr>
				<td><c:out value = "${user.account }" /></td>
				<td><c:out value = "${user.name }" /></td>
				<td><c:out value = "${user.branchName }" /></td>
				<td><c:out value = "${user.positionName }" /></td>

			<form action = "userManagement" method = "POST">
			<input type='hidden' name = "id" value="${user.id}" />
			<c:choose>
			<c:when test = "${user.freeze == 0 }">
				<td class = "freeze">利用可能</td>
				<td class = "td_submit"><c:if test = "${user.id != loginUser.id }">
					<input id = "submit_button" type = "submit" name = "submit" value = "停止"
						onclick = 'return confirm("[${user.account}：${user.name}]を停止します。\nよろしいですか？");' />
				</c:if></td>
			</c:when>
			<c:when test = "${user.freeze == 1 }">
				<td class = "freeze"><div class = "stop">停止中</td>
				<td class = "td_submit"><input id = "submit_button" type = "submit" name = "submit" value = "解除"
				onclick = 'return confirm("[${user.account}：${user.name}]の停止を解除します。\nよろしいですか？");' /></td>
			</c:when>
			</c:choose>
			</form>
			<form action = "setting" method = "GET">
			<input type = "hidden" name = "id" value="${user.id}" />
				<td><input class = "td_submit" id = "submit_button" type = "submit" value = "編集する" /></td>
			</form>
		</tr>
		</c:forEach>
	</table>
</div>

</div><!-- main -->
<div id = "footer"><div class = "copyRight">Copyright(c) Ohtsuka Jun</div></div>
</div>
</body>
</html>