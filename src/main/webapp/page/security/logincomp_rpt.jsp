<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
<style>
<!--
body{margin: 0; padding: 0;}
li,ul,ol{list-style: none; padding: 0; margin: 0 0 .2em 0;}
-->
</style>
</head>
<body>
<div id="rpt-toolbar" class="qdiv" >
<div style="background-color:#D2E1F0">
	<div class="qdiv-el">
	
		<form id="qfrm" name="qfrm" target="rptvw" action="">
			<input name="jsonfilter" id="jsonfilter" type="hidden" />
			<ul>
				<li><label for="bgnDate">开始日期:</label><input id="bgnDate" name="bgnDate" cp="ge" /></li>
				<li><label for="endDate">结束日期:</label><input id="endDate" name="endDate" cp="le" /></li>
				<li><label for="operUser">登录名:</label><input id="operUser" name="operUser" cp="eq" /></li>
				<li><a id="qfrm-submit" class="btn">查询</a></li>
				<li><a id="qfrm-reset" class="btn">重置</a></li>
			</ul>
		</form>
	</div>
	<div class="qdiv-el">
	    <button id="operate">操作</button>
		<ul>
				<li><a id="rptWord">导出rtf</a><div class="tip">rtf 格式支持 Microsoft Office Word 以及  Libre Office Writer 打开</div></li>
				<li><a id="rptExcel">导出excel</a></li>
				<li><a id="rptPdf">导出pdf</a></li>
				<li><a id="rptDocx">导出docx</a><div class="tip">docx 格式支持 Microsoft Office Word 以及  WPS 文字  打开</div></li>
			</ul>
	</div>
	</div>
</div>
<iframe frameborder="0" name="rptvw" id="rptvw" width="100%" scrolling="auto" ></iframe>
<script type="text/javascript">
<!--
var urls = {
 html:'loginCompStatisticReportHtml.action'
,word:'loginCompStatisticReportWord.action'
,excel:'loginCompStatisticReportExcel.action'
,pdf:'loginCompStatisticReportPdf.action'
,docx:'loginCompStatisticReportDocx.action'
};
$('.qdiv-el li a').hover(function(){$(this).next().show();},function(){$(this).next().hide();});
$('.qdiv-el li .tip').addClass('ui-state-highlight ui-corner-all').prepend('<span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>');
var openReport = function(_url, _target) {
	$('#jsonfilter').val($.jyform.jsonfilter('qfrm')); 
	$('#qfrm').attr('action',_url).attr('target',_target); 
	$('#qfrm')[0].submit();
};

// 加载页面 
$(function(){
// 输入控件渲染
$.jyform.dateBox({select:$('#bgnDate')}); $('#bgnDate').datepicker('setDate',new Date());
$.jyform.dateBox({select:$('#endDate')}); $('#endDate').datepicker('setDate',new Date());
// 查询按钮
$('#qfrm-submit').button({icons:{primary:"ui-icon-search"}}).click(function(){openReport(urls.html,'rptvw')});	
$('#qfrm-reset').button({icons:{primary:"ui-icon-refresh"}}).click(function(){$('#qfrm')[0].reset();});	
// 操作按钮
$('#rptWord').click(function(){openReport(urls.word, '_blank');});
$('#rptExcel').click(function(){openReport(urls.excel, '_blank');});
$('#rptPdf').click(function(){openReport(urls.pdf, '_blank');});
$('#rptDocx').click(function(){openReport(urls.docx, '_blank');});
$('#operate').button({ text: true, icons: { primary: "ui-icon-print" }});
// Menu 渲染
$.jyform.menulist('operate');
$.jyform.toolbarfix('rpt-toolbar');

// iframe 高度设置
// $.jyform.iframefix('rptvw');
// 加载默认页面
$('#qfrm-submit').click();
});
function afterload(){
	$('#rptvw').css({height:$('body').height()-$('#rpt-toolbar').height(),overflow:'auto'});
	$(window).resize(function(){$('#rptvw').css({height:$('body').height()-$('#rpt-toolbar').height(),overflow:'auto'});});
}
//-->
</script>
</body>
</html>