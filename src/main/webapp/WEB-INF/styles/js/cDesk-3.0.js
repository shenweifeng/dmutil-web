/*
 * cDesk v3.0
 * Date：2014-04-27
 * Website : http://www.cdesk.org
 * (c) 2012-2014 yanhua, im74@qq.com
 *
 * This is licensed under the GNU LGPL, version 3 or later.
 * For details, see: http://www.gnu.org/licenses/lgpl.html
 */
var cDesk = window.cDesk = {
    /* 常用工具类 */
    Common: {
        /*是否IE浏览器*/
        isIE: function () {
            if (window.ActiveXObject) {
                return true;
            }
            else if (window.XMLHttpRequest) {
                return false;
            }
        },
        isIE67: function () {
            var isie = false;
            if (window.ActiveXObject) {
                var browser = window.navigator.appName;
                var b_version = window.navigator.appVersion;
                var version = b_version.split(";");
                var trim_Version = version[1].replace(/[ ]/g, "");
                if (browser === "Microsoft Internet Explorer" && trim_Version === "MSIE7.0") {
                    isie = true;
                }
                else if (browser === "Microsoft Internet Explorer" && trim_Version === "MSIE6.0") {
                    isie = true;
                }
            }
            return isie;
        },
        /*是否全屏*/
        isFullScreen: function () {
            if (window.outerHeigth === screen.heigth && window.outerWidth === screen.width) {
                return true;
            }
            else {
                return false;
            }
        },
        /*全屏*/
        fullScreen: function () {
            if (cDesk.Common.isIE()) {
                var wsh = new ActiveXObject("WScript.Shell");
                wsh.sendKeys("{F11}");
            }
            else {
                var docElm = document.documentElement;
                if (docElm.requestFullscreen) {
                    docElm.requestFullscreen();
                } else if (docElm.mozRequestFullScreen) {
                    docElm.mozRequestFullScreen();
                } else if (docElm.webkitRequestFullScreen) {
                    docElm.webkitRequestFullScreen();
                }
            }
        },
        /* 退出全屏 */
        exitFullscreen : function() {
            if (document.exitFullscreen) {
                document.exitFullscreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitCancelFullScreen) {
                document.webkitCancelFullScreen();
            }
        },
        /* 浏览器最大化 */
        maxBrowser: function () {
            if (window.screen) {
                if (cDesk.Common.isFirstLoad) {//首次加载才设置
                    window.moveTo(0, 0);
                    window.resizeTo(screen.availWidth, screen.availHeight);
                }
                else {
                    cDesk.Common.isFirstLoad = false;
                }
            }
        },
        /*移除元素*/
        delElement: function (elem) {
            if (elem) {
                if (elem.parentNode) {
                    elem.parentNode.removeChild(elem);
                }
            }
        },
        /* Event对象兼容处理 */
        newEvent: function (event) {
            if (event.target) return event;

            var event2 = {
                target: event.srcElement || document,
                preventDefault: function () { event.returnValue = false },
                stopPropagation: function () { event.cancelBubble = true }
            };
            // IE6/7/8 在原生window.event对象写入数据会导致内存无法回收，应当采用拷贝
            for (var i in event) { event2[i] = event[i]; }
            return event2;
        },
        /* 是否首次加载 */
        isFirstLoad : true 
    },
    Class: {
        /*设置对象的class值*/
        setClassName: function (elem, value) {
            if (typeof (elem) === 'object' && elem.setAttribute) {
                if (value != undefined && value != null) {
                    if (cDesk.Common.isIE67()) {
                        elem.setAttribute("className", value);
                    }
                    else {
                        elem.setAttribute("class", value);
                    }
                }
            }
        },
        /*获取对象的class值*/
        getClassName: function (elem) {
            var val = "";
            if (typeof (elem) === 'object' && elem.getAttribute) {
                if (cDesk.Common.isIE67()) {
                    val = elem.getAttribute("className");
                }
                else {
                    val = elem.getAttribute("class");
                }
            }
            if (val === undefined || val === null) {
                val = "";
            }
            val += "";
            return val;
        }
    },
    Document: {
        getClientWidth: function () {
            var w = null;
            if (document && document.documentElement) {
                w = document.documentElement.clientWidth;
            }
            else if (document && document.body) {
                w = document.body.clientWidth;
            }
            return w;
        },
        getClientHeight: function () {
            var h = null;
            if (document && document.documentElement) {
                h = document.documentElement.clientHeight;
            }
            else if (document && document.body) {
                h = document.body.clientHeight;
            }
            return h;
        }
    },

    /* 事件操作类 */
    Event: {
        /*添加事件绑定*/
        add: function (elem, type, fn) {
            elem.addEventListener ? elem.addEventListener(type, fn, false) : elem.attachEvent('on' + type, fn);
            if (type === "contextmenu" && elem['oncontextmenu'] === null) {
                elem.oncontextmenu = fn; //oncontextmenu事件比较特殊
            }
        },
        /*移除事件绑定*/
        del: function (elem, type, fn) {
            if (elem.removeEventListener) {
                elem.removeEventListener(type, fn, false);
            }
            else if (elem.detachEvent) {
                elem.detachEvent("on" + type, fn);
            }
            else {
                elem["on" + type] = null;
            }
        },
        /*清空事件绑定*/
        empty: function (elem, type) {
            elem["on" + type] = null;
        },
        /*手动触发事件*/
        trigger: function (elem, type) {
            var ev = document.createEvent('HTMLEvents');
            ev.initEvent(type, false, true);
            elem.dispatchEvent(ev);
        }
    },

    /* 样式操作类 */
    Style: {
        /*添加样式*/
        set: function (elem, name, value) {
            if (elem) {
                if (typeof (name) === 'string') {
                    elem.style[name] = value;
                }
            }
        },
        /*获取样式值*/
        get: function (elem, name) {
            var value = undefined;
            if (elem) {
                if (elem.style[name]) {//获取内嵌样式
                    value = elem.style[name];
                }
                else if (elem.currentStyle) {//获取css样式表样式，IE
                    value = elem.currentStyle[name];
                }
                else if (document.defaultView && document.defaultView.getComputedStyle) {//获取css样式表样式，非IE
                    style = name.replace(/([A-Z])/g, '-$1').toLowerCase(); //
                    value = document.defaultView.getComputedStyle(elem, null)[name];
                }
            }
            return value;
        },
        /*移除样式*/
        del: function (elem, name) {
            if (elem) {
                if (typeof (name) === 'string') {
                    elem.style[name] = "";
                    //移除由css样式表设置的样式
                    var v = "";
                    if (elem.currentStyle) {
                        v = elem.currentStyle[name];
                    }
                    else if (document.defaultView && document.defaultView.getComputedStyle) {
                        v = document.defaultView.getComputedStyle(elem, null)[name];
                    }
                    if (v != "" && v != "auto") {
                        var className = "." + cDesk.Class.getClassName(elem);
                        var styleLeng = document.styleSheets.length;
                        for (var i = 0; i < styleLeng ; i++) {
                            var cssClass = document.styleSheets[i];
                            var cssList = cssClass.rules || cssClass.cssRules;
                            var tempLeng = cssList.length;
                            for (var j = 0; j < tempLeng; j++) {
                                var css = cssList[j];
                                if (css.selectorText == className) {
                                    css.style[name] = "";
                                }
                            }
                        }
                    }
                }
            }
        },
        /*元素透明操作*/
        Opacity: {
            get: function (elem) {
                return isOpacity
                       ? document.defaultView.getComputedStyle(elem, false).opacity
                       : ropacity.test((elem.currentStyle
				            ? elem.currentStyle.filter
				            : elem.style.filter) || '')
				                ? (parseFloat(RegExp.$1) / 100) + ''
				                : 1;
            },
            set: function (elem, value) {
                if (isOpacity) return elem.style.opacity = value;
                var style = elem.style;
                style.zoom = 1;

                var opacity = 'alpha(opacity=' + value * 100 + ')',
                    filter = style.filter || '';

                style.filter = ralpha.test(filter)
                     ? filter.replace(ralpha, opacity)
                     : style.filter + ' ' + opacity;
            }
        },
        /* 获取样式值(数字)，如：width=200px, 返回值为200 */
        getStyleNumber: function (elem, name) {
            if (elem) {
                var value = cDesk.Style.get(elem, name);
                if (value != undefined && value != 'auto') {
                    value = Number((value + "").split("px")[0]);
                }
                return value;
            }
        }
    },

    /* 拖拽类 */
    Drag: function (dragObj, moveObj, minleft, mintop, maxleft, maxtop, startDragCallback, endDragCallback) {
        var dragDOM = typeof (dragObj) === 'object' ? dragObj : document.getElementById(dragObj);
        var moveDOM = typeof (moveObj) === 'object' ? moveObj : document.getElementById(moveObj);
        if (dragDOM === null || dragDOM === undefined || moveDOM === null || moveDOM === undefined) {
            return;
        }
        if (minleft === undefined) {
            minleft = 0;
        }
        minleft = Number(minleft);
        minleft = isNaN(minleft) ? 0 : minleft;

        if (maxleft === undefined) {
            var dw = cDesk.Style.getStyleNumber(moveDOM, 'width');
            maxleft = cDesk.Document.getClientWidth() - dw;
        }
        maxleft = Number(maxleft);
        maxleft = isNaN(maxleft) ? cDesk.Document.getClientWidth() : maxleft;

        if (mintop === undefined) {
            mintop = 0;
        }
        mintop = Number(mintop);
        mintop = isNaN(mintop) ? 0 : mintop;

        if (maxtop === undefined) {
            var dh = cDesk.Style.getStyleNumber(moveDOM, 'height');
            maxtop = cDesk.Document.getClientHeight() - dh;
        }
        maxtop = Number(maxtop);
        maxtop = isNaN(maxtop) ? cDesk.Document.getClientHeight() : maxtop;

        cDesk.Event.add(dragDOM, "mousedown", function (ev) {
            var _dragMove = true;
            var _document = document;
            if (!ev) { ev = window.event; }
            ev = cDesk.Common.newEvent(ev);
            var _offsetX = ev.clientX - cDesk.Style.getStyleNumber(moveDOM, 'left'); //鼠标位置相对与移动对象的便宜值
            var _offsetY = ev.clientY - cDesk.Style.getStyleNumber(moveDOM, 'top');

            var _move = function (evm) {
                if (_dragMove) {
                    if (!evm) { evm = window.event; };
                    evm = cDesk.Common.newEvent(evm);
                    var nowleft = evm.clientX + document.body.scrollLeft - _offsetX;
                    var nowtop = evm.clientY + document.body.scrollTop - _offsetY;
                    if (document.documentElement) {
                        nowleft = evm.clientX + document.documentElement.scrollLeft - _offsetX;
                        nowtop = evm.clientY + document.documentElement.scrollTop - _offsetY;
                    }
                    else if (document.body) {
                        nowleft = evm.clientX + document.body.scrollLeft - _offsetX;
                        nowtop = evm.clientY + document.body.scrollTop - _offsetY;
                    }
                    if (nowleft >= minleft && nowleft < maxleft) {
                        cDesk.Style.set(moveDOM, 'left', nowleft + "px");
                    }
                    if (nowtop >= mintop && nowtop < maxtop) {
                        cDesk.Style.set(moveDOM, 'top', nowtop + "px");
                    }
                    evm.stopPropagation();
                    evm.preventDefault();
                }
            };
            var _end = function () {
                _dragMove = false;
                cDesk.Event.empty(_document, 'mousemove');
                cDesk.Event.empty(_document, 'mouseup');
                //执行结束拖拽回调方法
                if (endDragCallback && typeof (endDragCallback) === 'function') {
                    endDragCallback();
                }
            };

            cDesk.Event.add(_document, 'mousemove', _move);
            cDesk.Event.add(_document, 'mouseup', _end);
            ev.stopPropagation();
            ev.preventDefault();
            //执行开始拖拽回调方法
            if (startDragCallback && typeof (startDragCallback) === 'function') {
                startDragCallback();
            }
        });
    }
};

