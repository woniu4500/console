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
#addOper .restree{ overflow:auto; background:#eee; }
#addOper .restree h3{padding: 10px; margin: 0; font-size: 16px;font-family: "Microsoft YaHei", "NSimSun";}
-->
</style>
</head>
<body> 
<!-- 查询条件  -->
<div id="queryCondition">
<form id="queryForm" name="queryForm" action="#" method="post">
<ul class="qry-ul">
		<li><label for="q_loginId">操作员登录名:</label>
		<input  id="q_loginId" name="loginId" type="text" maxlength="10" qtype="string" comparison="like" /></li>
		<li><label for="q_userName">操作员姓名:</label>
		<input  id="q_userName" name="userName" type="text" maxlength="20" qtype="string" comparison="like" /></li>
		<li><label for="q_userId">操作员编号:</label><input id="q_userId" name="userId" type="text" maxlength="10" qtype="string" comparison="like" /></li>
	    <li><label for="q_orgCode">所属机构:</label><select id="q_orgCode" name="orgCode" comparison="eq" multiple="multiple"></select></li>
		<li><label for="q_status">状态:</label><select id="q_status" comparison="eq" name="status">
					<option value="0" selected="selected">正常</option>
					<option value="1">已删除</option>
			</select>
		</li>
		<li><input class='bt' id="queryBtn" value="查询" type="button" /></li>
		<li><input value="重置" class='bt' type="reset" /></li>
</ul>
</form>
</div>
<div id="table"></div>

<div id="addOper"  style="display: none;">
<table width="99.6%">
<tr>
	<td width="380" style="vertical-align: top" >
		<form id="form1" action="doInsertOper.action">
			<input type="hidden" id="version" name="version" /> <input
				type="hidden" id="orgCode" name="orgCode" /> <input type="hidden"
				id="status" name="status" />
			<ul class='frm-ul'>

				<li><label for="userId">操作员编号:</label>
				<input id="userId"
					name="userId" type="text" /></li>
				<li><label for="userName"><font color="red">*</font>操作员姓名:</label>
					<input id="userName" name="userName" type="text" maxLength="20" />
					<div id="userNameTip"></div></li>
				<li><label for="orgName"><font color="red">*</font>所属机构:</label>
					<input id="orgName" name="orgName" maxLength="30"
					readonly="readonly" type="text" />
					<div id="orgNameTip"></div></li>
				<li><label for="crdType"><font color="red">*</font>证件类型:</label>
					<select id="crdType" name="crdType" type="text">
				</select>
					<div id="crdTypeTip"></div></li>
				<li><label for="crdNo"><font color="red">*</font>证件号:</label>
				<input
					id="crdNo" name="crdNo" maxLength="30" type="text" />
					<div id="crdNoTip"></div></li>
				<li><label for="userPhone">电话:</label> <input id="userPhone"
					name="userPhone" maxLength="20" type="text" />
					<div id="userPhoneTip"></div></li>
				<li><label for="userEmail">E-MAIL:</label> <input
					id="userEmail" name="userEmail" maxLength="50" type="text" />
					<div id="userEmailTip"></div></li>
				<li><label for="loginId"><font color="red">*</font>登录名:</label>
					<input id="loginId" name="loginId" maxLength="40" type="text" />
					<div id="loginIdTip"></div></li>
				<li><label for="loginPasswd"><font color="red">*</font>登录密码:</label>
					<input id="loginPasswd" name="loginPasswd" maxLength="12"
					type="password" />
					<div id="loginPasswdTip"></div></li>
				<li><label for="pwdchk"><font color="red">*</font>确认密码:</label>
					<input id="pwdchk" name="pwdchk" maxLength="12" type="password" />
					<div id="pwdchkTip"></div></li>
				<li><label for="statusDesc">操作员状态:</label> <input
					class="qFormInput" id="statusDesc" name="statusDesc" type="text"
					readonly="readonly" disabled="disabled" /></li>
			</ul>
		</form>
		</td>
		
<td style="vertical-align: top" class="restree has-border">
<h3>角色列表</h3>
<div id="roleGrid" ></div>
</td>
</tr>
</table>
</div>
<!-- 上级机构选择弹出窗口 -->
<div id="orgSelect" style="display: none;">
<form id="q-org-frm" name="q-org-frm" action="#" method="post">
		<input type="hidden" name="status" value="0" comparison="eq" qtype="string" />
<ul class="qry-ul">
		<li><label for="f_orgCode">机构代码:</label><input  type="text" id="f_orgCode" name="orgCode" comparison="like" qtype="string"/></li>
		<li><label for="orgName">机构名称:</label><input type="text" name="orgName" comparison="like" qtype="string"/></li>
	  	<li><input type="button" class="bt qbutton" id="querysup"  value="查询" onclick="$.queryTable({formId:'q-org-frm',tableId:'org-table'});"/></li>
	   	<li><input type="reset" class="bt" value="重置"/></li>
	   	
