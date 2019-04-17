
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />

<style>
<!--
#util_dm_layout_01_right_bottom>ul>li>span 
{
	display: inline-block;
	width: 100px;
	text-align: center;
}

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


<div id="util_dm_layout_01_right_bottom">

	<div id="util_dm_div_02">
		Tips1：第2行：为标题行<br/>
		Tips2：第3行：为数据行</div>
				
		<div style="padding: 20px;">
			<form id="form1" action="${ctxPath }/util/dm/excel/xlsx.do" method="post" 
			enctype="multipart/form-data">
			
			选择文件【<span class="color_red">.xlsx</span>】
			<input type="file" name="file" id="file01"/>
			<a
			id="util_dm_btn_01_save" href="javascript:void(0);" 
			class="file_upload_btn">导入更新</a>
			</form>
		</div>

</div>

<script>
	$(function() {
		
		$('#util_dm_div_02').panel({
		    height: 400,
		    iconCls: 'icon-sum',
		    title:'点微-银行账单'
		}); 

		// XLSX 
		$('#util_dm_btn_01_save').linkbutton({
			iconCls : 'icon-print'
		});

		$('#util_dm_layout_01_right_bottom').panel({
			border : false
		});
		
		// 点击文件上传按钮
		$("#util_dm_btn_01_save").on("click", function(){
			if( $("#file01").val() == ''){
				tipsinfo("请选择需要导入的xlsx文件！");
				return false;
			}
			var fileName = $("#file01").val();
			var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
			if (file_typename == '.xlsx') {
				// ok
				$("#form1").submit();
			} else {
				alert("对不起，文件格式不符.,请重新选择要上传的文件(.xlsx格式）)");
				$("#file01").val('');
				return;
			}
		});

	});
</script>