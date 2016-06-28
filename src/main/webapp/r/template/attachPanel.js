// Attach List API
// build AttachList
var AttachPanel = function(_opt) {
	this.panelId = _opt.id;
	this.editable = _opt.editable?true:false;
	this.addImgBtnClick = _opt.addImgBtnClick ;
	this.athMap = new Object();
	AttachPanel.imageFileExtend = ".gif,.png,.jpg,.jpeg,.bmp";
	this.buildAttachPanel = function(attachlist) {
		var _this = this;
		// clean
		var $attachOper = $('#'+this.panelId); 
		$('#'+this.panelId + ' .panel').remove();
		$attachOper.addClass('attachView');
		if ( this.editable ) {
			$attachOper.addClass('checkableAttachPanel');
		}
		
		$(attachlist).each(function(_i,_n){
			var _type = _n.attachType;
			var _text = _n.attachName;
			var $ath = $('<div class="panel panel-default"></div>');
			var $ul = $('<ul></ul>').attr('id', 'ATTACH_' + _type).addClass('');
			if (_this.editable ) { $ul.append(_this.addImgBtn(_type) );}
			$ath.append($('<div class="panel-heading"></div>').html(_text))
				.append($('<div class="panel-body"></div>').append($ul));
			$attachOper.append($ath);
			_this.athMap[_type] = $ul;
		});
		// build others 
		var $other = $('<div class="panel panel-default"></div>');
		var $ul = $('<ul></ul>').attr('id', 'ATTACH_OTHER').addClass('');
		if ( _this.editable ) { $ul.append(_this.addImgBtn('OTHER') );}
		$other.append($('<div class="panel-heading"></div>').html('其他'))
			.append($('<div class="panel-body"></div>').append($ul));
		$attachOper.append($other);
		this.athMap['other'] = $ul;
	};
	// clean panel content
	this.resetAttachPanel = function() {
		$('#' + this.panelId + ' .panel .panel-body ul li').not('.addImgBtn').remove('');
	};
	// clean checked style 
	this.uncheckAll = function() {
		$('#' + this.panelId + ' .panel .panel-body ul li.checked').removeClass('checked');
	};
	this.checkAll = function() {
		this.uncheckAll();
		$('#' + this.panelId + ' .panel .panel-body ul li').not('.addImgBtn').addClass('checked');
	};
	// add button to upload attach
	this.addImgBtn = function(_type) {
		var _this = this;
		var $li = $('<li></li>').addClass('addImgBtn').on('click',function(){ _this.addImgBtnClick.apply(this, [_type]); });
		return $li;
	};
	// Add Single Attach 
	this.addSingleAttach = function(attach) {
		var _url = attach.attachUrl, _time = attach.attachTime, _type = attach.attachType;
		var $ul = this.athMap[_type]?this.athMap[_type]:this.athMap['other'];
		var $li = $('<li></li>').on('click',function(){$(this).toggleClass('checked');});
		var $content ;
		var extname = _url.substring(_url.lastIndexOf('.')).toLowerCase();
		var filename = _url.substring(_url.lastIndexOf('/')+1);
		if (AttachPanel.imageFileExtend.indexOf(extname)>-1) {
			$content = $('<img />').attr('src', _url+'?show=thumbnail').addClass('img-responsive');
		} else {
			$content = $('<label></label>').html(filename).addClass('');
		}
		$li.append($('<span></span>')).append($('<a></a>').attr({href:_url,target:'_blank',alt:_time,seq:attach.attachSeq}).append($content));
		if ( this.editable ) {
			$li.insertBefore($ul.find('.addImgBtn'));
		} else {
			$ul.append($li);
		}
	};
	// Add Attach into Panel
	this.addToAttachPanel = function(attach) {
		var _this = this;
		if ( isArray(attach) ) {
			$(attach).each(function(_i,_n){ _this.addSingleAttach(_n); });
		} else {
			this.addSingleAttach(attach);
		}
	};
	// Get checked attach's urls
	this.getCheckedUrls = function() {
		var urls = '';
		$('#'+this.panelId + ' .panel .panel-body ul li.checked a').each(function(_i,_n){
			var url = $(_n).attr('href');
			urls = (urls==''?'':(urls+',')) + url
		});
		return urls;
	};
	// Get checked attach's seqs
	this.getCheckedSeqs = function() {
		var seqs = '';
		$('#'+this.panelId + ' .panel .panel-body ul li.checked a').each(function(_i,_n){
			var seq = $(_n).attr('seq');
			seqs = (seqs==''?'':(seqs+',')) + seq
		});
		return seqs;
	};
	// Remove checked attach
	this.removeChecked = function () {
		$('#' + this.panelId + ' .panel .panel-body ul li.checked').remove();
	};
	return this;
};