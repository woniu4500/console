<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<title><f:message key="application.title" /></title>
<jsp:include page="/page/common/linksheet.jsp" />
<style>
.frm-ul li label{min-width:120px}
</style>
</head>
<body>
<!-- 查询条件 -->
<div id="queryCondition">
	<form id="queryForm" name="queryForm" action="#" method="post">
	<input type="hidden" name="customerType" value="0" comparison="eq"/>
		<ul class="qry-ul">
		<!-- 
		<li><label for="q_customerCode">会员代码:</label>
		<input type="text" id="q_customerCode" maxlength="20" comparison="eq" name="customerCode"/></li>
		<li><label for="q_mobile">手机号:</label><input type="text" id="q_mobile" comparison="like" name="mobile" ></input></li>
		<li><label for="q_isActive">是否有效:</label><select id="q_isActive" ctype="PUB_VAILD" comparison="like" name="isActive" ></select></li>
		 -->
			<li><label for="q_mchntCd">银联商户号:</label><input type="text" id="q_mchntCd" comparison="like" name="mchntCd" ></input></li>
			<li><label for="q_licNo">营业执照号:</label><input type="text" id="q_licNo" comparison="like" name="licNo" ></input></li>
			<li><label for="q_artifCertifId">法人证件号:</label><input type="text" id="q_artifCertifId" comparison="like" name="artifCertifId" ></input></li>
		</ul>
		<ul class="qry-ul">	
			<li><label for="q_mchntCnName">商户名称:</label><input type="text" id="q_mchntCnName" comparison="like" ftype="string" name="mchntCnName" ></input></li>
			<li><label for="q_mchntSt">商户状态:</label><select id="q_mchntSt" ctype="MCHNT_ST" comparison="eq" ftype="list" name="mchntSt" multiple="multiple" ></select></li>
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
		<div class="form-group" id="mhtinf-mchntCode-div" style="display: none;">
			<label class="col-sm-2 control-label">内部商户号:</label>
			<div class="col-sm-5">
				<input id="ins-mchntCode" type="text" name="mchntCode" class="form-control" readonly="readonly" />
			</div>
			<div class="col-sm-5"><div id="ins-mchntCdTip"></div></div>
		</div>
		<div class="form-group">
			<label for="ins-mchntCd" class="col-sm-2 control-label"><font color="red">*</font>银联商户号:</label>
			<div class="col-sm-5">
				<input id="ins-mchntCd" type="text" name="mchntCd" class="form-control" placeholder="请填写银联商户号"/>
			</div>
			<div class="col-sm-5"><div id="ins-mchntCdTip"></div></div>
		</div>
		<div class="form-group">
			<label for="ins-licNo" class="col-sm-2 control-label"><font color="red">*</font>营业执照号码:</label>
			<div class="col-sm-5">
				<input id="ins-licNo" type="text" name="licNo" class="form-control" placeholder="请填写营业执照号码" />
			</div>
			<div class="col-sm-5"><div id="ins-licNoTip"></div></div>
		</div>
		<div class="form-group">
			<label for="ins-artifCertifId" class="col-sm-2 control-label">法人证件号:</label>
			<div class="col-sm-5">
				<input id="ins-artifCertifId" type="text" name="artifCertifId" class="form-control" placeholder="请填写法人证件号" />
			</div>
			<div class="col-sm-5"><div id="ins-artifCertifIdTip"></div></div>
		</div>
	</form>
</div>
<!-- 查询 -->
<div id="viewOper">
	<ul>
		<li><a href="#mchntOper" id="mchnt_tab">商户信息</a></li>
		<li><a href="#riskInfoOper" id="risk_tab">风控数据信息</a></li>
		<li><a href="#beforeLoanOper" id="be_loan_tab">贷前统计信息</a></li>
		<li><a href="#afterLoanOper" id="af_loan_tab">贷后数据信息</a></li>
	</ul>
