<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
<!-- formValidation -->
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
#audiAttachOper .panel .panel-body ul li.checked {background-color: #DC2A0C; border-color: #DC2A0C; }
</style>
</head>
<body>
<!-- 查询条件 -->
<div id="queryCondition">
	<form id="queryForm" name="queryForm" action="#" method="post">
	<input type="hidden" name="customerType" value="0" comparison="eq"/>
	<input type="text" id="recTimeBgn" comparison="ge" name="recTime" xtype="datetime" style="display:none;"/>
	<input type="text" id="recTimeEnd" comparison="le" name="recTime" xtype="datetime" style="display:none;"/>
		<ul class="qry-ul">
			<li><label for="q_mchntCnName">商户名称:</label><input type="text" id="q_mchntCnName" comparison="like" ftype="string" name="mchntCnName" ></input></li>
			<li><label for="q_mchntCd">银联商户号:</label><input type="text" id="q_mchntCd" comparison="like" name="mchntCd" ></input></li>
			<li><label for="q_licNo">营业执照号:</label><input type="text" id="q_licNo" comparison="like" name="licNo" ></input></li>
			<li><label for="q_artifCertifId">法人证件号:</label><input type="text" id="q_artifCertifId" comparison="like" name="artifCertifId" ></input></li>
			<li><input type="checkbox" name="recAcc" value="APP" class="cbg"/> 仅APP </li>
		</ul>
		<ul class="qry-ul">	
			<li><label for="recTimeBgn-disp">录入起始时间:</label><input type="text" id="recTimeBgn-disp" /></li>
			<li><label for="recTimeEnd-disp">结束时间:</label><input type="text" id="recTimeEnd-disp" /></li>
			<li><label for="q_mchntSt">商户状态:</label><select id="q_mchntSt" ctype="MCHNT_ST_ALL" comparison="eq" ftype="list" name="mchntSt" multiple="multiple" ></select></li>
			<li><label for="q_dispMchntSt">客户端状态:</label><select id="q_dispMchntSt" ctype="MCHNT_DST" comparison="eq" ftype="list" name="dispMchntSt" multiple="multiple" ></select></li>
			<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
			<li><input class='bt' value="重置" type="reset" /></li>
		</ul>
	</form>
</div>
<!-- 列表 -->
<div id="table"></div>

<!-- 新增 -->
<div id="addView" style="display: none;"> 
	<form action="#" id="newMchntInfoForm" class="form-horizontal" role="form">
		<input type="hidden" name="version"/>
		<div class="form-group" id="mhtinf-mchntCode-div" style="display: none;">
			<label class="col-sm-2 control-label">内部商户号:</label>
			<div class="col-sm-5">
				<input id="ins-mchntCode" type="text" name="mchntCode" class="form-control" readonly="readonly" />
			</div>
		</div>
		<div class="form-group">
			<label for="ins-mchntCd" class="col-sm-2 control-label"><font color="red">*</font>银联商户号:</label>
			<div class="col-sm-5">
				<input id="ins-mchntCd" type="text" name="mchntCd" class="form-control" placeholder="请填写银联商户号" 
					data-fv-notempty data-fv-notempty-message="银联商户号不能为空" 
					data-fv-stringlength data-fv-stringlength-max="20"
				/>
			</div>
		</div>
		<div class="form-group">
			<label for="ins-licNo" class="col-sm-2 control-label"><font color="red">*</font>营业执照号码:</label>
			<div class="col-sm-5">
				<input id="ins-licNo" type="text" name="licNo" class="form-control" placeholder="请填写营业执照号码" 
					data-fv-stringlength data-fv-stringlength-max="30"
				/>
			</div>
			
		</div>
		<div class="form-group">
			<label for="ins-artifCertifId" class="col-sm-2 control-label">法人证件号:</label>
			<div class="col-sm-5">
				<input id="ins-artifCertifId" type="text" name="artifCertifId" class="form-control" placeholder="请填写法人证件号" 
					data-fv-stringlength data-fv-stringlength-max="22"
				/>
			</div>
		</div>
	</form>
</div>

<!-- 查询 -->
<div id="viewOper" style="display: none;">
<ul>
	<li><a href="#mchntOper" id="mchnt_tab">商户信息</a></li>
	<li><a href="#attachOper" id="attach_tab">附件信息</a></li>
	<li><a href="#riskInfoOper" id="risk_tab">风控数据信息</a></li>
	<li><a href="#beforeLoanOper" id="be_loan_tab">贷前统计信息</a></li>
	<li><a href="#afterLoanOper" id="af_loan_tab">贷后数据信息</a></li>
</ul>
<div id="mchntOper" >
<form action="#" id="mchntInfoForm" class="form-horizontal" role="form">
<input type="hidden" name="version" />
<div class="panel panel-default">
	<div class="panel-heading">商户信息</div>
	<div class="panel-body" id="view-mchntinfo" ></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">联系人信息</div>
	<div class="panel-body" id="view-contact" ></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">操作信息</div>
	<div class="panel-body" id="view-operate"></div>	
</div>
</form>
</div>
<!-- 商户附件信息 -->
<div id="attachOper"></div>
<!-- 贷前数据信息 -->
<div id="beforeLoanOper">
	<div id="beforeLoanGrid"></div>
