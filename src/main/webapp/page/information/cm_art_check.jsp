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
	<input type="hidden" name="artStatus" ftype="list" value="03,12" comparison="eq" />
		<ul class="qry-ul">
		<li><label for="q_artSeq">内部序号:</label>
		<input type="text" id="q_artSeq" maxlength="20" comparison="eq" name="artSeq"/></li>

		<li><label for="q_artAuthor">作者:</label><input type="text" id="q_artAuthor" comparison="eq" name="artAuthor" ></input></li>
				
		<li><label for="q_catStatus">标题:</label><input type="text" id="q_artTitle" comparison="like" name="artTitle" ></input></li>
		
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
	<input id="catSeq" name="catName"></select><div id="catNameTip"></div>
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

<div id="previewOper">
  <ul class='frm-ul'> 
  <label id="preLabel"></label>
  <img id="img-preview" src="" alt="" />
  </ul>
</div>
<script type="text/javascript">
<!--
// url
var url = {
grid: 'findCmArticle.action',
rej:'doRejectCmArticle.action',
rel:'doReleaseCmArticle.action',
detail:'findCmArticleDetail.action'
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
		url : url.grid, sort : "artSeq", checkbox: false, usepager: true, dbClickRecord: check,
		buttons : [
		 {name: "审核", show:'ROLE_INFO_ART_CHK', bclass: 'grid_review', onpress: check, 	aclass:'ui-button-warning' }
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
	$('#previewOper').dialog({ autoOpen : false, bgiframe : true, modal : true, resizable : false, draggable : true, width : 800, height : 380 });
	$('#previewOper').dialog("option",{title:"图片预览",buttons:{'关闭': function(){$(this).dialog('close');}} });
});
//-->
</script>
</body>
</html>