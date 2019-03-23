<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>非法请求</title>
    <meta name="format-detection" content="telephone=no" />
    <meta name="format-detection" content="address=no" />
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    
	<script type="text/javascript" src="${ctxPath }/js/jquery-1.7.2.min.js"></script>

</head>
<body>
<div style="padding: 20px; margin: 0 auto; font-size: 18px;">
非法请求，请重新 <a href="${ctxpath }/index.html"> 登录  </a>

<div style="padding: 10px; ">
<span id="error404_times" style="color: red; ">10</span>S后自动退出系统...
</div>

<script>
	$(function() {
		
		var djs = 10;
 		daojishi = function() {
			$("#error404_times").html(djs);
			djs = djs - 1;
			if(djs<=0){
				window.location.href = "${ctxPath}/index.html"; 
			}else {
				window.setTimeout(daojishi, 1000);
			}
		};
		daojishi();
		
	});
</script>

</div>

</body>