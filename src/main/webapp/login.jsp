<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>监控系统</title>
<%@include file="/header.jspf"%>
<!-- styles -->
<link href="${path}/css/login.css" rel="stylesheet">
<script>
	if (top != self) {
		top.location.href = self.location.href;
	}
</script>
</head>
<body>

	<div id="_head">
		<h1>监控系统</h1>
	</div>

	<div>
		<div class="row">
			<div class="span9">
				<form class="form-horizontal well" id="loginForm">
					<div class="row">
						<div class="control-group span9">
							<div class="controls">
								<input type="text" id="username" placeholder="用户名"
									class="input-large control-text" onkeypress="if(event.keyCode==13){login();}">
							</div>
						</div>

					</div>
					<div class="row">
						<div class="control-group span9">
							<div class="controls">
								<input type="password" id="password" placeholder="登陆密码"
									class="input-large control-text" onkeypress="if(event.keyCode==13){login();}">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="control-group span9">
							<div class="controls">
								<button type="button" id="loginBtn"
									class="button-large button button-info">登陆</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>


	</div>


	<script src="${path}/js/login.js"></script>
</body>
</html>
