
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />

<style>
<!--
#company_shops_listShops_import_layout_01_right_bottom>ul>li>span 
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


<div id="company_shops_listShops_import_layout_01_right_bottom">

	<ul style="padding-top: 5px;">

		<li><span>所属连锁：</span> 
		<select id="import_input_listShops_chainId" class="input_250" >
			<c:forEach items="${chains }" var="chain">
			<option value="${chain.chainId }">${chain.chainName }</option>
			</c:forEach>
		</select>
		</li>
		
		<li><span>所在省份：</span>
		<input id="import_input_listShops_province"  class="input_250" />
		</li>
		<li><span>所在城市：</span>
		<input id="import_input_listShops_city"  class="input_250" />
		</li>
		
		<li><span>服务团队：</span> <input
			id="import_input_listShops_teamId" value="" class="input_250" /></li>

		<li><span>存款开户行：</span> 
		<select id="import_input_listShops_khhBankTypeId" class="input_250" >
			<c:forEach items="${bankTypes }" var="region">
			<option value="${region.bankTypeId }">${region.bankTypeName }</option>
			</c:forEach>
		</select>
		</li>
		
		<li><span>现金存款银行：</span> 
		<select id="import_input_listShops_coinSaveBankId" class="input_250" >
			<option value="">-选择-</option>
			<c:forEach items="${banks }" var="region">
			<option value="${region.bankId }">${region.bankName }</option>
			</c:forEach>
		</select>
		</li>
		<li><span>零钱兑换银行：</span> 
		<select id="import_input_listShops_coinChangeBankId" class="input_250" >
			<option value="">-选择-</option>
			<c:forEach items="${banks }" var="region">
			<c:if test="${region.coinChange==1 }">
			<option value="${region.bankId }">${region.bankName }</option>
			</c:if>
			</c:forEach>
		</select>
		</li>
		
		<li>
		
		<span style="width: 140px; text-align: left;" id="order_cash_huidan_send_up">&nbsp;点击上传导入[.xls]文件</span>
		<span id="show_size_teamFile04"></span>
			
			<!-- 文件上传统一配置 -->
            <div id="div_teamFile04" style="padding-top: 5px;">
                  	<input type="hidden" id="file_teamFile04"/>
                  	<span  style="width: 140px;" data-pid="teamFile04" class="css_span_btn_red">导入进度：</span>
                  	<progress id="progress_teamFile04" value="0" style="width: 80px;"></progress>&nbsp;
                  	<span id="progress_percent_teamFile04">0%</span>&nbsp;
                  	<br/><span id="progress_size_up_teamFile04"></span>&nbsp;
            </div>
               	<div style="display:none;">
               	<input type="file"
               	id="teamFile04" name="file04" placeholder="上传服务单"/></div>
		</li>
		
		<li><a
			id="company_shops_listShops_import_btn_01_save" href="javascript:void(0);" 
			data-pid="teamFile04" class="file_upload_btn">导入</a>
		</li>
	</ul>
</div>

<script>
	$(function() {

		$('#company_shops_listShops_import_btn_01_save').linkbutton({
			iconCls : 'icon-print'
		});

		$('#company_shops_listShops_import_layout_01_right_bottom').panel({
			border : false
		});
		
		// 省份-城市-级联
		$('#import_input_listShops_province').combobox({
			editable: false,
		    url: '${ctxPath}/p01/getRegionSf',
		    valueField: 'code',
		    textField: 'name',
		    onSelect: function(record){
		    	$("#import_input_listShops_city").combobox("clear");
		    	if($(this).combobox("getValue")!=''){
		    		$("#import_input_listShops_city").combobox(
		    		"reload", "${ctxPath}/p01/getRegionCs/"
		    			+$(this).combobox("getValue"));
		    	}
		    }
		});
		$('#import_input_listShops_city').combobox({
			editable: false,
		    valueField: 'code',
		    textField: 'name'
		});
		

		f_post_url_company_shops_listShops = function() {
			var url = '';
			var data = new Object();
			// save
			url = "${ctxPath}/company/admin/shops/saveShops";
			data = f_company_shops_listShops_import_get_datas();
			$.post(url, data, function(data) {
				if (data.r1 == '200') {
					tipsinfo("操作成功!");
					$("#company_shops_listShops_import_grid_table_01").datagrid('load');
					$('#company_shops_listShops_import_tree_01').tree("reload");
				} else if (data.r1 == '600') {
					tipsinfo("非法访问！");
				} else if (data.d1) {
					tipsinfo("操作失败：" + data.d1);
				} else {
					tipsinfo("网络不给力，请稍后再试！");
				}
			}, 'json');
		};

		$('#import_input_listShops_teamId').combotree({
		    url: '${ctxPath }/company/admin/listTeamInfoTree',
		    multiple: false
		});
		

		// 点击文件上传按钮
		$(".file_upload_btn").on("click", function(){
			var pid = $(this).data("pid");
			if( $("#"+pid).val() == ''){
				tipsinfo("请选择需要导入的xls文件！");
				return false;
			}
			var file = document.getElementById(pid).files[0];
            if (file) {
    	        var size = file.size;
    	        $("#progress_size_up_"+pid).html("0/" + size);
    	        
    	      	//创建FormData对象，初始化为form表单中的数据。需要添加其他数据可使用formData.append("property", "value");  
    	        var formData = new FormData();  
    	        formData.append("file", document.getElementById(pid).files[0]);
    	        //ajax异步上传  
    	        $.ajax({  
    	            url: "${ctxPath }/company/admin/shops/import/check.do",  
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
		});
		
		// 选择上传文件操作，显示图片信息
		selectFileToShow = function(pid){
            
            $("#file_"+pid).val("");
	        $("#progress_size_up_"+pid).html(""); 
            $('#progress_percent_'+pid).html("0%");
	        $("#show_size_"+pid).html("");
			$("#show_"+pid).html('');
            $('#progress_'+pid).attr({value : 0});
	        
			var fileName = $("#"+pid).val();
			var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
			if (file_typename == '.xls') {
				//这里限定上传文件文件类型
				if(typeof FileReader == "undified") {
		            return;
		        }
				var resultFile = document.getElementById(pid).files[0];
	            if (resultFile) {
	                var reader = new FileReader();
	                reader.readAsDataURL(resultFile);
	                reader.onload = function (e) {
	                    var urlData = this.result;
	                    var size = 0;
	        	        if (resultFile.size > 1024 * 1024){
	    	            	size = (Math.round(resultFile.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
	    	           	} else{
	    	           		size = (Math.round(resultFile.size * 100 / 1024) / 100).toString() + 'KB';
	    	            }
	        	        $("#show_size_"+pid).html("文件大小：" + size+"<br/>");
	                }; 
	            }
			} else {
				alert("对不起，文件格式不符.,请重新选择要上传的文件(.xls格式）)");
				$("#"+pid).val('');
				return;
			}
		} // end of function selectFileToShow()
		
		//上传按钮点击
		$("#order_cash_huidan_send_up").on('click', function(){
			$("#teamFile04").click().change(function(){
				selectFileToShow("teamFile04");
			});
		});
	});
</script>