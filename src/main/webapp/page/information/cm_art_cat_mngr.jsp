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
		<li><label for="q_catSeq">内部序号:</label>
		<input type="text" id="q_catSeq" maxlength="20" comparison="eq" name="catSeq"/></li>

		<li><label for="q_catName">名称:</label><input type="text" id="q_catName" comparison="eq" name="catName" ></input></li>
				
		<li><label for="q_catStatus">栏目状态:</label><select id="q_catStatus" ctype="INFO_ST" multiple="multiple" comparison="like" name="catStatus" ></select></li>
		
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
	<input id="catParent" type="hidden" name="catParent"/>
	<ul class='frm-ul'>
	<li><label for="catSeq">内部序号:</label>
	<input type="text" id="catSeq" name="catSeq" /><div id="catSeqTip"></div></li>

	<li><label for="parentCatName"><font color="red">*</font>父节点:</label>
	<input type="text" id="parentCatName" name="parentCatName" /><div id="parentCatNameTip"></div>
	<label for="catName"><font color="red">*</font>名称:</label>
	<input type="text" id="catName" name="catName" maxLength="6" /><div id="catNameTip"></div>
	</li>
		
	<li><label for="catCode"><font color="red">*</font>栏目代码:</label>
	<input type="text" id="catCode" name="catCode" maxLength="30" /><div id="catCodeTip"></div>
	<label for="catSort"><font color="red">*</font>排序:</label>
	<input type="text" id="catSort" name="catSort" maxLength="10" /><div id="catSortTip"></div>
	</li>
	
	<li><label for="isVisible"><font color="red">*</font>是否可见:</label>
	<select ctype="PUB_VISIBLE" id="isVisible" name="isVisible" maxLength="10" ></select><div id="isVisibleTip"></div></li>
	
	<li><label for="catTitle">栏目副标题:</label>
	<input class="long" type="text" id="catTitle" name="catTitle" maxLength="15" /><div id="catTitleTip"></div></li>

	<li><label for="catLogo">栏目LOGO:</label>
	<input class="long" type="text" id="catLogo" name="catLogo"/><input id="catLogoPre" tar="catLogo" value="预览" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片</div><div id="catLogoTip"></div></li>
	
	<li><label for="catBanner">栏目BAN:</label>
	<input class="long" type="text" id="catBanner" name="catBanner"/><input id="catBannerPre" tar="catBanner" value="预览" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片</div><div id="catBannerTip"></div></li>
	
	<%-- <li><label for="editor"><font color="red">*</font>框:</label>
	<textarea id="editor" style="width:700px;height:300px;" ></textarea></li> --%>
	</ul>
</form>
</div>
<!-- 上级栏目选择弹出窗口 -->
<div id="catParentSelect" >
<form id="q-cat-frm" name="q-cat-frm" action="#" method="post">
<input type="hidden" name="catSeq" class="param" id="catParentSelect-catSeq" />
<ul class="qry-ul" style="display:none">
	<li><input class='bt' id="qryOrgBtn" value="查询" type="button" onclick="$.queryTable({formId:'q-cat-frm',tableId:'cat-table'});"/></li>
	<li><input value="重置" class='bt' type="reset" /></li>
