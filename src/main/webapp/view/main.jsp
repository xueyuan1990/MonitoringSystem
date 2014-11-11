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
<link href="${path}/css/main.css" rel="stylesheet">
<link href="${path}/assets/iconfont/iconfont.css" rel="stylesheet">
</head>
<body>
	<div id="_head">
		<div class="pull-left" style="margin-left:46%;">
			<h1>监控系统</h1>
		</div>
		<div class="pull-right">
			<span><i class="icon-user icon-white"></i>&nbsp;${username }&nbsp;&nbsp;</span>
			<span><a href="${path }/system/logoff/logoff.do"><i
					class="icon-off icon-white"></i>&nbsp;注销&nbsp;&nbsp;&nbsp;&nbsp;</a></span>

		</div>
	</div>
	<!--菜单 -->
	<div id="_menu">
		<ul class="nav">
			<li class="item"><a class="init" href="${path}/view/home.jsp"><i
					class="icon iconfont">&#xf012b;</i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主页</a></li>
			<li class="item"><a href="${path}/view/storage.jsp"><i
					class="icon iconfont">&#xf0025;</i>&nbsp;&nbsp;系统监控</a></li>
					<!-- 
			<li class="item"><a href="${path}/view/prediction.jsp"><i
					class="icon iconfont">&#xf00d0;</i>&nbsp;&nbsp;系统预测</a></li>
					 -->
			<li class="item"><a href="${path}/view/authority.jsp"> <i
					class="icon iconfont">&#xf00d5;</i>&nbsp;&nbsp;用户管理
			</a></li>
		</ul>
	</div>


	<!-- 右侧内容 -->
	<div id="_content">
		<iframe width="100%" height="100%" frameborder="0"
			onload="$(this).css('visibility','visible');"></iframe>
	</div>
	<script src="${path}/js/main.js"></script>
</body>
</html>