</div>
<!-- 风控数据信息 -->
<div id="riskInfoOper">
	<div id="riskInfoGrid"></div>
</div>
<!-- 贷后数据信息 -->
<div id="afterLoanOper">
	<div id="afterLoanGrid"></div>
</div>

<div style="display: none;">
<form id="detailgrid-frm">
	<input id="detailgrid-mchntCode" type="hidden" name="mchntCode" comparison="eq" />
</form>
</div>

</div>

<!-- 修改 -->
<div id="updOper" style="display: none;">
<ul>
	<li><a href="#updMchntOper" id="u_mchnt_tab">商户信息</a></li>
	<li><a href="#updAttachOper" id="u_attach_tab">附件信息</a></li>
</ul>	
<div id="updMchntOper">
<form action="#" id="u-mchntInfoForm" class="form-horizontal" role="form">
<input type="hidden" name="version" />
<div class="panel panel-default">
	<div class="panel-heading">商户信息</div>
	<div class="panel-body" id="upd-mchntinfo"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">联系人信息</div>
	<div class="panel-body" id="upd-contact"></div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">操作信息</div>
	<div class="panel-body" id="upd-operate"></div>	
</div>
</form>
</div><!-- End Of 商户信息 -->
<!-- 商户附件 -->
<div id="updAttachOper">
<div id="attachBtnGrp" class="attachBtnGrp">
	<button class="btn btn-danger" type="button" id="checkAll">全部选择</button>
	<button class="btn btn-danger" type="button" id="uncheckAll">取消选择</button>
	<button class="btn btn-danger" type="button" id="deleteImg">删除</button>
</div>
</div><!-- End Of 商户附件 -->
</div>

<!-- 提交商户资质审核 -->
<div id="auditView" style="display: none;">
<ul>
	<li><a href="#auditOper" id="audit_tab">商户信息</a></li>
	<li><a href="#audiAttachOper" id="audi_attach_tab">附件信息</a></li>
</ul>
<div id="auditOper">
	<form action="#" id="sendAduitForm" class="form-horizontal" role="form">
	<input id="medSeq" type="hidden" name="medSeq" />
	<input type="hidden" name="mchntCode" />
	<div class="form-group">	
		<label class="col-sm-2 control-label">银联商户号:</label>
		<div class="col-sm-5">
			<input type="text" name="mchntCd" id="aud-mchntCd" readonly="readonly" class="form-control"/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">营业执照号码:</label>
		<div class="col-sm-5">
			<input type="text" name="licNo" readonly="readonly" class="form-control"/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">法人证件号:</label>
		<div class="col-sm-5">
			<input type="text" name="artifCertifId" readonly="readonly" class="form-control"/>
		</div>
	</div>
	<div class="form-group">
		<label for="ins-mchntCd" class="col-sm-2 control-label">资质审核文件:</label>
		<div class="col-sm-5 input-group">
			<input id="med-filename" type="text" class="form-control" placeholder="请选择文件"/>
			<span class="input-group-btn">
       			<button class="btn btn-default" type="button" id="uploadFile">上传</button>
       			<button class="btn btn-default" type="button" id="downloadFile">下载</button>
       			<button class="btn btn-default" type="button" id="checkAttach">选择附件</button>
     		</span>
		</div>
	</div>
	</form>
</div>
<div id="audiAttachOper">
<div id="attachBtnGrp" class="attachBtnGrp">
	<button class="btn btn-danger" type="button" id="checkAll4Aud">全部选择</button>
	<button class="btn btn-danger" type="button" id="uncheckAll4Aud">取消选择</button>
	<button class="btn btn-danger" type="button" id="genZip">生成zip文件</button>
</div>
</div>
</div>

<!-- 发送短信框 -->
<div id="smsOper" style="display: none;">
<form action="#" id="smsForm" role="form" class="form-horizontal">
	<div class="form-group">
		<label class="col-sm-2 control-label"><font color="red">*</font>银联商户号:</label>
		<div class="col-sm-5">
			<input id="sms-mchntCd" type="text" name="mchntCd" class="form-control" readonly required/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><font color="red">*</font>用户手机号:</label>
		<div class="col-sm-5">
			<input id="sms-customerMobile" type="text" name="customerMobile" class="form-control" required/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">补填内容:</label>
		<div class="col-sm-5">
			<input id="sms-fields" type="text" name="fields" class="form-control" placeholder="商户补填信息必须填写" />
		</div>
	</div>
</form>
</div>

<script type="text/javascript">
<!--
var attachPanelView = new AttachPanel({id:'attachOper'});
var updAttachPanelView = new AttachPanel({id:'updAttachOper',editable:true, addImgBtnClick:function(type){ $('input[id=attachFile]').click(); $('#upload-attachType').val(type); $('#upload-mchntCode').val($('#upd-operate-mchntCode').val()); }});
var attachPanelAudit = new AttachPanel({id:'audiAttachOper'});

