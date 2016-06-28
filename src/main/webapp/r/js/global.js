// -- fixed IE8 indexOf function
if (!Array.prototype.indexOf){Array.prototype.indexOf = function(elt /*, from*/) {var len = this.length >>> 0; var from = Number(arguments[1]) || 0; from = (from < 0) ? Math.ceil(from)  : Math.floor(from);if (from < 0)  from += len; for (; from < len; from++) { if (from in this &&  this[from] === elt)  return from; }return -1;}; }
// global key event
var globalKeyEvent = function(){var e=event,k=e.keyCode,s=e.srcElement;if(k==13)e.keyCode=9;if(k==8&&(s.type!="text"&&s.type!="textarea"&&s.type!="password"))return false;};
// sort Array By Field (MultiFields split by comma); sort field must be number
var sortByFields =function (_arr, _flds){ if ( _arr ) { _arr.sort(function(_a,_b){ var _fldsArr = _flds.split(','); for(var _i in _fldsArr) { var _fld = _fldsArr[_i]; if (_a[_fld]==_b[_fld]) { continue; } else { return _a[_fld]-_b[_fld]; }} }); }  return _arr; };
// isArray
var isArray = function (object){ return object && typeof object==='object' && Array == object.constructor; }
var frameCtx=function(str){str = str.replace(/<\/?[^>]*>/g,''); /*去除HTML tag*/ str.value = str.replace(/[ | ]*\n/g,'\n'); /*去除行尾空白*/ /*str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行*/ return str; };
var handleFileName = function(id,tValue){ if ( tValue ) { var t1 = tValue.lastIndexOf("\\");  var t2 = tValue.length; if(t1 >= 0 ){ if (t1 < t2 ){ $("#"+id).val(tValue.substring(t1 + 1));  } } else { $("#"+id).val(tValue); } } };