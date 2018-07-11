<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<jsp:directive.page import="java.util.*, org.r2funny.yodalite.GlobalData, org.r2funny.yodalite.R2Dev"/>
<%
	// 生成本地设备列表数据库
	// 用于生产测试的数据
	String  srcIP = request.getRemoteAddr(); 
	String  strOpenType = request.getParameter("opertype"); 
	String	strDevType = request.getParameter("devicetype"); 
		
	if ((null != strOpenType)
		&& ("add".equals(strOpenType)) ){ 
		//	允许远程加
		// to do:可以修改
	 
		R2Dev	dev = new R2Dev();
		
		dev.setId(GlobalData.mDevMap.size());
		
		String  strShowName = request.getParameter("showname");
		if ( strShowName != null && strShowName.length()>=0 )
		{
			strShowName = new String(strShowName.getBytes("iso8859_1"),"utf-8");
		}
		dev.setName(strShowName);
		
		dev.setMac(request.getParameter("devmac"));
		dev.setRegTime(System.currentTimeMillis());
		dev.setToken("");
		dev.setDevType(Integer.valueOf(strDevType));
		dev.setDevPost(request.getParameter("devpost"));
		dev.setDevAddr("Your home");
		GlobalData.mDevMap.put(dev.getMac(), dev);
		
		out.println("Add device succeed!");
	}
	else {
		// check request IP, 只允许本地url访问， 
		// 可以自改更改, 规则
		if ("127.0.0.1".equals(srcIP)){
			// 生成默认数据
			R2Dev	dev = new R2Dev();
			
			dev.setId(0);
			dev.setName("First your r2dev");
			dev.setMac("d296850a8b77");
			dev.setRegTime(System.currentTimeMillis());
			dev.setToken("1f4fc2e4fa343f4f2161d16a3d605ad2");
			dev.setDevType(41);
			dev.setDevPost("23.1241,113.116");
			dev.setDevAddr("Your home");
			GlobalData.mDevMap.put(dev.getMac(), dev);
			
			out.println("Make dev db ok!");
		}
		else {
			out.println("error:100(请使用本地IP访问)");
		} 
	}
%>