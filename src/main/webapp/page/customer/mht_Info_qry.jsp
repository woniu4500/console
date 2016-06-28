<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<li><label for="q_customerCode">会员代码:</label>
		<input type="text" id="q_customerCode" maxlength="20" comparison="eq" name="customerCode"/></li>

		<li><label for="q_mobile">手机号:</label><input type="text" id="q_mobile" comparison="eq" name="mobile" ></input></li>
		
		<li><label for="q_realName">姓名:</label><input type="text" id="q_realName" comparison="eq" name="realName" ></input></li>
		
		<li><label for="q_isActive">是否有效:</label><select id="q_isActive" ctype="PUB_VAILD" comparison="like" name="isActive" ></select></li>
		
		<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
		
		<li><input class='bt' value="重置" type="reset" /></li>
</ul>
	</form>
</div>
<!-- 列表 -->
<div id="table"></div>
<div id="viewOper">
	<ul>
		<li><a href="#mchntOper" id="mchnt_tab">商户信息</a></li>
		<li><a href="#beforeLoanOper" id="be_loan_tab">贷前数据信息</a></li>
		<li><a href="#riskInfoOper" id="risk_tab">风控数据信息</a></li>
		<li><a href="#afterLoanOper" id="af_loan_tab">贷后数据信息</a></li>
	</ul>
<!-- 商户信息 -->
<div id="mchntOper" style="display: none;">
<form id="mchntInfoForm">
<ul class='frm-ul'>
	<li><label for="mchntCode">内部商户号:</label>
	<input type="text" name="mchntCode" readonly="readonly" />
	<label for="customerCode">会员代码:</label>
	<input type="text" name="customerCode" readonly="readonly"></input></li>
	
	<li><label for="mchntCnName">法人姓名:</label>
	<input type="text" name="artifNm" readonly="readonly"/>
	<label for="artifCertifId">法人证件号:</label>
	<input class="long" type="text" name="artifCertifId" readonly="readonly"/></li>
	
	<li><label for="mchntTp">商户类型:</label>
	<input type="text" name="mchntTp" readonly="readonly"/>
	<label for="mchntCrtDt">入网时间:</label>
	<input type="text" name="mchntCrtDt" readonly="readonly"/></li>
	
	<li><label for="posNum">装机数量:</label>
	<input type="text" name="posNum" readonly="readonly"/>
	<label for="mchntStDesc">商户状态:</label>
	<input type="text" name="mchntStDesc" readonly="readonly"/></li>
	
	
	<li><label for="licNo">营业执照号码:</label>
	<input class="long" type="text" name="licNo" readonly="readonly"/></li>

	<li><label for="mchntCd">商户代码:</label>
	<input class="long" type="text" name="mchntCd" readonly="readonly" /></li>

	<li><label for="acqInsIdCd">收单机构代码:</label>
	<input class="long" type="text" name="acqInsIdCd" readonly="readonly"/></li>

	<li><label for="reveRegCd">税务登记代码:</label>
	<input class="long" type="text" name="reveRegCd" readonly="readonly"/></li>

	<li><label for="mchntCnName">商户中文名称:</label>
	<input class="long" type="text" name="mchntCnName" readonly="readonly"/></li>
	
	<li><label for="regAddr">注册地址:</label>
	<input class="long" type="text" name=regAddr readonly="readonly"/></li>
	
	<li><label for="phone">联系电话:</label>
	<input class="long" type="text" name="phone" readonly="readonly"/></li>
	
	<li><label for="remark">备注:</label>
	<textarea type="text" name="remark" readonly="readonly"></textarea></li>

