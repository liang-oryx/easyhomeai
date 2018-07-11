<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<jsp:directive.page import="java.util.*, org.r2funny.yodalite.GlobalData"/>
<%
	String  srcIP = request.getRemoteAddr(); 
	
	// check request IP, 只允许本地url访问， 
	// 可以自改更改, 规则
	if ("127.0.0.1".equals(srcIP)){
		long  lCurTime = System.currentTimeMillis() / 1000;  
		
		// 返回token , 有效期2小时
		// 两个小时内不变
		if (lCurTime - GlobalData.TOKEN_MAKE_TIME < 60 * 2 * 60){
			out.println(GlobalData.SVR_CUR_TOKEN);
		}
		else {
			
			int   nR = (int)lCurTime % 100000; 
			java.util.Random r=new java.util.Random(nR); 
			nR = r.nextInt();
			nR = (0 > nR) ? -nR : nR;
			
			String  strToken = "R2TK_" + nR + "_" + lCurTime % 1000000;
			out.println(strToken);
			
			// 数据保存到全局变量中, save to global var.
			GlobalData.TOKEN_MAKE_TIME = lCurTime;
			GlobalData.SVR_CUR_TOKEN = strToken;
		}
	}
	else {
		out.println("error:100(请使用本地IP访问)");
	} 
%>