<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
<style>
<!--
#update {width:100%; padding: 20px 0 !important;}
#update .ui-tabs .ui-tabs-panel{margin: 0px !important; padding: 0 15px; }
#update .ui-tabs .ui-tabs-panel > ul > li {margin-top: 15px !important; }
#tabs > div > ul {margin: 16px 0 0 20px;}
#tabs > div > ul > li { height: 40px; }
#tabs > div > ul > li > label { font-size: 15px !important; width: 140px;  }
#tabs > div > ul > li > label > .labelTip { float: left; display: block; position: absolute; font-style: italic; font-weight:normal; font-size: 12px; text-indent: .8em; color: #AAAAAA; margin-top:-10px; }
#tabs > div > ul > li > input[type="text"] {width: 240px;}
-->
</style>
<script>
var preview=function(i){
	var _inputid=$(i).attr('tar');
	if($('#'+_inputid).val()!=undefined&&$('#'+_inputid).val().trim()!=''){
		$('#img-preview').attr('src',$('#'+_inputid).val()+'?show=thumbnail');
		$('#preLabel').html('');
	}else{
	$('#img-preview').attr('src','');
	$('#preLabel').html('暂无图片');
	}
	$('#previewOper').dialog('open');
};

var download=function(i){
	var _inputid=$(i).attr('tar');
	if($('#'+_inputid).val()!=''&&$('#'+_inputid).val()!=undefined){
		var $a=$('<a href="'+$('#'+_inputid).val()+'"></a>');
		$a[0].click();
	}else{
	alert('请先上传文件后下载');
	}
};
</script>
</head>
<body>
<div id="update">
<div id="tabs">
  <ul>
    <li style="margin-left: 40px;"><a href="#sys-mail">邮件参数</a></li>
    <li><a href="#sys-theme">管控台主题参数</a></li>
     <li><a href="#web-theme">网站参数</a></li>
    <li id="otherIdx" ><a href="#other">其他参数</a></li>
  </ul>
  <div id="sys-mail">
	  <ul id="mailul" class='frm-ul'></ul>
  </div>
  <div id="sys-theme">
  	<ul id="themeul" class='frm-ul'>
	  <li><label for="sys-theme-img-logo">LOGO图片:<span class="labelTip">sys.theme.img.logo</span></label><input id="sys-theme-img-logo" name="sys.theme.img.logo" type="text" /><input id="sys-theme-img-logo-pre" tar="sys-theme-img-logo" value="预览" class='bt' style="display:inline" onclick="preview(this)" type="button" /><div style="display:inline">双击输入框以上传图片</div></li>
	  <li><label for="sys-theme-img-loginbg">背景图片:<span class="labelTip">sys.theme.img.loginbg</span></label><input id="sys-theme-img-loginbg" name="sys.theme.img.loginbg" type="text" /><input id="sys-theme-img-loginbg-pre" tar="sys-theme-img-loginbg" value="预览" class='bt' onclick="preview(this)" style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片</div></li>
  	</ul>
  </div>
   <div id="web-theme">
  	<ul id="webthemeul" class='frm-ul'>
	  <li><label for="web-theme-logo">网站图片(图片):<span class="labelTip">web.theme.logo</span></label><input id="web-theme-logo" name="web.theme.logo" type="text" /><input id="web-theme-logo-pre" tar="web-theme-logo" value="预览" onclick="preview(this)" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片</li>
	  <li><label for="web-theme-logo2">网站LOGO2(图片):<span class="labelTip">web.theme.logo2</span></label><input id="web-theme-logo2" name="web.theme.logo2" type="text" /><input id="web-theme-logo2-pre" tar="web-theme-logo2" value="预览" onclick="preview(this)" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片</li>
	  <li><label for="web-theme-customer">咨询热线(图片):<span class="labelTip">web.theme.customer</span></label><input id="web-theme-customer" name="web.theme.customer" type="text" /><input id="web-theme-customer-pre" tar="web-theme-customer" value="预览" onclick="preview(this)" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片</li>
	  	  <li><label for="web-app-qrcode">App二维码(图片):<span class="labelTip">web.app.qrcode</span></label><input id="web-app-qrcode" name="web.app.qrcode" type="text" /><input id="web-app-qrcode-pre" tar="web-app-qrcode" value="预览" onclick="preview(this)" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传图片,推荐大小120px*120px</li>
	  <li><label for="web-app-apk">Android应用(附件):<span class="labelTip">web.app.apk</span></label><input id="web-app-apk" name="web.app.apk" type="text" /><input id="web-app-apk-pre" tar="web-app-apk" value="下载" onclick="download(this)" class='bt' style="display:inline" type="button" /><div style="display:inline">双击输入框以上传文件</li>
  	</ul>
  </div>
  <div id="other">
	  <ul id="otherul" class='frm-ul'></ul>
  </div>
