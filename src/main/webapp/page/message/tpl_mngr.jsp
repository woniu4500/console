<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="true" %>
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
		<li><label for="q_tplName">模板名称:<input type="text" id="q_tplName" comparison="like" name="tplName"/></li>
		<li><input class='bt' id="queryBtn" value="查询" type="button"></li>
		<li><input class='bt' value="重置" type="reset" /></li>
</ul>
</form>
</div>
<!-- 列表 -->
<div id="table"></div>
<!-- sended msg form -->
<div id="tplinfo" style="display: none;">
<form id="tplinfofrm">
<input type="hidden" name="version" />
<ul class='frm-ul'>
	<li><label for="d_tplId">模板ID:</label>
	<input id="d_tplId" name="tplId" readonly="readonly" type="text"/></li>

	<li><label for="d_tplName">标题:</label>
	<input id="d_tplName" name="tplName" maxLength="255" type="text" /><div id="d_tplNameTip"></div></li>

	<li><label for="d_tplRemark">备注:</label>
	<input id="d_tplRemark" name="tplRemark" maxLength="255" type="text" /><div id="d_tplRemarkTip"></div></li>

	<li><table><tr><td style="vertical-align: top"><label for="d_tplContent">内容:</label><br/>
	<button id="addParameter" class='bt'>参数</button></td><td><textarea id="d_tplContent" name="tplContent" maxLength="65535" rows="6" cols="30"></textarea><div id="d_tplContentTip"></div></td></tr></table></li>

</ul>
</form>
</div>
<!-- 预览 -->
<div id="prevtpl">
	<iframe id="prevtplview" name="prevtplview" frameborder="0" width="380" height="200"></iframe>
	<div style="display: none;"><form id="prevtplfrm" action="" method="post" ><input id="p_jsonObject" name="jsonObject"/></form></div>
</div>
<!-- 测试发送 -->
<div id="sendinfo">
<ul class='frm-ul'>
	<li><label for="s_toAddrs">测试收件地址:</label><input id="s_toAddrs" name="toAddrs" type="text" /></li>
</ul>
</div>
<!-- 设定模板 -->
<div id="setTpl">
<form id="setTplForm" action="setTpl.action" >
<input id="set_jsonObject" name="jsonObject" type="hidden" />
<input id="paramValue" type="hidden" name="paramValue" comparison="eq"/>
<ul class='frm-ul'><li><label for="paramCode">模板类型:</label><select id="paramCode"  comparison="eq" name="status">
						<option value="email.prodendcontent.template" >产品到期提醒内容模板</option>
						<option value="email.prodendtitle.template" selected="selected">产品到期提醒标题模板</option>
					</select>
				</li>
