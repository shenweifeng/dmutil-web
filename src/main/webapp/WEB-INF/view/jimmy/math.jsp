
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />

<style>
<!--
#jimmy_math_layout_01_right_bottom>ul>li>span 
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
	width: 50px;
	text-align: center;
}
-->
</style>


<div id="jimmy_math_layout_01_right_bottom">
	
	<div id="jimmy_math_div_02">
		（<span class="input_100" id="jimmy_math_v2"></span>）+
		（<input type="number" class="input_100" id="jimmy_math_v3" 
		style="border: 0px; color: blue;"/>）=
		（<span class="input_100" id="jimmy_math_v1" style="color: red;"></span>）
		<span id="jimmy_math_b1" 
		style="display: inline-block; width: 50px; text-align:center; border: 1px solid black; background-color: red; color: white;">确认</span>
		<br/><span id="jimmy_math_tips_01" style="color: red; width: 400px;text-align: left; display: inline-block;"></span>
	</div>
	<div id="jimmy_math_div_021"></div>
	
</div>

<script>
	$(function() {
		
		var success = 0;
		var failure = 0;
		
		$('#jimmy_math_div_02').panel({
		    height: 80,
		    iconCls: 'icon-sum',
		    title:'数学练习'
		});
		
		//生成从minNum到maxNum的随机数
		function randomNum(minNum, maxNum) {
		  switch (arguments.length) {
		    case 1:
		      return parseInt(Math.random() * minNum + 1, 10);
		      break;
		    case 2:
		      return parseInt(Math.random() * ( maxNum - minNum + 1 ) + minNum, 10);
		      //或者 Math.floor(Math.random()*( maxNum - minNum + 1 ) + minNum );
		      break;
		    default:
		      return 0;
		      break;
		  }
		};
		doSuccessRate = function(){
			if (success == 0){
		        return 0;
		    }
			if(failure == 0){
				return 100;
			}
		    return (Math.round(success / (success+failure) * 10000) / 100.00);// 小数点后两位百分比
		}
		doSuccess = function(){
			++success;
			$("#jimmy_math_tips_01").html('YES：正确【'+success+'】次，错误【'+failure+'】次，正确率【'+doSuccessRate()+'%】');
			$("#jimmy_math_v3").val('');
			$("#jimmy_math_v3").focus();
			
		};
		doFailure = function(){
			++failure;
			$("#jimmy_math_tips_01").html('NO：正确【'+success+'】次，错误【'+failure+'】次，正确率【'+doSuccessRate()+'%】');
			$("#jimmy_math_v3").val('');
			$("#jimmy_math_v3").focus();		
		};
		
		
		// 初始化
		resetFun = function(){
			var v1 = randomNum(1,10);
			var v2 = randomNum(0,v1);
			$("#jimmy_math_v1").html(v1);
			$("#jimmy_math_v2").html(v2);
			$("#jimmy_math_v3").val('');
			$("#jimmy_math_v3").focus();
		}
		
		resetFun();
		
		$("#jimmy_math_b1").bind('click',function(){
			var v3 = $("#jimmy_math_v3").val();
			if(v3==""){
				$("#jimmy_math_tips_01").html('No!');
				$("#jimmy_math_v3").val('');
				$("#jimmy_math_v3").focus();
				return;
			}
			var v1 = $("#jimmy_math_v1").html();
			var v2 = $("#jimmy_math_v2").html();
			var sum = parseInt(v1)-parseInt(v2);
			v3 = parseInt(v3);
			if(sum == v3){
				doSuccess();

				var vh = $("#jimmy_math_div_021").html();
				vh = v2 + " + " + v3 + " = " + v1 + "<br/>" + vh;
				$("#jimmy_math_div_021").html(vh);
				
				resetFun();
			}else {
				doFailure();

				var vh = $("#jimmy_math_div_021").html();
				vh = "<span style='color: red;'>" + v2 + " + " + v3 + " = " + v1 + "</span><br/>" + vh;
				$("#jimmy_math_div_021").html(vh);	
			}
		});
		
		

	});
</script>