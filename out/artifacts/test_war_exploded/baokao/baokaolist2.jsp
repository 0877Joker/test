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

<!--  快速转换位置按钮  -->
<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D1DDAA" align="center">
<tr>
 <td height="26" background="skin/images/newlinebg3.gif">
  <table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td align="center">
  <span style="font-weight: bold;"> ${title } </span>
 </td>
 </tr>
</table>
</td>
</tr>
</table>
  
<!--  内容列表   -->


<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="24" colspan="10" background="skin/images/tbg.gif">
	<form action="${url }" method="post">

报名编号：<input name="bianhao" type="text"  value="${bianhao }">
报考科目：<input name="kname" type="text"  value="${kname }">
考试姓名：<input name="truename" type="text"  value="${truename }">
审核状态：
<select name="shenhe">
<option value="">所有选项</option>
<option value="未审核" <c:if test="${shenhe=='未审核' }">selected</c:if> >未审核</option>
<option value="完成照片审核" <c:if test="${shenhe=='完成照片审核' }">selected</c:if> >完成照片审核</option>
<option value="确认报名" <c:if test="${shenhe=='确认报名' }">selected</c:if> >确认报名</option>
<option value="确认审核不通过" <c:if test="${shenhe=='确认审核不通过' }">selected</c:if> >确认审核不通过</option>
<option value="照片审核不通过" <c:if test="${shenhe=='照片审核不通过' }">selected</c:if> >照片审核不通过</option>
<option value="取消报名" <c:if test="${shenhe=='取消报名' }">selected</c:if> >取消报名</option>
</select>
专业：
<select name="zhuanye">
<option value="">所有选项</option>
<c:forEach items="${zylist}" var="bean">
<option value="${bean.zname }" <c:if test="${zhuanye==bean.zname }">selected</c:if> >${bean.zname }</option>

</c:forEach>

</select>

<input type="submit"  value="查询"/>
</form>
	
	</td>
</tr>
<tr align="center" bgcolor="#FAFAF1" height="22">
	<td >报考编号</td>
	<td >报考科目</td>
	<td >审核状态</td>
	<td >考试姓名</td>
	<td >专业</td>
	<td >报考时间</td>

	<td >操作</td>
	
</tr>

 <c:forEach items="${list}"  var="bean">
<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >

	<td>${bean.bianhao }</td>
	<td>${bean.kemu.kname }</td>
	<td>${bean.shenhe }</td>
	<td>${bean.truename }</td>
	<td>${bean.zhuanye.zname }</td>
	<td>${bean.t1 }</td>

	<td>
  	<a href="${url2 }update3?id=${bean.id }">查看详情</a>
	</td>
</tr>
</c:forEach>




<tr align="right" bgcolor="#EEF4EA">
	<td height="36" colspan="10" align="center">${pagerinfo }</td>
</tr>
</table>


</body>
</html>