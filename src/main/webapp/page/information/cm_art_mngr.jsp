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
<style>
<!--
.frm-ul li{min-width: 1050px;}
.frm-ul li > div {display: inline-block;}
-->
</style>
</head>
<body>
<!-- 查询条件 -->
<div id="queryCondition">
	<form id="queryForm" name="queryForm" action="#" method="post">
		<ul class="qry-ul">
		<li><label for="q_artSeq">内部序号:</label>
		<input type="text" id="q_artSeq" maxlength="20" comparison="eq" name="artSeq"/></li>

		<li><label for="q_artAuthor">作者:</label><input type="text" id="q_artAuthor" comparison="eq" name="artAuthor" ></input></li>
				
		<li><label for="q_catStatus">标题:</label><input type="text" id="q_artTitle" comparison="like" name="artTitle" ></input></li>
		
		<li><label for="q_artStatus">状态:</label><select id="q_artStatus" ctype="INFO_ST" multiple="multiple"  comparison="like" name="artStatus" ></select></li>
		
		<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
		
		<li><input class='bt' value="重置" type="reset" /></li>
</ul>
	</form>
</div>
<!-- 列表 -->
<div id="table"></div>
<!-- 新增表单 -->
<div id="addForm" style="display: none; min-width: 1050px;">
<form id="addForm1" action="#" name="af">
	<input id="version" type="hidden" name="version"/>
	<ul class='frm-ul'>
	
	<li>
	<label for="artSeq">内部序号:</label>
	<input type="text" id="artSeq" name="artSeq" /><div id="artSeqTip"></div>
	</li>
	
	<li>
	<label for="catSeq"><font color="red">*</font>所属栏目:</label>
	<select id="catSeq" name="catSeq"></select><div id="catSeqTip"></div>
	<label for="artAuthor">作者:</label>
	<input type="text" id="artAuthor" name="artAuthor" maxLength="10"/><div id="artAuthorTip"></div>
	<label for="metaWords">页面关键词:</label>
	<input class="long" type="text" id="metaWords" name="metaWords" maxLength="20" style="width:180px;"/>
	<div id="metaWordsTip"></div></li>
				
	<li><label for="isRecmd">是否推荐:</label>
	<select ctype="PUB_YON" type="text" id="isRecmd" name="isRecmd" ></select><div id="isRecmdTip"></div>
	<label for="isTops">是否置顶:</label>
	<select ctype="PUB_YON" type="text" id="isTops" name="isTops" ></select><div id="isTopsTip"></div>
	<label for="artImg">文章滚动图:</label>
	<input class="mini" type="text" id="artImg" name="artImg"/><input id="artImgPre" tar="artImg" value="预览" class='bt' style="display:inline" type="button" /><!-- <div style="display:inline">双击输入框以上传图片</div> -->
	<div id="catLogoTip"></div></li>
	
	<li><label for="artTitle"><font color="red">*</font>标题:</label>
	<input class="ulong" type="text" id="artTitle" name="artTitle"  maxLength="20"/><div id="artTitleTip"></div></li>
	
<!-- 	<li><label for="metaDes">页面描述:</label>
	<input type="text" id="metaDes" name="metaDes" maxLength="20"/><div id="metaDesTip"></div></li> -->
		
<!-- 	<li><label for="artUrl">静态地址:</label>
	<input type="text" id="artUrl" name="artUrl" /><div id="artUrlTip"></div></li> -->

	<li><label for="artRemark">摘要:</label>
	<textarea type="text" id="artRemark" name="artRemark" maxLength="280" class="ulong" style="height: 60px; resize:none;"></textarea><div id="artRemarkTip"></div></li>
	
	<li><label for="artContent"><font color="red">*</font>内容:</label>
	<textarea type="text" style="width:940px " id="artContent" name="artContent" ></textarea><div id="artContentTip"></div></li>
	
</ul>
</form>
</div>
<%-- <!-- 栏目选择弹出窗口 -->
<div id="catSelect" >
<form id="q-cat-frm" name="q-cat-frm" action="#" method="post">
<ul class="qry-ul">
	<li><input class='bt' id="qryOrgBtn" value="查询" type="button" onclick="$.queryTable({formId:'q-cat-frm',tableId:'cat-table'});"/></li>
	<li><input value="重置" class='bt' type="reset" /></li>
