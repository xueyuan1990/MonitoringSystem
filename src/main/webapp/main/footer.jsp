<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!-- <div class="dw-footer">
	<p>
		<a href="http://www.meizu.com/" target="_blank">魅族官网</a>
		<a href="http://pa.meizu.com/" target="_blank">数据流分析</a>
		<a href="http://uba.meizu.com/" target="_blank">用户行为分析</a>
		<a href="http://113.108.229.220:8080/dms/index.do" target="_blank">运维管理平台</a>
		<a href="http://bi.meizu.com/" target="_blank">数据驱动</a>
	</p>
	<p>©2014 Meizu Telecom Equipment Co., Ltd. All rights reserved.</p>
</div> -->
<script src="/resources/fastdfs/js/common/jquery.js"></script>
<script src="/resources/fastdfs/js/common/bootstrap.min.js"></script>
<script src="/resources/fastdfs/js/common/jquery.dataTables.min.js"></script>
<script src="/resources/fastdfs/js/common/jquery.calendar.js"></script>
<script src="/resources/fastdfs/js/common/mz.text.js"></script>
<script src="/resources/fastdfs/js/common/mz.date.js"></script>
<script src="/resources/fastdfs/js/common/highcharts.js"></script>
<script src="/resources/fastdfs/js/common/exporting.js"></script>
<script src="/resources/fastdfs/js/common/main-model.js"></script>
<script src="/resources/fastdfs/js/common/toolbar.js"></script>
<script src="/resources/fastdfs/js/common/create-chart.js"></script>
<script src="/resources/fastdfs/js/common/create-table.js"></script>
<script src="/resources/fastdfs/js/common/main.js"></script>
<script src="/resources/fastdfs/js/common/login.js"></script>
<script src="/resources/fastdfs/js/common/jquery.tmpl.min.js"></script>
<script src="/resources/fastdfs/js/common/jquery-ui-1.10.4.custom.js"></script>
<script src="/resources/fastdfs/js/common/plug.js"></script>
<script src="/resources/fastdfs/js/common/jquery.ztree.all-3.5.js"></script>
<script src="/resources/fastdfs/js/common/codemirror.js"></script>
<script src="/resources/fastdfs/js/common/moment.min.js"></script>
<script src="/resources/fastdfs/js/common/jquery.daterangepicker.js"></script>
<script src="/resources/fastdfs/js/common/auth.js"></script>
<script type="text/javascript">
	$.get('/rest/meissa/navmenu/project/list_can_show_service',{},function(result){
	    if(result.code == 200){
	    	var data = result.value;
	    	for(var i = 0 ; i < data.length ; i++){
	    		$('#productSevice').append('<li><a href="'+data[i].href+'" title="'+data[i].title+'" class="auth">'+data[i].title+'</a></li>');
	    	}
	    }
	});
	//$('#productSevice').append('<li><a href="/product/data/" title="统计分析" class="auth">统计分析</a></li>');
</script>