</ul>
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
<script type="text/javascript">
<!--
// url
var url = {
grid: 'findMchnt.action',
excel:'findMchntExcel.action'
};
// fields definition
$(function() {
	$('#viewOper').tabs();
	$('#viewOper').dialog({ title:'综合信息',autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 850, height : 570 });
	$('#viewOper').fullScreen();
	
	var view=function(rec,id){
	if (!rec) { alert("请选择一条记录"); return; }
	$('#viewOper').dialog("option", "buttons", {
		'关闭' : function() {
			$(this).dialog('close');
		}
	});
	$('#mchntInfoForm').fillObject({obj:rec});
	$('#mchntInfoForm :input').attr('disabled','disabled');
	var qobj=new Object();
	qobj.mchntCode=rec.mchntCode;
	$.ajax({ url:'findMhtHisTranbyMchntCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#beforeLoanGrid').refreshWithData(res); }});
	$.ajax({ url:'findMhtRiskDatabyMchntCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#riskInfoGrid').refreshWithData(res); }});	
	$.ajax({ url:'findMhtTransAfterLoanbyMchntCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#afterLoanGrid').refreshWithData(res); }});
	$('#viewOper').dialog('open');
	}
	var check=function(rec,id){
		if (!rec) { alert("请选择一条记录"); return; }
		if(rec.mchntSt == '1'){alert("该商户已通过内部初审");return;}
		if(!confirm("本次操作将使商户通过内部初审,确定操作吗?")){return;}
		var obj = new Object();
		obj.version = rec.version;
		obj.mchntCode = rec.mchntCode;
		obj.mchntSt = '1';
		$.ajax({type:'post',url:'doCheckMchnt.action',data : {jsonObject : $.toJSON(obj)},
			success : function(data){
				var res = $.parseJSON(data);
				if (res.success) {alert('操作成功');$('#table').flexModifyData(res.root[0]);
				} else {alert('操作失败 原因是' + res.syserr);}
			}});
	};
	// datagrid
	$('#table').flexigrid({
		url : url.grid, sort : "customerCode", checkbox: false, usepager: true,dbClickRecord:view,
		buttons : [
		{ name: "详细信息",bclass: 'grid_view',id: 'cel',onpress: view, aclass:'ui-button-primary'}
		,{ name: "审核",bclass: 'grid_edit',id: 'cel',onpress: check, aclass:'ui-button-primary'}
		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		],
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
		]
	});
	
	$('#beforeLoanGrid').flexigrid({
		url : 'findMchntbyCustomerCode.action', sort : "mchntCode", usepager : true,
		colModel : [
		            { display: '内部商户号', dataIndex: 'mchntCode', width:100, align: 'center',sortable:true }
		            ,{ display: '统计月份', dataIndex: 'hisTransMonth', width:100, align: 'center',sortable:true }
			        ,{ display: '月交易额', dataIndex: 'monthTransAt', width:100, align: 'center',sortable:true,render:fmtMoney }
			        ,{ display: '月交易额同比', dataIndex: 'monthTransAtYear', width:120, align: 'center',sortable:true ,render:fmtRate}
			        ,{ display: '月交易笔数', dataIndex: 'monthTransNum', width:120, align: 'center',sortable:true }			        
			        ,{ display: '月消费客户数', dataIndex: 'monthCusNum', width:120, align: 'center',sortable:true }
			        ,{ display: '月重复消费客户数', dataIndex: 'monthRepeatCusNum', width:120, align: 'center',sortable:true }
			        ,{ display: '贷记卡与借记卡交易比', dataIndex: 'creditDebitRatio', width:120, align: 'center',sortable:true,render:fmtRate }
			        ,{ display: '备注', dataIndex: 'remark', width:120, align: 'center',sortable:true }
			        ,{ display: '记录创建时间', dataIndex: 'recTime', width:120, align: 'center',sortable:true, render: fmtTime }
		]
	});
	$('#riskInfoGrid').flexigrid({
		url : 'findMhtRiskDatabyMchntCode.action', sort : "loanId", usepager : true,
		colModel : [
		            { display: '内部商户号', dataIndex: 'mchntCode', width:100, align: 'center',sortable:true }
		            ,{ display: '近三个月欺诈交易总笔数', dataIndex: 'fraudTransNum', width:100, align: 'center',sortable:true}
			        ,{ display: '近三个月欺诈交易总金额', dataIndex: 'fraudTransAt', width:100, align: 'center',sortable:true,render:fmtMoney}
			        ,{ display: '可疑商户名单是否命中', dataIndex: 'susMchntInDesc', width:120, align: 'center',sortable:true}
			        ,{ display: '不良持卡人名单是否命中', dataIndex: 'negCdhdInDesc', width:120, align: 'center',sortable:true}			        
			        ,{ display: '备注', dataIndex: 'remark', width:120, align: 'center',sortable:true}
			        ,{ display: '创建时间', dataIndex: 'recTime', width:120, align: 'center',sortable:true, render: fmtTime  }
		]
	});
	$('#afterLoanGrid').flexigrid({
		url : 'findMhtTransAfterLoanbyMchntCode.action', sort : "loanId", usepager : true,
		colModel : [
		            { display: '交易日期', dataIndex: 'transDt', width:100, align: 'center',sortable:true,render:fmtDate }
			        ,{ display: '当日交易额', dataIndex: 'daliyTransAt', width:100, align: 'center',sortable:true,render:fmtMoney }
			        ,{ display: '当日交易笔数', dataIndex: 'daliyTransNum', width:120, align: 'center',sortable:true}
			        ,{ display: '当日消费客户数', dataIndex: 'daliyCusNum', width:120, align: 'center',sortable:true}			        
			        ,{ display: '当日重复消费客户数', dataIndex: 'daliyRepeatCusNum', width:120, align: 'center',sortable:true }
			        ,{ display: '贷记卡与借记卡交易比', dataIndex: 'creditDebitRatio', width:120, align: 'center',sortable:true,render:fmtRate }
			        ,{ display: '备注', dataIndex: 'remark', width:120, align: 'center',sortable:true }
			        ,{ display: '记录创建时间', dataIndex: 'recTime', width:120, align: 'center',sortable:true, render: fmtTime   }
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