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
<title>发送直控指令/Post ctrl cmd</title>
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
						<h2>设备控制参数/Command param</h2>
					</td>
				</tr>  

				<tr >
					<td bgcolor="#efefef" >设备序列号/Mac</td>
					<td bgcolor="#efefef" align="center"><input type="text" name="mac" style="border:1px solid #8fcf8f;height:25px;width:300px;" value="<%=strMac %>"/>	</td> 
				</tr>
				 
				<tr  >
					<td bgcolor="#efefef" >自定义控制指令/Cmd</td> 
					<td bgcolor="#efefef" align="center"><input type="text" name="cmd" style="border:1px solid #8fcf8f;height:25px;width:300px;" value="<%=strCmd%>"/>	</td>  
				</tr> 
				<tr bgcolor="#99bbff">
					<td>常见指令</td>
					<td align="center"> 
						<select name="sel_cmd" onchange="get_cmd();" style="width:300px;border:1px solid #8fcf8f;height:25px;"> 
							<option style="width:200px" value="lockopen">open the door(开锁)</option>
							<option style="width:200px" value="lighton">turn on the light(开灯)</option>   
							<option style="width:200px" value="lightoff">turn off the light(关灯)</option>  
							<option style="width:200px" value="switchon">turn on the switch(开关开)</option>  
							<option style="width:200px" value="switchoff">turn on the switch(开关关)</option>   
							<option style="width:200px" value="wateron">打开浇花器(turn on the Smartwatering)</option>  
							<option style="width:200px" value="wateroff">关闭浇花器(turn off the Smartwatering)</option>
							<option style="width:200px" value="5">开空调(海信)</option>  
							<option style="width:200px" value="6">关空调(海信)</option> 
							<option style="width:200px" value="5">开空调(格力)</option>  
							<option style="width:200px" value="6">关空调(格力)</option> 
							<option style="width:200px" value="5">开空调(美的)</option>  
							<option style="width:200px" value="6">关空调(美的)</option> 
						</select>
					</td> 
				</tr>
				<tr  >
					<td bgcolor="#efefef" align="center" colspan="2"> 
						<!-- 控制token直接填这里是方便测试确认功能，如果实际使用请保存好token -->
						<input type="hidden" name="tk" value="<%=GlobalData.SVR_CUR_TOKEN%>" />
						<input type="submit" value="提交(Submit)"/>  
						<input type="hidden" name="normalcmd" value="2" />
					</td>
				</tr>
			</table>
		</center>
	</form>
 </body>
</html>