</ul>
</form>
</div>
<script type="text/javascript">
<!--
function initGridData() {$.queryTable({formId:'queryForm', tableId: 'table'});}
function clickAdd(c,n) {
	//alert($('#d_tplContent').getCursorPosition());
	$('#d_tplContent').insertAtCursor('${'+c+'!""}');
	//	alert('code:'+ c +',name:'+ n); 
}
$(function(){
// 组件初始化
$('.bt').addClass('ui-btn-sm').button(); 
$.formValidator.initConfig({ onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
$("#d_tplName").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).functionValidator({fun:chkCNLen});
$("#d_tplContent").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).functionValidator({fun:chkCNLen});
$("#d_tplRemark").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).functionValidator({fun:chkCNLen});
$('#tplinfo').dialog({autoOpen: false, bgiframe: true, modal: false, resizable: false, draggable: true, width: 500, height: 450 });
$('#setTpl').dialog({autoOpen: false, bgiframe: true, modal: false, resizable: false, draggable: true, width: 500, height: 450 });
$('#prevtpl').dialog({autoOpen: false, bgiframe: true, modal: true, resizable: false, draggable: true, width: 500, height: 450, buttons:{ '关闭': function(){$(this).dialog('close');}} });
$('#sendinfo').dialog({autoOpen: false, bgiframe: true, modal: true, resizable: false, draggable: true, width: 400, height: 250 });
// 异步加载参数信息
$.ajax({ url:'findTplParams.action', async:false ,success:function(data){
			var res=$.parseJSON(data);
			if(res.success) { 
				var _ap = $('#addParameter'); var _ul = $('<ul />');
				$.each(res.root,function(i,n) {
					_ul.append('<li><a id="'+n.paramCode+'" onclick="clickAdd(\''+n.paramCode+'\',\''+ n.paramName+'\')">' + n.paramName +'</a></li>');
				});
				_ap.after(_ul);
				$.jyform.menulist('addParameter');
			} else { alert('模板参数加载失败'); } 
}});
var previewtpl = function() {
	if ($.formValidator.pageIsValid('1')) {
		var jsonObj = new Object();
		jsonObj.tplId = $('#d_tplId').val();
		jsonObj.tplContent = $('#d_tplContent').val();
		$('#p_jsonObject').val($.toJSON(jsonObj));
		$('#prevtplfrm').attr('action','previewTpl.action').attr('target','prevtplview');
		$('#prevtplfrm')[0].submit();
		$('#prevtpl').dialog('option',{title:'预览 ' + $('#d_tplName').val() });
		$('#prevtpl').dialog('open');
	}
};
var sendtpl = function() {
	var jsonObj = new Object();
	jsonObj.toAddrs = $('#s_toAddrs').val();
	jsonObj.content = $('#d_tplContent').val();
	$.ajax({ url:'sendTestMail.action', data:{jsonObject: $.toJSON(jsonObj)}, type:'POST' ,async:false ,
		success:function(data){ 
			var res=$.parseJSON(data); if(res.success) { alert('发送成功！');} 
			else { alert('发送失败：' + res.syserr ); }
			$('#sendinfo').dialog('close');
		}});
};
var testsend = function() {
	if ($.formValidator.pageIsValid('1')) {
		$('#sendinfo').dialog('option',{title:'测试发送', buttons:{ '发送': sendtpl, '关闭': function(){$(this).dialog('close');} }});
		$('#sendinfo').dialog('open');
	}
};
var savetpl = function() {
	if ($.formValidator.pageIsValid('1')) {
		$.ajaxForm({formId : 'tplinfofrm', url: "insertTpl.action",
			success : function(data) { 
				if ( data.success ) { 
					alert('操作成功'); 
					$('#tplinfo').dialog('close'); 
					$('#table').flexAddData(data.root[0]); 
				} 
				else { alert('操作失败 原因是'+ data.syserr); }
			}
		});
	} else {return false; }
};
var updtpl = function() {
	if ($.formValidator.pageIsValid('1')) {
		$.ajaxForm({formId : 'tplinfofrm', url: "updateTpl.action",
			success : function(data) { 
				if ( data.success ) { 
					alert('操作成功'); 
					$('#tplinfo').dialog('close'); 
					$('#table').flexModifyData(data.root[0]); 
				} 
				else { alert('操作失败 原因是'+ data.syserr); }
			}
		});
	} else {return false; }
};
var newtpl = function(rec,id) {
	$("div[id$=Tip]").empty();
	$('#tplinfofrm')[0].reset();
	$('#d_tplId').val("<由系统自动生成>");		
	$('#tplinfo').dialog("option",{title:"新增模板",buttons:{'预览':previewtpl,'发送':testsend,'保存': savetpl, '关闭': function(){$(this).dialog('close');}} });
	$('#tplinfo').dialog('open');	
};
var edittpl = function(rec,id){
	if (!rec) { alert("请选择一条记录"); return; }
	$('#tplinfofrm')[0].reset();
	$.ajax({ url:'findTplDetail.action', data:{tplId:rec.tplId}, async:false
		,success:function(data){var res=$.parseJSON(data);if(res.success && res.totalProperty==1){ $.jyform.fillForm('tplinfo',res.root[0]); $('#table').flexModifyData(res.root[0]); }else { alert('模板内容加载失败'); } }});
	$('#tplinfo').dialog("option",{title:"编辑模板",buttons:{'预览':previewtpl,'发送':testsend,'保存': updtpl,'关闭': function(){$(this).dialog('close');}} });
	$('#tplinfo').dialog('open');
};


var settpl = function(rec,id){
	if (!rec) { alert("请选择一条记录"); return; }
	$('#paramValue').val(rec.tplId);
	$('#setTpl').dialog("option","title","设定模板");
	$('#setTpl').dialog("option","buttons",{
	'确定': function() {
		var jsonObj = new Object();
		jsonObj.paramCode = $('#paramCode').val();
		jsonObj.paramValue = $('#paramValue').val();
		$('#set_jsonObject').val($.toJSON(jsonObj));
		$.ajax({ url:'setTpl.action', data:{jsonObject: $.toJSON(jsonObj)}, type:'POST' ,async:false ,
			success:function(data){ 
				var res=$.parseJSON(data); if(res.success) { alert('操作成功！');} 
				else { alert('操作失败：' + res.syserr ); }
				$('#sendinfo').dialog('close');
			}});
	}, '关闭' : function() { $(this).dialog('close'); }
	});
	$('#setTpl').dialog('open');	
};
$('#tplinfo').dialog({ autoOpen: false });
$('#tplinfo').fullScreen();
$('#setTpl').dialog({ autoOpen: false });
$('#setTpl').fullScreen();
$('#table').flexigrid({ 
	url : 'findTplPage.action', sort : "lastUpdDt",  dir: 'DESC',  usepager : true, checkbox : false, dbClickRecord: edittpl,
	buttons: [
		 {name: "新增", show:'ROLE_SYSCONF_TPL_ADD', bclass: 'grid_add',  id : 'add',  onpress: newtpl, aclass:'ui-button-primary' },{separator : true }
		,{name: "修改", show:'ROLE_SYSCONF_TPL_UPD', bclass: 'grid_edit',  id: 'upd',  onpress: edittpl , aclass:'ui-button-primary'},{separator:true}
		/* ,{name: "设定模板", bclass: 'grid_edit',  id: 'set',  onpress: settpl, aclass:'ui-button-primary' },{separator:true} */
     	,{name: "导出Excel", bclass: 'grid_excel',  id : 'excel',  onpress: function() {$('#table').flexFile('findTplExcel.action');} }
   	],
	colModel : [
	 { display : 'ID', dataIndex : 'tplId', width : 40, align : 'center', sortable:true }
	,{ display : '模版名称', dataIndex : 'tplName', width : 120, align : 'center', sortable:true }
	,{ display : '备注', dataIndex : 'tplRemark', width : 120, align : 'center', sortable:true }
	,{ display : '修改时间', dataIndex : 'lastUptTime', width : 100, align : 'center', sortable:true, render: fmtTime }
	,{ display : '修改帐号', dataIndex : 'lastUptAcc', width : 80, align : 'center', sortable:true }
	]
});
$('#queryBtn').addClass('ui-button-primary').click(function(){$.queryTable({formId : 'queryForm', tableId : 'table'});});
initGridData();
});
//-->
</script>

</body>
</html>