<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
<link rel="stylesheet" href="r/plugins/jquery/bs/third-party/validator/css/bootstrapValidator.min.css"/>
<script type="text/javascript" src="r/plugins/jquery/bs/third-party/validator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="r/plugins/jquery/bs/third-party/validator/js/language/zh_CN.js"></script>
<style>
<!--
.form-horizontal .has-feedback .form-control-feedback {
top: 0;
right: 15px;
}
.has-error .form-control-feedback {
color: #a94442;
}
.has-feedback .form-control-feedback {
position: absolute;
top: 25px;
right: 0;
display: block;
width: 34px;
height: 34px;
line-height: 34px;
text-align: center;
}
-->
</style>
</head>
<body>
<!-- 查询条件 -->
<div id="queryCondition">
	<form id="queryForm" name="queryForm" action="#" method="post">
	<ul class="qry-ul">
		<!-- <li><label for="q_customerCode">会员代码:</label><input type="text" id="q_customerCode" maxlength="20" comparison="eq" name="customerCode"/></li> -->
		<li><label for="q_mobile">手机号:</label><input type="text" id="q_mobile" comparison="like" name="mobile" ></input></li>
		<li><label for="q_realName">姓名:</label><input type="text" id="q_realName" comparison="like" name="realName" ></input></li>
		<li><label for="q_isActive">状态:</label><select id="q_isActive" ctype="PUB_VAILD" multiple="multiple" comparison="like" name="userState" ></select></li>
		<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
		<li><input class='bt' value="重置" type="reset" /></li>
	</ul>
	</form>
</div>
<!-- 列表 -->
<div id="table"></div>

<!-- 新增界面 -->
<div id="addView">
<form action="#" id="add-webUserForm" class="form-horizontal" role="form">
	<div class="form-group">
		<label class="col-sm-2 control-label">手机号:</label>
		<div class="col-sm-4">
			<input type="text" id="add-mobile" name="mobile"  class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">姓名:</label>
		<div class="col-sm-4">
			<input type="text" id="add-realName" name="realName" class="form-control" />
		</div>
		<label class="col-sm-2 control-label">证件号:</label>
		<div class="col-sm-4">
			<input type="text" id="add-idNumber" name="idNumber" class="form-control" />
		</div>
	</div>	
	<div class="form-group">
		<label class="col-sm-2 control-label">电话:</label>
		<div class="col-sm-4">
			<input type="text" id="add-phone" name="phone" class="form-control" />
		</div>
		<label class="col-sm-2 control-label">电子邮件:</label>
		<div class="col-sm-4">
			<input type="text" id="add-email" name="email" class="form-control" />
		</div>
	</div>	
	<div class="form-group">	
		<label class="col-sm-2 control-label">地址:</label>
		<div class="col-sm-6">
			<input type="text" id="add-address" name="address" class="form-control" />
		</div>
		<label class="col-sm-1 control-label">邮编:</label>
		<div class="col-sm-3">
			<input type="text" id="add-zip" name="zip"  class="form-control" />
		</div>
	</div>

</form>		
</div>

<!-- 综合查询 -->
<div id="viewOper">
	<ul>
		<li><a href="#userOper" id="user_tab">用户信息</a></li>
		<li><a href="#rcmdOper" id="rcmd_tab">推荐号</a></li>
		<li><a href="#mchntOper" id="mchnt_tab">商户信息</a></li>
		<li><a href="#loanOper" id="loan_tab">贷款信息</a></li>
	</ul>
