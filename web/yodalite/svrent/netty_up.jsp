<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<jsp:directive.page import="java.util.*, org.r2funny.yodalite.*"/>
<%
	// 说明：高负载系统不应使用该方法，可以用内存数据库表或其它方式效果更高
	// 当前代码可用于家用的小型自建平台， 建议服务硬件设备数量不超过 3000台
	try {
		String  srcIP = request.getRemoteAddr(); 
		
		// check request IP, 只允许本地url访问， 
		// 可以自改更改, 规则
		if ("127.0.0.1".equals(srcIP)){ 
			long  lCurTime = System.currentTimeMillis() / 1000;  
			
			String  srcMac = request.getParameter("mac");
			String  strTK  = request.getParameter("tk");
			String  strUpData = request.getParameter("ud");
			
			if ((null != srcMac)
				&& (null != strUpData) ){
				R2Dev  dev = GlobalData.mDevMap.get(srcMac);	
				
				// 自动注册一个设备
				if (null == dev){ 
					dev = new R2Dev();
					dev.setId(GlobalData.mDevMap.size()); 
					dev.setName("unreg device");
					
					dev.setMac(srcMac);
					dev.setRegTime(System.currentTimeMillis());
					dev.setToken("");
					dev.setDevType(41);
					dev.setDevPost("");
					dev.setDevAddr("Your home");
					GlobalData.mDevMap.put(dev.getMac(), dev);
				}
				
				List<UploadData>	uploadDatas = GlobalData.mDevUploadMap.get(srcMac);
				
				if (null == uploadDatas){
					uploadDatas = new ArrayList<UploadData>();
					GlobalData.mDevUploadMap.put(srcMac, uploadDatas);
				}
				else {
					// 如果个数大于2000个，清除原有数据
					if (2000 < uploadDatas.size()){
						uploadDatas = new ArrayList<UploadData>();
						GlobalData.mDevUploadMap.put(srcMac, uploadDatas);
					}
				}
				
				UploadData  upData = new UploadData();
				
				upData.setUploadTime(lCurTime * 1000);
				upData.setUploadData(strUpData);
				
				uploadDatas.add(upData);

				dev.setUploadTime(lCurTime * 1000);
				dev.setUploadData(strUpData);
				
				out.println("ok:0");
			}
			else {
				out.println("error:101");
			}
		}
		else {
			out.println("error:100");
		} 
	}catch (Exception e){
		out.println("error:200" + e.toString());
	}
%>