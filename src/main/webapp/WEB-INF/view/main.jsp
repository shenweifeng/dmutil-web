<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />

<html>
<head>
<title> ${title } </title>

<jsp:include page="common/styles.jsp"></jsp:include>

<script>
	$(function() {

		var data = [ {
			id : 0,
			text : '${title}',
			state : 'open',
			children : [{
				id : 10,
				text : '拆分工具',
				state : 'open',
				children : [{
					id : 101,
					text : '安踏',
					children : [{
						id : 10101,
						text : '安踏-部门拆分',
						attributes : {
							url : '${ctxPath }/util/anta.html'
						}
					}]
				},{
					id : 102,
					text : '七匹狼',
					children : [{
						id : 10201,
						text : '七匹狼-部门拆分',
						attributes : {
							url : '${ctxPath }/util/sw.html'
						}
					}]
				}]
			}]
		} ];

		main_init = function() {
			// $("#banner_top").css("background-image", "url('/css/images/banner_${SESSION_LOCALE.language}.jpg')");
			$("#a_logout").css("text-decoration", "none"); // blink
			$('#tree_left').tree({
				data : data,
				animate : true,
				lines : true,
				//dnd: true,
				//checkbox: true,
				onSelect : function(node) {

					if (node.id == 999 && node.text == '系统注销') {
						$.messager.confirm("系统提示", '确定要注销?', function(r) {
							if (r) {
								window.location.href = "${ctxPath}/p01/logout.html";
							}
						});
						return;
					}
					
					if (node.attributes && node.attributes.url) {
						// 在新标签页中打开，=2017年11月2日10:29:48
						var reload = true;
						if(node.attributes.fix){
							reload = false;
						}
						Open(node.text, node.attributes.url, reload);
					}
				},
				onLoadSuccess : function(node, data) {
					// 默认选择“身份证审核”
					//var node = $('#tree_left').tree('find', 1001);
					//$('#tree_left').tree('select', node.target);
				}
			});
		};
		main_init();

		$('#divCenter').css('background', '#FFFFFF'); // #D2E9FF
		$('#divWest').css('background', '#FFFFFF');
		$('#divNorth').css('background', '#FFFFFF');
		$('#divSouth').css('background', '#FFFFFF');

		$(document).bind({
			click : function() {
				//tooltip.hide();
			},
			contextmenu : function(e) {
				//return false;
			}
		});

		// 标题栏居中
		$(".panel-header .panel-title").css("text-align", "center");
		
 		showTime = function() {
			$("#div_showTime").html(formatDateTime(new Date()));
			window.setTimeout(showTime, 1000);
		};
		showTime();

		// start of tab setting... 在右边center区域打开菜单，新增tab
		function Open(text, url, reload) {
			var tabCount = $('#tabs_main').tabs('tabs').length;// 获取当前打开窗口总数量
			var hasTab = $('#tabs_main').tabs('exists', text); //根据名称判断窗口是否已打开
			var add = function() {
				if (hasTab) {
					$('#tabs_main').tabs('select', text);
					// 是否要刷新
					if(reload){
						$('#tabs_main').tabs('getSelected').panel('refresh', url);
					}
				} else {
					$('#tabs_main').tabs('add', {
						title : text,
						closable : true,
						href : url,
					});
				}
			}
			add();
		};
		
		//绑定tabs的右键菜单
		$("#tabs_main").tabs({
			onSelect:function(title){
		        if(title == '首页'){
		        	// 刷新首页显示内容
		        	//$("#index_main").load("${ctxPath}/company/admin/main.html");
		        }
		    },
			onContextMenu : function(e, title) {
				e.preventDefault();
				$('#tabsMenu').menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data("tabTitle", title);
			}
		});

		//实例化menu的onClick事件
		$("#tabsMenu").menu({
			onClick : function(item) {
				CloseTab(this, item.name);
			}
		});

		//几个关闭事件的实现
		function CloseTab(menu, type) {
			var curTabTitle = $(menu).data("tabTitle");
			var tabs = $("#tabs_main");
			if (type === "close") {
				tabs.tabs("close", curTabTitle);
				return;
			}
			;
			var allTabs = tabs.tabs("tabs");
			var closeTabsTitle = [];
			$.each(allTabs, function() {
				var opt = $(this).panel("options");
				if (opt.closable && opt.title != curTabTitle
						&& type === "Other") {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === "All") {
					closeTabsTitle.push(opt.title);
				}
			});
			for (var i = 0; i < closeTabsTitle.length; i++) {
				tabs.tabs("close", closeTabsTitle[i]);
			}
			;
		}
		
		// end of tab setting...

		//查看图片
		$(".img_view").on('click', function() {
			view_img('${ctxPath}',$(this).data('img'));
		});
		
	});
</script>

</head>
<body id="cc" class="easyui-layout" style="text-align: center;">

	<input type="hidden" id="screenHeight" value="" />
	<input type="hidden" id="screenHeight360" value="" />
	<input type="hidden" id="screenHeight300" value="" />
	<input type="hidden" id="peishouExist" value="0" />

<div id="divNorth" region="north" border="false" style="height: 40px; background: #FFFFFF;">
   <div id="banner_top" class="banner_class"></div>
   <div class="banner_tip_class">
       <table width="98%">
           <tr>
               <td width="30%" align="left">
                   <div id="userinfo" style="color:blue; padding-left: 10px; font-size: 14px;">
                   	 </div>
               </td>
               <td width="50%" align="center">
                   <div style="padding-top: 10px;"><b>${title }</b></div>
                </td>
                <td width="20%" align="right">
                    <div id="div_showTime"></div>
                </td>
            </tr>
        </table>
    </div>
</div>

	<div id="divWest" region="west" split="true"
		style="width: 200px; padding-left: 5px; padding-top: 5px; text-align: left;">
		<div id="globalTips"></div>
		<ul  id="tree_left"></ul>
	</div>

	<div id="divCenter" region="center" style="padding: 5px;">

		<div class="easyui-tabs" fit="true" style="width:500px;height:250px;"
		border="true" id="tabs_main">

			<div title="首页" style="padding: 10px;">
				<div id="index_main">
					用于初始化显示提示信息
					<%-- <br/><spring:message code="project.name"/> --%>
				</div>
			</div>

		</div>
	</div>

	<div id="online_users_div"></div>
	
	<div id="tabsMenu" class="easyui-menu" style="width: 120px;">
		<div name="close">关闭</div>
		<div name="Other">关闭其他</div>
		<div name="All">关闭所有</div>
	</div>

</body>
</html>