<!-- 用户信息 -->
<div id="userOper">
<form action="#" id="webUserForm" class="form-horizontal" role="form">
	<input type="hidden" name="version"/>	
	<div class="form-group">
		<label class="col-sm-2 control-label">手机号:</label>
		<div class="col-sm-4">
			<input type="text" id="mobile" name="mobile"  class="form-control" readonly />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">姓名:</label>
		<div class="col-sm-4">
			<input type="text" id="realName" name="realName" class="form-control" 
				data-bv-stringlength data-bv-stringlength-max=15
			/>
		</div>
		<label class="col-sm-2 control-label">证件号:</label>
		<div class="col-sm-4">
			<input type="text" id="idNumber" name="idNumber" class="form-control" 
				data-bv-stringlength data-bv-stringlength-max=20
			/>
		</div>
	</div>	
	<div class="form-group">
		<label class="col-sm-2 control-label">电话:</label>
		<div class="col-sm-4">
			<input type="text" id="phone" name="phone" class="form-control" 
				data-bv-stringlength data-bv-stringlength-max=50
			/>
		</div>
		<label class="col-sm-2 control-label">电子邮件:</label>
		<div class="col-sm-4">
			<input type="text" id="email" name="email" class="form-control" 
				data-bv-stringlength data-bv-stringlength-max=150
				data-bv-email
			/>
		</div>
	</div>	
	<div class="form-group">	
		<label class="col-sm-2 control-label">地址:</label>
		<div class="col-sm-6">
			<input type="text" id="address" name="address" class="form-control" 
				data-bv-stringlength data-bv-stringlength-max=150
			/>
		</div>
		<label class="col-sm-1 control-label">邮编:</label>
		<div class="col-sm-3">
			<input type="text" id="zip" name="zip"  class="form-control" 
				data-bv-stringlength data-bv-stringlength-max=10
			/>
		</div>
	</div>
	<div class="form-group" >
		<label class="col-sm-2 control-label">会员代码:</label>
		<div class="col-sm-4">
			<input type="text" id="customerCode" name="customerCode"  class="form-control" readonly/>
		</div>
		<label class="col-sm-2 control-label">状态:</label>
		<div class="col-sm-4">
			<input type="text" id="userStateDesc" name="userStateDesc" class="form-control" readonly/>
		</div>
	</div>
	<div class="form-group" >
		<label class="col-sm-2 control-label">密码错次:</label>
		<div class="col-sm-4">
			<input type="text" id="passwdErr" name="passwdErr"  class="form-control" readonly/>
		</div>
		<label class="col-sm-2 control-label">创建时间:</label>
		<div class="col-sm-4">
			<input type="text" id="recTime" name="recTime" class="form-control" readonly/>
		</div>
	</div>
</form>	
</div>	
<!-- 推荐号 -->
<div id="rcmdOper">
<div id="rcmdGrid"></div>
</div>
<!-- 商户信息 -->
<div id="mchntOper">
<div id="mchntGrid"></div>
</div>
<!-- 贷款信息 -->
<div id="loanOper">
<div id="loanGrid"></div>
</div>
</div>
<!--  -->
<div id="addRcmdView">
<form action="#" id="add-rcmdForm" class="form-horizontal" role="form">
	<input type="hidden" name="customerCode" id="addrcmd-customerCode"/>
	<div class="form-group">
		<label class="col-sm-2 control-label">推荐号:</label>
		<div class="col-sm-10">
			<input type="text" id="add-rcmdCode" name="rcmdCode" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">描述:</label>
		<div class="col-sm-10">
			<input type="text" id="add-rcmdDesc" name="rcmdDesc" class="form-control" />
		</div>
	</div>
</form>	
</div>
<div id="updRcmdView">
<form action="#" id="upd-rcmdForm" class="form-horizontal" role="form">
	<input type="hidden" name="customerCode" id="updrcmd-customerCode"/>
	<div class="form-group">
		<label class="col-sm-2 control-label">推荐号:</label>
		<div class="col-sm-10">
			<input type="text" id="rcmdCode" name="rcmdCode" class="form-control" readonly/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">描述:</label>
		<div class="col-sm-10">
			<input type="text" id="rcmdDesc" name="rcmdDesc" class="form-control" />
		</div>
	</div>
</form>	
</div>

