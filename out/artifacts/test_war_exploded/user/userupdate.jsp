<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>等级考试报名系统</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">
<script language="javascript" type="text/javascript">

function checkform()
{
	 
	

	if (document.getElementById('usernameid').value=="")
	{
		alert("用户名不能为空");
		return false;
	}
	var valid=/^\w+$/;
	if(!valid.test(document.getElementById('usernameid').value)){
		alert("用户名必须是数字、字母或下划线");
		return false;
	}
	
	if (document.getElementById('truenameid').value=="")
	{
		alert("姓名不能为空");
		return false;
	}
	
	if (document.getElementById('truenameid').value.length>4)
	{
		alert("姓名长度必须小于4位");
		return false;
	}
	if (document.getElementById('dianhuaid').value=="")
	{
		alert("手机不能为空");
		return false;
	}
	
	valid=/^0?1[3,4,5,8][0,1,2,3,4,5,6,7,8,9]\d{8}$/;  
	if(!valid.test(document.getElementById('dianhuaid').value)){
		alert("请输入正确的手机号   如136-6666-4444");
		return false;
	}
	
	if (document.getElementById('sfzid').value=="")
	{
		alert("身份证不能为空");
		return false;
	}
	
	var reg=/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
	
	
	if(!reg.test(document.getElementById('sfzid').value)){
		alert("请输入正确的身份证格式");
		return false;
	}
	
	
	return true;
	
}


</script>
</head>
<body leftmargin="8" topmargin="8" background='skin/images/allbg.gif'>


<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D1DDAA" align="center">
<tr>
 <td height="26" background="skin/images/newlinebg3.gif" align="center">
  	<span style="font-weight: bold;">${title }</span>
</td>
</tr>
</table>
  

<form action="${url }" method="post" onsubmit="return checkform()">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">







<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	姓名：
	</td>
	<td width="70%">
	<input  type="text" name="truename"  id='truenameid'  size="30" value="${bean.truename }"  />

	</td>
	
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	手机：
	</td>
	<td width="70%">
	<input  type="text" name="dianhua"  id='dianhuaid'  size="30" value="${bean.dianhua }" />

	</td>
	
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	身份证：
	</td>
	<td width="70%">
	<input  type="text" name="sfz"  id='sfzid'  size="30" value="${bean.sfz }" />

	</td>
	
</tr>



<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	院系：
	</td>
	<td width="70%">
	<select name="yuanxi">
	<c:forEach items="${list}" var="bean2">
	<option value="${bean2.id }" <c:if test="${bean.yuanxi.yname==bean2.yname }">selected</c:if> >${bean2.yname }</option>
	</c:forEach>
	
	</select>

	</td>
	
</tr>



<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	操作：
	</td>
	<td width="70%">
 <input type="submit" value="提交" style="width: 60px" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input  onclick="javascript:history.go(-1);" style="width: 60px" type="button" value="返回" />
	</td>
	
</tr>




</table>

</form>


</body>
</html>