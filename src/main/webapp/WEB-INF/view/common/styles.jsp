<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="点微服务平台">
<meta http-equiv="description" content="点微服务平台">
<meta http-equiv="Content-Type" Content="text/html; Charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:set var="ctxPath" scope="request" value="${pageContext.request.contextPath}" />
<c:set var="dmutil_vesion" scope="request" value="v1.0.0" />

<link rel="stylesheet" type="text/css" href="${ctxPath }/css/main.css?v=${dmutil_vesion}">

<link rel="stylesheet" type="text/css" href="${ctxPath }/css/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctxPath }/css/easyui/themes/icon.css?v=${dmutil_vesion}">
<script type="text/javascript" src="${ctxPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctxPath }/js/easyui/jquery.easyui.min.js"></script>

<script type="text/javascript" src="${ctxPath }/js/main.js?v=${dmutil_vesion}"></script>

<script type="text/javascript" src="${ctxPath }/js/jquery.cookie.js"></script>

<script type="text/javascript" src="${ctxPath }/js/jQueryRotate.js"></script>

<!-- highcharts settings -->
<script type="text/javascript" src="${ctxPath }/plugin/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${ctxPath }/plugin/highcharts/exporting.js"></script>
<script type="text/javascript" src="${ctxPath }/plugin/highcharts/series-label.js"></script>
<script type="text/javascript" src="${ctxPath }/plugin/highcharts/export-data.js"></script>
