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
<script type="text/javascript">
    var req;
   
    function Change_Select(selectedId){//当第一个下拉框的选项发生改变时调用该函数
      var now = new Date();
      var url = "method!getcate?pid="+selectedId+"&t="+now.getTime();
      if(window.XMLHttpRequest){
        req = new XMLHttpRequest();
      }else if(window.ActiveXObject){
        req = new ActiveXObject("Microsoft.XMLHTTP");
      }
      if(req){
        //指定回调函数为callback
      	req.onreadystatechange = callback;
        req.open("GET",url,true);
        req.send(null);
      }
    }
    //回调函数
    function callback(){
      if(req.readyState ==4){
        if(req.status ==200){
          parseMessage();//解析XML文档
        }else{
          alert("不能得到描述信息:" + req.statusText);
        }
      }
    }
    //解析返回xml的方法
    function parseMessage(){
      var xSel = req.responseXML.getElementsByTagName('select');//获得返回的XML文档
      //获得XML文档中的所有<select>标记
      var select_root = document.getElementById('cid');
      //获得网页中的第二个下拉框
      select_root.options.length=0;
      //每次获得新的数据的时候先把每二个下拉框架的长度清0
     
      for(var i=0;i<xSel.length;i++){
        var xValue = xSel[i].childNodes[0].firstChild.nodeValue;
        //获得每个<select>标记中的第一个标记的值,也就是<value>标记的值
        var xText = xSel[i].childNodes[1].firstChild.nodeValue;
        //获得每个<select>标记中的第二个标记的值,也就是<text>标记的值
        var option = new Option(xText, xValue);
        //根据每组value和text标记的值创建一个option对象
       
        try{
          select_root.add(option);//将option对象添加到第二个下拉框中
        }catch(e){
        }
      }
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
  

<form action="${url }" method="post"  enctype="multipart/form-data">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">


<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	所属院系：
	</td>
	<td width="70%">
<select name="yuanxi" id="pid" onchange="Change_Select(this.value)">
					     <option value="0">请选择院系</option>
					    <c:forEach items="${list}" var="bean">
						<option value="${bean.id }">${bean.yname }</option>
						</c:forEach>
 </select>
	</td>
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	所属专业：
	</td>
	<td width="70%">
<select name="zhuanye" id="cid">
<option value="0">请选择专业</option>
</select>		
	</td>
</tr>


<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="30%">
	提示:
	</td>
	<td width="70%">
	导入的文件格式是xls,内容格式：用户名(学号)，姓名，身份证，手机，已考过等级
<br/>
(必须确认姓名长度小于等于4位，身份证和手机格式都正确，不然导入失败)
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