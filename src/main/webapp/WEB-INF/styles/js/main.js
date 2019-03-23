
/*
2012年9月7日16:45:57
用于显示列表项的详细信息
datas：需要显示的数据集合
ids：显示数据的容器ID集合
*/
showDatasForList = function(datas,ids){
    if(datas!=null && datas.length>0
        && ids!=null && ids.length==datas.length){
        for(var i=0; i<datas.length; i++){
            $('#'+ids[i]).html(datas[i]);
        }
        return true;
    }
    return false;
};

/*
2012年8月29日9:18:08
checkbox是否选择，打开or关闭指定层。
*/
function hiddenOrShowForCheckbox(control, show) {
    for(var i=0; i<show.length; i++){
        if (control.checked) {
            show[i].style.display='';
        } else {
            show[i].style.display='none';
        }
    }
}

/*
2012年8月27日14:48:59
获取tab，当前被选中的项，下标从0开始。
*/
getCurrentTab = function(divId){
    var ret = -1;
    if($('#'+divId)){
        var tab = $('#'+divId).tabs('getSelected');
        if(tab){
            ret = $('#'+divId).tabs('getTabIndex',tab);
        }
    }
    return ret;
};


/*
2012年8月17日8:37:22
关闭easyui弹层
@param id,divId
@param clear,是否要清除div内容
*/
closeWindowForEasyUI = function(id,notClear){
    if(notClear){
        $('#'+id).html('');
    }
    $('#'+id).window('close');
};

/*
2012年8月9日10:02:08
清空div内容
id：div_id
delay：延时关闭时间ms
*/
clearDivDelay = function(id,delay){
    setTimeout("clearDiv('"+id+"')",delay);
};
clearDiv = function(id){
    $('#'+id).html('');
    $('#'+id).hide('slow');
};

// Button mouseover style change
function btnMouseOver(){
    var eventTarget=getEventTarget();
    if (eventTarget){
        eventTarget.style.color="#FF6600";
    }
}
// Button mouseout style change
function btnMouseOut(){
    var eventTarget=getEventTarget();
    if (eventTarget){
        eventTarget.style.color="black";
    }
}

function getEventTarget(){
    if (window.event){
        return window.event.srcElement;
    }
    var func=getEventTarget.caller;
    while(func && func!=null){
        var arg0=func.arguments[0];
        if(arg0 && arg0.target){
            return arg0.target;
        }
        func=func.caller;
    }
    return null;
}

/*
	2012年6月19日13:58:14
	清除指定formId内所有input=text值域前后空格
*/
trimAllDatas = function(formId){
    $("input[type=text]",$('#'+formId)).each(function(){
        //alert('name='+this.name+', value='+this.value);
        this.value = $.trim(this.value);
    });
};
/*
	2012年6月19日13:58:14
	校验formId内所有必填项(带tipType属性的项)，值域是否非空，
	true-非空
	false-含空
*/
checkFormWithTipType = function(formId,tipType,tips_parent_id,tipstitle){
    trimAllDatas(formId);
    var ret = true;
    $("*",$('#'+formId)).each(function(){
        if($(this).attr(tipType)){
            this.value = $.trim(this.value);
            if(this.value==''){
				if(tips_parent_id){
					var tipstitle_ = 'Tips';
					if(tipstitle){
						tipstitle_ = tipstitle;
					}
					tips(tipstitle_,$(this).attr(tipType),getXpos(tips_parent_id),getYpos(tips_parent_id));
				}else {
					$.messager.alert('error',$(this).attr(tipType),'error');
				}
                //alert($(this).attr(tipType));
                $(this).focus();
                ret = false;
                return false;//跳出循环，(continue-return true)
            }
        }

    });
    return ret;
};

/*
	2012年8月14日13:43:01
	校验formId内所有必填项(带tipType属性的项)，值域是否非空，
	去除alert，显示tips
	true-非空
	false-含空
	tipType='nulltip,notspecial'
	nulltip--非空校验
	notspecial--非法字符校验
*/
checkFormWithTipTypeNotAlert = function(formId,tipType,tipsTitle,tipsXpos,tipsYpos,tipsXlen,tipsYlen){
    var ret = true;
    $("*",$('#'+formId)).each(function(){
        if($(this).attr(tipType)){
            this.value = $.trim(this.value);
            if(this.value==''){
                //$('#'+tipsId).html($(this).attr(tipType));
                //alert($(this).attr(tipType));
				tips(tipsTitle,$(this).attr(tipType),tipsXpos,tipsYpos,tipsXlen,tipsYlen);
                ret = false;
                return false;//跳出循环，(continue-return true)
            }
        }
    });
    return ret;
};
// 判断字段是否为空
isFieldNull = function(fieldId){
	var field = $("#"+fieldId);
	field.val($.trim(field));
	return field.val()=='';
};

