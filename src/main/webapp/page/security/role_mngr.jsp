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
#roleDetail_div .restree{ overflow: auto; background:#eee; }
#roleDetail_div .restree h3{padding: 10px; margin: 0; font-size: 16px;font-family: "Microsoft YaHei", "NSimSun";}
-->
</style>
</head>
<body>
<!-- 查询条件 -->
<div id="queryCondition">
<form id="queryForm" name="queryForm" action="#" method="post">
	<ul class="qry-ul">
			<li><label for="q_roleId">角色编号:</label><input class="qFormInput" id="q_roleId" name="roleId" type="text" maxlength="10" qtype="string" comparison="like" /></li>
			<li><label for="q_roleName">角色名称:</label><input class="qFormInput" id="q_roleName" name="roleName" type="text" maxlength="64" qtype="string" comparison="like" /></li>
			<li><label for="q_orgName">机构名称:</label><input class="qFormInput" id="q_orgName" name="orgName" type="text" maxlength="15" qtype="string" comparison="like" /></li>
			<li><label for="q_status">角色状态:</label><SELECT id="q_status" comparison="eq" name="status">
					<OPTION value="0" selected="selected">正常</OPTION>
					<OPTION value="1">已删除</OPTION>
			</SELECT></li>
			<li><input class='bt' id="queryBtn" value="查询" type="button"></li>
			<li><input value="重置" class='bt' type="reset" /></li>
	</ul>
</form>
</div>
<div id="table"></div>
<!-- 角色信息页面 -->
<div id="roleDetail_div" style="display: none;" cellpadding="0" cellspaceing="0">
<table width="99.6%" id="dtltbl">
<tr><td width="380" style="vertical-align: top" >
<form id="form1">
<input id="orgCode" type="hidden" name="orgCode"/>
<input id="version" type="hidden" name="version"/>
<input id="status" type="hidden" name="status"/>
	<ul class='frm-ul'>
		<li><label for="roleId">角色编号:</label><input id="roleId" name="roleId"
			type="text" readonly="readonly" disabled="disabled" /></li>
		<li><label for="roleName"><font color="red">*</font>角色名称:</label><input id="roleName" name="roleName"
			type="text" />
		<div id="roleNameTip"></div></li>

		<li><label for="orgName"><font color="red">*</font>机构名称:</label><input id="orgName" name="orgName"
			type="text" />
		<div id="orgNameTip"></div></li>

		<li><label for="statusDesc">角色状态:</label><input id="statusDesc" name="statusDesc"
			type="text" readonly="readonly" disabled="disabled" /></li>
	</ul>
	</form>
</td>
<td style="vertical-align: top" class="restree">
<h3>资源信息</h3>
<div style="border: 1px solid #D3D3D3; overflow: auto" id="restree-box" >
	<div id="resourceTree"></div>
</div>
</td>
</tr>
</div>
<div id="addRole" style="display: none;"></div>
<!-- 上级机构选择弹出窗口 -->
<div id="orgSelect" style="display: none;">
<form id="q-org-frm">
<input type="hidden" name="status" value="0" comparison="eq" qtype="string" />
<ul class='qry-ul'>
		<li><label for="f_orgCode">机构代码:</label><input  type="text" id="f_orgCode" name="orgCode" comparison="like" qtype="string"/></li>
		<li><label>机构名称:</label><input type="text" name="orgName" comparison="like" qtype="string"/></li>
	    <li><input type="button" class="bt qbutton" id="querysup"  value="查询" onclick="$.queryTable({formId:'q-org-frm',tableId:'org-table'});"/></li>
	   	<li><input type="reset" class="bt" value="重置"/></li>
