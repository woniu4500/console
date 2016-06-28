<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
<link rel="stylesheet" href="r/plugins/jquery/bs/third-party/validator/v0.6.0/formValidation.min.css"/>
<link type="text/css" rel="stylesheet" href="r/plugins/jquery/dataTimePicker/css/bootstrap-datetimepicker.min.css" />

<script type="text/javascript" src="r/plugins/jquery/bs/third-party/validator/v0.6.0/formValidation.min.js"></script>
<script type="text/javascript" src="r/plugins/jquery/bs/third-party/validator/v0.6.0/bootstrap.min.js"></script>
<script type="text/javascript" src="r/plugins/jquery/bs/third-party/validator/v0.6.0/zh_CN.js"></script>

<script type="text/javascript" src="r/plugins/jquery/dataTimePicker/js/bootstrap-datetimepicker.min.js"></script> 
<script type="text/javascript" src="r/plugins/jquery/dataTimePicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<!-- 附件列表 -->
<link type="text/css" rel="stylesheet" href="r/template/attachPanel.css" />
<script src="r/template/attachPanel.js" type="text/javascript"></script>
<script src="r/template/fieldBuilder.js" type="text/javascript"></script>

<style>
.frm-ul li label{min-width:120px}
</style>
</head>
<body>
<!-- 查询条件 -->
<div id="queryCondition">
	<form id="queryForm" name="queryForm" action="#" method="post">
	<ul class="qry-ul">
		<li><label for="q_artifNm">姓名:</label><input type="text" id="q_artifNm" comparison="like" name="artifNm" ></input></li>
		<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
		<li><input class='bt' value="重置" type="reset" /></li>
	</ul>
	</form>
</div>
<!-- 列表 -->
<div id="table"></div>

<!-- 新增贷款信息 -->
<div id="addOper" style="display: none;">
<form id="addform" class="form-horizontal" role="form">
<div class="panel panel-default">
<div class="panel-heading">商户信息</div>
<div class="panel-body">
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">内部商户号:</label>
			<div class="col-sm-8">
				<div class="input-group">
					<input id="a-mchntCode" type="text" name="mchntCode" class="form-control" readonly 
						data-fv-notempty data-fv-notempty-message="请选择商户" />
					<span class="input-group-btn">
		        		<button class="btn btn-default" type="button" id="a-mchntChoose">选择</button>
		      		</span>
	      		</div>
			</div>
		</div>
		<div class="form-group col-sm-6">	
			<label class="col-sm-4 control-label" >商户名称:</label>
			<div class="col-sm-8">
				<input id="a-mchntCnName" type="text" name="mchntCnName" class="form-control" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">姓名:</label>
			<div class="col-sm-8">
				<input id="a-artifNm" type="text" name="artifNm" class="form-control"/>
			</div>
		</div>
		<div class="form-group col-sm-6">	
			<label class="col-sm-4 control-label">证件号:</label>
			<div class="col-sm-8">
				<input id="a-artifCertifId" type="text" name="artifCertifId" class="form-control"/>
			</div>
		</div>
	</div>
</div>
</div><!-- End Of 商户信息 -->
<div class="panel panel-default">
<div class="panel-heading">贷款信息</div>
<div class="panel-body">
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">贷款金额:</label>
			<div class="col-sm-8">
				<div class="input-group">
					<input type="text" name="reqLoanAt" class="form-control" xtype="money"
						data-fv-notempty 
						data-fv-between data-fv-between-min=0 data-fv-between-max=9999999999 
						/>
					<span class="input-group-addon">元</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">贷款周期:</label>
			<div class="col-sm-8">
				<div class="input-group">
					<input type="text" name="loanCycle" class="form-control" 
						data-fv-notempty 
						data-fv-between data-fv-between-min=0 data-fv-between-max=99999
						/>
					<span class="input-group-addon">月</span>
				</div>
			</div>
		</div>
	</div>
</div>
</div><!-- End Of 贷款信息 -->
<div class="panel panel-default">
<div class="panel-heading">银行卡信息</div>
<div class="panel-body">
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">银行卡号:</label>
			<div class="col-sm-8">
				<input type="text" name="cardNo" class="form-control required" 
					data-fv-notempty 
					data-fv-stringlength data-fv-stringlength-max="30" data-fv-stringlength-message="卡号长度不正确"/>
			</div>
		</div>
		<div class="form-group col-sm-6">	
			<label class="col-sm-4 control-label">银行卡有效期:</label>
			<div class="col-sm-8">
				<input type="text" name="cardExpire" class="form-control" placeholder="YYMM" maxlength="4"
					data-fv-notempty 
					data-fv-regexp data-fv-regexp-regexp="[0-9]{4}" data-fv-regexp-message="有效期格式不正确" 
					/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">银行卡授信额度:</label>
			<div class="col-sm-8">
				<div class="input-group">
					<input type="text" name="cardCreditLimit" class="form-control" xtype="money"
						data-fv-between data-fv-between-min=0 data-fv-between-max=9999999999
					 />
					<span class="input-group-addon">元</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">	
			<label class="col-sm-4 control-label">银行卡使用额度:</label>
			<div class="col-sm-8 input-group">
				<div class="input-group">
					<input type="text" name="cardUseLimit" class="form-control" xtype="money"
						data-fv-between data-fv-between-min=0 data-fv-between-max=9999999999 
					/>
					<span class="input-group-addon">元</span>
				</div>
			</div>
		</div>
	</div>
</div>
</div><!-- End Of 贷款信息 -->
</form>
</div>


<!-- 贷款申请修改 -->
<div id="updateOper" style="display: none; min-width: 1050px;">
<ul>
	<li><a href="#loanOper" id="loan_tab">贷款信息</a></li>