/* cDesk前期准备工作 */
(function () {
    //设置浏览器最大化[IE有效]
    cDesk.Common.maxBrowser();

    //监视浏览大小变化
    cDesk.Event.add(window, 'resize', function () {
        if (cDesk.LoadCode && typeof (cDesk.LoadCode) === 'function') {
            //cDesk.LoadCode();
        }
    });
}());

/*功能按钮类*/
cDesk.AppButton = (function () {
    function init(config) {
        //默认设置
        var setting = {
            guid: Math.random(),       /*ID*/
            appUrl: "",                /*AppURL*/
            appName: "",               /*App名称*/
            appIcon: "",               /*App图标*/
            appType: "inapp",          /*App类型,[inapp,outapp,pendant,image,video],内部功能、外部应用、挂件、图片、视频，不同类型使用不同的打开方式*/
            appIndex: 1,               /*排序*/
            appData: null,             /*附带数据*/
            appOpenedState: "default", /*打开方式 [default,max]*/
            appOpenedWidth: 850,       /*打开时宽度*/
            appOpenedHeight: 500,      /*打开时高度*/
            appLeft: 0,                /*相对与父元素的左边位置*/
            appTop: 0,                 /*相对与父元素的上边位置*/
            appObject: this,           /*当前App对象实例*/
            appDOM: null,              /*当前APP DOM对现实例*/
            appNumberDOM: null,        /*当前APP数字DOM对现实例*/
            appClickHandle: null,      /*点击处理方法*/
            appUpdateHandler: null     /*当前APP更新处理程序*/
        };
        //更新配置
        if (typeof (config) === 'object') {
            for (var p in config) {
                if (setting[p] !== undefined) {
                    setting[p] = config[p];
                }
            }
            setting.appObject = this; //防止配置参数将其覆盖
        }
        //创建AppButton对象
        (function () {
            var thisappid = "cDesk-AppButton";
            var thisappicon = "cDesk-AppButton_Icon";
            var thisappname = "cDesk-AppButton_Name";

            var app = document.createElement("div");
            app.setAttribute("id", "" + thisappid + setting.guid + "");
            cDesk.Class.setClassName(app, thisappid);
            app.setAttribute("title", "" + setting.appName + "");
            cDesk.Style.set(app, "left", setting.appLeft + "px");
            cDesk.Style.set(app, "top", setting.appTop + "px");

            var icon = document.createElement("div");
            icon.setAttribute("id", "" + thisappicon + setting.guid + "");
            cDesk.Class.setClassName(icon, thisappicon);
            var number = document.createElement("div");
            number.setAttribute("id", "" + thisappid + "_num" + setting.guid + "");
            cDesk.Class.setClassName(number, thisappid + "_num");
            var numberleft = document.createElement("div");
            cDesk.Class.setClassName(numberleft, thisappid + "_numleft");
            number.appendChild(numberleft);
            var numbertext = document.createElement("div");
            cDesk.Class.setClassName(numbertext, thisappid + "_numtext");
            number.appendChild(numbertext);
            var numberright = document.createElement("div");
            cDesk.Class.setClassName(numberright, thisappid + "_numright");
            number.appendChild(numberright);
            icon.appendChild(number);
            if (setting.appIcon === "" || setting.appIcon === null || setting.appIcon === undefined) {
                var iconimg = document.createElement("div");
                cDesk.Class.setClassName(iconimg, thisappicon + "_Defimg");
                iconimg.setAttribute("src", setting.appIcon);
            }
            else {
                var iconimg = document.createElement("img");
                iconimg.setAttribute("src", setting.appIcon);
            }
            cDesk.Style.set(iconimg, 'width', '100%');
            cDesk.Style.set(iconimg, 'height', '100%');
            icon.appendChild(iconimg);
            app.appendChild(icon);

            var name = document.createElement("div");
            name.setAttribute("id", "" + thisappname + setting.guid + "");
            cDesk.Class.setClassName(name, thisappname);

            var nameleftb = document.createElement("div");
            cDesk.Class.setClassName(nameleftb, thisappname + "_left");
            name.appendChild(nameleftb);
            var text = document.createElement("div");
            cDesk.Class.setClassName(text, thisappname + "_text");
            text.innerHTML = setting.appName;
            name.appendChild(text);
            var namerightb = document.createElement("div");
            cDesk.Class.setClassName(namerightb, thisappname + "_right");
            name.appendChild(namerightb);

            app.appendChild(name);
            cDesk.Event.add(app, 'click', function () {
                if (setting.appClickHandle && typeof (setting.appClickHandle) === 'function') {
                    setting.appClickHandle(setting.appObject);
                }
            });
            setting.appDOM = app;
            setting.appNumberDOM = number;

            //添加事件绑定，用以更新提示数据
            function appUpdateFn() {
                var nums = 0;
                if (setting.appUpdateHandler) {
                    nums = setting.appUpdateHandler();
                }
                if (isNaN(Number(nums))) {
                    nums = 0;
                }
                if (nums > 0) {
                    setting.appNumberDOM.innerHTML = nums;
                    cDesk.Style.set(setting.appNumberDOM, 'display', 'block');
                }
                else {
                    cDesk.Style.set(setting.appNumberDOM, 'display', 'none');
                }
            }
            cDesk.Event.add(app, 'load', appUpdateFn);
            if (setting.appUpdateHandler) {
                this.AppUpdate = appUpdateFn; //App更新程序，用于外部调用
            }
        }());

        //公共方法
        this.AppObject = setting.appObject,
        this.AppDOM = setting.appDOM,
        this.AppValue = function (attributeName, attributeValue) {
            if (typeof (attributeName) === 'string') {
                if (attributeValue === undefined) {
                    return setting[attributeName];
                }
                else {
                    setting[attributeName] = attributeValue;
                }
            }
        }
    }

    return {
        /*获取一个AppButton实例*/
        getInstance: function (config) {
            var instance = new init(config);
            return instance;
        },
        /*App打开方式*/
        openApp: function (desk, appbtn, pendantMoveCallback, pendantCloseCallback) {
            var result = null;
            switch (appbtn.AppValue("appType")) {
                case "outapp":
                    /*使用新浏览器窗口打开方式*/
                    var openWidth = appbtn.AppValue("appOpenedWidth");
                    var openHeight = appbtn.AppValue("appOpenedHeight");
                    var top = (window.screen.height - openHeight) / 2;
                    var left = (window.screen.width - openWidth) / 2;
                    var bconfig = "'height=" + openHeight + ",widht=" + openWidth + ",top=" + top + ",left=" + left + ",location=no";
                    try {
                        var newwin = window.open(appbtn.AppValue("appUrl") + "", "_blank", bconfig);
                        newwin.resizeTo(openWidth, openHeight);
                    }
                    catch (e) { }
                    break;
                case "pendant":
                    /*使用挂件方式打开*/
                    var boxindex = appbtn.AppDOM.getAttribute("deskboxIndex");
                    var deskbox = document.getElementById("cDesk-DeskBox" + boxindex);
                    if (!deskbox) {/*验证是否打开*/
                        var pendant = cDesk.Pendant.getInstance(desk, {
                            pendantUrl: appbtn.AppValue("appUrl"),
                            pendantName: appbtn.AppValue("appName"),
                            pendantLeft: cDesk.Document.getClientWidth() / 2,
                            pendantTop: cDesk.Document.getClientHeight() / 2,
                            moveCallback: pendantMoveCallback,
                            closeCallback: function () {
                                appbtn.AppDOM.removeAttribute("deskboxIndex");//删除打开标识
                                if (pendantCloseCallback) { pendantCloseCallback() };
                            }
                        });
                        var deskbox = desk.GetShowDesk();
                        appbtn.AppDOM.setAttribute("deskboxIndex", deskbox.getAttribute("deskindex"));//设置打开标识
                        result = pendant;
                    }
                    break;
                case "image":

                    break;
                case "video":

                    break;
                default:
                    /*使用内部打开方式*/
                    //cDesk.Windows内部验证是否打开
                    var win = cDesk.Windows.getInstance(desk, appbtn, {
                        CloseCallback: function () {
                            cDesk.Taskbar.DelTask(win);
                        }
                    });
                    cDesk.Taskbar.AddTask(win);
                    result = win;
                    break;
            }
            return result;
        }
    }
}());