</ul>
<div id="org-table" class="is-grid"></div>
</form>
</div>
<script type="text/javascript">
<!--
$(function() {
	$('.bt').addClass('ui-btn-sm').button(); 
var fillOrgInfo = function(data,i) {
	if ( data && data.length > 0 ) {
		var rec = data[0];
		if ( rec ) {
			$('#orgCode').val(rec.orgCode); 
			$('#orgName').val(rec.orgName);
		}
	} else if(typeof (data)=="object"){
		var rec = data;
		if ( rec ) {
			$('#orgCode').val(rec.orgCode); 
			$('#orgName').val(rec.orgName);
			$('#orgSelect').dialog('close');
		}
	}
};
$('#orgSelect').qDialog({
	width : 700, height : 450, draggable:true, title: '选择机构',sort : "orgCode",
	flexigrid : {url : 'findOrg.action', height:180, dir : "orgCode", usepager : true
		,dbClickRecord: fillOrgInfo
		,colModel : [ 
		  {display : '机构代码',width : 80,dataIndex : 'orgCode',align : 'center',sortable:true}
		 ,{display : '机构名称',width : 150,dataIndex : 'orgName',align : 'center',sortable:true} ]
	},
	success:function(data){ 
		if (data) { fillOrgInfo(data); return true;} 
		else {alert("请选择 一条数据");return false;}
	}
});
var chooseOrg = function() {
	$('#orgSelect').dialog('open');
};
$('.bt').button();
// 上级选择机构按钮
$('#orgName').dblclick(chooseOrg);	
// Validate
$.formValidator.initConfig({ onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
$("#roleName").formValidator({ empty : false, onshow : "请输入角色名", oncorrect : "√", onfocus : "请输入角色名"}).inputValidator({ min : 1, max : 20, onerror : "角色名最多20个汉字"});
$("#orgName").formValidator({ empty : false, onshow : "双击选择机构", oncorrect : "√", onfocus : "请选择机构"});
$('#roleDetail_div').dialog({ autoOpen: false});
$('#roleDetail_div').fullScreen(function(){$('#dtltbl').fixHeight(); $('#restree-box').fixHeight({width:'330px'});});
var inited = false;
var loadTree = function(url){
	if ( inited == false ) {
		$('#resourceTree').jsTree({url:url,theme_name:'checkbox'});
		inited = true;
	} else {
		var $tree = $.tree_reference('resourceTree');
		$tree.settings.data.url=url;
		$tree.refresh();		
	}
};
// Operate
var edit = function(record,id) {				
	if (!record) { alert("请选择一条记录"); return; }
	$('#form1').fillObject({obj:record});
    $('#form1 :input').removeAttr('disabled');	
    $('#roleId').attr('disabled','disabled'); $('#statusDesc').attr('disabled','disabled');
	var urlStr= 'loadTreeWithChecked.action?jsonObject='+$.toJSON(record);
	loadTree(urlStr);
	//$('#resourceTree').jsTree({url:urlStr,theme_name:'checkbox'});
	$('#resourceTree :checkbox').removeAttr('disabled');
	$('#roleDetail_div').dialog( "option", "title", "编辑角色信息");
	$('#roleDetail_div').dialog("option","buttons",	{
		'更新': function(){										
			if ($.formValidator.pageIsValid('1')) {												
				var selectRes="";
				$('#resourceTree a.undetermined,#resourceTree a.checked').each(function(i){selectRes=selectRes+$(this).parent().attr('id')+",";});	
				if(selectRes==""){alert("资源不能为空");return;}
				$.ajaxForm({formId : 'form1', url : 'doUpdateRole.action',
					dealData:function(data){data.resource=selectRes;},									
					success : function(data) {
						if (data.success) {$('#roleDetail_div').dialog('close');$('#table').flexModifyData(data.root[0]);alert('操作成功');} 
						else {alert('操作失败 原因是'+ data.syserr);}
					}
				});
			} else {return false; }									
		},'关闭': function() {$(this).dialog('close');}});
		$('#roleDetail_div').dialog('open');			
};
//
var add=function() {
	$('#form1').fillObject({obj:null});
	$('#form1')[0].reset();
	$('#form1 :input').removeAttr('disabled');
	$('#roleId').val("<由系统自动生成>").attr('style','font-style:italic').attr('disabled','disabled');				
	$('#statusDesc').val("正常").attr('style','font-style:italic').attr('disabled','disabled');				
	var urlStr= 'loadTreeWithUnChecked.action';
	loadTree(urlStr);
	//$('#resourceTree').html('');
	//$('#resourceTree').jsTree({url:urlStr,theme_name:'checkbox'});		
	$('#resourceTree :checkbox').removeAttr('disabled');
	$('#roleDetail_div').dialog("option","title","新增角色");
	$('#roleDetail_div').dialog("option","buttons",	{
		'确定' : function() {
			if ($.formValidator.pageIsValid('1')) {
				var selectRes="";
				$('#resourceTree a.undetermined,#resourceTree a.checked').each(function(i){selectRes=selectRes+$(this).parent().attr('id')+",";});
				if(selectRes==""){alert("资源不能为空");return;}
				$.ajaxForm({formId : 'form1',url : "doInsertRole.action",
					dealData : function(data) {data.resource=selectRes;},
					success : function(data) {
						if (data.success) { $('#roleDetail_div').dialog('close'); $('#table').flexAddData(data.root[0]); alert('操作成功');} 
						else { alert('操作失败 原因是'+ data.syserr); }
					}
				});
			} else {return false;}
		}, '关闭' : function() {$(this).dialog('close');}
	});
	$('#roleDetail_div').dialog('open');
};
	
var view=function(record,id) {
	if (!record) {alert("请选择一条记录"); return; }
	$('#form1').fillObject({obj:record});
	$('#form1 :input').attr('disabled','disabled');
	var urlStr= 'loadTreeWithChecked.action?jsonObject='+$.toJSON(record);
	loadTree(urlStr);
	//$('#resourceTree').html('');
	//$('#resourceTree').jsTree({url:urlStr,theme_name:'checkbox'});
	$('#resourceTree :checkbox').removeAttr('disabled');
	$('#roleDetail_div').dialog("option","title","详细信息");
	$('#roleDetail_div').dialog("option","buttons",{
		'关闭' : function() {
			$('#resourceTree :checkbox').removeAttr('disabled');
			$('#form1 :input').removeAttr('disabled');
			$(this).dialog('close');
		}
	});
	$('#roleDetail_div').dialog('open');
};

var del= function(record,id) {
	if (!record) { alert("请选择一条记录"); return; }
	if (!confirm("确认要删除该角色吗？")) { return; }
	var obj = new Object();
	obj.roleId = record.roleId ;
	obj.version = record.version ;
	$.ajax({type : 'POST', url : 'doDeleteRole.action',
		data : {jsonObject : $.toJSON(obj)},
		success : function(	data) {
			var res = $.parseJSON(data);
			if (res.success) { alert('操作成功'); $('#table').flexRemoveData(res.root[0]); } 
			else { alert('操作失败 原因是'+ res.syserr); }
		}
	});
}

$('#table').flexigrid(	{
	url : 'findRole.action', sort : "roleId", checkbox : false, usepager : true, dbClickRecord: edit,
	buttons : [
	 { name : "新增角色", show : 'ROLE_SEC_ROLE_ADD', bclass : 'grid_add', id : 'txt', onpress : add,aclass:'ui-button-primary' }
	,{ name : "编辑角色", show : 'ROLE_SEC_ROLE_UPDATE', bclass : 'grid_edit', id : 'cel', onpress : edit,aclass:'ui-button-primary' }
	,{ name : "详细信息", bclass : 'grid_view', id : 'cel', onpress : view,aclass:'ui-button-primary' }
	,{ name : "删除", show : 'ROLE_SEC_ROLE_DEL', bclass : 'grid_del', id : 'txt', onpress :del,  id: 'del',  onpress :del, aclass:'ui-button-danger' }
	,{name: "导出Excel", bclass: 'grid_excel',  id : 'excel',  onpress: function() {$('#table').flexFile('findRoleExcel.action');} }
	],
	colModel : [
     {display: '角色编号', width: 100, dataIndex: 'roleId', align: 'center', sortable: true }
    ,{display: '角色名称', width: 250, dataIndex: 'roleName', align: 'center', sortable: true }
    ,{display: '机构名称', width: 340, dataIndex: 'orgName', align: 'center', sortable: true }
    ,{display: '角色状态', width: 90, dataIndex: 'statusDesc',  align: 'center', sortable: true }           
	]
});
$.jyform.multiSelectBox('q_status');
$('#queryBtn').addClass('ui-button-primary').click(function(){$.queryTable({formId : 'queryForm', tableId : 'table'});});
$.queryTable({ formId : 'queryForm', tableId : 'table' });
});
//-->
</script>
</body>
</html>