<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.*, java.text.SimpleDateFormat,java.util.Calendar,org.r2funny.yodalite.*"/> 
<%
	String  strMac 		= request.getParameter("mac");
 	
	if (null != strMac) {
		List<UploadData>  history = GlobalData.mDevUploadMap.get(strMac);
		
		out.println("<rep><code>0</code>");
		long  lPrvTime = 0;
		long  lStartTime = 0;
		int	  nOffCount = 0;
		int   nOffCount10 = 0;	// 超时10秒的次数
		int	  nOffCount30 = 0;  // 超时30秒的姿数
		int	  nOffCount60 = 0;  // 超时60秒的姿数
		long  lOffTotalTime = 0;
		if (null != history){
			// 日期格式化
			SimpleDateFormat sdfA =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
			
			for (int i = 0; i < history.size(); i++){
				
				if (0 == i){
					lPrvTime = history.get(i).getUploadTime();
					lStartTime = history.get(i).getUploadTime();
				}
				long  lTimeOff = history.get(i).getUploadTime() - lPrvTime;
				 
				if (lTimeOff > 30 * 1000){
					nOffCount30++;
					lOffTotalTime += lTimeOff;
					if (lTimeOff > 60 * 1000){
						nOffCount60++;
					}
					
					out.println("<uptm>" + sdfA.format(history.get(i).getUploadTime()) + " miss </uptm>");
				}  
				else {
					out.println("<uptm>" + sdfA.format(history.get(i).getUploadTime()) + "</uptm>");
				} 
				
				lPrvTime = history.get(i).getUploadTime();
			}
		}
		  
		out.println("<total>工作时长：" + (lPrvTime - lStartTime) / 1000 / 60 + "分钟,信号丢失累计: 9~12秒" + (nOffCount - nOffCount10) + "次，12~30秒"+ nOffCount10+ "次,30秒~1分钟" + (nOffCount30 - nOffCount60)+"次, 1分以上" + nOffCount60 +"次</total>");
		out.println("<offtm>离线时间:" + (lOffTotalTime) / 1000 / 60 + "分钟</offtm>"); 
		out.println("</rep>");
	}
	else {
		out.println("<rep><code>1</code><desc>参数不足！缺少: mac。 </desc></rep>");
	}

%>