/*桌面盒子*/
cDesk.DeskBox = (function () {
    //APP计数器集合
    var _arrAppNums = [];
    //窗口计数器集合
    var _arrWinNums = [];
    //桌面集合
    var _arrDeskList = [];

    //对象实例引用
    var instance;

    //初始化
    function init(namelist) {
        //私有变量
        var appNumber = [];
        if (typeof (namelist) === 'object' && namelist.length) {
            var nleng = namelist.length;
            for (var i = 0; i < nleng ; i++) {
                appNumber.push(0);
            }
        }
        var btnList = [];//按钮集合
        var deskboxName = "cDesk-DeskBox";          //分屏盒子名称
        var deskboxToolName = "cDesk-DeskBox_Tool"; //分屏工具栏名称
        var deskboxTabName = "cDesk-DeskBox_Tab";   //分屏工具按钮名称
        var appButtonWidth = 92;                    //应用盒子宽度
        var appButtonHeight = 85;                   //应用盒子高低，用于计算每个APP的left和top
        var deskList = [];                          //桌面集合
        var dexkboxToolDOM = null;                  //分屏栏DOM对象
        if (namelist === undefined || namelist === null || namelist === "") {
            namelist = [];
        };

        //创建桌面
        (function () {
            var thisdeskleft = 80; //工具栏宽度
            var thisdesktop = 35; //35是屏幕栏高度
            var taskbarheight = 50; //50是Taskbar高度
            var deskh = cDesk.Document.getClientHeight() - thisdesktop - taskbarheight;
            var deskw = cDesk.Document.getClientWidth() - thisdeskleft;
            var tempLeng = namelist.length;
            for (var i = 0; i < tempLeng ; i++) {
                var deskbox = document.createElement("div");
                deskbox.setAttribute("id", "" + deskboxName + i + "");
                cDesk.Class.setClassName(deskbox, deskboxName);
                deskbox.setAttribute("deskindex", i);
                cDesk.Style.set(deskbox, 'left', thisdeskleft + 'px');
                cDesk.Style.set(deskbox, 'top', thisdesktop + 'px');
                cDesk.Style.set(deskbox, 'top', thisdesktop + 'px');
                cDesk.Style.set(deskbox, 'width', deskw + 'px');
                cDesk.Style.set(deskbox, 'height', deskh + 'px');
                cDesk.Event.add(deskbox, 'contextmenu', function () {
                    return false; /*禁用右键菜单*/
                });
                if (i === 0) {
                    cDesk.Style.set(deskbox, 'display', 'block');
                }
                else {
                    cDesk.Style.set(deskbox, 'display', 'none');
                }
                deskList.push(deskbox);
                document.body.appendChild(deskbox);
                _arrAppNums.push(0);
                _arrWinNums.push(0);
                _arrDeskList.push(deskbox);//缓存桌布到桌面盒子，便于查找
            }
        }());

        //创建屏幕切换工具栏
        (function () {
            var tool = document.createElement("ul");
            tool.setAttribute("id", "" + deskboxToolName + "");
            cDesk.Class.setClassName(tool, deskboxToolName);
            cDesk.Style.set(tool, 'left', '400px');
            cDesk.Style.set(tool, 'height', '30px');
            cDesk.Style.set(tool, 'zIndex', 20000);
            cDesk.Drag(tool, tool, 0, 0, undefined, undefined);

            var toolleft = document.createElement("div");
            cDesk.Class.setClassName(toolleft, deskboxToolName + "_left");
            tool.appendChild(toolleft);
            var toolright = document.createElement("div");
            cDesk.Class.setClassName(toolright, deskboxToolName + "_right");
            tool.appendChild(toolright);
            var templeng = namelist.length;
            for (var i = 0; i < templeng ; i++) {
                (function (tabindex) {
                    var a = document.createElement("li");
                    a.innerHTML = namelist[i] + "";
                    a.setAttribute("id", "" + deskboxTabName + i + "");
                    cDesk.Class.setClassName(a, deskboxTabName);
                    a.setAttribute("href", "javascript:/*www.cDesk.org*/");
                    cDesk.Event.add(a, 'mousedown', function (e) {
                        CutoverDesk(tabindex);
                        //阻止事件冒泡
                        e = cDesk.Common.newEvent(e);
                        e.stopPropagation();
                        e.preventDefault();
                    });
                    if (i === 0) {
                        cDesk.Class.setClassName(a, "cDesk-DeskBox_Tab cDesk-DeskBox_Tab_show");
                    }
                    btnList.push(a);//将创建的按钮加入按钮集合
                    tool.appendChild(a);
                })(i);
            }
            document.body.appendChild(tool);
        }());

        //桌面切换
        function CutoverDesk(index) {
            var showboxid = deskboxName + index + "";
            var showtabid = deskboxTabName + index + "";
            var hidboxid = "";
            var hidtabid = "";
            var tempLeng = namelist.length;
            for (var i = 0; i < tempLeng; i++) {
                var nowtab = document.getElementById(deskboxTabName + i + "");
                var nowbox = document.getElementById(deskboxName + i + "");
                var nowboxStyle = cDesk.Style.get(nowbox, 'display');
                if (nowboxStyle != 'none') {
                    hidboxid = deskboxName + i + "";
                    hidtabid = deskboxTabName + i + "";
                    break;
                }
            }
            if (showboxid != hidboxid) {
                if (hidboxid != "" && hidtabid != "") {
                    var hidtab = document.getElementById(hidtabid);
                    var hidbox = document.getElementById(hidboxid);
                    var showbox = document.getElementById(showboxid);
                    var showtab = document.getElementById(showtabid);
                    cDesk.Class.setClassName(hidtab, "cDesk-DeskBox_Tab cDesk-DeskBox_Tab_hid");
                    cDesk.Class.setClassName(showtab, "cDesk-DeskBox_Tab cDesk-DeskBox_Tab_show");
                    if ($) {
                        //jQuery切换动画
                        for (var m = 0; m < btnList.length; m++) {
                            var obj = btnList[m];
                            if (obj.setAttribute) {
                                obj.setAttribute('disabled', true);
                            }
                            
                        }
                        $("#" + hidboxid).fadeOut(380, "linear", function () {
                            $("#" + showboxid).fadeIn(180, "linear", function () {
                                for (var n = 0; n < btnList.length; n++) {
                                    var obj = btnList[n];
                                    if (obj.removeAttribute) {
                                        obj.removeAttribute('disabled');
                                    }
                                }
                            });
                        });
                    }
                    else {
                        //原始切换效果
                        cDesk.Style.set(hidbox, 'display', 'none');
                        cDesk.Style.set(showbox, 'display', 'block');
                    }
                }
            }
        };
        //设置应用的位置
        function SetAppPosition(index,deskbox, appobj) {
            var apptop = 20;
            var appleft = 35;
            var xappNumsTemp = cDesk.Style.getStyleNumber(deskbox, 'width') / (appButtonWidth + appleft); /*横向可以放置APP按钮数量*/
            var xappNums = Number((xappNumsTemp + "").split('.')[0]); /*取整*/
            var yappNumsTemp = cDesk.Style.getStyleNumber(deskbox, 'height') / (appButtonHeight + apptop); /*纵向可以放置APP按钮数量*/
            var yappNums = Number((yappNumsTemp + "").split('.')[0]);
            var appNums = isNaN(Number(appNumber[index])) ? 0 : Number(appNumber[index]); //;
            if (appNums.length >= (xappNums * yappNums)) {
                //return; /*超过桌面可放置的最大上限就不添加到桌面*/
            }

            var appColumn = Number(((appNums / yappNums) + "").split('.')[0]); /*APP按钮放置的列号*/
            var appRow = appNums % yappNums; /*APP按钮放置的行号*/

            var thisleft = (appColumn * appButtonWidth) + ((appColumn + 1) * appleft);
            cDesk.Style.set(appobj.AppDOM, 'left', thisleft + 'px');
            var thistop = (appRow * appButtonHeight) + ((appRow + 1) * apptop);
            cDesk.Style.set(appobj.AppDOM, 'top', thistop + 'px');
            deskbox.appendChild(appobj.AppDOM);
        };
        //获取当前显示桌面
        function getShowDesk() {
            var deskleng = _arrDeskList.length;
            for (var i = 0; i < deskleng; i++) {
                var node = _arrDeskList[i];
                if (cDesk.Style.get(node, 'display') != 'none') {
                    return node;
                }
            }
        };
        //公共方法
        return {
            /*添加应用按钮到指定屏幕*/
            AddAppToDesk: function (deskIndex, appmodel) {
                var deskbox = document.getElementById(deskboxName + deskIndex + "");
                if (typeof (deskbox) === 'object') {
                    SetAppPosition(deskIndex,deskbox, appmodel);
                }
                appNumber[deskIndex] += 1;
                _arrAppNums[deskIndex] = appNumber[deskIndex];
            },
            /*从屏幕中移除APP*/
            RemoveAppForDesk: function (deskIndex, appmodel) {
                if (appmodel && appmodel.AppDOM) {
                    cDesk.Common.delElement(appmodel.AppDOM);
                }
                var nums = Number(appNumber[deskIndex]);
                nums = isNaN(nums) ? 0 : nums;
                if (nums > 0) { appNumber[deskIndex] = nums -= 1; };
                _arrAppNums[deskIndex] = appNumber[deskIndex];
            },
            /*获取当前桌面*/
            GetShowDesk: function () {
                return getShowDesk();
            },
            /*转到指定桌面*/
            GoDesk: function (index) {
                CutoverDesk(index);
            },
            /*获取桌面集合*/
            GetDeskList: deskList
        };
    }

    return {
        /*获取一个实例*/
        getInstance: function (deskNameList) {
            if (!instance) {
                instance = init(deskNameList);
            }
            return instance;
        },
        /*获取指定桌面的App数量*/
        getAppNums: function (index) {
            var nums = 0;
            if (_arrAppNums[index] !== undefined) {
                var temp = _arrAppNums[index];
                temp = Number(temp);
                if (!isNaN(temp)) {
                    nums = temp;
                }
            }
            return nums;
        },
        /*设置指定桌面的App数量*/
        setAppNums: function (index, nums) {
            if (_arrAppNums[index]) {
                _arrAppNums[index] = nums;
            }
        },
        /*获取指定桌面的窗口数量*/
        getWinNums: function (index) {
            var nums = 0;
            if (_arrWinNums[index] !== undefined) {
                var temp = _arrWinNums[index];
                temp = Number(temp);
                if (!isNaN(temp)) {
                    nums = temp;
                }
            }
            return nums;
        },
        /*设置指定桌面的窗口数量*/
        setWinNums: function (index, wnums) {
            if (_arrWinNums[index] !== undefined) {
                _arrWinNums[index] = wnums;
            }
        }
    }
}());

