<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>监控系统</title>
<%@include file="/header.jspf"%>
<link href="${path}/css/main.css" rel="stylesheet">
<link href="${path}/assets/iconfont/iconfont.css" rel="stylesheet">
</head>
<body>
	<!-- header头  -->
	<div class="header">
		<div class="pull-left">
			<h1>监控系统</h1>
		</div>
		<div class="pull-right">
			<span><i class="icon-user icon-white"></i>&nbsp;${username }&nbsp;&nbsp;</span>
			<span class="logoff"><i class="icon-off icon-white"></i>&nbsp;注销&nbsp;&nbsp;&nbsp;&nbsp;</span>

		</div>
	</div>
	<!-- 菜单 -->
	<div class="sidebar">
		<ul class="nav">
			<li class="item"><a class="init" href="${path}/view/home.jsp"
				title="主页"><i class="icon iconfont">&#xf012b;</i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主页</a></li>
			<li class="item"><a href="${path}/view/storage.jsp" title="系统监控"><i
					class="icon iconfont">&#xf0025;</i>&nbsp;&nbsp;系统监控</a></li>
			<li class="item"><a href="${path}/view/usermanager.jsp"
				title="用户管理"> <i class="icon iconfont">&#xf00d5;</i>&nbsp;&nbsp;用户管理
			</a></li>
		</ul>
	</div>


	<!-- 内容 -->
	<div class="content">

		<iframe onload="$(this).css('visibility','visible');"></iframe>
	</div>
	<script src="${path}/js/main.js"></script>
</body>
</html>