<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/header.jspf"%>
<link href="${path}/css/storage.css" rel="stylesheet">
</head>
<body>

	<div class="row">
		<div>
			<form class="well" id="searchForm">
				<input type="text" style="width: 200px;" name="time" id="time"
					class="calendar calendar-time control-text search-query"
					readonly="readonly">
				<button id="searchBtn" type="button" class="button button-info">
					<i class="icon-search icon-white"></i>&nbsp;搜索
				</button>
			</form>
		</div>
	</div>
	<div id="part2"></div>
	<div id="part1"></div>
	<script src="${path}/assets/bui/acharts-min.js"></script>
	<script src="${path}/js/storage.js"></script>
</body>
</html>