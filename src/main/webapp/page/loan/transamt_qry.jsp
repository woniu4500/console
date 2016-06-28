<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
</head>
<body>
<!-- 查询条件 -->
<div id="queryCondition">
<form id="queryForm" name="queryForm" action="#" method="post">
	<ul class="qry-ul">
		<li><label>起始时间:</label><input type="text" id="transTimeBgn" comparison="ge" name="transTime" /></li>
		<li><label>结束时间:</label><input type="text" id="transTimeEnd" comparison="le" name="transTime" /></li>
		<li>
			<label>注册费用:</label>
			<input type="text" id="registAmt" value="10" />
			<input type="hidden" name="registAmt" id="registAmtVal" class="param"/>
		</li>
		<li>
			<label>认证费用:</label>
			<input type="text" id="certAmt" value="20" />
			<input type="hidden" name="certAmt" id="certAmtVal" class="param"/>
		</li>
		<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
		<li><input value="重置" class='bt' type="reset" /></li>
	</ul>
</form>
</div>
<!-- 列表 -->
<div id="table"></div>
<script type="text/javascript">
<!--
$(function(){
$('.bt').button();
$.jyform.dateTimeBox({select:$('#transTimeBgn')}); 
$.jyform.dateTimeBox({select:$('#transTimeEnd')}); 
$.jyform.sysCodeBoxes();
$('#table').flexigrid({ 
	url : 'findTransLogAmt.action', sort : "logSeq",  dir: 'DESC',  usepager : true, checkbox : false,
	buttons: [
      {name: "导出Excel", bclass: 'grid_excel',  id : 'excel',  onpress: function() {$('#table').flexFile('findTransLogAmtExcel.action');} }
   	],
	colModel : [
	 { display : '序列号', dataIndex : 'logSeq', width : 80, align : 'center', sortable:true }
	,{ display : '操作帐号', dataIndex : 'oprAcct', width : 100, align : 'center', sortable:true }
	,{ display : '交易时间', dataIndex : 'transTime', width : 150, align : 'center', sortable:true, render:fmtTime }
	,{ display : '内部商户号', dataIndex : 'mchntCode', width : 80, align : 'center', sortable:true }
	,{ display : '银联商户号', dataIndex : 'mchntCd', width : 120, align : 'center', sortable:true }
	,{ display : '营业执照号码', dataIndex : 'licNo', width : 80, align : 'center', sortable:true }
	,{ display : '商户名称', dataIndex : 'mchntName', width : 130, align : 'center', sortable:true }
	,{ display : '交易类型', dataIndex : 'transTypeDesc', width : 150, align : 'center', sortable:true }
	,{ display : '费用信息', dataIndex : 'amount', width : 200, align : 'center', sortable:true,render:fmtMoney }
	]
});
$('#queryBtn').click(function() { 
	$('#registAmtVal').val(Math.round(($('#registAmt').val()-0)*100));
	$('#certAmtVal').val(Math.round(($('#certAmt').val()-0)*100));
	$.queryTable({ formId : 'queryForm', tableId : 'table' });
});
$('#registAmtVal').val(Math.round(($('#registAmt').val()-0)*100));
$('#certAmtVal').val(Math.round(($('#certAmt').val()-0)*100));
$.queryTable({ formId : 'queryForm', tableId : 'table' });
});
//-->
</script>

</body>
</html>