</ul>
<div id="cat-table" class="is-grid"></div>
</form>
</div> --%>

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
grid: 'findCmArticle.action',
excel:'findCmArticleExcel.action',
add:'doInsertCmArticle.action',
upd:'doUpdateCmArticle.action',
del:'doDeleteCmArticle.action',
com:'doCommitCmArticle.action',
rej:'doRejectCmArticle.action',
rel:'doReleaseCmArticle.action',
catgrid:'findCmArtCat.action',
detail:'findCmArticleDetail.action'
};
/* var fillCat = function(data,i) {
	if ( data && data.length > 0 ) {
		var rec = data[0];
		if ( rec ) {
			$('#catSeq').val(rec.catSeq); 
			$('#catName').val(rec.catName);
		}
	}
};
var chooseCat = function() {
	$('#catSelect').dialog('open');
};
//上级栏目机构按钮
$('#catName').dblclick(chooseCat);
$('#catSelect').qDialog({
	width: 600, height: 460, title: '选择栏目',
	flexigrid: {url: url.catgrid, height:240, dir: "catSeq", usepager: false,modal:false
		,dbClickRecord: fillCat
		,colModel : [
			 { display: '内部序号', dataIndex: 'catSeq', width:180, align: 'center',sortable:true}
			 ,{ display: '名称', dataIndex: 'catName', width:100, align: 'center',sortable:true}
			 ,{ display: '父节点', dataIndex: 'parentCatName', width:120, align: 'center',sortable:true}
			 ,{ display: '栏目副标题', dataIndex: 'catTitle', width: 80, align: 'center', sortable:true }
			 ,{ display: '排序', dataIndex: 'catSort', width:120, align: 'center',sortable:true}			        
			 ,{ display: '栏目状态', dataIndex: 'catStatusDesc', width:120, align: 'center',sortable:true}
			 ,{ display: '是否显示', dataIndex: 'isVisibleDesc', width:120, align: 'center',sortable:true}			        
			]
	},
	success:function(data){ 
		if (data) { fillCat(data); return true;} 
		else {alert("请选择 一条数据");return false;}
	}
}); */
//operate
// add new org
var add = function() {
	$("div[id$=Tip]").empty();
	$('#addForm1 :input').removeAttr('disabled');
	$('#addForm1 textarea').removeAttr('disabled');
	$('#addForm1 select').removeAttr('disabled');
	$('#artSeq').val("<由系统自动生成>").attr('style','font-style:italic').attr('disabled','disabled');
	$('#addForm1')[0].reset();
	editor.html('');
	editor.readonly(false);
	$('#addForm').dialog("option","title","新增栏目");
	$('#addForm').dialog("option","buttons",{
	'确定' : function() {
		editor.sync();
		if($.formValidator.pageIsValid('1')) {
			if(!$('#catSeq').val()){ alert('所属栏目必选择'); return; }
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
	$('#addForm1 :input').removeAttr('disabled');
	$('#addForm1 textarea').removeAttr('disabled');
	$('#addForm1 select').removeAttr('disabled');
	$('#addForm1 input[name="artSeq"]').attr('disabled','true');
	editor.readonly(false);
	if (!record) {alert("请选择一条记录"); return;}
	$('#addForm1')[0].reset();
	var rec;
	var obj=new Object();
	obj.artSeq=record.artSeq;
	$.ajax({type: 'POST', url: url.detail, data: {jsonObject : $.toJSON(obj)}, dataType:'json',
		success : function(data) {
			if (data.success) { rec=data.root[0];
			$('#addForm1 :input').each(function(i,n) { if (n.name in rec) {n.value = rec[n.name]; } });
			editor.html(rec.artContent);
			$('#addForm').dialog("option","title","编辑栏目信息");
			$('#addForm').dialog("option","buttons",{
			'更新' : function() { 
				editor.sync();
				if ($.formValidator.pageIsValid('1')) {
					if(!$('#catSeq').val()){ alert('所属栏目必选择'); return; }
					$.ajaxForm({formId: 'addForm1', url: url.upd,
					success: function(data) {
						if (data.success) {$('#addForm').dialog('close');
							$('#table').flexModifyData(data.root[0]); alert('操作成功'); 
						} else {alert('操作失败 原因是'+ data.syserr); }
					}});
				} else { return false; }
			}, '关闭' : function() {$(this).dialog('close'); }});
			$('#addForm').dialog('open');
			} else { alert('获取文章详细信息失败 原因是'+ data.syserr);}
		}});
};
// delete org
var del = function(record,id) {
	if (!record) { alert("请选择一条记录"); return; }
	if (!confirm("确认要删除该文章吗？")) { return; }
	var obj = {artSeq:record.artSeq, version:record.version};
	$.ajax({type: 'POST', url: url.del, data: {jsonObject : $.toJSON(obj)}, dataType:'json',
	success : function(data) {
		if (data.success) { $('#table').flexModifyData(data.root[0]); alert('操作成功'); } else { alert('操作失败 原因是'+ data.syserr);}
	}});
};

var commit = function(record,id) {
	if (!record) { alert("请选择一条记录"); return; }
	if (!confirm("确认要提交该文章吗？")) { return; }
	var obj = {artSeq:record.artSeq, version:record.version};
	$.ajax({type: 'POST', url: url.com, data: {jsonObject : $.toJSON(obj)}, dataType:'json',
	success : function(data) {
		if (data.success) { $('#table').flexModifyData(data.root[0]); alert('操作成功'); } else { alert('操作失败 原因是'+ data.syserr);}
	}});
};

//edit org info 
var check = function(record,id) {
	$("div[id$=Tip]").empty();
	$('#addForm1 input').attr('disabled','true');
	$('#addForm1 textarea').attr('disabled','true');
	$('#addForm1 select').attr('disabled','true');
	editor.readonly(true);
	if (!record) {alert("请选择一条记录"); return;}
	$('#addForm1')[0].reset();
	var rec;
	var obj=new Object();
	obj.artSeq=record.artSeq;
	$.ajax({type: 'POST', url: url.detail, data: {jsonObject : $.toJSON(obj)}, dataType:'json',
		success : function(data) {
			if (data.success) { rec=data.root[0];
			$('#addForm1 :input').each(function(i,n) { if (n.name in rec) {n.value = rec[n.name]; } });
			editor.html(rec.artContent);
			$('#addForm').dialog("option","title","审核栏目信息");
			$('#addForm').dialog("option","buttons",{
			'审核通过' : function() { 
				editor.sync();
				if ($.formValidator.pageIsValid('1')) {
					$.ajaxForm({formId: 'addForm1', url: url.rel,
					success: function(data) {
						if (data.success) {$('#addForm').dialog('close');
							$('#table').flexModifyData(data.root[0]); alert('操作成功'); 
						} else {alert('操作失败 原因是'+ data.syserr); }
					}});
				} else { return false; }
			},'审核驳回' : function() { 
				editor.sync();
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
			} else { alert('获取文章详细信息失败 原因是'+ data.syserr);}
		}});	
};
// var initGridData = function() {$.queryTable({formId:'queryForm', tableId: 'table'});}
$(function() {
	$.formValidator.initConfig({ onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
	$("#artTitle").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "标题必填" });
	$("#artContent").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, onerror : "内容必填" });
	$('#addForm').dialog({ autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 850, height : 570 });
	$('#addForm').dialog({ autoOpen: false });
	$('#addForm').fullScreen();

	// datagrid
	$('#table').flexigrid({
		url : url.grid, sort : "artSeq", checkbox: false, usepager: true, dbClickRecord: edit,
		buttons : [
		 {name: "新增", show:'ROLE_INFO_ART_ADD', bclass: 'grid_add',  onpress: add, 	aclass:'ui-button-primary' }
		,{name: "编辑", show:'ROLE_INFO_ART_UPD', bclass: 'grid_edit', onpress: edit, 	aclass:'ui-button-primary' }
		,{name: "提交", show:'ROLE_INFO_ART_COM', bclass: 'grid_review', onpress: commit, 	aclass:'ui-button-success' }
		,{name: "删除", show:'ROLE_INFO_ART_DEL', bclass: 'grid_del', onpress: del, 	aclass:'ui-button-danger' }
		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		],
		colModel : [
		            { display : '内部序号', dataIndex : 'artSeq', width : 80, align : 'center', sortable:true }
					,{ display : '所属栏目', dataIndex : 'catName', width : 80, align : 'center', sortable:true }
					,{ display : '标题', dataIndex : 'artTitle', width : 150, align : 'center', sortable:true }
					,{ display : '作者', dataIndex : 'artAuthor', width : 80, align : 'center', sortable:true }
					,{ display : '发布时间', dataIndex : 'artPubTime', width : 150, align : 'center', sortable:true , render: fmtTime}
					,{ display : '文章状态', dataIndex : 'artStatusDesc', width : 80, align : 'center', sortable:true }
					,{ display : '是否推荐', dataIndex : 'isRecmdDesc', width : 80, align : 'center', sortable:true }
					,{ display : '是否置顶', dataIndex : 'isTopsDesc', width : 80, align : 'center', sortable:true }
					,{ display : '点击数', dataIndex : 'artHits', width : 80, align : 'center', sortable:true }
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
	KindEditor.ready(function(K) {
        window.editor = K.create('#artContent',{uploadJson : 'uploadImg.action',filePostName:'uploadFile'});
});
	$.jyform.ajaxSelectBox({selectId:'catSeq',valueField:'catSeq',displayField:'catName',url:url.catgrid});
	
	$('#artImg').dblclick(function(){
		if($('#artImg').val()!=''){
			$('#img-upload-preview').attr('src',$('#artImg').val()+'?show=thumbnail');
		}else{
		$('#img-upload-preview').attr('src','');
		}
		$('#img-upload-file').val('');	
		$('#uploadOper').dialog("option",{title:"上传文件",buttons:{'关闭': function(){$(this).dialog('close');}} });
		$('#uploadOper').dialog('open');
		$('#uploadTarget').attr('from','artImg');
	});
	$('#upload-button').click(function() {
		$('#img-uploadFileName').val($('#img-upload-file').val());
		$('#upload-img-form').attr('action','uploadImg.action');
		$('#upload-img-form')[0].submit();  
	}); 
	
	$('#artImgPre').click(function(){
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