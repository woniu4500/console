<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="lang/globalMessages" scope="application"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
	<title><f:message key="application.title" /></title>
	<link rel="shortcut icon" href="r/img/icon/credit.ico"  type="image/x-icon" />
	<script type="text/javascript" src="r/plugins/jquery/jquery-1.6.4.min.js"></script>
	<script type="text/javascript" src="r/plugins/jquery/qrcode/jquery.qrcode.min.js"></script>
	<script type="text/javascript" src="r/js/mngr-util-debug.js"></script>
<style>
<!--
body {padding: 0; margin: 0; font-family: "Microsoft YaHei", "NSimSun"; font-size: 14px;color:#333;}
ul li{list-style: none; margin: 0; padding: 0;}
#page_box{margin:30px auto;width:780px;}
#logo{width:150px;height:40px;margin:0 0 45px;/* background:transparent url(r/img/bg/logo.gif)no-repeat; */}
#login_info{width: 780px; height:280px; border: 2px solid #BDC3C7; position:absolute;/* left:22%;position:absolute;top:30%; */border-radius:6px;/* background: transparent url(r/img/bg/login_info_bg_1.gif) no-repeat ;  */}
#login_info #sys_title{font-size:20px;font-weight:bold;height:30px;width:300px;letter-spacing:5px;padding:40px 20px 40px 0px;color:#F78E02;}
#login_block {width: 240px; height:240px; position: absolute;left:65%;top:18px;background:#F5F4F9;border:1px solid #E7E7E7;border-radius:8px;}

/* #login_block {width: 240px; height:240px; position: absolute; left:80%; top:50%; margin-left: -200px; margin-top:-120px;} */
 #login_block ul {width: 180px; float:right; margin: 5px;}
#login_block ul li{line-height: 24px; margin: 5px 0 0 0 ;}
#login_block ul li .inbox{width: 150px;height:20px;}
#login_block ul li .inbox{ border: 1px solid #BDC3C7;border-radius:8px;}
#login_block ul li .inbox:focus{border: 1px solid #5085B7;}
#login_block ul li .captcha{width: 80px;}
#login_block ul li #safecode{display: inline-block;line-height: 24px; position: relative;top:5px; }
#login_block ul li.alert{color: red;}
#login_block ul li .bt{ background: transparent url(r/img/bg/button_bg.gif) repeat scroll 0 0; border: 1px solid #7DE7FD;margin-left:35px; width:80px; font-size:12px; color:#2E6E9E;font-weight: bold;border-radius: 4px;height: 25px;cursor: pointer;}
#login_block ul li .bt:HOVER{ background: transparent url(r/img/bg/button_bg2.gif) repeat scroll 0 0; border: 1px solid #C3A336; }
#auth_block{width: 100%; height: 90px; position: absolute; top: 100%; margin-top: -90px; background-color: #2A4473;}
#auth_block ul {margin: auto; width: 400px; margin-top: 0.8em; color: #FFF; font-size: 10px;}
#auth_block ul li{text-align: center;}

#login_method{width:45px;height:45px;position:absolute; right: 0;overflow:hidden;}
.computerImg{background:transparent url(r/img/bg/computer.gif) no-repeat;}
.qrcodeImg{background:transparent url(r/img/bg/qrcode.gif) no-repeat;}
#qrcode_login{width:150px;height:210px;padding:0 45px;}
#qrcode_login p{font-size:16px;color:#333;width:150px;/* padding:0 35px; */}
-->

</style>
</head>
<body>
<div id="page_box">
<div id="logo"></div>
<div id="login_info">
<ul>
	<li id="sys_title"></li>
</ul>
<div id="login_block">
<div id="login_method" class="qrcodeImg"></div>
<ul id="now_login">
<form name='f' action='<c:url value="/j_spring_security_check" />' method='post'>
	<input type="hidden" name="flag" value="web"/>
	<li><f:message key="prompt.login.userName"/> : <br/><input name='j_username' id="username" class="inbox"/></li>
	<li><f:message key="prompt.login.password"/> : <br/><input name='j_password' type='password' class="inbox" /></li>
	<li><f:message key="prompt.login.code"/> : <br/><input name='j_captcha' class="captcha inbox"/>
		<a href="javascript:reloadVerifyCode();"><img id="safecode" src='security/captcha' /></a>${captcha_msg}</li>
	<li class="alert">
		<c:choose>
			<c:when test="${not empty param.errorCode}">
				<c:if test="${ param.errorCode == 1 }"><f:message key="prompt.login.code.error" /></c:if>
				<c:if test="${ param.errorCode == 2 }"><f:message key="prompt.login.code.nvl" /></c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${not empty param.login_error}">${SPRING_SECURITY_LAST_EXCEPTION.message}</c:if>
			</c:otherwise>
		</c:choose>
		&nbsp;
	</li>
	<li><input type='submit' class='bt' value='<f:message key="prompt.login.button.submit"/>'/></li>
</form>
</ul>
<div id="qrcode_login" style="display:none"><p>扫描下载手机客户端</p></div>
</div>
</div>
</div>

<div id="auth_block">
<ul>
	<li><f:message key="rightversion.login" /></li>
	<li><f:message key="build.version" /></li>
	<li style="color: #2A4473"><f:message key="package.time" /></li>
</ul>
</div>
<script type="text/javascript">
document.getElementById("username").focus();
function getHeight(){ 
	var yScroll; 
	if (window.innerHeight && window.scrollMaxY) { yScroll = window.innerHeight + window.scrollMaxY; } 
	else if (document.body.scrollHeight > document.body.offsetHeight) { yScroll = document.body.scrollHeight; } 
	else { yScroll = document.body.offsetHeight;  } 
	var windowHeight; 
	if (self.innerHeight) { windowHeight = self.innerHeight; } 
	else if (document.documentElement && document.documentElement.clientHeight) { windowHeight = document.documentElement.clientHeight; } 
	else if (document.body) {  windowHeight = document.body.clientHeight; } 
	if (yScroll < windowHeight) { pageHeight = windowHeight; }  else { pageHeight = yScroll; } 
	return pageHeight;
}
function reloadVerifyCode(){
	var timenow = new Date().getTime();                        
	document.getElementById('safecode').src='<%=request.getContextPath()%>/security/captcha?d='+timenow;
}
window.onresize = function(){
	var h = getHeight();
	if ( h <= 330 ) {
		document.getElementById("auth_block").style.display="none";
	} else {
		document.getElementById("auth_block").style.display="";
	}
}
function toUtf8(str) {    
    var out, i, len, c;    
    out = "";    
    len = str.length;    
    for(i = 0; i < len; i++) {    
        c = str.charCodeAt(i);    
        if ((c >= 0x0001) && (c <= 0x007F)) {    
            out += str.charAt(i);    
        } else if (c > 0x07FF) {    
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));    
            out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));    
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
        } else {    
            out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));    
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
        }    
    }    
    return out;    
}
$(function(){
	//获取生成二维码字符串 
	var paramObj = new Object();
	paramObj.paramCode="sys.qrcode.text";
	$.ajax({type:'post', url:'selectQrcodeText.action', data:{jsonObject:$.toJSON(paramObj)},
				success:function(data){
					var res=$.parseJSON(data);
					if(res.success){var qrcode_text = toUtf8(res.root[0].paramValue);$('#qrcode_login').qrcode({render:'table',width:150,height:150,text:qrcode_text}); }  
					}}); 
	
	$.ajax({type:'post',url:'loadTheme.action',
		success:function(data){
			var res = $.parseJSON(data);
			if(res.success){$.each(res.root,function(i,theme){
				if(theme.paramCode == 'sys.theme.img.logo'){$('#logo').attr('style','background:transparent url('+theme.paramValue+') no-repeat');}
				if(theme.paramCode == 'sys.theme.img.loginbg'){$('#login_info').attr('style','background:transparent url('+theme.paramValue+') no-repeat');}
				if(theme.paramCode == 'sys.theme.systitle'){$('#sys_title').html(theme.paramValue);}
			});}
			else{alert("主题加载失败"+res.syserr);}
		}});
	$('#login_method').click(function(){  
			 if($(this).hasClass('qrcodeImg')){
				$('#login_method').removeClass('qrcodeImg');
				$('#login_method').addClass('computerImg');
				$('#now_login').attr('style','display:none');
				$('#qrcode_login').attr('style','display:');
			}else{
				if($(this).hasClass('computerImg')){
					$('#login_method').removeClass('computerImg');
					$('#login_method').addClass('qrcodeImg');
					$('#now_login').attr('style','display:');
					$('#qrcode_login').attr('style','display:none');
				}
			}
		});
})
</script>
<div style="display: none;" id="preload">
</div>
</body>
</html>