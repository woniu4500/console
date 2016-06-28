/*
 * Flexigrid for jQuery - New Wave Grid
 *
 * Copyright (c) 2008 Paulo P. Marinas (webplicity.net/flexigrid)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * $Date: 2008-07-14 00:09:43 +0800 (Tue, 14 Jul 2008) $
 */

(function($) {

	$.addFlex = function(t, p) {

		if (t.grid)
			return t; // return if already exist
		// apply default properties
		p = $.extend({
			height : 300, // default height
			extHori: false, // 
			width : 'auto', // auto width
			striped : true, // apply odd even stripes
			novstripe : false,
			minwidth : 30, // min width of columns
			minheight : 200, // min height of columns
			resizable : false, // resizable table
			url : false, // ajax url
			method : 'POST', // data sending method
			dataType : 'json', // type of data loaded
			errormsg : '连接错误',
			usepager : false, //
			nowrap : true, //				
			start : 0,
			sort : "",
			dir:"desc",
			page : 1, // current page
			total : 0, // total pages
			useRp : true, // use the results per page select box
			limit : 20, // results per page
			limitOptions : [20, 40, 60, 80, 100],
			title : false,
			pagestat : '显示 {from} 至  {to} 条记录  共  {total} 条',
			procmsg : '加载中，请稍候...',
			nomsg : '未找到记录',
			pagesize : '&nbsp;每页&nbsp;',
			minColToggle : 1, // minimum allowed column to be hidden
			showToggleBtn : true, // show or hide column toggle popup
			hideOnSubmit : true,
			clickRecord:null,
			autoload : true,
			checkbox : false, // 是否选择多选框
			blockOpacity : 0.5,
			onToggleCol : false,
			onChangeSort : false,
			dbClickRecord : false,
			onSuccess : false,
			ajaxSuccess : false,
			ajaxFail : false,
			onSubmit : false,// using a custom populate function,
			root : 'root',
			singleSelect : true,
			totalProperty : 'totalProperty',
			dataRecord : '',
			dataStore : [],
			colModel : [],
			viewStore : [],
			jsonfilter : [],
			enableLoadFirst : false,
			// some delete items
			index : ''
		}, p);

		$(t).show() // show if hidden
		.attr({
			cellPadding : 0,
			cellSpacing : 0,
			border : 0
		}) // remove padding and spacing
		.removeAttr('width') // remove width properties
		;

		var g = {
			// copy
			objClone : function(obj) {
				var result = new Object();
				for ( var i in obj) { result[i] = obj[i]; }
				return result;
			},
			// 存储数据
			dataStore : function(data) {
				p.index = null;
				p.dataRecord = null;
				p.dataStore = [];
				p.viewStore = [];
				$(data[p.root]).each(function(i) {
					var datax = data[p.root][i];
					p.dataStore.push(g.objClone(datax));
					p.viewStore.push(g.objClone(datax));
				});
			},

			buildpager : function() { // rebuild pager based on new properties
				$('.pcontrol input', this.pDiv).val(p.page);
				$('.pcontrol span', this.pDiv).html(p.pages);
				var r1 = (p.page - 1) * p.limit + 1;
				var r2 = r1 + p.limit - 1;

				if (p.total < r2)
					r2 = p.total;

				var stat = p.pagestat;

				stat = stat.replace(/{from}/, r1);
				stat = stat.replace(/{to}/, r2);
				stat = stat.replace(/{total}/, p.total);
				$('.pPageStat', this.pDiv).html(stat);

			},

			// --
			addRowProp : function() {
				$('tbody tr', g.table).each(function(index) {
					$(this).click(function(e) {
						$(this).toggleClass('trSelected');

						var obj = (e.target || e.srcElement);
						if (obj.href || obj.type) {
							if ($(this).hasClass('trSelected')) {
								p.index = index;
								p.dataRecord = p.dataStore[index];
							} else {
								p.index = null;
								p.dataRecord = null;
							}
							return true;
						}

						if ($(this).hasClass('trSelected')) {
						
							p.index = index;
							p.dataRecord = p.dataStore[index];
							if(p.clickRecord){								
								p.clickRecord.apply(t,[p.dataRecord,p.index]);
							}
						} else {
							p.index = null;
							p.dataRecord = null;
						}
						if (p.checkbox) {
							check = $(this).find('input')[0];
							check.checked = !check.checked;
						}
						if (!p.checkbox)
						$(this).siblings().removeClass('trSelected');
						
						
					}).hover(function(){$(this).addClass("hover");},
							function(){$(this).removeClass("hover");}
					).dblclick(
							function (e){
								if(p.dbClickRecord){
									p.index = index;
									p.dbClickRecord(p.dataStore[index],index);
								}
							}	
						);
				});

			},

			changePage : function(ctype) { // change page
				if (this.loading)
					return true;
				switch (ctype) {
				case 'first':
					p.start = 0;
					p.newp = 1;
					break;
				case 'prev':
					if (p.page > 1) {
						p.start = p.start - p.limit;
						p.newp = parseInt(p.page) - 1;
					}
					break;
				case 'next':
					if (p.page < p.pages) {
						p.start = p.start + p.limit;
						p.newp = parseInt(p.page) + 1;
					}
					break;
				case 'last': {
					p.start = (p.pages - 1) * p.limit;
					p.newp = p.pages;
				}
					break;
				case 'input':
					var nv = parseInt($('.pcontrol input', this.pDiv).val());
					if (isNaN(nv))
						nv = 1;
					if (nv < 1)
						nv = 1;
					else if (nv > p.pages)
						nv = p.pages;
					$('.pcontrol input', this.pDiv).val(nv);
					p.newp = nv;
					p.start = (nv - 1) * p.limit;
					break;
				}
				if (p.newp == p.page)
					return false;

				if (p.onChangePage)
					p.onChangePage(p.newp);
				else
					this.populate();

			},
			// 刷新表格
			populate : function() {
				if (!p.enableLoadFirst)
					return false;
				if (this.loading)
					return true;
				if (!p.url)
					return false;
				this.loading = true;
				$('.pPageStat', this.pDiv).html(p.procmsg);
				$('.pReload', this.pDiv).addClass('loading');
				if (!p.newp)
					p.newp = 1;
				var param = [ {
					name : 'start',
					value : p.start
				}, {
					name : 'limit',
					value : p.limit
				}, {
					name : 'sort',
					value : p.sort
				}, {
					name : 'dir',
					value : p.dir
				}, {
					name : 'jsonfilter',
					value : p.jsonfilter
				} ];

				if (p.params) {
					for ( var pi = 0; pi < p.params.length; pi++)
						param[param.length] = p.params[pi];
				}
				if (p.ajaxStart)
					p.ajaxStart();
				
				$.ajax({
					type : p.method,
					url : p.url,
					data : param,
					dataType : p.dataType,
					success : function(data) {
						if ( data ) {
							if ( data.success) {
								g.dataStore(data);
								g.addData(data);
								return;
							} else {
								if ( data.syserr ) { alert(data.syserr); }
								else { alert('加载数据失败！'); }
								
							}
						} else { alert('加载数据失败，未获取响应内容！'); }
						try {
							$('.pReload', this.pDiv).removeClass('loading');
							g.loading = false;							
							$('.pPageStat', this.pDiv).html(p.errormsg);						
							if (p.ajaxFail) p.ajaxFail(data);
						} catch (e) { }
					},
					error : function(data) {
						try {
							
							$('.pReload', this.pDiv).removeClass('loading');
							g.loading = false;							
							$('.pPageStat', this.pDiv).html(p.errormsg);						
							if (p.ajaxFail)
								p.ajaxFail(data);
						} catch (e) {
						}
					}
				});

			},

			changeSort : function(th) { // change sortorder
				if (this.loading) return true;
				if (p.dir == 'asc') { p.dir = 'desc'; } else { p.dir = 'asc'; }
				$(th).addClass('sorted').siblings().removeClass('sorted');
				$('.sdesc', g.table.head).removeClass('sdesc');
				$('.sasc', g.table.head).removeClass('sasc');
				$(th).addClass('s' + p.dir);
				p.sort = $(th).attr('sortField');
				this.populate();
			},
			
			 //single data operation
			 sigleDataStore:function(record){
				$(p.colModel).each(function(i){
					if(p.colModel[i].renderer){
						$('thead tr:first th',g.hDiv).each
						(
							function (){
								var renderer_col_name = $(this).attr('abbr');
								if(p.colModel[i].dataIndex == renderer_col_name){
									record[renderer_col_name] = p.colModel[i].renderer(record);
								}
							}
						);
					
					}
				});
				  return record;
			 },

			// 刷新数据
			addData : function(data) {
				// ///-------

				$('.pReload', this.pDiv).removeClass('loading');
				this.loading = false;
				if (!data) {
					$('.pPageStat', this.pDiv).html(p.errormsg);
					return false;
				}
				p.total = data[p.totalProperty];
				$(this.table.body).empty();
				if (p.total == 0) {
					$('tr, a, td, div', this.table).unbind();
					p.pages = 1;
					p.page = 1;
					this.buildpager();
					$('.pPageStat', this.pDiv).html(p.nomsg);
					return false;
				}

				p.pages = Math.ceil(p.total / p.limit);
				p.page = Math.floor(p.start / p.limit) + 1;
				this.buildpager();
				$.each(p.viewStore, function(i, row) {
					var tr = document.createElement('tr');
					if(p.striped && i%2 ) { tr.className = 'erow'; }
					if (row) { tr.id = 'row' + i ;};
					// add cell
					if (p.checkbox) {
						var td = $('<td />');
						var tdiv = $('<div />').addClass('iCellHead');
						tdiv.css({'overflow':'hidden','width':'22px','text-align':'center'});
						tdiv.append(inner);
						var inner = ''; 
						if(row.check){
							inner = "<input type='checkbox' checked='checked'/>";
							$(tr).addClass('trSelected');
						} else {
							inner = "<input type='checkbox'/>";
						}
						tdiv.append(inner);
						td.append(tdiv);
						td.addClass('ctd');
						$(tr).append(td);
					}
				
					$('tr:first th[hide!=true]', g.table.head).each(
							function(i) {
								var td = $('<td />');//document.createElement('td');
								var idx = $(this).attr('axis').substr(3);
								var col_name = $(this).attr('abbr');
								var colvalue = row[col_name];
								
								var inner = '';
								if (this.render) {
									inner = this.render(row, colvalue,tr);
								} else if (this.decode) {
									if (this.decode[colvalue]) {
										inner = this.decode[colvalue];
									} else {
										inner = this.decode.noMatch;
									}
								} else if (colvalue) {
									inner = row[col_name];
								}
								//td.attr('title',inner);//td.innerHTML;
								var tdiv = $('<div/>').addClass('iCell');
								//tdiv.attr('width',this.width);
								tdiv.css({'overflow':'hidden','width':$(this).attr('cmwidth')+'px','textAlign':$(this).attr('innerAlign')||'center'});
								tdiv.append(inner);
								tdiv.attr('fld', col_name);
								tdiv.attr('val', colvalue);
								td.append(tdiv);
								$(tr).append(td);
								td = null;
							});
					
					g.table.body.append($(tr));
				});
				g.addRowProp();

			},
			rePosDrag: function () {
				var cdleft = 0 - this.hDiv[0].scrollLeft;
				if (this.hDiv[0].scrollLeft>0) {cdleft -= Math.floor(p.cgwidth/2);};
				if ( p.checkbox ) { cdleft += 21; } else { cdleft -= 2; }
				g.cDrag.css({top:g.hDiv[0].offsetTop+1});
				var cdpad = this.cdpad;
				
				$('thead tr:first th:visible',this.hDiv[0]).each ( function () {
					var n = $('thead tr:first th:visible',g.hDiv[0]).index(this);
					var cdpos = parseInt($('div',this).width());
					//var ppos = cdpos;
					if (cdleft==0) { cdleft -= Math.floor(p.cgwidth/2);} 
					cdpos = cdpos + cdleft + cdpad;
					//alert(n+':'+ppos+':'+cdleft+':'+cdpad+':'+cdpos);
					$('div:eq('+n+')',g.cDrag[0]).css({'left':cdpos+'px'}).show();
					cdleft = cdpos;
				});
			},
			fixHeight: function (newH) {
				//newH = false;
				//if (!newH) newH = g.bDiv.height();
				var cdheight = $(g.bDiv).height();
				var hdheight = $(g.hDiv).height();
				cdheight = cdheight == 0 ? 24:cdheight;
				hdheight = hdheight == 0 ? 24:hdheight;
				//var hdHeight = this.hDiv.height();
				$('div',this.cDrag[0]).each( function () { $(this).height(cdheight+hdheight); } );
				//var nd = parseInt($(g.nDiv).height());
				//if (nd>newH) $(g.nDiv).height(newH).width(200);
				//else $(g.nDiv).height('auto').width('auto');
				//$(g.block).css({height:newH,marginBottom:(newH * -1)});
				//var hrH = g.bDiv.offsetTop + newH;
				//if (p.height != 'auto' && p.resizable) hrH = g.vDiv.offsetTop;
				//$(g.rDiv).css({height: hrH});
			},
			// drag event
			dragStart: function (dragtype,e,obj) { //default drag function start
				if (dragtype=='colresize') {
					// $(g.nDiv).hide();$(g.nBtn).hide();
					var n = $('div',this.cDrag).index(obj);
					var ow = $('th:visible div:eq('+n+')',this.hDiv).width();
					$(obj).addClass('dragging').siblings().hide();
					$(obj).prev().addClass('dragging').show();
					this.colresize = {startX: e.pageX, ol: parseInt(obj.style.left), ow: ow, n : n };
					$('body').css('cursor','col-resize');
				} else if (dragtype=='vresize') {
					var hgo = false;
					$('body').css('cursor','row-resize');
					if (obj) { hgo = true; $('body').css('cursor','col-resize'); }
					this.vresize = {h: p.height, sy: e.pageY, w: p.width, sx: e.pageX, hgo: hgo};
				} else if (dragtype=='colMove') {
					// $(g.nDiv).hide();$(g.nBtn).hide();
					this.hset = $(this.hDiv).offset();
					this.hset.right = this.hset.left + $('table',this.hDiv).width();
					this.hset.bottom = this.hset.top + $('table',this.hDiv).height();
					this.dcol = obj;
					this.dcoln = $('th',this.hDiv).index(obj);
						
					this.colCopy = document.createElement("div");
					this.colCopy.className = "colCopy";
					this.colCopy.innerHTML = obj.innerHTML;
					if ($.browser.msie) { this.colCopy.className = "colCopy ie"; }
						
					$(this.colCopy).css({'position':'absolute','float':'left','display':'none', 'textAlign': obj.align});
					$('body').append(this.colCopy);
					$(this.cDrag).hide();
				}
				$('body').noSelect();
			},
			dragMove: function (e) {
				if (this.colresize)  {
					var n = this.colresize.n;
					var diff = e.pageX-this.colresize.startX;
					var nleft = this.colresize.ol + diff;
					var nw = this.colresize.ow + diff;
					if (nw > p.minwidth) { $('div:eq('+n+')',this.cDrag).css('left',nleft); this.colresize.nw = nw; }
				} else if (this.vresize) {
					var v = this.vresize;
					var y = e.pageY;
					var diff = y-v.sy;
					if (!p.defwidth) p.defwidth = p.width;
					if (p.width != 'auto' && !p.nohresize && v.hgo) {
						var x = e.pageX;
						var xdiff = x - v.sx;
						var newW = v.w + xdiff;
						if (newW > p.defwidth) { this.gDiv.style.width = newW + 'px'; p.width = newW; }
					}
					var newH = v.h + diff;
					if ((newH > p.minheight || p.height < p.minheight) && !v.hgo) {
						if ( ! p.extHori ) {
							this.bDiv.style.height = newH + 'px';
						}
						p.height = newH;
						this.fixHeight(newH);
					}
					v = null;
				} else if (this.colCopy) {
					$(this.dcol).addClass('thMove').removeClass('thOver'); 
					if (e.pageX > this.hset.right || e.pageX < this.hset.left || e.pageY > this.hset.bottom || e.pageY < this.hset.top) {
						//this.dragEnd();
						$('body').css('cursor','move');
					} else $('body').css('cursor','pointer');
					$(this.colCopy).css({top:e.pageY + 10,left:e.pageX + 20, display: 'block'});
				}													
			
			},
			dragEnd: function () {
				if (this.colresize) {
					var n = this.colresize.n;
					var nw = this.colresize.nw;
					var nh = p.checkbox? (n+1):n;
					$('thead th:visible div:eq('+n+')',this.hDiv).css('width',nw);
					$('thead th:eq('+n+')',this.hDiv).attr('cmwidth',nw);
					$('tr',this.bDiv[0]).each ( function () { 
						$('td:visible div:eq('+nh+')',this).css('width',nw); 
					});
					this.hDiv.scrollLeft = this.bDiv.scrollLeft;
					$('div:eq('+n+')',this.cDrag).siblings().show();
					$('.dragging',this.cDrag).removeClass('dragging');
					this.rePosDrag();
					this.fixHeight();
					this.colresize = false;
				} else if (this.vresize) {
					this.vresize = false;
				} else if (this.colCopy) {
					$(this.colCopy).remove();
					if (this.dcolt != null) {
						if (this.dcoln>this.dcolt) $('th:eq('+this.dcolt+')',this.hDiv).before(this.dcol);
						else $('th:eq('+this.dcolt+')',this.hDiv).after(this.dcol);
						
						this.switchCol(this.dcoln,this.dcolt);
						$(this.cdropleft).remove();
						$(this.cdropright).remove();
						this.rePosDrag();
					}
					this.dcol = null;
					this.hset = null;
					this.dcoln = null;
					this.dcolt = null;
					this.colCopy = null;
					
					$('.thMove',this.hDiv).removeClass('thMove');
					$(this.cDrag).show();
				}										
				$('body').css('cursor','default');
				$('body').noSelect(false);
			},
			scroll: function () {
				this.hDiv[0].scrollLeft = this.bDiv[0].scrollLeft;
				this.rePosDrag();
			}
		};
		g.table = $('<div></div>').addClass("gDiv");//$('<table border="0"></table>').addClass('itable');
		g.gDiv = g.table;
		g.table.head = $('<thead/>').addClass('ihead');
		g.hDiv = $('<div/>').addClass('hDiv');
		var hbox = $('<div/>').addClass('hDivBox');
		g.hDiv.append(hbox);
		hbox.append($('<table></table>').addClass('itable').append(g.table.head));
		g.table.body = $('<tbody/>').addClass('ibody');
		g.bDiv = $('<div></div>').addClass('bDiv');
		if ( p.height ) {
			if ( !p.extHori ) {
				g.bDiv.css({'height': p.height,'overflow':'auto'});
			} else {
				g.bDiv.css({'minHeight':p.height,'overflow':'auto'});
			}
		}
		g.bDiv.append($('<table></table>').addClass('itable').append(g.table.body));
		g.table.foot = $('<tfoot/>').addClass('ifoot');
		g.table.dFoot = $('<div></div>').addClass('fDiv');
		g.table.dFoot.append($('<table></table>').addClass('itable').append(g.table.foot));
		
		g.nBtn = $('<div/>').addClass('ibutton'); // create column show/hide
													// button
		g.pDiv = $('<div/>').addClass('pDiv');
		g.table.append(g.hDiv);
		g.table.append(g.bDiv);
		g.table.append(g.table.dFoot);
		$(t).append(g.table);
		$(t).css({'width':p.width});
		if (p.usepager) {
			$(t).append(g.pDiv);

		}

		// 画表头
		if (p.colModel) {
			tr = document.createElement('tr');

			for (var i = 0; i < p.colModel.length; i++) {
				var cm = p.colModel[i];
				var th = $('<th />');//document.createElement('th');
				var thdiv = $('<div />').addClass('iCellHead');
				thdiv.css({'overflow':'hidden','width':(cm.width||'100') + 'px','text-align':cm.innerAlign||'center'});
				thdiv.append( cm.display );
				thdiv.attr('fld', cm.dataIndex);
				// h.innerHTML = cm.display;

				if (cm.dataIndex){
					th.attr('abbr', cm.dataIndex);
					th.attr('sortField', cm.dataIndex);
				}
				th.attr('axis', 'col' + i);

				if (cm.sortField){ th.attr('sortField', cm.sortField); }
				if (cm.align) {th.attr('innerAlign',cm.align||'center'); }
				if (cm.decode) {/*th.attr('decode',cm.decode);*/ th[0].decode=cm.decode;}
				if (cm.render) {/*th.attr('render',cm.render);*/ th[0].render=cm.render;}
				if (cm.sortable) {
					th.click(function(e) {
						var obj = (e.target || e.srcElement);
						if (obj.href || obj.type)
							return true;
						g.changeSort(this);
					}).toggleClass("thOver");
				}

				if (cm.width) { th.attr('cmwidth', cm.width); } else {th.attr('cmwidth',100);}
				if (cm.hide) { th.attr('hide',true); th.hide(); }
				th.append(thdiv);
				$(tr).append(th);
			}
			$(g.table.head).append(tr);
		} // end if p.colmodel

		// 添加多选框
		if (p.checkbox) {
			$('tr', g.table.head).each(function() {
				var cth = $('<td/>');
				var cthch = $('<input type="checkbox"/>');
				cthch.click(function() {
					if (this.checked) {
						$('tr', g.table.body).each(function() {
							if ($(this).attr('disabled')) return;
							$(this).addClass('trSelected').find('input')[0].checked = true;});
					} else {
						$('tr', g.table.body).each(function() {
							if ($(this).attr('disabled')) return;
							$(this).removeClass('trSelected').find('input')[0].checked = false;});
					}
				});
				var cthdiv = $('<div />');
				cthdiv.css({'overflow':'hidden','width':'22px','text-align':'center'});
				cthdiv.append(cthch);
				cth.addClass("cth").append(cthdiv);//.attr({width : "22"}).append(cthch);
				$(this).prepend(cth);
			});
		}
		;
		// ----------

		// 是否有分页信息
		if (p.usepager) {
			g.pDiv.addClass('pDiv');
			g.pDiv.html('<div class="pDiv2"></div>');
			var html = ' <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol">第 <input id="pageNum" type="text" size="4" value="1" /> 页 共  <span>1</span> 页 </span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pPageStat"></span></div>';

			$('div', g.pDiv).html(html);
			$('#pageNum', g.pDiv).change(function() {
				if (p.onRpChange)
					p.onRpChange(+this.value);
				else {
					var num = parseInt($(this).val());
					if (num <= p.pages && num >= 1) {
						p.start = (num - 1) * p.limit;
						p.newp = num;
						g.populate();
					}
				}
			});
			$('.pReload', g.pDiv).addClass('ui-state-default').click(function() {g.populate();}).find('span').addClass('ui-icon ui-icon-refresh');
			$('.pFirst', g.pDiv).addClass('ui-state-default').click(function() {g.changePage('first');}).find('span').addClass('ui-icon ui-icon-seek-first');
			$('.pPrev', g.pDiv).addClass('ui-state-default').click(function() {g.changePage('prev');}).find('span').addClass('ui-icon ui-icon-seek-prev');
			$('.pNext', g.pDiv).addClass('ui-state-default').click(function() {g.changePage('next');}).find('span').addClass('ui-icon ui-icon-seek-next');
			$('.pLast', g.pDiv).addClass('ui-state-default').click(function() {g.changePage('last');}).find('span').addClass('ui-icon ui-icon-seek-end');
			$('.pButton',g.pDiv).hover( function () { $(this).addClass('ui-state-hover'); }, function () { $(this).removeClass('ui-state-hover') } );
			
			$('.pcontrol input', g.pDiv).keydown(function(e) {if (e.keyCode == 13)g.changePage('input');});
			if ($.browser.msie && $.browser.version < 7) $('.pButton', g.pDiv).hover(function() {$(this).addClass('pBtnOver');}, function() {$(this).removeClass('pBtnOver');});

			if (p.useRp) {
				var opt = "";
				for ( var nx = 0; nx < p.limitOptions.length; nx++) {
					if (p.limit == p.limitOptions[nx])
						sel = 'selected="selected"';
					else
						sel = '';
					opt += "<option value='" + p.limitOptions[nx] + "' " + sel
							+ " >" + p.limitOptions[nx]
							+ "&nbsp;&nbsp;</option>";
				}
				;
				$('.pDiv2', g.pDiv)
						.prepend(
								"<div class='pGroup'><span class='pcontrol'>"
										+ p.pagesize
										+ "<select name='rp'>"
										+ opt
										+ "</select></span></div><div class='btnseparator'></div>");
				$('select', g.pDiv).change(function() {
					if (p.onRpChange)
						p.onRpChange(+this.value);
					else {
						p.newp = 1;
						p.start = 0;
						p.limit = +this.value;
						g.populate();
					}
				});
			}
		}

		// 是否有按钮
		if (p.buttons) {
			var tDiv2 = document.createElement('div');
			tDiv2.className = 'tDiv2';

			for (i = 0; i < p.buttons.length; i++) {
				var btn = p.buttons[i];
				if(btn.show){
					var showArr = btn.show.split('|');
					var count = -1;
					for (var si=0;si<showArr.length;si++) {
						count += $.inArray(showArr[si], parent.buttonAuthors);
					}
					if(count < 0){
						continue;
					}
				}			
				if (!btn.separator) {
					var $btn = $('<button />').attr({id:btn.id,name:btn.name}).data('onpress',btn.onpress).html(btn.name).addClass('ui-btn-sm ' + (btn.aclass?btn.aclass:''));
					$btn.click(function(){if($(this).hasClass('idisablebutton')){ return; } else $(this).data('onpress').apply(t, [ p.dataRecord, this.id ]);});
					$(tDiv2).append($btn);
					$btn.button({icons: {primary:btn.bclass?btn.bclass:null} });
					/*var btnDiv = $('<div/>').addClass('fbutton').html("<div><span>" + btn.name+ "</span></div>");
					btnDiv.data('onpress', btn.onpress);
					if (btn.bclass) btnDiv.prepend($('<span />').addClass(btn.bclass));
					btnDiv.attr({name:btn.name,id:btn.id});
					if (btn.onpress) {
						$(btnDiv).click(function() {
							if($(this).hasClass('idisablebutton')){
								return;
							}
							else $(this).data('onpress').apply(t, [ p.dataRecord, this.id ]);
						});
					}
					$(tDiv2).append(btnDiv);
					if ($.browser.msie && $.browser.version < 7.0) {
						$(btnDiv).hover(function() {
							$(this).addClass('fbOver');
						}, function() {
							$(this).removeClass('fbOver');
						});
					}*/
				} else {
					$(tDiv2).append("<div class='btnseparator'></div>");
				}
			}
			g.nBtn.append(tDiv2);
			g.nBtn.append("<div style='clear:both'></div>");

			$(t).prepend(g.nBtn);
		}

		$(t).addClass('flexigrid');

		//set cDrag
		var cdcol = $('thead tr:first th[hide!=true]',g.hDiv).get(0);
		if (cdcol != null) {		
			// build cDrag div
			var cdheight = $(g.bDiv).height();
			var hdheight = $(g.hDiv).height();
			
			g.cDrag = $('<div/>').addClass('cDrag');
			g.cDrag.css({'top':'0','left':'0','display':'block'});
			var ttl = p.checkbox?23:0;
			$('thead tr:first th[hide!=true]', g.hDiv).each(function(i) {
				var cw = $(this).attr('cmwidth'); 
				ttl = parseInt(ttl) + parseInt(cw) + 1 ;
				var dl = $('<div/>');
				dl.css({'display':'block','left': parseInt(ttl-3) +'px','height':hdheight + cdheight});
				dl.mousedown(function(e){g.dragStart('colresize',e,this);});
				dl.dblclick(function(e){});
				if ($.browser.msie&&$.browser.version<7.0) {
					g.fixHeight($(g.gDiv).height());
					dl.hover(
						function ()  { g.fixHeight(); $(this).addClass('dragging'); },
						function () { if (!g.colresize) $(this).removeClass('dragging'); }
					);
				}
				if (!p.cgwidth) p.cgwidth = dl.width();
				g.cDrag.append(dl);
			});
			g.table.append(g.cDrag);

			g.cdpad = 0;
			g.cdpad += (isNaN(parseInt($('div',cdcol).css('borderLeftWidth'))) ? 0 : parseInt($('div',cdcol).css('borderLeftWidth'))); 
			g.cdpad += (isNaN(parseInt($('div',cdcol).css('borderRightWidth'))) ? 0 : parseInt($('div',cdcol).css('borderRightWidth'))); 
			g.cdpad += (isNaN(parseInt($('div',cdcol).css('paddingLeft'))) ? 0 : parseInt($('div',cdcol).css('paddingLeft'))); 
			g.cdpad += (isNaN(parseInt($('div',cdcol).css('paddingRight'))) ? 0 : parseInt($('div',cdcol).css('paddingRight'))); 
			g.cdpad += (isNaN(parseInt($(cdcol).css('borderLeftWidth'))) ? 0 : parseInt($(cdcol).css('borderLeftWidth'))); 
			g.cdpad += (isNaN(parseInt($(cdcol).css('borderRightWidth'))) ? 0 : parseInt($(cdcol).css('borderRightWidth'))); 
			g.cdpad += (isNaN(parseInt($(cdcol).css('paddingLeft'))) ? 0 : parseInt($(cdcol).css('paddingLeft'))); 
			g.cdpad += (isNaN(parseInt($(cdcol).css('paddingRight'))) ? 0 : parseInt($(cdcol).css('paddingRight'))); 
	
			g.bDiv.before(g.cDrag);
			
		}
		
		$(document)
			.mousemove(function(e){g.dragMove(e);})
			.mouseup(function(e){g.dragEnd();})
			.hover(function(){},function (){g.dragEnd();}) ;
		g.bDiv.scroll(function (e) { g.scroll();});
		g.rePosDrag();
		g.fixHeight();
		
		t.p = p;
		t.grid = g;
		return t;
	};

	$.fn.flexReload = function(data,params) { // function to reload grid
		if (!data)
			return;
		return this.each(function() {
			if (!this.p)
				return;
			this.p.jsonfilter = new Array();
			this.p.jsonfilter.push($.toJSON(data));
			this.p.start = 0;
			this.p.params=params;
			if (this.grid && this.p.url) {
				this.p.enableLoadFirst = true;
				this.grid.populate();
				this.grid.rePosDrag();
				this.grid.fixHeight();
			}
		});

	}; // end flexReload
	

	$.fn.flexAllSelectData = function() {
		var data = new Array();
		this.each(function() {
			if (this.grid) {
				dataStore = this.p.dataStore;
				$(this).find('.trSelected').each(function() {
					index = $(this).attr('id').substr(3);
					data.push(dataStore[index]);
				});
			}
		});
		return data;
	};
	$.fn.flexicheck = function(fld, values) {
		this.each(function() {
			if (this.grid) {
				var _ds = this.p.dataStore;
				var _this = this;
				$(_ds).each(function(_i,_n){
					var $tr = $(_this).find('#row'+_i);
					$tr.removeClass('trSelected');
					$tr.find(':checkbox').removeAttr('checked');
				});
				$(_ds).each(function(_i,_n){
					var v = _n[fld];
					if ( $.isArray(values) ) {
						$(values).each(function(_i0,_n0){
							if ( v == _n0 ) {
								var tr = $(_this).find('#row'+_i).addClass('trSelected');
								tr.find(':checkbox').attr('checked','checked');
							}
						});
					} else {
						if ( v == values) {
							var tr = $(_this).find('#row'+_i).addClass('trSelected');
							tr.find(':checkbox').attr('checked','checked');
						}
					}
				});
			}
		});
	};
	
	$.fn.flexAllData = function() {	
		var grid =this.get(0);		
			if (grid.grid) {
				return grid.p.dataStore;				
			}	
	};
	$.fn.refresh = function() {
		this.each(function() {
			if (this.grid) {
				if (this.p.jsonfilter == '')
					return;
					this.grid.populate();
			}
		});
	};
	$.fn.refreshGrid = function() {
		this.each(function() {
			if (this.grid) {
				this.grid.populate();
			}
		});
	};
	
	$.fn.refreshWithData = function(data) {
		this.each(function() {
			if (this.grid) {
				this.grid.dataStore(data);
				this.grid.addData(data);	
				this.grid.rePosDrag();
				this.grid.fixHeight();
			}
		});

	};
	
	$.fn.flexAddData = function(data) { // function to add data to grid

		/*return this.each( function() {
				if (this.grid) this.grid.addData(data);
			});*/
		return this.each( function() {
			if (this.grid) {
				this.p.dataStore.unshift(data);
				var newRecord = this.grid.sigleDataStore(data);
				this.p.viewStore.unshift(newRecord);			
			   var 	obj=new Object();
				obj[this.p.totalProperty]=this.p.viewStore.length;
				obj.root=this.p.viewStore;
				this.grid.addData(obj);
			};
		});

	};
	$.fn.flexModifyData = function(data) { // function to modify data to grid
		return this.each( function() {
			if (this.grid) {			 
				var record_index = this.p.index;
				if(record_index!=null){				
					this.p.dataStore.splice(record_index,1,data);
					this.p.viewStore.splice(record_index,1,data);
					obj=new Object();
 				    obj[this.p.totalProperty]=this.p.total;
					obj.root=this.p.viewStore;
					this.grid.addData(obj);
				}
			};
		});
	};
	$.fn.flexRemoveData = function(data) { // function to modify data to grid
		return this.each( function() {
			if (this.grid ) {			 
				var record_index = this.p.index;
				if(record_index!=null){				
					this.p.dataStore.splice(record_index,1);
					this.p.viewStore.splice(record_index,1);
					obj=new Object();
 				    obj[this.p.totalProperty]=this.p.total;
					obj.root=this.p.viewStore;
					this.grid.addData(obj);
				}
			};
		});
	};
	
	$.fn.flexDisableButton = function(data) { // function to modify data to grid
		  var div= $('#'+data.id);
		div.addClass('idisablebutton');
		 div.css('color','gray');
		 return;

	};
	$.fn.flexEnableButton = function(data) { // function to modify data to grid
			var div= $('#'+data.id);
			div.removeClass('idisablebutton');
			div.css('color','black');		
		  return;
	
	};
	/**
	 * Auto resize flexigrid with parent's height
	 */
	$.fn.flexFixHeight = function () {
		this.each(function(){
			var mHeight = 0; //$(this).prev().height();
			$(this).parent().children(":visible").not('.ui-dialog').not('.ui-widget-overlay').not($(this)).each(function(){
				// console.log('#' + $(this).attr('id') + ':'+ $(this).height() + ':' + $(this).attr('class'));
				mHeight += $(this).outerHeight();
			});

			// console.log("sur-height:" + mHeight);
			// console.log("parent-height:" + $(this).parent().height());
			
			mHeight += ($(this).find('.ibutton').height());
			mHeight += $(this).find('.pDiv').height() ;
			mHeight += $(this).find('.hDiv').height() ; 
			var _ch = $(this).parent().height() - mHeight - 3; // 2(.hDiv border-top border-bottom) + 1(.bDiv border-bottom)
			
			$(this).find('.bDiv').css({height:_ch});
			$(this).find('.cDrag div').css({height:_ch+$(this).find('.hDiv').height()});
		});
	};
	$.fn.flexFile = function(url) { 
		 this.each( function() {
			if (this.grid) {
				if ( this.p.total && this.p.total > 10000 ) {
					alert("总记录数超过 10000 条，请筛选后再导出");
					return ; 
				}
				var gid = $(this).attr('id');
				var fid = gid + '_exp_frm';
				var jsonfilter_fld = fid + '_jsonfilter';
				var dir_fld = fid + '_dir';
				var sort_fld = fid + '_sort';
				var pid = fid + '_params';
				var frm = $('#' + fid);
				if (frm.length<=0) {
					var fDiv = $('<div id="' +fid+ '_div" />').css({display:'none'});
					frm = $('<form></form>').attr('id', fid).attr('method','post').attr('target','_blank');
					frm.append($('<input />').attr('id',jsonfilter_fld).attr('name','jsonfilter'));
					frm.append($('<input />').attr('id',dir_fld).attr('name','dir'));
					frm.append($('<input />').attr('id',sort_fld).attr('name','sort'));
					var $p = $('<div />').attr('id',pid);
					frm.append($p);
					
					fDiv.append(frm);
					$('body').append(fDiv);
				}
				$('#' + jsonfilter_fld).val(this.p.jsonfilter);
				$('#' + dir_fld).val(this.p.dir);
				$('#' + sort_fld).val(this.p.sort);
				
				var $pdiv = $('#'+pid);
				$pdiv.html('');
				if (this.p.params) {
					$(this.p.params).each(function(_i,_p){
						if ( _p.name && _p.value ) {
							$pdiv.append($('<input />').attr('name',_p.name).val(_p.value));
						}
					});
				}
				frm.attr('action',url) ;
				frm.submit();
			};
		});
	};
	

	$.fn.flexigrid = function(p) {

		return this.each(function() {
			$.addFlex(this, p); 
		});
	};

	$.fn.noSelect = function(p) { //no select plugin by me :-)
		if (p == null)  prevent = true;
		else prevent = p;

		if (prevent) {
			return this.each(function () {
				if ($.browser.msie||$.browser.safari) $(this).bind('selectstart',function(){return false;});
				else if ($.browser.mozilla) 
					{
						$(this).css('MozUserSelect','none');
						$('body').trigger('focus');
					}
				else if ($.browser.opera) $(this).bind('mousedown',function(){return false;});
				else $(this).attr('unselectable','on');
			});
		} else {
			return this.each(function () {
				if ($.browser.msie||$.browser.safari) $(this).unbind('selectstart');
				else if ($.browser.mozilla) $(this).css('MozUserSelect','inherit');
				else if ($.browser.opera) $(this).unbind('mousedown');
				else $(this).removeAttr('unselectable','on');
			});
		}
	}; //end noSelect
	
})(jQuery);