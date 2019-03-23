<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml;charset=UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>jvm</title>
</head>
<body>
<p style="font-size: 12px;">
<%
Runtime lRuntime = Runtime.getRuntime();
out.println("*** BEGIN MEMORY STATISTICS ***<br/>");
out.println("JVM MAX MEMORY : " + Runtime.getRuntime().maxMemory()/1024/1024+"M<br>"); 
out.println("JVM IS USING MEMORY : " + Runtime.getRuntime().totalMemory()/1024/1024+"M<br>"); 
out.println("JVM IS FREE MEMORY : " + Runtime.getRuntime().freeMemory()/1024/1024+"M<br>"); 
out.println("Available Processors : "+lRuntime.availableProcessors()+"<br/>");
out.println("*** END MEMORY STATISTICS ***<br/><br/>");

Enumeration<?> enu = System.getProperties().keys();
while(enu.hasMoreElements()){
	String k = enu.nextElement().toString();
	String v = System.getProperty(k);
	out.println(k+" : "+v+"<br/>");
}
out.println("<br/><br/>");
Map<?,?> env = System.getenv();
Iterator<?> i = env.entrySet().iterator();
while (i.hasNext()) {
        Map.Entry<?,?> entry = (Map.Entry<?,?>) i.next();
        String k=entry.getKey().toString();
        String v = entry.getValue().toString();
        out.println(k+" : "+v+"<br/>");
}

%>
</p>
</body>
</html>