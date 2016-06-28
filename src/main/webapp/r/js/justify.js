$(window).resize(function(){
  $('.justify').css({ 
	position: 'absolute',
	display: 'block',
	left: ($(window).width() - $('.justify').outerWidth())/2, 
	top: ($(window).height() - $('.justify').outerHeight())/2,
  });
});

$().ready(function() {
  $(window).resize();
});