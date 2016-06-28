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

		<li><label for="q_status">状态:</label><select id="q_status" ctype="MSG_STATUS" comparison="eq" name="status" ></select></li>
		<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
		
		<li><input class='bt' value="重置" type="reset" /></li>
</ul>
</form>
</div>
<!-- 列表 -->
<div id="table"></div>
<!-- draft msg form -->
<div id="draftmsg" style="display: none;">
<form id="draftfrm">
<input type="hidden" id="version" name="version"/>
<input type="hidden" id="status" name="status"/>
<ul class='frm-ul'>
	<li><label for="d_msgId">消息ID:</label>
	<input id="d_msgId" name="msgId" disabled="disabled" style="font-style: italic;" type="text" /></li>
	
	<li><label for="d_msgTitle"><font color="red">*</font>标题:</label>
	<input id="d_msgTitle" name="msgTitle" maxLength="255"  type="text" /><div id="d_msgTitleTip"></div></li>
	
	<li><label for="d_msgContent"><font color="red">*</font>消息内容:</label>
	<textarea id="d_msgContent" style="height:100px;width:300px" name="msgContent" maxLength="65535" ></textarea><div id="d_msgContentTip"></div></li>
	
	<li><label for="d_showFlag"><font color="red">*</font>显示标识:</label>
	<select id="d_showFlag" name="showFlag"></select><div id="d_showFlagTip"></div></li>
	
	<li><label for="d_expireTime"><font color="red"></font>过期时间:</label>
	<input id="d_expireTime" name="expireTime" type="text" /><div id="d_expireTimeTip"></div></li>
</ul>
</form>
</div>
<!-- sended msg form -->
<div id="sendedmsg" style="display: none;">
<form id="sendedfrm">
<ul class='frm-ul'>
	<li><label for="msgTitle">标题:</label>
	<input name="msgTitle" readonly="readonly" type="text" /></li>

	<li><label for="sendAcct">作者:</label>
	<input name="sendAcct" readonly="readonly" type="text"/></li>

	<li><label for="msgContent">消息内容:</label>
	<textareaname="msgContent" style="height:100px;width:300px" readonly="readonly" type="text"></textarea></li>

	<li><label for="showFlagDesc">显示标识:</label>
	<input  name="showFlagDesc" readonly="readonly"  type="text"/></li>

	<li><label for="v_expireTime">过期时间:</label>
	<input id="v_expireTime" name="expireTime" readonly="readonly" type="text"/></li>

	<li><label for="v_sendTime">发送时间:</label>
	<input id="v_sendTime" name="sendTime" readonly="readonly" type="text"/></li>

	<li><label for="statusDesc">状态:</label>
	<input name="statusDesc" readonly="readonly" type="text"/></li>
</ul>
</form>
	<div class="restree"><div id="recvGrid" ></div></div>
</div>
<!-- choose recv user form -->
<div id="recvchoose" style="display: none;">
	<div id="recvChooseGrid"></div>
