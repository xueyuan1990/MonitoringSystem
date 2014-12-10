$(function() {
	$('.sidebar li.item a').bind('click', function() {
		var me = $(this);
		$('.sidebar li.item a').removeClass('selected');
		me.addClass('selected');
		var href = me.attr('href');
		$('.content iframe').css('visibility', 'hidden').attr('src', href);
		return false;
	})
	$('.logoff').bind('click', logoff);
	$('.sidebar li.item a.init').trigger('click');

	setContentHeight();
});
/*
 * window resize时，设置content高度
 */
$(window).resize(function() {
	setContentHeight();
});
/*
 * 注销
 */
function logoff() {
	$.ajax({
		url : path + '/system/logoff/logoff.do',
		type : 'POST',
		dataType : 'json',
		success : function(response) {
			if (response.code == 200) {
				window.location.href = path + '/login.jsp';
			} else if(response.message!=null) {
				BUI.Message.Alert(response.message, 'error');
			}else{
				window.location.href = path + '/500.jsp';
			}
		}

	});
}
/*
 * 设置content高度
 */
function setContentHeight() {
	var h1 = $(window).height();
	var h2 = $('.content').offset().top;
	var h = h1 - h2;
	$('.content').css('height', h);
}