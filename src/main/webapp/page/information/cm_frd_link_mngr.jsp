<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
<script type="text/javascript" src="r/plugins/kindEditor/kindeditor-all.js"></script>
<link type="text/css" rel="stylesheet" href="r/plugins/kindEditor/themes/default/default.css" />
</head>
<body>
<!-- 查询条件 -->
<div id="queryCondition">
	<form id="queryForm" name="queryForm" action="#" method="post">
		<ul class="qry-ul">
		<li><label for="q_frdSeq">内部序号:</label>
		<input type="text" id="q_frdSeq" maxlength="20" comparison="eq" name="frdSeq"/></li>

		<li><label for="q_frdName">名称:</label><input type="text" id="q_frdName" comparison="eq" name="frdName" ></input></li>
				
		<li><label for="q_frdStatus">状态:</label><select id="q_frdStatus" ctype="INFO_ST" multiple="multiple" comparison="like" name="frdStatus" ></select></li>
		
		<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
		
		<li><input class='bt' value="重置" type="reset" /></li>
</ul>
	</form>
</div>
<!-- 列表 -->
<div id="table"></div>
<!-- 新增表单 -->
<div id="addForm" style="display: none;">
<form id="addForm1" action="#" name="af">
	<input id="version" type="hidden" name="version"/>
	<ul class='frm-ul'>
	<li><label for="frdSeq">内部序号:</label>
	<input type="text" id="frdSeq" name="frdSeq" /><div id="frdSeqTip"></div></li>
		
	<li><label for="frdName"><font color="red">*</font>名称:</label>
	<input type="text" id="frdName" name="frdName" maxLength="255" /><div id="frdNameTip"></div>
	<label for="frdType"><font color="red">*</font>类型:</label>
	<select ctype="FRD_TYPE" type="text" id="frdType" name="frdType" ></select><div id="frdTypeTip"></div>
	</li>
		
	<li><label for="isVisible"><font color="red">*</font>是否显示:</label>
	<select type="text" ctype="PUB_VISIBLE" id="isVisible" name="isVisible" ></select><div id="isVisibleTip"></div>
	<label for="frdSort"><font color="red">*</font>排序:</label>
	<input type="text" id="frdSort" name="frdSort" maxLength="10" /><div id="frdSortTip"></div>
	</li>
	
	<!-- <li><label for="frdStyle">样式:</label>
	<input type="text" id="frdStyle" name="frdStyle" maxLength="255" /><div id="frdStyleTip"></div></li> -->
	
	<li><label for="frdUrl"><font color="red">*</font>链接地址:</label>
	<input class="long" type="text" id="frdUrl" name="frdUrl" maxLength="255"/><div id="frdUrlTip"></div></li>
	
	<li><label for="frdLogo">图片:</label>
	<input class="long" type="text" id="frdLogo" name="frdLogo" /><input id="frdLogoPre" tar="frdLogo" value="预览" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片</div><div id="frdLogoTip"></div></li>
	
	<li><label for="frdLogoHover">图片浮动:</label>
	<input class="long" type="text" id="frdLogoHover" name="frdLogoHover" /><input id="frdLogoHoverPre" tar="frdLogoHover" value="预览" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片</div><div id="frdLogoHoverTip"></div></li>
	
</ul>
</form>
</div>

<div id="uploadOper">
<form id="upload-img-form" action="#" enctype="multipart/form-data" method="post" target="uploadTarget" >
  <ul class='frm-ul'> 
  <li><input type="hidden" name="uploadFileName" id="img-uploadFileName"/> 
  <input id="img-upload-file" name="uploadFile" type="file" style="width:240px" />
  <input class='bt' id="upload-button" value="上传" type="button" /></li>
  <img id="img-upload-preview" src="" alt="" />
  </ul>
  </form>
</div>

<div id="previewOper">
  <ul class='frm-ul'> 
  <label id="preLabel"></label>
  <img id="img-preview" src="" alt="" />
  </ul>
</div>
<script type="text/javascript">
var tostr=function(str){
	var index=str.indexOf("<");
	if(index!=-1)
		{
		str=str.substr(0,index);
		}
	return str;
};
var init=function() {
	var data= $('#uploadTarget').contents().find("body").html();
	if(data!=""){
	data=tostr(data);
	var dataObj=JSON.parse(data);//eval("("+data+")");
	if(dataObj) {
		if(dataObj.success){
			alert('操作成功');
			var _inputid=$('#uploadTarget').attr('from');
			$('#'+_inputid).val(dataObj.root[0].medPath.trim());
			$('#img-upload-preview').attr('src',dataObj.root[0].medPath+'?show=thumbnail');
			}
		else{
			 alert('操作失败 原因是'+ dataObj.syserr); 
			}
		}
	}
};
</script>
<iframe style="display:none;width:100%" name="uploadTarget" id="uploadTarget" onload="init()"></iframe>  
<script type="text/javascript">
<!--
// url
var url = {
grid: 'findCmFrdLink.action',
excel:'findCmFrdLinkExcel.action',
add:'doInsertCmFrdLink.action',
upd:'doUpdateCmFrdLink.action',
del:'doDeleteCmFrdLink.action',
com:'doCommitCmFrdLink.action',
rej:'doRejectCmFrdLink.action',
rel:'doReleaseCmFrdLink.action'
};

