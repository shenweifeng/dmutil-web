
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"
	scope="request" />

<script type="text/javascript">  
  var socket;   
  var reconnectCount = 0;
  //实际生产中，id可以从session里面拿用户id
  var id  = Math.random().toString(36).substr(2);
  if(!window.WebSocket){  
      window.WebSocket = window.MozWebSocket;  
  }  
   
  if(window.WebSocket){  
	  openSocket();
  }else{  
        alert("WebSocket is not support");  
  }  
  
  // 开始建立socket链接，2019年3月28日22:08:28
  function openSocket(){
	  socket = new WebSocket("ws://localhost:7397");  
      socket.onmessage = function(event){             
            appendln("receive:" + event.data);  
      };  
      socket.onopen = function(event){  
            appendln("WebSocket is opened");  
            login(); 
      };  
      socket.onclose = function(event){  
    	  	reconnectCount = reconnectCount+1;
            appendln("WebSocket is closed, program will reconnect after 10 senconds.");  
            window.setTimeout(openSocket, 10000);
      };  
  }

    
  function appendln(text) {  
    var ta = document.getElementById('responseText');  
    ta.value += text + "\r\n";  
  }  
    
  function login(){
      console.log("["+reconnectCount+"]aaaaaa");
      var date={"id":id,"type":"aa"+reconnectCount};
      var login = JSON.stringify(date);
      socket.send(login);
  }  

	$(function() {
		// 发送到服务器
		$("#btn01").on('click', function(){
		      console.log($("#message").val());
			if(socket){
			      var date={"id":id, "message": $("#message").val() };
			      var login = JSON.stringify(date);
				socket.send(login);
			}
		});
		
		// 服务器推送到客户端
		$("#btn02").on('click', function(){
			$("#responseText2").load("${ctxPath}/util/ws/push");
		});
	});
  </script>

<form onSubmit="return false;">
	<input type="text" name="message" id="message" value="hello" /> 
	<button id="btn01">发送</button>
	<button id="btn02">服务器推送</button>
	<br /> <br />

	<textarea id="responseText" style="width: 800px; height: 300px;"></textarea>
	
	<br /> <br />
	<textarea id="responseText2" style="width: 800px; height: 100px;"></textarea>
	
</form>

