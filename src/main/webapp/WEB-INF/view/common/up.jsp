
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />

<div id="common_up_div_01">

	<h2>HTML5异步上传文件，带进度条</h2>

	<form id="form1" method="post" enctype="multipart/form-data">

		其他需要提交的信息：<input type="text" name="otherInfo" /><br /> <br />
		选择要上传的文件：<br /> <input type="file" name="file" id="file" /><span></span><br />

	</form>

	<br /> <br /> <input type="button" value="上传吧" onclick="upload()" />
	<br /> <br /> 上传进度：
	<progress></progress>
	<br />

	<p id="progress">0 bytes</p>

	<p id="info">info</p>

	<p id="result">result</p>
	
	<div style="border: 1px solid red; padding-top: 10px; height: 400px;">
	<input type="hidden" id="compressValue"/>
	<br/>
	<span id="process"></span>
	<br/>
	<img id="show2" width="200px" height="150"/>
	</div>

</div>


<script>
	$(function() {

		var totalSize = 0;

		//绑定所有type=file的元素的onchange事件的处理函数  
		$(':file').change(function() {
			var file = this.files[0]; //假设file标签没打开multiple属性，那么只取第一个文件就行了  
			var name = file.name;
			//var size = file.size;  
			var type = file.type;
			var url = window.URL.createObjectURL(file); //获取本地文件的url，如果是图片文件，可用于预览图片  
			var size = 0;
			if (file.size > 1024 * 1024) {
				size = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
			} else {
				size = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
			}
			$(this).next().html("<br/>原图：<br/>文件名：" + name + "<br/> 文件类型：" + type + "<br/> 文件大小：" + size + "<br/> url: " + url);
            
			$("#info").html("总大小: " + size);
			
			// 压缩后：
			//uploadBtnChange();
            //var scope = this;
            $("#compressValue").val("");
            if(window.File && window.FileReader && window.FileList && window.Blob){ 
                //获取上传file
                //获取用于存放压缩后图片base64编码
                var base64 = processFile(file, 'compressValue', 1280, 800);
                check_compress("compressValue");
            }else{
                alert("此浏览器不完全支持压缩上传图片");
            }
		});
		
		check_compress2 = function(){
           	if($("#compressValue").val()!=""){
           		$("#process").html(new Date().toLocaleTimeString());
                $("#show2").attr("src",$("#compressValue").val());
           	}else {
           		$("#process").html("undo: "+new Date().toLocaleTimeString());
           		setTimeout(check_compress, 500);
           	}
		}

		upload = function() {
			//创建FormData对象，初始化为form表单中的数据。需要添加其他数据可使用formData.append("property", "value");  
			var formData = new FormData();
			formData.append("file", document.getElementById('file').files[0]);
			formData.append("filepress", $('#compressValue').val());
			//ajax异步上传  
			$
					.ajax({
						url : "${ctxPath }/p01/up/test.do",
						type : "POST",
						data : formData,
						xhr : function() { //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数  
							myXhr = $.ajaxSettings.xhr();
							if (myXhr.upload) { //检查upload属性是否存在  
								//绑定progress事件的回调函数  
								myXhr.upload.addEventListener('progress',
										progressHandlingFunction, false);
							}
							return myXhr; //xhr对象返回给jQuery使用  
						},
						dataType : "json",
						success : function(data) {
							$("#result").html(
									data.a + "<br/>" + data.d.d1 + "<br/>"
											+ data.d.d2);
						},
						contentType : false, //必须false才会自动加上正确的Content-Type  
						processData : false
					//必须false才会避开jQuery对 formdata 的默认处理  
					});
		}

		//上传进度回调函数：  
		function progressHandlingFunction(e) {
			if (e.lengthComputable) {
				$('progress').attr({
					value : e.loaded,
					max : e.total
				}); //更新数据到进度条  
				var percent = e.loaded / e.total * 100;
				$('#progress').html(
						e.loaded + "/" + e.total + " bytes. "
								+ percent.toFixed(2) + "%");
			}
		}
	});
</script>