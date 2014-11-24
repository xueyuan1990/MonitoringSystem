<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>主页</title>
<%@include file="/header.jspf"%>
<link href="${path}/css/home.css" rel="stylesheet">
</head>
<body>
	<!-- 搜索框-->
	<div>
		<form class="well">
			<input type="text" name="time" id="time"
				class="calendar calendar-time control-text search-query"
				readonly="readonly" />
			<button id="searchBtn" type="button" class="button button-info">
				<i class="icon-search icon-white"></i>&nbsp;搜索
			</button>
		</form>
	</div>

	<!-- tracker 服务器 -->
	<div class="row-fluid show-grid">
		<div id="trackerGroup" class="group"></div>
	</div>

	<!-- storage 服务器  -->
	<div id="storageGroup" class="row-fluid show-grid"></div>
	<script src="${path}/assets/bui/acharts-min.js"></script>
	<script src="${path}/js/home.js"></script>
</body>
</html>