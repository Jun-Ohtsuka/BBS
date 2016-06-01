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

<div class = "header">
<div class = "ScreenTransition">
	<a href = "./">戻る</a>
</div>
</div><!-- header -->

<div class = "main-content">
<div class = "newThread">
<h1>新規投稿作成</h1>

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

<label id = "categoryTitle">カテゴリー選択方式：</label>
<form action = "newThread" method = "get" id = "changeCategory">
<c:if test = "${inputCategory == 0 }" >
	<input class = "submit" type = "submit" name = "submit" value = "自由入力" />
</c:if>
<c:if test = "${inputCategory == 1 }">
	<input class = "submit" type = "submit" name = "submit" value = "一覧から選択" />
</c:if>
</form>

<form action = "newThread" method = "post" id = "newThread">
<div class = "category">
<c:if test = "${inputCategory == 0 }" >
	<input type = "hidden" name = "inputCategory" value = "0" />
	<label>カテゴリー一覧 ：</label>
	<select name = "category" id = "category" >
		<c:forEach items = "${categorys }" var="category">
			<option value = <c:out value = "${category }" /> <c:if test= "${editThread.category == category }" >selected</c:if>>
				<c:out value = "${category }" />
			</option>
		</c:forEach>
	</select><br>
</c:if>
<c:if test = "${inputCategory == 1 }">
	<input type = "hidden" name = "inputCategory" value = "1" />
	<label for = "category">カテゴリー自由入力 ：＜10文字以内で入力してください＞</label>
	<input type="text" name="category" value = "${editThread.category }"/><br>
</c:if>
</div>
<div id = "title-text">
	<label for = "title">件名 ：＜50文字以内で入力してください＞</label>
	<input id = "text-box" type="text" size = "50" name="title" value="${editThread.title }" /><br>
	<label for = "text">本文 ：＜1000文字以内で入力して下さい＞</label>
	<textarea id = "text-box" name="text" rows = "5" cols = "100" ><c:out value = "${editThread.text }" /></textarea><br>
</div>
	<input class = "submit" type = "submit" value = "投稿" /><br>
</form>
</div>
</div><!-- main -->
<div id = "footer"><div class = "copyRight">Copyright(c) Ohtsuka Jun</div></div>
</div>
</body>
</html>