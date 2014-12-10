<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%
String shead="system";
if (request.getParameter("shead") != null) {
shead=request.getParameter("shead");
}
%>
<div class="row clearfix">
	<div class="col-md-12 column header">
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> <a class="navbar-brand logo" title="数据分析平台" href="/">数据分析平台</a>
				</div>
				
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav" id="mainNav">
						<li class="  <%= shead.equalsIgnoreCase("home")? "active":"" %>">
							<a href="/" title="首页">首页</a>
						</li>
						
						<li class="dropdown <%= shead.equalsIgnoreCase("schedule")? "active":"" %>">
							<a href="javascript:;" title="产品服务" class="dropdown-toggle" data-toggle="dropdown">产品服务<strong class="caret"></strong></a>
							<ul class="dropdown-menu main_menu" id='productSevice'>
								<!-- <li>
									<a href="/product/schedule/" title="调度系统" class='auth'>调度系统</a>
								</li>
								<li>
									<a href="/product/data/" title="统计分析" class='auth'>统计分析</a>
								</li>
								<li>
									<a href="/usermanage/" title="用户管理" class='auth'>后台管理</a>
								</li> 
								<li>
									<a href="/product/monitoring/" title="监控系统" class='auth'>监控系统</a>
								</li>
								<li>
									<a href="/product/webIDE" title="测试" class='auth' style='display:none'>&nbspwebIDE</a>
								</li> -->
							</ul>
						</li>
						
						 
						<li>
							<a href="/product/workbench/" title="我的工作台" class='auth'>我的工作台</a>
						</li>
						<li style="display:none">
							<a href="javascript:;" title="帮助中心">帮助中心</a>
						</li>
					</ul>
					
					<div class="navbar-right">
						<span class="login_info" id="loginInfo">
							<a href="javascript:;" id="login"  data-toggle="modal" data-target="#myModal" title="登录">登录</a>
						</span>
						<span id="logoutInfo" class="logout_info" style="display:none;">
							<span id="loginName">ecofe</span>
							<a href="javascript:;" id="logout" title="退出">退出</a>
						</span>
					</div>
				</div>
				
			</div>
		</nav>
	</div>
</div>
