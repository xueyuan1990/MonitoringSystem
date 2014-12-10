<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404</title>
<%@include file="/header.jspf"%>
<link href="${path}/css/error.css" rel="stylesheet">
</head>
<body>
	
	<div class="errorcode">404</div>
	<div class="text">抱歉，您访问的资源不存在！</div>
	<div class="text">请确认您输入的网址是否正确。</div>
	<div class="text"><a href="${path}/login.jsp">返回主页</a></div>
</body>
</html>
