<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户管理</title>
<%@include file="/header.jspf"%>
<link href="${path}/css/usermanager.css" rel="stylesheet">
<style>
</style>
</head>
<body>

	<!-- 搜索框  -->
	<div>
		<form class="well">
			<input type="text" id="username" placeholder="用户名"
				class="search-query"
				onkeypress="if(event.keyCode==13){showUserInTable();return false}">
			<button type="button" id="searchBtn" class="button button-info">
				<i class="icon-search icon-white"></i>&nbsp;搜索
			</button>
		</form>
	</div>
	<!-- 用户列表  -->
	<div id="usertable"></div>
	<!-- 隐藏的添加用户弹出框 -->
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
						<input name="password" type="password"
							data-rules="{required:true}" class="input-normal control-text">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="control-group span8">
					<label class="control-label"><s>*</s>确认密码：</label>
					<div class="controls">
						<input name="password2" type="password"
							data-rules="{required:true}" class="input-normal control-text">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="control-group span8">
					<label class="control-label"><s>*</s>用户权限：</label>
					<div class="controls">
						<select name="userRights">
							<option class="input-normal control-text" value="" selected="selected">普通用户</option>
							<option class="input-normal control-text" value="admin">管理员</option>
						</select>
					</div>
				</div>
			</div>
		</form>
	</div>
	<script src="${path}/assets/bui/acharts-min.js"></script>
	<script src="${path}/js/usermanager.js"></script>

</body>
</html>