var loadImgFile = function() {
	$.jyajax.hideCover(); var data= $('#imgFileFrame').contents().find("body").html();
	if(data != ""){
		data = frameCtx(data);
		var dataObj = JSON.parse(data);
		if(dataObj) {
			if(dataObj.success && dataObj.root.length == 1){
				var attach = dataObj.root[0];
				updAttachPanelView.addToAttachPanel(attach);
			} else {
				alert('上传文件失败' + dataObj.syserr);
			}
		}
	}
};
var loadZipFile = function() {
	$.jyajax.hideCover();
	var data= $('#zipFileFrame').contents().find("body").html();
	// alert(data);
	if(data != ""){
		data = frameCtx(data);
		// alert(data);
		var dataObj = JSON.parse(data);
		if(dataObj) {
			if(dataObj.success && dataObj.root.length == 1){
				var medpath = dataObj.root[0].medPath;
				var medSeq = dataObj.root[0].medSeq;
				alert('上传文件成功');
				$('#med-filename').val(medpath.trim());
				$('#medSeq').val(medSeq);
			} else {
				alert('上传文件失败' + dataObj.syserr);
			}
		}
	}
};

//url
var url = {
grid: 'findMngrMchnt.action',
excel:'findMngrMchntExcel.action',
single:'findMchntbyMchntCode.action',

listHis:'findMhtHisTranbyMchntCode.action',
listHisExcel:'findMhtHisTranbyMchntCodeExcel.action',
listRisk:'findMhtRiskDatabyMchntCode.action',
listRiskExcel:'findMhtRiskDatabyMchntCodeExcel.action',
listLoanAfter:'findMhtTransAfterLoanbyMchntCode.action',
listLoanAfterExcel:'findMhtTransAfterLoanbyMchntCodeExcel.action',

update: 'updateMchntInfo4Mngr.action', // 修改商户信息
mchntinfo: 'cupsMchntInfo4Mngr.action', 	//
riskdata:  'cupsRiskData4Mngr.action',
sendaudit: 'cupsSendAudit4Mngr.action',
transdata: 'cupsFetchTransdata4Mngr.action',
audit: 'auditMchnt4Mngr.action',
deny: 'denyMchnt4Mngr.action',

smsc: 'sendInfoCompleteSMS.action',
smsr: 'sendRegistSMS.action',

athdef: 'findAttachDef.action',
attach: 'findMchntAttach.action',
uploadAttach: 'uploadAttachFile4Mngr.action',
deleteAttach: 'deleteAttachFile4Mngr.action',
genZip: 'genZipFile.action'

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
,{name:'mchntCrtDt', desc:'商户入网时间'  , dateFormat:'Ymd',xtype: 'date', jtype:'String', length: 8}
,{name:'posNum', desc:'装机数量'  , jtype:'Long', length: 10}
,{name:'remark', desc:'备注'  , jtype:'String', length: 200}
,{name:'mchntSt', desc:'商户状态'  , jtype:'String', length: 2}
,{name:'mchntStDesc', desc:'商户状态'  , jtype:'String', length: 30}
,{name:'dispMchntSt', desc:'客户端显示状态'  , jtype:'String', length: 2}
,{name:'dispMchntStDesc', desc:'客户端显示状态'  , jtype:'String', length: 30}
,{name:'recAcc', desc:'记录创建账户'  , jtype:'String', length: 30}
,{name:'recAccDesc', desc:'记录创建账户'  , jtype:'String', length: 200}
,{name:'recTime', desc:'记录创建时间' , dateFormat:'YmdHis',xtype: 'date' , jtype:'String', length: 14}
,{name:'lastUptTime', desc:'最后修改时间' , dateFormat:'YmdHis',xtype: 'date' , jtype:'String', length: 14}
,{name:'lastUptAcc', desc:'最后更新账户'  , jtype:'String', length: 30}
,{name:'lastUptOrg', desc:'最后更新机构'  , jtype:'String', length: 30}
,{name:'lastCupOpr', desc:'最近接口操作'  , jtype:'String', length: 40}
,{name:'lastCupOprDesc', desc:'最近接口操作'  , jtype:'String', length: 100}
,{name:'lastCupTime', desc:'最近接口调用时间' , dateFormat:'YmdHis',xtype: 'date' , jtype:'String', length: 14}
,{name:'lastCupResp', desc:'最近接口返回信息'  , jtype:'String', length: 200}
,{name:'auditTime', desc:'最近资质申请成功时间' , dateFormat:'YmdHis',xtype: 'date' , jtype:'String', length: 14}
,{name:'beforeTime', desc:'最近贷前查询成功时间' , dateFormat:'YmdHis',xtype: 'date' , jtype:'String', length: 14}
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

]);

fb.buildFormFields('view-mchntinfo',
		['mchntCnName','mchntDispName',{name:'regAddr', xtype:'textarea'}
		,'phone',['mchntTpDesc','mchntGrpDesc'],['mchntEnTypeDesc','mchntStartDate']
		,[{name:'regCapital',xtype:'money',unit:'元'},'orgCode'],['licNo','mchntCd'],['artifNm','artifCertifId']
		,['posNum','mchntCrtDt'],['acqInsIdCd','reveRegCd'],{name:'remark',xtype:'textarea'}],{readonly:'true'});
