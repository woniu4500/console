jQuery.jyform = {
dateBox:function(options){
	var select = options.select;
	if ( select.hasClass('hasDatepicker')) { return ; }
	var dtid = select.attr('id') + '-display';
	var show = $('<input id="'+ dtid +'" type="text"/>');
	select.parent().prepend(show);
	select.datepicker($.extend({
		showWeek:false,
		firstDay:1,
		showOn:"button",
		buttonImage:"r/img/icon/calendar.png",
		buttonImageOnly: true,
		onSelect:function(){$(this).blur();},
		minDate:new Date(1900,0,1),
		maxDate:new Date(3000,11,31),
		changeMonth: true,
	    changeYear: true,
		dateFormat: 'yymmdd',
		altField: '#'+dtid, 
		altFormat:'yy-mm-dd'
	}, options));
	show.detach();
	show.insertAfter(select);
	select.css({'width':'0px',border:'0','color':'#fff','padding':'0'});
	show.css({'width':'115px','marginRight':'3px'});
	select.attr('readOnly','true').attr('disabled','true');
	show.attr('readOnly','true');
	show.click(function(){ select.datepicker('show');});
}
,dateTimeBox:function(options){
	var select = options.select;
	if ( select.hasClass('hasDatepicker')) { return ; }
	var dtid = select.attr('id') + '-display';
	var show = $('<input id="'+ dtid +'" type="text"/>');
	select.parent().prepend(show);
	select.datetimepicker($.extend({
		showWeek:false,
		firstDay:1,
		showOn:"button",
		buttonImage:"r/img/icon/calendar.png",
		buttonImageOnly: true,
		onSelect:function(){$(this).blur();},
		minDate:new Date(1900,0,1),
		maxDate:new Date(3000,11,31), 
		dateFormat: 'yymmdd',
		timeFormat: "HHmmss",
		changeMonth: true,
	    changeYear: true,
		pickerTimeFormat: 'HH:mm:ss',
		altField: '#'+dtid, 
		altFormat:'yy-mm-dd',
		altSeparator: ' ',
		altFieldTimeOnly: false,
		altTimeFormat: 'HH:mm:ss',
		stepMinute: 10,
		stepSecond: 10,
		separator:''
	}, options));
	show.detach();
	show.insertAfter(select);
	select.css({'width':'0px',border:'0','color':'#fff','padding':'0'});
	show.css({'width':'125px','marginRight':'3px'});
	select.attr('readOnly','true').attr('disabled','true');
	show.attr('readOnly','true');
	show.click(function(){ select.datepicker('show');});
}
,bsDateTimeBoxes: function() { // bootstrap datetimepicker
	$("input[xtype='date']").each(function(_i,_n) {
		var $c = $(this);
		if ( $c.attr('readonly') ) {} else {
			var cid = $c.attr('id');
			$('#' + cid + '-disp').datetimepicker({format: "yyyy-mm-dd", todayBtn: true, minView: 2, startView: 2
				, linkField: cid, linkFormat: "yyyymmdd", pickerPosition: "bottom-left", language:'zh-CN' });
		}
	});
	$("input[xtype='datetime']").each(function(_i,_n) {
		var $c = $(this);
		if ( $c.attr('readonly') ) {} else {
			var cid = $c.attr('id');
			$('#' + cid + '-disp').datetimepicker({format: "yyyy-mm-dd hh:ii", todayBtn: true, 
				linkField: cid, linkFormat: "yyyymmddhhiiss", pickerPosition: "bottom-left", language:'zh-CN' });
		}
	});
}
,ajaxSelectBox:function(options){ // 
	jQuery.jyajax.ajaxCache(options.url, options.data, options.val?false:true, function(res){
		if ( res && res.length > 0 ) {
			var src_opts = options.emptyOpt?"<option value=''></option>":"";
			var store_data = new Array();
			for ( var cnt in res) {
				var src = res[cnt];
				var sval = src[options.valueField];
				var sopt = src[options.displayField];
				if ( options.render ) {
					sopt = options.render(src);
				}
				if ( options.val) {
					if ( isArray(options.val)) {
						var checked = false;
						for(var _i in options.val) {
							if ( options.val[_i] == sval ) {
								src_opts += "<option value='"+sval+"' selected='selected'>"+ sopt +"</option>";
								checked = true;
								break;
							}
						}
						if ( !checked ) {
							src_opts += "<option value='"+sval+"' >"+ sopt +"</option>";
						}
					} else if (options.val == sval) {
						src_opts += "<option value='"+sval+"' selected='selected'>"+ sopt +"</option>";
					} else {
						src_opts += "<option value='"+sval+"' >"+ sopt +"</option>";
					}
				} else {
					src_opts += "<option value='"+sval+"' >"+ sopt +"</option>";
				}
				store_data[sval] =  src;
			}
			if ($.isArray(options.selectId)) {
				$(options.selectId).each(function(i,n){
					$('#' + n).html(src_opts);
				});
			} else {
				$("#" + options.selectId ).append(src_opts);
			}
			if ( options.select) {
				$(options.select).append(src_opts);
			}
			if ( options.store ) {
				options.store[options.selectId] = store_data;
			}
			if ( options.func ) {
				options.func(res);
			}
		} else if(typeof (res)=="object"){
			var src_opts = "";
			var store_data = new Array();
			var src = res;
			var sval = src[options.valueField];
			var sopt = src[options.displayField];
			if ( options.render ) {
				sopt = options.render(src);
			}
				if (typeof(sval)!= "undefined"&&typeof(sopt)!= "undefined"){
			src_opts += "<option value='"+sval+"' >"+ sopt +"</option>";
				store_data[sval] =  src;			}
			$("#" + options.selectId ).append(src_opts);
			if ( options.store ) {
				options.store[options.selectId] = store_data;
			}
			if ( options.func ) {
				options.func(res);
			}
		}else {
			alert(options.failedMsg?options.failedMsg:'获取信息失败！');
		}
	});	
}
// 用户下拉列表
,sysuserBox:function(options){sysUserBox(options);}
,sysUserBox:function(options) {
	var opt = $.extend({
		valueField:'loginId',
		displayField:'loginId',
		render:function(res){return res.loginId;},
		failedMsg:'获取系统代码失败!'
	},options);
	if ( !opt.url && opt.ctype ) {
		opt.url = 'findUserList.action?codeType=' + opt.ctype ;
	}
	jQuery.jyform.ajaxSelectBox(opt);
}
//
,syscodeBox:function(options) { jQuery.jyform.sysCodeBox(options);}
,sysCodeBox:function(options) {
	var opt = $.extend({
		valueField:'codeNo',
		displayField:'codeName',
		render:function(res){return res.codeName;},
		failedMsg:'获取系统代码失败!'
	},options);
	if ( !opt.url && opt.ctype ) {
		opt.url = 'findSecCodeList.action?codeType=' + opt.ctype;
	}
	jQuery.jyform.ajaxSelectBox(opt);
}
,qrySysCodeBox:function(options) {
	$('#'+options.selectId).append('<OPTION value="" >全部</OPTION>'); 
	$('#' + options.selectId).val('');
	var opt = $.extend({
		valueField:'codeNo',
		displayField:'codeName',
		render:function(res){return res.codeName;},
		failedMsg:'获取系统代码失败!'
	},options);
	if ( !opt.url && opt.ctype ) {
		opt.url = 'findSecCodeList.action?codeType=' + opt.ctype;
	}
	jQuery.jyform.ajaxSelectBox(opt);
}
,frmSysCodeBox:function(options) {
	$('#'+options.selectId).append('<OPTION value="" ></OPTION>'); 
	$('#' + options.selectId).val('');
	var opt = $.extend({
		valueField:'codeNo',
		displayField:'codeName',
		render:function(res){return res.codeName;},
		failedMsg:'获取系统代码失败!'
	},options);
	if ( !opt.url && opt.ctype ) {
		opt.url = 'findSecCodeList.action?codeType=' + opt.ctype;
	}
	jQuery.jyform.ajaxSelectBox(opt);
}
,sysCodeBoxes:function(){
	$('select[ctype]').each(function(i,n){
		var $n = $(n);
		var _ctype = $n.attr('ctype');
		var _sid = $n.attr('id');
		var _c = $n.attr('comparison');
		var _v = $n.attr('def');
		if ( _c ) {
			// $.jyform.qrySysCodeBox({selectId:_sid,ctype:_ctype});
			var opt = {selectId:_sid,ctype:_ctype, func:function(){ $.jyform.multiSelectBox(_sid); }};
			if ( _v && (typeof (_v)) != "undefined" ) { 
				console.log(typeof (_v));
				if ( _v.indexOf(',') > 0 ) {
					opt.val = _v.split(',');
				} else {
					opt.val = _v; 
				}
			}
			$.jyform.sysCodeBox(opt);
		} else {
			$.jyform.frmSysCodeBox({selectId:_sid,ctype:_ctype});
		}
	});
}
,commonValidator:function(vg,fields){
	$(fields).each(function(i,n){
		if ( typeof n == 'object' ) {
			$("#"+n.id).formValidator({ empty : n.empty, onshow : "请输入内容", oncorrect : "√",validatorgroup:vg }).functionValidator({fun:chkCNLen});
		} else {
			$("#" + n).formValidator({ empty : true, onshow : "请输入内容", oncorrect : "√" }).functionValidator({fun:chkCNLen});
		}
	});
}
,jsonForm:function(setting) {
	var p = setting;
	var form = $('#' + p.formId);
	var obj = new Object();
	$(':input', form).not(':button,.ignore').each(function(i, n) {
		switch (n.type) {
		case "radio":
			if (n.checked) { obj[n.name] = $(n).val(); }
			break;
		case "checkbox":
			if (!obj[n.name]) { obj[n.name] = new Array(); }
			if (n.checked) { obj[n.name].push($(n).val()); }
			;
			break;
		default:
			if (p.noEmpty) { if ($(n).val() != "") { obj[n.name] = $(n).val(); }
			} else obj[n.name] = $(n).val() || '';
			break;
		}
	});
	if (p.dealData) {
		p.dealData(obj);
	}
	return obj;
}
,jsonfilter:function(id) {
	if ( id ) {
		var qfrm = $('#'+id);
		var filter = new Array();
	    $(':input[cp]',qfrm).each(function() {
	      if ($(this).val() && this.name ) {
	    	  var ftype = $(this).attr("ft");
	    	  filter.push({
	    		  "field" : this.name, 
	    		  "data" : { "type" : ftype ? ftype:'string' ,"value" : $(this).val() ,"comparison" : $(this).attr("cp")}});
	      }
		});
	    return $.toJSON(filter);
	}
}
,iframefix:function(id){
	$('#' + id).load(function(){ 
		var mainheight = $(this).contents().find("body").height()+30; 
		$(this).height(mainheight); 
	});
}
,menulist:function(id){
	$('#' + id)
		.click(function() {
		 	var menu = $(this).next().show().position({ my: "left top", at: "left bottom", of: this });
		 	$(document).one( "click", function() { 
		 		menu.hide(); 
		 	}); 
		 	return false;
		}).next().hide().menu();
	$('#' + id).next().css({'position': 'absolute', 'width': '100px'});
}
,toolbarfix:function(id){
	var rtb = $('#' + id);
	var startPos = rtb.offset().top;
	rtb.css({'width':'99.8%', 'background-color': '#FCFCFC', 'border-bottom':'1px solid #ECECEC', 'padding': '3px 0 3px 0','background': "url('r/img/bg/bg-band.png') 0 -387px repeat-x" });
	rtb.find('ul').css({'margin':'0'});
	rtb.find('ul li').css({'display':'block','float':'left','margin-right':'.8em'});
	rtb.find('ul li label').css({'display': 'inline-block', 'width': '60px', 'text-align': 'right', 'margin-right': '.8em'});
	rtb.find('ul li input').css({'margin-top':'1px'});
	$.event.add(window,'scroll',function(){var p = $(window).scrollTop();rtb.css({'position':((p)>startPos)?'fixed':'static','top':((p)>startPos)?'0px':''});});
}
,gridHeightFix:function(id){
	if ($('#'+id).length>0) {
		$('#'+id).flexFixHeight();
		$(window).resize(function(){ setTimeout("$.jyform.lazyFixHeight('"+id+"')",200);} );
	}
}
,lazyFixHeight:function(id) {
	var $t = $('#'+id);
	var bh = $t.data("bh");
	var ph = $t.parent().height();
	if ( bh && bh == ph) {
		$t.flexFixHeight();
	} else {
		$t.data("bh", ph);
		setTimeout("$.jyform.lazyFixHeight('"+id+"')",200);
	}
},
/**
 * generate html node object from 
 * @param _f field
 * @param _t template string
 * @returns
 */
htmlFromTpl:function(_f,_t){
	var html =  _t.replace(/{([^{}]+)}/g,function(word){ 
		var _attr=word.replace(/({|})+/g,""); 
		return _f[_attr]; });
	return $(html);
}
,fillForm:function(_id,_r){
	$('#'+ _id +' :input').each(function(i,n) {
		if ( n.name in _r) { 
			v = _r[n.name] ;
			if ( $(n).attr('xtype') == 'money' ) {
				v = frmMoney(v);
			} 
			if ( $(n).attr('xtype') == 'rate' ) {
				v = frmRate(v);
			}
			if ( $(n).attr('xtype') == 'date') {
				var cid = $(n).attr('id');
				$('#'+cid+'-disp').val(frmBSDate(v));
			}
			if ( $(n).attr('xtype') == 'datetime') {
				var cid = $(n).attr('id');
				$('#'+cid+'-disp').val(frmBSTime(v));
			}
			$(n).val(v);
			if ( $(n).hasClass('hasDatepicker') ) { 
				$(n).datepicker('setDate',frmTime(v));
			}
		}
	});
}
,multiSelectBox:function(_id, _opts) {
	var _ori = $('#' + _id) ;
	var vid = _id + '-input'; 
	var _input = $('<input id="' + vid + '" />');
	_input.attr('name',_ori.attr('name'))
		.attr('ftype', 'list')
		.attr('comparison',_ori.attr('comparison'));
	_input.css({'display':'none'});
	_ori.after(_input);
	_ori.removeAttr('comparison');
	var _setVal = function() {
		var checked = $('#'+_id).multiselect('getChecked');
		var _v = '';
		if ( checked.length == 0 ) {
			$('#' + vid).val(_v);
		}
		$(checked).each(function(i,n){_v += ($(n).val() + ',');});
		$('#' + vid).val(_v);
	};
	$('#'+_id).multiselect($.extend({
		minWidth:160, height:100,
		selectedList:3, 
		classes:'ui-btn-sm',
		checkAllText: '全选',
		uncheckAllText: '取消',
		noneSelectedText: '请选择...',
		selectedText: '已选 # 项',
		close:function(){
			_setVal();
		}
	}, _opts));
	_setVal();
}
};

