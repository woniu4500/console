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
			<li><input class='bt' id="queryBtn" value="查询" type="button" onclick="$.queryTable({formId:'queryForm', tableId:'table'});"/></li>
			<li><input value="重置" class='bt' type="reset" /></li>
		</ul>
	</form>
</div>
<!-- 列表 -->
<div id="table"></div>
<!-- 新增表单 -->
<div id="addForm" style="display: none;">
<form id="addForm1" action="#" name="af">
	<input id="version" type="hidden" name="version"/>
	<input id="status" type="hidden" name="status"/>
	<input id="orgType" type="hidden" name="orgType" value="10"/>
	<input id="fatherOrgCode" type="hidden" name="fatherOrgCode"/>
	<ul class='frm-ul'></ul>
</form>
</div>
<!-- 上级机构选择弹出窗口 -->
<div id="fatherOrgSelect" >
<form id="q-org-frm" name="q-org-frm" action="#" method="post">
<input type="hidden" name="status" value="0" comparison="eq" qtype="string" />
<input type="hidden" name="orgid" class="param" id="fatherOrgSelect-orgid" comparison="eq" qtype="string" />
<ul class="qry-ul">
	<li><input class='bt' id="qryOrgBtn" value="查询" type="button" onclick="$.queryTable({formId:'q-org-frm',tableId:'org-table'});"/></li>
	<li><input value="重置" class='bt' type="reset" /></li>
</ul>
<div id="org-table" class="is-grid"></div>
</form>
</div>

<script type="text/javascript">
<!--
// url
var url = {
grid: 'findOrg.action',
excel:'findOrgExcel.action',
add:'doInsertOrg.action',
upd:'doUpdateOrg.action',
del:'doDeleteOrg.action',
sgrid:'findFatherOrgCombo.action'
};
// fields definition
var formbuilder = new LightFormBuilder([
 {name:'orgCode', desc:'机构代码'  , jtype:'string', length: 30, nvl:true}
,{name:'version', desc:'VERSION'  , jtype:'number', length: 10}
,{name:'fatherOrgCode', desc:'上级机构代码'  , jtype:'string', length: 30, nvl:true}
,{name:'fatherOrgName', desc:'上级机构'  , jtype:'string', length: 100, nvl:true}
,{name:'orgName', desc:'机构名称'  , jtype:'string', length: 100, nvl:true}
,{name:'orgType', desc:'机构类型'  , jtype:'string', length: 10}
,{name:'persName', desc:'联系人'  , jtype:'string', length: 20}
,{name:'persPhone', desc:'联系人电话'  , jtype:'string', length: 50}
,{name:'email', desc:'EMAIL', jtype:'string', length: 200}
,{name:'orgAddr', desc:'地址', jtype:'string', length: 200}
,{name:'status', desc:'状态', jtype:'string', length: 1}
,{name:'statusDesc', desc:'状态', jtype:'string', length: 100}
,{name:'lastUptTime', desc:'最后更新时间' , jtype:'datetime', length: 14}
,{name:'lastUptAcc', desc:'最后更新账户', jtype:'string', length: 30}
,{name:'lastUptOrg', desc:'最后更新机构', jtype:'string', length: 30}
]);