</ul>
<div id="org-table" class="is-grid"></div>
</form>
</div>

<script type="text/javascript">
<!--
$(function(){
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
		width : 500, height : 400, draggable:true, title: '选择机构',sort : "orgCode",
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
	$('.bt').addClass('ui-btn-sm').button();
// 上级选择机构按钮
$('#orgName').dblclick(chooseOrg);
// Validate
$.formValidator.initConfig({ onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
$("#loginId").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, max:10, onerror : "登录名最多10个字节(数字，字母或下划线)" }).regexValidator({ regexp : "^[A-Za-z0-9_]+$", onerror : "登陆名仅能输入数字，字母或下划线" });
$("#userName").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, max: 20, onerror : "用户名最多20个字符" });
$("#orgName").formValidator({ empty : false, onshow : "双击选择机构", oncorrect : "√", onfocus : "双击选择机构"});
$("#loginPasswd").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min: 1, max: 12, onerror : "密码最多12个字符" }).regexValidator({ regexp : "^[A-Za-z0-9]+$", onerror : "密码仅能输入数字或字母" });
$("#pwdchk").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).regexValidator({ regexp : "^[A-Za-z0-9]+$", onerror : "仅能输入数字或字母" }).compareValidator({ desid : "loginPasswd", operateor : "=", onerror : "2次密码不一致,请确认" });
$("#status").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" });
$("#crdNo").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" }).inputValidator({ min : 1, max : 18, onerror : "证件号长度18位" });
$("#email") .formValidator({ empty : true, oncorrect : "√" }) .inputValidator({ min : 1, max : 50, onerror : "EMAIL最多50个字符" }) .regexValidator( { regexp : "^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$", onerror : "邮箱格式不正确" });
$('#addOper').dialog({ autoOpen: false });
$('#addOper').fullScreen(function(){$('#roleGrid').flexFixHeight();});

// Operate
var add = function() {
	$("div[id$=Tip]").empty();
	$('#form1 :input').removeAttr('disabled');
	$('#form1>select').removeAttr('disabled');
	$('#form1')[0].reset();
	$('#userId').val("<由系统自动生成>").attr('style','font-style:italic').attr('disabled','disabled');				
	$('#statusDesc').val("正常").attr('style','font-style:italic').attr('disabled','disabled');
	$.ajax({ url:'loadUserTreeWithUnChecked.action', async:false, success:function(data){var res=$.parseJSON(data);if(res.success) $('#roleGrid').refreshWithData(res); }});
	$('#addOper').dialog("option","title","新增操作员");
	$('#addOper').dialog("option","buttons",{
	'确定': function() {
		if ($.formValidator.pageIsValid('1')) {
			if(!$('#orgCode').val()){ alert('所属机构必选择'); return;}
			var roles=""; var data = $('#roleGrid').flexAllSelectData(); $.each(data,function(i) { if (i== 0) {roles = roles + this.roleId} else { roles = roles + ',' + this.roleId}}) ;
			if( roles == "" ){ alert("请选择一个角色"); return; }
			$.ajaxForm({formId : 'form1', url : "doInsertUser.action",
				dealData : function(data) { data.roles=roles; },
				success : function(data) { 
					if (data.success) { alert('操作成功'); $('#addOper').dialog('close'); $('#table').flexAddData(data.root[0]); } 
					else { alert('操作失败 原因是'+ data.syserr); }
				}
			});
		} else {return false; }
	}, '关闭' : function() { $(this).dialog('close'); }
	});
	$('#addOper').dialog('open');
};

