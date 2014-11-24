<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>系统监控</title>
<%@include file="/header.jspf"%>
<link href="${path}/css/storage.css" rel="stylesheet">
</head>
<body>

	<div>
		<form class="well">
			<input type="text" name="time" id="time"
				class="calendar calendar-time control-text search-query"
				readonly="readonly">
			<button id="searchBtn" type="button" class="button button-info">
				<i class="icon-search icon-white"></i>&nbsp;搜索
			</button>
		</form>
	</div>
	<div id="graph"></div>
	<div id="storagetable"></div>
	<script src="${path}/assets/bui/acharts-min.js"></script>
	<script src="${path}/js/storage.js"></script>
</body>
</html>