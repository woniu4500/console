<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="lang/globalMessages" scope="application"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-store, must-revalidate"> 
<meta http-equiv="expires" content="0">
<title>弘昊金融管理系统</title>
<link rel="shortcut icon" href="r/img/icon/credit.ico"  type="image/x-icon" />
<jsp:include page="/page/common/linksheet.jsp" />
<script type="text/javascript" src="r/js/jiuyv/mainframe.js"></script>
<style> 
<!--
html,body{width:100%;height:100%;background-color: #FCFCFC;}
body,h1,h2,h3,div,span,a,input,textarea,p,label,tr,td,table,ul,li,ol{ font-family: 'Microsoft Yahei','SimHei','STHeiti Light',verdana; }
body { color: #222222; margin: 0; padding: 0; }
ul,li,ol{list-style: none; padding: 0;margin: 0;}
a,a:HOVER{text-decoration: none;color: #222222;}
a:VISITED{color: #222222;}

.ico-user,.ico-history,.ico-mail,.ico-logout{width: 20px;height: 20px;display: block;overflow: hidden;}
.ico-user{background:url("r/img/icon/main-icon-s.png") 0 0;}
.ico-history{background:url("r/img/icon/main-icon-s.png") -20px 0;}
.ico-mail{background:url("r/img/icon/main-icon-s.png") 40px 0;}
.ico-logout{background:url("r/img/icon/main-icon-s.png") 20px 0;}

.ico-user:HOVER{background:url("r/img/icon/main-icon-s.png") 0 20px;}
.ico-history:HOVER{background:url("r/img/icon/main-icon-s.png") -20px 20px;}
.ico-mail:HOVER{background:url("r/img/icon/main-icon-s.png") 40px 20px;}
.ico-logout:HOVER{background:url("r/img/icon/main-icon-s.png") 20px 20px;}

.ico-refresh,.ico-close{width: 16px; height:16px; display: block;overflow: hidden;}
.ico-refresh{background: url("r/img/icon/ctrl-icon.png") 0 0;}
.ico-close{background: url("r/img/icon/ctrl-icon.png") -16px 0;}
.ico-refresh:HOVER{background: url("r/img/icon/ctrl-icon.png") 0 16px;}
.ico-close:HOVER{background: url("r/img/icon/ctrl-icon.png") -16px 16px;}

#main-window{background-color: #ECECEC;}
#top-band{z-index: 10;}
#body-band{z-index: 1;}
/* #top-logo{background-image: url("r/img/bg/bg-line.png"); background-repeat: repeat-y; background-position: 245px 0;} */
#body-menu{background: #515151;}
#top-info,#body-view{background-color: #FCFCFC;} 
#top-info{background-color: #EEEEEE}

#top-info-title,#top-info-ctrl{position: absolute;top: 0;height:100%; overflow: hidden;}
#top-info-title{left: 0;  }
#top-info-title h2{display: block; float:left; padding-left:40px; color: #444444; letter-spacing: 4px; cursor: default; font-weight: normal; margin-top:25px; font-size: 22px; text-shadow: 0 1px 0 #D3D3D3;}
#top-info-title ul { overflow: auto; display: block; float: left; margin-top:32px;}
#top-info-title ul li {display: block;float:left; margin-left: 4px; }

#top-info-ctrl{right: 0; width: 160px;}
#top-info-ctrl ul{overflow: auto;height:100%;line-height: 100%; }
#top-info-ctrl ul li {display: block;float: left;margin:32px 10px 0 8px;}
#top-info-ctrl ul li a{}

.arrow-outer {position:absolute;height:22px;width:60px;overflow:hidden; background: none;}
.arrow-shadow {
-webkit-box-shadow:0 0 10px 0 #AAAAAA;
-moz-box-shadow: 0 0 10px 0 #AAAAAA;
box-shadow: 0 0 10px #AAAAAA;
filter: progid:DXImageTransform.Microsoft.Shadow(Strength=10, Direction=90, Color='#AAAAAA');
transform:rotate(45deg);
-ms-transform:rotate(45deg); 	/* IE 9 */
-moz-transform:rotate(45deg); 	/* Firefox */
-webkit-transform:rotate(45deg); /* Safari 和 Chrome */
-o-transform:rotate(45deg); 	/* Opera */
background:none repeat scroll 0 0 #FCFCFC;height:40px;left:15px;position:absolute;top:16px;width:40px;}

#open-list-container {width: 240px; position: absolute; top: 60px; right: 120px; z-index: 101;}
#open-list-box{background-color: #FCFCFC; padding: 4px; border: 1px solid #cccccc; border-radius:3px; border-top-left-radius:0; border-top-right-radius:0; top: 16px; position: relative;}
#open-list-container ul{width: 100%; max-height: 200px;overflow: auto;}
#open-list-container ul li {height: 30px; line-height: 30px; text-indent: 20px;}
#open-list-container ul li:HOVER{background-color: #428BCA; color:#fff; border-radius:3px;}
#open-list-container ul li:HOVER a{color:#fff; text-decoration: none; cursor: pointer;}

#user-card {width:240px; position: absolute;  top: 60px; right: 20px; z-index: 101;}
#user-card-box{background-color: #FCFCFC; padding: 4px; border: 1px solid #cccccc; border-radius:3px; border-top-left-radius:0; border-top-right-radius:0; top: 16px; position: relative;}
#user-card h2{margin: 8px 0; font-size: 14px; text-indent: 14px;}
#user-card ul li {height: 30px; line-height: 30px; text-indent: 14px;}
#user-card ul li {display: block; margin-left: 8px; margin-top: 4px; font-size: 12px; width:100%; line-height: 20px; height:20px; color: #222222; }
#user-card .oper-bottom{ background-color:#f5f5f5; text-align: center; border-top: 1px solid #e6e6e6;}
#user-card .oper-bottom a{ color: #222222; padding: 2px 6px; margin: 8px 0 8px; display: inline-block;}
#user-card .oper-bottom a:hover{background-color:#e6e6e6; border-radius:3px; }
#body-menu-head,#body-menu-list-container,#body-menu-bottom{width: 100%;}
#body-menu-head{height: 40px; line-height:40px; font-size: 17px; font-style: italic;color: #1D72AE; text-shadow: 0 0 1px #69A9E9 ; font-weight: 700; text-align: center; vertical-align: middle; font-stretch: narrower; }
#body-menu-list-container{position: relative;  overflow: auto; }
#body-menu-list-container #body-menu-list{display:block; z-index:10;width: 100%; overflow: auto; background:none; padding:0; margin: 0; }
 
#body-menu-bottom{height: 30px; text-align: center ;}
#body-menu-bottom hr{width: 80%;margin: auto;border-top:1px solid #D4D4D4;border-bottom:1px solid #FAFAFA; border-left: 0;border-right: 0;}
#body-menu-bottom ul{margin-top: 4px; text-align: left; width: 80%;margin-left: auto;margin-right: auto;}
#body-menu-bottom ul li{font-size: 12px; color: #fff;}

#body-view{border-top: 1px solid #DCDCDC; background:url("r/img/bg/bg.png") no-repeat scroll 50% 80% #FCFCFC; overflow: hidden; }
#body-view iframe{border:0;}
#body-view #indexpage{padding: 12px;}
#head-tips{position: absolute; width: 100%; height:0; top: 0 ; left: 0; z-index: 999;}
#head-tips-ul{margin: auto; display: block; width: 300px;  margin-top: 5px;}
#head-tips-ul li {color: #6a6a6a ;width: 100%;  margin: 4px 12px ; display: block; border: 1px solid #C5DBEC; border-radius: 3px; background-image: url("r/img/bg/tab-default.png"); background-color: #ddeaf7; background-position:0 0; background-repeat: repeat-x; }
#head-tips-ul li h2 {display: block; width: 256px; height: 18px;line-height:18px; overflow:hidden; white-space:nowrap;text-overflow:ellipsis;padding: 2px 8px; margin: 0; font-size: 14px; font-weight: bold;} 
#head-tips-ul li span {display:inline-block;font-size: 12px; font-style: italic;margin: 2px 8px; }

#indexpage h2{color:#333;font-size:16px;font-weight:normal;padding:30px 0 0 50px;}
#indexpage h4,#maila,#maila span{color:#333;font-size:14px;font-weight:normal;}
#indexpage h4{padding:10px 0 0 50px;}
#indexpage h4 a{color:#003399;padding:4px 10px;}
#indexpage h4 a:hover{background:#3366cc;color:#fff;border-radius:4px;}
#indexpage h4 a span{color:#CC0000;font-weight:700}

.style-indexpage,
.style-role_sec_mngr,
.style-role_member_mngr,
.style-role_loan_req_mngr,
.style-role_info_mngr,
.style-role_sysconf_mngr,
.style-role_mht_mngr,
.style-menu {display:block; width:18px;height: 18px; overflow: hidden; background: url('r/img/icon/icon.png') repeat scroll; position: absolute; left: 12px; top: 10px; z-index: 99;}


.style-indexpage{background-position: -31px -15px;}
.active > .style-indexpage{background-position: -71px -15px;}
.style-role_sec_mngr{background-position: -31px -54px;}
.active > .style-role_sec_mngr{background-position: -73px -54px;}
.style-role_member_mngr{background-position: -31px -94px;}
.active > .style-role_member_mngr{background-position: -73px -94px;}
.style-role_loan_req_mngr{background-position: -31px -135px;}
.active > .style-role_loan_req_mngr{background-position: -73px -135px;}
.style-role_info_mngr{background-position: -31px -178px;}
.active > .style-role_info_mngr{background-position: -73px -178px;}
.style-role_sysconf_mngr{background-position: -31px 29px}
.active > .style-role_sysconf_mngr{background-position: -73px 29px}
.style-menu{background-position: -125px -40px; left: 42px; }
.active > .style-menu{background-position: -125px -16px;  }

.style-role_mht_mngr{left: 14px;background: url('r/img/icon/store_18.png') repeat scroll; }
.active > .style-role_mht_mngr{left: 14px;background: url('r/img/icon/store_18.png') repeat scroll; background-position: 18px 0; }


.style-back{background-position: -125px -99px!important;display: block;
width: 18px;
height: 18px;
overflow: hidden;
position: absolute;
left: 200px;
top: 10px;
z-index: 99;
background: url('r/img/icon/icon.png') repeat scroll;}
.active > .style-back{background-position: -125px -73px!important;}

-->
</style>
<script type="text/javascript" language="javascript" for="document" event="onkeydown"> globalKeyEvent(); </script>
<script type="text/javascript">if ( self != top ) { top.location=self.location; }</script>
</head>
<body>
<div id="main-window" >
	<div id="top-band" class="jy-ui-row" fh=75 >
		<div id="top-logo" class="jy-ui-col" fw=250 >
			<img src="r/img/logo-hh.png" alt="弘昊金融" id="logo" style="margin-top:15px;margin-left:25px;" />
		</div>
		<div id="top-info" class="jy-ui-col">
			<div id="top-info-title" class="jy-ui-fixwidth">
				<h2></h2>
				<ul style="display: none;">
					<li><a href="javascript:void(0);" id="frame-refresh" class="ico-refresh" title="刷新页面">&nbsp;</a></li>
					<li><a href="javascript:void(0);" id="frame-close" class="ico-close" title="关闭页面">&nbsp;</a></li>
				</ul>
			</div>
			<div id="top-info-ctrl">
				<ul>
					<li><a href="javascript:void(0);" id="ctrl-history" class="ico-history" title="打开页面列表" >&nbsp;</a></li>
					<li><a href="javascript:void(0);" id="ctrl-mail" class="ico-mail"  role="role_sysconf_msgbox" title="用户消息">&nbsp;</a></li>
					<li><a href="javascript:void(0);" id="ctrl-userinfo" class="user-name ico-user" title="用户信息">&nbsp;</a></li>
					<li><a href="j_spring_security_logout" class="ico-logout" title="退出">&nbsp;</a></li>
				</ul>
			</div>
			<div id="user-card" class="" style="display: none;">
				<div class="arrow-outer" style="right:20px;top:-5px;z-index: 101;"><div class="arrow-shadow">&nbsp;</div></div>
				<div id="user-card-box">
				<h2>${userDetail.userName}</h2>
				<ul>
					<li>${userDetail.loginId}</li>
					<li>${userDetail.orgName}</li>
					<li>${userDetail.userEmail}</li>
					<li>${userDetail.userPhone}</li>
				</ul>
				<br />
				<div class="oper-bottom">
					<a class="btn" href="javascript:void(0);" id="updUserinfo" role="role_sec_user" nav="role_sec_mngr" rname="用户管理" >信息修改</a>&nbsp;&nbsp;
				<a class="btn" href="javascript:void(0);" id="chngPasswd" role="role_sec_user_passwdchage" nav="role_sec_mngr" rname="修改密码" ">修改密码</a>
				</div> 
				</div>
			</div>
			<div id="open-list-container" class="" style="display: none;">
				<div class="arrow-outer" style="right:-5px;top:-5px;z-index: 101;"><div class="arrow-shadow">&nbsp;</div></div>
				<div id="open-list-box">
				<ul id="open-list"></ul>
				</div>
			</div>
		</div>
	</div>
	<div id="body-band" class="jy-ui-row" >
		<div id="body-menu" class="jy-ui-col" fw=250 >
			<!-- <div id="body-menu-head">
			</div> -->
			<div id="body-menu-list-container" class="bs-sidebar jy-ui-fixheight">
				<ul id="body-menu-list" class="nav bs-sidenav"></ul>
			</div>
			<div id="body-menu-bottom">
				<ul>
					<li><span id="company"></span></li>
				</ul>
			</div>
		</div>
		<div id="body-view" class="jy-ui-col jy-ui-framebox" >
			<div id="indexpage">
				<h2><font style="text-transform:capitalize; font-size: 28px; ">${userDetail.userName}</font>, 欢迎使用<font id="systitle"></font>. </h2>
				<h4>未读邮件&nbsp;:&nbsp;<a href="javascript:void(0);" id="maila" role="role_sysconf_msgbox" nav="role_sysconf_mngr" rname="消息收件箱"  url="recvMsgSetup.action" >消息收件箱&nbsp;<span style="color:red;">0</span>&nbsp;封</a></h4>
			</div>
			<!-- 用户信息修改弹框 -->
			<div id="body-update-userinfo" style="display:none ;">
				<form id="form1" action="#">
					<input type="hidden" id="version" name="version" />
					<input type="hidden" id="userId" name="userId" />
					<ul class='frm-ul'>
						<li><label for="userName">用户姓名:</label><input name="userName" type="text" id="userName" maxLength="20" /><div id="userNameTip"></div></li>
						<li><label for="crdType">证件类型:</label><select id="crdType" name="crdType"></select><div id="crdTypeTip"></div></li>
						<li><label for="crdNo">证件号:</label><input name="crdNo" type="text" id="crdNo" maxLength="30"/><div id="crdNoTip"></div></li>
						<li><label for="userPhone">电话:</label><input name="userPhone" type="text" id="userPhone" maxLength="20"/><div id="userPhoneTip"></div></li>
						<li><label for="userEmail">E-MAIL:</label><input name="userEmail" type="text" id="userEmail" maxLength="50"/><div id="userEmailTip"></div></li>
					</ul>
				</form>
			</div>
			<!-- 用户密码修改弹框 -->
			<div id="body-update-userpw" style='display: none;'>
				<form id="form2">
					<ul class='frm-ul'>
						<li><label for="oldPasswd">原密码:</label><input name="oldPasswd" type="password" id="oldPasswd" /><div id="oldPasswdTip"></div></li>
						<li><label for="newPasswd">新密码:</label><input name="newPasswd" type="password" id="newPasswd" /><div id="newPasswdTip"></div></li>
						<li><label for="confirmPasswd">重复密码:</label><input name="confirmPasswd" type="password" id="confirmPasswd" /><div id="confirmPasswdTip"></div></li>
					</ul>
				</form>
			</div>
		</div>
		<div id="head-tips" ><ul id="head-tips-ul"></ul></div>
	</div>

</div>

<script type="text/javascript">
<!--
var buttonAuthors = '${buttonsAuthor}'.split(',');
var msgstore = new Array();
var _tipcnt = 0;
var call_interval = 60000;
var show_mill = 3000;
var msgtip = function(c, t) {
	var _id = 'tip-'+ (_tipcnt++);
	var $tip = $('<li />').append($('<span />').html(c)).css({display:'none'}).attr('id',_id);
	$('#head-tips-ul').append($tip);
	$tip.slideDown();
	setTimeout('hidemsg("'+_id+'")', t);
};
var hidemsg = function(i){ $('#' + i ).slideUp(); };
var bindhidediv = function(_id,_tgr){
	$(document).bind('click',function(e){
		var $target = $(e.target);
		var $show = $('#'+_id);
		if ( !$target.closest('#' + _id).length && !$target.closest('#' + _tgr).length) {
			if ( $show.css('display') != 'none') { $('#'+_id).hide(); }		
		}
	});
};
var msgtask = function() {
	//header-user-msg
	$.ajax({url:'userRecentMsg.action', dataType:'json', success: function(data){
		if (data.success) {
			//msgstore
			$(data.root).each(function(i,n) {
				if ( n && n.msgTitle) {
					var _idx = msgstore.indexOf(n.msgId);
					if ( _idx < 0 ) {
						msgtip('<h2>' + n.msgTitle + '</h2><span>' + fmtTime(n.sendTime) + ' by ' + n.sendAcct + '</span>', show_mill );
						msgstore.push(n.msgId);
					} 
				}
			});
			var $usermsg = $('#indexpage h4 a span');
			$usermsg.html(data.totalProperty);
			prmtTm = 0;
			setTimeout('msgtask()',call_interval);
		} else {
			if (prmtTm == 2) { prmtTm = 0; alert('系统连接超时，请重新登录. ');　window.location = 'login.jsp'; } else { prmtTm++; setTimeout('msgtask()',call_interval); }
		}
	}, error : function() {
		if (prmtTm == 2) { alert('系统连接异常，请重新登录. ');　window.location = 'login.jsp'; } else { prmtTm++; setTimeout('msgtask()',call_interval);}
	}});
};
/** 修改个人信息 */
var saveInfo = function() {
	if ($.formValidator.pageIsValid('1')) {
		$.ajaxForm({formId : 'form1', url : 'doUpdateSelfInfo.action',
			success : function(data) {
				if (data.success) { alert('操作成功'); $('#body-update-userinfo').dialog('close'); }
				else {alert('操作失败 原因是'+ data.syserr);}
			}
		});
	} else {return false;} 
};
/** 打开个人信息 */
var updateUserInfo = function() {
	$("#form1 div[id$=Tip]").empty(); $('#form1')[0].reset();
	var userObj = {userId: userId};
	$.ajax({type:'post', url:'findUserInfoById.action', data:{jsonObject:$.toJSON(userObj)}, async:true, dataType:'json', 
		success:function(data){ if(data.success){$.jyform.fillForm('form1',data.root[0]);} }});
	$('#body-update-userinfo').dialog('open');
};
/** 修改密码 */
var changePwd = function (){
	var oldpwd= $('#body-update-userpw input[name="oldPasswd"]').val();
	var newpwd= $('#body-update-userpw input[name="newPasswd"]').val();
	var pwdrep= $('#body-update-userpw input[name="confirmPasswd"]').val();
	if(newpwd==""||newpwd==""||oldpwd==""){ alert("密码不许为空"); return; }
	if(newpwd!=pwdrep){ alert("密码和确认密码输入不一致"); return; }
 	$.ajaxForm({formId : 'form2', url:'changePasswd.action', 
		success:function(data){
			if(data.success == true){ alert("密码修改成功，请注销后重新登录！"); $('#body-update-userpw input').val(''); $('#body-update-userpw').dialog('close');  }
			else { alert(data.syserr); } 
		}
	});
} ;

var logoImg = '<%=application.getAttribute("sys.theme.img.logo")%>';
logoImg = (logoImg=='null'||logoImg=='')?'r/img/bg/logo_def.png':logoImg;
var loginId = '${userDetail.loginId}';
var userId = '${userDetail.userId}';
var title = '<f:message key="application.title" />';
title=(title=='null'||title=='')?'<f:message key="application.title" />':title;
var company = '<%=application.getAttribute("sys.theme.company")%>';

$(function(){
	// set global info. 
	$('#systitle').html(title); 
	$('#body-menu-head').html(title);  
	document.title=title;
	$('#company').html(company);
	//$('#logo').attr({src:logoImg,alt:title});
	
	var m = new MainFrame({sid:'main-window', menuNode: ${resData} });
	$(window).resize(function(){ m.autosize(); });
	
	$('#maila').click(function() {m.openFrame($('#'+$(this).attr('role')));});
	$('#ctrl-mail').click(function() {m.openFrame($('#'+$(this).attr('role')));});
	$('#frame-refresh').click(function(){m.refreshFrame();});
	$('#frame-close').click(function(){m.closeFrame();});
	$('#ctrl-history').click(function() {$('#open-list-container').toggle();}); 
	$('#ctrl-userinfo').click(function() {  $('#user-card').toggle(); }); 
	
	bindhidediv('open-list-container','ctrl-history');
	bindhidediv('user-card','ctrl-userinfo');
	
	$.formValidator.initConfig({ formid:'form1',validatorgroup:'1',onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
	$("#userName").formValidator({ validatorgroup:'1',empty : false, oncorrect : "√" }).inputValidator({ min : 1, max: 20, onerror : "用户名最多20个字符" });
	$("#crdNo").formValidator({ validatorgroup:'1',empty : false, oncorrect : "√" }).inputValidator({ min : 1, max : 18, onerror : "证件号长度18位" });
	$("#userEmail") .formValidator({ validatorgroup:'1',empty : true, oncorrect : "√" }) .inputValidator({ min : 1, max : 50, onerror : "EMAIL最多50个字符" }).regexValidator({regexp:'email',datatype:'enum',onerror : "邮箱格式不正确" });
	$("#userPhone").formValidator({validatorgroup:'1', empty : true, oncorrect : "√" }).inputValidator({ min : 1, max: 20, onerror : "电话最多20个字符" }).regexValidator({regexp:'tel',datatype:'enum',onerror:'电话格式不正确'});
	$.formValidator.initConfig({ formid:'form2',validatorgroup:'2',onerror : function(msg,obj) { 	alert(msg); $(obj).focus();}});
	$("#oldPasswd").formValidator({validatorgroup:'2', empty : false, onshow : "", oncorrect : "√" }).inputValidator({ min : 1, max : 12, onerror : "密码最多12个字符" }).regexValidator({ regexp : "^[A-Za-z0-9]+$", onerror : "密码仅能输入数字或字母" });
	$("#confirmPasswd").formValidator({ validatorgroup:'2',empty : false, onshow : "", oncorrect : "√" }).regexValidator({ regexp : "^[A-Za-z0-9]+$", onerror : "仅能输入数字或字母" }).compareValidator({ desid : "newPasswd", operateor : "=", onerror : "2次密码不一致,请确认" });

	$('#body-update-userinfo').dialog({title: loginId + ' 个人信息', width: 400, height: 350, buttons:{'保存': saveInfo}, autoOpen: false, bgiframe: true, modal: true, resizable: false, draggable: false});
	$('#body-update-userpw').dialog({title:"修改密码", width:400,height:240, buttons:{'确定':changePwd},bgiframe:true,  autoOpen:false, draggable:false,resizable:false,modal:true});
	
	//$.jyform.frmSysCodeBox({selectId:'crdType',ctype:'FIN_BUYER_PASSPORT_TYPE'});
	$.jyform.syscodeBox({selectId:'crdType',ctype:'PUB_CERT_TYPE'});
	$('#updUserinfo').click(function(){updateUserInfo();});
	$('#chngPasswd').click(function(){ $('#form2')[0].reset(); $('#body-update-userpw').dialog('open');  });
	// 
	// $('#body-menu-list').accordion({ header:'> li',heightStyle: "fill", animate:false});
	// site msg task
	msgtask();
});

//-->
</script>
</body>
</html>