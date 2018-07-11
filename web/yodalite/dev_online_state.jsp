<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.*,  java.text.SimpleDateFormat,java.util.Calendar, org.r2funny.yodalite.GlobalData, org.r2funny.yodalite.R2Dev"/> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看设备在线状态/Online state</title> 
<meta http-equiv="refresh" content="20;url=dev_online_sate.jsp" >
</head>
<script language="JavaScript">
function myrefresh(){
   window.location.reload();
}
setTimeout('myrefresh()',16000); //指定1秒刷新一次
</script>
<body>

<center><h2>设备在线列表/Online device</h2></center>

	<form id="dataform" name="dataform" method="post" action="" onSubmit="">
		<center>
			<table border="0" cellpadding="3" cellspacing="1" bordercolor="#ffffff" bgcolor="#ffffff" width="95%">
				
				
				<tr bgcolor="#d0d0d0">
					<td align="center" colspan="12"> NIO设备状态列表 </td>
				</tr>	
				
				<tr bgcolor="#e0e0e0">
					<td align="center"></td>
					<td align="center">设备Mac</td>
					<td align="center">设备名称</td> 
					<td align="center">最后上传时间</td>
					<td align="center">在线状态</td>
					<td align="center">传感器数据</td>  
				</tr>
		
<%
	try {
		
		 
		// 日期格式化
		SimpleDateFormat 	sdfA =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		long  				lCurTime = System.currentTimeMillis(); 
		 
		int  n = 0;
		for(Map.Entry<String, R2Dev> entry: GlobalData.mDevMap.entrySet())
		{
			int		nDid 		= entry.getValue().getId();
			String  strMac 		= entry.getValue().getMac();
			//String  strToken 	= entry.getValue().getToken(); 
			//int  	nDevType 	= entry.getValue().getDevType();
			String  strShowName = entry.getValue().getName(); 
			long	lUpTime 		= entry.getValue().getUploadTime();  
			String  strDevPost 	= entry.getValue().getDevPost(); 
			String	srtUploadData = entry.getValue().getUploadData(); 
			
			// 把16进制字符变成字符串
			try { 
				StringBuffer strData = new StringBuffer();
				for (int i = 0; i < srtUploadData.length() / 2; i++) {  
					strData.append( (char) Integer.valueOf(srtUploadData.substring(i * 2, i * 2 + 2), 16).byteValue());  
				}  
				srtUploadData = strData.toString();  
			}
			catch (Exception e){
				srtUploadData += "(Not hex)";
			}
			
			n++;

%>
				<tr bgcolor="#f3f3f3">
					<td align="center"><%=n++ %></td>
					<td align="center"><%=strMac %></td>
					<td align="center"><%=strShowName %></td> 
					<td align="center"><%=(0 == lUpTime) ? "未在线" : sdfA.format(lUpTime) %></td>
					<td align="center"><%=((lCurTime - lUpTime < 1000 * 60 * 1) ? "online" : "offline") %> </td> 
					<td align="center" ><%=srtUploadData %></td> 
					 
				</tr>
<%
		}
 
	 
	}
	catch (Exception e){
		out.println("访问数据异常! error " + e.toString());
	}
%>
			</table>						
		</center>
	</form>
</body>
</html>