var fillFatherOrgInfo = function(data,i) {
	if ( data && data.length > 0 ) {
		var rec = data[0];
		if ( rec ) {
			$('#fatherOrgCode').val(rec.orgCode); 
			$('#fatherOrgName').val(rec.orgName);
		}
	} else if(typeof (data)=="object"){
		var rec = data;
		if ( rec ) {
			$('#fatherOrgCode').val(rec.orgCode); 
			$('#fatherOrgName').val(rec.orgName);
			$('#fatherOrgSelect').dialog('close');
		}
	}
};
var chooseFatherOrg = function() {
	var orgid = $('#orgCode').val();
	if ( orgid ) {$('#fatherOrgSelect-orgid').val(orgid);}
	$('#fatherOrgSelect').dialog('open');
};
//operate
// add new org
var add = function() {
	$("div[id$=Tip]").empty();
	$('#addForm1 :input').removeAttr('disabled');
	$('#addForm1>select').removeAttr('disabled');
	$('#addForm1')[0].reset();
	$('#addForm').dialog("option","title","新增机构");
	$('#addForm').dialog("option","buttons",{
	'确定' : function() {
		if($.formValidator.pageIsValid('1')) {
			// if(!$('#fatherOrgCode').val()){ alert('上级机构必选择'); return; }
			$.ajaxForm({formId : 'addForm1', url : url.add,
			success : function(data) {
				if (data.success) { $('#addForm').dialog('close'); alert('操作成功'); 
					if ( data.root && data.root[0] ) $('#table').flexAddData(data.root[0]);
				} else { alert('操作失败 原因是'+ data.syserr); }
			}});
		} else { return false; }}
	,'关闭' : function() { $(this).dialog('close'); } });
	$('#addForm').dialog('open');
};
// edit org info 
var edit = function(record,id) {
	$("div[id$=Tip]").empty();
	$('#addForm1 input[name="orgCode"]').attr('disabled','true');
	if (!record) {alert("请选择一条记录"); return;}
	$('#addForm1')[0].reset();
	$('#addForm1 :input').each(function(i,n) { if (n.name in record) {n.value = record[n.name]; } });
	$('#addForm').dialog("option","title","编辑机构信息");
	$('#addForm').dialog("option","buttons",{
	'更新' : function() { 
		if ($.formValidator.pageIsValid('1')) {
			$.ajaxForm({formId: 'addForm1', url: url.upd,
			dealData: function(data) { data.orgCode = record.orgCode; },
			success: function(data) {
				if (data.success) {$('#addForm').dialog('close');
					$('#table').flexModifyData(data.root[0]); alert('操作成功'); 
				} else {alert('操作失败 原因是'+ data.syserr); }
			}});
		} else { return false; }
	}, '关闭' : function() {$(this).dialog('close'); }});
	$('#addForm').dialog('open');
};
// delete org
var del = function(record,id) {
	if (!record) { alert("请选择一条记录"); return; }
	if (!confirm("确认要删除该机构吗？")) { return; }
	var obj = {orgCode:recode.orgCode, version:record.version};
	$.ajax({type: 'POST', url: url.del, data: {jsonObject : $.toJSON(obj)}, dataType:'json',
	success : function(data) {
		if (data.success) { $('#table').flexRemoveData(data.root[0]); alert('操作成功'); } else { alert('操作失败 原因是'+ data.syserr);}
	}});
};
// var initGridData = function() {$.queryTable({formId:'queryForm', tableId: 'table'});}
$(function() {
	//
	formbuilder.qryList('queryForm',['orgCode','orgName',{name:'status',xtype:'select',ctype:'PUB_STATUE',def:'0'}]);
	formbuilder.qryList('q-org-frm',['orgCode','orgName']);
	// build form content
	$.formValidator.initConfig({ onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
	formbuilder.frmList('addForm1',['orgCode','orgName',{name:'fatherOrgName',id:'fatherOrgName',readonly:'readonly'},'persName','persPhone','email',{name:'orgAddr',xtype:'textarea'}]);
	
	// 上级选择机构按钮
	$('#fatherOrgName').dblclick(chooseFatherOrg);
	$('#fatherOrgSelect').qDialog({
		width: 600, height: 460, title: '选择上级机构',
		flexigrid: {url: url.sgrid, height:240, dir: "orgCode", usepager: false
			,dbClickRecord: fillFatherOrgInfo
			,colModel: formbuilder.getColumns('forg-grid', ['orgCode',{name:'orgName',width:200},'persName','persPhone','email'])
		},
		success:function(data){ 
			if (data) { fillFatherOrgInfo(data); return true;} 
			else {alert("请选择 一条数据");return false;}
		}
	});
	$('#addForm').dialog({ autoOpen: false });
	$('#addForm').fullScreen();

	// datagrid
	$('#table').flexigrid({
		url : url.grid, sort : "orgCode", checkbox: false, usepager: true, dbClickRecord: edit,
		buttons : [
		 {name: "新增", show:'ROLE_SEC_ORG_ADD', 	bclass: 'grid_add', 	onpress: add, 	aclass:'ui-button-primary' }
		,{name: "编辑", show:'ROLE_SEC_ORG_UPDATE', bclass: 'grid_edit', 	onpress: edit, 	aclass:'ui-button-primary' }
		,{name: "删除", show:'ROLE_SEC_ORG_DEL', 	bclass: 'grid_del', 	onpress: del, 	aclass:'ui-button-danger'  }
		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		],
		colModel: formbuilder.getColumns('grid', ['orgCode',{name:'orgName',width:250},'persName','persPhone','email','orgAddr','statusDesc'],{width:100})
	});
	//
	$.jyform.sysCodeBoxes();
	// buttons
	$('.qry-ul input[type="button"]').addClass('ui-button-primary');
	$('.bt').addClass('ui-btn-sm').button();
	$('#queryForm .qry-ul input[type="button"]').click();
	
});
//-->
</script>
</body>
</html>