//operate
// add new org
var add = function() {
	$("div[id$=Tip]").empty();	
	$('#addForm1 :input').removeAttr('disabled');
	$('#addForm1 select').removeAttr('disabled');
	$('#frdSeq').val("<由系统自动生成>").attr('style','font-style:italic').attr('disabled','disabled');
	$('#addForm1')[0].reset();
	$('#addForm').dialog("option","title","新增友情链接");
	$('#addForm').dialog("option","buttons",{
	'确定' : function() {
		if($.formValidator.pageIsValid('1')) {
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
	$('#addForm1 select').removeAttr('disabled');
	$('#addForm1 :input').removeAttr('disabled');
	$('#addForm1 input[name="frdSeq"]').attr('disabled','true');
	if (!record) {alert("请选择一条记录"); return;}
	$('#addForm1')[0].reset();
	$('#addForm1 :input').each(function(i,n) { if (n.name in record) {n.value = record[n.name]; } });
	$('#addForm').dialog("option","title","编辑友情链接信息");
	$('#addForm').dialog("option","buttons",{
	'更新' : function() { 
		if ($.formValidator.pageIsValid('1')) {
			$.ajaxForm({formId: 'addForm1', url: url.upd,
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
	if (!confirm("确认要删除该友情链接吗？")) { return; }
	var obj = {frdSeq:record.frdSeq, version:record.version};
	$.ajax({type: 'POST', url: url.del, data: {jsonObject : $.toJSON(obj)}, dataType:'json',
	success : function(data) {
		if (data.success) { $('#table').flexModifyData(data.root[0]); alert('操作成功'); } else { alert('操作失败 原因是'+ data.syserr);}
	}});
};

var commit = function(record,id) {
	if (!record) { alert("请选择一条记录"); return; }
	if (!confirm("确认要提交该友情链接吗？")) { return; }
	var obj = {frdSeq:record.frdSeq, version:record.version};
	$.ajax({type: 'POST', url: url.com, data: {jsonObject : $.toJSON(obj)}, dataType:'json',
	success : function(data) {
		if (data.success) { $('#table').flexModifyData(data.root[0]); alert('操作成功'); } else { alert('操作失败 原因是'+ data.syserr);}
	}});
};

//edit org info 
var check = function(record,id) {
	$("div[id$=Tip]").empty();
	$('#addForm1 input[type!="button"]').attr('disabled','true');
	$('#addForm1 select').attr('disabled','true');
	if (!record) {alert("请选择一条记录"); return;}
	$('#addForm1')[0].reset();
	$('#addForm1 :input').each(function(i,n) { if (n.name in record) {n.value = record[n.name]; } });
	$('#addForm').dialog("option","title","审核友情链接信息");
	$('#addForm').dialog("option","buttons",{
	'审核通过' : function() { 
		if ($.formValidator.pageIsValid('1')) {
			$.ajaxForm({formId: 'addForm1', url: url.rel,
			success: function(data) {
				if (data.success) {$('#addForm').dialog('close');
					$('#table').flexModifyData(data.root[0]); alert('操作成功'); 
				} else {alert('操作失败 原因是'+ data.syserr); }
			}});
		} else { return false; }
	},'审核驳回' : function() { 
		if ($.formValidator.pageIsValid('1')) {
			$.ajaxForm({formId: 'addForm1', url: url.rej,
			success: function(data) {
				if (data.success) {$('#addForm').dialog('close');
					$('#table').flexModifyData(data.root[0]); alert('操作成功'); 
				} else {alert('操作失败 原因是'+ data.syserr); }
			}});
		} else { return false; }
	}, '关闭' : function() {$(this).dialog('close'); }});
	$('#addForm').dialog('open');
};
// var initGridData = function() {$.queryTable({formId:'queryForm', tableId: 'table'});}
$(function() {
	$.formValidator.initConfig({ onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
	$("#isVisible").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "是否显示必填" });	
	$("#frdName").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "名称必填" });
	$("#frdType").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "类型必填" });
	$("#frdSort").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "排序必填" });
	$("#frdUrl").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "链接地址必填" });
	
	$('#addForm').dialog({ autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 850, height : 570 });
	$('#addForm').dialog({ autoOpen: false });
	$('#addForm').fullScreen();

	// datagrid
	$('#table').flexigrid({
		url : url.grid, sort : "frdSeq", checkbox: false, usepager: true, dbClickRecord: edit,
		buttons : [
		 {name: "新增", show:'ROLE_INFO_FRD_LINK_ADD', bclass: 'grid_add',  onpress: add, 	aclass:'ui-button-primary' }
		,{name: "编辑", show:'ROLE_INFO_FRD_LINK_UPD', bclass: 'grid_edit', onpress: edit, 	aclass:'ui-button-primary' }
		,{name: "提交", show:'ROLE_INFO_FRD_LINK_COM', bclass: 'grid_edit', onpress: commit, 	aclass:'ui-button-success' }
		,{name: "删除", show:'ROLE_INFO_FRD_LINK_DEL', bclass: 'grid_edit', onpress: del, 	aclass:'ui-button-danger' }
		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		],
		colModel : [
					{ display : '内部序号', dataIndex : 'frdSeq', width : 80, align : 'center', sortable:true }
					,{ display : '名称', dataIndex : 'frdName', width : 80, align : 'center', sortable:true }
					,{ display : '类型', dataIndex : 'frdTypeDesc', width : 80, align : 'center', sortable:true }
					,{ display : '排序', dataIndex : 'frdSort', width : 80, align : 'center', sortable:true }
					,{ display : '是否显示', dataIndex : 'isVisibleDesc', width : 80, align : 'center', sortable:true }
					,{ display : '状态', dataIndex : 'frdStatusDesc', width : 80, align : 'center', sortable:true }
					,{ display : '记录创建时间', dataIndex : 'recCrtTime', width : 150, align : 'center', sortable:true , render: fmtTime}
					,{ display : '记录创建用户帐号', dataIndex : 'recCrtAcc', width : 80, align : 'center', sortable:true }
					,{ display : '最后修改时间', dataIndex : 'lastUptTime', width : 150, align : 'center', sortable:true , render: fmtTime}
					,{ display : '最后修改用户帐号', dataIndex : 'lastUptAcc', width : 80, align : 'center', sortable:true }
		]
	});
	//
	$.jyform.sysCodeBoxes();
	// buttons
	$('#queryBtn').click(function(){$.queryTable({formId : 'queryForm', tableId : 'table'});});
	$('.qry-ul input[type="button"]').addClass('ui-button-primary');
	$('.bt').addClass('ui-btn-sm').button();
	$('#queryForm .qry-ul input[type="button"]').click();
	/* KindEditor.ready(function(K) {
        window.editor = K.create('#editor',{uploadJson : 'uploadImg.action',filePostName:'uploadFile'});
}); */
	$('#frdLogo').dblclick(function(){
		if($('#frdLogo').val()!=''){
			$('#img-upload-preview').attr('src',$('#frdLogo').val()+'?show=thumbnail');
		}else{
		$('#img-upload-preview').attr('src','');
		}
		$('#img-upload-file').val('');
		$('#uploadOper').dialog("option",{title:"上传文件",buttons:{'关闭': function(){$(this).dialog('close');}} });
		$('#uploadOper').dialog('open');
		$('#uploadTarget').attr('from','frdLogo');
	});
	$('#frdLogoHover').dblclick(function(){
		if($('#frdLogoHover').val()!=''){
			$('#img-upload-preview').attr('src',$('#frdLogoHover').val()+'?show=thumbnail');
		}else{
		$('#img-upload-preview').attr('src','');
		}
		$('#img-upload-file').val('');	
		$('#uploadOper').dialog("option",{title:"上传文件",buttons:{'关闭': function(){$(this).dialog('close');}} });
		$('#uploadOper').dialog('open');
		$('#uploadTarget').attr('from','frdLogoHover');
	});
	$('#upload-button').click(function() {
		$('#img-uploadFileName').val($('#img-upload-file').val());
		$('#upload-img-form').attr('action','uploadImg.action');
		$('#upload-img-form')[0].submit();  
	}); 
	
	$('#frdLogoPre').click(function(){
		var _inputid=$(this).attr('tar');
		if($('#'+_inputid).val()!=''&&$('#'+_inputid).val()!=undefined){
			$('#img-preview').attr('src',$('#'+_inputid).val()+'?show=thumbnail');
			$('#preLabel').html('');
		}else{
		$('#img-preview').attr('src','');
		$('#preLabel').html('暂无图片');
		}
		$('#previewOper').dialog('open');
	});
	
	$('#frdLogoHoverPre').click(function(){
		var _inputid=$(this).attr('tar');
		if($('#'+_inputid).val()!=''&&$('#'+_inputid).val()!=undefined){
			$('#img-preview').attr('src',$('#'+_inputid).val()+'?show=thumbnail');
			$('#preLabel').html('');
		}else{
		$('#img-preview').attr('src','');
		$('#preLabel').html('暂无图片');
		}
		$('#previewOper').dialog('open');
	});
	$('#uploadOper').dialog({ autoOpen : false, bgiframe : true, modal : true, resizable : false, draggable : true, width : 800, height : 380 });
	$('#previewOper').dialog({ autoOpen : false, bgiframe : true, modal : true, resizable : false, draggable : true, width : 800, height : 380 });
	$('#previewOper').dialog("option",{title:"图片预览",buttons:{'关闭': function(){$(this).dialog('close');}} });
});
//-->
</script>
</body>
</html>