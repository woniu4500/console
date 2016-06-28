<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
<link rel="stylesheet" href="r/plugins/jquery/bs/third-party/validator/css/bootstrapValidator.min.css"/>
<script type="text/javascript" src="r/plugins/jquery/bs/third-party/validator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="r/plugins/jquery/bs/third-party/validator/js/language/zh_CN.js"></script>
<style>
.frm-ul li label{min-width:120px}
h5{font-weight: 700;}

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
</style>
</head>
<body>
<!-- 查询条件 -->
<div id="queryCondition">
	<form id="queryForm" name="queryForm" action="#" method="post">
		<ul class="qry-ul">
		<li><label for="q_mchntCd">商户代码:</label><input type="text" id="q_mchntCd" comparison="like" name="mchntCd" ></input></li>
		<li><label for="q_licNo">营业执照号:</label><input type="text" id="q_licNo" comparison="like" name="licNo" ></input></li>
		<li><label for="q_cupLoanId">银联贷款编号:</label><input type="text" id="q_cupLoanId" comparison="like" name="cupLoanId" ></input></li>
		<li><label for="q_creditSt">贷款状态:</label><select id="q_creditSt" ctype="MCHNT_LOAN_ST" comparison="like" name="creditSt" multiple="multiple" ></select></li>
		
		<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
		<li><input class='bt' value="重置" type="reset" /></li>
</ul>
	</form>
</div>
<!-- 列表 -->
<div id="table"></div>

<!-- 贷款信息修改 -->
<div id="updateOper" style="display: none; min-width: 1050px;">
<div>
<form id="updform"  class="form-horizontal" role="form" >
	<input type="hidden" name="version" />
	<input type="hidden" name="loanId" />
	<h5>商户信息</h5>
	<div class="form-group">
		<label for="u-mchntCode" class="col-sm-2 control-label">内部商户号:</label>
		<div class="col-sm-4 input-group">
			<input id="u-mchntCode" type="text" name="mchntCode" class="form-control" readonly="readonly"/>
			<span class="input-group-btn">
        		<button class="btn btn-default" type="button" id="mchntChoose">选择</button>
      		</span>
		</div>
		<label class="col-sm-2 control-label" >商户名称:</label>
		<div class="col-sm-4">
			<input id="u-mchntCnName" type="text" name="mchntCnName" class="form-control" />
			<span id="u-mchntCnNameTip" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">营业执照号码:</label>
		<div class="col-sm-4">
			<input id="u-licNo" type="text" name="licNo" class="form-control"/>
			<span id="u-licNoTip" />
		</div>
		<label class="col-sm-2 control-label">商户代码:</label>
		<div class="col-sm-4">
			<input id="u-mchntCd" type="text" name="mchntCd" class="form-control"/>
			<span id="u-mchntCdTip" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">法人姓名:</label>
		<div class="col-sm-4">
			<input id="u-artifNm" type="text" name="artifNm" class="form-control"/>
			<span id="u-artifNmTip" />
		</div>
		<label class="col-sm-2 control-label">法人证件号:</label>
		<div class="col-sm-4">
			<input id="u-artifCertifId" type="text" name="artifCertifId" class="form-control"/>
			<span id="u-artifCertifIdTip" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">融资信贷状态:</label>
		<div class="col-sm-4">
			<input id="u-creditSt" name="loanStDesc" class="form-control" readonly="readonly"/>
			<span id="u-creditStTip" />
		</div>
		<label class="col-sm-2 control-label">商户状态:</label>
		<div class="col-sm-4">
			<select id="u-mchntSt" ctype="MCHNT_ST" name="mchntSt" class="form-control"></select>
			<span id="u-mchntStTip" />
		</div>
	</div>
	<h5>贷款信息</h5>
	<div class="form-group">
		<label class="col-sm-2 control-label">银联贷款编号:</label>
		<div class="col-sm-4">
			<input id="u-cupLoanId" type="text" name="cupLoanId" class="form-control" />
		</div>
		<label class="col-sm-2 control-label">资金方编码:</label>
		<div class="col-sm-4">
			<select id="u-lenderId" ctype="MCHNT_LENDER" name="lenderId" class="form-control"></select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">贷款金额:</label>
		<div class="col-sm-4 input-group">
			<input type="text" name="loanAt" class="form-control" xtype="money" />
			<span class="input-group-addon">元</span>
		</div>
		<label class="col-sm-2 control-label">贷款周期:</label>
		<div class="col-sm-4 input-group">
			<input type="text" name="loanCycle" class="form-control" />
			<span class="input-group-addon">月</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">贷款利率:</label>
		<div class="col-sm-4 input-group">
			<input type="text" name="loanrt" class="form-control" xtype="rate" />
			<span class="input-group-addon">%</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">放款成功时间:</label>
		<div class="col-sm-4">
			<input id="u-loanSuccTm" type="text" name="loanSuccTm" class="form-control" xtype="time" />
		</div>
		<label class="col-sm-2 control-label">贷款有效期:</label>
		<div class="col-sm-4">
			<input id="u-loanExpire" type="text" name="loanExpire" class="form-control" xtype="time" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">贷款余额:</label>
		<div class="col-sm-4 input-group">
			<input type="text" name="loanBalance" class="form-control" xtype="money" />
			<span class="input-group-addon">元</span>
		</div>
		<label class="col-sm-2 control-label">贷款产品:</label>
		<div class="col-sm-4">
			<select id="u-loanProd" ctype="MCHNT_LOAN_PROD" name="loanProd" class="form-control"></select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">服务费收益:</label>
		<div class="col-sm-4 input-group">
			<input type="text" name="svcFeeBenefit" class="form-control" xtype="money" />
			<span class="input-group-addon">元</span>
		</div>
		<label class="col-sm-2 control-label">分润方式:</label>
		<div class="col-sm-4">
			<input type="text" name="allotTp" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">逾期金额:</label>
		<div class="col-sm-4 input-group">
			<input type="text" name="exceedAt" class="form-control" xtype="money" />
			<span class="input-group-addon">元</span>
		</div>
		<label class="col-sm-2 control-label">逾期笔数:</label>
		<div class="col-sm-4">
			<input type="text" name="exceedNum" class="form-control" />
		</div>
	</div>
	<h5>银行卡信息</h5>
	<div class="form-group">
		<label class="col-sm-2 control-label">银行卡授信额度:</label>
		<div class="col-sm-4 input-group">
			<input type="text" name="cardCreditLimit" class="form-control" xtype="money" />
			<span class="input-group-addon">元</span>
		</div>
		<label class="col-sm-2 control-label">银行卡使用额度:</label>
		<div class="col-sm-4 input-group">
			<input type="text" name="cardUseLimit" class="form-control" xtype="money" />
			<span class="input-group-addon">元</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">银行卡有效期:</label>
		<div class="col-sm-4">
			<input type="text" name="cardExpire" class="form-control" placeholder="YYMM"/>
		</div>
	</div>
