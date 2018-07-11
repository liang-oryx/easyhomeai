<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>新增设备/New device</title>
 </head>
<script Language="JavaScript">
  function get_mototype(){
	var sel= document.getElementsByName("sel_mototype")[0];
	var selvalue = sel.options[sel.options.selectedIndex].value;
	
	document.accountform.mototype.value = selvalue;
  }
  
  function get_devtype(){
	var sel= document.getElementsByName("sel_devtype")[0];
	var selvalue = sel.options[sel.options.selectedIndex].value;
	
	document.accountform.devicetype.value = selvalue;
  }
  
  function get_motostep(){
	var sel= document.getElementsByName("sel_motostep")[0];
	var selvalue = sel.options[sel.options.selectedIndex].value;
	
	document.accountform.motostep.value = selvalue;
  }
  
</script>
 <body> 
	<form id="accountform" name="accountform" method="post" action="make_dev_db.jsp" onSubmit="">
		<center>
			<table border="0" cellpadding="3" cellspacing="1" bordercolor="#ffffff" bgcolor="#ffffff" width="60%">

				<tr>
					<td align="center" colspan="2">
						<h2>设备参数/Device Info</h2>
					</td>
				</tr>

				<tr  >
					<td bgcolor="#d0d0d0">设备序列号/Mac</td>
					<td bgcolor="#f3f3f3" align="center"><input type="text" name="devmac" style="border:1px solid #8fcf8f;height:25px;width:300px;" />	</td> 
				</tr>
				<tr  >
					<td bgcolor="#d0d0d0" >设备显示名称/Name</td>
					<td bgcolor="#f3f3f3" align="center"><input type="text" name="showname" style="border:1px solid #8fcf8f;height:25px;width:300px;" />	</td> 
				</tr>
				 
				<tr >
					<td bgcolor="#d0d0d0" >密码/Password</td>
					<td bgcolor="#f3f3f3" align="center"><input type="text" name="password" style="border:1px solid #8fcf8f;height:25px;width:300px;" />	</td> 
				</tr> 
				 
				 
				<tr >
					<td bgcolor="#d0d0d0" >设备类型/DevType</td>
					<td bgcolor="#f3f3f3" align="center"> 
						<select name="sel_devtype" onchange="get_devtype();" style="width:100px;border:1px solid #8fcf8f;height:25px;"> 
							<option style="width:200px" value="0">--请选择--</option>
							<option style="width:200px" value="1">user</option>   
							<option style="width:200px" value="2" selected >dev</option>  
							<option style="width:200px" value="3">3</option>  
							<option style="width:200px" value="4">4</option>  
							<option style="width:200px" value="5">5</option>  
							<option style="width:200px" value="6">6</option>  
							<option style="width:200px" value="7">7</option>  
							<option style="width:200px" value="8">8</option>  
							<option style="width:200px" value="9">9</option>  
							<option style="width:200px" value="10">10</option>  
							<option style="width:200px" value="11">11</option>  
							<option style="width:200px" value="12">12</option> 
							<option style="width:200px" value="13">13</option>  
							<option style="width:200px" value="14">14</option>  
							<option style="width:200px" value="15">15</option>  
							<option style="width:200px" value="16">16</option>  
							<option style="width:200px" value="17">17</option>  
							<option style="width:200px" value="18">18</option>  
							<option style="width:200px" value="19">19</option>  
							<option style="width:200px" value="20">20</option>  
							<option style="width:200px" value="21">21</option>  
							<option style="width:200px" value="22">22</option>  
							<option style="width:200px" value="23">23</option>  
							<option style="width:200px" value="24">24</option>  
							<option style="width:200px" value="25">25</option>   
							<option style="width:200px" value="26">26</option>  
							<option style="width:200px" value="27">27</option>  
							<option style="width:200px" value="28">28</option>  
							<option style="width:200px" value="29">29</option>  
							<option style="width:200px" value="30">30</option>  
							<option style="width:200px" value="31">31</option>  
							<option style="width:200px" value="32">32</option>  
							<option style="width:200px" value="33">33</option>  
							<option style="width:200px" value="34">34</option>  
							<option style="width:200px" value="35">35</option>  
							<option style="width:200px" value="36">36</option> 
							<option style="width:200px" value="37">37</option> 
							<option style="width:200px" value="38">38</option> 
							<option style="width:200px" value="39">39</option> 
							<option style="width:200px" value="40">40</option> 
						</select>
					</td> 
				</tr>
				<tr >
					<td bgcolor="#d0d0d0">设备位置/DevPost</td>
					<td bgcolor="#f3f3f3" align="center"><input type="text" name="devpost" style="border:1px solid #8fcf8f;height:25px;width:300px;" />	</td>  
				</tr>
				 
				<tr bgcolor="#d0d0d0">
					<td align="center" colspan="2">
						<input type="submit" value="提交(Submit)"/> 
						<input type="hidden" name="opertype" value="add" /> 
						<input type="hidden" name="devicetype" value="0" />
					</td>
				</tr>
			</table>
		</center>
	</form>
 </body>
</html>