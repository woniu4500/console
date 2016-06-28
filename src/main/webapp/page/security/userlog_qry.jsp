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
	<li><label for="oprTimeBgn">起始时间:</label><input type="text" id="oprTimeBgn" comparison="ge" name="oprTime" /></li>
	<li><label for="oprTimeEnd">结束时间:</label><input type="text" id="oprTimeEnd" comparison="le" name="oprTime" /></li>
	<li><label for="resZh">操作名称:</label><input type="text" id="resZh" maxlength="20" comparison="like" name="resZh" /></li>
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
// $('#infoDtlQuery').dialog({ title:"操作员日志", autoOpen : false, bgiframe : true, modal : true, resizable : false, draggable : false, width : 700, height : 400 });
$('.bt').button();
$('#table').flexigrid({ 
	url : 'findOprlogUser.action', sort : "oprNo", dir: 'DESC',  usepager : true, 
	buttons: [
       {name: "导出Excel", bclass: 'grid_excel',  id : 'excel',  onpress: function() {$('#table').flexFile('findOprlogUserExcel.action');} }
    ],
	colModel : [
	  { display : '序号', dataIndex : 'oprNo', width : 80, align : 'center', sortable:true }
	 ,{ display : '操作时间', dataIndex : 'oprTime', width : 100, align : 'center', sortable:true, render: fmtTime }
	 ,{ display : '操作员登录名', dataIndex : 'oprLoginId', width : 100, align : 'center' }
	 ,{ display : '操作员姓名', dataIndex : 'oprUserName', width : 100, align : 'center' }
	 ,{ display : '操作名称', dataIndex : 'resZh', width : 150, align : 'center', sortable:true }
	 ,{ display : '操作所在IP', dataIndex : 'oprIp', width : 100, align : 'center' }
	 ,{ display : '操作结果', dataIndex : 'oprSuccess', width : 100, align : 'center', sortable:true }
	 ,{ display : '备注', dataIndex : 'oprRemark', width : 200, align : 'center', sortable:true }
	]
});
$('#queryBtn').click(function() { $.queryTable({ formId : 'queryForm', tableId : 'table' });});
$.queryTable({ formId : 'queryForm', tableId : 'table' });	
$.jyform.dateTimeBox({select:$('#oprTimeBgn')});
$.jyform.dateTimeBox({select:$('#oprTimeEnd')});

});
//-->
</script>

</body>
</html>