$.jytree = {
setState:function(p,t){
	var state = "";
	p.children('ul').children('li').each(function() {
		var chidrenstate;
		if ($(this).hasClass('leaf')) {
			if ($(this).attr('check') == 'true') {
				$(this).children('a').addClass('checked');
				t.open_branch($(this).parents('li'));
				chidrenstate = "checked";
			} else {
				$(this).children('a').addClass('unchecked');
				chidrenstate = "unchecked";
			}
		} else {
			chidrenstate = $.jytree.setState($(this),t);
		}
		if (state == "undetermined"){
			return;
		}
		if (state == "") {
			state = chidrenstate;
		} else if (state != chidrenstate) {
			state = "undetermined";
		}
	});
	$(p).children('a').addClass(state);
		return state;
	}
};

(function($, undefined) {
    $.fn.getCursorPosition = function() {
        var el = $(this).get(0);
        var pos = 0;
        if ('selectionStart' in el) {
            pos = el.selectionStart;
        } else if ('selection' in document) {
            el.focus();
            var Sel = document.selection.createRange();
            var SelLength = document.selection.createRange().text.length;
            Sel.moveStart('character', -el.value.length);
            pos = Sel.text.length - SelLength;
        }
        return pos;
    };
})(jQuery);

$.fn.extend({
    insertAtCursor : function(myValue) {
        var $t = $(this)[0];
        if (document.selection) {
            this.focus();
            sel = document.selection.createRange();
            sel.text = myValue;
            this.focus();
        } else if ($t.selectionStart || $t.selectionStart == '0') {
            var startPos = $t.selectionStart;
            var endPos = $t.selectionEnd;
            var scrollTop = $t.scrollTop;
            $t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
            this.focus();
            $t.selectionStart = startPos + myValue.length;
            $t.selectionEnd = startPos + myValue.length;
            $t.scrollTop = scrollTop;
        } else {
            this.value += myValue;
            this.focus();
        }
    }
});

