var MainFrame = function(_conf) {
	return this.init(_conf);
};
MainFrame.prototype = {
init:function(_conf) {
	var m = {
		 DEFAULT: {MIN_HEIGHT:300,MIN_WIDTH:400}
		,settings:{
			 sid:false
			,indexName:'首页'
			,menuId:'body-menu-list'
			,menuNodeId:'menu-node-text'
//			,menuBoxId:'body-menu-list-content'
			,titleId:'top-info-title'
			,frameBoxId:'body-view'
			,menuNode:false
			,openListId:'open-list'
		}
		,msgStore: new Array()
		,failedTimes: 0
		,current: false
		,__init:function() {
			var $m = $('#' + this.settings.sid);
			$('.jy-ui-row', $m).each(function(_i,_n){
				var _rfh = $(_n).attr('fh')?$(_n).attr('fh'):'auto';
				$(_n).attr('fh',_rfh);
				$('.jy-ui-col', this).each(function(_i0,_n0){
					$(_n0).attr('fh',_rfh);
					if (!$(_n0).attr('fw')){ $(_n0).attr('fw','auto');} 
				});
			}).css({width:'100%'});
			$('.jy-ui-row,.jy-ui-col', $m).each(function(_i,_n){
				var _fh = $(_n).attr('fh');
				var _fw = $(_n).attr('fw');
				if ( _fh != 'auto' ) { $(_n).css({height:_fh + 'px'});}
				if ( _fw != 'auto' ) { $(_n).css({width:_fw + 'px'});}
			});
			this.autosize();
			this.initMenu();
			$('#' + this.settings.openListId).find('#indexpage-li a').click();
			var _this = this;
			$('#'+this.settings.titleId).hover(
					 function(){if(_this.current=='indexpage'){$('ul',this).hide();return;}  if( _this.current ) $('ul',this).show(); }
					,function(){if( _this.current ) $('ul',this).hide();});
		}
		,showFrame:function(_id, _li) {
			$('#'+this.settings.frameBoxId).children().hide();
			$('#'+this.settings.frameBoxId).find('#' + _id).show();
			if ( _li ) { } else {
				_li = $('#' + _id);
			}
			this.current = $(_li).attr('id');
			this.shiftMenu(_li);
			this.showTitle(_li);
		}
		,refreshFrame:function(_id) {
			var _fid = _id?_id:this.current;
			var frame= $('#'+this.settings.frameBoxId).find('#'+_fid+'-iframe');
			frame.attr('src',frame.attr('src'));
		}
		,closeFrame:function(_id){
			var _fid = _id?_id:this.current;
			$('#'+this.settings.frameBoxId).find('#'+_fid).remove();
			if( _fid == this.current){
				var $li = $('#'+_fid+'-li').prev();
				$li.find('a').click();
				var _preId = $li.find('a').attr('href');
				$(_preId).addClass('active');
				var _preParentid = $(_preId).attr('parentid');
				$('#'+_preParentid).click();
			}
			$('#'+ _fid +'-li').remove();
			$('#'+_fid).removeClass('active');
		}
		,showTitle:function(_li) {
			$('#'+this.settings.titleId).find('h2').html($(_li).attr('text'));
		}
		,addToOpenlist:function(_li) {
			// add to open list
			var _id = $(_li).attr('id');
			var _this = this;
			var $oli = $('<li id="'+_id+'-li"/>').attr('role',$(_li).attr('id')).append($('<a />').html($(_li).attr('text'))
					.attr('href','#'+_id).click(function(){
						_this.showFrame(_id,_li);
						$('#'+ _this.settings.openListId+'-container').hide();
			}));
			var $close = $('<span class="ui-icon ui-icon-close" >close</span>').click(function(){_this.closeFrame(_id);});
				// $('<span class="ui-state-default" ></span>').append().hover(function(){$(this).addClass('ui-state-hover');},function(){$(this).removeClass('ui-state-hover');});
			$oli.append($('<span class="ui-state-highlight" title="关闭页面"></span>').css({'display':'block','background':'none','float':'right','marginTop':'6px','marginRight':'6px','border':'0'}).append($close).hover(function(){$(this).addClass('ui-state-white');},function(){$(this).removeClass('ui-state-white');}));
			$('#' + this.settings.openListId).children().removeClass('opened'); 
			$oli.addClass('opened');
			$('#'+ this.settings.openListId).append($oli);
		}
		,shiftMenu:function(_li) {
			// shift menu to open state
			//var oms = new Object();oms.nav = $(menu).attr('nav'); oms.menu = $(menu).attr('id'); oms.id = _id;
			//openmenustore[_id]=oms;
			//mockmenu(oms);
		}
		,openFrame:function(_li) {
			var _id = $(_li).attr('id');
			var $li = $(_li);
			// $li.parent().children().removeClass('active');
			$li.parent().parent().addClass('active');
			$li.addClass('active');
			var $fb = $('#'+this.settings.frameBoxId);
			var _this = this;
			this.current = $(_li).attr('id');
			
			// locate nav and menu
			if ( $fb.find('#' + _id).length != 0 ) {
				this.showFrame(_id,_li);
			} else {
				$fb.children().hide();
				this.showTitle(_li);
				var $div = $('<div id="'+_id+'"/>');
				var $frame = $('<iframe id="'+_id+'-iframe"/>')
					.attr('src', $li.attr('href'))
					.css({height: $fb.height(), width: $fb.width()})
					.load(function(){ 
						$(this).contents().find("body").height($fb.height()).width($fb.width());
						document.getElementById($(this).attr('id')).contentWindow.afterload(); 
					});
				$fb.append($div.append($frame));
				// add to openlist
				this.addToOpenlist(_li);
			}
			this.shiftMenu(_li);
		}
		,initMenu:function() {
			var _md = sortByFields(this.settings.menuNode[0].children,'sort');
			var $mn = $('#'+this.settings.menuId); $mn.html('');
			//var _ms = this.menuStore;
			//var $mb = $('#'+this.settings.menuBoxId); $mb.html('');
			var _this = this;
			// index page
			$mn.append($('<li/>').attr({'id':'indexpage','text':this.settings.indexName}).append('<i class="style-indexpage">&nbsp;</i>').append($('<a />').attr('href','javascript:void(0);').html(this.settings.indexName))
//			.append($('<ul/>').addClass('nav').append($('<li/>').attr({'id':'indexpage','text':this.settings.indexName})
//							.append($('<a />').attr('href','javascript:void(0);').html(this.settings.indexName))
//					))
					);
			$('#'+ this.settings.openListId).append($('<li id="indexpage-li"/>').append($('<a />').html(this.settings.indexName)
					.click(function(){
						_this.showFrame('indexpage');_this.showTitle('#indexpage');
						//$('#body-menu-list div li').removeClass('active');
						$('li',$mn).removeClass('active');
						// $('#indexpage').parent().addClass('active');
						$('#indexpage').addClass('active');
						$('#indexpage').click();
						})));
			/*$('#indexpage').click(function(){
				_this.showFrame('indexpage');
				_this.showTitle('#indexpage');
				$('#body-menu-list div li').removeClass('active');
				$(this).addClass('active');
			});*/
			$(_md).each(function(_i,_n){
				var $mnli = $('<li/>').attr(_n).removeAttr('children')
					.append('<i class="style-'+_n.id+'">&nbsp;</i>')
					.append($('<a />').attr('href','javascript:void(0);').html(_n.text))
					.append('<i class="style-back">&nbsp;</i>');
				$mn.append($mnli); // _ms[_n.id]=_n;
				var $mbul = $('<ul />').attr('id','box-'+_n.id).addClass('nav');
				_this.loadSMenu(_n.children, $mbul, 1);
				$mnli.append($mbul);
			});
			$('li', $mn).not('#indexpage').click(function(e){
				$('li',$mn).removeClass('active');
				if ( $(this).find('ul.nav').length>0 ) {
					$mn.find('ul.nav').hide();
					$(this).find('ul.nav').show(); 
					$(this).addClass('active');
					$(this).find('ul.nav li#'+_this.current).addClass('active');
				} else {
					_this.openFrame(this);
				}
				e.stopPropagation();
			});
		}
		,loadSMenu:function(_ns, _box, _lv) {
			var _smd = sortByFields(_ns,'sort');
			var _this = this;
			$(_smd).each(function(_i,_n){
				var $smnli = $('<li/>').attr(_n).removeAttr('children')
					.append('<i class="style-menu">&nbsp;</i>')
					.append($('<a />').attr('href','javascript:void(0);').html(_n.text))
					;
				if ( _n.children && _n.children.length > 0 ) {
					var $sul = $('<ul />').addClass('nav');
					_this.loadSMenu(_n.children,$sul,_lv+1);
					$smnli.append($sul);
				}
				_box.append($smnli);
			});
		}
		,autosize:function() {
			var $m = $('#' + this.settings.sid);
			var _bh = document.body.offsetHeight ;
			_bh = (_bh? (_bh < this.DEFAULT.MIN_HEIGHT ?this.DEFAULT.MIN_HEIGHT:_bh ):this.DEFAULT.MIN_HEIGHT) ;
			var _bw = document.body.offsetWidth;
			_bw = (_bw? (_bw < this.DEFAULT.MIN_WIDTH ? this.DEFAULT.MIN_WIDTH:_bw ):this.DEFAULT.MIN_WIDTH) ;
			$m.css({height:_bh,width:_bw,overflow:'hidden',top:'0',left:'0',position:'absolute'});
			var _tth = 0; var _autoHCnt = 0;
			$('.jy-ui-row[fh!="auto"]', $m).each(function(_i,_n){
				_tth += $(_n).height();
			});
			$('.jy-ui-row[fh="auto"]', $m).each(function(_i,_n){
				_autoHCnt ++ ;
			});
			var _top=0,_left=0;
			var _autoHeight = (_bh - _tth) / _autoHCnt ; // alert(_bh+ ':'+_tth+':'+_autoHCnt);
			$('.jy-ui-row',$m).each(function(_i,_n){
				var _ttw = 0; var _autoCnt = 0;
				$('.jy-ui-col[fw!="auto"]', $(_n)).each(function(_i0,_n0){
					_ttw += $(_n0).width();
				});
				$('.jy-ui-col[fw="auto"]', $(_n)).each(function(_i0,_n0){
					_autoCnt ++ ;
				});
				var _autoWidth = (_bw - _ttw) / _autoCnt ; // alert(_bw+ ':'+_ttw+':'+_autoCnt);
				var _height = $(_n).attr('fh')=='auto'?_autoHeight:$(_n).height();
				$('.jy-ui-col', $(_n)).each(function(_i0,_n0){
					var _width = $(_n0).attr('fw')=='auto'?_autoWidth:$(_n0).width();
					$(_n0).css({position:'absolute',top:_top+'px',left:_left+'px',width:_width+'px',height:_height+'px'});
					_left += _width ; 
				});
				_left = 0;
				_top += _height;
			});
			$('.jy-ui-fixheight').each(function(_i,_n){
				var _ph = $(_n).parent().height();
				var _tth = 0;
				$(_n).parent().children(':visible').not($(_n)).each(function(_i0,_n0){
					_tth += $(_n0).height();
				});
				$(_n).css({height:(_ph-_tth)+'px'});
			});
			$('.jy-ui-fixwidth').each(function(_i,_n){
				var _pw = $(_n).parent().width();
				var _ttw = 0;
				$(_n).parent().children(':visible').not($(_n)).each(function(_i0,_n0){
					_ttw += $(_n0).width();
				});
				$(_n).css({width:(_pw-_ttw)+'px'});
			});
			var _this = this;
			
			/*var MIN_HEIGHT = 300, MIN_WIDTH = 400;
			var _bh = document.body.offsetHeight - $('#top-band').height();
			_bh = (_bh? (_bh < MIN_HEIGHT ? MIN_HEIGHT:_bh ):MIN_HEIGHT) +'px';
			var _bw = document.body.offsetWidth - $('#body-menu').width();
			_bw = (_bw? (_bw < MIN_WIDTH ? MIN_WIDTH:_bw ):MIN_WIDTH) +'px';
			$('#body-view iframe').css({height:_bh,width:_bw});
			$('#body-view iframe').each(function(){$(this).contents().find("body").height($('#body-view').height()-8);});*/
			$('.jy-ui-framebox').each(function(_i,_n){
				var _pw = $('#' + _this.settings.frameBoxId).width();
				var _ph = $('#' + _this.settings.frameBoxId).height();
				$(this).children().css({height:_ph,width:_pw,overflow:'hidden'});
				$(this).find('iframe').css({height:_ph,width:_pw,overflow:'hidden'});
				$(this).find('iframe').each(function(){$(this).contents().find("body").height(_ph).width(_pw);});
			});
		}
	};
	$.extend(m.settings, _conf);
	m.__init();
	return m;
}
};