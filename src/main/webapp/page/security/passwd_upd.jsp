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
<div id="changepwd" style="display:none">
<form id="form1">
<table class="ftable" cellpadding="1" cellspacing="1" bgcolor="#8392a5" width="100%">
	<tr>
		<td class="flabel">原始密码:</td>
		<td class="fcontent"><input name="oldPasswd" type="password" id="oldPasswd"/><div id="oldPasswdTip"></div></td>
	</tr>
	<tr>
		<td class="flabel">新密码:</td>
		<td class="fcontent"><input name="newPasswd" type="password" id="newPasswd"/><div id="newPasswdTip"></div></td>
	</tr>
	<tr>
		<td class="flabel">重复新密码:</td>
		<td class="fcontent"><input name="confirmPasswd" type="password" id="confirmPasswd" /><div id="confirmPasswdTip"></div></td>
	</tr>
</table>
</form>
</div>
<script type="text/javascript">
<!--
$(function(){
// Validator
$.formValidator.initConfig({ onerror : function(msg) { alert(msg) } });
$("#oldPasswd").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, max : 12, onerror : "密码最多12个字符" }).regexValidator({ regexp : "^[A-Za-z0-9]+$", onerror : "密码仅能输入数字或字母" });
$("#confirmPasswd").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).regexValidator({ regexp : "^[A-Za-z0-9]+$", onerror : "仅能输入数字或字母" }).compareValidator({ desid : "newPasswd", operateor : "=", onerror : "2次密码不一致,请确认" });
function changePwd(){
	var oldpwd= $('#changepwd input[name="oldPasswd"]').val();
	var newpwd= $('#changepwd input[name="newPasswd"]').val();
	var pwdrep= $('#changepwd input[name="confirmPasswd"]').val();
	if(newpwd==""||newpwd==""||oldpwd==""){ alert("密码不许为空"); return; }
	if(newpwd!=pwdrep){ alert("密码和确认密码输入不一致"); return; }
 	$.ajaxForm({formId : 'form1', url:'changePasswd.action', 
		success:function(data){
			if(data.success == true){ alert("密码修改成功， 注销后重新登录！"); $('#changepwd input').val(''); /* parent.window.location='j_spring_security_logout'; */ }
			else { alert(data.syserr); } 
		}
	});
}
$('#changepwd input').val('');
$('#changepwd').dialog({title:"修改密码", width:400,height:200,bgiframe:true, buttons:{'确定':changePwd},autoOpen:true,draggable:false,resizable:false,modal:true});
$('#changepwd').dialog('open'); 
$('.ui-dialog-titlebar-close').css({display:"none"});
});
//-->
</script>
</body>
</html>