/*任务栏类*/
cDesk.Taskbar = (function () {
    //对象实例引用
    var instance;
    //App集合
    var _appList = [];
    //实例化
    function init() {
        var taskbarName = "cDesk-Taskbar";   //任务栏名称
        var taskbarObj = this;               //当前Taskbar对象实例
        var taskbarDOM = null;               //当前任务栏DOM对象
        var taskbarLeftDOM = 0;
        var taskbarRightDOM = 0;
        var taskContentDOM = null;           //任务内容框DOM对对象

        //创建任务栏
        (function () {
            var taskbar = document.createElement("div");
            taskbar.setAttribute("id", "" + taskbarName + "");
            cDesk.Class.setClassName(taskbar, taskbarName);
            cDesk.Event.add(taskbar, 'contextmenu', function () { return false; }); //禁用右键菜单

            //左移动按钮
            var leftBtn = document.createElement("div");
            leftBtn.setAttribute("id", "" + taskbarName + "_Left" + "");
            cDesk.Class.setClassName(leftBtn, taskbarName + "_Left");
            cDesk.Event.add(leftBtn, 'click', function () {
                var nowLeft = cDesk.Style.getStyleNumber(taskContentDOM, 'left');
                if (nowLeft < 0) {
                    nowLeft += 120;
                    if (nowLeft > 0) {
                        nowLeft = 0;
                    }
                }
                cDesk.Style.set(taskContentDOM, 'left', nowLeft + 'px');
            });
            taskbar.appendChild(leftBtn);

            //右移动按钮
            var rightBtn = document.createElement("div");
            rightBtn.setAttribute("id", "" + taskbarName + "_Right" + "");
            cDesk.Class.setClassName(rightBtn, taskbarName + "_Right");
            cDesk.Event.add(rightBtn, 'click', function () {
                var nowLeft = cDesk.Style.getStyleNumber(taskContentDOM, 'left');
                var nowWidth = cDesk.Style.getStyleNumber(taskContentDOM, 'width');
                if (((nowLeft * -1) + cDesk.Document.getClientWidth()) < nowWidth) {
                    nowLeft -= 120;
                    if (((nowLeft * -1) + cDesk.Document.getClientWidth()) > nowWidth) {
                        nowLeft = nowWidth - cDesk.Document.getClientWidth();
                    }
                }
                if (nowLeft >= 0) {
                    cDesk.Style.set(taskContentDOM, 'left', (nowLeft * -1) + 'px');
                }
                else {
                    cDesk.Style.set(taskContentDOM, 'left', nowLeft + 'px');
                }
            });
            taskbar.appendChild(rightBtn);

            /*任务栏内容框*/
            var content = document.createElement("ul");
            content.setAttribute("id", "" + taskbarName + "_Content" + "");
            cDesk.Class.setClassName(content, taskbarName + "_Content");
            taskbar.appendChild(content);

            document.body.appendChild(taskbar);
            taskbarDOM = taskbar;
            taskbarLeftDOM = leftBtn;
            taskbarRightDOM = rightBtn;
            taskContentDOM = content;
        }());

        //添加任务到任务栏
        function AddTask(winObj) {
            if (typeof (winObj) === 'object' && typeof (winObj.WindowDOM) === 'object') {
                if (typeof (winObj.AppButton) === 'object') {
                    var appicon = winObj.AppButton.AppValue('appIcon');
                    var appname = winObj.AppButton.AppValue('appName');

                    var task = document.createElement("li");
                    task.setAttribute("id", "cDesk-Taskbar_Task_" + winObj.WindowDOM.getAttribute("id") + "");
                    cDesk.Class.setClassName(task, "cDesk-Taskbar_Task");
                    task.setAttribute("title", "" + appname + "");

                    var taskiconbox = document.createElement("div");
                    cDesk.Class.setClassName(taskiconbox, "cDesk-Taskbar_Task_Icon");
                    var taskicon = document.createElement("img");
                    taskicon.setAttribute("src", appicon);
                    taskicon.setAttribute("width", "34px");
                    taskicon.setAttribute("height", "34px");
                    taskiconbox.appendChild(taskicon);
                    task.appendChild(taskiconbox);

                    var taskname = document.createElement("div");
                    cDesk.Class.setClassName(taskname, "cDesk-Taskbar_Task_Name");
                    taskname.innerHTML = appname;
                    task.appendChild(taskname);

                    //任务图标点击处理
                    cDesk.Event.add(task, 'click', function () {
                        winObj.DeskObj.GoDesk(winObj.WindowDOM.parentNode.getAttribute("deskindex"));
                        winObj.Toggle();
                    });
                    //任务图标右键处理
                    cDesk.Event.add(task, 'contextmenu', function (e) {
                        if (e === undefined) {
                            e = window.event;
                        }
                        e = cDesk.Common.newEvent(e);
                        RightClickMenu(e, winObj);
                        return false;
                    });

                    //动态设置任务框宽度
                    taskContentDOM.appendChild(task);
                    SettingStyle("add");
                    _appList.push(task);
                }
            }
        }

        //验证任务是否已经添加到任务栏
        function IsAddTask(taskid) {
            var result = false;
            var tleng = _appList.length;
            for (var i = 0; i < tleng ; i++) {
                var taskdom = _appList[i];
                if (taskdom.setAttribute("id") == taskid && taskdom.tagName.toLowerCase() == 'li') {
                    result = true;
                    break;
                }
            }
            return result;
        }

        //删除任务图标
        function DeleteTask(winObj, isCallback) {
            var winId = "cDesk-Taskbar_Task_" + winObj.WindowDOM.getAttribute("id");
            var tleng = _appList.length;
            for (var i = 0; i < tleng ; i++) {
                var taskDom = _appList[i];
                var nodeType = taskDom.tagName.toLowerCase()+"";
                var nodeId = taskDom.getAttribute('id') + "";
                if (nodeType === 'li' && nodeId === winId) {
                    cDesk.Common.delElement(taskDom);
                    SettingStyle("del");
                    break;
                }
            }
        }

        //动态设置任务栏样式
        function SettingStyle(type) {
            var nowWidth = cDesk.Style.getStyleNumber(taskContentDOM, 'width');
            var nowLeft = cDesk.Style.getStyleNumber(taskContentDOM, 'left');
            if (type === 'add') {//添加任务
                if (nowWidth == 'auto') {
                    nowWidth = 120;
                }
                else {
                    nowWidth += 120;
                }
            }
            else if (type === 'del') {//删除任务
                nowWidth -= 120;
                nowLeft += 120;
            }
            //任务盒子位置
            if (nowLeft > 10 || nowLeft == "" || nowLeft == "auto") {
                nowLeft = 10;
            }
            if ((nowLeft * -1) > nowWidth) {
                nowLeft = nowWidth - 120;
            }
            //左右按钮控制
            if (nowWidth > cDesk.Document.getClientWidth()) {
                cDesk.Style.set(taskbarLeftDOM, 'display', 'block');
                cDesk.Style.set(taskbarRightDOM, 'display', 'block');
            }
            else {
                cDesk.Style.set(taskbarLeftDOM, 'display', 'none');
                cDesk.Style.set(taskbarRightDOM, 'display', 'none');
            }
            //设置新的位置和宽度
            cDesk.Style.set(taskContentDOM, 'width', nowWidth + 'px');
            cDesk.Style.set(taskContentDOM, 'left', nowLeft + 'px');
        }

        //右键菜单方法
        function RightClickMenu(p, winobj) {
            var rcMenuName = "cDesk-Taskbar_RightClickMenu";
            //删除已经打开的右键菜单
            var oldMenu = document.getElementById(rcMenuName);
            cDesk.Common.delElement(oldMenu);

            //创建新的右键菜单
            var menu = document.createElement("div");
            menu.setAttribute("id", "" + rcMenuName + "");
            cDesk.Class.setClassName(menu, rcMenuName);
            cDesk.Event.add(document, 'click', function () {
                cDesk.Common.delElement(menu);
                cDesk.Event.empty(document, 'click');
            });

            //设置位置
            var menuWidth = 120;
            var menuHeight = 90;
            var offset = 8;
            var theLeft = p.clientX + offset;
            var theTop = p.clientY - menuHeight - offset;
            if ((theLeft + menuWidth) > cDesk.Document.getClientWidth()) {
                theLeft -= (menuWidth + offset);
            }
            cDesk.Style.set(menu, 'left', theLeft + 'px');
            cDesk.Style.set(menu, 'top', theTop + 'px');

            /*添加菜单项*/
            var minItem = document.createElement("div");
            cDesk.Class.setClassName(minItem, rcMenuName + "_Item");
            minItem.innerHTML = "最小化";
            cDesk.Event.add(minItem, 'click', function () {
                winobj.Minimize();
                cDesk.Common.delElement(menu);
            });
            menu.appendChild(minItem);
            var maxItem = document.createElement("div");
            cDesk.Class.setClassName(maxItem, rcMenuName + "_Item");
            maxItem.innerHTML = "最大化";
            cDesk.Event.add(maxItem, 'click', function () {
                winobj.DeskObj.GoDesk(winobj.WindowDOM.parentNode.getAttribute("deskindex"));
                winobj.Maximize();
                winobj.Show();
                cDesk.Common.delElement(menu);
            });
            menu.appendChild(maxItem);
            var closeItem = document.createElement("div");
            cDesk.Class.setClassName(closeItem, rcMenuName + "_Item");
            closeItem.innerHTML = "关闭";
            cDesk.Event.add(closeItem, 'click', function () {
                winobj.Close();
                cDesk.Common.delElement(menu);
            });
            menu.appendChild(closeItem);
            document.body.appendChild(menu);
        }

        //公共方法
        return {
            //添加图标到任务栏
            AddTask: function (winObj) {
                AddTask(winObj);
            },
            //从任务栏移除任务图标，并关闭窗口
            DelTask: function (winObj) {
                DeleteTask(winObj, true);
            }
        }
    }

    //创建对象实例
    if (!instance) {
        instance = init();
    }
    return instance;
}());

