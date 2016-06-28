jQuery.jyajax = {
/*X------------------------AJAX向服务器发送请求----------------------*/
ajaxRequest:function(requesturl,thejsonObject, isAsync, successcallback){
	$.ajax({url:requesturl,
		type:"POST", cache:false, dataType:"json", async:isAsync, data:{'jsonObject':thejsonObject},
		success:function(data){
			if(data.success == false){ jQuery.jyajax.outputErrors(data); }else{ successcallback(jQuery.jyajax.jsonWeightloss(data)); }
		}
	});
},
ajaxCache:function(requesturl,thejsonObject, isAsync, successcallback){
	$.ajax({url:requesturl,
		type:"GET", cache:true, ifModified:true, dataType:"json", async:false, data:{'jsonObject':thejsonObject},
		success:function(data){
			if(data.success == false){ jQuery.jyajax.outputErrors(data); }else{ successcallback(jQuery.jyajax.jsonWeightloss(data)); }
		}
	});
},
ajaxSimple:function(requesturl,dd,successcallback){
	$.ajax({url:requesturl, type:"POST", cache:false,
		dataType:"json", data: dd,
		success:function(data){
			if(data.success == false){ jQuery.jyajax.outputErrors(data); }else{ successcallback(jQuery.jyajax.jsonWeightloss(data)); }
		}
		//error:falsecallback
	});
},
/*X------------------------AJAX向服务器发送请求----------------------*/
team_ONajaxrequest:function(requesturl,thejsonObject,successcallback){
	$.ajax({url:requesturl,
		type:"POST", cache:false, dataType:"json", data:{'jsonObject':thejsonObject},
		success:function(data){ successcallback(data); }
		//error:falsecallback
	});
},
/*------------------对AJAX回来的函数进行处理------------------*/
jsonWeightloss:function(data){
	if($(data.root).size() == 1){ return data.root[0]; }else{ return data.root; }
},
/*---------------------------输出服务器返回错误信息----------------------------*/
outputErrors:function(data){
	var adderrors="";
	if(data.syserr !="" && data.syserr != null){
		adderrors =	data.syserr;
		adderrors +=". ";
	}
	if(adderrors != ""){
		alert(adderrors);
	};
},
coverToggle:function (content) {
    content = content==undefined?"Loading...":content;
    if ($("#CHCover").size() == 0) {
        var html = '<div id="CHCover" class="coverDiv"></div>' +
                '<span id="CHSpan" class="coverSpan"></span>';
        $("body").append(html);
    } else if ($("#CHCover")[0].style.display != "none") {
        $("#CHCover,#CHSpan").hide();
        return;
    }
    var gif='<img src="r/img/icon/loader.gif" style="display:inline"></img>';
    $("#CHSpan").append(gif);
    var rel = document.compatMode == 'CSS1Compat' ? document.documentElement : document.body;
    var width = Math.max(rel.scrollWidth, rel.clientWidth || 0) - 1;
    var height = Math.max(rel.scrollHeight, rel.clientHeight || 0) - 1;
    $("#CHCover").addClass('overlay-div');
    $("#CHSpan").addClass('overlay-span');
    $("#CHCover").css({
        width: width + 'px',
        height: height + 'px'
    }).show();
    $("#CHSpan").css({
        top:(height - 20-100) / 2,
        left:(width - 160) / 2
    }).show();
},
hideCover:function (){
	if ($("#CHCover")[0].style.display != "none") {
        $("#CHCover,#CHSpan").hide();
	}
},
showCover:function  (content){
    $("#CHCover,#CHSpan").show();
}
};
