<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>新用户注册</title>
		<%
		response.addHeader("Cache-Control", "no-store, must-revalidate"); 
		response.addHeader("Expires", new java.util.Date().getTime()+"");
		%>
		
	<style type="text/css">
#regdiv {
	position: absolute;
	width: 700px;
	height: 500px;
	background-image: url(img/b2c_04.jpg);
}
</style>
<script language="javascript" type="text/javascript" src="js/jquery.min.js"></script>

<script language="javascript" type="text/javascript">

function checkregisterform()
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

	if (document.getElementById('passwordid').value=="")
	{
		alert("密码不能为空");
		return false;
	}
	if (document.getElementById('passwordid').value.length<6)
	{
		alert("密码长度必须大于6位");
		return false;
	}
	if (document.getElementById('password2id').value != document.getElementById('passwordid').value)
	{
		alert("确认密码与密码不一致");
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
	
	
	doRequestUsingPOSTregister();
	
}

function createQueryStringregister(){
	//必须两次编码才能解决中文问题
	var username = encodeURI(encodeURI($("#usernameid").val()));
	var password = encodeURI(encodeURI($("#passwordid").val()));
	var truename = encodeURI(encodeURI($("#truenameid").val()));
	var sfz = encodeURI(encodeURI($("#sfzid").val()));
	var dianhua = encodeURI(encodeURI($("#dianhuaid").val()));
	var yuanxi = encodeURI(encodeURI($("#pid").val()));
	var zhuanye = encodeURI(encodeURI($("#cid").val()));
	var dengji = encodeURI(encodeURI($("#dengjiid").val()));
	

	var queryString = 
	"username="+username+
	"&password="+password+
	"&truename="+truename+
	"&sfz="+sfz+
	"&dianhua="+dianhua+
	"&yuanxi="+yuanxi+
	"&zhuanye="+zhuanye+
	"&dengji="+dengji;
	return queryString;
}



function doRequestUsingPOSTregister(){

		$.ajax({
			type: "POST",
			url: "method!register2",
			data: createQueryStringregister(),
			success: function(data){
			var returnvalue = decodeURI(data);
				alert(returnvalue);
				if('注册新用户成功！请妥善保管！'===returnvalue){
					window.close();
				}
				
			}
		});
}


function resetform(){
window.close();
}
</script>
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


	<body>



		<div id="regdiv">

			<br />
			<br />
			<table align="center">
				<tr align="center">
					<td>
						考生用户注册
					</td>
				</tr>
			</table>
			
			
				<table align="center" border="1" cellpadding="5" cellspacing="3">
					<tr>
						<td>
							用户名(考生学号)：
						</td>
						<td>
							<input type="text" name="username" size="25"  id="usernameid"/>
						</td>
					</tr>
					<tr>
						<td>
							密码：
						</td>
						<td>
							<input type="password" name="password" size="25" id="passwordid"/>
						</td>
					</tr>
					<tr>
						<td>
							确认密码：
						</td>
						<td>
							<input type="password" name="password2" size="25" id="password2id"/>
						</td>
					</tr>
					<tr>
						<td>
							姓名：
						</td>
						<td>
							<input type="text" name="truename" size="25" id="truenameid"/>
						</td>
					</tr>
					<tr>
						<td>
							身份证：
						</td>
						<td>
							<input type="text" name="sfz" size="25" id="sfzid"/>
						</td>
					</tr>
					<tr>
						<td>
							手机：
						</td>
						<td>
							<input type="text" name="dianhua" size="25" id="dianhuaid" />
						</td>
					</tr>
					
					<tr>
						<td>
							所属院系：
						</td>
						<td>
						<select name="yuanxi" id="pid" onchange="Change_Select(this.value)">
					     <option value="0">请选择院系</option>
					    <c:forEach items="${list}" var="bean">
						<option value="${bean.id }">${bean.yname }</option>
						</c:forEach>
 						</select>
							
						</td>
					</tr>
					
					<tr>
						<td>
							所属专业:
						</td>
						<td>
							<select name="zhuanye" id="cid">
<option value="0">请选择专业</option>
</select>	
						</td>
					</tr>
					
					<tr>
						<td>
						已考过等级：
						</td>
						<td>
							<select name="dengji" id="dengjiid">
							<option value="无">无</option>
							<option value="一级">一级</option>
							<option value="二级">二级</option>
							<option value="三级">三级</option>
							<option value="四级">四级</option>
							</select>
						</td>
					</tr>
					
					
					<tr>

						<td colspan="2">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="submit" value="注册"  onclick="checkregisterform()"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="取消" onclick="resetform()"/>
						</td>
					</tr>
				</table>


		</div>

	</body>

</html>
