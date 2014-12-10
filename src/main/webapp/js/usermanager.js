$(function() {
	$("#searchBtn").bind('click', showUserInTable);
	$("#searchBtn").trigger('click');
});

/*
 * 在表格中显示user的详细信息
 */
function showUserInTable() {
	var username = $('#username').val();
	$("#username").val("");
	$('#usertable').empty();
	$('#usertable').append('<div id="grid"></div>');
	var id = 0;
	var columns = [ {
		title : '用户名',
		dataIndex : 'username',
		elCls : 'center'
	}, {
		title : '创建时间',
		dataIndex : 'createTime',
		elCls : 'center'
	}, {
		title : '用户权限',
		dataIndex : 'userRights',
		elCls : 'center'
	}, {
		title : '用户管理',
		dataIndex : 'management',
		elCls : 'center',
		renderer : function() {
			return '<span class="grid-command" operation="delete">删除</span>';
		}
	} ];

	var store = new BUI.Data.Store({
		url : path + '/system/user/getUsers.do',
		autoLoad : true, 
		params : { 
			username : username
		},
		pageSize : 20
	});
	/*
	 * 删除用户
	 */
	function deleteUser(the) {
		var username = the.username;
		BUI.Message.Confirm("确定要删除用户  " + username, function() {
			$.ajax({
				url : path + '/system/user/deleteUser.do',
				type : 'POST',
				dataType : 'json',
				data : {
					username : username
				},
				success : function(response) {
					if (response.code == 200) {
						BUI.Message.Alert(response.message, 'success');
					} else if(response.message!=null) {
						BUI.Message.Alert(response.message, 'error');
					}else{
						window.location.href = path + '/500.jsp';
					}
					store.load();
				}
			});
		}, 'question');
	}
	var editing = new BUI.Grid.Plugins.DialogEditing({
		contentId : 'content', // 设置隐藏的Dialog内容
		triggerCls : 'btn-edit', // 触发显示Dialog的样式
		editor : {
			success : function() {
				var edtor = this;
				var form = edtor.get('form');
				var editType = editing.get('editType');// add 或者 edit
				var username = $.trim(form.getFieldValue("username"));
				var password = $.trim(form.getFieldValue("password"));
				var password2 = $.trim(form.getFieldValue("password2"));
				var userRights = $.trim(form.getFieldValue("userRights"));
				form.valid();// 检验
				if (form.isValid()) {
					if (password !== password2) {// 两次输入密码是否一致
						BUI.Message.Alert('两次输入密码不一致！', 'warning');
					} else {
						form.ajaxSubmit({ // 表单异步提交
							url : path + '/system/user/addUser.do',
							data : {
								username : username,
								password : password,
								userRights : userRights
							},
							success : function(response) {
								if (response.code == 200) {
									BUI.Message.Alert(response.message, 'success');
								} else if(response.message!=null) {
									BUI.Message.Alert(response.message, 'error');
								}else{
									window.location.href = path + '/500.jsp';
								}
								edtor.accept();
								store.load();
							}
						});
					}
				}
			}
		}
	});
	var grid = new BUI.Grid.Grid({
		render : '#grid',
		forceFit : true, // 列宽按百分比自适应
		height : $(window).height() - $('#usertable').offset().top - 5,
		columns : columns,
		bbar : {
			pagingBar : true
		// 表明包含分页栏
		},
		tbar : {
			items : [ {
				btnCls : 'button button-small',
				text : '<i class="icon-plus"></i>添加',
				listeners : {
					'click' : function() {
						var newData = {
							username : ''
						};
						editing.add(newData);
					}
				}
			} ]
		},
		plugins : [ editing, BUI.Grid.Plugins.AutoFit ],
		store : store, 
		listeners : {
			rowclick : function(ev) {
				if ($(ev.domTarget).hasClass('grid-command')) {
					var operation = $(ev.domTarget).attr("operation");
					var rowData = ev.record;
					if (operation == "delete") {
						deleteUser(rowData);
					}
				}
			}
		}
	});
	store.on('exception', function(ev) {
		BUI.Message.Alert(ev.error);
	});
	grid.render();

}
