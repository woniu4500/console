<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
</head>
<body>
<div id="update" style="width:100%;">
<h2>系统参数设置</h2>
<form id="paramfrm">
<table id="paramfrmTbl" class="ftable"  cellpadding="1" cellspacing="1" bgcolor="#8392a5" width="100%">

</table>
<div style="margin-top: 1.2em;">
	<a id="saveParams" class="bt" href="#">保存</a>
	<a id="refresh" class="bt" href="#">刷新</a>
</div>
</form>
</div>
<script type="text/javascript">
<!--
$(function(){
// 组件初始化
$('.bt').button();
	var load = function(){
	$.ajax({url:'findSysParamList.action',async:false, success:function(data){
		var res=$.parseJSON(data);
		if(res.success) { 
			var _tbl = $('#paramfrmTbl');
			_tbl.html('');
			$.formValidator.initConfig({ onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
			$.each(res.root,function(i,n) {
				if(n.paramCode.indexOf('theme') == -1){
				var _tr = $('<tr />');
				var _id = n.paramCode.replace(/\./gm,'-');
				var _tdinput = $('<input class="cdnInput" />')
					.attr('id', _id)
					.attr('version', n.version)
					.attr('name',n.paramCode).css({'width':'250px'}).val(n.paramValue);
			
				_tr.append($('<td />').addClass('flable').attr('width','250').html(n.paramDesc + '(' + n.paramCode + ')'))
					.append($('<td />').addClass('fcontent').append(_tdinput).append($('<div id="'+_id+'Tip"/>')));
				_tbl.append(_tr);
				if ( n.paramModifyFlag == '0') {
					_tdinput.attr('readonly','readonly');				
				} else {
					$('#' + _id).formValidator({empty:false, onshow:"请输入内容", oncorrect: "√"});
				}
			}});
		} else { alert('系统参数加载失败'); } 	
	} });
};
load();
$('#refresh').click(function(){
	load();
});	
// <security:authorize ifAnyGranted="ROLE_SYSCONF_PARM_UPD">
$('#saveParams').click(function(){
	if ($.formValidator.pageIsValid('1')) {
		var params = new Array();
		$('#paramfrmTbl .cdnInput').each(function(i,n){
			var param = new Object;
			param.paramCode = $(n).attr('name');
			param.version = $(n).attr('version');
			param.paramValue = $(n).val();
			params.push(param);
		});
		$.ajax({type : 'POST', url : 'updateSysParam.action', data : {jsonArray: $.toJSON(params)},
			success : function(data) {
				var res = $.parseJSON(data);
				if (res.success) {
					alert('操作成功'); 
					if ( res.totalProperty > 0 ) { load(); }
				} else { alert('操作失败 原因是'+ res.syserr); }
			}
		});
	} else {
		return false;
	}
});	
// </security:authorize>

});
//-->
</script>

</body>
</html>