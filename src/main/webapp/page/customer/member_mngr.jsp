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
<div id="viewOper">
	<ul>
		<li><a href="#userOper" id="user_tab">用户信息</a></li>
		<li><a href="#loanOper" id="loan_tab">贷款信息</a></li>
	</ul>
<!-- 用户信息 -->
<div id="userOper">
<form action="#" id="webUserForm" class="form-horizontal" role="form">
	<input type="hidden" name="version"/>
	
	<div class="form-group">
		<label class="col-sm-2 control-label">手机号:</label>
		<div class="col-sm-4">
			<input type="text" id="mobile" name="mobile"  class="form-control" readonly/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">姓名:</label>
		<div class="col-sm-4">
			<input type="text" id="realName" name="realName" class="form-control" readonly/>
		</div>
		<label class="col-sm-2 control-label">证件号:</label>
		<div class="col-sm-4">
			<input type="text" id="idNumber" name="idNumber" class="form-control" readonly/>
		</div>
	</div>	
	<div class="form-group">
		<label class="col-sm-2 control-label">电话:</label>
		<div class="col-sm-4">
			<input type="text" id="phone" name="phone" class="form-control" readonly/>
		</div>
		<label class="col-sm-2 control-label">电子邮件:</label>
		<div class="col-sm-4">
			<input type="text" id="email" name="email" class="form-control" readonly/>
		</div>
	</div>	
	<div class="form-group">	
		<label class="col-sm-2 control-label">地址:</label>
		<div class="col-sm-6">
			<input type="text" id="address" name="address" class="form-control" readonly/>
		</div>
		<label class="col-sm-1 control-label">邮编:</label>
		<div class="col-sm-3">
			<input type="text" id="zip" name="zip"  class="form-control" readonly/>
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
		<label class="col-sm-2 control-label">推荐代码:</label>
		<div class="col-sm-4">
			<input type="text" id="rcmdCode" name="rcmdCode"  class="form-control" readonly/>
		</div>
		<label class="col-sm-2 control-label">推荐人:</label>
		<div class="col-sm-4">
			<input type="text" id="fatherCustomerCodeDesc" name="fatherCustomerCodeDesc" class="form-control" readonly/>
		</div>
	</div>
	<div class="form-group" >
		<label class="col-sm-2 control-label">密码错次:</label>
		<div class="col-sm-4">
			<input type="text" id="passwdErr" name="passwdErr"  class="form-control" readonly/>
		</div>
		<label class="col-sm-2 control-label">注册时间:</label>
		<div class="col-sm-4">
			<input type="text" id="recTime" name="recTime" class="form-control" readonly/>
		</div>
	</div>
</form>	
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
<script type="text/javascript">
<!--
// url
var url = {
grid: 'webUserPage.action',
excel:'webUserExcel.action',
lock:'lockWebUser.action',
unlock:'unlockWebUser.action',
resetpasswd:'resetWebUserPasswd.action'
};
// fields definition
$(function() {
	$('#viewOper').tabs();
	$('#viewOper').dialog({ title:'综合信息',autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 850, height : 570});
	$('#viewOper').fullScreen();

	/*=================锁定===============*/
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
							'关闭': function(){$(this).dialog('close');}
							,'解锁': function(){unlock(res,id);}
							,'重置密码':function() { resetpw(res,id); }
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
							'关闭': function(){$(this).dialog('close');}
							,'锁定': function(){lock(res,id);}
							,'重置密码':function() { resetpw(res,id); }
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
				'关闭': function(){$(this).dialog('close');}
				,'锁定': function(){lock(rec,id);}
				,'重置密码':function() { resetpw(rec,id); }
			});
		} else {
			$('#viewOper').dialog("option","buttons",{
				'关闭': function(){$(this).dialog('close');}
				,'解锁': function(){unlock(rec,id);}
				,'重置密码':function() { resetpw(rec,id); }
			});
		}
		$('#webUserForm')[0].reset();
		$('#webUserForm').fillObject({obj:rec});
		$('#recTime').val(frmTime(rec.recTime));
		var qobj=new Object();
		qobj.customerCode=rec.customerCode;
		$.ajax({ url:'findMchntbyCustomerCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#mchntGrid').refreshWithData(res); }});	
		$.ajax({ url:'findLoanbyCustomerCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#loanGrid').refreshWithData(res); }});
		$('#viewOper').dialog('open');
		$('#user_tab').click();
	};
	// datagrid
	$('#table').flexigrid({
		url : url.grid, sort : "customerCode", checkbox: false, usepager: true,dbClickRecord:view,
		buttons : [
		 { name: "综合查询",bclass: 'grid_view',id: 'cel',onpress: view, aclass:'ui-button-primary'}
		,{ name: "锁定会员",show:'ROLE_MEMBER_DELETE',bclass: 'grid_lock',id: 'cel',onpress: lock, aclass:'ui-button-danger'}
		,{ name: "解锁会员",show:'ROLE_MEMBER_RECOVER',bclass: 'grid_unlock',id: 'cel',onpress: unlock, aclass:'ui-button-danger'}
		,{ name: "重置密码",show:'ROLE_MEMBER_RESET',bclass: 'grid_edit',id: 'cel',onpress: resetpw, aclass:'ui-button-danger'}
		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		],
		colModel : [
		       	// { display : '会员代码', dataIndex : 'customerCode', width : 120, align : 'center', sortable:true }
		       	 { display : '手机号码', dataIndex : 'mobile', width : 200, align : 'center', sortable:true }
		       	,{ display : '姓名', dataIndex : 'realName', width : 80, align : 'center', sortable:true }
		       	,{ display : '注册时间', dataIndex : 'recTime', width : 150, align : 'center', sortable:true, render: fmtTime  }
		       	//,{ display : '推荐号', dataIndex : 'rcmdCode', width : 80, align : 'center', sortable:true }
		       	//,{ display : '地址', dataIndex : 'address', width : 180, align : 'center', sortable:true }
		       	//,{ display : '最后登录时间', dataIndex : 'lastLoginTime', width : 150, align : 'center', sortable:true, render: fmtTime }
		       	//,{ display : '最后登录IP', dataIndex : 'lastLoginIp', width : 150, align : 'center', sortable:true }
		    	//,{ display : '证件号', dataIndex : 'idNumber', width : 150, align : 'center', sortable:true }
		    	,{ display : '状态', dataIndex : 'userStateDesc', width : 80, align : 'center', sortable:true  }
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