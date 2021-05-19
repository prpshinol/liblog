/**
 * 分页类，用于生成分页条
 * options={
 * 		containerId:'bar',	//分页条容器的id
 * 		page:1,
 * 		rows:10,
 * 		count:100,
 * 		toPage:function(page){	//回调函数，点击分页条中按钮，会调用这个函数
 * 			//用户根据需要，调用自己的业务，如ajax获取该页数据
 * 		}
 * }
 * 注意：不需要data属性（我们的目标只是生成分页条，获取数据不负责）
 */
function Pagination(options){
	//当前页号
	this.page=options.page||null;
	//每页行数
	this.rows=options.rows||null;
	//总记录数
	this.count=options.count||null;
	
	var toPage=options.toPage;
	
	this.$pageBar=$("#"+options.containerId);
	
	//计算总页数
	this.getPageCount=function() {
		return this.count%this.rows==0?this.count/this.rows:Math.ceil(this.count/this.rows);
	};
	
	//是否首页
	this.isFirst=function(){
		return this.page==1;
	};
	
	//是否尾页
	this.isLast=function(){
		return this.page==this.getPageCount();
	};
	
	//上一页
	this.getPrePage=function(){
		return	this.page==1?this.page:this.page-1;
	};
	
	//下一页
	this.getNextPage=function(){
		return this.page==this.getPageCount()?this.page:this.page+1;
	};
	
	//-----普通getters and setters
	this.getPage=function(){
		return this.page;
	};
	
	this.setPage=function(page){
		this.page=page;
	};
	
	this.getRows=function(){
		return this.rows;
	};
	
	this.setRows=function(rows){
		this.rows=rows;
	};
	
	this.getCount=function(){
		return this.rows;
	};
	
	this.setCount=function(count){
		this.count=count;
	};
	
	//生成分页条,模拟京东
	this.toPageBar=function(){
		this.$pageBar.html("");
		//如果当前页号<=0或者总页数<1,不做处理
		if(this.getPage()<=0||this.getPageCount()<1){
			return;
		}
		this.$pageBar.addClass("page-bar");
		var $pageNum=$("<div class='page-num'></div>");
		var html="<a class='"+(this.isFirst()?'disable':'')+" btn btn-default' page='"+this.getPrePage()+"' href='javascript:void(0);'>上一页</a>";
		if(this.getPageCount()<=9){
			//1.总页数<=9条，直接显示全部
			for(var i=1;i<=this.getPageCount();i++){
				html+="<a href='javascript:void(0);' class='"+(this.getPage()==i?"curr-page":"")+" btn btn-default' page='"+i+"'>"+i+"</a>";
			}
		}else if(this.getPage()<=5){
			//2.当前页号<=5,显示1-8,...,尾页号
			for(var i=1;i<=8;i++){
				html+="<a href='javascript:void(0);' class='"+(this.getPage()==i?"curr-page":"")+" btn btn-default' page='"+i+"' >"+i+"</a>";
			}
			html+="<div class='pn-break'>…</div>";
			html+="<a href='javascript:void(0);' class='btn btn-default' page='"+this.getPageCount()+"' >"+this.getPageCount()+"</a>";
			
		}else if(this.getPage()>=this.getPageCount()-4){
			//3.当前页号>=尾页号-4,显示1,...,尾页号-7~尾页号
			html+="<a href='javascript:void(0);' class='btn btn-default' page='1' >1</a>";
			html+="<div class='pn-break'>…</div>";
			for(var i=this.getPageCount()-7;i<=this.getPageCount();i++){
				html+="<a href='javascript:void(0);' class='"+(this.getPage()==i?"curr-page":"")+" btn btn-default' page='"+i+"' >"+i+"</a>";
			}
		}else{
			//4.否则，显示1,...,当前页号-3,当前页号-2,当前页号-1,当前页号,当前页号+1,当前页号+2,当前页号+3,...,...,尾页号
			html+="<a href='javascript:void(0);' class='btn btn-default' page='1' >1</a>";
			html+="<div class='pn-break'>…</div>";
			for(var i=this.getPage()-3;i<=this.getPage()+3;i++){
				html+="<a href='javascript:void(0);' class='"+(this.getPage()==i?"curr-page":"")+" btn btn-default' page='"+i+"' >"+i+"</a>";
			}
			html+="<div class='pn-break'>…</div>";
			html+="<a href='javascript:void(0);' class='btn btn-default' page='"+this.getPageCount()+"' >"+this.getPageCount()+"</a>";
		}
		html+="<a class='"+(this.isLast()?'disable':'')+" btn btn-default' page='"+this.getNextPage()+"' href='javascript:void(0);'>下一页</a>";
		
		//页号列表
		$pageNum.html(html);
		//为每个按钮添加点击事件
		var me=this;
		$pageNum.find("a").click(function(){
			var a=$(this);
			var page=parseInt(a.attr("page"));
			toPage(page);
		});
		this.$pageBar.append($pageNum);
		
		//跳转处理
		var $pageSkin=$("<div class='p-skip'></div>");
		var shtml="";
		shtml+='<div style="float:left;margin-top:6px;">共'+this.getPageCount()+'页&nbsp;&nbsp;到第</div> ';
		shtml+='<input class="form-control" style="width:45px;display:block;float:left;margin:0px 2px;" type="text" maxlength="4" value="'+this.getPage()+'" id="jumpPage">';
		shtml+='<div style="float:left;margin-top:6px;">页</div>';
		shtml+='<a class="btn btn-default" href="javascript:void(0)" id="jumpBtn">确定</a>';
		$pageSkin.html(shtml);
		//为输入框添加输入验证
		function jump(){
			var reg=/^[1-9][0-9]*$/;
			var $input=$pageSkin.find("#jumpPage");
			if(!reg.test($input.val())){
				//1.判断输入是否符合格式
				//还原输入为当前页号
				$input.val(me.getPage());
				alert("请输入正整数！");
				return;
			}else if(parseInt($input.val())>me.getPageCount()){
				//2.判断输入是否大于总页数
				$input.val(me.getPage());
				alert("输入页号不能大于总页数！");
				return;
			}
			
			//输入合法，调用跳转函数
			toPage(parseInt($input.val()));
		}
		$pageSkin.find("#jumpBtn").click(function(){
			jump();
		});
		
		$pageSkin.find("#jumpPage").keydown(function(e){
			if(e.keyCode==13){
				jump();
			}
		});
		this.$pageBar.append($pageSkin);
		this.$pageBar.append('<div class="clearfix"></div>');
	};
	
	
	//初始化，直接生成分页条
	this.toPageBar();
	
	
}