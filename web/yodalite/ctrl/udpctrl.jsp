<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.*, java.net.DatagramPacket, java.net.DatagramSocket, java.net.InetSocketAddress, org.r2funny.yodalite.*"/> 
<%
	 
	// 处理使用udp方式发送 nio指令
	String  strToken		= request.getParameter("tk"); 
	String	strMac			= request.getParameter("mac");
	String	strCmd			= request.getParameter("cmd");
	String  strIsSetMember	= request.getParameter("setmember");
	
	if (null == strToken){
		strToken = "";
	}
	
	// 处理配置成员的指令
	if ((null != strIsSetMember)
		&& ("yes".equalsIgnoreCase(strIsSetMember) ) ){
		int  nRC = 0;
		strCmd = "";
		
		String  strMember1 = request.getParameter("member1");
		if ((null != strMember1)
			&& (0 < strMember1.length()) ){
			strCmd += "&m00=" + strMember1;
			nRC++;
		}
		
		String  strMember2 = request.getParameter("member2");
		if ((null != strMember1)
			&& (0 < strMember1.length()) ){
			strCmd += "&m01=" + strMember1;
			nRC++;
		}
		
		String  strMember3 = request.getParameter("member3");
		if ((null != strMember1)
			&& (0 < strMember1.length()) ){
			strCmd += "&m02=" + strMember1;
			nRC++;
		}
		
		String  strMember4 = request.getParameter("member4");
		if ((null != strMember1)
			&& (0 < strMember1.length()) ){
			strCmd += "&m03=" + strMember1;
			nRC++;
		}
		
		strCmd = "&rc=0" + nRC + strCmd; 
	}
	
	if ((null != strMac)
		&& (0 < strMac.length())
		&& (null != strToken)
		&& (0 < strToken.length())
		&& (null != strCmd)
		&& (0 < strCmd.length())){		
		try {
			// token 不对不能发
			if (GlobalData.SVR_CUR_TOKEN.equalsIgnoreCase(strToken) ){
				
				// 确认设备是否在线
				R2Dev  	dev = GlobalData.mDevMap.get(strMac);
				long  	lCurTime = System.currentTimeMillis() / 1000; 
				
				if ((null != dev)
					&& (lCurTime - dev.getUploadTime() < 60) ){
					// 发送代码... 
					DatagramSocket 	ds = new DatagramSocket(); 
					
					String  strUdpCmd = strMac + "R2" + strCmd;
					
					// 注：应保证8889端口在防火墙内
					DatagramPacket dp = new DatagramPacket(strUdpCmd.getBytes(),   
						strUdpCmd.getBytes().length, new InetSocketAddress("127.0.0.1", 8889));  
				
					ds.send(dp);
					 
					out.println("ok:0");
				}
				else {
					out.println("error:102(device off line)");
				}
				
			}  
		}
		catch (Exception e){
			out.println("error:102" + e.toString());
		} 
	}
	else{
		out.println("error:101");
	}	 
%>
