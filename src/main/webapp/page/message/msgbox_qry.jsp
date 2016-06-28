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
	<li><label for="q_sendTimeBgn">发送时间  开始:</label>
	<input type="text" id="q_sendTimeBgn" maxlength="14" comparison="ge" name="sendTime"/></li>
		
	<li><label for="q_sendTimeEnd">发送时间  结束:</label>
	<input type="text" id="q_sendTimeEnd" maxlength="14" comparison="le" name="sendTime"/></li>
		
	<li><label for="q_msgTitle">标题:</label>
	<input type="text" id="q_msgTitle" maxlength="20" comparison="eq" name="msgTitle"/></li>

	<li><label for="q_readFlag">状态:</label><select id="q_readFlag" ctype="MSG_READ_FLAG" comparison="eq" name="readFlag" ></select></li>
	<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
		
	<li><input class='bt' value="重置" type="reset" /></li>
</ul>
</form>
</div>

<!-- 列表 -->
<div id="table"></div>
<!-- sended msg form -->
<div id="msginfo" style="display: none;">
<form id="msginfofrm">
<ul class='frm-ul'>
	<li><label for="msgTitle">标题:</label>
	<input name="msgTitle" readonly="readonly" /></li>

	<li><label for="sendAcct">作者:</label>
	<input name="sendAcct" readonly="readonly"/></li>

	<li><label for="msgContent">消息内容:</label>
	<textarea style="height:100px;width:300px" name="msgContent" readonly="readonly"></textarea></li>

	<li><label for="showFlagDesc">显示标识:</label>
	<input name="showFlagDesc" readonly="readonly" /></li>

	<li><label for="sendTime">发送时间:</label>
	<input id="sendTime" name="sendTime" readonly="readonly"/></li>

	<li><label for="expireTime">过期时间:</label>
	<input id="expireTime" name="expireTime" readonly="readonly"/></li>

	<li><label for="readFlagDesc">状态:</label>
	<input name="readFlagDesc" readonly="readonly"/></li>
</ul>
</form>
</div>
<script type="text/javascript">
<!--
function initGridData() {$.queryTable({formId:'queryForm', tableId: 'table'});}
$(function(){
// 组件初始化
$('.bt').button();
$.jyform.dateTimeBox({select:$('#q_sendTimeBgn')}); 
$.jyform.dateTimeBox({select:$('#q_sendTimeEnd')});
$('#msginfo').dialog({autoOpen: false, bgiframe: true, modal: false, resizable: false, draggable: true, width: 600, height: 385 });

var readmsg = function(rec,id) {
	$('#msginfofrm')[0].reset();
	if (!rec) { alert("请选择一条记录"); return; }
	$.ajax({ url:'readMsg.action', data:{msgId:rec.msgId}, async:false
		,success:function(data){var res=$.parseJSON(data);if(res.success && res.totalProperty==1){ $.jyform.fillForm('msginfo',res.root[0]); $('#table').flexModifyData(res.root[0]); }else { alert('消息内容加载失败'); } }});
	$('#expireTime').val(fmtTime(null,$('#expireTime').val()));
	$('#sendTime').val(fmtTime(null,$('#sendTime').val()));
	$('#msginfo').dialog("option",{"title":"消息详情","buttons":{'关闭': function(){$(this).dialog('close');}} });
	$('#msginfo').dialog('open');
}
var delmsg = function(rec,id){
	if (!rec) { alert("请选择一条记录"); return; }
	if (!confirm("确认要删除该消息?")) { return; }
	$.ajax({type : 'POST', url : 'recycleMsg.action', data : {msgId : rec.msgId},
		success : function(data) {
			var res = $.parseJSON(data);
			if (res.success) { alert('操作成功'); $('#table').flexRemoveData(res.root[0]); } 
			else { alert('操作失败 原因是'+ res.syserr); }
		}
	});
}
$('#msginfo').dialog({ autoOpen: false });
$('#msginfo').fullScreen();
$('#table').flexigrid({ 
	url : 'findRecvMsgPage.action', sort : "lastUpdDt",  dir: 'DESC',  usepager : true, checkbox : false, dbClickRecord: readmsg,
	buttons: [
		 {name: "查看信息", bclass: 'grid_edit',  id : 'cel',  onpress: readmsg, aclass:'ui-button-primary' },{separator : true }
		,{name: "删除", show:'ROLE_SYSCONF_SENDBOX_DEL', bclass: 'grid_del',  id: 'del',  onpress: delmsg, aclass:'ui-button-danger' },{separator:true}
     	,{name: "导出Excel", bclass: 'grid_excel',  id : 'excel',  onpress: function() {$('#table').flexFile('findRecvMsgExcel.action');} }
   	],
	colModel : [
	 { display : 'ID', dataIndex : 'msgId', width : 40, align : 'center', sortable:true }
	,{ display : '标题', dataIndex : 'msgTitle', width : 200, align : 'center', sortable:true }
	,{ display : '作者', dataIndex : 'sendAcct', width : 80, align : 'center', sortable:true }
	,{ display : '状态', dataIndex : 'readFlagDesc', width : 80, align : 'center', sortable:true }
	,{ display : '发送时间', dataIndex : 'sendTime', width : 150, align : 'center', sortable:true, render: fmtTime }
	,{ display : '过期时间', dataIndex : 'expireTime', width : 150, align : 'center', sortable:true, render: fmtTime  }
	]
});
$.jyform.sysCodeBoxes();
$('#queryBtn').addClass('ui-button-primary').click(function(){$.queryTable({formId : 'queryForm', tableId : 'table'});});
// $.queryTable({ formId : 'queryForm', tableId : 'table' });
});
//-->
</script>

</body>
</html>