/*工具栏类*/
cDesk.Toolbar = (function () {
    //对象实例引用
    var _instance;
    var _appList = [];
    //实例化
    function init(desk, config) {
        //默认配置参数
        var setting = {
            toolbarName: "cDesk-Toolbar",        //任务栏名称
            toolbarWidth: 60,                    //任务栏宽度
            toolbarHeight: 350,
            toolbarLeft: 0,
            toolbarTop: (cDesk.Document.getClientHeight() - 350) / 2,
            setBtn1Title: "设置1",
            setBtn2Title: "设置2",
            setBtn3Title: "设置3",
            setBtn4Title: "设置4",
            setBtn1Handler: null,
            setBtn2Handler: null,
            setBtn3Handler: null,
            setBtn4Handler: null,
            outBtnHandler: null,                 //退出按钮处理方法
            toolbarDOM: null,                    //当前工具栏DOM对象
            toolbarContentDOM: null              //工具内容框DOM对对象
        };
        //更新配置
        if (typeof (config) === 'object') {
            for (var p in config) {
                if (setting[p] !== undefined) {
                    setting[p] = config[p];
                }
            }
            setting.toolbarObj = this; //防止配置参数将其覆盖
        }

        //创建工具栏
        (function () {
            //工具栏框
            var toolbar = document.createElement("div");
            toolbar.setAttribute("id", "" + setting.toolbarName + "");
            cDesk.Class.setClassName(toolbar, setting.toolbarName);
            cDesk.Style.set(toolbar, 'top', (setting.toolbarTop - 50) + "px");
            cDesk.Event.add(toolbar, 'contextmenu', function (p) { return false; }); //禁用右键菜单
            cDesk.Event.add(toolbar, 'mousedown', function (ev) {/*绑定拖拽*/
                cDesk.Style.set(toolbar, 'cursor', 'move');
                cDesk.Style.set(toolbar, 'zIndex', 39999);
                var _dragMove = true;
                var doc = document;
                if (!ev) {
                    ev = window.event;
                }
                ev = cDesk.Common.newEvent(ev);
                var X = ev.clientX - document.body.scrollLeft - toolbar.offsetLeft;
                cDesk.Event.add(doc, 'mousemove', function (e) {
                    if (_dragMove) {
                        if (!e) {
                            e = window.event;
                        }
                        e = cDesk.Common.newEvent(e);
                        cDesk.Style.set(toolbar, 'left', (e.clientX + document.body.scrollLeft - X) + "px");
                    }
                });
                cDesk.Event.add(doc, 'mouseup', function () {
                    if (_dragMove) {
                        var nowwidth = cDesk.Style.getStyleNumber(toolbar, 'width');
                        var nowleft = cDesk.Style.getStyleNumber(toolbar, 'left');
                        var nowaxis = cDesk.Document.getClientWidth() / 2;
                        var ttleng = desk.GetDeskList.length;
                        if (nowleft <= nowaxis) {
                            cDesk.Style.set(toolbar, 'left', '0px');
                            for (var i = 0; i < ttleng; i++) {
                                var deskbox = desk.GetDeskList[i];
                                cDesk.Style.set(deskbox, 'left', (nowwidth + 20) + "px");
                            }
                        }
                        else {
                            cDesk.Style.del(toolbar, 'left');
                            cDesk.Style.set(toolbar, 'right', '0px');
                            for (var i = 0; i < ttleng; i++) {
                                var deskbox = desk.GetDeskList[i];
                                cDesk.Style.set(deskbox, 'left', "0px");
                            }
                        }
                        cDesk.Style.del(toolbar, 'cursor');
                        cDesk.Style.set(toolbar, 'zIndex', 9999);
                        cDesk.Event.empty(doc, 'mousemove');
                        cDesk.Event.empty(doc, 'mouseup');
                        _dragMove = false;
                    }
                });
            });

            var toolbartop = document.createElement("div");
            cDesk.Class.setClassName(toolbartop, setting.toolbarName + "_Top");
            toolbar.appendChild(toolbartop);
            var toolbarbottom = document.createElement("div");
            cDesk.Class.setClassName(toolbarbottom, setting.toolbarName + "_Bottom");
            toolbar.appendChild(toolbarbottom);

            /*快捷功能图标*/
            var content = document.createElement("div");
            content.setAttribute("id", "" + setting.toolbarName + "_Content");
            cDesk.Class.setClassName(content, setting.toolbarName + "_Content");
            toolbar.appendChild(content);
            setting.toolbarContentDOM = content;

            /*桌面设置图标块*/
            var set = document.createElement("div");
            set.setAttribute("id", "" + setting.toolbarName + "_Setting");
            cDesk.Class.setClassName(set, setting.toolbarName + "_Setting");
            var setbtn1 = document.createElement("div");
            setbtn1.setAttribute("id", "" + setting.toolbarName + "_Setting_Btn1");
            cDesk.Class.setClassName(setbtn1, setting.toolbarName + "_Setting_Btn");
            setbtn1.setAttribute("title", "" + setting.setBtn1Title + "");
            cDesk.Event.add(setbtn1, 'mousedown', function (e) {
                if (setting.setBtn1Handler) {
                    setting.setBtn1Handler(setbtn1);
                }
                //阻止事件冒泡
                e = cDesk.Common.newEvent(e);
                e.stopPropagation();
                e.preventDefault();
            });
            set.appendChild(setbtn1);

            var setbtn2 = document.createElement("div");
            setbtn2.setAttribute("id", "" + setting.toolbarName + "_Setting_Btn2");
            cDesk.Class.setClassName(setbtn2, setting.toolbarName + "_Setting_Btn");
            setbtn2.setAttribute("title", "" + setting.setBtn2Title + "");
            cDesk.Event.add(setbtn2, 'mousedown', function (e) {
                if (setting.setBtn2Handler) {
                    setting.setBtn2Handler(setbtn2);
                }
                //阻止事件冒泡
                e = cDesk.Common.newEvent(e);
                e.stopPropagation();
                e.preventDefault();
            });
            set.appendChild(setbtn2);

            var setbtn3 = document.createElement("div");
            setbtn3.setAttribute("id", "" + setting.toolbarName + "_Setting_Btn3");
            cDesk.Class.setClassName(setbtn3, setting.toolbarName + "_Setting_Btn");
            setbtn3.setAttribute("title", "" + setting.setBtn3Title + "");
            cDesk.Event.add(setbtn3, 'mousedown', function (e) {
                if (setting.setBtn3Handler) {
                    setting.setBtn3Handler(setbtn3);
                }
                //阻止事件冒泡
                e = cDesk.Common.newEvent(e);
                e.stopPropagation();
                e.preventDefault();
            });
            set.appendChild(setbtn3);

            var setbtn4 = document.createElement("div");
            setbtn4.setAttribute("id", "" + setting.toolbarName + "_Setting_Btn4");
            cDesk.Class.setClassName(setbtn4, setting.toolbarName + "_Setting_Btn");
            setbtn4.setAttribute("title", "" + setting.setBtn4Title + "");
            setbtn4.setAttribute("state", "none");
            cDesk.Event.add(setbtn4, 'mousedown', function (e) {
                if (setting.setBtn4Handler) {
                    setting.setBtn4Handler(setbtn4);
                }
                //阻止事件冒泡
                e = cDesk.Common.newEvent(e);
                e.stopPropagation();
                e.preventDefault();
            });
            set.appendChild(setbtn4);
            toolbar.appendChild(set);

            /*退出按钮*/
            var outbutton = document.createElement("div");
            outbutton.setAttribute("id", "" + setting.toolbarName + "_Outbutton");
            cDesk.Class.setClassName(outbutton, setting.toolbarName + "_Outbutton");
            outbutton.setAttribute("title", "退出系统");
            cDesk.Event.add(outbutton, 'mousedown', function (e) {
                if (window.confirm("您确定要退出系统吗？")) {
                    if (setting.outBtnHandler) {
                        setting.outBtnHandler();
                    }
                }
                //阻止事件冒泡
                e = cDesk.Common.newEvent(e);
                e.stopPropagation();
                e.preventDefault();
            });
            toolbar.appendChild(outbutton);
            document.body.appendChild(toolbar);
        }());

        //添加APP到工具栏
        function addApp(appbutton) {
            var appname = setting.toolbarName + "_Content_App";
            var appleng = _appList.length;
            if (appleng < 5) {
                var app = document.createElement("div");
                app.setAttribute("id", "" + appname + "_" + appleng + "");
                cDesk.Class.setClassName(app, appname);
                app.setAttribute("title", "" + appbutton.AppValue("appName") + "");
                var top = 0;
                if (appleng > 0) {
                    top = appleng * 45;
                }
                cDesk.Style.set(app, 'top', top + "px");
                cDesk.Event.add(app, 'click', function () {
                    if (appbutton.AppValue('appClickHandle')) {
                        appbutton.AppValue('appClickHandle')(appbutton);
                    }
                });

                var appicon = document.createElement("img");
                cDesk.Class.setClassName(appicon, appname + "_Icon");
                appicon.setAttribute("src", appbutton.AppValue("appIcon"));
                app.appendChild(appicon);
                setting.toolbarContentDOM.appendChild(app);
                _appList.push(app);
            }
        }

        /*添加一个AppButton到任务栏*/
        this.AddAppbutton = function (appbotton) {
            addApp(appbotton);
        }
    }

    return {
        /*获取一个对象实例*/
        getInstance: function (desk, setting) {
            if (!_instance) {
                _instance = new init(desk, setting);
            }
            return _instance;
        }
    }
}());