</ul>
<div id="loanOper">
<form id="updform"  class="form-horizontal" role="form" >
<input type="hidden" name="version" />
<input type="hidden" name="loanId" />
<div class="panel panel-default">
<div class="panel-heading">商户信息</div>
<div class="panel-body">
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">内部商户号:</label>
			<div class="col-sm-8">
				<div class="input-group">
					<input id="u-mchntCode" type="text" name="mchntCode" class="form-control" readonly 
						data-fv-notempty data-fv-notempty-message="请选择商户" />
					<span class="input-group-btn">
		        		<button class="btn btn-default" type="button" id="u-mchntChoose">选择</button>
		      		</span>
	      		</div>
			</div>
		</div>
		<div class="form-group col-sm-6">	
			<label class="col-sm-4 control-label" >商户名称:</label>
			<div class="col-sm-8">
				<input id="u-mchntCnName" type="text" name="mchntCnName" class="form-control" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">姓名:</label>
			<div class="col-sm-8">
				<input id="u-artifNm" type="text" name="artifNm" class="form-control"/>
			</div>
		</div>
		<div class="form-group col-sm-6">	
			<label class="col-sm-4 control-label">证件号:</label>
			<div class="col-sm-8">
				<input id="u-artifCertifId" type="text" name="artifCertifId" class="form-control"/>
			</div>
		</div>
	</div>
</div>
</div><!-- End Of 商户信息 -->
<div class="panel panel-default">
<div class="panel-heading">贷款信息</div>
<div class="panel-body">
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">贷款金额:</label>
			<div class="col-sm-8">
				<div class="input-group">
					<input type="text" name="reqLoanAt" class="form-control" xtype="money"
						data-fv-notempty 
						data-fv-between data-fv-between-min=0 data-fv-between-max=9999999999 
						/>
					<span class="input-group-addon">元</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">贷款周期:</label>
			<div class="col-sm-8">
				<div class="input-group">
					<input type="text" name="loanCycle" class="form-control" 
						data-fv-notempty 
						data-fv-between data-fv-between-min=0 data-fv-between-max=99999
						/>
					<span class="input-group-addon">月</span>
				</div>
			</div>
		</div>
	</div>
</div>
</div><!-- End Of 贷款信息 -->
<div class="panel panel-default">
<div class="panel-heading">银行卡信息</div>
<div class="panel-body">
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">银行卡号:</label>
			<div class="col-sm-8">
				<input type="text" name="cardNo" class="form-control required" 
					data-fv-notempty 
					data-fv-stringlength data-fv-stringlength-max="30" data-fv-stringlength-message="卡号长度不正确"/>
			</div>
		</div>
		<div class="form-group col-sm-6">	
			<label class="col-sm-4 control-label">银行卡有效期:</label>
			<div class="col-sm-8">
				<input type="text" name="cardExpire" class="form-control" placeholder="YYMM" maxlength="4"
					data-fv-notempty 
					data-fv-regexp data-fv-regexp-regexp="[0-9]{4}" data-fv-regexp-message="有效期格式不正确" 
					/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-4 control-label">银行卡授信额度:</label>
			<div class="col-sm-8">
				<div class="input-group">
					<input type="text" name="cardCreditLimit" class="form-control" xtype="money"
						data-fv-between data-fv-between-min=0 data-fv-between-max=9999999999
					 />
					<span class="input-group-addon">元</span>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-6">	
			<label class="col-sm-4 control-label">银行卡使用额度:</label>
			<div class="col-sm-8 input-group">
				<div class="input-group">
					<input type="text" name="cardUseLimit" class="form-control" xtype="money"
						data-fv-between data-fv-between-min=0 data-fv-between-max=9999999999 
					/>
					<span class="input-group-addon">元</span>
				</div>
			</div>
		</div>
	</div>
</div>
</div><!-- End Of 贷款信息 -->
</form>		
</div>
	
</div>

<!-- 商户认证后修改内容 -->
<div id="updateOper2" style="display: none;">
<ul>
	<li><a href="#loanOper2" id="loan2_tab">贷款信息</a></li>
</ul>
<div id="loanOper2">
<form id="updform2"  class="form-horizontal" role="form" >
<input type="hidden" name="version" />
<input type="hidden" name="loanId" />
<div class="panel panel-default">
	<div class="panel-heading">商户信息</div>
	<div class="panel-body" id="updform2-mchnt"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">贷款信息</div>
	<div class="panel-body" id="updform2-loan"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">银行卡信息</div>
	<div class="panel-body" id="updform2-card"></div>
</div>
</form>
</div>		
</div>

<!-- 提交银联资质审核 -->
<div id="updateOper3" style="display: none;">
<ul>
	<li><a href="#loanOper3" id="loan3_tab">贷款信息</a></li>
	<li><a href="#mchntOper3" id="mchnt3_tab">商户信息</a></li>
</ul>
<div id="loanOper3">
<form id="updform3"  class="form-horizontal" role="form" >
<input type="hidden" name="version" />
<input type="hidden" name="loanId" />
<div class="panel panel-default">
	<div class="panel-heading">商户信息</div>
	<div class="panel-body" id="updform3-mchnt"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">贷款信息</div>
	<div class="panel-body" id="updform3-loan"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">银联信息</div>
	<div class="panel-body" id="updform3-cups"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">银行卡信息</div>
	<div class="panel-body" id="updform3-card"></div>