var edit = function(rec,id) {
	$("div[id$=Tip]").empty();
	$('#form1 :input').removeAttr('disabled');
	$('#form1>select').removeAttr('disabled');
	$('#userId').attr('disabled','true');
	$('#loginId').attr('disabled','true');
	$('#loginPasswd, #pwdchk').attr('disabled','true');
	$('#statusDesc').attr('disabled','true');
	if (!rec) { alert("请选择一条记录"); return; }
	$('#form1')[0].reset();
	$('#form1 :input').each(function(i,n) { if (n.name in rec) {n.value = rec[n.name]; } });
	$('#loginPasswd, #pwdchk').val("00000000");
	$.ajax({ url:'loadUserTreeWithChecked.action', data:{jsonObject:$.toJSON(rec)}, async:false, success:function(data){var res=$.parseJSON(data);if(res.success) $('#roleGrid').refreshWithData(res); }});
	$('#addOper').dialog("option","title","编辑操作员信息");
	$('#addOper').dialog("option","buttons",{
		'重置密码':function(){
			var isable=confirm("该操作将重置该用户密码为1111, 是否进行? ");
			if(isable){ 
				var obj = new Object(); obj.userId = rec.userId; obj.version=rec.version;
				$.ajax({url:'doResetUserPasswd.action', data : {jsonObject : $.toJSON(obj)},
					success:function(data){ var res=$.parseJSON(data); if(res.success == true){ alert("密码修改成功"); } else alert(res.syserr); }
				});
			}
		},'更新':function() {
			if ($.formValidator.pageIsValid('1')) {
				var roles=""; var data = $('#roleGrid').flexAllSelectData(); $.each(data,function(i) { if (i== 0) {roles = roles + this.roleId} else { roles = roles + ',' + this.roleId}}) ;
				if( roles == "" ){ alert("请选择一个角色"); return; }
				$.ajaxForm({formId : 'form1', url : 'doUpdateUser.action',
					dealData : function(data) {data.userId = rec.userId; data.roles=roles;},
					success : function(data) {
						if (data.success) { alert('操作成功'); $('#addOper').dialog('close'); $('#table').flexModifyData(data.root[0]);} 
						else {alert('操作失败 原因是'+ data.syserr);}
					}
				});
			} else {return false;}
		},'关闭' : function() { $(this).dialog('close'); }
	});
	$('#addOper').fullScreen().dialog('open');
};
$('#roleGrid').flexigrid({
	url : 'loadUserTreeWithUnChecked.action', limit: 100, width:300, checkbox : true, usepager : false,					
	colModel : [
	 {display: '角色编号', width: 80, dataIndex: 'roleId', align: 'center' }
	,{display: '角色名称', width: 150, dataIndex: 'roleName', align: 'center' }
	]
});
var del = function(rec,id) {
	if (!rec) { alert("请选择一条记录"); return; }
	if (!confirm("确认要删除该操作员吗？")) { return; }
	var obj = new Object();
	obj.userId = rec.userId; obj.version=rec.version;
	$.ajax({type : 'POST', url : 'doDeleteUser.action', data : {jsonObject : $.toJSON(obj)},
		success : function(data) {
			var res = $.parseJSON(data);
			if (res.success) { alert('操作成功'); $('#table').flexRemoveData(res.root[0]); } 
			else { alert('操作失败 原因是'+ res.syserr); }
		}
	});
};

$('#table').flexigrid({
	url : 'findUser.action', sort : "loginId", checkbox : false, usepager : true, dbClickRecord: edit,
	buttons: [
      { name: "新增",show:'ROLE_SEC_USER_ADD',bclass: 'grid_add',id: 'txt',onpress: add, aclass:'ui-button-primary' }
     ,{ name: "编辑",show:'ROLE_SEC_USER_UPDATE',bclass: 'grid_edit',id: 'cel',onpress: edit, aclass:'ui-button-primary' }
     ,{ name: "删除",show:'ROLE_SEC_USER_DEL',bclass: 'grid_del',id: 'txt',onpress: del, aclass:'ui-button-danger' }
 	 ,{ name: "导出Excel", bclass: 'grid_excel',  id : 'excel',  onpress: function() {$('#table').flexFile('findUserExcel.action');} }
	],
	colModel : [
	  { display: '操作员编号', dataIndex: 'userId', width: 80, align: 'center',sortable:true}
	 ,{ display: '操作员姓名', dataIndex: 'userName', width:100, align: 'center',sortable:true }
	 ,{ display: '操作员登录名', dataIndex: 'loginId', width:120, align: 'center',sortable:true }
	 ,{ display: '所属机构', dataIndex: 'orgName', width:120, align: 'center',sortable:true }
	 ,{ display: '操作员状态', dataIndex: 'statusDesc', width:100, align: 'center',sortable:true }
	 ,{ display: '证件类型', dataIndex: 'crdTypeDesc', width:100, align: 'center',sortable:true }
	 ,{ display: '证件号', dataIndex: 'crdNo', width:100, align: 'center',sortable:true }
	]
});
$.jyform.ajaxSelectBox({selectId:'q_orgCode',valueField:'orgCode',displayField:'orgName',url:'findOrgCombo.action',func:function(){$.jyform.multiSelectBox('q_orgCode');}});
$.jyform.syscodeBox({selectId:'crdType',ctype:'PUB_CERT_TYPE'});
$.jyform.multiSelectBox('q_status');
$('#queryBtn').addClass('ui-button-primary').click(function(){$.queryTable({formId : 'queryForm', tableId : 'table'});});
$.queryTable({ formId : 'queryForm', tableId : 'table' });
});
//-->
</script>
</body>
</html>