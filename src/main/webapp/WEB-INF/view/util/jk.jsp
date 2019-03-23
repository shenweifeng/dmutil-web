
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />

<style>
<!--
.input_250 {
	display: inline-block;
	width: 310px;
	text-align: left;
	padding-left: 5px;
}

.input_100 {
	display: inline-block;
	width: 100px;
	text-align: left;
	padding-left: 5px;
}
-->
</style>


<div id="util_jk_layout_01_right_bottom">

	<form action="${ctxPat }/sdk/api/alarm/jk" 
	enctype="multipart/form-data"
	method="post">
	<input type="text" name="p1" value="08203ebad1ce" />
	<input type="text" name="p2" value="001" />
	<input type="text" name="p3" value="1009" />
	<input type="text" name="p4" value="2019-02-18 20:45:01" />
	<input type="file" name="p5" />
	<input type="submit" value="提交" />
	</form>

</div>

<script>
	$(function() {
		
	});
</script>