</div>
<script type="text/javascript">
<!--
function initGridData() {$.queryTable({formId:'queryForm', tableId: 'table'});}
$(function(){
// 组件初始化
$('.bt').addClass('ui-btn-sm').button(); 
$.jyform.dateTimeBox({select:$('#q_sendTimeBgn')}); 
$.jyform.dateTimeBox({select:$('#q_sendTimeEnd')});
$.jyform.dateTimeBox({select:$('#d_expireTime')});
$.jyform.syscodeBox({selectId:'d_showFlag',ctype:'MSG_SHOW_FLAG'});

$.formValidator.initConfig({ onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
$("#d_msgTitle").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).functionValidator({fun:chkCNLen});
$("#d_msgContent").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).functionValidator({fun:chkCNLen});
$("#d_showFlag").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" });
//$("#d_expireTime").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" });

$('#recvChooseGrid').flexigrid({
	url : 'findUserCombo.action', limit: 100, width: 400, height:230, 
	checkbox: true, usepager: false, 				
	colModel : [
	 {display: 'ID', width: 40, dataIndex: 'userId', align: 'center' }
	,{display: '用户名', width: 150, dataIndex: 'userName', align: 'center' }
	,{display: '登录名', width: 150, dataIndex: 'loginId', align: 'center' }
	]
});
$('#recvChooseGrid').flexReload(new Array(),new Array());
$('#recvGrid').flexigrid({
	url : 'findMsgRecvList.action', limit: 100, width: 300, height:170, usepager: false, 				
	colModel : [
	  { display : '收件人', dataIndex : 'recvName', width : 100, align : 'center', sortable:true }
	 ,{ display : '帐号', dataIndex : 'recvAcct', width : 100, align : 'center', sortable:true }
	 ,{ display : '信件状态', dataIndex : 'readFlagDesc', width : 60, align : 'center', sortable:true }
	]
});

$('#draftmsg').dialog({autoOpen: false, bgiframe: true, modal: false, resizable: false, draggable: true, width: 600, height: 430 });
$('#sendedmsg').dialog({autoOpen: false, bgiframe: true, modal: false, resizable: false, draggable: true, width: 800, height: 620 });
$('#recvchoose').dialog({title:'收件人选择',autoOpen: false, bgiframe: true, modal: true, resizable: false, draggable: true, width: 450, height: 420,
	buttons:{'发送':function(){
			var recvIds=""; var data = $('#recvChooseGrid').flexAllSelectData(); $.each(data,function(i) { if (i == 0) {recvIds = recvIds + this.userId} else { recvIds = recvIds + ',' + this.userId}}) ;
			if( recvIds == "" ){ alert("请选择收件人"); return; }
			$.ajaxForm({formId : 'draftfrm', url : "saveSendMsg.action",
				dealData : function(data) {data.recvId=recvIds;},
				success : function(data) { 
					if ( data.success ) { 
						alert('操作成功');  
						if ($('#d_msgId').val() == '<由系统自动生成>' ) { $('#table').flexAddData(data.root[0]);  } else { $('#table').flexModifyData(data.root[0]); }
					} else { alert('操作失败 原因是'+ data.syserr); }
					$('#recvchoose').dialog('close'); 
					$('#draftmsg').dialog('close');
				}
			});
		},'取消':function(){
			$('#draftmsg').dialog('close');
			$('#recvchoose').dialog('close');
		}
	}});

// 方法定义
// 保存消息草稿
var savemsg = function() {
	if ($.formValidator.pageIsValid('1')) {
		$.ajaxForm({formId : 'draftfrm', url: "saveMsgDraft.action",
			success : function(data) { 
				if ( data.success ) { 
					alert('操作成功'); 
					$('#draftmsg').dialog('close'); 
					if ($('#d_msgId').val() == '<由系统自动生成>' ) {
						$('#table').flexAddData(data.root[0]); 
					} else {
						$('#table').flexModifyData(data.root[0]);
					}
				} 
				else { alert('操作失败 原因是'+ data.syserr); }
			}
		});
	} else {return false; }
};
// 发送消息：选择收件人发送
var sendmsg = function() {
	if ($.formValidator.pageIsValid('1')) {
		$('#recvchoose').dialog('open');
	} else {return false; }
};
var addmsg = function () {
	$("div[id$=Tip]").empty();
	$('#draftfrm')[0].reset();
	$('#d_msgId').val("<由系统自动生成>");		
	$('#draftmsg').dialog("option",{"title":"新增消息","buttons":{'保存': savemsg, '发送': sendmsg, '关闭': function(){$(this).dialog('close');}} });
	$('#draftmsg').dialog('open');	
} ;
var editmsg = function (rec,id) {
	if (!rec) { alert("请选择一条记录"); return; }
	if ( rec.status == '1' ) {
		$("div[id$=Tip]").empty();
		$('#draftfrm')[0].reset();
		$.ajax({ url:'findMsgDetail.action', data:{msgId:rec.msgId}, async:false
			,success:function(data){var res=$.parseJSON(data);if(res.success && res.totalProperty==1){ $.jyform.fillForm('draftfrm',res.root[0]); }else { alert('消息内容加载失败'); } }});
		$('#draftmsg').dialog("option",{"title":"编辑消息","buttons":{'保存': savemsg, '发送': sendmsg, '关闭': function(){$(this).dialog('close');}} });
		$('#draftmsg').dialog('open');
	} else {
		$('#sendedfrm')[0].reset();
		$.ajax({ url:'findMsgRecvList.action', data:{msgId:rec.msgId}, async:false, success:function(data){var res=$.parseJSON(data);if(res.success) $('#recvGrid').refreshWithData(res); }});
		$.ajax({ url:'findMsgDetail.action', data:{msgId:rec.msgId}, async:false
			,success:function(data){var res=$.parseJSON(data);if(res.success && res.totalProperty==1){ $.jyform.fillForm('sendedfrm',res.root[0]); }else { alert('消息内容加载失败'); } }});
		$('#v_expireTime').val(fmtTime(null,$('#v_expireTime').val()));
		$('#v_sendTime').val(fmtTime(null,$('#v_sendTime').val()));
		$('#sendedmsg').dialog("option",{"title":"消息详情","buttons":{'关闭': function(){$(this).dialog('close');}} });
		$('#sendedmsg').dialog('open');
	}
} ;
var delmsg = function(rec,id) {
	if (!rec) { alert("请选择一条记录"); return; }
	if (!confirm("确认要删除该消息?")) { return; }
	var obj = new Object();
	obj.msgId = rec.msgId; obj.version=rec.version;
	$.ajax({type : 'POST', url : 'deleteMsgDraft.action', data : {jsonObject : $.toJSON(obj)},
		success : function(data) {
			var res = $.parseJSON(data);
			if (res.success) { alert('操作成功'); $('#table').flexRemoveData(res.root[0]); } 
			else { alert('操作失败 原因是'+ res.syserr); }
		}
	});
};
$('#draftmsg').dialog({ autoOpen: false });
$('#draftmsg').fullScreen();
$('#sendedmsg').dialog({ autoOpen: false });
$('#sendedmsg').fullScreen();
$('#table').flexigrid({ 
	url : 'findSendMsgPage.action', sort : "lastUpdDt",  dir: 'DESC',  usepager : true, checkbox : false, dbClickRecord: editmsg,
	buttons: [
		 {name: "新消息", show:'ROLE_SYSCONF_SENDBOX_SEND|ROLE_SYSCONF_SENDBOX_SAVE', bclass: 'grid_add', id: 'txt', onpress: addmsg, aclass:'ui-button-primary' },{separator : true }
		,{name: "详细信息", bclass: 'grid_edit',  id : 'cel',  onpress: editmsg, aclass:'ui-button-primary' },{separator : true }
		,{name: "删除", show:'ROLE_SYSCONF_SENDBOX_DEL', bclass: 'grid_del',  id: 'del',  onpress: delmsg, aclass:'ui-button-danger' },{separator:true}
     	,{name: "导出Excel", bclass: 'grid_excel',  id : 'excel',  onpress: function() {$('#table').flexFile('findSendMsgExcel.action');} }
   	],
	colModel : [
	 { display : 'ID', dataIndex : 'msgId', width : 40, align : 'center', sortable:true }
	,{ display : '标题', dataIndex : 'msgTitle', width : 200, align : 'center', sortable:true }
	,{ display : '作者', dataIndex : 'sendAcct', width : 80, align : 'center', sortable:true }
	,{ display : '状态', dataIndex : 'statusDesc', width : 80, align : 'center', sortable:true }
	,{ display : '显示', dataIndex : 'showFlagDesc', width : 80, align : 'center', sortable:true }
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