fb.buildFormFields('view-contact', [['mchntContact','mchntContactMobile'],['mchntArtifMobile','telephone']], {readonly:'true'});
fb.buildFormFields('view-operate', [['mchntStDesc','dispMchntStDesc'],['lastCupOprDesc','lastCupResp'],[{name:'mchntCode',id:'mchntCode'},'recAccDesc'],['customerCodeDesc','customerMobile']], {readonly:'true'});

fb.buildFormFields('upd-mchntinfo',
		['mchntCnName','mchntDispName',{name:'regAddr', xtype:'textarea'}
		,'phone',[{name:'mchntTp',ctype:'MCHNT_TP'},{name:'mchntGrp',ctype:'MCHNT_GRP'}],[{name:'mchntEnType',ctype:'MCHNT_ETP'},'mchntStartDate']
		,[{name:'regCapital',xtype:'money',unit:'元'},'orgCode'],['licNo',{name:'mchntCd', readonly:'true'}],['artifNm','artifCertifId']
		,['posNum','mchntCrtDt'],['acqInsIdCd','reveRegCd'],{name:'remark',xtype:'textarea'}],{});
fb.buildFormFields('upd-contact', [['mchntContact','mchntContactMobile'],['mchntArtifMobile','telephone']], {});
fb.buildFormFields('upd-operate', [['mchntStDesc','dispMchntStDesc'],['lastCupOprDesc','lastCupResp'],['mchntCode','recAccDesc'],['customerCodeDesc','customerMobile']], {readonly:'true'});

