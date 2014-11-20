$(function() {
	$('#_menu li.item a').bind('click', function() {
		var me = $(this);
		$('#_menu li.item a').removeClass('selected');
		me.addClass('selected');
		var href = me.attr('href');
		$('#_content iframe').css('visibility', 'hidden').attr('src', href);
		return false;
	})

	$('#_menu li.item a.init').trigger('click');

	setContentHeight();
});
$(window).resize(function() {
	setContentHeight();
});
// 设置内容高度
function setContentHeight() {
	var h1 = $(window).height();
	var h2 = $('#_content').offset().top;
	var h = h1 - h2;
	$('#_content').css('height', h);
}