</div><!-- eof #tabs -->
<div id="uploadOper" style="display: none;">
<form id="upload-img-form" action="#" enctype="multipart/form-data" method="post" target="uploadTarget" >
  <ul class='frm-ul'> 
  	<li><input type="hidden" name="uploadFileName" id="img-uploadFileName"/> 
  	<input id="img-upload-file" name="uploadFile" type="file" style="width:240px" />
  	<input class='bt' id="upload-button" value="上传" type="button" />
  	</li>
  	<img id="img-upload-preview" src="" alt="" />
  </ul>
  </form>
</div>

<div id="uploadFileOper" style="display: none;">
<form id="upload-file-form" action="#" enctype="multipart/form-data" method="post" target="uploadFileTarget" >
  <ul class='frm-ul'> 
  	<li><input type="hidden" name="uploadFileName" id="file-uploadFileName"/> 
  	<input id="file-upload-file" name="uploadFile" type="file" style="width:150px" />
  	<input class='bt' id="upload-file-button" value="上传" type="button" />
  	</li>
  </ul>
  </form>
</div>
<div id="previewOper">
  <ul class='frm-ul'> 
  <label id="preLabel"></label>
  <img id="img-preview" src="" alt="" />
  </ul>
</div>
</div><!-- eof #update -->
<script type="text/javascript">
var tostr=function(str){
	var index=str.indexOf("<");
	if(index!=-1)
		{
		str=str.substr(0,index);
		}
	return str;
};
var add=function() {
	var data= $('#uploadTarget').contents().find("body").html();
	if(data!=""){
	data=tostr(data);
	var dataObj=JSON.parse(data); //eval("("+data+")");
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
var addFile=function() {
	var data= $('#uploadFileTarget').contents().find("body").html();
	if(data!=""){
	data=tostr(data);
	var dataObj=JSON.parse(data);//eval("("+data+")");
	if(dataObj) {
		if(dataObj.success){
			alert('操作成功');
			var _inputid=$('#uploadFileTarget').attr('from');
			$('#'+_inputid).val(dataObj.root[0].medPath.trim());
			}
		else{
			 alert('操作失败 原因是'+ dataObj.syserr); 
			}
		}
	}
};
</script>
<iframe style="display:none" name="uploadTarget" id="uploadTarget" onload="add()"></iframe>  
<iframe style="display:none" name="uploadFileTarget" id="uploadFileTarget" onload="addFile()"></iframe> 
<script type="text/javascript">
<!--
var url = {
load:'findSysParamList.action'
};

var listTpl = '<li class="to-redraw"><label for="{id}">{paramDesc}:<span class="labelTip">{paramCode}</span></label><input id="{id}" type="text" name="{paramCode}" version="{version}" value="{paramValue}"/><div id="{id}Tip" /></li>';
/**
 * Reload Params 
 */
var redrawParams = function(_params) {
	var $mail=$('#mailul'), $theme=$('#themeul'),$web=$('#webthemeul'), $other=$('#otherul');
	var otherHtml = $other.html();
	$.formValidator.initConfig({ onerror : function(msg,obj) { alert(msg); $(obj).focus();}});
	$('.to-redraw').remove(); 
	$(_params).each(function(_i,n) {
		var _id = n.paramCode.replace(/\./gm,'-'),
			_type = n.paramCode.substring(0, n.paramCode.indexOf('.', n.paramCode.indexOf('.') + 1));
		if ( $('#'+_id).length > 0) {
			$('#'+_id).attr('version', n.version).val(n.paramValue);
		} else {
			var param = $.extend({id:_id  }, n);
			var $li = $.jyform.htmlFromTpl(param, listTpl);
			switch(_type) {
				case "sys.email": $mail.append($li); break;
				case "sys.theme": $theme.append($li);break;
				case "web.theme": $web.append($li);break;
				case "web.app": $web.append($li);break;
				case "web.article": $web.append($li);break;
				default: $other.append($li);
			}
		}
		if ( n.paramModifyFlag == '0') {
			$('#' + _id).attr('readonly','readonly');				
		} else {
			$('#' + _id).formValidator({empty:false, onshow:"请输入内容", oncorrect: "√"});
		}
	});
	if ($other.html() == otherHtml) { $('#otherIdx').hide();}
};
/**
 * Load Parameters
 */
var loadParam = function() {
$.ajax({url:url.load,dataType:'json'
	,success:function(data){
		if ( data.success ) { redrawParams(data.root);
		} else { alert('系统参数加载失败'); console.log(data.syserr); }
	}});	
};
$(function(){
var load = function(){loadParam();}; load();

var saveParams = function() {
	if ($.formValidator.pageIsValid('1')) {
		var params = new Array();
		$('#tabs input').each(function(i,n){
			params.push({paramCode:$(n).attr('name'), version:$(n).attr('version'), paramValue:$(n).val() });
		});
		$.ajax({type: 'POST', url : 'updateSysParam.action', data:{jsonArray: $.toJSON(params)}, dataType:'json',
			success: function(data) {
				if (data.success) {
					alert('操作成功'); if ( data.totalProperty > 0 ) { load(); }
				} else { alert('操作失败 原因是' + data.syserr); }
			}
		});
	}
};

$('#sys-theme-img-logo').on('dblclick',function(){
	if($('#sys-theme-img-logo').val().trim()!=''){
		$('#img-upload-preview').attr('src',$('#sys-theme-img-logo').val()+'?show=thumbnail');
	}else{
	$('#img-upload-preview').attr('src','');
	}
	$('#img-upload-file').val('');
	$('#uploadOper').dialog('open');
	$('#uploadTarget').attr('from','sys-theme-img-logo');
});
$('#sys-theme-img-loginbg').on('dblclick',function(){
	if($('#sys-theme-img-loginbg').val().trim()!=''){
		$('#img-upload-preview').attr('src',$('#sys-theme-img-loginbg').val()+'?show=thumbnail');
	}else{
	$('#img-upload-preview').attr('src','');
	}
	$('#img-upload-file').val('');	
	$('#uploadOper').dialog('open');
	$('#uploadTarget').attr('from','sys-theme-img-loginbg');
});

$('#web-theme-logo').on('dblclick',function(){
	if($('#web-theme-logo').val().trim()!=''){
		$('#img-upload-preview').attr('src',$('#web-theme-logo').val()+'?show=thumbnail');
	}else{
		$('#img-upload-preview').attr('src','');
	}
	$('#img-upload-file').val('');	
	$('#uploadOper').dialog('open');
	$('#uploadTarget').attr('from','web-theme-logo');
});

$('#web-theme-logo2').on('dblclick',function(){
	if($('#web-theme-logo2').val().trim()!=''){
		$('#img-upload-preview').attr('src',$('#web-theme-logo2').val()+'?show=thumbnail');
	}else{
		$('#img-upload-preview').attr('src','');
	}
	$('#img-upload-file').val('');	
	$('#uploadOper').dialog('open');
	$('#uploadTarget').attr('from','web-theme-logo2');
});

$('#web-theme-customer').on('dblclick',function(){
	if($('#web-theme-customer').val().trim()!=''){
		$('#img-upload-preview').attr('src',$('#web-theme-customer').val()+'?show=thumbnail');
	}else{
	$('#img-upload-preview').attr('src','');
	}
	$('#img-upload-file').val('');	
	$('#uploadOper').dialog('open');
	$('#uploadTarget').attr('from','web-theme-customer');
});

$('#web-app-qrcode').on('dblclick',function(){
	if($('#web-app-qrcode').val().trim()!=''){
		$('#img-upload-preview').attr('src',$('#web-app-qrcode').val()+'?show=thumbnail');
	}else{
	$('#img-upload-preview').attr('src','');
	}
	$('#img-upload-file').val('');	
	$('#uploadOper').dialog('open');
	$('#uploadTarget').attr('from','web-app-qrcode');
});
$('#upload-button').on('click',function() {
	$('#img-uploadFileName').val($('#img-upload-file').val());
	$('#upload-img-form').attr('action','uploadImg.action');
	$('#upload-img-form')[0].submit();  
}); 
$('#uploadOper').dialog({ autoOpen: false, bgiframe: true, modal: true, resizable: true, draggable: true
	, width: 800, height: 380, title:"上传文件", buttons:{'关闭': function(){$(this).dialog('close');}} });

// fullScreen Dialog
$('#update').dialog({autoOpen:false, buttons:{'保存': saveParams, '刷新': load} });
$('#update').fullScreen();

// load after body size set
setTimeout("$('#update').dialog('open');", 200);
// init component
$('.bt').button();
// on dialog open 
$('#update').on('dialogopen',function(){ 
	// remove titlebar
	var _oh = $('#update').prev().outerHeight();
	$('#update').height($('#update').height() + _oh);
	$('#update').prev().hide();
	setTimeout("$('#tabs').tabs({heightStyle:'fill'});", 200);
});
$('#uploadFileOper').dialog({ autoOpen: false, bgiframe: true, modal: true, resizable: true, draggable: true
	, width: 800, height: 380, title:"上传文件", buttons:{'关闭': function(){$(this).dialog('close');}} });
$('#upload-file-button').on('click',function() {
	$('#file-uploadFileName').val($('#file-upload-file').val());
	$('#upload-file-form').attr('action','uploadFile.action');
	$('#upload-file-form')[0].submit();  
}); 

$('#web-app-apk').on('dblclick',function(){
	$('#img-upload-file').val('');	
	$('#uploadFileOper').dialog('open');
	$('#uploadFileTarget').attr('from','web-app-apk');
});
$('#previewOper').dialog({ autoOpen : false, bgiframe : true, modal : true, resizable : false, draggable : true, width : 800, height : 380 });
$('#previewOper').dialog("option",{title:"图片预览",buttons:{'关闭': function(){$(this).dialog('close');}} });
});
//-->
</script>

</body>
</html>