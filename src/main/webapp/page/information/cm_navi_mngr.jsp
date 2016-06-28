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
		<li><label for="q_naviSeq">内部序号:</label>
		<input type="text" id="q_naviSeq" maxlength="20" comparison="eq" name="naviSeq"/></li>

		<li><label for="q_naviName">名称:</label><input type="text" id="q_naviName" comparison="eq" name="naviName" ></input></li>
				
		<li><label for="q_naviStatus">状态:</label><select id="q_naviStatus" ctype="INFO_ST" multiple="multiple" comparison="like" name="naviStatus" ></select></li>
		
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
	<li><label for="naviSeq">内部序号:</label>
	<input type="text" id="naviSeq" name="naviSeq" /><div id="naviSeqTip"></div></li>
<!-- 
	<li><label for="isBlankTarget">是否新建窗口:</label>
	<select type="text" id="isBlankTarget" ctype="PUB_YON" name="isBlankTarget"></select><div id="isBlankTargetTip"></div></li>
 -->
	<li style="display:none"><label for="isVisible"><font color="red">*</font>是否显示:</label>
	<select type="text" id="isVisible" ctype="PUB_VISIBLE" name="isVisible" ></select><div id="isVisibleTip"></div></li>

	<li style="display:none">label for="isHret"><font color="red">*</font>是否为外链:</label>
	<select type="text" id="isHret" ctype="PUB_YON" name="isHret" ></select><div id="isHretTip"></div></li>

	<li><label for="naviName"><font color="red">*</font>名称:</label>
	<input class="long" type="text" id="naviName" name="naviName" maxLength="8"></input><div id="naviNameTip"></div></li>

	<li style="display:none"><label for="naviPosition"><font color="red">*</font>导航位置:</label>
	<select type="text" id="naviPosition" name="naviPosition" ctype="NAVI_POSITION" ></select><div id="naviPositionTip"></div></li>

	<!-- <li><label for="naviStyle">样式类别:</label>
	<input type="text" id="naviStyle" name="naviStyle" maxLength="255"/><div id="naviStyleTip"></div></li> -->

	<li style="display:none"><label for="naviOrderList"><font color="red">*</font>导航顺序:</label>
	<input type="text" id="naviOrderList" name="naviOrderList" maxLength="10"/><div id="naviOrderListTip"></div></li>
	
	<li><label for="naviTitle"><font color="red">*</font>副标题:</label>
	<input class="long"  type="text" id="naviTitle" name="naviTitle"maxLength="20" /><div id="naviTitleTip"></div></li>

	<li><label for="naviLogo">导航LOGO:</label>
	<input class="long" type="text" id="naviLogo" name="naviLogo" /><input id="naviLogoPre" tar="naviLogo" value="预览" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片</div><div id="naviLogoTip"></div></li>

	<li><label for="naviBanner">导航BAN:</label>
	<input class="long" type="text" id="naviBanner" name="naviBanner" /><input id="naviBannerPre" tar="naviBanner" value="预览" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片</div><div id="naviBannerTip"></div></li>


	<li><label for="naviContent">导航内容:</label>
	<textarea style="width:940px ;height:100px;" id="naviContent" name="naviContent" maxLength="150"></textarea><div id="naviOrderListTip"></div></li>

	<%-- <li><label for="naviUrl"><font color="red">*</font>导航地址:</label>
	<input type="text" id="naviUrl" name="naviUrl" maxLength="255"/><div id="naviUrlTip"></div></li> --%>

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
grid: 'findCmNavi.action',
excel:'findCmNaviExcel.action',
add:'doInsertCmNavi.action',
upd:'doUpdateCmNavi.action',
del:'doDeleteCmNavi.action',
com:'doCommitCmNavi.action',
rej:'doRejectCmNavi.action',
rel:'doReleaseCmNavi.action'
};

//operate
// add new org
var add = function() {
	$("div[id$=Tip]").empty();	
	$('#addForm1 :input').removeAttr('disabled');
	$('#addForm1 select').removeAttr('disabled');
	$('#addForm1 textarea').removeAttr('disabled');
	$('#naviSeq').val("<由系统自动生成>").attr('style','font-style:italic').attr('disabled','disabled');
	$('#addForm1')[0].reset();
	$('#addForm').dialog("option","title","新增导航");
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
	$('#addForm1 textarea').removeAttr('disabled');
	$('#addForm1 input[name="naviSeq"]').attr('disabled','true');
	if (!record) {alert("请选择一条记录"); return;}
	$('#addForm1')[0].reset();
	$('#addForm1 :input').each(function(i,n) { if (n.name in record) {n.value = record[n.name]; } });
	$('#addForm').dialog("option","title","编辑导航信息");
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
	if (!confirm("确认要删除该导航吗？")) { return; }
	var obj = {naviSeq:record.naviSeq, version:record.version};
	$.ajax({type: 'POST', url: url.del, data: {jsonObject : $.toJSON(obj)}, dataType:'json',
	success : function(data) {
		if (data.success) { $('#table').flexModifyData(data.root[0]); alert('操作成功'); } else { alert('操作失败 原因是'+ data.syserr);}
	}});
};