//fields definition
$(function() {
	// init form validate
	$('#u-mchntInfoForm').formValidation({
		framework: 'bootstrap',  live: 'enabled',  message: '未正确填写', locale: 'zh_CN', 
	    excluded: [':disabled', ':hidden', ':not(:visible)'],
	    icons: { valid: 'glyphicon glyphicon-ok', invalid: 'glyphicon glyphicon-remove', validating: 'glyphicon glyphicon-refresh' } 
	});

	$('#newMchntInfoForm').formValidation({
		framework: 'bootstrap',  live: 'enabled',  message: '未正确填写', locale: 'zh_CN', 
	    excluded: [':disabled', ':hidden', ':not(:visible)'],
	    icons: { valid: 'glyphicon glyphicon-ok', invalid: 'glyphicon glyphicon-remove', validating: 'glyphicon glyphicon-refresh' } 
	});
	
	$('#updOper').tabs();
	$('#updOper').dialog({ title:'商户信息修改',autoOpen: false, bgiframe: true, modal: false, resizable: false, draggable: true
		,buttons:{
			 '保存': function() { 
				var frmValid = $('#u-mchntInfoForm').data('formValidation'); 
				frmValid.validate();
				if ( frmValid.isValid() ) {
					$.ajaxForm({formId: 'u-mchntInfoForm', url: url.update,
					success: function(data) {
						if (data.success) {
							$('#updOper').dialog('close');
							$('#table').flexModifyData(data.root[0]);
							alert('操作成功'); 
						} else { 
							alert('操作失败 原因是'+ data.syserr); 
						}
					}});
				} else { return false; }  
			},
			'关闭' : function() { $(this).dialog('close'); }
		}
	});
	$('#updOper').fullScreen();
	// SMS form
	$('#smsForm').formValidation({
		framework: 'bootstrap',  live: 'enabled',  message: '未正确填写', locale: 'zh_CN', 
	    excluded: [':disabled', ':hidden', ':not(:visible)'],
	    icons: { valid: 'glyphicon glyphicon-ok', invalid: 'glyphicon glyphicon-remove', validating: 'glyphicon glyphicon-refresh' } 
	});
	
	$('#smsOper').dialog({title:'通知短信发送',autoOpen:false,modal:false
		,buttons:{
			'已注册通知': function() {
				var frmValid = $('#smsForm').data('formValidation'); 
				frmValid.validate();
				if ( frmValid.isValid() ) {
					var mchntCd = $('#sms-mchntCd').val();
					var customerMobile = $('#sms-customerMobile').val();
					$.ajax({type:'POST', dataType:'json', url: url.smsr, modal:true, 
						data:{mchntCd:mchntCd,customerMobile:customerMobile},
						success: function(data) {
							if (data.success) {
								alert('发送成功'); 
							} else { alert('操作失败 原因是'+ data.syserr); }
						}});
				} else { return false; }  
			},
			'补填信息通知': function() {
				var frmValid = $('#smsForm').data('formValidation'); 
				frmValid.validate();
				var fields = $('#sms-fields').val();
				if ( frmValid.isValid() ) {
					var mchntCd = $('#sms-mchntCd').val();
					var customerMobile = $('#sms-customerMobile').val();
					$.ajax({type:'POST', dataType:'json', url: url.smsc, modal:true, 
						data:{mchntCd:mchntCd,customerMobile:customerMobile,fields:fields},
						success: function(data) {
							if (data.success) {
								alert('发送成功'); 
							} else { alert('操作失败 原因是'+ data.syserr); }
						}});
				} else { return false; }  
			},
			'关闭' : function() { $(this).dialog('close'); }
		}});
	$('#smsOper').fullScreen();
	// 
	$('#viewOper').tabs();
	$('#viewOper').dialog({ title:'商户综合信息', autoOpen : false, bgiframe : true, modal : false, resizable: false, draggable: true, width : 850, height : 570 
		,buttons:{
			// <sec:authorize ifAllGranted ="ROLE_MEM_CUPADM_QRYR" >	
			 '获取风险信息': function() { 
				 $.ajaxForm({formId: 'mchntInfoForm', url: url.riskdata,
						success : function(data) { 
							if (data.success) { 
								alert('操作成功');  $('#risk_tab').click();
								$.queryTable({formId : 'detailgrid-frm', tableId : 'riskInfoGrid'});
								$.queryTable({formId : 'queryForm', tableId : 'table'});
							} else { 
								alert('操作失败 原因是 '+ data.syserr); 
							}
						}
					});
			}, // </sec:authorize>
			// <sec:authorize ifAllGranted ="ROLE_MEM_CUPADM_QRYB" >	
			'获取贷前数据': function() {
				$.ajaxForm({formId: 'mchntInfoForm', url: url.transdata,
					success : function(data) { 
						if (data.success) { 
							alert('操作成功');  $('#be_loan_tab').click();
							$.queryTable({formId : 'detailgrid-frm', tableId : 'beforeLoanGrid'});
						} else { 
							alert('操作失败 原因是 '+ data.syserr); 
						}
					}
				});
			}, // </sec:authorize>
			// <sec:authorize ifAllGranted ="ROLE_MEM_CUPADM_PASS" >	
			'审核通过': function() {
				$.ajaxForm({formId: 'mchntInfoForm', url: url.audit,
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
				var remark = prompt("请输入驳回原因","");
				
				$.ajaxForm({formId: 'mchntInfoForm', url: url.deny,
					dealData: function(obj) { obj.remark = remark ;},
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
	$('#viewOper').fullScreen();
	
	// Initial Attach panel
	$.ajax({url:url.athdef, async:true, dataType:'json', 
		success:function(data) { 
			if ( data.success )  { 
				attachPanelView.buildAttachPanel(data.root); 
				updAttachPanelView.buildAttachPanel(data.root);
				attachPanelAudit.buildAttachPanel(data.root)
			} 
		}
	});
	// 直接上传
	$('#attachFile').change(function() { 
		handleFileName('attachFileName', $(this).val());
		$.jyajax.showCover();
		$("#uploadImgForm")[0].submit();
	});
	$('#checkAll').on('click',function(){ updAttachPanelView.checkAll(); });
	$('#uncheckAll').on('click',function(){ updAttachPanelView.uncheckAll(); });
	$('#deleteImg').on('click',function(){
		var seqs = updAttachPanelView.getCheckedSeqs();
		if ( seqs == '') {
			alert('请选择要删除附件'); return;
		}
		$.ajax({url:url.deleteAttach, type:'POST', async:true, dataType:'json', modal:true, 
			data :{attachSeqs: seqs},
			success: function(data) {
				if ( data.success) {
					alert('操作成功');
					updAttachPanelView.removeChecked();
				} else {
					alert('操作失败 ' + data.syserr);
				}
		}});
	});
	
	// 选择附件
	$('#checkAttach').on('click', function() {
		attachPanelAudit.uncheckAll();
		$('#audi_attach_tab').click();
	});
	$('#checkAll4Aud').on('click',function(){ attachPanelAudit.checkAll(); });
	$('#uncheckAll4Aud').on('click',function(){ attachPanelAudit.uncheckAll(); });
	$('#genZip').on('click',function(){
		var fileName = $('#aud-mchntCd').val();
		fileName = fileName?fileName:'商户附件';
		var medUrls = attachPanelAudit.getCheckedUrls();
		if (medUrls == '') {
			alert('请选择附件');
			return;
		}
		if ( medUrls.indexOf(',') == -1 && medUrls.indexOf('.') > 0 ) {
			var extName = medUrls.substring(medUrls.indexOf('.'), medUrls.length);
			console.log(extName.toLowerCase());
			if ( extName.toLowerCase() == '.zip' ) {
				$('#med-filename').val(medUrls.trim());
				$('#audit_tab').click();
				return ;
			}
		}
		$.ajax({url:url.genZip, type:'POST', async:true, dataType:'json', modal:true, 
			data :{fileName:fileName, medUrls:medUrls},
			success: function(data) {
				if ( data.success) {
					alert('操作成功');
					var medpath = data.root[0].medPath;
					var medSeq = data.root[0].medSeq;
					console.log(medpath);
					$('#med-filename').val(medpath.trim());
					$('#medSeq').val(medSeq);
					$('#audit_tab').click();
				} else {
					alert('操作失败 ' + data.syserr);
				}
		}});
	});
	
	// 直接上传
	$('#uploadFile').click(function() { $('input[id=zipFile]').click(); });
	$('#zipFile').change(function() { 
		handleFileName('zipFileName', $(this).val());
		$.jyajax.showCover();
		$("#uploadZipForm")[0].submit();
	});
	// 下载
	$('#downloadFile').on('click', function() {
		var url = $('#med-filename').val();
		if ( url != '') {
			window.open(url);
		}
	});
	
	// 获取商户Dialog
	$('#addView').dialog({
		autoOpen: false, title:'获取商户信息', modal:false,
		buttons:[
		{text:'获取商户信息', click:function() {
			var frmValid = $('#newMchntInfoForm').data('formValidation'); 
			frmValid.validate();
			if ( frmValid.isValid() ) {
				$.ajaxForm({formId: 'newMchntInfoForm', url: url.mchntinfo,
					success : function(data) { 
						if (data.success) { 
							if ( data.total == 1 && data.rows[0] && data.rows[0].lastCupResp ) {
								alert(data.rows[0].lastCupResp);
							} else {
								alert('操作成功'); 
							}
							$('#addView').dialog('close'); 
							$.queryTable({formId : 'queryForm', tableId : 'table'});
						} else { 
							alert('操作失败 原因是 '+ data.syserr);
							$.queryTable({formId : 'queryForm', tableId : 'table'});
							if ( data.total == 1 && data.rows[0] && data.rows[0].mchntCode ) {
								$('#ins-mchntCode').val(data.rows[0].mchntCode);
								$('#mhtinf-mchntCode-div').show();
							}
						}
					}
				});
			} else { return false; }  
		}}
		,{text:'关闭', click:function() { $(this).dialog('close'); }}
		]
	});
	$('#addView').fullScreen();
	
	$('#auditView').tabs();
	$('#auditView').dialog({
		autoOpen: false, title:'提交商户资质审核', 
		buttons:[
		{text:'提交资质审核', click:function() {
			var obj = new Object();
			obj.licNo = $('#sendAduitForm input[name=licNo]').val();
			obj.mchntCd = $('#sendAduitForm input[name=mchntCd]').val();
			obj.artifCertifId = $('#sendAduitForm input[name=artifCertifId]').val();
			obj.mchntCode = $('#sendAduitForm input[name=mchntCode]').val();
			$.ajax({url: url.sendaudit, dataType:'json', type:'POST', modal:true, 
				data:{jsonObject: $.toJSON(obj), medSeq:$('#medSeq').val(), medUrl:$('#med-filename').val()},
				success : function(data) { 
					if (data.success) { 
						alert('操作成功');  
						$.queryTable({formId : 'queryForm', tableId : 'table'});
					} else { 
						alert('操作失败 原因是 '+ data.syserr); 
					}
				}
			});
		}}
		,{text:'关闭', click:function() { $(this).dialog('close'); }}
		]
	});
	$('#auditView').fullScreen();
	
	var addnew = function(rec,id) {
		if ( !rec ) {
			// 未选中记录时，获取新商户
			$('#newMchntInfoForm')[0].reset();
			$('#mhtinf-mchntCode-div').hide();
			$("#newMchntInfoForm input[name='mchntCd']").removeAttr('readonly');
			$('#addView').fullScreen().dialog('open');
		} else {
			$('#newMchntInfoForm')[0].reset();
			$('#newMchntInfoForm').fillObject({obj:rec});
			$("#newMchntInfoForm input[name='mchntCd']").attr('readonly','true');
			if (rec.mchntSt == '01' || rec.mchntSt == '09' ) {
				$("#newMchntInfoForm input[name='mchntCd']").removeAttr('readonly');
			} 
			$('#mhtinf-mchntCode-div').show();
			$('#addView').fullScreen().dialog('open');
		}
	};
	
	var auditMchnt = function(rec,id) {
		if (!rec) { alert("请选择一条记录"); return; }
		var qobj=new Object();
		qobj.mchntCode=rec.mchntCode;
		var qjson = $.toJSON(qobj);
		
		$('#sendAduitForm')[0].reset();
		$('#sendAduitForm').fillObject({obj:rec});
		$('#auditView').fullScreen().dialog('open');
		
		// add attach
		attachPanelAudit.resetAttachPanel();
		$.ajax({url:url.attach,type:'POST',dataType:'json',async:true,data:{jsonObject:qjson},success:function(data){if(data.success) attachPanelAudit.addToAttachPanel(data.root); }});
	};
	
	var view=function(rec,id){
		if (!rec) { alert("请选择一条记录"); return; }
		
		var qobj=new Object();
		qobj.mchntCode=rec.mchntCode;
		var qjson = $.toJSON(qobj);
		$.ajax({url:url.single, modal:true, async:false,type:'POST',dataType:'json',data:{jsonObject:qjson},
			success:function(data){ if(data.success) { $('#mchntInfoForm').fillObject({obj:data.root[0]});} else { alert('未查询到该商户信息，请刷新后重试. ')} }});

		$('#viewOper').dialog('open');
		$('#mchnt_tab').click();
		
		$('#detailgrid-mchntCode').val(rec.mchntCode);
		$.queryTable({formId : 'detailgrid-frm', tableId : 'riskInfoGrid'});
		$.queryTable({formId : 'detailgrid-frm', tableId : 'beforeLoanGrid'});
		$.queryTable({formId : 'detailgrid-frm', tableId : 'afterLoanGrid'});
		
		// add attach
		attachPanelView.resetAttachPanel();
		$.ajax({url:url.attach,type:'POST',dataType:'json',async:true,data:{jsonObject:qjson},success:function(data){if(data.success) attachPanelView.addToAttachPanel(data.root); }});
	};
	
	var updateView = function (rec, id ) {
		if (!rec) { alert("请选择一条记录"); return; }
		$('#u-mchntInfoForm').data('formValidation').resetForm(true);
		
		var qobj=new Object();
		qobj.mchntCode=rec.mchntCode;
		var qjson = $.toJSON(qobj);
		$.ajax({url:url.single, modal:true, async:false,type:'POST',dataType:'json',data:{jsonObject:qjson},
			success:function(data){ if(data.success) { $('#u-mchntInfoForm').fillObject({obj:data.root[0]});} else { alert('未查询到该商户信息，请刷新后重试. ')} }});
		
		$('#updOper').dialog('open');
		$('#u_mchnt_tab').click();
		// add attach
		updAttachPanelView.resetAttachPanel();
		$.ajax({url:url.attach,type:'POST',dataType:'json',async:true,data:{jsonObject:qjson},success:function(data){if(data.success) updAttachPanelView.addToAttachPanel(data.root); }});
		
	} ;	
	
	var smsview = function(rec, id ) {
		if (!rec) { alert("请选择一条记录"); return; }
		$('#smsForm').data('formValidation').resetForm(true);
		$('#smsForm').fillObject({obj:rec});
		$('#smsOper').dialog('open');
	}
	
	var singleLine = function( rec , id ) {
		if (!rec) { alert("请选择一条记录"); return; }
		window.open(url.excel+"?mchntCode="+rec.mchntCode);
	};
	
	
	// datagrid
	$('#table').flexigrid({
		url : url.grid, sort : "mchntCode", checkbox: false, usepager: true,dbClickRecord:view,
		buttons : [
			 { name: "详细信息",bclass: 'grid_view',id: 'cel', onpress: view, aclass:'ui-button-primary'}
			,{ name: "修改信息",show:'ROLE_MEM_CUPADM_UPD',bclass: 'grid_edit',id: 'cel', onpress: updateView, aclass:'ui-button-success'}
			,{ name: "获取商户信息",show:'ROLE_MEM_CUPADM_QRY', bclass:'grid_add', id:'add',  onpress: addnew, aclass:'ui-button-success' } 
			,{ name: "提交资质审核",show:'ROLE_MEM_CUPADM_AUD', bclass:'grid_view', id:'send',  onpress: auditMchnt, aclass:'ui-button-success' }
			,{ name: "短信通知",  bclass:'grid_view', id:'sms',  onpress: smsview, aclass:'ui-button-success' }  
			,{ name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
			,{ name: "单行导出", bclass: 'grid_excel', onpress:singleLine}
		],
		colModel : [
             { display: '内部商户号', dataIndex: 'mchntCode', width:100, align: 'center', sortable:true }
            ,{ display: '营业执照号码', dataIndex: 'licNo', width:150, align: 'center', sortable:true }
	        ,{ display: '银联商户号', dataIndex: 'mchntCd', width:150, align: 'center', sortable:true }
	        ,{ display: '税务登记代码', dataIndex: 'reveRegCd', width:120, align: 'center', sortable:true }
	        ,{ display: '商户中文名称', dataIndex: 'mchntCnName', width:120, align: 'center', sortable:true }			        
	        ,{ display: '注册地址', dataIndex: 'regAddr', width:120, align: 'center', sortable:true }
	        ,{ display: '法人代表姓名', dataIndex: 'artifNm', width:120, align: 'center', sortable:true }
	        ,{ display: '法人代表证件号', dataIndex: 'artifCertifId', width:120, align: 'center', sortable:true }
	        ,{ display: '用户手机号', dataIndex: 'customerMobile', width:100, align: 'center', sortable:true }
	        ,{ display: '商户状态', dataIndex: 'mchntStDesc', width:120, align: 'center', sortable:true }
	        ,{ display: '客户端状态', dataIndex: 'dispMchntStDesc', width:100, align: 'center', sortable:true }
	        ,{ display: '查询响应', dataIndex: 'lastCupResp', width:120, align: 'center', sortable:true }
	        ,{ display: '提交用户', dataIndex: 'recAccDesc', width:80, align: 'center', sortable:true }
	        ,{ display: '录入时间', dataIndex: 'recTime', width:120, align: 'center', sortable:true , render: fmtTime}
	        ,{ display: '提交资质审核时间', dataIndex: 'auditTime', width:120, align: 'center', sortable:true, render: fmtTime }
	        ,{ display: '获取贷前数据时间', dataIndex: 'beforeTime', width:120, align: 'center', sortable:true, render: fmtTime }
		]
	});
	$('#beforeLoanGrid').flexigrid({
		url : url.listHis, sort : "hisTransMonth", usepager : true, height: 250,
		buttons : [{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#beforeLoanGrid').flexFile(url.listHisExcel); }}],
		colModel : [
           //{ display: '内部商户号', dataIndex: 'mchntCode', width:100, align: 'center',sortable:true }
             { display: '统计月份', dataIndex: 'hisTransMonth', width:100, align: 'center',sortable:true, render: fmtMonth }
	        ,{ display: '月交易额', dataIndex: 'monthTransAt', width:100, align: 'center',sortable:true,render:fmtMoney }
	        ,{ display: '月交易额同比', dataIndex: 'monthTransAtYear', width:120, align: 'center',sortable:true /* ,render:fmtRate */}
	        ,{ display: '月交易笔数', dataIndex: 'monthTransNum', width:120, align: 'center',sortable:true }			        
	        ,{ display: '月消费客户数', dataIndex: 'monthCusNum', width:120, align: 'center',sortable:true }
	        ,{ display: '月重复消费客户数', dataIndex: 'monthRepeatCusNum', width:120, align: 'center',sortable:true }
	        ,{ display: '贷记卡与借记卡交易比', dataIndex: 'creditDebitRatio', width:120, align: 'center',sortable:true/* ,render:fmtRate */ }
	        //,{ display: '备注', dataIndex: 'remark', width:120, align: 'center',sortable:true }
	        //,{ display: '记录创建时间', dataIndex: 'recTime', width:120, align: 'center',sortable:true, render: fmtTime }
		]
	});
	$('#riskInfoGrid').flexigrid({
		url : url.listRisk, sort : "recTime", usepager : true, height: 250,
		buttons : [{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#riskInfoGrid').flexFile(url.listRiskExcel); }}],
		colModel : [
            //{ display: '内部商户号', dataIndex: 'mchntCode', width:100, align: 'center',sortable:true }
             { display: '近三个月欺诈交易总笔数', dataIndex: 'fraudTransNum', width:150, align: 'center',sortable:true, render: fmtCount}
	        ,{ display: '近三个月欺诈交易总金额', dataIndex: 'fraudTransAt', width:150, align: 'center',sortable:true,render:fmtMoney}
	        ,{ display: '可疑商户名单是否命中', dataIndex: 'susMchntInDesc', width:180, align: 'center',sortable:true}
	        ,{ display: '不良持卡人名单是否命中', dataIndex: 'negCdhdInDesc', width:180, align: 'center',sortable:true}			        
	        //,{ display: '备注', dataIndex: 'remark', width:120, align: 'center',sortable:true}
	        ,{ display: '记录时间', dataIndex: 'recTime', width:120, align: 'center',sortable:true, render: fmtTime  }
		]
	});
	$('#afterLoanGrid').flexigrid({
		url : url.listLoanAfter, sort : "transDt", usepager : true, height: 250,
		buttons : [{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#afterLoanGrid').flexFile(url.listLoanAfterExcel); }}],
		colModel : [
             { display: '交易日期', dataIndex: 'transDt', width:100, align: 'center',sortable:true,render:fmtDate }
	        ,{ display: '当日交易额', dataIndex: 'daliyTransAt', width:100, align: 'center',sortable:true,render:fmtMoney }
	        ,{ display: '当日交易笔数', dataIndex: 'daliyTransNum', width:120, align: 'center',sortable:true}
	        ,{ display: '当日消费客户数', dataIndex: 'daliyCusNum', width:120, align: 'center',sortable:true}			        
	        ,{ display: '当日重复消费客户数', dataIndex: 'daliyRepeatCusNum', width:120, align: 'center',sortable:true }
	        ,{ display: '贷记卡与借记卡交易比', dataIndex: 'creditDebitRatio', width:120, align: 'center',sortable:true}
	        //,{ display: '备注', dataIndex: 'remark', width:120, align: 'center',sortable:true }
	        //,{ display: '记录创建时间', dataIndex: 'recTime', width:120, align: 'center',sortable:true, render: fmtTime   }
		]
	});
	//
	$.jyform.sysCodeBoxes(); 
	$.jyform.bsDateTimeBoxes();
	// buttons
	$('#queryBtn').click(function(){$.queryTable({formId: 'queryForm', tableId: 'table'});});
	$('.qry-ul input[type="button"]').addClass('ui-button-primary');
	$('.bt').addClass('ui-btn-sm').button();
	$('#queryForm .qry-ul input[type="button"]').click();
});
//-->
</script>

<!-- fileupload -->
<div style="display: none">
	<form action="uploadAttachFile4Mngr.action" id="uploadImgForm" target="imgFileFrame" enctype="multipart/form-data" method="post" >
		<input id="attachFile" type="file" name="uploadFile" style="display:none" accept="image/*" />
		<input type="text" id="attachFileName" name="uploadFileName"/>
		<input type="text" id="upload-attachType" name="attachType"/>
		<input type="text" id="upload-mchntCode" name="mchntCode"/>
		<input type="hidden" value="ajaxUpload" name="ajaxUpload"/>
	</form>
	<iframe id="imgFileFrame" name="imgFileFrame" onload="loadImgFile()" ></iframe>
	<form action="uploadAuditZipFile4Mngr.action" id="uploadZipForm" target="zipFileFrame" enctype="multipart/form-data" method="post" >
		<input id="zipFile" type="file" name="uploadFile" style="display:none" accept="application/zip" />
		<input type="text" id="zipFileName" name="uploadFileName"/>
		<input type="hidden" value="ajaxUpload" name="ajaxUpload"/>
	</form>
	<iframe id="zipFileFrame" name="zipFileFrame" onload="loadZipFile()" ></iframe>
</div>
</body>
</html>