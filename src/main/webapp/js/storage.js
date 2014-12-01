$(function() {
	var datepicker = new BUI.Calendar.DatePicker({
		trigger : '.calendar-time',
		showTime : true,
		dateMask : 'yyyy-mm-dd HH:MM',
		autoRender : true
	});

	$("#searchBtn").bind('click', showInTable);
	$("#searchBtn").trigger('click');
});

/*
 * 获取URL参数
 */
function GetRequest() {
	var url = location.search; // 获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") !== -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}
/*
 * 在表格中显示storage的详细信息
 */
function showInTable() {
	var Request = new Object();
	Request = GetRequest();
	var groupId = Request['groupId'];
	var serverId = Request['serverId'];
	if (groupId != null && serverId != null) {
		searchOneStorage(groupId, serverId, 1);
	} else {
		groupId = -1;
		serverId = -1;
		searchOneStorage(1, 1, 1);
	}
	$('#storagetable').empty();
	$('#storagetable').append('<div id="grid"></div>');
	var id = 0;
	var columns = [
			{
				title : '时间',
				dataIndex : 'time',
				elCls : 'center'
			},
			{
				title : '组ID',
				dataIndex : 'groupId',
				elCls : 'center'
			},
			{
				title : '服务器ID',
				dataIndex : 'serverId',
				elCls : 'center'
			},
			{
				title : '上传文件',
				dataIndex : 'successUploadCount',
				elCls : 'right'
			},
			{
				title : '下载文件',
				dataIndex : 'successDownloadCount',
				elCls : 'right'
			},
			{
				title : '总容量',
				dataIndex : 'totalStorage',
				elCls : 'right'
			},
			{
				title : '已用容量',
				dataIndex : 'usedStorage',
				elCls : 'right'
			},
			{
				title : '空闲容量',
				dataIndex : 'freeStorage',
				elCls : 'right'
			},
			{
				title : '阀值',
				dataIndex : 'serverThreshold',
				elCls : 'right',
				editor : {
					xtype : 'number',
					rules : {
						required : true
					},
					validator : valid
				}
			},
			{
				title : 'IP',
				dataIndex : 'ipAddr',
				elCls : 'center'
			},
			{
				title : '最后在线时间',
				dataIndex : 'lastHeartBeatTime',
				elCls : 'center'
			},
			{
				title : '趋势图',
				dataIndex : 'show',
				elCls : 'center',
				renderer : function() {
					return '<span class="grid-command" days="1">1天</span><span class="grid-command" days="7">7天</span><span class="grid-command" days="30">30天</span><span class="grid-command" days="90">90天</span>';
				}
			} ];
	/*
	 * 验证阀值设置合法
	 */
	function valid(value) {
		if (value < 0 || value % 1 !== 0) {
			return "阀值只能设置为正整数";
		}
	}
	var editing = new BUI.Grid.Plugins.CellEditing({
		triggerSelected : true,
		listeners : {
			accept : function(ev) {
				$.ajax({
					url : path + '/system/storage/setServerThreshold.do',
					type : 'POST',
					dataType : 'json',
					data : {
						groupId : ev.record.groupId,
						serverId : ev.record.serverId,
						serverThreshold : ev.record.serverThreshold
					},
					success : function(response) {
						if (response.code != 200) {
							BUI.Message.Alert(response.message, 'error');
						}
					}
				});
			}
		}
	});

	var store = new BUI.Data.Store({
		url : path + '/system/storage/getStoragesByPage.do',
		autoLoad : true,
		params : {
			time : $('#time').val(),
			groupId : groupId,
			serverId : serverId
		},
		pageSize : 10
	});
	var grid = new BUI.Grid.Grid({
		render : '#grid',
		forceFit : true,
		height : $(window).height() - $('#storagetable').offset().top - 5,
		columns : columns,
		plugins : [ editing, BUI.Grid.Plugins.AutoFit ],
		store : store,
		bbar : {
			pagingBar : true
		},
		listeners : {
			rowclick : function(ev) {
				if ($(ev.domTarget).hasClass('grid-command')) {
					var days = $(ev.domTarget).attr("days");
					var rowData = ev.record;
					var groupId = rowData.groupId;
					var serverId = rowData.serverId;
					searchOneStorage(groupId, serverId, days);
				}
			}
		}
	});

	grid.render();
}

/*
 * 趋势图
 */
function searchOneStorage(groupId, serverId, days) {
	$('#graph').empty();
	var now = new Date();
	var endTime = now;
	var dayStart = new Date(now.getTime() - (now.getHours() * 3600000 + now.getMinutes() * 60000 + now.getSeconds() * 1000));
	var startTime = new Date(dayStart.getTime() - (days - 1) * 24 * 3600 * 1000);
	var startTimeFormat;
	var endTimeFormat;
	if (days == 1) {// 按小时
		startTimeFormat = startTime.getFullYear() + "-" + (startTime.getMonth() + 1) + "-" + startTime.getDate() + " " + startTime.getHours() + ":00";
		endTimeFormat = endTime.getFullYear() + "-" + (endTime.getMonth() + 1) + "-" + endTime.getDate() + " " + endTime.getHours() + ":00";
	} else {// 按天
		startTimeFormat = startTime.getFullYear() + "-" + (startTime.getMonth() + 1) + "-" + startTime.getDate() + " 00:00";
		endTimeFormat = endTime.getFullYear() + "-" + (endTime.getMonth() + 1) + "-" + endTime.getDate() + " 00:00";
	}
	var store = new BUI.Data.Store({
		url : path + '/system/storage/getStoragesPeriod.do',
		autoLoad : false,
		params : {
			startTime : startTimeFormat,
			endTime : endTimeFormat,
			groupId : groupId,
			serverId : serverId,
			days : days
		}
	});
	var categories = new Array();
	if (days == 1) {// 1天按小时显示
		categories = [
				new Date(startTime.getTime()).getFullYear() + "." + (new Date(startTime.getTime()).getMonth() + 1) + "."
						+ new Date(startTime.getTime()).getDate(), '1时', '2时', '3时', '4时', '5时', '6时', '7时', '8时', '9时', '10时', '11时', '12时', '13时',
				'14时', '15时', '16时', '17时', '18时', '19时', '20时', '21时', '22时', '23时' ];
	} else if (days == 7 || days == 30) {// 7天和30天按天显示
		for (var i = 0; i < days; i++) {
			var year = new Date(startTime.getTime() + i * 24 * 3600 * 1000).getFullYear();
			var month = (new Date(startTime.getTime() + i * 24 * 3600 * 1000).getMonth() + 1);
			var date = new Date(startTime.getTime() + i * 24 * 3600 * 1000).getDate();
			categories[i] = formatXAxis(year, month, date);
		}
	} else if (days == 90) {// 90天按每五天显示
		var j = 0;
		for (var i = 0; i < days; i++) {
			var year = new Date(startTime.getTime() + i * 24 * 3600 * 1000).getFullYear();
			var month = (new Date(startTime.getTime() + i * 24 * 3600 * 1000).getMonth() + 1);
			var date = new Date(startTime.getTime() + i * 24 * 3600 * 1000).getDate();
			if ((date % 5 == 0 && date != 30) || date == 1) {
				categories[j] = formatXAxis(year, month, date);
				j++;
			}

		}
	}
	var chart = new AChart({
		id : 'graph',
		forceFit : true, // 自适应宽度
		fitRatio : 0.25, // 高度是宽度的 0.25
		plotCfg : {
			margin : [ 50, 50, 80 ]
		},
		title : {
			text : groupId + '组' + serverId + '号服务器 ' + days + '天内趋势图'
		},
		// colors : ["#0590FA","#4DCEFF","#BBE3A7","#FFB65D"],
		xAxis : {
			categories : categories
		},
		yAxis : [ {
			position : 'left',
			title : {
				text : '容量（M）',
				x : -40,
				rotate : -90
			}
		}, {
			type : 'number',
			position : 'right',
			line : null,
			tickLine : null,
			labels : {
				label : {
					x : 12
				}
			},
			title : {
				text : '文件数(个)',
				x : 40,
				rotate : 90
			}
		} ],
		seriesOptions : {
			lineCfg : {
				smooth : false
			}
		},
		tooltip : {
			shared : true, // 多个数据序列共同显示信息
			crosshairs : true
		// 出现基准线
		},
		series : [ {
			name : '已用容量(M)',
			yField : 'usedStorage'
		}, {
			name : '未用容量(M)',
			yField : 'freeStorage'
		}, {
			type : 'column',
			name : '上传文件数(个)',
			yAxis : 1,// 使用第二个坐标轴，索引为1
			yField : 'successUploadCount'
		}, {
			type : 'column',
			name : '下载文件数(个)',
			yAxis : 1,// 使用第二个坐标轴，索引为1
			yField : 'successDownloadCount'
		} ]
	});

	chart.render();

	store.on('load', function() {
		var data = store.getResult();
		chart.changeData(data);
	});
	store.load();

}
/*
 * 设置日期显示样式
 */
function formatXAxis(year, month, date) {

	if (date == 1 && month == 1) {
		return year + "年";
	} else if (date == 1) {
		return month + "月";
	} else {
		return month + "." + date;
	}
}