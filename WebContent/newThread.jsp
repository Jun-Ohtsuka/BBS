<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href = "./css/style.css" rel = "stylesheet" type = "text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
</head>
<body>
<div id = "container">
<div class = "main-content">
<div class = "header">
	<a href = "./">戻る</a>
</div><!-- header -->
<div class = "main-content">
<h1>新規投稿作成</h1>

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

<form action = "newThread" method = "get">
<c:if test = "${inputCategory == 0 }" >
	<input type = "submit" name = "submit" value = "自由入力" />
</c:if>
<c:if test = "${inputCategory == 1 }">
	<input type = "submit" name = "submit" value = "一覧から選択" />
</c:if>
</form>

<form action = "newThread" method = "post"><br>
<div class = "category">
<c:if test = "${inputCategory == 0 }" >
	<input type = "hidden" name = "inputCategory" value = "0" />
	<label>カテゴリー一覧：</label>
	<select name = "category" id = "category" >
		<c:forEach items = "${categorys }" var="category">
			<option value = <c:out value = "${category }" /> <c:if test= "${message.category == category }" >selected</c:if>>
				<c:out value = "${category }" />
			</option>
		</c:forEach>
	</select><br>
</c:if>
<c:if test = "${inputCategory == 1 }">
	<input type = "hidden" name = "inputCategory" value = "1" />
	<label for = "category">カテゴリー自由入力 ：</label>
	<input type = "text" name = "category" id = "category" /><br>
</c:if>
</div>
	<label for = "title">件名 ：</label>
	<input type = "text" name = "title" id = "title" /><br>
	<label for = "text">本文 ：</label>
	<textarea name = "text" cols = "100" rows = "5" class = "text-box"></textarea><br>

	<input type = "submit" value = "投稿" /><br>
</form>
<br>
</div><!-- main -->
<div id = "footer"><div class = "copyRight">Copyright(c) Ohtsuka Jun</div></div>
</div>
</body>
</html>