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
<span class = "ScreenTransition">
	<a href = "newThread">新規投稿</a>
	<c:if test = "${loginUser.positionId == 1 }">
		<a href = "userManagement">ユーザー管理</a>
	</c:if>
	<a href = "signout">サインアウト</a>
</span>
</div><!-- header -->

<div class = "main-content">

<h1>投稿一覧</h1>
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
<label>投稿日検索(日付リセットで「投稿日検索」全表示)：<input type = "submit" name = "submit" value = "日付リセット" /></label>
	<select name = "startYear" id = "startYear" >
		<c:forEach begin="${checkStartYear }" end="${checkEndYear }" var="startYear">
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
		<c:forEach begin="${checkStartYear }" end="${checkEndYear }" var="endYear">
			<option value="${endYear}" <c:if test= "${endYear == checkEndYear}" >selected</c:if>><c:out value = "${endYear }" /></option>
		</c:forEach>
	</select>年
	<select name = "endMonth" id = "endMonth" >
		<c:forEach begin="1" end="12" var="endMonth">
			<option value="${endMonth}" <c:if test= "${endMonth == checkEndMonth}" >selected</c:if>><c:out value = "${endMonth }" /></option>
		</c:forEach>
	</select>月
	<select name = "endDay" id = "endDay" >
		<c:forEach begin="1" end="31" var="endDay">
			<option value="${endDay}" <c:if test= "${endDay == checkEndDay}" >selected</c:if>><c:out value = "${endDay }" /></option>
		</c:forEach>
	</select>日<br>
	<input class = "submit" type = "submit" name = "submit" value = "検索" />
</form>
</div>

<!-- メッセージ表示機能 -->
<div class = "messages">
	<c:forEach items = "${threads }" var = "thread" varStatus = "threadStatus">
	<table class = "thread" border="1" cellspacing="0">
	<div class = "message">
		<form action = "delete" method = "post">
		<input type = "hidden" name = "thread_id" id = "thread_id" value = "${thread.threadId }" />
		<input type = "hidden" name = "user_id" id = "user_id" value = "${thread.userId }" />
		<tr>
		<div class = "account-name-date">
			<th class = "thread">
			Name：<span class = "name"><c:out value = "${thread.name }" /></span>　
			投稿：<span class = "date"><c:out value="${thread.differenceTime }" />
			　投稿日時：<fmt:formatDate value="${thread.insertDate }" pattern = "yyy/MM/dd HH:mm:ss" /></span>
			<c:if test="${(loginUser.id == thread.userId) || (loginUser.positionId == 2) ||
					((loginUser.positionId == 3) && (loginUser.branchId == thread.userBranchId))}">
				　<input class = "delete-button" type = "submit" name = "submit" value = "この投稿を削除する"
				 onclick = 'return confirm("本当に削除してよろしいですか？");'/>
			 </c:if>
			</th>
		</div>
		</tr>
		<tr>
			<td class = "title">件名：<c:out value = "${thread.title }" /></td>
		</tr>
		<tr>
			<td class = "category">カテゴリー：<c:out value = "${thread.category }" /></td>
		</tr>
		<tr>
			<td class = "text"><pre><c:out value = "${thread.text }" /></pre></td>
		</tr>
		</form>
		<tr>
			<td class = "comment">
				<c:forEach items = "${userComments }" var = "userComment">
				<c:if test ="${thread.threadId == userComment.threadId}" >
				<form action = "delete" method = "post">
					<input type = "hidden" name = "thread_id" id = "thread_id" value = "${thread.threadId }" />
					<input type = "hidden" name = "user_id" id = "user_id" value = "${userComment.userId }" />
					<div class = "comment-date">
						Name：<span class = "name"><c:out value = "${userComment.name }" /></span>　
						投稿：<span class = "date"><c:out value="${userComment.differenceTime }" />
						　投稿日時：<fmt:formatDate value="${userComment.insertDate }" pattern = "yyy/MM/dd HH:mm:ss" /></span>
						<input type = "hidden" name = "comment_id" id = "comment_id" value = "${userComment.commentId }" />
						<c:if test="${(loginUser.id == userComment.userId) || (loginUser.positionId == 2) ||
						 ((loginUser.positionId == 3) && (loginUser.branchId == userComment.userBranchId))}">
							　<input class = "delete-button" type = "submit" name = "submit" value = "このコメントを削除する"
							 onclick = 'return confirm("[${userComment.commentId }：${userComment.text}]本当に削除してよろしいですか？");'/>
						</c:if>
					</div>
					<pre class = "comment-text"><c:out value = "${userComment.text }" /></pre>
				</form>
				</c:if>
				</c:forEach>
				<form class = "comment-area" action = "home" method = "post">
					<input type = "hidden" name = "thread_id" id = "thread_id" value = "${thread.threadId }" />
					＜コメントは500文字まで＞<br>
					<textarea id = "text-box" name="comment" rows = "3" cols = "50" style = "color: #999999;"
					 onfocus="if(this.value==this.defaultValue){this.value='';this.style.color='black';}"
					 onblur="if(this.value==''){this.value=this.defaultValue;this.style.color='#999999'}" >コメント</textarea>
					<input id = "comment_submit" type = "submit" name = "submit" value = "コメントする" />
				</form>
			</td>
		</tr>
	</div>
	</table>
	</c:forEach>
</div>

</div><!-- main -->

<div id = "footer"><div class = "copyRight">Copyright Ohtsuka Jun</div></div>
</div>
</body>
</html>