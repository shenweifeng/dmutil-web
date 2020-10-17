
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />

<style>
<!--
#util_jf_layout_01_right_bottom>ul>li>span 
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


<div id="util_jf_layout_01_right_bottom">
	
	<div id="util_jf_div_02">
		<div style="padding: 20px; color: red;">
		Tips：第1行为标题行，第2行开始为数据行，且只读取第1个sheet数据！<br/>
		Tips：如果第1行为非标题行，需要删除第1行，不然数据读取会出错！<br/>
		Tips：列名配置【计划开始日期，计划结束日期】，可配置！<br/>
		</div>
				
		<div style="padding: 20px;">
			<form id="jf_form1" action="${ctxPath }/util/jf/excel/xlsx.do" method="post" 
			enctype="multipart/form-data">
			
			<div style="padding-bottom: 20px;">
			日期所在列-名称：<input type="text" id="dateColumnName" name="dateColumnName" value="计划开始日期，计划结束日期" style="width: 300px;"/>
			</div>
			
			选择文件【<span class="color_red">.xlsx</span>】
			<input type="file" name="file" id="jf_file01"/>
			<a
			id="util_jf_btn_01_save" href="javascript:void(0);" 
			class="file_upload_btn">导入更新</a>
			</form>
		</div>

	</div>
</div>

<script>
	$(function() {
		
		$('#util_jf_div_02').panel({
		    height: 400,
		    iconCls: 'icon-sum',
		    title:'建发-报表数据-修订日期'
		}); 
	
		// 导入更新按钮
		$('#util_jf_btn_01_save').linkbutton({
			iconCls : 'icon-print'
		});

		$('#util_jf_layout_01_right_bottom').panel({
			border : false
		});
		
		// 点击文件上传按钮
		$("#util_jf_btn_01_save").on("click", function(){
			if( $("#jf_file01").val() == ''){
				tipsinfo("请选择需要导入的xlsx文件！");
				return false;
			}
			var fileName = $("#jf_file01").val();
			var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
			if (file_typename == '.xlsx') {
				// ok
				$("#jf_form1").submit();
			} else {
				alert("对不起，文件格式不符.,请重新选择要上传的文件(.xlsx格式）)");
				$("#jf_file01").val('');
				return;
			}
		});

	});
</script>