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
		
		$("#divCenter").load("/jimmy/math.html");
	
	});
</script>

</head>
<body id="cc" class="easyui-layout" style="text-align: center;">

<div id="divNorth" region="north" border="false" style="height: 40px; background: #FFFFFF;">
   <div class="banner_tip_class">
       <table width="98%">
           <tr>
               <td width="100%" align="center">
                   <div style="padding-top: 3px; margin-right: 10px;"><b>Jimmy学习</b>
                    	【<span id="div_showTime"></span>】
                   </div>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="divCenter" region="center" style="padding: 5px;">
	
</div>

</body>
</html>
