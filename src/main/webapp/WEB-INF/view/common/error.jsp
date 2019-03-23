<%--

访问受限
1、权限不足
2、页面不存在

--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>${title }</title>
	
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="keywords" content="点微服务平台"/>
<meta http-equiv="description" content="点微服务平台"/>
<meta http-equiv="Content-Type"  content="text/html; Charset=utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	
<script type="text/javascript" src="${ctxPath }/js/jquery-1.7.2.min.js"></script>

<style>
*{
	margin:0;
	padding:0;
}
#order_main{
	width:750px;
	margin:0 auto;
	font-size:14px;
	font-family:'宋体';
}

#order_main ul{
	list-style: none;
}
#order_main ul li{
	padding-top: 20px;
}
.pay_yes{
	color: white;
    font-family: 幼圆;
    font-size: 12px;
    background-color: #00B0F0;
    border-radius: 10px;
    width: 60px;
    height: 25px;
    cursor: hand;
}
.pay_no{
	color: white;
    font-family: 幼圆;
    font-size: 12px;
    background-color: #c0c0c0;
    border-radius: 10px;
    width: 60px;
    height: 25px;
    cursor: hand;
}
</style>

<script type="text/javascript">
<!--
$(function() {
});
//-->
</script>

</head>
<body>

	<div id="order_main" style="text-align: center; padding-top: 50px;">
	
		<table cellspacing="10">
			
			<thead>
				<tr>
					<th><span style="color: red;">单点登录失败：${error }，请联系客服！</span></th>
				</tr>
			</thead>
			
		</table>
	
	</div>
	
</body>
</html>