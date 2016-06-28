/**
 * Field Builder 
 * Usage: var fb = new FieldBuilder([ {name:'mchntCode', desc:'内部商户号', jtype:'Long', length: 10},{...},{...}]);
 * fb.buildFormFields('#id',['mchntCode',[{},{}]], {readonly:true});
 * 
 * Field {name,desc,maxlength,readonly:,pattern, maxlength,minlength, required, max,min, id, ctype="", unit:[元|月|日|%], xtype=[money|date|datetime|select|textarea], placeholder }
 * readonly
 * validate: regex, stringlength, notempty, between 
 */
var FieldBuilder = function ( _cols ) {
	// {name:'mchntCode', desc:'内部商户号', jtype:'Long', length: 10}
	this.columns = _cols;
	// init fieldMap
	this.columnMap = new Object (); 
	FieldBuilder.MAX_VAL = [0,9,99,999,9999,99999,999999,9999999,99999999,999999999,9999999999,99999999999,999999999999];
	var _columnMap = this.columnMap;
	$(_cols).each(function(_i,_n){
		if ( _n && _n.name) {
			_columnMap[_n.name] = _n;
		}
	});
	// 从column对象转换到表单对象
	this.col2Field = function(_column) {
		var field = $.extend({}, _column);
		if ( _column.jtype=='String' ) {
			field.maxlength = _column.length;
		} else if ( field.jtype=='Long' ) {
			var flen = field.length;
			if ( field.xtype=='rate' || field.xtype == 'money' ) {
				field.max = FieldBuilder.MAX_VAL[field.length-2];
			} else {
				field.max = FieldBuilder.MAX_VAL[field.length];
			}
		}
		return field;
	};
	// 构造表单元素
	this.buildField = function(_f, _fCount) {
		var grp_col = 12 / _fCount ; 
		var label_col = _fCount == 1 ? 2 : 4 ;
		var field_col = _fCount == 1 ? 9 : 8 ;
		var id = _f.id?_f.id:(_f.fid+'-'+_f.name);
		var $fg = $('<div />').addClass('form-group');
		if ( _fCount != 1 ) {
			$fg.addClass('col-sm-'+grp_col);
		}
		// create label
		var $label = $('<label />').addClass('control-label col-sm-'+label_col).html( _f.desc + ":");
		var $incol = $('<div />').addClass('col-sm-' + field_col);
		var $parent = $incol ;
		if ( _f.unit || _f.preunit) {
			var $ig = $('<div />').addClass('input-group');
			$incol.append($ig);
			$parent = $ig;
		} 
		var $component;
		if ( (_f.xtype && _f.xtype == 'select') || _f.ctype ) {
			$component = $('<select ></select>'); 
			$component.attr($.extend({id:id}, _f)).addClass('form-control');
		} else if (_f.xtype && _f.xtype == 'textarea') {
			$component = $('<textarea ></textarea>');
			$component.attr($.extend({id:id}, _f)).addClass('form-control');
		} else if ( _f.xtype == 'date' || _f.xtype == 'datetime' ) {
			var $value = $('<input type="hidden" />');
			$value.attr($.extend({id:id}, _f));
			$parent.append($value);
			var $component = $('<input type="text" />').attr({'id':id + '-disp', 'name': _f.name + '-disp'}).addClass('form-control');
			if ( _f.readonly ) $component.attr('readonly', _f.readonly);
			if ( _f.required ) $component.attr('required', _f.required);
			if ( _f.placeholder ) $component.attr('placeholder', _f.placeholder);
		} else {
			$component = $('<input type="text" />');
			$component.attr($.extend({id:id}, _f)).addClass('form-control');
		}
		if ( _f.preunit ) {
			var $preunit = $('<span class="input-group-addon"></span>').html(_f.preunit);
			$parent.append($preunit);
		}
		$parent.append($component);
		if ( _f.unit ) {
			var $unit = $('<span class="input-group-addon"></span>').html(_f.unit);
			$parent.append($unit);
		}
		$fg.append($label);
		$fg.append($incol);
		return $fg;
	};
	// 构造row
	this.buildRow = function(_fld) {
		var $row = $('<div />').addClass('row');
		var __this = this;
		if ( isArray(_fld) ) {
			var count = _fld.length;
			$(_fld).each(function(_i,_n) {
				if (_n) {
					$row.append(__this.buildField(_n, count));
				}
			});
		} else {
			if ( _fld) {
				$row.append(this.buildField(_fld, 1));
			}
		}
		return $row;
	};
	// 获取元素，存在嵌套
	this.buildElement = function(_el,_def) {
		var def = {}; 
		$.extend(def,_def);
		var obj = _el;
		
		if ( isArray(obj) ) {
			var fields = new Array();
			for ( var i in obj ) {
				fields.push(this.buildElement(obj[i], def));
			}
			return fields;
		} else if ( typeof obj == "string" ) {
			var column = _columnMap[obj];
			if ( column ) {
				
				return $.extend(def, this.col2Field(column));
			}
		} else if ( typeof obj == "object") {
			var column = _columnMap[obj.name];
			if ( column ) {
				return $.extend(this.col2Field(column), def, obj);
			}
		} 
	};
	/**
	 * _id: 目标id
	 * _arrs: [[{},{},{}],{}]
	 * _def: 默认配置
	 */
	this.buildFormFields = function(_id, _arrs, _def) {
		var $target = $('#'+_id);
		var def = _def?_def:{};
		def = $.extend({fid:_id}, def);
		for ( var i in _arrs) {
			var field = this.buildElement(_arrs[i], def);
			$target.append(this.buildRow(field));
		}
	};
};