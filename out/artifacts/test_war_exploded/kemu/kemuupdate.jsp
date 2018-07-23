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
	 
	

	if (document.getElementById('knameid').value=="")
	{
		alert("科目名不能为空");
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
	等级：
	</td>
	<td width="70%">
	<select name="dengji">
	<option value="一级" <c:if test="${bean.dengji=='一级' }">selected</c:if> >一级</option>
	<option value="二级" <c:if test="${bean.dengji=='二级' }">selected</c:if> >二级</option>
	<option value="三级" <c:if test="${bean.dengji=='三级' }">selected</c:if> >三级</option>
	<option value="四级" <c:if test="${bean.dengji=='四级' }">selected</c:if> >四级</option>
	</select>
	</td>
	
</tr>


<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	科目名：
	</td>
	<td width="70%">
	<input  type="text" name="kname"  id='knameid'  size="30" value="${bean.kname }" />

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