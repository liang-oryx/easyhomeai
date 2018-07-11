<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!doctype html>
<jsp:directive.page import="org.r2funny.yodalite.GlobalData"/>
<html lang="en">
 <%
	String   strMac 	= request.getParameter("mac");
	String   strToken 	= request.getParameter("tk"); 
	String   strCmd		= request.getParameter("cmd");
	
	if (null == strMac){
		strMac = "";
	}
	
	if (null == strToken){
		strToken = "";
	}
	
	if (null == strCmd){
		strCmd = "";
	} 
	 
 %> 
<head> 
<title>配置网关成员/Config gate member</title>
<html class="no-js">

<meta charset="utf-8">  
</head>
<script Language="JavaScript">
  function set_cmd(id){
	var sel= document.getElementsByName(id)[0];
	var selvalue = sel.value;
	
	document.cmdform.cmd.value = selvalue;
	document.cmdform.submit();
  } 
  
</script>
 <body> 
	<form id="cmdform" name="cmdform" method="post" action="udpctrl.jsp" onSubmit="">
		<center>
			<table border="0" cellpadding="3" cellspacing="1" bordercolor="#cfcfcf" bgcolor="#cfcfcf" width="60%">

				<tr bgcolor="#ffffff">
					<td align="center" colspan="2">
						<h2>网关成员/gate members</h2>
					</td>
				</tr>  

				<tr >
					<td bgcolor="#efefef" >设备序列号/Mac</td>
					<td bgcolor="#efefef" align="center"><input type="text" name="mac" style="border:1px solid #8fcf8f;height:25px;width:300px;" value="<%=strMac %>"/>	</td> 
				</tr>
				 
				<tr  >
					<td bgcolor="#efefef" >成员1/Member1</td> 
					<td bgcolor="#efefef" align="center"><input type="text" name="member1" style="border:1px solid #8fcf8f;height:25px;width:300px;" value=""/>	</td>  
				</tr> 
				
				<tr  >
					<td bgcolor="#efefef" >成员2/Member2</td> 
					<td bgcolor="#efefef" align="center"><input type="text" name="member2" style="border:1px solid #8fcf8f;height:25px;width:300px;" value=""/>	</td>  
				</tr> 
				
				<tr  >
					<td bgcolor="#efefef" >成员3/Member3</td> 
					<td bgcolor="#efefef" align="center"><input type="text" name="member3" style="border:1px solid #8fcf8f;height:25px;width:300px;" value=""/>	</td>  
				</tr> 
				
				<tr  >
					<td bgcolor="#efefef" >成员4/Member4</td> 
					<td bgcolor="#efefef" align="center"><input type="text" name="member4" style="border:1px solid #8fcf8f;height:25px;width:300px;" value=""/>	</td>  
				</tr> 
				
				<tr  >
					<td bgcolor="#efefef" align="center" colspan="2"> 
						<!-- 控制token直接填这里是方便测试确认功能，如果实际使用请保存好token -->
						<input type="hidden" name="tk" value="<%=GlobalData.SVR_CUR_TOKEN%>" />
						<input type="submit" value="提交(Submit)"/>  
						<input type="hidden" name="setmember" value="yes" />
					</td>
				</tr>
			</table>
		</center>
	</form>
 </body>
</html>