/**
 * A Simple Form Builder
 * @param _fields 字段定义
 */
function LightFormBuilder(_fields){
	this.fields = this.convertFields(_fields);
	return this;
};

LightFormBuilder.prototype = {
	/** converted fields */
	fields:false, 
	/** components map */
	components:new Object(),
	/** id numbers */
	idnumber: 0,
	__t_textfld: '<li><label for="{id}"><span class="not-empty">{notempty}</span>{desc}:</label><input id="{id}" name="{name}" maxLength="{length}" type="{xtype}"/><div id="{id}Tip"></div></li>',
	__t_textarea: '<li><label for="{id}"><span class="not-empty">{notempty}</span>{desc}:</label><textarea id="{id}" name="{name}" maxLength="{length}" ></textarea><div id="{id}Tip"></div></li>',
	__t_select:'<li><label for="{id}"><span class="not-empty">{notempty}</span>{desc}:</label><select id="{id}" name="{name}" ></select><div id="{id}Tip"></div></li>',
	__t_qryfld: '<li><label for="{id}">{desc}:</label><input id="{id}" name="{name}" maxLength="{length}" type="{xtype}"/></li>',
	__t_qryselect:'<li><label for="{id}">{desc}:</label><select id="{id}" name="{name}" multiple="multiple" ></select></li>',
	/**
	 * convert fields from array object to one map object.
	 * each element become a field of the object with the name in each el.
	 */
	convertFields:function(_flds) {
		var f = new Object();
		$(_flds).each(function(i,n){
			f[n.name] = n ;
		});
		return f;
	},
	/**
	 * generate form li with fields and tpl
	 * @param _f field definition
	 * @param _t template 
	 */
	htmlFromTpl:function(_f,_t){
		var html =  _t.replace(/{([^{}]+)}/g,function(word){ 
			var _attr=word.replace(/({|})+/g,""); 
			return _f[_attr]; });
		var $html = $(html);
		$html.find('#'+_f.id).attr(_f);
		return $html;
	},
	/**
	 * generate form li with fields def
	 * @param _p string or config object
	 */
	frmFld:function(_p){
		var tpl = this.__t_textfld;
		_p.notempty = _p.nvl?'*':'';
		switch (_p.xtype) {
			case 'textarea': tpl = this.__t_textarea; break;
			case 'select': tpl = this.__t_select; break;
			default:break;
		}
		return this.htmlFromTpl(_p,tpl);
	},
	/**
	 * generate form li with fields def
	 * @param _p string or config object
	 */
	qryFld:function(_p){
		var tpl = this.__t_qryfld;
		_p = $.extend({ftype:_p.jtype=='string'?'string':'number',
				oper:_p.jtype=='string'?'like':'eq'},_p);
		_p.comparison = _p.oper;
		switch (_p.xtype) {
			case 'select': tpl = this.__t_qryselect; break;
			default:break;
		}	
		return this.htmlFromTpl(_p,tpl);
	},
	addComponent:function(_o){
		if ( this.components[_o.id]){
			_o.id = _o.id + '-' + (this.idnumber++);
			this.addComponent(_o);
		} else {
			this.components[_o.id] = _o;
		}
		return _o;
	},
	getFld:function(_p,_f,_fid) {
		var fld = false;
		if ( (typeof _p) == 'string' ) {
			fld = $.extend({},_f[_p.trim()]);
		} else if ( (typeof _p) == 'object') {
			if ( _p.name ) {
				fld = $.extend(_p,_f[_p.name]);
			}
		}
		if ( fld ) {
			var o = $.extend({id:_fid+'-'+fld.name,xtype:'text'}, fld);
			return this.addComponent(o);
		}
		return null;
	},
	/**
	 * generate form field list
	 * @param _fid form id
	 * @param _flds field list
	 * @param _fdef field definition
	 * @param _nid node to append fields, if null then append to the ul.frm-ul under form
	 */
	frmList:function(_fid, _flds, _fdef, _nid){
		_fdef = _fdef?_fdef:this.fields;
		if ( $('#'+_fid).length == 0 ) {
			alert('Invalid form id. ');
			return false;
		}
		var $ul = (_nid && $('#' + _nid)[0]) ? $('#' + _nid):$('#'+ _fid +' ul.frm-ul');
		if ( $ul.length==0 ) {
			$ul = $('<ul class="frm-ul"></ul>');
			$('#'+_fid).append($ul);
		}
		var fldArr = new Array();
		// generate fields
		var _this = this;
		$(_flds).each(function(i,n){
			var p = _this.getFld(n,_fdef,_fid);
			if ( p ) {
				fldArr.push(p);
				var h = _this.frmFld(p);
				if (h) $ul.append($(h));
			}
		});
		// add validator
		$(fldArr).each(function(i,n){
			//alert(n.id + n.name + n.desc + n.nvl);
			var $fld = $("#" + n.id);
			$fld.formValidator({empty: n.nvl?false:true, validatorGroup:_fid, onEmpty:n.nvl?n.desc+'必须填写':'', onFocus: "请输入"+n.desc, onCorrect : "√" })
				.inputValidator({min: n.nvl?1:0, max: n.length, onError: "请输入"+n.desc, onErrorMax: n.desc+"最多可输入"+n.length+'个字符' });
		});
	},
	/**
	 * generate query form field list
	 * @param _fid form id
	 * @param _flds field list
	 * @param _fdef field definition
	 * @param _nid node to append fields, if null then append to the ul.frm-ul under form
	 */
	qryList:function(_fid, _flds, _fdef, _nid){
		_fdef = _fdef?_fdef:this.fields;
		if ( $('#'+_fid).length == 0 ) {
			alert('Invalid form id. ');
			return false;
		}
		var $ul = (_nid && $('#' + _nid)[0]) ? $('#' + _nid):$('#'+ _fid +' ul.qry-ul');
		if ( $ul.length==0 ) {
			$ul = $('<ul class="qry-ul"></ul>');
			$('#'+_fid).append($ul);
		}
		// generate fields
		var _this = this;
		var inner = $ul.children();
		inner.detach(); // detach from parent
		$(_flds).each(function(i,n){
			var p = _this.getFld(n,_fdef,_fid);
			if ( p ) {
				var h = _this.qryFld(p);
				if (h) $ul.append($(h));
			}
		});
		$ul.append(inner); // append after add node
	},
	/**
	 * generate colModel for flexigrid
	 * @param _fid use this to generate column id
	 * @param _flds field list
	 * @param _gd global definition
	 * @param _fdef field definition
	 * @returns {Array}
	 */
	getColumns:function(_fid, _flds, _gd, _fdef){
		_fdef = _fdef?_fdef:this.fields;
		_gd = _gd?_gd:{};
		var arr = new Array();
		var _this = this;
		$(_flds).each(function(i,n){
			var p = _this.getFld(n,_fdef,_fid);
			if ( p ) {
				var el = $.extend({
					display:p.desc,
					dataIndex:p.name,
					align:(p.jtype=='number'||p.jtype=='money'||p.jtype=='percent')?'right':'center',
					sortable:true,
					width:80
				}, _gd, n);
				arr.push(el);
			}
		});
		return arr;
	}
};
