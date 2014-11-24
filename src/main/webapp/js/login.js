$(function() {
	$("#username").focus();
	$(".login_btn").bind('click', login);
});

function login() {
	$.ajax({
		url : path + '/login/loginCheck.do',
		type : 'POST',
		dataType : 'json',
		data : {
			username : $('#username').val(),
			password : $('#password').val()
		},
		success : function(response) {
			if (response.code == 200) {
				window.location.href = path + '/view/main.jsp';
			} else {
				BUI.Message.Alert(response.message, 'error');
			}
		}

	});
}
