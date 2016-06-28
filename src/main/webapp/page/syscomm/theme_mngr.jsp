<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
</head>
<body>
<div id="queryCondition">
<form id="queryForm" name="queryForm" action="#" method="post">
</form>
</div>
<div id="table"></div>
<div id="addOper" style="display: none;">
<table width="100%">
<tr>
	<td width="450">
	<form id="form1" action="#" enctype="multipart/form-data" method="post" target="uploadTarget" >
	<input type="hidden" name="uploadFileName" id="uploadFileName"/>
	<input type="hidden" name="paramCode" id="f_paramCode"/>
	<input  type="hidden" id="paramValue"  name="paramValue" />
	<input type="hidden" id="version" name="version"/>
	<table class="ftable"  cellpadding="1" cellspacing="1" bgcolor="#8392a5" width="100%">
	<tr>
		<td class="flabel"><font color="red">*</font>参数名:</td>
		<td class="fcontent"><input class="cdnInput" id="paramCode" name="paramCode" maxLength="30"/></td>
	</tr>
	<tr>
		<td class="flabel">参数说明:</td>
		<td class="fcontent"><input class="cdnInput" id="paramDesc" name="paramDesc" maxLength="30" /></td>
	</tr>
	<tr id="img" style="display:none">
		<td class="flabel">参数值:</td>
		<td class="fcontent">
 			 <input class="qFormInput" id="img_paramValue"  name="img_paramValue" type="text" readonly="readonly"/> 
 		    <input type="file" name="uploadFile" id="uploadFile"/>
		</td>
	</tr>
	<tr id="text" style="display:none">
		<td class="flabel">参数值:</td>
		<td class="fcontent">
			<input class="qFormInput" id="text_paramValue"  name="text_paramValue" type="text"/>
		</td>
	</tr>
	<tr  id="imgPreview" style="display:none;">
		<td class="flabel">图片预览</td>
		<td style="background:#fff;height:90px;"><img src="" alt=""></img></td>
	</tr>
	</table></form>
	</td>
</tr>
</table>
</div>
<script type="text/javascript">
var add=function() {
	$.ajax({url:'loadTheme.action',type:'post',success:function(data){ 
		var dataObj=JSON.parse(data);
		if(dataObj) {
			if(dataObj.success){
				$('#table').refreshWithData(dataObj);
				}
			else{
				 alert('操作失败 原因是'+ dataObj.syserr); 
				}
			}
	}});
};

</script>
<iframe  style="display:none" name="uploadTarget" id="uploadTarget" onload="add()"></iframe>  
<script type="text/javascript">
 $(function(){
	$('.bt').button();
	$.formValidator.initConfig({ onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
	$('#addOper').dialog({ autoOpen : false, bgiframe : true, modal : true, resizable : false, draggable : true, width : 500, height : 380 });

	var edit = function(rec,id){
		$('#addOper').dialog("option","title","编辑主题信息");  
		$('#form1')[0].reset();
		if (!rec) { alert("请选择一条记录"); return; }
		$('#paramCode,#paramDesc').attr('style','font-style:italic').attr('disabled','disabled');
		$('#form1 :input').each(function(i,n) { if (n.name in rec) {n.value = rec[n.name];} });
		var str= rec.paramCode;
		if(str.indexOf('img') != -1){
				$('#img').attr('style','display:');
				$('#text').attr('style','display:none');
				$('#img_paramValue').val($('#paramValue').val());
				$('#imgPreview').attr('style','display:');
				$('#imgPreview img').attr('src',$('#paramValue').val()+'?show=thumbnail');
			}else{
				$('#imgPreview').attr('style','display:none');
				$('#img').attr('style','display:none');
				$('#text').attr('style','display:');
				$('#text_paramValue').val($('#paramValue').val());
		}
		$('#addOper').dialog("option","buttons",{
			'更新':function() {
				$('#uploadFileName').val($('#uploadFile').val());
				$('#f_paramCode').val($('#paramCode').val()); 
				if(str.indexOf('img') != -1){
					$('#paramValue').val($('#img_paramValue').val());
				}else{
					$('#paramValue').val($('#text_paramValue').val());
			}
				  $('#form1').attr('action','updateTheme.action');
				$('#form1')[0].submit();  
				//$('#addOper').dialog('close');
			} 
		,'关闭' : function() { $(this).dialog('close'); }
		});
		$('#addOper').dialog('open');
	};
	
	var addCallback=function(){
		var dataObj=retObject('uploadTarget');
		if( dataObj ) {
			if(dataObj.success){
				alert("操作成功");
				$('#addOper').dialog('close');
			} else{
				alert('图片上传失败:'+ dataObj.syserr); 
			}
		}
	};
	var retObject=function(_id) {
		var data= $('#'+_id).contents().find("body").html();
		if(data!=""){
			var index = data.indexOf("<");
			if( index!= -1 ) { 
				data = data.substr(0,index); 
			}
			return  JSON.parse(data) ;
		} else {
			return false;
		}
	};
	$('#uploadTarget').load(function(){addCallback();});
	$('#table').flexigrid({
		url : 'loadTheme.action', sort : "paramCode", checkbox : false, usepager : true, dbClickRecord: edit,
		buttons: [
	     { name: "编辑",show:'ROLE_SYSCONF_THEMEDEF',bclass: 'grid_edit',id: 'cel',onpress: edit}
		],
		colModel : [
		  { display: '参数名', dataIndex: 'paramCode', width: 150, align: 'center',sortable:true}
		  ,{ display: '参数说明', dataIndex: 'paramDesc', width:200, align: 'center',sortable:true } 
		 ,{ display: '参数值', dataIndex: 'paramValue', width:300, align: 'center',sortable:true }  
		]
	});	
$.queryTable({ formId : 'queryForm', tableId : 'table' });
});
</script>

</body>
</html>