</div>
</form>	
</div>
<div id="mchntOper3">
<form action="#" id="mchntInfoForm3" class="form-horizontal" role="form">
<div class="panel panel-default">
	<div class="panel-heading">商户信息</div>
	<div class="panel-body" id="upd-mchntinfo3"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">联系人信息</div>
	<div class="panel-body" id="upd-contact3"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">操作信息</div>
	<div class="panel-body" id="upd-operate3"></div>	
</div>
</form>
</div>

</div>

<!-- 查看界面 -->
<div id="updateOper4" style="display: none;">
<ul>
	<li><a href="#loanOper4" id="loan4_tab">贷款信息</a></li>
</ul>
<div id="loanOper4">
<form id="updform4"  class="form-horizontal" role="form" >
<input type="hidden" name="version" />
<input type="hidden" name="loanId" />
<div class="panel panel-default">
	<div class="panel-heading">商户信息</div>
	<div class="panel-body" id="updform4-mchnt"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">贷款信息</div>
	<div class="panel-body" id="updform4-loan"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">银行卡信息</div>
	<div class="panel-body" id="updform4-card"></div>
</div>
</form>	
</div>

</div>


<!-- 商户选择弹出窗口
<div id="mchntSelect" style="display: none;">
<form id="q-mchnt-form" name="q-mchnt-form" action="#" method="post">
<ul class="qry-ul">
	<li><label>银联商户号:</label><input type="text" name="mchntCd" comparison="like" /></li>
	<li><label>营业执照号:</label><input type="text" name="licNo" comparison="like" /></li>
	<li><label>商户名称:</label><input type="text" name="mchntCnName" comparison="like" /></li>
	<li><label>商户状态:</label><select id="mq-mchntSt" ctype="MCHNT_ST_QRY" comparison="eq" ftype="list" name="mchntSt" multiple="multiple" def="02,03,04,05" ></select></li>
	<li><input type="button" class="bt qbutton" id="querysup" value="查询" onclick="$.queryTable({formId:'q-mchnt-form', tableId:'mchnt-table'});"/></li>
	<li><input type="reset" class="bt" value="重置"/></li>
</ul>
<div id="mchnt-table" class="is-grid"></div>
</form>
</div>
 -->

<script type="text/javascript">
<!--
// url
var url = {
grid: 'findLoan.action',
excel:'findLoanExcel.action',
single:'findMchntbyMchntCode.action',
add:'doInsertLoan.action',
upd:'doUpdateLoan.action',
mtan:"doMaintainLoan.action",
submit:"doSubmitLoan.action",
certify:"doCertifyLoan.action",
deny:"doDenyLoan.action",
send2bank:'doSend2Bank.action',
loanSucc:'doLoanSuccess.action',
loanFail:'doLoanFailed.action',
send:'doSendLoanInfo.action',
audit: 'doAuditLoan.action'
};