<!-- 商户信息 -->
<div id="mchntOper" style="display: none;">
<form id="mchntInfoForm" class="form-horizontal" role="form">
	<div class="form-group">
		<label for="mchntCode" class="col-sm-2 control-label">内部商户号:</label>
		<div class="col-sm-4">
			<input id="mchntCode" type="text" name="mchntCode" readonly="readonly" class="form-control" />
		</div>
		<label class="col-sm-2 control-label">提交用户:</label>
		<div class="col-sm-4">
			<input type="text" name="recAcc" readonly="readonly" class="form-control"  />
		</div>
	</div>
	<div class="form-group">
		<label for="mchntCnName" class="col-sm-2 control-label" >商户名称:</label>
		<div class="col-sm-10">
			<input type="text" name="mchntCnName" readonly="readonly" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label for="regAddr" class="col-sm-2 control-label">注册地址:</label>
		<div class="col-sm-10">
			<textarea type="text" name="regAddr" readonly="readonly" class="form-control" ></textarea>
		</div>
	</div>
	<div class="form-group">
		<label for="phone" class="col-sm-2 control-label">联系电话:</label>
		<div class="col-sm-4">
			<input type="text" name="phone" readonly="readonly" class="form-control"/>
		</div>
<!-- 		<label for="mchntStDesc" class="col-sm-2 control-label">商户状态:</label>
		<div class="col-sm-4">
			<input type="text" name="mchntStDesc" readonly="readonly" class="form-control"/>
		</div> -->
	</div>
	<div class="form-group">
		<label for="mchntStDesc" class="col-sm-2 control-label">商户状态:</label>
		<div class="col-sm-4">
			<input type="text" name="mchntStDesc" readonly="readonly" class="form-control"/>
		</div>
		<label class="col-sm-2 control-label">查询响应:</label>
		<div class="col-sm-4">
			<input type="text" name="respInfo" readonly="readonly" class="form-control"/>
		</div>
	</div>
	
	<div class="form-group">
		<label for="mchntTpDesc" class="col-sm-2 control-label">商户类型:</label>
		<div class="col-sm-4">
			<input type="text" name="mchntTpDesc" readonly="readonly" class="form-control" />
		</div>
		<label for="mchntGrpDesc" class="col-sm-2 control-label">商户组别:</label>
		<div class="col-sm-4">
			<input type="text" name="mchntGrpDesc" readonly="readonly" class="form-control" />
		</div>	
	</div>
	
	<div class="form-group">
		<label for="licNo" class="col-sm-2 control-label">营业执照号码:</label>
		<div class="col-sm-4">
			<input type="text" name="licNo" class="form-control" readonly="readonly"/>
		</div>
		<label for="mchntCd" class="col-sm-2 control-label">银联商户号:</label>
		<div class="col-sm-4">
			<input type="text" name="mchntCd" readonly="readonly" class="form-control"/>
		</div>
	</div>
	
	<div class="form-group">
		<label for="mchntCnName" class="col-sm-2 control-label">法人姓名:</label>
		<div class="col-sm-4">
			<input type="text" name="artifNm" readonly="readonly" class="form-control"/>
		</div>
		<label for="artifCertifId" class="col-sm-2 control-label">法人证件号:</label>
		<div class="col-sm-4">
			<input type="text" name="artifCertifId" class="form-control" readonly="readonly"/>
		</div>
	</div>
		
	<div class="form-group">
		<label for="posNum" class="col-sm-2 control-label">装机数量:</label>
		<div class="col-sm-4">
			<input type="text" name="posNum" readonly="readonly" class="form-control"/>
		</div>
		<label for="mchntCrtDt" class="col-sm-2 control-label">入网时间:</label>
		<div class="col-sm-4">
			<input type="text" name="mchntCrtDt" readonly="readonly" class="form-control"/>
		</div>	
	</div>
	
	<div class="form-group">
		<label for="acqInsIdCd" class="col-sm-2 control-label">收单机构代码:</label>
		<div class="col-sm-4">
			<input type="text" name="acqInsIdCd" readonly="readonly" class="form-control"/>
		</div>
		<label for="reveRegCd" class="col-sm-2 control-label">税务登记代码:</label>
		<div class="col-sm-4">
			<input type="text" name="reveRegCd" readonly="readonly" class="form-control"/>
		</div>
	</div>
	
	<div class="form-group">
		<label for="remark" class="col-sm-2 control-label">备注:</label>
		<div class="col-sm-10">
			<textarea type="text" name="remark" readonly="readonly" class="form-control"></textarea>
		</div>
	</div>
