(function($) {
$.extend({
/*----------------------------表单数据AJAX提交---------------------------*/
ajaxForm : function(setting) {
	var p = setting;
	var form = $('#' + p.formId);
	var obj = new Object();
	$(':input', form).not(':button,.ignore').each(function(i, n) {
		
		switch (n.type) {
		case "radio":
			if (n.checked) {
				obj[n.name] = $(n).val();
			}
			break;
		case "checkbox":
			if (!obj[n.name]) {
				obj[n.name] = new Array();
			}
			if (n.checked) {
				obj[n.name].push($(n).val());
			}
			;
			break;
		default:
			if (p.noEmpty) {
				if ($(n).val() != "") {
					obj[n.name] = $(n).val();
				}
			} else
				obj[n.name] = $(n).val() || '';
			break;
		}
		var xtype = $(n).attr('xtype');
		switch ( xtype ) {
			case 'money': 
			case 'rate':
				if ($(n).val() != "") {
					obj[n.name] = $(n).val()*100 ;
				}
			default : break;
		}
	});

	if (p.dealData) {
		p.dealData(obj);
	}

	var objstr = $.toJSON(obj);
	$.ajax({
	url : p.url,
	modal:p.modal,
	dataType : 'json',
	type : 'POST',
	async:true,
	modal:true,
	data : {
		jsonObject : objstr
	},
	success : p.success
	});
},
/*----------------------------简化验证---------------------------*/
someAuthentication : function(list) {
	$(list).each(function(i) {
		var obj = $("#" + this.id);
		if (obj.length == 0)
			return;

		obj.formValidator(this.formValidator);

		if (this.inputValidator)
			obj.inputValidator(this.inputValidator);

		if (this.regexValidator)
			obj.regexValidator(this.regexValidator);

		if (this.functionValidator)
			obj.functionValidator(this.functionValidator);
		if (this.compareValidator)
			obj.compareValidator(this.compareValidator);

	});
},
changeDecimal : function(x, pos) {
	var f_x = parseFloat(x);
	pos = pos || 2;
	if (isNaN(f_x)) {
		// alert('function:changeTwoDecimal->parameter error');
		return "";
	}
	var f_x = Math.round(x * 100) / 100;
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0) {
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + pos) {
		s_x += '0';
	}
	return s_x;

},
changeFenToYuan : function(x) {
	return $.changeDecimal(parseFloat(x) / 100);
},
changeYuanToFen : function(x) {
	return $.changeDecimal(parseFloat(x) * 100);
},
createIframe : function(setting) {
	var p = $.extend({
	url : '',
	text : '标签',
	only : true
	}, setting);
	var fid = '' + new Date().getTime();
	var action = p.url.substr(0, p.url.indexOf('?'));
	if (p.only) {
		if (p.url in iframeId) {
		} else {
			iframeId[action] = fid;
		}
		parent.addTab(iframeId[action], p.text, p.url);
	} else
		parent.addTab(fid, p.text, p.url);

},
		/*----------------------------表单数据AJAX提交---------------------------*/
queryTable : function(setting) {
	var p = $.extend({
		filter : []
	}, setting);
	var filter = new Array();
	var qdiv = $('#' + p.formId);
	$(':input[comparison]', qdiv).each(function() {
		var theType = (typeof $(this).val());
		if (theType != "object") {
			var $this = $(this);
			if ($this.val()) {
				var ftype = $this.attr("ftype");
				var comp = $this.attr('oper')?$this.attr('oper'):$this.attr('comparison');
				filter.push({
				field: this.name,
				data: {
				  type: ftype?ftype:"string",
				  value: $this.val(),
				  comparison: comp?comp:'eq'
				}
				});
			}
		}
	});
	// CheckBox Group process
	var cbmap = new Object();
	$('input.cbg:checked', qdiv).each(function() {
		var theType = (typeof $(this).val());
		if (theType != "object") {
			var $this = $(this);
			if ($this.val() && this.name) {
				var cbval = cbmap[this.name];
				cbval = (typeof(cbval))=='undefined'?($this.val()):(cbval+','+$this.val());
				cbmap[this.name] = cbval;
			}
		}
	});
	for ( var _f in cbmap ) {
		var _name = _f;
		var _value = cbmap[_f];
		if ( _name && _value ) {
			filter.push({
				field: _name,
				data: {
				  type: 'list',
				  value: _value,
				  comparison: 'eq'
				}
			});
		}
	}
	
	$.each(p.filter, function(i, n) {
		var v = n.value();
		if (v)
			filter.push({
			"field" : n.field,
			"data" : {
			"type" : n.type,
			"value" : v,
			"comparison" : n.comparison
			}
			});
	});
	var params = new Array();
	$(':input.param', qdiv).each(function() {
		params.push({
		name : this.name,
		value : $(this).val()
		});
	});
	$('#' + p.tableId).flexReload(filter, params);
},
toJSON : function(object) {
	var type = typeof object;
	if ('object' == type) {
		if (Array == object.constructor)
			type = 'array';
		else if (RegExp == object.constructor)
			type = 'regexp';
		else
			type = 'object';
	}
	switch (type) {
	case 'undefined':
	case 'unknown':
		return;
		break;
	case 'function':
	case 'boolean':
	case 'regexp':
		return object.toString();
		break;
	case 'number':
		return isFinite(object) ? object.toString() : 'null';
		break;
	case 'string':
		return '"' + object
				.replace(/(\\|\")/g, "\\$1")
				.replace(/\n|\r|\t/g, function() {
					var a = arguments[0];
					return (a == '\n') ? '\\n' : (a == '\r') ? '\\r' : (a == '\t') ? '\\t' : "";
				}) + '"';
		break;
	case 'object':
		if (object === null)
			return 'null';
		var results = [];
		for ( var property in object) {
			var value = jQuery.toJSON(object[property]);
			if (value !== undefined)
				results.push(jQuery.toJSON(property) + ':' + value);
		}
		return '{' + results.join(',') + '}';
		break;
	case 'array':
		var results = [];
		for ( var i = 0; i < object.length; i++) {
			var value = jQuery.toJSON(object[i]);
			if (value !== undefined)
				results.push(value);
		}
		return '[' + results.join(',') + ']';
		break;
	}
}

});
$.fn.extend({
pickdate : function(op) {
	var a = $.extend({
	oncleared : function() {
		$(this).blur();
	},
	onpicked : function() {
		$(this).blur();
	}
	}, op);
	$(this).click(function() {
		WdatePicker(a);
	});
	return this;
},
jsonObject: function() {
	var obj = new Object();
	$(this).find(':input').not(':button').each(function(i, n) {
	if ($(this).val()) {
		if (this.type == "radio") {
			if (this.checked) {
				obj[n.name] = $(this).val();
			}
		} else
			obj[n.name] = $(this).val();
		}
	});
	return obj;
},
fillObject:function(setting){
	 var p=$.extend({clear:true,obj:null},setting);
	 if(p.clear){
		 $(this).find(':input[type!=radio][type!=button]').not(':checkbox').not('.notFill').val('');
	 }
	 if(!p.obj)
     return;
	 $(this).find(':input,span[name]').not(':checkbox').each(function(){
		var name=$(this).attr('name');
		var xtype = $(this).attr('xtype');
		
		if(name in p.obj){        				 
			  if(this.type=='radio'){
				  if(this.value==p.obj[name])
					  this.checked='checked';	        				  
			  } else {	        				  
				  if(this.tagName=="SPAN"){
					  this.innerHTML=p.obj[name];
			      } else {
			    	  this.value=p.obj[name];
			    	  switch ( xtype ) {
						case 'money': 
						case 'rate':
							this.value = frmMoney(p.obj[name]);
							break;
						case 'date':
							var cid = $(this).attr('id');
							$('#'+cid+'-disp').val(frmBSDate(p.obj[name]));
							break;
						case 'datetime':
							var cid = $(this).attr('id');
							$('#'+cid+'-disp').val(frmBSTime(p.obj[name]));
							break;
						default : break;
			    	  }
			      }
			  }
				  
				  
		 }	        			 
	 });
	 	        	 
 },
 /**
  * Set dialog to full screen
  * @param _opt 
  */
fullScreenDialog:function(_opt){
	var _w = $('body').width()-1;
	var _h = $('body').height()-4;
	// console.log('body(width,height): ' + _w + ',' + _h);
	this.each(function(){
		var options = $.extend({width:_w ,height:_h, resizable:false, draggable: false, modal: false, bgiframe: false, dialogClass: 'jy-widget jy-no-border'}, _opt);
		return $(this).dialog('option', options);
	});
	return this;
},
/**
 * 
 * @param _func 方法
 */
fullScreen:function(_func){
	this.each(function(){
		$(this).dialog({open:function(){ 
				$(this).fullScreenDialog();
				if ( _func && (typeof _func) == 'function') {
					_func();
				}
			}
		});
		var $this = $(this);
		$(window).resize(function(){
			if($this.is(':visible') ) {
				var _id = $this.attr('id');
				if (_id) {
					setTimeout('$("#'+_id+'").dialog("close"); $("#'+_id+'").dialog("open");', 200);
				}
				if ( _func && (typeof _func) == 'function') {
					setTimeout(_func,1000);
				}
			}
		});
	});
	return this;
},
/**
 * Auto set size to parent
 */
fixHeight:function(_css){
	this.each(function(){
		var $this = $(this);
		var MIN_HEIGHT = _css? (_css.minHeight?_css.minHeight:'20px'):'20px';
		$this.height(MIN_HEIGHT);
		var _ph = $this.parent().height();
		var _pw = $this.parent().width();
		var _mh = 0;
		$this.parent().children(":visible").not('.ui-dialog').not('.ui-widget-overlay').not($(this)).each(function(){
			_mh += $(this).outerHeight();
		});
		var _ch = _ph-_mh<=MIN_HEIGHT?MIN_HEIGHT:(_ph-_mh)+'px';
		var style = $.extend({height:_ch, width: '100%', overflow: 'auto'},_css);
		console.log('p-height:' + _ph + ', m-height:' + _mh);
		$this.css(style);
	});
	return this;
},
qDialog:function(_opt){
	this.each(function() {
		var dc =  $.extend({ autoOpen: false, bgiframe: true, modal: true,
			resizable: false, draggable: true, width: 600, height: 500,
			success: function(d){alert(d);}
		}, _opt);
		dc.buttons=
		{'确定':function(){
					var g=$('.flexigrid',this);
					var chkdt=g.flexAllSelectData();
					if(dc.success(chkdt))  $(this).dialog('close');
				}
		,'取消':function(){$(this).dialog('close');}} ;
		dc.open = function() { $('.qry-ul .bt', $(this)).first().click();};
		$(this).dialog(dc);
		$('.is-grid',$(this)).first().flexigrid(dc.flexigrid);
	});
},
 queryDialog: function(setting) {
		var p = { autoOpen : false, bgiframe : true, modal : true,
			resizable : false, draggable : false, width : 600, height : 500,
			success : function(data) { alert(data); }
		};
		if (setting) p = $.extend(p, setting);
		$(this).dialog(p);
		var grid = $('<div></div>');
		$(this).append('<div>&nbsp;</div>');
		$(this).append(grid);
		grid.flexigrid(p.flexigrid);
		var qtable = $(this).find('.qtable');
		var qbutton = $(this).find('.qbutton');
		$.each(qbutton, function() {
		  $(this).click(function() { 
		    var filter = new Array();
		    $(':input[comparison]',qtable).each(function() {
		      if ($(this).val()) filter.push({"field" : this.name,"data" : {"type" : "string","value" : $(this).val(),"comparison" : $(this).attr("comparison")}});
			});
		    var params = new Array();
		    $(':input.param', qtable).each(function() {
		      params.push({name : this.name, value : $(this).val()});
		    });
		    grid.flexReload(filter,params);
		  });
		});
	},
 queryDialogOpen:function(setting){ 
	 $('.qbutton',this).trigger('click');
    var grid=$('.flexigrid',this);		
	var buttons=new Object();
	buttons["确定"]=function(){
		var data=grid.flexAllSelectData();
		
		if(setting.list){
			if(setting.success(data))
		    $(this).dialog('close');
		}
		else if(data.length>0){
			  if(setting.success(data[0]))
			 $(this).dialog('close');
		  }					   
		else {
			if(setting.success(null))
				$(this).dialog('close');
		}											
	};
	if(setting.clear){
		buttons["清空"]=function(){if(setting.clear)
		       setting.clear();
	    $(this).dialog('close');
       } ;			
	}				
	buttons["取消"]=function(){$(this).dialog('close');}	;				
	 $(this).dialog('option','buttons',buttons);
		$(this).dialog('open');	        
 },	         
 ajaxSelect : function(op){
		var $this=this;
		var setting={
				displayField:'text',
				valueField:'value',
				blank:false,
				async:false,
				isall:false,							
				initData:null,							
				data:null
				};							
  var p=$.extend({},setting,op);
  $.ajax({
  url:p.url,
  async:p.async,
  data:p.data,
  dataType:'json',
  success:function(data){
	  var datas=data.root;	
	  $this.each(function(){		    		  	  	
            var select=$(this);
            select.empty();			            
           if(p.blank){
        	   select.append("<option value='' selected='selected' >不限</option>");
        	}
           if(p.initData){
        	   for(var attr in p.initData){
        		   select.append("<option value='"+p.initData[attr]+"'>"+attr+"</option>");
        	   }
           }
           if(datas.length==0)
	            return;	
         $.each(datas,function(i,n){
        	 if(i==0&&select.children('[selected]').length==0)
        	 select.append("<option selected='selected' value=" + n[p.valueField] + ">" + n[p.displayField] + "</option>");
        	 else select.append("<option value=" + n[p.valueField] + ">" + n[p.displayField] + "</option>");
        	 });								
	     });
	}		
	});				
	 return this;
	},
jsTree : function(setting) {
var conf = {
	data   	 : {type : "json", method : "POST", json : null, url : setting.url },
	ui 		 : { theme_name : setting.theme_name || "apple" },
	callback : {
		beforeclose : function(NODE, TREE_OBJ) {
			var $this = $(NODE).is("li") ? $(NODE) : $(NODE).parent();
			$this.children('span').removeClass().addClass( 'folderclosed');
			return true;
		},
		beforeopen : function(NODE, TREE_OBJ) {
			var $this = $(NODE).is("li") ? $(NODE) : $(NODE).parent();
			$this.children('span').removeClass().addClass('folderopen');
			return true;
		},
		onchange : function(NODE, TREE_OBJ) {
			if (setting.onchange) {
				setting.onchange(NODE, TREE_OBJ);
				return;
			}
			if (TREE_OBJ.settings.ui.theme_name == "checkbox") {
				var $this = $(NODE).is("li") ? $(NODE) : $( NODE).parent();
				var state;
				$this.children("a").removeClass("clicked");
				if ($this.children("a").hasClass("checked")) {
					$this.find("li").andSelf() .children("a").removeClass("checked").removeClass("undetermined").addClass("unchecked");
					state = 0;
				} else {
					$this.find("li").andSelf().children("a").removeClass("unchecked").removeClass("undetermined").addClass("checked");
					$this.find('li').andSelf().each(function() {TREE_OBJ.open_branch($(this));});
					state = 1;
				}
				$this.parents("li").each(function() {
					if (state == 1) {
						if ($(this).find("a.unchecked, a.undetermined").size() - 1 > 0) {
							$(this).parents("li").andSelf().children("a").removeClass("unchecked").removeClass("checked").addClass("undetermined");
							return false;
						} else
							$(this).children("a").removeClass("unchecked").removeClass("undetermined").addClass("checked");
					} else {
						if ($(this).find("a.checked, a.undetermined").size() - 1 > 0) {
							$(this).parents("li").andSelf().children("a").removeClass("unchecked").removeClass("checked").addClass("undetermined");
							return false;
						} else
							$(this).children("a").removeClass("checked").removeClass("undetermined").addClass("unchecked");
					}
				});
			}
		}
	}};
if (conf.ui.theme_name == "checkbox") {
	conf.callback.onload = function(treeobj) {
		var div = treeobj.container;
		//div.find('a').before('<span/>');
		$.jytree.setState(div.find('li:first'), treeobj);
		treeobj.open_all();
	};
	conf.callback.oncreate = function(node, treeobj) {
		$.jytree.setState($(node),treeobj);
	};
} else {
	conf.callback.onload = function(treeobj) { treeobj.open_all(); };
}
if ($(this).data('tree')) {
	var roleTree = $(this).data('tree');
	roleTree.destroy();
	roleTree.selected = null;
	roleTree.init($(this), $.extend({}, conf));
} else {
	var roleTree = $.tree_create();
	$(this).data('tree', roleTree);
	roleTree.init($(this), $.extend({}, conf));
}

}
});
})(jQuery);