var fb = new FieldBuilder([
 {name:'mchntCode', desc:'内部商户号'  , jtype:'Long', length: 10}
,{name:'version', desc:'VERSION'  , jtype:'Long', length: 10}
,{name:'licNo', desc:'营业执照号码'  , jtype:'String', length: 70}
,{name:'customerCode', desc:'会员代码'  , jtype:'Long', length: 10}
,{name:'customerMobile', desc:'会员手机号'  , jtype:'String', length: 20}
,{name:'customerCodeDesc', desc:'会员姓名'  , jtype:'String', length: 100}
,{name:'mchntCd', desc:'商户代码'  , jtype:'String', length: 15}
,{name:'acqInsIdCd', desc:'收单机构代码'  , jtype:'String', length: 20}
,{name:'reveRegCd', desc:'税务登记代码'  , jtype:'String', length: 70}
,{name:'mchntCnName', desc:'工商注册名称'  , jtype:'String', length: 100}
,{name:'regAddr', desc:'注册地址'  , jtype:'String', length: 90}
,{name:'artifNm', desc:'法人代表姓名'  , jtype:'String', length: 40}
,{name:'artifCertifId', desc:'法人代表证件号'  , jtype:'String', length: 22}
,{name:'artifCertifType', desc:'法人代表证件类型'  , jtype:'String', length: 2}
,{name:'mchntTp', desc:'商户类型'  , jtype:'String', length: 4}
,{name:'mchntTpDesc', desc:'商户类型'  , jtype:'String', length: 30}
,{name:'mchntGrp', desc:'商户组别'  , jtype:'String', length: 4}
,{name:'mchntGrpDesc', desc:'商户组别'  , jtype:'String', length: 30}
,{name:'phone', desc:'联系电话'  , jtype:'String', length: 40}
,{name:'mchntCrtDt', desc:'商户入网时间'  , jtype:'String', length: 8}
,{name:'posNum', desc:'装机数量'  , jtype:'Long', length: 10}
,{name:'remark', desc:'备注'  , jtype:'String', length: 200}
,{name:'mchntSt', desc:'商户状态'  , jtype:'String', length: 2}
,{name:'mchntStDesc', desc:'商户状态'  , jtype:'String', length: 30}
,{name:'dispMchntSt', desc:'客户端显示状态'  , jtype:'String', length: 2}
,{name:'dispMchntStDesc', desc:'客户端显示状态'  , jtype:'String', length: 30}
,{name:'recAcc', desc:'记录创建账户'  , jtype:'String', length: 30}
,{name:'recAccDesc', desc:'记录创建账户'  , jtype:'String', length: 200}
,{name:'recTime', desc:'记录创建时间' , jtype:'String', length: 14}
,{name:'lastCupOpr', desc:'最近接口操作'  , jtype:'String', length: 40}
,{name:'lastCupOprDesc', desc:'最近接口操作'  , jtype:'String', length: 100}
,{name:'lastCupTime', desc:'最近接口调用时间' , dateFormat:'YmdHis',xtype: 'datetime' , jtype:'String', length: 14}
,{name:'lastCupResp', desc:'最近接口返回信息'  , jtype:'String', length: 200}
,{name:'auditTime', desc:'最近资质申请成功时间' , dateFormat:'YmdHis',xtype: 'datetime' , jtype:'String', length: 14}
,{name:'beforeTime', desc:'最近贷前查询成功时间' , dateFormat:'YmdHis',xtype: 'datetime' , jtype:'String', length: 14}
,{name:'cupMchntInfo', desc:'银联商户数据内容'  , jtype:'String', length: 1500}

,{name:'mchntEnType', desc:'商户性质'  , jtype:'String', length: 1}
,{name:'mchntEnTypeDesc', desc:'商户性质'  , jtype:'String', length: 30}
,{name:'mchntDispName', desc:'商户名称'  , jtype:'String', length: 200}
,{name:'mchntContact', desc:'商户联系人'  , jtype:'String', length: 20}
,{name:'mchntContactMobile', desc:'联系人手机'  , jtype:'String', length: 40}
,{name:'mchntArtifMobile', desc:'法人代表手机'  , jtype:'String', length: 40}
,{name:'telephone', desc:'固定电话'  , jtype:'String', length: 40}
,{name:'mchntStartDate', desc:'经营起始日期'  , dateFormat:'Ymd',xtype: 'date', jtype:'String', length: 8}
,{name:'regCapital', desc:'注册资本', jtype:'Long', length: 12}
,{name:'orgCode', desc:'组织结构代码', jtype:'String', length: 20}
,{name:'othBankName', desc:'他行储蓄账户开户行', jtype:'String', length: 80}
,{name:'othCardNo', desc:'他行储蓄账户卡号', jtype:'String', length: 30}

,{name:'loanId', desc:'贷款编号'  , jtype:'String', length: 32}
,{name:'cupLoanId', desc:'银联贷款编号'  , jtype:'String', length: 40}
,{name:'licNo', desc:'营业执照号码'  , jtype:'String', length: 70}
,{name:'artifCertifId', desc:'法人代表证件号'  , jtype:'String', length: 22}
,{name:'artifNm', desc:'法人代表姓名'  , jtype:'String', length: 40}
,{name:'creditSt', desc:'贷款状态'  , jtype:'String', length: 2}
,{name:'creditStDesc', desc:'贷款状态'  , jtype:'String', length: 30}
,{name:'mchntSt', desc:'商户状态'  , jtype:'String', length: 2}
,{name:'mchntStDesc', desc:'商户状态'  , jtype:'String', length: 30}
,{name:'cancelFlag', desc:'用户撤销标识'  , jtype:'String', length: 1}
,{name:'cancelFlagDesc', desc:'用户撤销标识'  , jtype:'String', length: 30}
,{name:'lenderId', desc:'资金方编码'  , jtype:'String', length: 40}
,{name:'reqLoanAt', desc:'申请贷款金额'  , jtype:'Long', length: 12, xtype:'money', unit:'元'}
,{name:'loanAt', desc:'贷款/放贷金额'  , jtype:'Long', length: 12, xtype:'money', unit:'元'}
,{name:'loanCycle', desc:'贷款周期'  , jtype:'Long', length: 10, unit:'月'}
,{name:'loanRt', desc:'贷款利率'  , jtype:'Long', length: 10, xtype:'rate', unit:'%'}
,{name:'cardCreditLimit', desc:'银行卡授信额度'  , jtype:'Long', length: 12, xtype:'money', unit:'元'}
,{name:'cardUseLimit', desc:'银行卡使用额度'  , jtype:'Long', length: 12, xtype:'money', unit:'元'}
,{name:'cardExpire', desc:'银行卡有效期'  , jtype:'String', length: 8}
,{name:'cardNo', desc:'银行卡号'  , jtype:'String', length: 30}
,{name:'loanSuccTm', desc:'放款成功时间'  , jtype:'String', length: 14, xtype:'datetime'}
,{name:'loanBalance', desc:'贷款余额'  , jtype:'Long', length: 12, xtype:'money', unit:'元'}
,{name:'loanProd', desc:'贷款产品'  , jtype:'String', length: 8}
,{name:'svcFeeBenefit', desc:'服务费收益'  , jtype:'Long', length: 12, xtype:'money', unit:'元'}
,{name:'allotTp', desc:'分润方式'  , jtype:'String', length: 20}
,{name:'exceedAt', desc:'逾期金额'  , jtype:'Long', length: 12, xtype:'money', unit:'元'}
,{name:'exceedNum', desc:'逾期笔数'  , jtype:'Long', length: 11}
,{name:'loanExpire', desc:'贷款有效期'  , jtype:'String', xtype:'date', length: 8}
,{name:'remark', desc:'备注'  , jtype:'String', length: 200}
,{name:'loanReqTime', desc:'贷款申请时间' , dateFormat:'YmdHis', xtype:'datetime', jtype:'String', length: 14}
,{name:'loanProcResp', desc:'贷款处理意见'  , jtype:'String', length: 200}
,{name:'loanProcAcc', desc:'贷款处理账户'  , jtype:'String', length: 30}
,{name:'loanProcTime', desc:'贷款处理时间' , dateFormat:'YmdHis',xtype: 'datetime' , jtype:'String', length: 14}

]);