/*桌面窗口类*/
cDesk.Windows = (function () {
    function init(desk, appbutton, config) {
        //默认配置参数
        var _ContentIframe = null;
        var setting = {
            windowName: "cDesk-Windows",
            windowTitle: "新窗口",
            windowWidth: 850,
            windowHeight: 500,
            ShwoHome: true,
            ShowRefresh: true,               //是否显示刷新按钮
            ShowMinimize: true,              //是否显示最小化按钮
            ShowMaximize: true,              //是否显示最大化按钮
            ShowClose: true,                 //是否显示关闭按钮
            MinimizeCallback: null,          //最小化后的回调方法
            MaximizeCallback: null,          //最大化后的回调方法
            ReductionCallback: null,         //还原后的回调方法
            CloseCallback: null,             //关闭后的回调方法
            Move: true,                      //窗口是否可以移动
            Lock: false,                     //是否开启锁屏
            Resize: true,                    //是否允许调整窗口大小
            windowObj: this,                 //当前对象实例
            windowDOM: null,                 //当前窗口DOM对象
            maximizeBtn: null,               //最大化按钮
            reductionBtn: null               //还原按钮
        };
        //更新配置参数
        if (typeof (config) === 'object') {
            for (var p in config) {
                if (setting[p] !== undefined) {
                    setting[p] = config[p];
                }
            }
            setting.windowObj = this; //防止配置参数将其覆盖
        }

        //初始化方法
        var deskbox = null;
        var deskindex = null;
        var appid = null;
        var appurl = null;
        var appname = null;
        var apptype = null;
        var MyCopyright = "javascript:/*www.cdesk.org*/";
        var IndexName = "winZIndex";
        var isopened = false; //App是否打开标识

        (function () {
            if (typeof (desk) !== 'object') {
                return;
            }
            if (typeof (appbutton) !== 'object') {
                return;
            }
            deskbox = desk.GetShowDesk();
            deskindex = deskbox.getAttribute("deskindex");
            appid = appbutton.AppDOM.getAttribute("id");
            appurl = appbutton.AppValue("appUrl");
            appname = appbutton.AppValue("appName");

            //APP没有打开才创建窗口
            if (!IsOpenedApp()) {
                if (appbutton) {
                    setting.windowTitle = appname;
                }
                //创建打开窗口
                CreateWindow();
                //设置APP打开标识
                appbutton.AppDOM.setAttribute("deskboxIndex", deskindex);
                if (appbutton.AppValue("appOpenedState") + "" === "max") {
                    MaximizeHandler();
                }
                var wNums = cDesk.DeskBox.getWinNums(deskindex);
                wNums += 1;
                cDesk.DeskBox.setWinNums(deskindex, wNums);
            }
            else {
                isopened = true;
            }
        }());

        //验证APP是否已经打开========??????
        function IsOpenedApp() {
            var result = false;
            var deskIndex = appbutton.AppDOM.getAttribute("deskboxIndex");
            var oldDeskbox = document.getElementById("cDesk-DeskBox" + deskIndex);
            if (oldDeskbox) {
                result = true;
                deskbox = oldDeskbox;
                var openedApps = deskbox.childNodes;
                var ttleng = openedApps.length;
                for (var i = 0; i < ttleng ; i++) {
                    var openapp = openedApps[i];
                    if (cDesk.Class.getClassName(openapp) + "" === setting.windowName + "") {
                        if (openapp.getAttribute("appid") === appid) {
                            //如果已经打开APP就显示在最上面
                            var nowZindex = Number(deskbox.getAttribute("windZIndex"));
                            var meZindex = cDesk.Style.get(openapp, 'zIndex');
                            if (meZindex < nowZindex) {
                                meZindex = nowZindex + 1;
                                cDesk.Style.set(openapp, 'zIndex', meZindex);
                                deskbox.setAttribute("windZIndex", meZindex);
                            }
                            cDesk.Style.set(openapp, 'display', 'block');
                            desk.GoDesk(deskIndex); /*转到对应的桌面*/
                            break;
                        }
                    }
                }
            }
            return result;
        }

        //创建并打开窗口
        function CreateWindow() {
            //窗口是否加载完成
            var isLoad = false;
            var baseZIndex = 10000;
            //设置窗口Zindex
            if (!deskbox.getAttribute(IndexName)) {
                deskbox.setAttribute(IndexName, baseZIndex);
            }
            var maxleft = cDesk.Document.getClientWidth() - 200;
            var maxtop = cDesk.Document.getClientHeight() - 200;
            var winZIndex = Number(deskbox.getAttribute(IndexName)) + 1;
            var win = document.createElement("div");
            var winId = setting.windowName + Math.random();
            win.setAttribute("id", "" + winId + "");
            cDesk.Class.setClassName(win, setting.windowName);
            win.setAttribute("name", "" + setting.windowName + ""); /*方便查找*/
            win.setAttribute("appid", appid);
            cDesk.Style.set(win, 'zIndex', winZIndex);
            cDesk.Style.set(win, 'width', setting.windowWidth + "px");
            cDesk.Style.set(win, 'height', setting.windowHeight + "px");
            var wnums = cDesk.DeskBox.getWinNums(deskindex) +1;
            cDesk.Style.set(win, 'left', ((wnums * 30) % maxleft) + "px"); //设置窗口默认位置
            cDesk.Style.set(win, 'top', ((wnums * 40) % maxtop) + "px");

            //东方位框
            var winEastBox = document.createElement("div");
            winEastBox.setAttribute("id", "" + winId + "_East");
            cDesk.Class.setClassName(winEastBox, setting.windowName + "_East");
            win.appendChild(winEastBox);
            //南方位框
            var winSouthBox = document.createElement("div");
            winSouthBox.setAttribute("id", "" + winId + "_South");
            cDesk.Class.setClassName(winSouthBox, setting.windowName + "_South");
            win.appendChild(winSouthBox);
            //西方位框
            var winWesternBox = document.createElement("div");
            winWesternBox.setAttribute("id", "" + winId + "_Western");
            cDesk.Class.setClassName(winWesternBox, setting.windowName + "_Western");
            win.appendChild(winWesternBox);
            //北方位框
            var winNorthBox = document.createElement("div");
            winNorthBox.setAttribute("id", "" + winId + "_North");
            cDesk.Class.setClassName(winNorthBox, setting.windowName + "_North");
            win.appendChild(winNorthBox);
            var winWesternTopBox = document.createElement("div");
            winWesternTopBox.setAttribute("id", "" + winId + "_Westerntop");
            cDesk.Class.setClassName(winWesternTopBox, setting.windowName + "_Westerntop");
            win.appendChild(winWesternTopBox);
            var winEastTopBox = document.createElement("div");
            winEastTopBox.setAttribute("id", "" + winId + "_Easttop");
            cDesk.Class.setClassName(winEastTopBox, setting.windowName + "_Easttop");
            win.appendChild(winEastTopBox);
            //东北方位框
            var winNortheastBox = document.createElement("div");
            winNortheastBox.setAttribute("id", "" + winId + "_Northeast");
            cDesk.Class.setClassName(winNortheastBox, setting.windowName + "_Northeast");
            win.appendChild(winNortheastBox);
            //东南方位框
            var winSoutheastBox = document.createElement("div");
            winSoutheastBox.setAttribute("id", "" + winId + "_Southeast");
            cDesk.Class.setClassName(winSoutheastBox, setting.windowName + "_Southeast");
            win.appendChild(winSoutheastBox);
            //西南方位框
            var winNorthwestBox = document.createElement("div");
            winNorthwestBox.setAttribute("id", "" + winId + "_Southwest");
            cDesk.Class.setClassName(winNorthwestBox, setting.windowName + "_Southwest");
            win.appendChild(winNorthwestBox);
            //西北方位框
            var winSouthwestBox = document.createElement("div");
            winSouthwestBox.setAttribute("id", "" + winId + "_Northwest");
            cDesk.Class.setClassName(winSouthwestBox, setting.windowName + "_Northwest");
            win.appendChild(winSouthwestBox);

            //标题栏
            var winTitle = document.createElement("div");
            winTitle.setAttribute("id", "" + winId + "_Title");
            cDesk.Class.setClassName(winTitle, setting.windowName + "_Title");
            cDesk.Event.add(winTitle, "dblclick", function () { _isMaximize ? ReductionHandler() : MaximizeHandler() });
            //标题栏中的标题框
            var TitleBox = document.createElement("div");
            cDesk.Class.setClassName(TitleBox, setting.windowName + "_TitleBox");
            TitleBox.innerHTML = setting.windowTitle;
            winTitle.appendChild(TitleBox);
            //标题栏中的常规按钮框
            var FunButtonBox = document.createElement("div");
            cDesk.Class.setClassName(FunButtonBox, setting.windowName + "_ButtonBox");
            //主页按钮
            var btnHome = document.createElement("a");
            btnHome.setAttribute("id", "" + setting.windowName + "_Homebtn");
            cDesk.Class.setClassName(btnHome, setting.windowName + "_Homebtn");
            btnHome.setAttribute("href", MyCopyright);
            btnHome.setAttribute("title", "主页");
            cDesk.Event.add(btnHome, 'click', function () { HomeHandler(btnHome); });
            if (!setting.ShwoHome) {
                cDesk.Style.set(btnHome, 'display', 'none');
            }
            FunButtonBox.appendChild(btnHome);
            //刷新按钮
            var btnRefresh = document.createElement("a");
            btnRefresh.setAttribute("id", "" + setting.windowName + "_Refreshbtn");
            cDesk.Class.setClassName(btnRefresh, setting.windowName + "_Refreshbtn");
            btnRefresh.setAttribute("href", MyCopyright);
            btnRefresh.setAttribute("title", "刷新");
            cDesk.Event.add(btnRefresh, 'click', function () { RefreshHandler(btnRefresh); });
            if (!setting.ShowRefresh) {
                cDesk.Style.set(btnRefresh, 'display', 'none');
            }
            FunButtonBox.appendChild(btnRefresh);
            //最小化按钮
            var btnMinimize = document.createElement("a");
            btnMinimize.setAttribute("id", "" + setting.windowName + "_Minimizebtn");
            cDesk.Class.setClassName(btnMinimize, setting.windowName + "_Minimizebtn");
            btnMinimize.setAttribute("href", MyCopyright);
            btnMinimize.setAttribute("title", "最小化");
            cDesk.Event.add(btnMinimize, 'click', function () { MinimizeHandler(btnMinimize); });
            if (!setting.ShowMinimize) {
                cDesk.Style.set(btnMinimize, 'display', 'none');
            }
            FunButtonBox.appendChild(btnMinimize);
            //最大化按钮
            var btnMaximize = document.createElement("a");
            btnMaximize.setAttribute("id", "" + setting.windowName + "_Maximizebtn");
            cDesk.Class.setClassName(btnMaximize, setting.windowName + "_Maximizebtn");
            btnMaximize.setAttribute("href", MyCopyright);
            btnMaximize.setAttribute("title", "最大化");
            btnMaximize.setAttribute("winZIndex", 999999); /*最大化时窗口的Z序*/
            cDesk.Event.add(btnMaximize, 'click', function () { MaximizeHandler(); });
            if (!setting.ShowMaximize) {
                cDesk.Style.set(btnMaximize, 'display', 'none');
            }
            setting.maximizeBtn = btnMaximize;
            FunButtonBox.appendChild(btnMaximize);
            //还原按钮
            var btnReduction = document.createElement("a");
            btnReduction.setAttribute("id", "" + setting.windowName + "_Reductionbtn");
            cDesk.Class.setClassName(btnReduction, setting.windowName + "_Reductionbtn");
            btnReduction.setAttribute("href", MyCopyright);
            btnReduction.setAttribute("title", "还原");
            btnReduction.setAttribute("winZIndex", winZIndex); /*还原时保持窗口原来的宽、高和Z序*/
            btnReduction.setAttribute("winWidth", cDesk.Style.get(win, 'width'));
            btnReduction.setAttribute("winHeight", cDesk.Style.get(win, 'height'));
            setting.reductionBtn = btnReduction;
            cDesk.Event.add(btnReduction, 'click', function () { ReductionHandler(); });
            FunButtonBox.appendChild(btnReduction);
            //关闭按钮
            var btnClose = document.createElement("a");
            btnClose.setAttribute("id", "" + setting.windowName + "_Closebtn");
            cDesk.Class.setClassName(btnClose, setting.windowName + "_Closebtn");
            btnClose.setAttribute("href", MyCopyright);
            btnClose.setAttribute("title", "关闭");
            cDesk.Event.add(btnClose, 'click', function () { CloseHandler(btnClose); });
            if (!setting.ShowClose) { cDesk.Style.set(btnClose, 'display', 'none'); };
            FunButtonBox.appendChild(btnClose);
            winTitle.appendChild(FunButtonBox);
            win.appendChild(winTitle);

            //创建内容窗口
            var winContent = document.createElement("div");
            cDesk.Class.setClassName(winContent, setting.windowName + "_Content");
            var winContentLoadBox = document.createElement("div");
            cDesk.Class.setClassName(winContentLoadBox, setting.windowName + "_Content_LoadBox");
            var winContentLoadImg = document.createElement("div");
            cDesk.Class.setClassName(winContentLoadImg, setting.windowName + "_Content_LoadImg");
            winContentLoadBox.appendChild(winContentLoadImg);
            var winContentLoadText = document.createElement("div");

            winContentLoadText.innerHTML = "<br/><b><i>努力加载中....</i></b>";
            winContentLoadBox.appendChild(winContentLoadText);
            winContent.appendChild(winContentLoadBox);
            var winContentIframe = document.createElement("iframe");
            _ContentIframe = winContentIframe;
            winContentIframe.setAttribute("frameBorder", "no");
            winContentIframe.setAttribute("scrolling", "auto");
            winContentIframe.setAttribute("src", appurl);
            winContentIframe.setAttribute("id", "" + setting.windowName + "_Content_Iframe_" + winZIndex + "");
            cDesk.Class.setClassName(winContentIframe, setting.windowName + "_Content_Iframe");
            cDesk.Style.set(winContentIframe, 'display', "none");
            cDesk.Event.add(winContentIframe, 'load', function () {
                if (!isLoad) {
                    cDesk.Common.delElement(winContentLoadImg);
                    winContentLoadText.innerHTML = "<b><i>拖拽中，放开显示....</i></b>";
                    cDesk.Class.setClassName(winContentLoadText, setting.windowName + "_Content_LoadText");
                    cDesk.Style.set(winContentLoadBox, 'display', "none");
                    cDesk.Style.set(winContentIframe, 'display', "block");
                    isLoad = true;
                }
            });
            winContent.appendChild(winContentIframe);
            win.appendChild(winContent);
            deskbox.appendChild(win);
            deskbox.setAttribute(IndexName, winZIndex);

            //主页事件处理方法
            function HomeHandler(btn) {
                winContentIframe.setAttribute("src", winContentIframe.getAttribute("src"));
            }
            //刷新事件处理方法
            function RefreshHandler(btn) {
                try {
                    if (winContentIframe.contentDocument) {
                        var newurl = winContentIframe.contentDocument.URL;
                        winContentIframe.setAttribute("src", newurl);
                    }
                }
                catch (e) {
                    //跨域访问网页时，winContentIframe.contentDocument被禁用
                }
            }
            setting.windowDOM = win;

            //支持拖拽
            if (setting.Move === true) {
                cDesk.Drag(win, win, -300, 0, undefined, 400, function () { 
                    cDesk.Style.set(winContentLoadBox, 'display', "block");
                    cDesk.Style.set(winContentIframe, 'display', "none");
                },
                function () {
                    cDesk.Style.set(winContentLoadBox, 'display', "none");
                    cDesk.Style.set(winContentIframe, 'display', "block");
                });
            }
            //选择后，窗体显示在最上面
            cDesk.Event.add(win, 'click', function () {
                var maxZindex = Number(deskbox.getAttribute(IndexName));
                var meZindex = cDesk.Style.get(win, 'zIndex');
                if (meZindex < maxZindex) {
                    meZindex = maxZindex + 1;
                    cDesk.Style.set(win, 'zIndex', meZindex);
                    deskbox.setAttribute(IndexName, meZindex);
                }
            });

            //8秒后如果页面还未加载完成，就手动触发加载完成事件，因为flash页面不会触发加载完成事件
            (function () {
                setTimeout(function () {
                    cDesk.Event.trigger(winContentIframe, 'load');
                }, 8000);
            }());
        }

        //显示窗口
        function ShowWindows() {
            var maxZindex = Number(deskbox.getAttribute(IndexName));
            var meZindex = Number(cDesk.Style.get(setting.windowDOM, 'zIndex'));
            if (meZindex < maxZindex) {
                meZindex = maxZindex + 1;
                cDesk.Style.set(setting.windowDOM, 'zIndex', meZindex);
                deskbox.setAttribute(IndexName, meZindex);
            }
            cDesk.Style.set(setting.windowDOM, 'display', 'block');
        }
        //最小化
        function MinimizeHandler(btn) {
            cDesk.Style.set(setting.windowDOM, 'display', 'none');
            if (setting.MinimizeCallback) {
                setting.MinimizeCallback(setting.windowObj);
            }
        }
        //窗口显示切换
        function Toggle() {
            var state = cDesk.Style.get(setting.windowDOM, 'display');
            if (state === "none") {
                ShowWindows();
            }
            else if (state === "block") {
                MinimizeHandler();
            }
        }
        //是否最大化
        var _isMaximize = false;
        //最大化
        function MaximizeHandler() {
            var oldleft = cDesk.Style.get(setting.windowDOM, 'left');
            var oldtop = cDesk.Style.get(setting.windowDOM, 'top');
            setting.reductionBtn.setAttribute("winLeft", oldleft);
            setting.reductionBtn.setAttribute("winTop", oldtop);
            var maxZindex = setting.maximizeBtn.getAttribute("winZIndex");
            cDesk.Style.set(setting.windowDOM, 'zIndex', maxZindex);
            cDesk.Style.set(setting.windowDOM, 'width', cDesk.Document.getClientWidth() - 10 + "px");
            cDesk.Style.set(setting.windowDOM, 'height', cDesk.Document.getClientHeight() - 10 + "px");
            cDesk.Style.set(setting.windowDOM, 'top', '5px');
            cDesk.Style.set(setting.windowDOM, 'left', '5px');
            cDesk.Style.set(setting.maximizeBtn, 'display', 'none');
            cDesk.Style.set(setting.reductionBtn, 'display', 'block');
            if (setting.MaximizeCallback) {
                setting.MaximizeCallback(setting.windowObj);
            }
            _isMaximize = true;
        }
        //还原
        function ReductionHandler() {
            var newIndex = setting.reductionBtn.getAttribute("winZIndex");
            var newW = setting.reductionBtn.getAttribute("winWidth");
            var newH = setting.reductionBtn.getAttribute("winHeight");
            var newL = setting.reductionBtn.getAttribute("winLeft");
            var newT = setting.reductionBtn.getAttribute("winTop");
            cDesk.Style.set(setting.windowDOM, 'zIndex', newIndex);
            cDesk.Style.set(setting.windowDOM, 'width', newW);
            cDesk.Style.set(setting.windowDOM, 'height', newH);
            cDesk.Style.set(setting.windowDOM, 'left', newL);
            cDesk.Style.set(setting.windowDOM, 'top', newT);
            cDesk.Style.set(setting.reductionBtn, 'display', 'none');
            cDesk.Style.set(setting.maximizeBtn, 'display', 'block');
            if (setting.ReductionCallback) {
                setting.ReductionCallback(setting.windowObj);
            }
            _isMaximize = false;
        }
        //关闭
        function CloseHandler(btn) {
            cDesk.Style.set(setting.windowDOM, 'display', 'none');
            appbutton.AppDOM.removeAttribute("deskboxIndex");
            if (setting.CloseCallback) {
                setting.CloseCallback(setting.windowObj);
            }
            if (_ContentIframe !== null) {
                _ContentIframe.src = "about:blank";
                _ContentIframe = null;
            }
            cDesk.Common.delElement(_ContentIframe);//删除框架
            setting.windowDOM.innerHTML = "";
            cDesk.Common.delElement(setting.windowDOM);
            setting.windowDOM = null;
            setting.windowObj = null;

            var num = cDesk.DeskBox.getWinNums(deskindex);
            num -= 1;
            cDesk.DeskBox.setWinNums(deskindex,num);
        }

        //公共方法，如果APP已经打开，就返回一个未设置值
        if (!isopened) {
            return {
                AppButton: appbutton,
                WindowObj: setting.windowObj,
                WindowDOM: setting.windowDOM,
                Reduction: ReductionHandler,
                Minimize: MinimizeHandler,
                Maximize: MaximizeHandler,
                Close: CloseHandler,
                Show: ShowWindows,
                Toggle: Toggle,
                DeskObj: desk
            }
        }
        else {
            return undefined;
        }
    }

    return {
        /*获取一个Windows实例*/
        getInstance: function (desk, appbutton, config) {
            var instance = init(desk, appbutton, config);
            return instance;
        }
    }
}());

