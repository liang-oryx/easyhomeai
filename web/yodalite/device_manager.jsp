<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="java.util.*,java.util.concurrent.ConcurrentHashMap, java.text.SimpleDateFormat, org.r2funny.yodalite.GlobalData, org.r2funny.yodalite.R2Dev"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备列表/Device list</title>

<script Language="JavaScript">

</script>
</head>
<body>
<% 
 
	// 日期格式化
	SimpleDateFormat sdfA =   new SimpleDateFormat( "yyyy-MM-dd HH:mm"); 
	try {
 
	 
%>
	<center><h2>设备列表/Device list</h2></center>

	<form id="devform" name="devform" method="post" action="" onSubmit="">
		<center>
			<table border="0" cellpadding="3" cellspacing="1" bordercolor="#ffffff" bgcolor="#ffffff" width="95%">
				<tr>
					<td align="right" colspan="11">
						<a href="device_add.jsp">新增设备/New device</a><p/>
						&nbsp;
						<a href="dev_online_state.jsp" >在线设备/Online device</a>&nbsp; | &nbsp; 
					</td>
				</tr>
				<tr bgcolor="#d0d0d0">
					<td align="center" colspan="11"> 设备信息列表/Device Info list</td>
				</tr>	
				
				<tr bgcolor="#e0e0e0">
					<td>&nbsp;&nbsp;</td>
					<td align="center">编号/ID</td>
					<td align="center">设备/Mac</td>
					<td align="center">名称/Name</td>
					<td align="center">Token</td> 
					<td align="center">注册时间/Regtime</td> 
					<td align="center">类型/Dev Type</td>
					<td align="center">位置/Dev Post</td> 
					<td align="center">控制/Control</td>
				</tr>
		
<%
		 
		int  i = 0;
		for(Map.Entry<String, R2Dev> entry: GlobalData.mDevMap.entrySet())
        {
			int		nDid 		= entry.getValue().getId();
			String  strMac 		= entry.getValue().getMac();
			String  strToken 	= entry.getValue().getToken(); 
			String  strShowName = entry.getValue().getName(); 
			long	lTime 		= entry.getValue().getRegTime();  
			int  	nDevType 	= entry.getValue().getDevType();
			String  strDevPost 	= entry.getValue().getDevPost(); 
			
			i++;

			 
%>				
				<tr bgcolor="#f3f3f3">
					<td><%=i %></td>
					<td align="center" ><%=nDid %></td>
					<td align="center" ><a href="dev_upload_history.jsp?mac=<%=strMac %>" ><%=strMac %></a></td>
					<td align="center" ><%=strShowName %></td>
					<td align="center" ><%=strToken %></td>
					<td align="center" ><%=sdfA.format(lTime) %></td>
					<td align="center" ><%=nDevType %></td>
					<td align="center" ><%=strDevPost %></td> 
					<td align="center" ><a href="./ctrl/post_dev_cmd.jsp?mac=<%=strMac %>" >控制(Post Cmd)</a> &nbsp; 
<%
			if ((20 == nDevType) || (40 == nDevType)){
				out.println("|&nbsp; <a href='./ctrl/set_dev_member.jsp?mac=" + strMac + "' >设置成员(Set member)</a>");
			}
%>					
					</td>
				</tr>
<%
		} 
	}
	catch (Exception e){
		out.println("<tr><td colspan='7'>访问数据内容失败，请报告管理员。" + e.toString() + "</td></tr>");
	}	

%>
			</table>						
		</center>
	</form>
</body>
</html>