fb.buildFormFields('updform2-mchnt',[[{name:'mchntCode',readonly:'true'},'artifNm'],[{name:'creditStDesc',readonly:'true'},{name:'cancelFlagDesc',readonly:'true'}]],{});
fb.buildFormFields('updform2-loan',[['reqLoanAt','loanCycle'],['loanAt','loanRt'],['loanBalance','loanSuccTm'],['exceedAt','exceedNum'],['loanExpire',{name:'loanReqTime',readonly:'true'}],[{name:'loanProcAcc',readonly:'true'},{name:'loanProcResp',readonly:'true'}]],{});
fb.buildFormFields('updform2-card',[['cardNo','cardExpire'],['cardCreditLimit','cardUseLimit']],{});

fb.buildFormFields('updform3-mchnt',[[{name:'mchntCode',readonly:'true'},'artifNm'],[{name:'creditStDesc',readonly:'true'},{name:'cancelFlagDesc',readonly:'true'}]],{});
fb.buildFormFields('updform3-loan',[['reqLoanAt','loanCycle'],['loanAt','loanRt'],['loanBalance','loanSuccTm'],['exceedAt','exceedNum'],['loanExpire',{name:'loanReqTime',readonly:'true'}],[{name:'loanProcAcc',readonly:'true'},{name:'loanProcResp',readonly:'true'}]],{});
fb.buildFormFields('updform3-cups',[[{name:'cupLoanId',id:'u-cupLoanId'},{name:'mchntSt',ctype:'MCHNT_CUPST'}],[{name:'loanProd',ctype:'MCHNT_LOAN_PROD'},{name:'lenderId',ctype:'MCHNT_LENDER'}],['svcFeeBenefit','allotTp']],{});
fb.buildFormFields('updform3-card',[['cardNo','cardExpire'],['cardCreditLimit','cardUseLimit']],{});

fb.buildFormFields('updform4-mchnt',[[{name:'mchntCode',readonly:'true'},'artifNm'],[{name:'creditStDesc',readonly:'true'},{name:'cancelFlagDesc',readonly:'true'}]],{readonly:'true'});
fb.buildFormFields('updform4-loan',[['reqLoanAt','loanCycle'],['loanAt','loanRt'],['loanBalance','loanSuccTm'],['exceedAt','exceedNum'],['loanExpire',{name:'loanReqTime',readonly:'true'}],[{name:'loanProcAcc',readonly:'true'},{name:'loanProcResp',readonly:'true'}]],{readonly:'true'});
fb.buildFormFields('updform4-cups',[[{name:'cupLoanId'},{name:'mchntSt',ctype:'MCHNT_CUPST'}],[{name:'loanProd',ctype:'MCHNT_LOAN_PROD'},{name:'lenderId',ctype:'MCHNT_LENDER'}],['svcFeeBenefit','allotTp']],{readonly:'true'});
fb.buildFormFields('updform4-card',[['cardNo','cardExpire'],['cardCreditLimit','cardUseLimit']],{readonly:'true'});


fb.buildFormFields('upd-mchntinfo2',
		['mchntCnName','mchntDispName',{name:'regAddr', xtype:'textarea'}
		,'phone',['mchntTpDesc','mchntGrpDesc'],['mchntEnTypeDesc','mchntStartDate']
		,[{name:'regCapital',xtype:'money',unit:'元'},'orgCode'],['licNo','mchntCd'],['artifNm','artifCertifId']
		,['posNum','mchntCrtDt'],['acqInsIdCd','reveRegCd'],{name:'remark',xtype:'textarea'}],{readonly:'true'});
fb.buildFormFields('upd-contact2', [['mchntContact','mchntContactMobile'],['mchntArtifMobile','telephone']], {readonly:'true'});
fb.buildFormFields('upd-operate2', [['mchntStDesc','dispMchntStDesc'],['lastCupOprDesc','lastCupResp'],[{name:'mchntCode',id:'mchntCode'},'recAccDesc'],['customerCodeDesc','customerMobile']], {readonly:'true'});

fb.buildFormFields('upd-mchntinfo3',
		['mchntCnName','mchntDispName',{name:'regAddr', xtype:'textarea'}
		,'phone',[{name:'mchntTp',ctype:'MCHNT_TP'},{name:'mchntGrp',ctype:'MCHNT_GRP'}],[{name:'mchntEnType',ctype:'MCHNT_ETP'},'mchntStartDate']
		,[{name:'regCapital',xtype:'money',unit:'元'},'orgCode'],['licNo','mchntCd'],['artifNm','artifCertifId']
		,['posNum','mchntCrtDt'],['acqInsIdCd','reveRegCd'],{name:'remark',xtype:'textarea'}],{});
fb.buildFormFields('upd-contact3', [['mchntContact','mchntContactMobile'],['mchntArtifMobile','telephone']], {});
fb.buildFormFields('upd-operate3', [['mchntStDesc','dispMchntStDesc'],['lastCupOprDesc','lastCupResp'],['mchntCode','recAccDesc'],['customerCodeDesc','customerMobile']], {readonly:'true'});

fb.buildFormFields('upd-mchntinfo4',
		['mchntCnName','mchntDispName',{name:'regAddr', xtype:'textarea'}
		,'phone',[{name:'mchntTp',ctype:'MCHNT_TP'},{name:'mchntGrp',ctype:'MCHNT_GRP'}],[{name:'mchntEnType',ctype:'MCHNT_ETP'},'mchntStartDate']
		,[{name:'regCapital',xtype:'money',unit:'元'},'orgCode'],['licNo','mchntCd'],['artifNm','artifCertifId']
		,['posNum','mchntCrtDt'],['acqInsIdCd','reveRegCd'],{name:'remark',xtype:'textarea'}],{});