/*主题类*/
cDesk.Themes = (function () {
    //对象实例引用
    var instance;

    //实例化
    function init(config) {
        //默认配置
        var setting = {
            Name: "cDesk-ThemeBox",
            Width: 550,
            Height: 485,
            That: this,
            ThatDOM: null,
            ContentDOM: null,
            CloseCallback: null
        };
        var appNums = 0;
        if (config) {
            if (config.Name) {
                setting.Name = config.Name;
            }
            if (config.Width) {
                setting.Width = config.Width;
            }
            if (config.Height) {
                setting.Height = config.Height;
            }
            if (config.CloseCallback) {
                setting.CloseCallback = config.CloseCallback;
            }
        }

        //创建主题盒子
        (function () {
            var themebox = document.createElement("div");
            themebox.setAttribute("id", "" + setting.Name + "");
            cDesk.Class.setClassName(themebox, setting.Name);
            var left = (cDesk.Document.getClientWidth() - setting.Width) / 2 + 'px';
            var top = (cDesk.Document.getClientHeight() - setting.Height) / 2 + 'px';
            cDesk.Style.set(themebox, 'left', left);
            cDesk.Style.set(themebox, 'top', top);

            var title = document.createElement("div");
            title.setAttribute("id", "" + setting.Name + "_Title");
            cDesk.Class.setClassName(title, setting.Name + "_Title");
            title.innerHTML = "主题设置";

            var close = document.createElement("div");
            close.setAttribute("id", "" + setting.Name + "_Close");
            cDesk.Class.setClassName(close, setting.Name + "_Close");
            close.setAttribute("title", "关闭");
            cDesk.Event.add(close, 'click', function (e) {
                closeThemeBox();
            });
            title.appendChild(close);
            themebox.appendChild(title);

            var content = document.createElement("div");
            content.setAttribute("id", "" + setting.Name + "_Content");
            cDesk.Class.setClassName(content, setting.Name + "_Content");
            var contentBox = document.createElement("div");
            contentBox.setAttribute("id", "" + setting.Name + "_ContentBox");
            cDesk.Class.setClassName(contentBox, setting.Name + "_ContentBox");
            content.appendChild(contentBox);
            themebox.appendChild(content);

            setting.ContentDOM = contentBox;
            setting.ThatDOM = themebox;
            document.body.appendChild(themebox);
            cDesk.Drag(title, themebox, 0, 0, 1200, 500);
        }());

        //添加主题到盒子
        function addTheme(config) {
            var _setting = {
                themeName: '默认主题',
                themeIcon: '',
                themeImage: '',
                themeCss: '',
                setCallback: null
            };
            //更新配置参数
            if (typeof (config) === 'object') {
                for (var p in config) {
                    if (_setting[p] !== undefined) {
                        _setting[p] = config[p];
                    }
                }
            }
            var IdName = "cDesk-Theme";
            var theme = document.createElement("div");
            theme.setAttribute("id", "" + IdName + appNums + "");
            cDesk.Class.setClassName(theme, IdName);
            theme.setAttribute("title", "点击设置为主题");
            var themeTitle = document.createElement("div");
            cDesk.Class.setClassName(themeTitle, IdName + "_Title");
            themeTitle.innerHTML = _setting.themeName;
            theme.appendChild(themeTitle);
            var themeContent = document.createElement("div");
            themeContent.setAttribute("id", "" + IdName + "_Content");
            cDesk.Class.setClassName(themeContent, IdName + "_Content");
            var themeImg = document.createElement("img");
            cDesk.Class.setClassName(themeImg, IdName + "_Img");
            themeImg.setAttribute("src", _setting.themeIcon);
            themeContent.appendChild(themeImg);
            theme.appendChild(themeContent);
            cDesk.Event.add(theme, 'click', function (e) {
                var backimg = new Image();
                backimg.src = _setting.themeImage;
                cDesk.Event.add(backimg, 'load', function () {
                    cDesk.Style.set(document.body, "background", "url('" + _setting.themeImage + "')");
                });
                if (_setting.setCallback) {
                    _setting.setCallback(_setting);
                }
            });
            setting.ContentDOM.appendChild(theme);
            appNums += 1;
        };
        //显示主题盒子
        function showThemeBox() {
            cDesk.Style.set(setting.ThatDOM, 'display', 'block');
        }
        //关闭主题盒子
        function closeThemeBox() {
            cDesk.Style.set(setting.ThatDOM, 'display', 'none');
            setting.ContentDOM = null;
            setting.ThatDOM = null;
            setting.That = null;
            cDesk.Common.delElement(setting.ThatDOM);
            instance = undefined;
            if (CloseCallback && typeof(CloseCallback)==='function') {
                CloseCallback();
            }
        }

        //公共方法
        return {
            AddTheme: function (config) { addTheme(config); },
            ShowThemeBox: showThemeBox,
            CloseThemeBox: closeThemeBox,
            ThemeboxObj: setting.That,
            ThemeboxDOM: setting.ThatDOM
        }
    };

    return {
        /*获取一个对象实例*/
        getInstance: function (config) {
            if (!instance) {
                instance = init(config);
            }
            return instance;
        },
        /* 是否创建 */
        isCreate: function () {
            if (instance) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}());

/*挂件类*/
cDesk.Pendant = (function () {
    function init(desk, config) {
        var _PendantIframe = null;
        //默认配置参数
        var setting = {
            pendantUrl: "",                //URL
            pendantName: "cDesk-Pendant",  //名称
            pendantLeft: 9999,             //大于屏幕宽度就向右对齐，小于0就左对齐
            pendantTop: -1,                //大于屏幕高度就向下对齐，小于0就顶对齐
            loadType: 'iframe',            //加载方式，html方式或者iframe方式
            type: "part",                  //局部:part,全局:global
            data: null,                    //关联数据
            isClose: true,                 //是否可关闭
            isMove: true,                  //是否可移动
            moveCallback: null,            //移动后的回调方法
            closeCallback: null,           //关闭后的回调方法
            pendantDOM: null
        };
        //更新配置参数
        if (typeof (config) === 'object') {
            for (var p in config) {
                if (setting[p] !== undefined) {
                    setting[p] = config[p];
                }
            }
            setting.pendantList = [];
        }
        //创建挂件
        (function () {
            var deskbox = desk.GetShowDesk();
            var maxleft = cDesk.Document.getClientWidth() - 10;
            var maxtop = cDesk.Document.getClientHeight() - 20;
            var pendantWidth = 0;
            var pendantHeight = 0;
            var className = "cDesk-Pendant";
            var pendant = document.createElement("div");
            var pendantId = className + Math.random();
            pendant.setAttribute("id", "" + pendantId + "");
            cDesk.Class.setClassName(pendant, className);

            //创建内容框
            if (setting.loadType && setting.loadType === "iframe") {
                var pendantIframe = document.createElement("iframe");
                _PendantIframe = pendantIframe;
                cDesk.Class.setClassName(pendantIframe, className + "_Iframe");
                var ifid = className + "_" + Math.random();
                pendantIframe.setAttribute("id", ifid);
                pendantIframe.setAttribute("frameBorder", "no");
                pendantIframe.setAttribute("scrolling", "auto");
                pendantIframe.setAttribute("src", setting.pendantUrl);
                cDesk.Event.add(pendantIframe, 'load', function () {
                    //自动适应内容宽高
                    try {
                        if (cDesk.Common.isIE67()) {
                            pendantWidth = Math.max(pendantIframe.Document.body.scrollWidth,
                                                    pendantIframe.Document.documentElement.scrollWidth);
                            pendantHeight = Math.max(pendantIframe.Document.body.scrollHeight,
                                                     pendantIframe.Document.documentElement.scrollHeight);
                        }
                        else {
                            var bw = -1;
                            var bh = -1;
                            if (pendantIframe.contentDocument.body) {
                                bw = pendantIframe.contentDocument.body.scrollWidth;
                                bh = pendantIframe.contentDocument.body.scrollHeight;
                            }
                            var dw = -1;
                            var dh = -1;
                            if (pendantIframe.contentDocument.documentElement) {
                                dw = pendantIframe.contentDocument.documentElement.scrollWidth;
                                dh = pendantIframe.contentDocument.documentElement.scrollHeight;
                            }
                            pendantWidth = Math.max(bw, dw);
                            pendantHeight = Math.max(bh, dh);
                        }
                    }
                    catch (e) {
                        pendantWidth = 150;
                        pendantHeight = 90;
                    }
                    cDesk.Style.set(pendant, 'width', pendantWidth + "px");
                    cDesk.Style.set(pendant, 'height', pendantHeight + "px");
                    cDesk.Style.set(pendantTopbox, 'width', (pendantWidth + 45) + "px");
                    cDesk.Style.set(pendantTopbox, 'height', (pendantHeight + 45) + "px");
                    //支持移动挂件
                    if (setting.isMove) {
                        var maxleft = cDesk.Document.getClientWidth() - pendantWidth;
                        var maxtop = cDesk.Document.getClientHeight() - pendantHeight;
                        cDesk.Drag(pendantTopbox, pendant, 0, 0, maxleft, maxtop);
                    }
                    else {
                        cDesk.Style.set(pendantTopbox, 'cursor', "default");
                    }
                    SetLeftTop();
                });
                pendant.appendChild(pendantIframe);
            }
            else {
                //预留接口，html方式
            }
            //设置挂件位置
            SetLeftTop();
            function SetLeftTop() {
                if (setting.pendantLeft < 0) {
                    setting.pendantLeft = 0;
                }
                else if (setting.pendantLeft > (maxleft - pendantWidth)) {
                    setting.pendantLeft = maxleft - pendantWidth;
                }
                else if ((setting.pendantLeft - (pendantWidth/2)) > 0) {
                    setting.pendantLeft -= (pendantWidth/2);
                }
                cDesk.Style.set(pendant, 'left', setting.pendantLeft + 'px');

                if (setting.pendantTop < 5) {
                    setting.pendantTop = 5;
                }
                else if (setting.pendantTop > (maxtop - pendantHeight)) {
                    setting.pendantTop = maxtop - pendantHeight;
                }
                else if ((setting.pendantTop - (pendantHeight / 2)) > 0) {
                    setting.pendantTop -= (pendantHeight / 2);
                }
                cDesk.Style.set(pendant, 'top', setting.pendantTop + 'px');
            };

            //创建挂件外框
            var pendantTopbox = document.createElement("div");
            pendantTopbox.setAttribute("id", "" + pendantId + "_Top");
            cDesk.Class.setClassName(pendantTopbox, className + "_Top");
            cDesk.Event.add(pendant, 'mouseover', function () {
                cDesk.Style.set(pendantTopbox, 'display', 'block');
            });
            cDesk.Event.add(pendant, 'mouseout', function () {
                cDesk.Style.set(pendantTopbox, 'display', 'none');
            });
            /*关闭按钮*/
            var pendantClosebtn = document.createElement("div");
            pendantClosebtn.setAttribute("id", "" + pendantId + "_Close");
            cDesk.Class.setClassName(pendantClosebtn, className + "_Close");
            pendantClosebtn.setAttribute("title", "移除挂件");
            if (setting.isClose) {
                cDesk.Style.set(pendantClosebtn, 'display', 'block');
                cDesk.Event.add(pendantClosebtn, 'click', function () {
                    if (_PendantIframe) {
                        _PendantIframe.src = "about:blank";
                        _PendantIframe = null;
                        cDesk.Common.delElement(_PendantIframe);
                    }
                    if (setting.closeCallback) {
                        setting.closeCallback(setting.data);
                    }
                    pendant.innerHTML = "";
                    cDesk.Common.delElement(pendant);
                    pendant = null;
                    setting.pendantDOM = null;
                    setting.pendantObj = null;
                });
            }
            else {
                cDesk.Style.set(pendantClosebtn, 'display', 'none');
            }
            pendantTopbox.appendChild(pendantClosebtn);
            pendant.appendChild(pendantTopbox);
            setting.pendantDOM = pendant;

            //全局挂件在每个桌面都可见，局部挂件只能在指定桌面可见
            if (setting.type == "global") {
                document.body.appendChild(pendant);
            }
            else {
                deskbox.appendChild(pendant);
            }
        }());

        //公共方法
        return {
            PendantDOM: setting.pendantDOM,
            PendantLeft: setting.pendantLeft,
            PendantTop: setting.pendantTop,
            Data: setting.data
        }
    }

    return {
        /*获取一个对象实例*/
        getInstance: function (desk, config) {
            var instance = init(desk, config);
            return instance;
        }
    }
}());

/* cDesk加载代码，建议将cDesk所有调用代码放入这里 */
cDesk.LoadCode = null;