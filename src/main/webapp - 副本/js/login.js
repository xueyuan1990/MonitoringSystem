$(function() {
	$("#username").focus();
	$("#loginBtn").bind('click', login);// 注册按钮点击事件
});

function login() {
	$.ajax({
		url : path+'/login/loginCheck.do',
		type : 'POST',
		dataType : 'json',
		data : {
			username : $('#username').val(),
			password : $('#password').val()
		},
		success : function(response) {
			if (response.code == 200) {
				window.location.href = path+'/view/main.jsp';
			} else {
				BUI.Message.Alert(response.message, 'error');
			}
		}

	});
	//return false;
}