fb.buildFormFields('upd-contact4', [['mchntContact','mchntContactMobile'],['mchntArtifMobile','telephone']], {});
fb.buildFormFields('upd-operate4', [['mchntStDesc','dispMchntStDesc'],['lastCupOprDesc','lastCupResp'],['mchntCode','recAccDesc'],['customerCodeDesc','customerMobile']], {readonly:'true'});


var currDialog = false;
var currForm = false;
// fields definition
$(function() {
	// init form validator
	$('#addform').formValidation({
		framework: 'bootstrap',  live: 'enabled',  message: '未正确填写', locale: 'zh_CN', 
	    excluded: [':disabled', ':hidden', ':not(:visible)'],
	    icons: { valid: 'glyphicon glyphicon-ok', invalid: 'glyphicon glyphicon-remove', validating: 'glyphicon glyphicon-refresh' } 
	});
	
	$('#updform').formValidation({
		framework: 'bootstrap',  live: 'enabled',  message: '未正确填写', locale: 'zh_CN', 
	    excluded: [':disabled', ':hidden', ':not(:visible)'],
	    icons: { valid: 'glyphicon glyphicon-ok', invalid: 'glyphicon glyphicon-remove', validating: 'glyphicon glyphicon-refresh' } 
	});
	
	$('#updform2').formValidation({
		framework: 'bootstrap',  live: 'enabled',  message: '未正确填写', locale: 'zh_CN', 
	    excluded: [':disabled', ':hidden', ':not(:visible)'],
	    icons: { valid: 'glyphicon glyphicon-ok', invalid: 'glyphicon glyphicon-remove', validating: 'glyphicon glyphicon-refresh' } 
	});
	
	$('#updform3').formValidation({
		framework: 'bootstrap',  live: 'enabled',  message: '未正确填写', locale: 'zh_CN', 
	    excluded: [':disabled', ':hidden', ':not(:visible)'],
	    icons: { valid: 'glyphicon glyphicon-ok', invalid: 'glyphicon glyphicon-remove', validating: 'glyphicon glyphicon-refresh' } 
	});
	
	var btnUpd = {text:'保存', click:function() {
		var frmValid = $('#updform').data('formValidation');
		frmValid.validate();
		if ( frmValid.isValid() ) {
			$.ajaxForm({formId: 'updform', url: url.upd,
			success: function(data) {
				if (data.success) {
					$('#updateOper4').dialog('close');
					$('#table').flexModifyData(data.root[0]); 
					alert('操作成功'); 
				} else {alert('操作失败 原因是'+ data.syserr); }
			}});
		} else { return false; }  
	}};	

	var btnSubmit = {text:'提交', click:function() {
		var frmValid = $('#updform').data('formValidation');
		frmValid.validate();
		if ( frmValid.isValid() ) {
			$.ajaxForm({formId: 'updform', url: url.submit,
			success: function(data) {
				if (data.success) {
					$('#updateOper4').dialog('close');
					$('#table').flexModifyData(data.root[0]); 
					alert('操作成功'); 
				} else {alert('操作失败 原因是'+ data.syserr); }
			}});
		} else { return false; }  
	}};	

	var btnDeny = {text:'申请驳回',click: function() { 
		var loanProcResp = prompt("请输入驳回原因","");
		$.ajaxForm({formId: 'updform', url: url.deny,
			dealData: function(obj) { obj.loanProcResp = loanProcResp ;},	
			success: function(data) {
				if (data.success) {
					$('#updateOper').dialog('close');
					$('#table').flexAddData(data.root[0]);
					alert('操作成功'); 
				} else { alert('操作失败 原因是'+data.syserr); }
			}});
	}}; 
	 
	var btnCert = {text:'商户认证', click: function() {
		$.ajaxForm({formId: 'updform', url: url.certify,
		success: function(data) {
			if (data.success) {
				$('#updateOper').dialog('close');
				$('#table').flexAddData(data.root[0]);
				alert('操作成功'); 
			} else { alert('操作失败 原因是'+data.syserr); }
		}});
	}};
	
	var btnUOClose = {text: '关闭', click: function() { $("#updateOper").dialog('close'); }};
	
	// 贷款新增
	var addBtns = [
		{text:'保存', click:function() {
		var frmValid = $('#addform').data('formValidation'); // .formValidation('validate');
		frmValid.validate();
		if ( frmValid.isValid() ) {
			$.ajaxForm({formId: 'addform', url: url.add,
			success: function(data) {
				if (data.success) {
					$('#addOper').dialog('close');
					$('#table').flexAddData(data.root[0]);
					alert('操作成功'); 
				} else { alert('操作失败 原因是'+data.syserr); }
			}});
		} else { return false; }  
	 }}, 
	{text:'关闭', click:function(){ $('#addOper').dialog('close'); }}   
	];
	
	$('#addOper').dialog({title:'新增贷款申请', autoOpen:false, bgiframe:true, modal:false, resizeable:false, draggable:false, buttons: addBtns});
	$('#addOper').fullScreen();
	
	var addloan = function() {
		currForm = "addform";
		$('#addform').data('formValidation').resetForm(true);
		$('#addOper').dialog('open');
	};
	
	$('#updateOper').tabs();
	$('#updateOper').dialog({ title:'贷款申请信息',autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true });
	$('#updateOper').fullScreen();
	
	$('#updateOper2').tabs();
	$('#updateOper2').dialog({ title:'贷款申请信息',autoOpen: false, bgiframe: true, modal: false, resizable: false, draggable: true
		,buttons:{
		 '保存': function() {
			 var frmValid = $('#updform2').data('formValidation'); frmValid.validate();
			if ( frmValid.isValid() ) {
				$.ajaxForm({formId: 'updform2', url: url.mtan,
				success: function(data) {
					if (data.success) {
						$('#updateOper2').dialog('close');
						$('#table').flexModifyData(data.root[0]);
						alert('操作成功'); 
					} else { alert('操作失败 原因是'+data.syserr); }
				}});
			} else { return false; }
		 } ,
		'关闭':function() { $('#updateOper2').dialog('close');}
		}
	});
	$('#updateOper2').fullScreen();
	
	$('#updateOper3').tabs();
	$('#updateOper3').dialog({ title:'贷款信息提交银联',autoOpen : false, bgiframe: true, modal: false, resizable: false, draggable: true
		,buttons:{
		'提交银联': function() {
			var frmValid = $('#updform3').data('formValidation'); frmValid.validate();
			if ( frmValid.isValid() ) {
				$.ajaxForm({formId: 'updform3', url: url.send,
				success: function(data) {
					if (data.success) {
						$('#updateOper3').dialog('close');
						alert('操作成功'); 
					} else { alert('操作失败 原因是'+data.syserr); }
				}});
			} else { return false; }
		}
		,'关闭':function() { $('#updateOper3').dialog('close');}
		}
	});
	$('#updateOper3').fullScreen();
	
	$('#updateOper4').tabs();
	$('#updateOper4').dialog({ title:'贷款申请信息',autoOpen : false, bgiframe: true, modal: false, resizable: false, draggable: true
		,buttons:{
			// <sec:authorize ifAllGranted ="ROLE_MEM_CUPADM_PASS" >	
			'审核通过': function() {
				$.ajaxForm({formId: 'updform4', url: url.audit,
					success : function(data) { 
						if (data.success) { 
							alert('操作成功');
							$('#viewOper').dialog('close');
							$('#table').flexRemoveData(data.root[0]);
						} else { 
							alert('操作失败 原因是 '+ data.syserr); 
						}
					}
				});
			},// </sec:authorize>
			// <sec:authorize ifAllGranted ="ROLE_MEM_CUPADM_DENY" >	
			'审核驳回': function() {
				var loanProcResp = prompt("请输入驳回原因","");
				$.ajaxForm({formId: 'updform4', url: url.deny,
					dealData: function(obj) { obj.loanProcResp = loanProcResp ;},	
					success : function(data) { 
						if (data.success) { 
							alert('操作成功');
							$('#viewOper').dialog('close');
							$('#table').flexRemoveData(data.root[0]);
						} else { 
							alert('操作失败 原因是 '+ data.syserr); 
						}
					}
				});
			},// </sec:authorize>
			'关闭' : function() { $(this).dialog('close'); }
		}
	});
	$('#updateOper4').fullScreen();
	
	var fillMchntInfoWithRec = function( rec ) {
		if ( rec ) {
			$("#"+currForm + " input[name='mchntCode']").val(rec.mchntCode); 
			$("#"+currForm + " input[name='mchntCnName']").val(rec.mchntCnName); 
			$("#"+currForm + " input[name='licNo']").val(rec.licNo); 
			$("#"+currForm + " input[name='mchntCd']").val(rec.mchntCd); 
			$("#"+currForm + " input[name='artifNm']").val(rec.artifNm); 
			$("#"+currForm + " input[name='artifCertifId']").val(rec.artifCertifId); 
		}
	};
	var fillMchntInfo = function(data ) {
		if ( data && data.length > 0 ) {
			fillMchntInfoWithRec(data[0]);
		} else if(typeof (data)=="object"){
			if ( data ) {
				fillMchntInfoWithRec(data);
				$('#mchntSelect').dialog('close');
			}
		}
		var $valid = $('#'+currForm).data('formValidation');
		
		$valid.revalidateField('licNo');
		$valid.revalidateField('mchntCd');
		$valid.revalidateField('artifNm');
		$valid.revalidateField('artifCertifId'); 
		
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
	$('#a-mchntChoose').click(chooseMcnht);
	//$('#updform').dialog({title:'贷款信息', autoOpen: false, bgiframe: true, modal: true, resizable: false, draggable: true, width: 650, height: 350 });
	
	var zeroArr = ['','0','00','000','0000','00000','000000'];
	
	var view = function(rec, id ) {
		currForm = "updform4";
		if (!rec) { alert("请选择一条记录"); return; }
		
		var qobj=new Object();
		qobj.mchntCode=rec.mchntCode;
		var qjson = $.toJSON(qobj);
		$.ajax({url:url.single, modal:true, async:false,type:'POST',dataType:'json',data:{jsonObject:qjson},
			success:function(data){ if(data.success) { $('#mchntInfoForm4').fillObject({obj:data.root[0]});} }});

		$('#updateOper4').dialog('open');
		//$('#updform4').data('formValidation').resetForm(true);
		$('#updform4')[0].reset();
		$.jyform.fillForm('updform4',rec);
	};
	
	var updView = function(rec,id) {
		if (!rec) { alert("请选择一条记录"); return; }
		var loanSt = rec.creditSt ;
		var mfrm = "mchntInfoForm";
		switch(loanSt){
		case '00':
		case '01':
			currForm = "updform";
			mfrm = "mchntInfoForm";
			var updBtns ;
			if ( rec.creditSt == '00' ) {
				updBtns = [ btnUpd, 
//<sec:authorize ifAllGranted ="ROLE_LOAN_LOAN_SUBMIT" >
							btnSubmit, //</sec:authorize> 
				           	btnUOClose];
			} else if ( rec.creditSt == '01' ) {
				updBtns = [btnUpd, 
//<sec:authorize ifAllGranted ="ROLE_LOAN_LOAN_DENY" >
				           btnDeny, //</sec:authorize>
//<sec:authorize ifAllGranted ="ROLE_LOAN_LOAN_CERTFY" >
				           btnCert, //</sec:authorize>
				           btnUOClose];
			}
			$('#updateOper').dialog('option',{buttons:updBtns});
			$('#updateOper').dialog('open');
			$('#updform').data('formValidation').resetForm(true);
			$.jyform.fillForm('updform',rec);
			break;
		default: 
			currForm = "updform2";
			mfrm = "mchntInfoForm2";
			$('#updateOper2').dialog('open');
			$('#updform2').data('formValidation').resetForm(true);
			$.jyform.fillForm('updform2',rec);
		};
		
		var qobj=new Object();
		qobj.mchntCode=rec.mchntCode;
		var qjson = $.toJSON(qobj);
		$.ajax({url:url.single, modal:true, async:false,type:'POST',dataType:'json',data:{jsonObject:qjson},
			success:function(data){ if(data.success) { $('#'+mfrm).fillObject({obj:data.root[0]});} }});
	};
	
	var sendloan = function(rec, id) {
		if (!rec) { alert("请选择一条记录"); return; }
		var qobj=new Object();
		qobj.mchntCode=rec.mchntCode;
		var qjson = $.toJSON(qobj);
		$.ajax({url:url.single, modal:true, async:false,type:'POST',dataType:'json',data:{jsonObject:qjson},
			success:function(data){ if(data.success) { $('#mchntInfoForm3').fillObject({obj:data.root[0]});} }});
		
		$('#updform3').data('formValidation').resetForm(true);
		$.jyform.fillForm('updform3',rec);
		var cupLoanId = rec.cupLoanId;
		if ( cupLoanId ) { } else {
			var idx = 6-rec.loanId.length;
			cupLoanId = 'S0000021'+ zeroArr[idx] + rec.loanId + rec.loanReqTime;
		}
		$('#u-cupLoanId').val(cupLoanId);
		$('#updateOper3').dialog('open');
		$('#loan3_tab').click();
	};
	
	// datagrid
	$('#table').flexigrid({
		url : 'findLoan.action', sort : "loanId", usepager : true, dbClickRecord: view,
		buttons : [
			 { name: "详细信息",bclass: 'grid_view',id: 'cel',onpress: view, aclass:'ui-button-primary'}
			,{ name: "新增贷款",show:'ROLE_LOAN_LOAN_REQIN_ADD',bclass: 'grid_add',id: 'add',onpress: addloan, aclass:'ui-button-success'}
			,{ name: "修改信息",show:'ROLE_LOAN_LOAN_REQIN_UPD',bclass: 'grid_edit',id: 'cel',onpress: updView, aclass:'ui-button-success'}
//	   		,{ name: "提交银联",show:'ROLE_LOAN_LOAN_TOCUP',bclass: 'grid_edit',id: 'send', onpress: sendloan, aclass:'ui-button-success'}
	   		,{ name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		],
		colModel : [
             { display: '贷款编号', dataIndex: 'loanId', width:100, align: 'center',sortable:true }
//			,{ display: '银联商户号', dataIndex: 'mchntCd', width:120, align: 'center',sortable:true }
//			,{ display: '营业执照号码', dataIndex: 'licNo', width:120, align: 'center',sortable:true }
	        ,{ display: '贷款状态', dataIndex: 'creditStDesc', width:120, align: 'center',sortable:true }
//	        ,{ display: '用户撤销', dataIndex: 'cancelFlagDesc', width:100, align: 'center',sortable:true }
			,{ display: '姓名', dataIndex: 'artifNm', width:100, align: 'center',sortable:true }
//			,{ display: '证件号', dataIndex: 'artifCertifId', width:120, align: 'center',sortable:true }
            ,{ display: '申请贷款金额', dataIndex: 'reqLoanAt', width:100, align: 'center',sortable:true,render:fmtMoney }
	        ,{ display: '贷款余额', dataIndex: 'loanBalance', width:120, align: 'center',sortable:true,render:fmtMoney }
	        ,{ display: '贷款周期', dataIndex: 'loanCycle', width:100, align: 'center',sortable:true }
	        ,{ display: '贷款利率', dataIndex: 'loanRt', width:120, align: 'center',sortable:true,render:fmtRate }
//          ,{ display: '银联贷款编号', dataIndex: 'cupLoanId', width:120, align: 'center',sortable:true }
            ,{ display: '提交账户', dataIndex: 'recAccDesc', width:120, align: 'center',sortable:true }
            ,{ display: '申请时间', dataIndex: 'loanReqTime', width:120, align: 'center',sortable:true, render: fmtTime }
	        ,{ display: '放款成功时间', dataIndex: 'loanSuccTm', width:120, align: 'center',sortable:true, render: fmtTime }			        
		]
	});
	// $.jyform.dateTimeBox({select:$('#u-loanSuccTm')}); 
	// $.jyform.dateBox({select:$('#u-loanExpire')});
	$.jyform.sysCodeBoxes();
	$.jyform.bsDateTimeBoxes();
	// buttons
	$('#queryBtn').click(function(){$.queryTable({formId : 'queryForm', tableId : 'table'});});
	$('.bt').addClass('ui-btn-sm').button();
	$('.qry-ul input[type="button"]').addClass('ui-button-primary');
	$('#queryForm .qry-ul input[type="button"]').click();
	// <sec:authorize ifNotGranted ="ROLE_LOAN_LOAN_REQIN_ADD,ROLE_LOAN_LOAN_REQIN_UPD" >
	$('#updform input').attr('readonly','readonly');
	// </sec:authorize>
	
});
//-->
</script>
</body>
</html>