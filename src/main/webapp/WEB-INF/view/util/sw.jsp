
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />

<style>
<!--
#util_sw_layout_01_right_bottom>ul>li>span 
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


<div id="util_sw_layout_01_right_bottom">

	<div id="util_sw_div_01">
	
		<div style="padding: 20px; color: red;">
		Tips1：请先导入部门信息，再导入需要拆分的报表，顺序不要错！<br/>
		Tips2：第1行为标题行，第2行开始为数据行，且只读取第1个sheet数据！</div>
	
		<div style="padding: 20px;">
			<form id="form11" action="${ctxPath }/util/sw/import/dept.do" method="post" 
			enctype="multipart/form-data">
			1、导入部门信息：选择文件【<span class="color_red">.xlsx</span>】
			<input type="file" name="file" id="file11"/>
			<a
			id="util_sw_btn_11_save" href="javascript:void(0);" 
			class="file_upload_btn">导入部门</a>
			
			<br/>	
				<span style="width: 140px;" data-pid="file11" 
					class="css_span_btn_red">导入进度：</span>		
              	<progress id="progress_file11" value="0" style="width: 80px;"></progress>&nbsp;
              	<span id="progress_percent_file11">0%</span>&nbsp;
              	<span id="progress_size_up_file11"></span>&nbsp;
              	
			</form>
		</div>
	
	</div>
	
	<div id="util_sw_div_02">
		<div style="padding: 20px; color: red;">
		Tips：第2行为标题行，第3行开始为数据行，且只读取第1个sheet数据！</div>
		
		<div style="padding: 20px;">
			<form id="form1" action="${ctxPath }/util/sw/excel/xlsx.do" method="post" 
			enctype="multipart/form-data">
			选择文件【<span class="color_red">.xlsx</span>】
			<input type="file" name="file" id="file01"/>
			<a
			id="util_sw_btn_01_save" href="javascript:void(0);" 
			class="file_upload_btn">导入更新</a>
			</form>
		</div>

	</div>
</div>

<script>
	$(function() {
		
		$('#util_sw_div_01').panel({
		    height: 300,
		    iconCls: 'icon-print',
		    title:'七匹狼-数据导入-部门'
		}); 
		$('#util_sw_div_02').panel({
		    height: 400,
		    iconCls: 'icon-sum',
		    title:'七匹狼-报表数据-部门拆分'
		}); 

		// 部门导入
		$('#util_sw_btn_11_save').linkbutton({
			iconCls : 'icon-print'
		});
		
		// XLSX 
		$('#util_sw_btn_01_save').linkbutton({
			iconCls : 'icon-print'
		});

		$('#util_sw_layout_01_right_bottom').panel({
			border : false
		});
		
		// 点击文件上传按钮
		$("#util_sw_btn_01_save").on("click", function(){
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

		// 部门：导入
		$("#util_sw_btn_11_save").on("click", function(){
			if( $("#file11").val() == ''){
				tipsinfo("请选择需要导入的文件！");
				return false;
			}
			var fileName = $("#file11").val();
			var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
			if (file_typename == '.xlsx' || file_typename == '.csv') {
				// ok
				var pid = "file11";
				var file = document.getElementById(pid).files[0];
	            if (file) {
	    	        var size = file.size;
	    	        $("#progress_size_up_"+pid).html("0/" + size);
	    	        
	    	      	//创建FormData对象，初始化为form表单中的数据。需要添加其他数据可使用formData.append("property", "value");  
	    	        var formData = new FormData();  
	    	        formData.append("file", document.getElementById(pid).files[0]);
	    	        //ajax异步上传  
	    	        $.ajax({  
	    	            url: "${ctxPath }/util/sw/import/dept.do",  
	    	            type: "POST",  
	    	            data: formData,  
	    	            xhr: function(){ //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数  
	    	                myXhr = $.ajaxSettings.xhr();  
	    	                if(myXhr.upload){ //检查upload属性是否存在  
	    	                    //绑定progress事件的回调函数  
	    	                    myXhr.upload.addEventListener('progress', function(e) {
	    	            	        if (e.lengthComputable) {  
	    	            	            $('#progress_'+pid).attr({value : e.loaded, max : e.total}); //更新数据到进度条  
	    	            	            var percent = e.loaded/e.total*100;  
	    	            	            percent = percent.toFixed(0);
	    	            	            if(percent==100){
	    	            	            	percent = 99;
	    	            	            }
	    	            	            $('#progress_percent_'+pid).html(percent + "%");
	    	                	        $("#progress_size_up_"+pid).html(e.loaded + "/" + e.total + "&nbsp;<font color='blue'>导入上传中...</font>");  
	    	            	        }  
	    	            	    }  , false);   
	    	                }  
	    	                return myXhr; //xhr对象返回给jQuery使用  
	    	            },  
	    	            dataType: "json",
	    	            success: function(data){
	    	            	if(data.r1==200){
	    	            		// 上传成功
	        	    	        $("#progress_size_up_"+pid).html("<font color='red'>导入上传成功：</font>"+data.d1); 
	    	            		$("#file_"+pid).val();
	            	            $('#progress_percent_'+pid).html("100%");
	    	            	}else {
	    	            		// 上传失败
	        	    	        $("#progress_size_up_"+pid).html("导入上传失败！"); 
	    	            	}
	    	            },  
	    	            contentType: false, //必须false才会自动加上正确的Content-Type  
	    	            processData: false  //必须false才会避开jQuery对 formdata 的默认处理  
	    	        }); 
	            }
			} else {
				alert("对不起，文件格式不符.,请重新选择要上传的文件( .xlsx 格式  )");
				$("#file11").val('');
				return;
			}
		});

	});
</script>