/*
	2012年6月19日13:58:14
	重置formId内所有值域
*/
clearForm = function(formId){
    $("*",$('#'+formId)).each(function(){
        this.value = '';
    });
};

function CheckUtil(){}

//校验上传文件后缀名是否合法
CheckUtil.isCorrectFileType = function(str){
	var hz = str.substring(str.lastIndexOf('.')+1);
    var patrn = /^(zip|msi)$/;
    return patrn.test(hz);
};

//校验IP地址是否正确
CheckUtil.isCorrectIP = function(str){
    var patrn = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    return patrn.test(str);
};
//校验是否全是数字
CheckUtil.isDigit = function(str){
    var patrn = /^\d+$/;
    return patrn.test(str);
};
//校验是否全是整数
CheckUtil.isInteger = function(str){
    var patrn = /^([+-]?)(\d+)$/;
    return patrn.test(str);
};
//校验是否为正整数
CheckUtil.isPlusInteger = function (str) {
    var patrn=/^([+]?)(\d+)$/;
    return patrn.test(str);
};
//校验是否为负整数
CheckUtil.isMinusInteger = function (str) {
    var patrn=/^-(\d+)$/;
    return patrn.test(str);
};
//校验是否为浮点数
CheckUtil.isFloat=function(str){
    var patrn=/^([+-]?)\d*\.\d+$/;
    return patrn.test(str);
};
//校验是否为正浮点数
CheckUtil.isPlusFloat=function(str){
    var patrn=/^([+]?)\d*\.\d+$/;
    return patrn.test(str);
};
//校验是否为负浮点数
CheckUtil.isMinusFloat=function(str){
    var patrn=/^-\d*\.\d+$/;
    return patrn.test(str);
};
//校验是否仅中文
CheckUtil.isChinese=function(str){
    var patrn=/[\u4E00-\u9FA5\uF900-\uFA2D]+$/;
    return patrn.test(str);
};
//校验是否仅ACSII字符
CheckUtil.isAcsii=function(str){
    var patrn=/^[\x00-\xFF]+$/;
    return patrn.test(str);
};
//校验手机号码
CheckUtil.isMobile = function (str) {
    var patrn = /^1[3,4,5,7,8,9]\d{9}$/; // /^0?1((3[0-9]{1})|(59)){1}[0-9]{8}$/;
    return patrn.test(str);
};
//校验电话号码
CheckUtil.isPhone = function (str) {
    var patrn = /^(0[\d]{2,3}-)?\d{6,8}(-\d{3,4})?$/;
    return patrn.test(str);
};
//校验URL地址
CheckUtil.isUrl=function(str){
    var patrn= /^http[s]?:\/\/[\w-]+(\.[\w-]+)+([\w-\.\/?%&=]*)?$/;
    return patrn.test(str);
};
//校验电邮地址
CheckUtil.isEmail = function (str) {
    var patrn = /^[\w-\.]+@[\w-]+(\.[\w-]+)+$/;
    return patrn.test(str);
};
//校验邮编
CheckUtil.isZipCode = function (str) {
    var patrn = /^\d{6}$/;
    return patrn.test(str);
};
//校验合法日期
CheckUtil.isDate = function (str) {
    var patrn = /^\d{4}-\d{2}-\d{2}$/;
    return patrn.test(str);
};
//校验合法日期+时间
CheckUtil.isDatetime = function (str) {
    var patrn = /^\d{4}-\d{2}-\d{2}\s+\d{2}:\d{2}:\d{2}$/;
    return patrn.test(str);
};
//校验字符串：只能输入6-20个字母、数字、下划线(常用于校验用户名和密码)
CheckUtil.isString6_20=function(str){
    var patrn=/^(\w){6,20}$/;
    return patrn.test(str);
};
// 检验字符串：是否含有常用特殊字符
CheckUtil.isHasSpecialCharacterCommon = function(str){
    var patern = /[\^@\/\'\\\"#\$%&\^\*\(\)\[\]\{\}\.]/;
    return patern.test(str);
};
// 清除字符串中包含的特殊字符
CheckUtil.clearSpecialCharacterCommon = function(str){
    var patern = /[\^@\/\'\\\"#\$%&\^\*\(\)\[\]\{\}\.]/g;
    return str.replace(patern,"");
};
// 检验字符串：是否含有特殊字符
CheckUtil.isHasSpecialCharacter = function(str){
    //var patern = /[\^@\/\'\\\"#\$%&\^\*\(\)\[\]\{\}]+$/;
    var patern = /[^a-zA-Z0-9_]/;
    return patern.test(str);
};
// 清除字符串中包含的特殊字符
CheckUtil.clearSpecialCharacter = function(str){
    var patern = /[^a-zA-Z0-9_]/g;
    return str.replace(patern,"");
};

// 检验是否包含小写字母
CheckUtil.isContainUper = function(str){
	var p1 = /[a-z]/;
	return p1.test(str);
};
// 检验是否包含大写字母
CheckUtil.isContainLower = function(str){
	var p1 = /[A-Z]/;
	return p1.test(str);
};
// 检验是否包含数字
CheckUtil.isContainDig = function(str){
	var p1 = /[0-9]/;
	return p1.test(str);
};
// 检验密码复杂度是否合格：字符串中必须要含数字和字母，且至少含一大写字母
CheckUtil.isComplexPassword = function(str){
    var p1 = /^[a-zA-Z0-9_\-\!@#\$%&\*\(\)\+\^]{8,16}$/;
    if(!p1.test(str)){
		return false;
	}
	return CheckUtil.isContainUper(str) && CheckUtil.isContainLower(str) && CheckUtil.isContainDig(str);
};

$.fn.datebox.defaults.formatter = function(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
};

/*
 * 2012年10月30日9:20:39
 * 错误消息提示信息
 */
function tips(_title,_msg,_xpos,_ypos,_xlen,_ylen){

	var xlen=500;
	var ylen=100;
	var xpos=0;
	var ypos=0;

	if(_xpos){
		xpos = _xpos;
	}
	if(_ypos){
		ypos = _ypos;
	}
	if(_xlen){
		xlen = _xlen;
	}
	if(_ylen){
		ylen = _ylen;
	}

	$.messager.show({
		title: '系统提示',
		msg: _msg,
		timeout: 2000,
		showType:'slide',
		width: xlen,
		height: ylen
		/*,style:{
			right:'',
			top: (document.body.scrollTop+document.documentElement.scrollTop)/2,
			bottom:''
		}*/
		,style:{
			left: "",
			top: "",
			right:xpos-(xlen/2),
			zIndex:$.fn.window.defaults.zIndex++,
			bottom: ypos
		}
	});
};

/*
 * 2014年4月5日15:35:17
 * 默认提示信息
 */
function tipscommon(_msg){

	var xlen=300;
	var ylen=100;
	var xpos=getXpos('divCenter');
	var ypos=getYpos('divCenter');

	$.messager.show({
		title: '系统提示',
		msg: _msg,
		timeout: 2000,
		showType:'slide',
		width: xlen,
		height: ylen
		,style:{
			left: "",
			top: "",
			right:xpos-(xlen/2),
			zIndex:$.fn.window.defaults.zIndex++,
			bottom: ypos
		}
	});
};

/*
	id: divId
	w: width[default: 800]
	h: height[default: 420]
*/
createSelfWindow = function(id){
	var w = 800;
	var h = 420;

	return $('#'+id).window({
		height: h,
		width: w,
		//top: ($(window).height()-h)*0.5,
		//left: ($(window).width()-w)*0.5,
		modal:true,
		closed: true,
		collapsible: true,//是否可 折叠
		maximizable: true, // 最大化
		draggable: true, //不能拖拽
		resizable: true, //不能调整大小
		//shadow: false, // If set to true,when window show the shadow will show also.
		minimizable: false,
		onClose:function(){
			$(this).html('');
		}
	}).window("center");
	//$('#'+id).window('close');  //close window
} ;

/*
	id: divId
	w: width[default: 800]
	h: height[default: 420]
*/
createSelfWindow = function(id,_w,_h){
	var w = 800;
	var h = 420;
	if(_w){
		w = _w;
	}

	if(_h){
		h = _h;
	}

	return $('#'+id).window({
		height: h,
		width: w,
//		top: ($(window).height()-h)*0.5,
//		left: ($(window).width()-w)*0.5,
		modal:true,
		closed: true,
		collapsible: true,//是否可 折叠
		maximizable: true, // 最大化
		draggable: true, //不能拖拽
		resizable: true, //不能调整大小
		//shadow: false, // If set to true,when window show the shadow will show also.
		minimizable: false,
		onClose:function(){
			$(this).html('');
		}
	}).window("center");
	//$('#'+id).window('close');  //close window
} ;

/*
	2013年3月13日9:06:13
	创建不可最大化、不可调整大小的窗口
	id: divId
	w: width[default: 800]
	h: height[default: 420]
*/
createSelfWindowOfFixedSize = function(id,_w,_h){
	var w = 800;
	var h = 420;
	if(_w){
		w = _w;
	}
	if(_h){
		h = _h;
	}
	return $('#'+id).window({
		height: h,
		width: w,
		top: ($(window).height()-h)*0.5,
		left: ($(window).width()-w)*0.5,
		modal:true,
		closed: true,
		collapsible: true,//是否可 折叠
		maximizable: false, // 最大化
		draggable: false, //不能拖拽
		resizable: false, //不能调整大小
		//shadow: false, // If set to true,when window show the shadow will show also.
		minimizable: false,
		onClose:function(){
			$(this).html('');
		}
	});
	//$('#'+id).window('close');  //close window
} ;

createUuid = function(){
	function S4() {
		return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    };
    return S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4();
};

createWindowRandom = function(_w,_h){
	winId = createUuid();
	var w = 800;
	var h = 420;
	if(_w){
		w = _w;
	}
	if(_h){
		h = _h;
	}
	$('<div/>').attr("id", winId).window({
		height: h,
		width: w,
		top: ($(window).height()-h)*0.5,
		left: ($(window).width()-w)*0.5,
		modal:true,
		closed: true, // 默认是关闭状态
		collapsible: true,//是否可 折叠
		maximizable: true, // 最大化
		draggable: true, //不能拖拽
		resizable: true, //不能调整大小
		minimizable: false,
		onClose:function(){
			 // 关闭即销毁
			$(this).window('destroy');
		}
	});
	return winId;
} ;

function getXpos(id){
	id = "cc";
	return document.body.clientWidth - ($("#"+id).offset().left+$("#"+id).width()/2);
};

function getYpos(id){
	id = "cc";
	return document.body.clientHeight - ($("#"+id).offset().top+$("#"+id).height());
};

function tipsinfo(msg){
	$.messager.alert('系统提示', msg, 'info');
};
function tipserror(msg){
	$.messager.alert('系统提示', msg, 'error');
};
function tipsquestion(msg){
	$.messager.alert('系统提示', msg, 'question');
};
function tipswarning(msg){
	$.messager.alert('系统提示', msg, 'warning');
};

function openWindowRandom(title,url,_w,_h){
	var winId = createUuid();
	var w = 800;
	var h = 420;
	if(_w){
		w = _w;
	}
	if(_h){
		h = _h;
	}
	$('<div/>').attr("id", winId).window({
		height: h,
		width: w,
		top: ($(window).height()-h)*0.5,
		left: ($(window).width()-w)*0.5,
		modal:true,
		closed: true, // 默认是关闭状态
		collapsible: false,//是否可 折叠
		maximizable: true, // 最大化
		draggable: true, //不能拖拽
		resizable: true, //不能调整大小
		minimizable: false,
		onClose:function(){
			 // 关闭即销毁
			$(this).window('destroy');
		}
	});
    $("#"+winId).window('setTitle', title)
    .load(url)
    .window('open');
    return winId;
};

// 固定尺寸，不可变更，2014年8月19日23:35:45
function openWindowRandomFixed(title,url,_w,_h){
	var winId = createUuid();
	var w = 800;
	var h = 420;
	if(_w){
		w = _w;
	}
	if(_h){
		h = _h;
	}
	$('<div/>').attr("id", winId).window({
		height: h,
		width: w,
		top: ($(window).height()-h)*0.5,
		left: ($(window).width()-w)*0.5,
		modal:true,
		closed: true, // 默认是关闭状态
		collapsible: true,//是否可 折叠
		maximizable: false, // 最大化
		draggable: true, //不能拖拽
		resizable: false, //不能调整大小
		minimizable: false,
		onClose:function(){
			 // 关闭即销毁
			$(this).window('destroy');
		}
	});
    $("#"+winId).window('setTitle', title)
    .load(url)
    .window('open');
    return winId;
};

/**
 * 格式化日期函数
 * @param date
 * @param type 1: 2014-01-01 01:01:01, 2: 2014-01-01
 */
function formatDateTime(date, type){
	var ret = "";
	if(date){
		var time;
		if(date instanceof Date){
			// 本身是日期函数了，不需要再转化
			time = date;
		}else {
			// 非日期格式，需要转化为日期函数
			time = new Date(date);
		}

		var y = time.getFullYear();
		var m = time.getMonth() + 1;
		var d = time.getDate();
		var hh = time.getHours();
		var mm = time.getMinutes();
		var ss = time.getSeconds();

		if(type && type==2){
			// yyyy-m-d
			ret = y + "-" + (m<10?"0"+m:m) + "-" + (d<10?"0"+d:d);
		}else {
			// 缺省为：yyyy-m-d hh:mm:ss
			ret = y + "-" + (m<10?"0"+m:m) + "-" + (d<10?"0"+d:d) + " " + (hh<10?"0"+hh:hh) + ":" + (mm<10?"0"+mm:mm) + ":" + (ss<10?"0"+ss:ss);
		}
	}
	
	return ret;
};

/*
 * 2014年4月27日18:55:55
 * 事件通知提示信息
 */
function notice(_msg,timeout){

	var xlen=300;
	var ylen=100;
	var xpos=getXpos('divCenter');
	var ypos=getYpos('divCenter');
	
	var _timeout = 0;
	if(timeout){
		_timeout = timeout;
	}

	$.messager.show({
		title: '系统通知',
		msg: "<div style='overflow-y:auto;height: 50px;'>"+_msg+"</div>",
		timeout: _timeout,
		showType:'slide',
		width: xlen,
		height: ylen
		,style:{
			left: "",
			top: "",
			right:xpos-(xlen/2),
			zIndex:$.fn.window.defaults.zIndex++,
			bottom: ypos
		}
	});
};

/**
 * 自动选中菜单操作
 * @param nodeid
 */
function chooseMenu(nodeid){
	// find a node and then select it
	var node = $('#tree_left').tree('find', nodeid);
	$('#tree_left').tree('select', node.target);
};

/**
 * 柱状图
 * chartBarDatas.div
 * chartBarDatas.title
 * chartBarDatas.xdatas
 * chartBarDatas.ynames
 * chartBarDatas.ydatas
 */
function createChartBar(chartBarDatas){
    return chart = new Highcharts.Chart({ 
        chart: { 
            renderTo: chartBarDatas.div, //图表放置的容器，关联DIV#id 
            zoomType: 'xy'   //X、Y轴均可放大 
            //因为是柱状图和曲线图共存在一个图表中，所以默认图表类型不在这里设置。 
        }, 
        title: { 
            text: chartBarDatas.title //图表标题 
        }, 
        /* subtitle: { 
            text: '数据来源：新浪财经'   //图表副标题 
        },  */
        credits: { 
            enabled: false   //不显示LOGO 
        }, 
        xAxis: [{ //X轴标签 
            categories: chartBarDatas.xdatas, 
            labels: {
                rotation: 0,  //-45-逆时针旋转45°，标签名称太长。 
                align: 'right'  //设置右对齐 
            } 
        }], 
        yAxis: [
        { //设置Y轴-第二个（金额） 
            gridLineWidth: 1,  //设置网格宽度为0，因为第一个Y轴默认了网格宽度为1 
            title: {text: ''},//Y轴标题设为空 
            labels: { 
                formatter: function() {//格式化标签名称 
                    return this.value; // + ' 万亿元'; 
                }, 
                style: { 
                    color: '#4572A7' //设置标签颜色 
                } 
            } 
 
        }], 
        tooltip: { //鼠标滑向数据区显示的提示框 
            formatter: function() {  //格式化提示框信息 
                /*var unit = { 
                    '金额': '亿元', 
                    '增幅': '%' 
                } [this.series.name]; */
                return this.series.name + '<br/>' + this.x + '<br/>' + this.y + ''; // + ' ' + unit; 
            } 
        }, 
        legend: { //设置图例 
            layout: 'horizontal', //horizontal-水平排列图例, vertical-垂直排列
            shadow: true  //设置阴影,
        }, 
/*        series: [{  //数据列 
            name: chartBarDatas.yname, 
            //color: '#4572A7', 
            type: 'column', //类型：纵向柱状图 
            yAxis: 0, //数据列关联到Y轴，默认是0，设置为1表示关联上述第二个Y轴即金额 
            data: chartBarDatas.ydatas //金额数据 
        },{  //数据列 
            name: chartBarDatas.yname+"2", 
            //color: '#2272A7', 
            type: 'column', //类型：纵向柱状图 
            yAxis: 0, //数据列关联到Y轴，默认是0，设置为1表示关联上述第二个Y轴即金额 
            data: chartBarDatas.ydatas //金额数据 
        },{  //数据列 
            name: chartBarDatas.yname+"3", 
            //color: '#2272A7', 
            type: 'column', //类型：纵向柱状图 
            yAxis: 0, //数据列关联到Y轴，默认是0，设置为1表示关联上述第二个Y轴即金额 
            data: chartBarDatas.ydatas //金额数据 
        }] , */
        series: createChartBarSeries(chartBarDatas.ynames,chartBarDatas.ydatas)
    });
};

function createChartBarSeries(ynames, ydatas){
	var series = [];
	for(var i=0;i<ynames.length;i++){
		series.push({  //数据列 
            name: ynames[i], 
            //color: '#4572A7', 
            type: 'column', //类型：纵向柱状图 
            yAxis: 0, //数据列关联到Y轴，默认是0，设置为1表示关联上述第二个Y轴即金额 
            data: ydatas[i] //金额数据 
        });
	}
/*	$.each(ydatas, function(index, item){
		alert(item);
		series.push({  //数据列 
            name: ynames[index], 
            //color: '#4572A7', 
            type: 'column', //类型：纵向柱状图 
            yAxis: 0, //数据列关联到Y轴，默认是0，设置为1表示关联上述第二个Y轴即金额 
            data: item //金额数据 
        });
	});*/
	return series;
};

/**
 * 饼状图
 * chartPieDatas.div
 * chartPieDatas.title
 * chartPieDatas.datas
 * chartPieDatas.name
 */
function createChartPie(chartPieDatas){
    return chart = new Highcharts.Chart({
        chart: {  
            renderTo: chartPieDatas.div 
            //plotBackgroundColor: null,  
            //plotBorderWidth: null,  
            //plotShadow: false  
          },  
          title: {  
            text: chartPieDatas.title 
          },  
          credits: { 
              enabled: false   //不显示LOGO 
          }, 
          tooltip: {  
            formatter: function() {  
              return '<b>' + this.point.name + '</b>: ' + this.y;  
            }  
          },  
          plotOptions: {  
            pie: {  
              allowPointSelect: false,  
              cursor: 'pointer',  
              dataLabels: {
                enabled: true,  
                //color: '#000000',  
                //connectorColor: '#000000',  
                formatter: function() {  
                  return '<b>' + this.point.name + '</b>: ' + this.y;  
                } 
              },
              showInLegend: true
            }  
          },  
          series: [  
            {
              type: 'pie',  
              name: chartPieDatas.name,  
              data: chartPieDatas.datas  
            }  
          ]  
        }); 
};

/* 一级菜单点击事件 */
function link_menu1(page){
	$('#divWest').html('');
	$('#divWest').load(page + '?_t=' + Math.random());
};

/* 二级菜单点击事件 */
function link_menu2(title, url){
	//$('#divCenter').panel('setTitle', title);
	$('#divCenter').html('');
	$('#divCenter').load(url + '?_t=' + Math.random());
};

function init_menu2(ctxPath){
	$('.menu2_title_background').each(function(index,item){
		var div = $(this).attr("title");
		$("#"+div).removeClass("menu2_hide").addClass("menu2_show");
		$("#"+div+"_icon").attr("src",ctxPath+"/css/img/gx/main/button_down.png");
	});
};

function open_select_fwry(ctxPath,divId, divName){
	openWindowRandom('【服务人员】',
			ctxPath + '/common/select/fwry?divId='+divId+'&divName='+divName+'&_t='
					+ Math.random(), 600, 350);
};

function open_select_location_province(ctxPath,divId, divName){
	openWindowRandom('【选择省份】',
			ctxPath + '/common/select/location/0/'+divId+'/'+divName+'?_t='
					+ Math.random(), 600, 350);
};
function open_select_location_city(ctxPath,divId, divName, parentId){
	if($("#"+parentId).val()==''){
		tipscommon("省份不能为空！");
		return false;
	}
	openWindowRandom('【选择城市】',
			ctxPath + '/common/select/location/'+$("#"+parentId).val()+'/'+divId+'/'+divName+'?_t='
					+ Math.random(), 600, 350);
};
function open_select_location_county(ctxPath,divId, divName, parentId){
	if($("#"+parentId).val()==''){
		tipscommon("城市不能为空！");
		return false;
	}
	openWindowRandom('【选择区县】',
			ctxPath + '/common/select/location/'+$("#"+parentId).val()+'/'+divId+'/'+divName+'?_t='
					+ Math.random(), 600, 350);
};

function open_select_ipc(ctxPath,divId, divName){
	openWindowRandom('【IPC设备】',
			ctxPath + '/common/select/ipc/'+divId+'/'+divName+'?_t='
					+ Math.random(), 600, 350);
};

function open_select_alarmtype(ctxPath,divId, divName){
	openWindowRandom('【警情内容】',
			ctxPath + '/common/select/alarmtype/'+divId+'/'+divName+'?_t='
					+ Math.random(), 600, 350);
};

function open_select_alarmtypecode(ctxPath,divId, divName){
	openWindowRandom('【警情代码】',
			ctxPath + '/common/select/alarmtypecode/'+divId+'/'+divName+'?_t='
					+ Math.random(), 600, 350);
};

// 选择流量卡，2014年8月19日21:37:39
function open_select_llk(ctxPath,divId){
	openWindowRandomFixed('【流量卡】',
			ctxPath + '/common/select/llk/'+divId+'?_t='
					+ Math.random(), 600, 350);
};

// 打开新窗口，2014年8月18日08:28:00
function openWindowNew(title,url,_w,_h){
	var w = 1000;
	var h = 650;
	var top = ($(window).height()-h)*0.5;
	var left = ($(window).width()-w)*0.5;
    window.open (url,title,'height='+h+',width='+w+',top='+top+',left='+left+',toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
};


/* 
 * 2013年12月29日15:27:31
 * 
 * 创建需要销毁的window
 * 
 *  */
function createSelfWindowOnce(id,_w,_h){
	var w = 800;
	var h = 420;
	if(_w){
		w = _w;
	}

	if(_h){
		h = _h;
	}

	return $('<div id="window_'+id+'"/>').window({
		height: h,
		width: w,
		modal:true,
		closed: true,
		collapsible: true,//是否可 折叠
		maximizable: true, // 最大化
		draggable: true, //不能拖拽
		resizable: true, //不能调整大小
		//shadow: false, // If set to true,when window show the shadow will show also.
		minimizable: false,
		onClose:function(){
			$(this).window('destroy');
		}
	}).window("center");
} ;

function open_select_splb(ctxPath,divId, divName){
	openWindowRandom('【商品类别】',
			ctxPath + '/public/select/splb/'+divId+'/'+divName+'?_t='
					+ Math.random(), 600, 350);
};



/**
 * 折线图
 * chartBarDatas.div
 * chartBarDatas.title
 * chartBarDatas.xdatas
 * chartBarDatas.ynames
 * chartBarDatas.ydatas
 */
function createChartLine(chartBarDatas){
    return chart = new Highcharts.Chart({ 
        chart: { 
            renderTo: chartBarDatas.div, //图表放置的容器，关联DIV#id 
            zoomType: 'xy'   //X、Y轴均可放大 
            //因为是柱状图和曲线图共存在一个图表中，所以默认图表类型不在这里设置。 
        }, 
        title: { 
            text: chartBarDatas.title //图表标题 
        }, 
        /* subtitle: { 
            text: '数据来源：新浪财经'   //图表副标题 
        },  */
        credits: { 
            enabled: false   //不显示LOGO 
        }, 
        xAxis: [{ //X轴标签 
            categories: chartBarDatas.xdatas, 
            labels: {
                rotation: 0,  //-45-逆时针旋转45°，标签名称太长。 
                align: 'right'  //设置右对齐 
            } 
        }], 
        yAxis: [
        { //设置Y轴-第二个（金额） 
            gridLineWidth: 1,  //设置网格宽度为0，因为第一个Y轴默认了网格宽度为1 
            title: {text: '数量'},//Y轴标题设为空 
            labels: { 
                formatter: function() {//格式化标签名称 
                    return this.value; // + ' 万亿元'; 
                }, 
                style: { 
                    color: '#4572A7' //设置标签颜色 
                } 
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
 
        }], 
        tooltip: { //鼠标滑向数据区显示的提示框 
            formatter: function() {  //格式化提示框信息 
                /*var unit = { 
                    '金额': '亿元', 
                    '增幅': '%' 
                } [this.series.name]; */
                return this.series.name + '<br/>' + this.x + '<br/>' + this.y + ''; // + ' ' + unit; 
            } 
        }, 
        legend: { //设置图例 
            layout: 'horizontal', //horizontal-水平排列图例, vertical-垂直排列
            shadow: true  //设置阴影,
        }, 
/*        series: [{  //数据列 
            name: chartBarDatas.yname, 
            //color: '#4572A7', 
            type: 'column', //类型：纵向柱状图 
            yAxis: 0, //数据列关联到Y轴，默认是0，设置为1表示关联上述第二个Y轴即金额 
            data: chartBarDatas.ydatas //金额数据 
        },{  //数据列 
            name: chartBarDatas.yname+"2", 
            //color: '#2272A7', 
            type: 'column', //类型：纵向柱状图 
            yAxis: 0, //数据列关联到Y轴，默认是0，设置为1表示关联上述第二个Y轴即金额 
            data: chartBarDatas.ydatas //金额数据 
        },{  //数据列 
            name: chartBarDatas.yname+"3", 
            //color: '#2272A7', 
            type: 'column', //类型：纵向柱状图 
            yAxis: 0, //数据列关联到Y轴，默认是0，设置为1表示关联上述第二个Y轴即金额 
            data: chartBarDatas.ydatas //金额数据 
        }] , */
        series: createChartLineSeries(chartBarDatas.ynames,chartBarDatas.ydatas)
    });
};

function createChartLineSeries(ynames, ydatas){
	var series = [];
	for(var i=0;i<ynames.length;i++){
		series.push({  //数据列 
            name: ynames[i], 
            //color: '#4572A7', 
            type: 'line', //类型：纵向柱状图 
            yAxis: 0, //数据列关联到Y轴，默认是0，设置为1表示关联上述第二个Y轴即金额 
            data: ydatas[ynames[i]] //金额数据 
        });
	}
/*	$.each(ydatas, function(index, item){
		alert(item);
		series.push({  //数据列 
            name: ynames[index], 
            //color: '#4572A7', 
            type: 'column', //类型：纵向柱状图 
            yAxis: 0, //数据列关联到Y轴，默认是0，设置为1表示关联上述第二个Y轴即金额 
            data: item //金额数据 
        });
	});*/
	return series;
};

// 商品类别：选择
selectProductTypePage = function(ctxPath, divId, divName){
	openWindowRandom('选择商品类别',
			'/company/admin/productTypeListForSelect.html?'
			+'divName='+divName+'&divId='+divId+'&_t='
			+ Math.random(), 400, 600);
}

/*
 * 压缩图片
 */
function processFile(file, saveNodeId, width, height) {
	var needCompress = false;
	if(file.size > 1024 * 1024){
		// 大于 500K的图片进行压缩
		needCompress = true;
	}
	var reader = new FileReader();
	reader.onload = function(event) {
		var blob = new Blob([ event.target.result ]);
		window.URL = window.URL || window.webkitURL;
		var blobURL = window.URL.createObjectURL(blob);
		var image = new Image();
		image.src = blobURL;
		image.onload = function() {
			var resized = resizeMe(image, width, height, needCompress);
			$("#"+saveNodeId).val(resized);
			return resized;
		}
	};
	reader.readAsArrayBuffer(file);
}

function resizeMe(img, width, height, needCompress) {
	// 压缩的大小
	var max_width = 1280;
	var max_height = 800;
	
	if(width){
		max_width = width;
	}
	
	if(height){
		max_height = height;
	}

	var canvas = document.createElement('canvas');
	var width = img.width;
	var height = img.height;

	if(needCompress){
		if (width > height) {
			if (width > max_width) {
				height = Math.round(height *= max_width / width);
				width = max_width;
			}
		} else {
			if (height > max_height) {
				width = Math.round(width *= max_height / height);
				height = max_height;
			}
		}
	}

	canvas.width = width;
	canvas.height = height;

	var ctx = canvas.getContext("2d");
	ctx.drawImage(img, 0, 0, width, height);
	// 压缩率
	return canvas.toDataURL("image/jpeg", 0.95);
}

/**
 * check press is success.
 * @param id
 */
function check_compress(id){
   	if($("#"+id).val() == ""){
   		setTimeout(check_compress, 500, id);
   	}
};

function view_img(ctx, src, w, h){
	var width = $(window).width()*0.8;
	var height = $(window).height()*0.8;
	if(w){
		width = w;
	}
	if(h){
		height = h;
	}
	openWindowRandom('图片预览', ctx+'/p01/img/view.html?src='
			+encodeURIComponent(src)+'&w='+width+'&h='+height,
			width, height);
};

function view_img_with_title(ctx, src,t){
	var width = $(window).width()*0.8;
	var height = $(window).height()*0.8;
	var title = '图片预览';
	if(t){
		title = '图片预览：'+t;
	}
	openWindowRandom(title, ctx+'/p01/img/view.html?src='
			+encodeURIComponent(src)+'&w='+width+'&h='+height,
			width, height);
};
