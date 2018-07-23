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

</head>
<body leftmargin="8" topmargin="8" background='skin/images/allbg.gif'>


<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D1DDAA" align="center">
<tr>
 <td height="26" background="skin/images/newlinebg3.gif" align="center">
  	<span style="font-weight: bold;">${title }</span>
</td>
</tr>
</table>
  

<form action="${url }" method="post"  enctype="multipart/form-data">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">


<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	所属专业：
	</td>
	<td width="70%">
<select name="zhuanye" >
				
					    <c:forEach items="${zhuanyelist}" var="bean">
						<option value="${bean.id }">${bean.zname }</option>
						</c:forEach>
 </select>
	</td>
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	提示:
	</td>
	<td width="70%">
	导入的文件格式是xls,内容格式：用户名(学号)，姓名，身份证，手机，已考过等级

	</td>
	
</tr>
<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	导入格式下载
	</td>
	<td width="70%">
	<a href="uploadfile/geshi.zip">点击下载</a>

	</td>
	
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	请选择正确格式的xls文件：
	</td>
	<td width="70%">
	<input  name="uploadfile"  type="file"  size="30"/>

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