</form>
</div>
</div>

<!-- 商户选择弹出窗口 -->
<div id="mchntSelect" style="display: none;">
<form id="q-mchnt-form" name="q-mchnt-form" action="#" method="post">
<ul class="qry-ul">
	<li><label>银联商户号:</label><input type="text" name="mchntCd" comparison="like" /></li>
	<li><label>营业执照号:</label><input type="text" name="licNo" comparison="like" /></li>
	<li><label>商户名称:</label><input type="text" name="mchntCnName" comparison="like" /></li>
	<li><input type="button" class="bt qbutton" id="querysup" value="查询" onclick="$.queryTable({formId:'q-mchnt-form', tableId:'mchnt-table'});"/></li>
	<li><input type="reset" class="bt" value="重置"/></li>
</ul>
<div id="mchnt-table" class="is-grid"></div>
</form>
</div>

<script type="text/javascript">
<!--
// url
var url = {
grid: 'findLoan.action',
excel:'findLoanExcel.action',
add:'doInsertLoan.action',
upd:'doUpdateLoan.action',
send2bank:'doSend2Bank.action',
loanSucc:'doLoanSuccess.action',
loanFail:'doLoanFailed.action',
send:'doSendLoanInfo.action'
};
var isAdd = false ;
// fields definition
$(function() {
	 $('#updform').bootstrapValidator({
	        message: '格式有问题',
	        live: 'enabled',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	mchntCd: {
	                message: '请选择商户',
	                validators: {
	                    notEmpty: {  message: '请选择商户' }
	                }
	            },
	            licNo: { 
	            	message: '请填写营业执照号',
	            	validators: {
	            		notEmpty: {  message: '请填写营业执照号' },
	            		stringLength: { min:1, max:70}
	            	}
	            },
	            artifNm:{
	            	message:'请填写法人信息',
	            	validators: {
	            		notEmpty: {  message: '请填写法人信息' },
	            		stringLength: { min:1, max:40}
	            	}
	            },
	            artifCertifId:{
	            	message:'请填写法人信息',
	            	validators: {
	            		notEmpty: {  message: '请填写法人信息' },
	            		stringLength: { min:1, max:22}
	            	}
	            },
	            mchntSt:{
	            	message:'请选择状态',
	            	validators: {
	            		notEmpty: {  message: '请选择状态' }
	            	}
	            },
	            lenderId:{
	            	message:'请选择资金方',
	            	validators: {
	            		notEmpty: {  message: '请选择资金方' }
	            	}
	            },
	            loanAt:{
	            	message:'请填写金额',
	            	validators: {
	            		notEmpty: {  message: '请填写金额' }
	            	}
	            },
	            loanCycle:{
	            	message:'请填写周期',
	            	validators: {
	            		notEmpty: {  message: '请填写周期' }
	            	}
	            },
	            loanrt:{
	            	message:'请填写利率',
	            	validators: {
	            		notEmpty: {  message: '请填写利率' }
	            	}
	            },
	            loanBalance:{
	            	message:'请填写金额',
	            	validators: {
	            		notEmpty: {  message: '请填写金额' }
	            	}
	            },
	            loanProd:{
	            	message:'请选择产品',
	            	validators: {
	            		notEmpty: {  message: '请选择产品' }
	            	}
	            },
	            svcFeeBenefit:{
	            	message:'请填写金额',
	            	validators: {
	            		notEmpty: {  message: '请填写金额' }
	            	}
	            },
	            exceedAt:{
	            	message:'请填写金额',
	            	validators: {
	            		notEmpty: {  message: '请填写金额' }
	            	}
	            },
	            exceedNum:{
	            	message:'请填写数字',
	            	validators: {
	            		notEmpty: {  message: '请填写数字' }
	            	}
	            },
	            cardCreditLimit:{
	            	message:'请填写金额',
	            	validators: {
	            		notEmpty: {  message: '请填写金额' }
	            	}
	            },
	            cardUseLimit:{
	            	message:'请填写金额',
	            	validators: {
	            		notEmpty: {  message: '请填写金额' }
	            	}
	            },
	            cardExpire:{
	            	message:'请填过期年月YYYMM',
	            	validators: {
	            		notEmpty: {  message: '请填过期年月YYYMM' },
	            		stringLength: { min:4, max:4}
	            	}
	            }
	        }
	 });
	 
var addBtns = [
// <sec:authorize ifAllGranted ="ROLE_LOAN_LOAN_REQIN_ADD" >
 {text:'保存', click:function() {
  $('#updform').bootstrapValidator('validate');
  if ( $('#updform').data('bootstrapValidator').isValid() ) {
	  
		$.ajaxForm({formId: 'updform', url: isAdd?url.add:url.upd,
		success: function(data) {
			if (data.success) {
				$('#updateOper').dialog('close');
				if ( isAdd) {
					$('#table').flexAddData(data.root[0]);
				} else {
					$('#table').flexModifyData(data.root[0]); 
				}
				alert('操作成功'); 
			} else {alert('操作失败 原因是'+ data.syserr); }
		}});
	} else { return false; }  
 }}, // </sec:authorize>
{text:'关闭', click:function(){ $(this).dialog('close'); }}   
];
var updBtns = [
//<sec:authorize ifAllGranted ="ROLE_LOAN_LOAN_REQIN_UPD" >
{text:'保存', click:function() {
	  $('#updform').bootstrapValidator('validate');
	  if ( $('#updform').data('bootstrapValidator').isValid() ) {
		  
			$.ajaxForm({formId: 'updform', url: isAdd?url.add:url.upd,
			success: function(data) {
				if (data.success) {
					$('#updateOper').dialog('close');
					if ( isAdd) {
						$('#table').flexAddData(data.root[0]);
					} else {
						$('#table').flexModifyData(data.root[0]); 
					}
					alert('操作成功'); 
				} else {alert('操作失败 原因是'+ data.syserr); }
			}});
		} else { return false; }  
}}, // </sec:authorize>
{text:'关闭', click:function(){ $(this).dialog('close'); }}
];
	$('#updateOper').dialog({ title:'贷款信息',autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 850, height : 570 });
	$('#updateOper').fullScreen();
	
	var fillMchntInfo = function(data ) {
		if ( data && data.length > 0 ) {
			var rec = data[0];
			if ( rec ) {
				$('#u-mchntCode').val(rec.mchntCode); 
				$('#u-mchntCnName').val(rec.mchntCnName);
				$('#u-licNo').val(rec.licNo).change();
				$('#u-mchntCd').val(rec.mchntCd).change();
				$('#u-artifNm').val(rec.artifNm);
				$('#u-artifCertifId').val(rec.artifCertifId);
				$('#u-mchntSt').val(rec.mchntSt);
			}
		} else if(typeof (data)=="object"){
			var rec = data;
			if ( rec ) {
				$('#u-mchntCode').val(rec.mchntCode); 
				$('#u-mchntCnName').val(rec.mchntCnName);
				$('#u-licNo').val(rec.licNo).change();
				$('#u-mchntCd').val(rec.mchntCd).change();
				$('#u-artifNm').val(rec.artifNm);
				$('#u-artifCertifId').val(rec.artifCertifId);
				$('#u-mchntSt').val(rec.mchntSt);
				$('#mchntSelect').dialog('close');
			}
		}
		var $valid = $('#updform').data('bootstrapValidator');
		$valid.revalidateField('licNo');
		$valid.revalidateField('mchntCd');
		$valid.revalidateField('artifNm');
		$valid.revalidateField('artifCertifId');
		$valid.revalidateField('mchntSt');
	} ;
	$('#mchntSelect').qDialog({
		width : 700, height : 500, draggable:true, title: '选择商户', 
		flexigrid : {url : 'findMchnt.action', height:180, sort:"mchntCd", usepager : true
			,dbClickRecord: fillMchntInfo
			,colModel : [ 
			  {display: '银联商户号',width: 150, dataIndex: 'mchntCd',		align: 'center',sortable:true}
			 ,{display: '营业执照号',width: 150, dataIndex: 'licNo',		align: 'center',sortable:true}
			 ,{display: '商户名称',	 width: 150, dataIndex: 'mchntCnName',	align: 'center',sortable:true}
			 ,{display: '商户状态',	 width: 100, dataIndex: 'mchntStDesc',	align: 'center',sortable:true} ]
		}, success:function(data){ 
			if (data) { fillMchntInfo(data); return true;} 
			else {alert("请选择商户记录");return false;}
		}
	});
	var chooseMcnht = function() { $('#mchntSelect').dialog('open'); };
	$('#mchntChoose').click(chooseMcnht);
	//$('#updform').dialog({title:'贷款信息', autoOpen: false, bgiframe: true, modal: true, resizable: false, draggable: true, width: 650, height: 350 });
	
	var zeroArr = ['','0','00','000','0000','00000','000000'];
	
	
	var view = function(rec, id ) {
		isAdd = false;
		if (!rec) { alert("请选择一条记录"); return; }
		var cupLoanId = rec.cupLoanId;
		if ( cupLoanId ) {
			
		} else {
			var idx = 6-rec.loanId.length;
			cupLoanId = 'S0000021'+ zeroArr[idx] + rec.loanId + rec.recCrtTime;
		}
		$('#updateOper').dialog('option',{title:'贷款申请信息',buttons:updBtns});
		$('#updateOper').dialog('open');
		$('#updform').data('bootstrapValidator').resetForm(true);
		//$('#updform')[0].reset();
		$.jyform.fillForm('updform',rec);
		$('#u-cupLoanId').val(cupLoanId);
	};
	
	var addloan = function() {
		isAdd = true;
		$('#updform').data('bootstrapValidator').resetForm(true);
		$('#updateOper').dialog('option',{title:'新增贷款申请',buttons:updBtns});
		$('#updateOper').dialog('open');
		/* $.ajax({url:url.add, dataType:'json',type:'POST',async:false, modal:true
			,data:{jsonObject:$.toJSON({loanSt:'00',creditSt:'00'})}
			,success: function(data) {
				if ( data.success ) {
					alert('操作成功'); 
					if ( data.root && data.root[0] ) {
						$('#table').flexAddData(data.root[0]);
						$('#table #row0').dblclick();
					}
				} else {
					alert('操作失败: '+ data.syserr);
				}
			}
		}); */
	};
	
	var sendloan = function(rec, id) {
		if (!rec) { alert("请选择一条记录"); return; }
		$.ajax({url: url.send, dataType:'json', modal:true, type:'POST',
			data:{jsonObject:$.toJSON({loanId:rec.loanId, version:rec.version})}, 
			success : function(data) { 
				if (data.success) { 
					alert('操作成功');  
				} else { 
					alert('操作失败 原因是 '+ data.syserr); 
				}
			}
		});
	};
	var send2bank = function(rec, id) {
		if (!rec) { alert("请选择一条记录"); return; }
		$.ajax({url: url.send2bank, dataType:'json', modal:true, type:'POST',
			data:{jsonObject:$.toJSON({loanId:rec.loanId, version:rec.version})}, 
			success : function(data) { 
				if (data.success) { 
					alert('操作成功');  
					$('#table').flexModifyData(data.root[0]);
				} else { 
					alert('操作失败 原因是 '+ data.syserr); 
				}
			}
		});
	};
	var loanSucc = function(rec, id) {
		if (!rec) { alert("请选择一条记录"); return; }
		$.ajax({url: url.loanSucc, dataType:'json', modal:true, type:'POST',
			data:{jsonObject:$.toJSON({loanId:rec.loanId, version:rec.version})}, 
			success : function(data) { 
				if (data.success) { 
					alert('操作成功');  
					$('#table').flexModifyData(data.root[0]);
				} else { 
					alert('操作失败 原因是 '+ data.syserr); 
				}
			}
		});
	};
	var loanFail = function(rec, id) {
		if (!rec) { alert("请选择一条记录"); return; }
		$.ajax({url: url.loanFail, dataType:'json', modal:true, type:'POST',
			data:{jsonObject:$.toJSON({loanId:rec.loanId, version:rec.version})}, 
			success : function(data) { 
				if (data.success) { 
					alert('操作成功');  
					$('#table').flexModifyData(data.root[0]);
				} else { 
					alert('操作失败 原因是 '+ data.syserr); 
				}
			}
		});
	};
	
	// datagrid
	$('#table').flexigrid({
		url : 'findLoan.action', sort : "loanId", usepager : true, dbClickRecord: view,
		buttons : [
				 { name: "详细信息",bclass: 'grid_view',id: 'cel',onpress: view, aclass:'ui-button-primary'}
				,{ name: "新增贷款信息",show:'ROLE_LOAN_LOAN_REQIN_ADD',bclass: 'grid_add',id: 'add',onpress: addloan, aclass:'ui-button-success'}
				,{ name: "提交银行",show:'ROLE_LOAN_LOAN_REQ_CHECK',bclass: 'grid_view',id: 's2b', onpress: send2bank, aclass:'ui-button-success'}
				,{ name: "贷款成功",show:'ROLE_LOAN_LOAN_SUCC',bclass: 'grid_view',id: 'succ', onpress: loanSucc, aclass:'ui-button-success'}
				,{ name: "贷款失败",show:'ROLE_LOAN_LOAN_FAIL',bclass: 'grid_view',id: 'fail', onpress: loanFail, aclass:'ui-button-success'}
		   		,{ name: "上送贷款信息到银联",show:'ROLE_LOAN_LOAN_TOCUP',bclass: 'grid_view',id: 'send', onpress: sendloan, aclass:'ui-button-success'}
		   		,{ name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		   		],
		colModel : [
		             { display: '贷款编号', dataIndex: 'loanId', width:100, align: 'center',sortable:true }
		            ,{ display: '银联贷款编号', dataIndex: 'cupLoanId', width:120, align: 'center',sortable:true }
					,{ display: '银联商户号', dataIndex: 'mchntCd', width:120, align: 'center',sortable:true }
					,{ display: '营业执照号码', dataIndex: 'licNo', width:120, align: 'center',sortable:true }
					,{ display: '法人证件号', dataIndex: 'artifCertifId', width:120, align: 'center',sortable:true }
					,{ display: '法人代表姓名', dataIndex: 'artifNm', width:100, align: 'center',sortable:true }
		            ,{ display: '贷款金额', dataIndex: 'loanAt', width:100, align: 'center',sortable:true,render:fmtMoney }
			        ,{ display: '贷款周期', dataIndex: 'loanCycle', width:100, align: 'center',sortable:true }
			        ,{ display: '贷款利率', dataIndex: 'loanrt', width:120, align: 'center',sortable:true,render:fmtRate }
			        ,{ display: '放款成功时间', dataIndex: 'loanSuccTm', width:120, align: 'center',sortable:true, render: fmtTime }			        
			        ,{ display: '贷款余额', dataIndex: 'loanBalance', width:120, align: 'center',sortable:true,render:fmtMoney }
			        ,{ display: '贷款状态', dataIndex: 'loanStDesc', width:120, align: 'center',sortable:true }
		]
	});
	$.jyform.dateTimeBox({select:$('#u-loanSuccTm')}); 
	$.jyform.dateBox({select:$('#u-loanExpire')});
	$.jyform.sysCodeBoxes();
	// buttons
	$('#queryBtn').click(function(){$.queryTable({formId : 'queryForm', tableId : 'table'});});
	$('.qry-ul input[type="button"]').addClass('ui-button-primary');
	$('.bt').addClass('ui-btn-sm').button();
	$('#queryForm .qry-ul input[type="button"]').click();
	// <sec:authorize ifNotGranted ="ROLE_LOAN_LOAN_REQIN_ADD,ROLE_LOAN_LOAN_REQIN_UPD" >
	$('#updform input').attr('readonly','readonly');
	// </sec:authorize>
	
});
//-->
</script>
</body>
</html>