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
<title>連絡業務BBS</title>
</head>
<body>
<div id = "container">
<div class = "header">
	<a href = "newThread">新規投稿</a>
	<a href = "userManagement">ユーザー管理</a>
	<c:if test = "${empty loginUser }">
		<a href = "login">ログイン</a>
	</c:if>
	<c:if test = "${not empty loginUser }">
		<a href = "logout">ログアウト</a>
	</c:if>
</div><!-- header -->

<div class = "main-content">

<!-- ログインしたら表示するユーザー情報 -->
<c:if test = "${not empty loginUser }">
<div class = "profile">
ログイン中：
	<div class = "name"><h2><c:out value = "${loginUser.name }" /></h2></div>
	<!-- <div class = "account">@<c:out value = "${loginUser.account }" /></div> -->
</div>
</c:if>



<h1>投稿一覧</h1>
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

<!-- カテゴリー検索 -->
<div class = "search">
<form action = "home" method = "Get">
<label>カテゴリー検索：</label>
	<select name = "category" id = "category" >
		<option value = "" >全表示</option>
		<c:forEach items = "${categorys }" var="category">
			<option value = <c:out value = "${category }" /> <c:if test= "${categorySelect == category }" >selected</c:if>>
				<c:out value = "${category }" />
			</option>
		</c:forEach>
	</select><br>
<!-- 日付検索 -->
<label>投稿日検索(日付リセット後検索で全表示)：</label>
	<select name = "startYear" id = "startYear" >
		<c:forEach begin="2016" end="2025" var="startYear">
			<option value="${startYear}" <c:if test= "${startYear == checkStartYear}" >selected</c:if>><c:out value = "${startYear }" /></option>
		</c:forEach>
	</select>年
	<select name = "startMonth" id = "startMonth" >
		<c:forEach begin="1" end="12" var="startMonth">
			<option value="${startMonth}" <c:if test= "${startMonth == checkStartMonth}" >selected</c:if>><c:out value = "${startMonth }" /></option>
		</c:forEach>
	</select>月
	<select name = "startDay" id = "startDay" >
		<c:forEach begin="1" end="31" var="startDay">
			<option value="${startDay}" <c:if test= "${startDay == checkStartDay}" >selected</c:if>><c:out value = "${startDay }" /></option>
		</c:forEach>
	</select>日～
	<select name = "endYear" id = "endYear" >
		<option value = "" >  </option>
		<c:forEach begin="2016" end="2025" var="endYear">
			<option value="${endYear}" <c:if test= "${endYear == checkEndYear}" >selected</c:if>><c:out value = "${endYear }" /></option>
		</c:forEach>
	</select>年
	<select name = "endMonth" id = "endMonth" >
		<option value = "" >  </option>
		<c:forEach begin="1" end="12" var="endMonth">
			<option value="${endMonth}" <c:if test= "${endMonth == checkEndMonth}" >selected</c:if>><c:out value = "${endMonth }" /></option>
		</c:forEach>
	</select>月
	<select name = "endDay" id = "endDay" >
		<option value = "" >  </option>
		<c:forEach begin="1" end="31" var="endDay">
			<option value="${endDay}" <c:if test= "${endDay == checkEndDay}" >selected</c:if>><c:out value = "${endDay }" /></option>
		</c:forEach>
	</select>日
	<input type = "submit" name = "submit" value = "日付リセット" /><br>
	<input class = "submit" type = "submit" name = "submit" value = "検索" />
</form>
</div>

<!-- メッセージ表示機能 -->
<div class = "messages">
	<c:forEach items = "${messages }" var = "message" varStatus = "threadStatus">
	<table class = "thread" border="1" cellspacing="0">
	<div class = "message">
		<form action = "home" method = "post">
		<input type = "hidden" name = "thread_id" id = "thread_id" value = "${message.threadId }" />
		<input type = "hidden" name = "user_id" id = "user_id" value = "${message.userId }" />
		<tr>
		<div class = "account-name-date">
			<th class = "thread">
			ID：<span class = "account"><c:out value = "${message.account }" /></span>　
			Name：<span class = "name"><c:out value = "${message.name }" /></span>　
			投稿日時：<span class = "date"><c:out value="${message.differenceTime }" /></span>
			　<input class = "delete-button" type = "submit" name = "submit" value = "この投稿を削除する"
			 onclick = 'return confirm("本当に削除してよろしいですか？");'/>
			</th>
		</div>
		</tr>
		<tr>
			<td class = "title">件名：<c:out value = "${message.title }" /></td>
		</tr>
		<tr>
			<td class = "category">カテゴリー：<c:out value = "${message.category }" /></td>
		</tr>
		<tr>
			<td class = "text"><pre><c:out value = "${message.text }" /></pre></td>
		</tr>
		</form>
		<tr>
			<td>
			<c:forEach items = "${userComments }" var = "userComment">
			<c:if test ="${message.threadId == userComment.threadId}" >
			<form action = "home" method = "post">
			<input type = "hidden" name = "thread_id" id = "thread_id" value = "${message.threadId }" />
			<input type = "hidden" name = "user_id" id = "user_id" value = "${userComment.userId }" />
			<table class = "comment">
				<tr><th class = "comment-date">
					Name：<span class = "name"><c:out value = "${userComment.name }" /></span>　
					投稿日時：<span class = "date"><fmt:formatDate value="${userComment.insertDate }" pattern = "yyy/MM/dd HH:mm:ss" /></span>
					<input type = "hidden" name = "comment_id" id = "comment_id" value = "${userComment.commentId }" />
					　<input class = "delete-button" type = "submit" name = "submit" value = "このコメントを削除する"
					 onclick = 'return confirm("[${userComment.commentId }：${userComment.text}]本当に削除してよろしいですか？");'/>
				 </th></tr>
				<tr>
					<td class = "comment-text"><pre><c:out value = "${userComment.text }" /></pre></td>
				</tr>
			</table>
			</form>
			</c:if>
			</c:forEach>
			<form class = "comment-area" action = "home" method = "post">
				<input type = "hidden" name = "thread_id" id = "thread_id" value = "${message.threadId }" />
				<textarea name = "comment" cols = "50" rows = "3" class = "text-box">コメント</textarea>
				<input type = "submit" name = "submit" value = "コメントする" />
			</form>
			</td>
		</tr>
	</div>
	</table>
	</c:forEach>
</div>

</div><!-- main -->

<div id = "footer"><div class = "copyRight">Copyright(c) Ohtsuka Jun</div></div>
</div>
</body>
</html>