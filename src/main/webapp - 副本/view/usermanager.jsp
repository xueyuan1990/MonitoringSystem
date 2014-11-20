<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/header.jspf"%>
<link href="${path}/css/usermanager.css" rel="stylesheet">
<style>
</style>
</head>
<body>


	<div class="row">
		<div>
			<form class="well">
				<input type="text" id="username" placeholder="用户名"
					onkeypress="if(event.keyCode==13){showUserInTable();return false}">
				<button type="button" id="searchBtn" class="button button-info">
					<i class="icon-search icon-white"></i>&nbsp;搜索
				</button>
			</form>

		</div>
	</div>

	<div id="part1"></div>
	<div id="content" class="hide">
		<form class="form-vertical" method="post">
			<div class="row">
				<div class="control-group span8">
					<label class="control-label"><s>*</s>用户名：</label>
					<div class="controls">
						<input name="username" type="text" data-rules="{required:true}"
							class="input-normal control-text">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="control-group span8">
					<label class="control-label"><s>*</s>密码：</label>
					<div class="controls">
						<input name="password" type="password" data-rules="{required:true}"
							class="input-normal control-text">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="control-group span8">
					<label class="control-label"><s>*</s>确认密码：</label>
					<div class="controls">
						<input name="password2" type="password" data-rules="{required:true}"
							class="input-normal control-text">
					</div>
				</div>
			</div>
		</form>
	</div>
	<script src="${path}/assets/bui/acharts-min.js"></script>
	<script src="${path}/js/usermanager.js"></script>

</body>
</html>