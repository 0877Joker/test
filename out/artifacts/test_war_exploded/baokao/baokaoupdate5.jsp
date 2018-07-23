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
	审核状态：
	</td>
	<td width="70%">
	${bean.shenhe }
	</td>
	
</tr>


<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	报考编号：
	</td>
	<td width="70%">
	${bean.bianhao }
	</td>
	
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	报考科目：
	</td>
	<td width="70%">
	${bean.kemu.kname }
	</td>
	
</tr>



<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	考生姓名：
	</td>
	<td width="70%">
	${bean.truename }
	</td>
	
</tr>


<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	考生照片：
	</td>
	<td width="70%">

	<img  src="<%=basePath %>uploadfile/${bean.photo }"  width="200" height="200"/>
	</td>	
</tr>


<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	手机：
	</td>
	<td width="70%">

${bean.dianhua }
	</td>
	
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	身份证：
	</td>
	<td width="70%">

${bean.sfz }
	</td>
	
</tr>



<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	院系：
	</td>
	<td width="70%">
	${bean.yuanxi.yname }
	
	</td>
	
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	专业：
	</td>
	<td width="70%">
	${bean.zhuanye.zname }
	
	</td>
	
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	已考过等级：
	</td>
	<td width="70%">
	${bean.dengji }
	
	</td>
	
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	报考时间：
	</td>
	<td width="70%">
	${bean.t1 }
	
	</td>
	
</tr>
<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	审核结果：
	</td>
	<td width="70%">
	<select name="shenhe">
	<option value="确认报名">确认报名</option>
	<option value="确认审核不通过">确认审核不通过</option>
	</select>
	</td>	
</tr>





<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	操作：
	</td>
	<td width="70%">
 <input type="submit" value="提交" style="width: 60px" />
				<input  onclick="javascript:history.go(-1);" style="width: 60px" type="button" value="返回" />
	</td>
	
</tr>




</table>

</form>


</body>
</html>