<script type="text/javascript">
<!--
// url
var url = {
grid: 'webAgentPage.action',
excel:'webAgentExcel.action',
lock:'lockWebAgent.action',
unlock:'unlockWebAgent.action',
resetpasswd:'resetWebAgentPasswd.action',
randCd:'randomCode.action',
add:'addWebAgent.action',
upd:'updWebAgent.action',
rcmdlist:'webAgentRcmdList.action',
addRcmd:'addWebAgentRcmd.action',
updRcmd:'updWebAgentRcmd.action',
delRcmd:'delWebAgentRcmd.action'
};
// fields definition
$(function() {
	 $('#webUserForm').bootstrapValidator({
		 message: '格式有问题', live: 'enabled',
	     feedbackIcons: { valid: 'glyphicon glyphicon-ok', invalid: 'glyphicon glyphicon-remove', validating: 'glyphicon glyphicon-refresh'}
	 });
	 $('#add-webUserForm').bootstrapValidator({
        message: '格式有问题',
        live: 'enabled',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	mobile: {
                message: '请填写手机号',
                validators: {
                    notEmpty: {  message: '请填写手机号' }, stringLength:{ min:11, max:11}
                }
            }
        	,realName: {
                message: '请填写姓名',
                validators: {
                    stringLength:{ min:0, max:15}
                }
            }
        	,idNumber: {
                message: '请填写证件号',
                validators: {
                    stringLength:{ min:0, max:20}
                }
            }
        	,phone: {
                message: '请填写联系电话',
                validators: {
                    stringLength:{ min:0, max:50}
                }
            }
        	,email: {
                message: '请填写邮件地址',
                validators: {
                    stringLength:{ min:0, max:150}
        			,emailAddress: { message:'不合法的邮件地址'}
                }
            }
        	,address: {
                message: '请填写地址',
                validators: {
                    stringLength:{ min:0, max:150}
                }
            }
        	,zip: {
                message: '请填写邮编',
                validators: {
                    stringLength:{ min:0, max:10}
                }
            }
        }
	});
	$('#add-rcmdForm').bootstrapValidator({
        message: '格式有问题',
        live: 'enabled',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	rcmdCode: {
                message: '请填写推荐号',
                validators: {
                    notEmpty: {  message: '请填写推荐号' }
        			, stringLength:{ min:15, max:50}
                }
            }
        	,rcmdDesc: {
        		 message: '请填写描述',
        		 validators: {
         			stringLength:{ min:0, max:200}
                 }
        	}
        }
	});
	$('#upd-rcmdForm').bootstrapValidator({
        message: '格式有问题',
        live: 'enabled',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	rcmdDesc: {
        		 message: '请填写描述',
        		 validators: {
         			stringLength:{ min:0, max:200}
                 }
        	}
        }
	});
	// 新增代理会员
	$('#addView').dialog({title:'新增代理会员', autoOpen: false, bgiframe: true, modal: false, resizable: false, draggable: false
		,buttons:{
			 '保存':function(){
				$('#add-webUserForm').bootstrapValidator('validate');
				if ( $('#add-webUserForm').data('bootstrapValidator').isValid() ) {
					$.ajaxForm({formId: 'add-webUserForm', url: url.add,
					success: function(data) {
						if (data.success) {
							$('#addView').dialog('close');
							$('#table').flexAddData(data.root[0]);
							alert('操作成功'); 
						} else {alert('操作失败 原因是'+ data.syserr); }
					}});
				} else { return false; }  
			 }
			,'关闭': function(){$(this).dialog('close');}
		}});
	$('#addView').fullScreen();
	
	$('#viewOper').tabs();
	$('#viewOper').dialog({ title:'代理会员信息', autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 850, height : 570});
	$('#viewOper').fullScreen();

	$('#addRcmdView').dialog({ title:'新增推荐码', autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 480, height : 270
		,buttons:{
			'保存':function(){
				$('#add-rcmdForm').bootstrapValidator('validate');
				if ( $('#add-rcmdForm').data('bootstrapValidator').isValid() ) {
					$.ajaxForm({formId: 'add-rcmdForm', url: url.addRcmd,
					success: function(data) {
						if (data.success) {
							$('#addRcmdView').dialog('close');
							$('#rcmdGrid').flexAddData(data.root[0]);
							alert('操作成功'); 
						} else {alert('操作失败 原因是'+ data.syserr); }
					}});
				} else { return false; } 
			}
			,'关闭': function(){$(this).dialog('close');}
		}
	});
	$('#updRcmdView').dialog({ title:'修改推荐码', autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 480, height : 270
		,buttons:{
			'保存':function(){
				$('#upd-rcmdForm').bootstrapValidator('validate');
				if ( $('#upd-rcmdForm').data('bootstrapValidator').isValid() ) {
					$.ajaxForm({formId: 'upd-rcmdForm', url: url.updRcmd,
					success: function(data) {
						if (data.success) {
							$('#updRcmdView').dialog('close');
							$('#rcmdGrid').flexModifyData(data.root[0]);
							alert('操作成功'); 
						} else {alert('操作失败 原因是'+ data.syserr); }
					}});
				} else { return false; } 
			}
			,'关闭': function(){$(this).dialog('close');}
		}
	});
	
	/*=================锁定===============*/
	var updAgent = function(rec,id) {
		if (!rec) { alert("请选择一条记录"); return; }
		var frmValid = $('#webUserForm').data('bootstrapValidator'); 
		frmValid.validate();
		if ( frmValid.isValid() ) {
			$.ajaxForm({formId: 'webUserForm', url: url.upd,
			success: function(data) {
				if (data.success) {
					$('#viewOper').dialog('close');
					$('#table').flexModifyData(data.root[0]);
					alert('操作成功'); 
				} else { 
					alert('操作失败 原因是'+ data.syserr); 
				}
			}});
		} else { return false; } 
	};
	var lock = function(rec,id){
		if (!rec) { alert("请选择一条记录"); return; }
		if(!confirm("是否锁定用户?")){return;}
		var obj = new Object();
		obj.version = rec.version;
		obj.customerCode = rec.customerCode;
		obj.status = '1';
		$.ajax({type:'post', url:url.lock, dataType:'json', data: {jsonObject : $.toJSON(obj)},
			success : function(data){
				if (data.success) {
					alert('操作成功');
					var res = data.root[0];
					$('#table').flexModifyData(res);
					if ($('#viewOper').dialog('isOpen')) {
						
						$('#viewOper').dialog("option","buttons",{
							 '保存':function(){updAgent(res,id)}
							,'解锁': function(){unlock(res,id);}
							,'重置密码':function() { resetpw(res,id); }
							,'关闭': function(){$(this).dialog('close');}
						});
						
						$('#webUserForm').fillObject({obj:res});
						$('#recTime').val(frmTime(res.recTime));
					} 
				} else {alert('操作失败 原因是' + data.syserr);}
			}});
	};
	/*=================解锁 ===============*/
	var unlock = function(rec,id){
		if (!rec) { alert("请选择一条记录"); return; }
		if(!confirm("是否解锁用户?")){return;}
		var obj = new Object();
		obj.version = rec.version;
		obj.customerCode = rec.customerCode;
		$.ajax({type:'post',url:url.unlock, dataType:'json', data : {jsonObject : $.toJSON(obj)},
			success : function(data){
				if (data.success) {
					alert('操作成功');
					var res = data.root[0];
					$('#table').flexModifyData(res);
					if ($('#viewOper').dialog('isOpen')) {
						
						$('#viewOper').dialog("option","buttons",{
							'保存':function(){updAgent(res,id)}
							,'锁定': function(){lock(res,id);}
							,'重置密码':function() { resetpw(res,id); }
							,'关闭': function(){$(this).dialog('close');}
						});
						
						$('#webUserForm').fillObject({obj:res});
						$('#recTime').val(frmTime(res.recTime));
					}
				} else {alert('操作失败 原因是' + data.syserr);}
			}});
	};
	/*================= 重置密码 ===============*/
	var resetpw=function(rec,id){
		if(confirm("该操作将重置该用户密码, 默认密码为手机号后六位, 是否进行? ")){ 
			var obj = new Object(); obj.customerCode = rec.customerCode; obj.version=rec.version;
			$.ajax({url: url.resetpasswd, dataType:'json', data: {jsonObject : $.toJSON(obj)},
				success:function(data){ 
					if(data.success == true){ 
						alert("密码重置成功"); 
					} else {
						alert('操作失败 原因是' + data.syserr);
					} 
				}
			});
		}
	};
	var view=function(rec,id){
		if (!rec) { alert("请选择一条记录"); return; }
		if ( rec.userState == '0') {
			$('#viewOper').dialog("option","buttons",{
				'保存':function(){updAgent(rec,id)}
				,'锁定': function(){lock(rec,id);}
				,'重置密码':function() { resetpw(rec,id); }
				,'关闭': function(){$(this).dialog('close');}
			});
		} else {
			$('#viewOper').dialog("option","buttons",{
				'保存':function(){updAgent(rec,id)}
				,'解锁': function(){unlock(rec,id);}
				,'重置密码':function() { resetpw(rec,id); }
				,'关闭': function(){$(this).dialog('close');}
			});
		}
		//$('#webUserForm')[0].reset();
		$('#webUserForm').data('bootstrapValidator').resetForm(true);
		$('#webUserForm').fillObject({obj:rec});
		$('#recTime').val(frmTime(rec.recTime));
		var qobj=new Object();
		qobj.customerCode=rec.customerCode;
		$.ajax({ url:'findMchntbyCustomerCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#mchntGrid').refreshWithData(res); }});	
		$.ajax({ url:'findLoanbyCustomerCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#loanGrid').refreshWithData(res); }});
		
		$.ajax({ url:url.rcmdlist,type:'POST',async:false,dataType:'json',data:{jsonObject:$.toJSON(qobj)}, success:function(data){if(data.success) $('#rcmdGrid').refreshWithData(data); }});
		$('#viewOper').dialog('open');
	};
	var add = function() {
		$('#add-webUserForm')[0].reset();
		$('#addView').dialog('open');
	};
	var addRcmd = function() {
		$('#add-rcmdForm')[0].reset();
		$('#addrcmd-customerCode').val($('#customerCode').val());
		$('#addRcmdView').dialog('open');
	};
	var updRcmd = function(rec,id) {
		if (!rec) { alert("请选择一条记录"); return; }
		$('#upd-rcmdForm')[0].reset();
		$('#upd-rcmdForm').fillObject({obj:rec});
		$('#updRcmdView').dialog('open');
	};
	var delRcmd = function(rec,id) {
		if (!rec) { alert("请选择一条记录"); return; }
		var obj = new Object(); obj.customerCode = rec.customerCode; obj.rcmdCode = rec.rcmdCode;
		$.ajax({url: url.delRcmd, dataType:'json', method:'POST',data: {jsonObject : $.toJSON(obj)},
			success:function(data){ 
				if(data.success == true){
					alert("操作成功"); 
					$('#rcmdGrid').flexRemoveData(data.root[0]);
				} else {
					alert('操作失败 原因是' + data.syserr);
				} 
			}
		});
	};
	// datagrid
	$('#table').flexigrid({
		url : url.grid, sort : "customerCode", checkbox: false, usepager: true,dbClickRecord:view,
		buttons : [
		 { name: "综合查询",bclass: 'grid_view',id: 'cel',onpress: view, aclass:'ui-button-primary'}
		,{ name: "新增代理会员",bclass: 'grid_add',id: 'cel',onpress: add, aclass:'ui-button-primary'}
		,{ name: "锁定",show:'ROLE_MEMBER_DELETE',bclass: 'grid_lock',id: 'cel',onpress: lock, aclass:'ui-button-danger'}
		,{ name: "解锁",show:'ROLE_MEMBER_RECOVER',bclass: 'grid_unlock',id: 'cel',onpress: unlock, aclass:'ui-button-danger'}
		,{ name: "重置密码",show:'ROLE_MEMBER_RESET',bclass: 'grid_edit',id: 'cel',onpress: resetpw, aclass:'ui-button-danger'}
		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		],
		colModel : [
		       	// { display : '会员代码', dataIndex : 'customerCode', width : 120, align : 'center', sortable:true }
		       	 { display : '手机号码', dataIndex : 'mobile', width : 200, align : 'center', sortable:true }
		       	,{ display : '姓名', dataIndex : 'realName', width : 80, align : 'center', sortable:true }
		       	,{ display : '创建时间', dataIndex : 'recTime', width : 150, align : 'center', sortable:true, render: fmtTime  }
		       	,{ display : '地址', dataIndex : 'address', width : 180, align : 'center', sortable:true }
		       	//,{ display : '最后登录时间', dataIndex : 'lastLoginTime', width : 150, align : 'center', sortable:true, render: fmtTime }
		       	//,{ display : '最后登录IP', dataIndex : 'lastLoginIp', width : 150, align : 'center', sortable:true }
		    	,{ display : '证件号', dataIndex : 'idNumber', width : 150, align : 'center', sortable:true }
		    	,{ display : '状态', dataIndex : 'userStateDesc', width : 80, align : 'center', sortable:true  }
		       	]
	});
	
	$('#mchntGrid').flexigrid({
		url : 'findMchntbyCustomerCode.action', sort : "mchntCode", usepager : true,
		colModel : [
		             { display: '内部商户号', dataIndex: 'mchntCode', width:100, align: 'center',sortable:true }
		            ,{ display: '营业执照号码', dataIndex: 'licNo', width:100, align: 'center',sortable:true }
			        ,{ display: '商户代码', dataIndex: 'mchntCd', width:100, align: 'center',sortable:true }
			        ,{ display: '税务登记代码', dataIndex: 'reveRegCd', width:120, align: 'center',sortable:true }
			        ,{ display: '商户中文名称', dataIndex: 'mchntCnName', width:120, align: 'center',sortable:true }			        
			        ,{ display: '注册地址', dataIndex: 'regAddr', width:120, align: 'center',sortable:true }
			        ,{ display: '法人代表姓名', dataIndex: 'artifNm', width:120, align: 'center',sortable:true }
			        ,{ display: '法人代表证件号', dataIndex: 'artifCertifId', width:120, align: 'center',sortable:true }
			        ,{ display: '商户状态', dataIndex: 'mchntStDesc', width:120, align: 'center',sortable:true }
			        ,{ display: '客户端状态', dataIndex: 'dispMchntStDesc', width:100, align: 'center', sortable:true }
		]
	});
	$('#loanGrid').flexigrid({
		url : 'findLoanbyCustomerCode.action', sort : "loanId", usepager : true,
		colModel : [
		             { display: '贷款编号', dataIndex: 'loanId', width:100, align: 'center',sortable:true }
		            ,{ display: '贷款金额', dataIndex: 'loanAt', width:100, align: 'center',sortable:true,render:fmtMoney }
			        ,{ display: '贷款周期', dataIndex: 'loanCycle', width:100, align: 'center',sortable:true }
			        ,{ display: '贷款利率', dataIndex: 'loanRt', width:120, align: 'center',sortable:true,render:fmtRate }
			        ,{ display: '放款成功时间', dataIndex: 'loanSuccTm', width:120, align: 'center',sortable:true, render: fmtTime }			        
			        ,{ display: '贷款余额', dataIndex: 'loanBalance', width:120, align: 'center',sortable:true,render:fmtMoney }
			        ,{ display: '贷款状态', dataIndex: 'creditStDesc', width:120, align: 'center',sortable:true }
			        ,{ display: '用户撤销', dataIndex: 'cancelFlagDesc', width:100, align: 'center',sortable:true }
		]
	});
	$('#rcmdGrid').flexigrid({
		url : url.rcmdlist, sort : "rcmdCode", usepager : false,
		buttons : [
	  		 { name: "新增",bclass: 'grid_add',id: 'cel',onpress: addRcmd, aclass:'ui-button-primary'}
	  		,{ name: "修改",bclass: 'grid_edit',id: 'cel',onpress: updRcmd, aclass:'ui-button-primary'}
	  		,{ name: "删除",bclass: 'grid_del',id: 'cel',onpress: delRcmd, aclass:'ui-button-danger'}
	  		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#rcmdGrid').flexFile(url.excel+"?jsonObject={\"customerCode\":"+$('#customerCode').val()+"}"); }}
		],
		colModel : [
			 { display : '推荐号', dataIndex : 'rcmdCode', width : 120, align : 'center', sortable:true }
			,{ display : '描述', dataIndex : 'rcmdDesc', width : 120, align : 'center', sortable:true }
			,{ display : '推荐数', dataIndex : 'rcmdCnt', width : 80, align : 'center', sortable:true }        
		]
	});
	//
	$.jyform.sysCodeBoxes();
	// buttons
	$('#queryBtn').click(function(){$.queryTable({formId : 'queryForm', tableId : 'table'});});
	$('.qry-ul input[type="button"]').addClass('ui-button-primary');
	$('.bt').addClass('ui-btn-sm').button();
	$('#queryForm .qry-ul input[type="button"]').click();
	
});
//-->
</script>
</body>
</html>