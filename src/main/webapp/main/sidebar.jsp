<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<% 
	String sidebar="system"; 
	if (request.getParameter("sidebar") != null) {
		sidebar=request.getParameter("sidebar");
		
	}
%>
<div class="col-md-2 sidebar" id="sidebar">
	<div class="list-group">
		<a id="summary" href="/monitor/index.jsp" class="list-group-item <%= sidebar.equalsIgnoreCase("summary")? "active":"" %> ">主页</a>
		<a id="manageRule" href="/monitor/system-monitor.jsp" class="list-group-item <%= sidebar.equalsIgnoreCase("manageRule")? "active":"" %>">系统监控</a>
		<a id="runManager" href="/monitor/user-manage.jsp" class="list-group-item <%= sidebar.equalsIgnoreCase("runManager")? "active":"" %>">用户管理</a>
	</div>
</div>