var commit = function(record,id) {
	if (!record) { alert("请选择一条记录"); return; }
	if (!confirm("确认要提交该导航吗？")) { return; }
	var obj = {naviSeq:record.naviSeq, version:record.version};
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
	$('#addForm1 textarea').attr('disabled','true');
	if (!record) {alert("请选择一条记录"); return;}
	$('#addForm1')[0].reset();
	$('#addForm1 :input').each(function(i,n) { if (n.name in record) {n.value = record[n.name]; } });
	$('#addForm').dialog("option","title","审核导航信息");
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
	$("#isHert").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "是否为外链必填" });
	$("#naviName").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "名称必填" });
	$("#naviTitle").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "副标题必填" });
	$("#naviPosition").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "导航位置必填" });
	$("#naviOrderList").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "导航顺序必填" });
	/* $("#naviUrl").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "导航地址必填" }); */
	$('#addForm').dialog({ autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 850, height : 570 });
	$('#addForm').dialog({ autoOpen: false });
	$('#addForm').fullScreen();

	// datagrid
	$('#table').flexigrid({
		url : url.grid, sort : "naviSeq", checkbox: false, usepager: true, dbClickRecord: edit,
		buttons : [
		 {name: "新增", show:'ROLE_INFO_NAVI_ADD', bclass: 'grid_add',  onpress: add, 	aclass:'ui-button-primary' }
		,{name: "编辑", show:'ROLE_INFO_NAVI_UPD', bclass: 'grid_edit', onpress: edit, 	aclass:'ui-button-primary' }
		,{name: "提交", show:'ROLE_INFO_NAVI_COM', bclass: 'grid_edit', onpress: commit, 	aclass:'ui-button-success' }
		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		],
		colModel : [
		            { display : '内部序号', dataIndex : 'naviSeq', width : 80, align : 'center', sortable:true }
					,{ display : '状态', dataIndex : 'naviStatusDesc', width : 80, align : 'center', sortable:true }
					,{ display : '是否显示', dataIndex : 'isVisibleDesc', width : 80, align : 'center', sortable:true }
					,{ display : '名称', dataIndex : 'naviName', width : 120, align : 'center', sortable:true }
					,{ display : '副标题', dataIndex : 'naviTitle', width : 150, align : 'center', sortable:true }
					,{ display : '导航位置', dataIndex : 'naviPositionDesc', width : 80, align : 'center', sortable:true }
					,{ display : '导航顺序', dataIndex : 'naviOrderList', width : 80, align : 'center', sortable:true }
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
	$('#naviLogo').dblclick(function(){
		if($('#naviLogo').val()!=''){
			$('#img-upload-preview').attr('src',$('#naviLogo').val()+'?show=thumbnail');
		}else{
		$('#img-upload-preview').attr('src','');
		}
		$('#img-upload-file').val('');
		$('#uploadOper').dialog("option",{title:"上传文件",buttons:{'关闭': function(){$(this).dialog('close');}} });
		$('#uploadOper').dialog('open');
		$('#uploadTarget').attr('from','naviLogo');
	});
	$('#naviBanner').dblclick(function(){
		if($('#naviBanner').val()!=''){
			$('#img-upload-preview').attr('src',$('#naviBanner').val()+'?show=thumbnail');
		}else{
		$('#img-upload-preview').attr('src','');
		}
		$('#img-upload-file').val('');	
		$('#uploadOper').dialog("option",{title:"上传文件",buttons:{'关闭': function(){$(this).dialog('close');}} });
		$('#uploadOper').dialog('open');
		$('#uploadTarget').attr('from','naviBanner');
	});
	$('#upload-button').click(function() {
		$('#img-uploadFileName').val($('#img-upload-file').val());
		$('#upload-img-form').attr('action','uploadImg.action');
		$('#upload-img-form')[0].submit();  
	}); 
	
	$('#naviLogoPre').click(function(){
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
	
	$('#naviBannerPre').click(function(){
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