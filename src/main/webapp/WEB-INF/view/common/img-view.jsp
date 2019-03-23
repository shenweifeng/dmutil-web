
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />

<div id="common_img_view_div_01" style="margin: 0 auto; text-align: center;">

	<img id="common_img_view_img_01" alt="图片" 
	src="${src }" width="800px"
	title="单击可以进行旋转" />
	
</div>


<script>
	$(function() {
		
		var rotate = 0;
		$("#common_img_view_img_01").click(function(){
			if(rotate>=360){
				rotate = 0;
			}
			rotate += 90;
			$(this).rotate(rotate);
		});
		
	});
</script>