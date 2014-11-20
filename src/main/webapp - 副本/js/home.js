$(function() {
	var datepicker = new BUI.Calendar.DatePicker({
		trigger : '.calendar-time',
		showTime : true,
		dateMask : 'yyyy-mm-dd HH:MM',
		autoRender : true
	});
	showTracker();
	$("#searchBtn").bind('click', search);
	$("#searchBtn").trigger('click');

	$("#total").delegate(".pie", 'click', function(e) {
		var tagName = $(e.target).prop('tagName');
		// firefox and chrome path but ie is shape
		if (tagName == 'path' || tagName == 'tspan' || tagName == 'shape') {
			showDetails($(this));
		}
	});// 向未来的子元素添加事件
});
// showTracker
function showTracker() {
	$("#trackerGroup").empty();
$.ajax({
	url:path+'/system/tracker/selectAllTracker.do',
	type:'POST',
	dataType:'json',
	success:function(response){
		var datas = response.value;
		for(var i in datas){
			var data = datas[i];
			var trackerId = "tracker"+data.trackerId;
			var trackerIp = data.trackerIp;
			var trackerState = data.trackerState;
			var image;
			if(trackerState=="ACTIVE"){
				image=path +"/image/tracker.jpg";
			}else{
				image=path +"/image/trackerOffline.jpg";
			}
			$("#trackerGroup").append('<div id="'+trackerId+'" class="tracker span7"></div>');
			$("#"+trackerId).append('<img width="128" height="128" src="'+image+'" hspace="90" vspace="10" alt="tracker"/><div align="center">'+trackerIp+'</div>');
		}
	}
});
}
// 按组别显示详细信息
function search() {
	$('#total').empty();
	// 先查询组信息，之后将按组查询的信息（总信息和各个服务器具体信息），分别放在对应的组中
	$.ajax({
		url : path + '/system/storage/selectGroupInfo.do',
		type : 'POST',
		dataType : 'json',
		data : {
			time : $('#time').val()
		},
		success : function(response) {// 获得groupId,及阀值、已用、未用
			var datas = response.value;
			for ( var i in datas) {
				var groupId = (parseInt(i) + 1);
				var data = datas[i];
				var info = "&nbsp;&nbsp;&nbsp;&nbsp;存储服务器（第" + data.groupId + "组）：总容量=" + data.groupTotal + "M（已用" + data.groupFree + "M，空闲"
						+ (data.groupTotal - data.groupFree) + "M），阀值=" + data.groupThreshold + "M";
				$('#total').append(
						'<div  style="" id="group' + groupId + '" class="group" ><h2 style="clear:left;">' + info
								+ '</h2></div>');
				selectStorageByGroup(groupId);// 按组查询服务器
			}
//			$('#time').attr("value",datas[0].time.substring(0,16));
		}
	});
}
// 按组查询服务器具体信息，用饼图显示在该组的group中
function selectStorageByGroup(groupId) {
	$.ajax({
		url : path + '/system/storage/selectStorageByGroup.do',
		type : 'POST',
		dataType : 'json',
		data : {
			time : $('#time').val(),
			groupId : groupId
		},
		success : function(storeInfo) {
			showInPie(storeInfo.value);
		}
	});
}
// 点击饼图，显示该server的详细信息
function showDetails(the) {
	var groupId = the.attr("groupId");
	var serverId = the.attr("serverId");
	window.location.href = path + '/view/storage.jsp?groupId=' + groupId + '&serverId=' + serverId;
}
// 在饼图中显示各组信息
function showInPie(datas) {
	for ( var i in datas) {
		var data = datas[i];
		var pieId = data.groupId + "-" + data.serverId;
		var pieIp = data.ipAddr.split(" ")[0];
		$('#group' + data.groupId).append(
				'<div groupId="' + data.groupId + '" serverId="' + data.serverId + '" id="pie' + pieId + '" class="pie"></div>');// .pie已添加click事件
		// 根据状态显示颜色，非ACTIVE为灰色，超过阀值为红色
		var green1 = '#a6c96a';
		var green2 = '#7bab12';
		var red1 = '#F28383';
		var red2 = '#F96D6D';
		var gray1 = '#C0B9B9';
		var gray2 = '#A39797';
		var color1 = green1;
		var color2 = green2;
		if (data.serverThreshold != 0 && data.freeStorage < data.serverThreshold) {
			color1 = red1;
			color2 = red2;
		}
		if (data.ipAddr.indexOf("OFFLINE") != -1) {// 是OFFLINE
			color1 = gray1;
			color2 = gray2;
		}
		var chart = new AChart({
			id : 'pie' + pieId,
			width : 300,
			height : 300,
			plotCfg : {
				margin : [ 5 ]
			},
			title : {
				text : pieId + " (" + pieIp + ")"
			},
			legend : null,// 不显示图例

			tooltip : {
				pointRenderer : function(point) {
					return (point.percent * 100).toFixed(2) + '%';
				}
			},
			series : [ {
				colors : [ color1, color2 ],// green,red,gray
				enableMouseTracking : false,// 鼠标移到图上时是否触发显示框
				type : 'pie',
				name : '所占比例',
				allowPointSelect : false, // 不允许选中
				labels : {
					distance : -30, // 文本距离圆的距离
					label : {
						fill : '#fff'
					}

				},
				data : [ [ '未用' + (data.freeStorage / data.totalStorage * 100).toFixed(2) + '%', data.freeStorage ],

				{
					name : '已用' + ((data.totalStorage - data.freeStorage) / data.totalStorage * 100).toFixed(2) + '%',
					y : data.totalStorage - data.freeStorage,
					sliced : true,
					selected : true
				}

				]
			} ]
		});

		chart.render();
	}

}