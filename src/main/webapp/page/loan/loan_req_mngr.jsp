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
		<ul class="qry-ul">
		<li><label for="q_cmchntCode">内部商户号:</label>
		<input type="text" id="q_mchntCode" maxlength="20" comparison="eq" name="mchntCode"/></li>

		<li><label for="q_ArtifNm">法人代表姓名:</label><input type="text" id="q_ArtifNm" comparison="eq" name="ArtifNm" ></input></li>
		
		<li><label for="q_loanId">贷款编号:</label><input type="text" id="q_loanId" comparison="eq" name="loanId" ></input></li>
		
		<li><label for="q_loanSt">贷款状态:</label><select id="q_loanSt" ctype="LOAN_ST" comparison="like" name="loanSt" ></select></li>
		
		<li><input class='bt' id="queryBtn" value="查询" type="button"/></li>
		
		<li><input class='bt' value="重置" type="reset" /></li>
</ul>
	</form>
</div>
<!-- 列表 -->
<div id="table"></div>
<!-- 贷款申请 -->
<div id="addForm">
<form id="addForm1" action="doUpdateLoan.action" >
			<ul class='frm-ul'>
				<li><label for="u_loanSt">贷款状态:</label><select id="u_loanSt"
					ctype="LOAN_ST" name="loanSt">
				</select></li>
				<li><label for="u_remark">备注</label><textarea type="textarea" id="u_remark" name="remark"></textarea></li>
			</ul>
		</form>
</div>
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
grid: 'findLoan.action',
excel:'findLoanExcel.action'
};
// fields definition
$(function() {
	$('#viewOper').tabs();
	$('#viewOper').dialog({ title:'综合信息',autoOpen : false, bgiframe : true, modal : false, resizable : false, draggable : true, width : 850, height : 570 });
	$('#viewOper').fullScreen();

	$('#addForm').dialog({title:'贷款申请审核',autoOpen: false, bgiframe: true, modal: true, resizable: false, draggable: true, width: 650, height: 350 });
	/*=================审核 ===============*/
	var upd = function(rec,id){
		if (!rec) { alert("请选择一条记录"); return; }
		$('#u_remark').val(rec.remark);
		$('#u_loanSt').val(rec.loanSt);
		$('#addForm').dialog("option","buttons",{
			'确定': function() {
				var obj = new Object();
				obj.version = rec.version;
				obj.loanId = rec.loanId;
				obj.loanSt=$('#u_loanSt').val();
				obj.remark=$('#u_remark').val();
				$.ajax({type:'post',url:'doUpdateLoan.action',data : {jsonObject : $.toJSON(obj)},
					success : function(data){
						var res = $.parseJSON(data);
						if (res.success) {alert('操作成功');$('#table').flexModifyData(res.root[0]);
						} else {alert('操作失败 原因是' + res.syserr);}
						$('#addForm').dialog('close');
					}});
				
			}, '关闭' : function() { $(this).dialog('close'); }
			});
			$('#addForm').dialog('open');
	}
	var view=function(rec,id){
		if (!rec) { alert("请选择一条记录"); return; }
		$('#viewOper').dialog("option", "buttons", {
			'关闭' : function() {
				$(this).dialog('close');
			}
		});
		var qobj=new Object();
		qobj.mchntCode=rec.mchntCode;	
		$.ajax({ url:'findMchntbyMchntCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) {$('#mchntInfoForm').fillObject({obj:res.root[0]});
		$('#mchntInfoForm :input').attr('disabled','disabled');} }});
		$.ajax({ url:'findMhtHisTranbyMchntCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#beforeLoanGrid').refreshWithData(res); }});
		$.ajax({ url:'findMhtRiskDatabyMchntCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#riskInfoGrid').refreshWithData(res); }});	
		$.ajax({ url:'findMhtTransAfterLoanbyMchntCode.action',type:'POST',async:false,data:{jsonObject:$.toJSON(qobj)}, success:function(data){var res=$.parseJSON(data);if(res.success) $('#afterLoanGrid').refreshWithData(res); }});
		$('#viewOper').dialog('open');
	}
	// datagrid
	$('#table').flexigrid({
		url : 'findLoan.action', sort : "loanId", usepager : true,
		buttons : [
				{ name: "详细信息",bclass: 'grid_view',id: 'cel',onpress: view, aclass:'ui-button-primary'}
		   		,{ name: "申请审核",show:'ROLE_LOAN_LOAN_REQ_CHECK',bclass: 'grid_view',id: 'cel',onpress: upd, aclass:'ui-button-primary'}
		   		,{ name: "发起贷款申请",bclass: 'grid_view',id: 'cel',aclass:'ui-button-primary'}
		   		,{name: "导出Excel", bclass: 'grid_excel', onpress:function() {$('#table').flexFile(url.excel); }}
		   		],
		colModel : [
					{ display: '内部商户号', dataIndex: 'mchntCode', width:100, align: 'center',sortable:true }
					,{ display: '营业执照号码', dataIndex: 'licNo', width:100, align: 'center',sortable:true }
					,{ display: '税务登记代码', dataIndex: 'reveRegCd', width:100, align: 'center',sortable:true }
					,{ display: '法人代表姓名', dataIndex: 'artifNm', width:100, align: 'center',sortable:true }
		            ,{ display: '贷款编号', dataIndex: 'loanId', width:100, align: 'center',sortable:true }
		            ,{ display: '贷款金额', dataIndex: 'loanAt', width:100, align: 'center',sortable:true,render:fmtMoney }
			        ,{ display: '贷款周期', dataIndex: 'loanCycle', width:100, align: 'center',sortable:true }
			        ,{ display: '贷款利率', dataIndex: 'loanrt', width:120, align: 'center',sortable:true,render:fmtRate }
			        ,{ display: '放款成功时间', dataIndex: 'loanSuccTm', width:120, align: 'center',sortable:true, render: fmtTime }			        
			        ,{ display: '贷款余额', dataIndex: 'loanBalance', width:120, align: 'center',sortable:true,render:fmtMoney }
			        ,{ display: '贷款状态', dataIndex: 'loanStDesc', width:120, align: 'center',sortable:true }
			        ,{ display: '备注', dataIndex: 'remark', width:120, align: 'center',sortable:true }
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