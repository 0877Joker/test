<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="baoming.util.Util"%>
<%@page import="org.springframework.web.context.WebApplicationContext,
org.springframework.web.context.support.WebApplicationContextUtils,baoming.dao.*,baoming.model.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Util.init(request);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>等级考试报名系统</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="css/css.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">


var code ; //在全局 定义验证码
function createCode(){
code = "";
var codeLength = 4;//验证码的长度
var checkCode = document.getElementById("checkCode");
checkCode.value = "";

var selectChar = new Array(2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');

for(var i=0;i<codeLength;i++) {
   var charIndex = Math.floor(Math.random()*32);
   code +=selectChar[charIndex];
}
if(code.length != codeLength){
   createCode();
}
checkCode.value = code;
}



		function checkregisterform(){

		var inputCode = document.getElementById("input1").value.toUpperCase();

if(inputCode.length <=0) {
   alert("请输入验证码！");
   return false;
}
else if(inputCode != code ){
   alert("验证码输入错误！");
   createCode();
   return false;



}

	 if (document.getElementById('usernameid').value=="")
	{
		alert("账号不能为空");
		return false;
	}


	if (document.getElementById('passwordid').value=="")
	{
		alert("密码不能为空");
		return false;
	}

	return true;

}


function   fnckeystop(evt){

     if(! window.event){

         var    keycode=evt.keycode;

        var key=String.fromcharcode(keycode).toLowercase();

        if(evt.ctrlkey&&key=="v"){

                 evt.preventDefault();

                evt.stopPropagation();

         }

    }

}



	</script>


<script language="javascript" type="text/javascript">

		
function registershow(){
		var now = new Date(); 
		var t = now.getTime()+''; 
		window.showModalDialog("method!register?t="+t, null, 
		'dialogWidth:700px;dialogHeight:500px;help:no;unadorned:no;resizable:no;status:no;scroll:no');
	}
</script>

</head>

<body onload="createCode();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="147" background="images/top02.gif">
    <span style="font-size: 40px;font-weight: bold;color: black;">
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
     &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    等级考试报名系统</span>
    </td>
  </tr>
</table>

 <%
            WebApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
            TongzhiDao tongzhiDao = (TongzhiDao)app.getBean("tongzhiDao");

            //通知列表
            List<Tongzhi> list = tongzhiDao.selectBeanList(0,6," where deletestatus=0  order by id desc");
            List<Tongzhi> list2 = new ArrayList<Tongzhi>();
           	for(Tongzhi bean:list){
           		if(bean.getBiaoti().length()>=15){
           			bean.setBiaoti(bean.getBiaoti().substring(0,8)+"...");
           		}
           		list2.add(bean);
           		
           	}
           	
         
            
            %>

<table width="562" border="0" align="center" cellpadding="0" cellspacing="0" class="right-table03">
  <tr>
    <td width="221"><table width="95%" border="0" cellpadding="0" cellspacing="0" class="login-text01">
      
      <tr>
     
        <td>
        
        <div style="margin-left:  -200px;">
        <table>
        <tr>
        <td>报考动态</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布时间</td>
        </tr>
         <%
        for(Tongzhi bean:list2){
        %>
        <tr >
        <td height="20px;"><span style="font-size: 12px;">
        <a href="method!tongzhiupdate3?id=<%=bean.getId() %>" >
       <%=bean.getBiaoti() %> 
        </a>
       
        
        </span></td> <td align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <%=bean.getCreatetime().substring(0,10) %> </td>
        </tr>
        
       
       
        
        
       <%
       }
       %>
        </table>
        </div>
        </td>
        <td align="right"><img src="images/line01.gif" width="5" height="292" /></td>
      </tr>
    </table></td>
  
    
    
    <td>
     <form method="post"   action="method!login" onsubmit=" return checkregisterform()">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="31%" height="35" class="login-text02">用户名称：<br /></td>
        <td width="69%"><input name="username" type="text" size="31" id="usernameid" /></td>
      </tr>
      <tr>
        <td height="35" class="login-text02">密　码：<br /></td>
        <td><input name="password" type="password" size="32"  id="passwordid"/></td>
      </tr>
       <tr>
        <td height="35" class="login-text02">用户角色：<br /></td>
        <td>
        <select name="role">
        <option value="3">考生</option>
        <option value="2">院系管理员</option>
        <option value="1">系统管理员</option>
        </select>
        </td>
      </tr>
      <tr>
        <td height="35" class="login-text02">输入验证码:<br /></td>
        <td><input type="text" id="input1" size="32" /></td>
      </tr>
      <tr>
        <td height="35" class="login-text02">获取验证码:<br /></td>
        <td>
        <input type="text" id="checkCode" class="code" style="width: 55px" size="20" readonly="readonly"  onpaste="return false" oncontextmenu="return false" oncopy="javascript:alert('不可复制');return false;" oncut="return false" />
        <a href="####" onclick="createCode()">看不清楚</a>
        </td>
      </tr>
      
      <tr>
        <td height="35">&nbsp;</td>
        <td><input name="Submit2" type="submit" class="right-button01" value="确认登陆"  />
       <input name="Submit232" type="button" class="right-button01" value="考试注册" onclick="registershow()" />
        
          <input name="Submit232" onclick="javascript:alert('请联系系统管理员重置密码，系统管理员的联系方式13000000000');" type="button" class="right-button01" value="忘记密码" /></td>
      </tr>
    </table>
    </form>
    
    </td>
  </tr>
</table>
</body>
</html>