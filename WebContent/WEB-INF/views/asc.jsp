<%@page import="vo.GuestVo"%>
<%@page import="java.util.List"%>
<%@page import="dao.GuestDaoImpl"%>
<%@page import="dao.GuestDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록</title>
<style type="text/css">
body {
	margin: 0 auto;
}

h1{
	text-align:center;
}

table{
	margin : 5px auto;
	width : 400px;
	text-align: center;
}

form {
	text-align: center;
}

div {
	border: 1px solid #000;
	width: 500px;
	margin : 5px auto;
}
textarea{
	width: 400px;
	height: 150px;
}
.content{
	background: #FAF4C0;
	border: 1px solid #000;
	height:200px;
}
.no{
	background:#D5D5D5;
	border: 1px solid #000;
}
.name{
	background:#B2EBF4;
	border: 1px solid #000;
}
input[type=submit]{
	background:#1DDB16;
	border:1px solid #000;
}
#write{
	background:#D4F4FA;
}
#list{
	background:#FFEBFE;
	text-align:center;
}
a:link { color: green; text-decoration: none; background:yellow; border:1px solid black}
a:visited { color: green; text-decoration: none;}
a:hover { color: red; text-decoration: underline;}
</style>
</head>
<body>
<%
	String HomePath = (String) request.getAttribute("HomePath");
	List<GuestVo> list = (List<GuestVo>) request.getAttribute("list");
%>

	<div id="write">
		<h1>방명록 작성</h1>
		<form method="post" action="<%=HomePath%>/write.jsp">
			<table>
				<tr>
					<td>
						이름
					</td>
					<td>
						<input type="text" name="name" placeholder="이름">
					</td>
				</tr>
				<tr>
					<td>
						비밀번호
					</td>
					<td>
						<input type="password" name="password">
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<textarea name="content" ></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="submit" value="작성">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="list">
		<h1>방명록 리스트</h1>
		<a href="<%=HomePath%>/" >내림차순</a>
		<%
			for(int i = 0; i<list.size();i++){
				GuestVo vo = list.get(i);
				//list크기만큼 반복하여 리스트 전체를 띄워줄 것임.
				//리스트의 인덱스를 이용하여 리스트속에서 하나씩 꺼내옴
				//꺼내온 GuestVo를 vo로 선언, 정의
				//하나씩 테이블로 만들어서 출력.
		%>
			<table>
				<tr>
					<td class="no">
						<%= vo.getNo() %>
					</td>
					<td class="name">
						<%= vo.getName() %>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="content">
						<%= vo.getContent() %>
					</td>
				</tr>
				<tr>
					<td>
						<form method="post" action="<%=HomePath%>/">
							<input type="hidden" name="do" value="modify">
							<input type="hidden" name="no" value="<%= vo.getNo()%>">
							<input type="hidden" name="name" value="<%= vo.getName()%>">
                			<input type="hidden" name="password" value="<%= vo.getPassword()%>">
                			<input type="hidden" name="content" value="<%= vo.getContent()%>">
                			<input type="submit" value="수정">
           				</form>
					</td>
					<td>
						<form method="post" action="<%=HomePath%>/">
							<input type="hidden" name="do" value="deleteform">
							<input type="hidden" name="no" value="<%= vo.getNo()%>">
                			<input type="hidden" name="password" value="<%= vo.getPassword()%>">
                			<input type="submit" value="삭제">
           				</form>
					</td>
				</tr>
			</table>
		
		<%
			}
		%>
	</div>
</body>
</html>