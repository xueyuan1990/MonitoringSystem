$(function() {
	$('.sidebar li.item a').bind('click', function() {
		var me = $(this);
		$('.sidebar li.item a').removeClass('selected');
		me.addClass('selected');
		var href = me.attr('href');
		$('.content iframe').css('visibility', 'hidden').attr('src', href);
		return false;
	})

	$('.sidebar li.item a.init').trigger('click');

	setContentHeight();
});
$(window).resize(function() {
	setContentHeight();
});
// 设置内容高度
function setContentHeight() {
	var h1 = $(window).height();
	var h2 = $('.content').offset().top;
	var h = h1 - h2;
	$('.content').css('height', h);
}