</form>
</div>
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
</div>

<!-- 提交商户资质审核 -->
<div id="auditView" style="display: none;">
	<form action="#" id="sendAduitForm" class="form-horizontal" role="form">
	<input id="medSeq" type="hidden" name="medSeq" />
	<input type="hidden" name="mchntCode" />
	<div class="form-group">	
		<label class="col-sm-2 control-label">银联商户号:</label>
		<div class="col-sm-5">
			<input type="text" name="mchntCd" readonly="readonly" class="form-control"/>
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
     		</span>
		</div>
	</div>
	</form>
</div>

<script type="text/javascript">
<!--
// url
var url = {
grid: 'findMchnt.action',
excel:'findMchntExcel.action',
single:'findMchntbyMchntCode.action',
add: 'addMchnt.action',	   		// 添加商户
mchntinfo: 'mchntInfo.action', 	//
riskdata:  'riskData.action',   //
sendaudit: 'sendAudit.action',
transdata: 'fetchTransdata.action',
transafter: 'fetchTransdataAfter.action',
uploadFile: 'uploadAuditZipFile.action' 
};

var frameCtx=function(str){
	str = str.replace(/<\/?[^>]*>/g,''); //去除HTML tag
	str.value = str.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
	//str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
	return str;
};
var loadFile = function() {
	$.jyajax.hideCover();
	var data= $('#fileFrame').contents().find("body").html();
	// alert(data);
	if(data != ""){
		data = frameCtx(data);
		// alert(data);
		var dataObj = JSON.parse(data);
		if(dataObj) {
			if(dataObj.success && dataObj.root.length == 1){
				var medpath = dataObj.root[0].medPath;
				var medSeq = dataObj.root[0].medSeq;
				console.log(medpath);
				alert('上传文件成功');
				$('#med-filename').val('/'+medpath.trim());
				$('#medSeq').val(medSeq);
			} else {
				alert('上传文件失败' + dataObj.syserr);
			}
		}
	}
} ;
var fileName = function(tValue){ 
	if ( tValue ) {
		var t1 = tValue.lastIndexOf("\\");  
		var t2 = tValue.length;  
		if(t1 >= 0 ){
			if (t1 < t2 ){  
				$("#zipFileName").val(tValue.substring(t1 + 1));  
			}
		} else {
			$("#zipFileName").val(tValue);
		}
	}
}
// fields definition
$(function() {
	$('#viewOper').tabs();
	$('#viewOper').dialog({ title:'商户综合信息',autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 850, height : 570 
		,buttons:{
			// <sec:authorize ifAllGranted ="ROLE_MEM_CUP_QRYR" >	
			 '获取风险信息': function() { 
				 $.ajaxForm({formId: 'mchntInfoForm', url: url.riskdata,
						success : function(data) { 
							if (data.success) { 
								alert('操作成功');  $('#risk_tab').click();
								var qobj= new Object(); qobj.mchntCode=$('#mchntCode').val();
								$.ajax({ url:'findMhtRiskDatabyMchntCode.action',type:'POST',async:true,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#riskInfoGrid').refreshWithData(res); }});	
								$.queryTable({formId : 'queryForm', tableId : 'table'});
							} else { 
								alert('操作失败 原因是 '+ data.syserr); 
							}
						}
					});
			}, // </sec:authorize>
			// <sec:authorize ifAllGranted ="ROLE_MEM_CUP_QRYB" >
			'获取贷前数据': function() {
				$.ajaxForm({formId: 'mchntInfoForm', url: url.transdata,
					success : function(data) { 
						if (data.success) { 
							alert('操作成功');  $('#be_loan_tab').click();
							var qobj= new Object(); qobj.mchntCode=$('#mchntCode').val();
							$.ajax({ url:'findMhtHisTranbyMchntCode.action',type:'POST',async:true,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#beforeLoanGrid').refreshWithData(res); }});
						} else { 
							alert('操作失败 原因是 '+ data.syserr); 
						}
					}
				});
			}, // </sec:authorize>
			'关闭' : function() { $(this).dialog('close'); }
		}
	});
	$('#viewOper').fullScreen();
	
	$.formValidator.initConfig({ onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
	$("#ins-licNo").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" });
	$("#ins-mchntCd").formValidator({ empty : false, onshow : "请输入内容", oncorrect : "√" });
	$('#addView').dialog({
		autoOpen: false, title:'获取商户信息', 
		buttons:[
		{text:'获取商户信息', click:function() {
			if ($.formValidator.pageIsValid('1')) {
				$.ajaxForm({formId: 'newMchntInfoForm', url: url.mchntinfo,
					success : function(data) { 
						if (data.success) { 
							alert('操作成功'); 
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
	
	// 直接上传
	$('#uploadFile').click(function() { $('input[id=zipFile]').click(); });
	$('#zipFile').change(function() { 
		fileName($(this).val());
		$.jyajax.showCover();
		$("#uploadForm")[0].submit();
		
	});
	
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
				data:{jsonObject: $.toJSON(obj), medSeq:$('#medSeq').val()},
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
			$('#addView').fullScreen().dialog('open');
		} else {
			$('#newMchntInfoForm')[0].reset();
			$('#newMchntInfoForm').fillObject({obj:rec});
			$('#mhtinf-mchntCode-div').show();
			$('#addView').fullScreen().dialog('open');
		}
	};
	var auditMchnt = function(rec,id) {
		if (!rec) { alert("请选择一条记录"); return; }
		$('#sendAduitForm')[0].reset();
		$('#sendAduitForm').fillObject({obj:rec});
		$('#auditView').fullScreen().dialog('open');
	};
	
	var view=function(rec,id){
		if (!rec) { alert("请选择一条记录"); return; }
		
		$('#mchntInfoForm').fillObject({obj:rec});
		// $('#mchntInfoForm :input').attr('disabled','disabled');
		var qobj=new Object();
		qobj.mchntCode=rec.mchntCode;
		$('#viewOper').dialog('open');
		$('#mchnt_tab').click();
		$.ajax({ url:'findMhtHisTranbyMchntCode.action',type:'POST',async:true,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#beforeLoanGrid').refreshWithData(res); }});
		$.ajax({ url:'findMhtRiskDatabyMchntCode.action',type:'POST',async:true,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#riskInfoGrid').refreshWithData(res); }});	
		$.ajax({ url:'findMhtTransAfterLoanbyMchntCode.action',type:'POST',async:true,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#afterLoanGrid').refreshWithData(res); }});
	};
	
	var check=function(rec,id){
		if (!rec) { alert("请选择一条记录"); return; }
		if(rec.mchntSt == '1'){alert("该商户已通过内部初审");return;}
		if(!confirm("本次操作将使商户通过内部初审,确定操作吗?")){return;}
		var obj = new Object();
		obj.version = rec.version;
		obj.mchntCode = rec.mchntCode;
		obj.mchntSt = '1';
		$.ajax({type:'post',url:'doCheckMchnt.action',data : {jsonObject : $.toJSON(obj)}, modal:true,
			success : function(data){
				var res = $.parseJSON(data);
				if (res.success) {
					alert('操作成功');$('#table').flexModifyData(res.root[0]);
				} else {
					alert('操作失败 原因是' + res.syserr);
				}
			}});
	};
	
	var fetchdata = function(){
		$.ajax({type:'post',url: url.transafter, data: {}, dataType:'json', modal:true, 
			success : function(data){
				if (data.success) {
					alert('操作成功');
				} else {
					alert('操作失败 原因是' + data.syserr);
				}
			}});
	};
	// datagrid
	$('#table').flexigrid({
		url : url.grid, sort : "mchntCode", checkbox: false, usepager: true,dbClickRecord:view,
		buttons : [
		 { name: "详细信息",bclass: 'grid_view',id: 'cel', onpress: view, aclass:'ui-button-primary'}
		,{ name: "获取商户信息",show:'ROLE_MEM_CUP_QRY', bclass:'grid_add', id:'add',  onpress: addnew, aclass:'ui-button-success' } 
		,{ name: "提交资质审核",show:'ROLE_MEM_CUP_AUD', bclass:'grid_view', id:'send',  onpress: auditMchnt, aclass:'ui-button-success' } 
		,{ name: "获取贷后数据",show:'ROLE_MEM_CUP_QRYA', bclass:'grid_view', id:'after',  onpress: fetchdata, aclass:'ui-button-success' } 
		//,{ name: "审核",bclass: 'grid_edit',id: 'cel',onpress: check, aclass:'ui-button-primary'}
		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
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
			        ,{ display: '商户状态', dataIndex: 'mchntStDesc', width:120, align: 'center', sortable:true }
			        ,{ display: '查询响应', dataIndex: 'respInfo', width:120, align: 'center', sortable:true }
			        ,{ display: '提交用户', dataIndex: 'recAcc', width:80, align: 'center', sortable:true }
			        ,{ display: '录入时间', dataIndex: 'recTime', width:120, align: 'center', sortable:true , render: fmtTime}
			        ,{ display: '提交资质审核时间', dataIndex: 'auditTime', width:120, align: 'center', sortable:true, render: fmtTime }
			        ,{ display: '获取贷前数据时间', dataIndex: 'beforeTime', width:120, align: 'center', sortable:true, render: fmtTime }
		]
	});
	
	$('#beforeLoanGrid').flexigrid({
		url : 'findMhtHisTranbyMchntCode.action', sort : "hisTransMonth", usepager : true, height: 250,
		buttons : [{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#beforeLoanGrid').flexFile("findMhtHisTranbyMchntCodeExcel.action?jsonObject={\"mchntCode\":"+$('#mchntCode').val()+"}"); }}],
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
		url : 'findMhtRiskDatabyMchntCode.action', sort : "recTime", usepager : true, height: 250,
		buttons : [{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#riskInfoGrid').flexFile("findMhtRiskDatabyMchntCodeExcel.action?jsonObject={\"mchntCode\":"+$('#mchntCode').val()+"}"); }}],
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
		url : 'findMhtTransAfterLoanbyMchntCode.action', sort : "transDt", usepager : true, height: 250,
		buttons : [{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#afterLoanGrid').flexFile("findMhtTransAfterLoanbyMchntCodeExcel.action?jsonObject={\"mchntCode\":"+$('#mchntCode').val()+"}"); }}],
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
	// buttons
	$('#queryBtn').click(function(){$.queryTable({formId : 'queryForm', tableId : 'table'});});
	$('.qry-ul input[type="button"]').addClass('ui-button-primary');
	$('.bt').addClass('ui-btn-sm').button();
	$('#queryForm .qry-ul input[type="button"]').click();
	
});
//-->
</script>

<!-- fileupload -->
<div style="display: none">
	<form action="uploadAuditZipFile.action" id="uploadForm" target="fileFrame" enctype="multipart/form-data" method="post" >
		<input id="zipFile" type="file" name="uploadFile" style="display:none" accept="application/zip" />
		<input type="text" id="zipFileName" name="uploadFileName"/>
		<input type="hidden" value="ajaxUpload" name="ajaxUpload"/>
	</form>
	<iframe id="fileFrame" name="fileFrame" onload="loadFile()" ></iframe>
</div>
</body>
</html>