</ul>
<div id="cat-table" class="is-grid"></div>
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
grid: 'findCmArtCat.action',
excel:'findCmArtCatExcel.action',
add:'doInsertCmArtCat.action',
upd:'doUpdateCmArtCat.action',
del:'doDeleteCmArtCat.action',
com:'doCommitCmArtCat.action',
rej:'doRejectCmArtCat.action',
rel:'doReleaseCmArtCat.action'
};
var fillCatParent = function(data,i) {
	if ( data && data.length > 0 ) {
		var rec = data[0];
		if ( rec ) {
			$('#catParent').val(rec.catSeq); 
			$('#parentCatName').val(rec.catName);
		}
	}
};
var chooseCatParent = function() {
	var catSeq = $('#catSeq').val();
	if ( catSeq ) {$('#catParentSelect-catSeq').val(catSeq);}else{
		$('#catParentSelect-catSeq').val(0);
	}
	$('#catParentSelect').dialog('open');
};
//上级栏目机构按钮
$('#parentCatName').dblclick(chooseCatParent);
$('#catParentSelect').qDialog({
	width: 600, height: 460, title: '选择上级机构',
	flexigrid: {url: 'findCmArtCatCombo.action', height:240, dir: "catSeq", usepager: false
		,dbClickRecord: fillCatParent
		,colModel : [
			            { display: '内部序号', dataIndex: 'catSeq', width:180, align: 'center',sortable:true}
				        ,{ display: '名称', dataIndex: 'catName', width:100, align: 'center',sortable:true}
				        ,{ display: '父节点', dataIndex: 'parentCatName', width:120, align: 'center',sortable:true}
				        ,{ display: '排序', dataIndex: 'catSort', width:120, align: 'center',sortable:true}			        
				        ,{ display: '栏目状态', dataIndex: 'catStatusDesc', width:120, align: 'center',sortable:true}
				        ,{ display: '是否显示', dataIndex: 'isVisibleDesc', width:120, align: 'center',sortable:true}			        
			]
	},
	success:function(data){ 
		if (data) { fillCatParent(data); return true;} 
		else {alert("请选择 一条数据");return false;}
	}
});
//operate
// add new org
var add = function() {
	$("div[id$=Tip]").empty();	
	$('#addForm1 :input').removeAttr('disabled');
	$('#addForm1 select').removeAttr('disabled');
	$('#catSeq').val("<由系统自动生成>").attr('style','font-style:italic').attr('disabled','disabled');
	$('#addForm1')[0].reset();
	$('#addForm').dialog("option","title","新增栏目");
	$('#addForm').dialog("option","buttons",{
	'确定' : function() {
		if($.formValidator.pageIsValid('1')) {
			if(!$('#parentCatName').val()){ alert('父节点必选择'); return; }
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
	$('#addForm1 input[name="catSeq"]').attr('disabled','true');
	if (!record) {alert("请选择一条记录"); return;}
	$('#addForm1')[0].reset();
	$('#addForm1 :input').each(function(i,n) { if (n.name in record) {n.value = record[n.name]; } });
	$('#addForm').dialog("option","title","编辑栏目信息");
	$('#addForm').dialog("option","buttons",{
	'更新' : function() { 
		if ($.formValidator.pageIsValid('1')) {
			if(!$('#parentCatName').val()){ alert('父节点必选择'); return; }
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
	if (!confirm("确认要删除该栏目吗？")) { return; }
	var obj = {catSeq:record.catSeq, version:record.version};
	$.ajax({type: 'POST', url: url.del, data: {jsonObject : $.toJSON(obj)}, dataType:'json',
	success : function(data) {
		if (data.success) { $('#table').flexModifyData(data.root[0]); alert('操作成功'); } else { alert('操作失败 原因是'+ data.syserr);}
	}});
};

var commit = function(record,id) {
	if (!record) { alert("请选择一条记录"); return; }
	if (!confirm("确认要提交该栏目吗？")) { return; }
	var obj = {catSeq:record.catSeq, version:record.version};
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
	$('#addForm').dialog("option","title","审核栏目信息");
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
	$("#catCode").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "栏目代码选择" });
	$("#parentCatName").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "父节点必选择" });
	$("#catName").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, max:255, onerror : "名称必填" });
	$("#catSort").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, max:10, onerror : "排序必填" }).regexValidator({ regexp : "^[0-9]+$", onerror : "排序只能是数字" });
	$("#isVisible").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "是否显示必填" });
	$('#addForm').dialog({ autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 850, height : 570 });
	$('#addForm').dialog({ autoOpen: false });
	$('#addForm').fullScreen();

	// datagrid
	$('#table').flexigrid({
		url : url.grid, sort : "catSeq", checkbox: false, usepager: true, dbClickRecord: edit,
		buttons : [
		 {name: "新增", show:'ROLE_INFO_CAT_ADD', bclass: 'grid_add',  onpress: add, 	aclass:'ui-button-primary' }
		,{name: "编辑", show:'ROLE_INFO_CAT_UPD', bclass: 'grid_edit', onpress: edit, 	aclass:'ui-button-primary' }
		,{name: "提交", show:'ROLE_INFO_CAT_COM', bclass: 'grid_edit', onpress: commit, 	aclass:'ui-button-success' }
		,{name: "删除", show:'ROLE_INFO_CAT_DEL', bclass: 'grid_edit', onpress: del, 	aclass:'ui-button-danger' }
		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		],
		colModel : [
		            { display: '内部序号', dataIndex: 'catSeq', width:180, align: 'center',sortable:true}
			        ,{ display: '名称', dataIndex: 'catName', width:100, align: 'center',sortable:true}
			        ,{ display: '父节点', dataIndex: 'parentCatName', width:120, align: 'center',sortable:true}
			        ,{ display: '栏目副标题', dataIndex: 'catTitle', width: 150, align: 'center', sortable:true }
			        ,{ display: '排序', dataIndex: 'catSort', width:120, align: 'center',sortable:true}			        
			        ,{ display: '栏目状态', dataIndex: 'catStatusDesc', width:120, align: 'center',sortable:true}
			        ,{ display: '是否显示', dataIndex: 'isVisibleDesc', width:120, align: 'center',sortable:true}
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
	$('#catLogo').dblclick(function(){
		if($('#catLogo').val()!=''){
			$('#img-upload-preview').attr('src',$('#catLogo').val()+'?show=thumbnail');
		}else{
		$('#img-upload-preview').attr('src','');
		}
		$('#img-upload-file').val('');
		$('#uploadOper').dialog("option",{title:"上传文件",buttons:{'关闭': function(){$(this).dialog('close');}} });
		$('#uploadOper').dialog('open');
		$('#uploadTarget').attr('from','catLogo');
	});
	$('#catBanner').dblclick(function(){
		if($('#catBanner').val()!=''){
			$('#img-upload-preview').attr('src',$('#catBanner').val()+'?show=thumbnail');
		}else{
		$('#img-upload-preview').attr('src','');
		}
		$('#img-upload-file').val('');	
		$('#uploadOper').dialog("option",{title:"上传文件",buttons:{'关闭': function(){$(this).dialog('close');}} });
		$('#uploadOper').dialog('open');
		$('#uploadTarget').attr('from','catBanner');
	});
	$('#upload-button').click(function() {
		$('#img-uploadFileName').val($('#img-upload-file').val());
		$('#upload-img-form').attr('action','uploadImg.action');
		$('#upload-img-form')[0].submit();  
	}); 
	
	$('#catLogoPre').click(function(){
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
	
	$('#catBannerPre').click(function(){
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