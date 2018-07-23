<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>menu</title>
<link rel="stylesheet" href="skin/css/base.css" type="text/css" />
<link rel="stylesheet" href="skin/css/menu.css" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<script language='javascript'>var curopenItem = '1';</script>
<script language="javascript" type="text/javascript" src="skin/js/frame/menu.js"></script>
<base target="main" />
</head>
<body target="main">

<table width='99%' height="100%" border='0' cellspacing='0' cellpadding='0'>
  <tr>
    <td style='padding-left:3px;padding-top:8px' valign="top">
	<!-- Item 1 Strat -->
      
      <c:if test="${user.role==1}">
      
      <dl class='bitem'>
        <dt onClick='showHide("items1_1")'><b>发布通知公告</b></dt>
        <dd style='display:block' class='sitem' id='items1_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!tongzhilist' target='main'>通知公告列表</a></div>
               
              </div>
            </li>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!tongzhiadd' target='main'>添加通知公告</a></div>
               
              </div>
            </li>
          </ul>
        </dd>
      </dl>
      
      
      <dl class='bitem'>
        <dt onClick='showHide("items5_1")'><b>设置考试报名时间</b></dt>
        <dd style='display:block' class='sitem' id='items5_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!shijianadd' target='main'>点击设置</a></div>
               
              </div>
            </li>
           
            
          </ul>
        </dd>
      </dl>
      
      
      <dl class='bitem'>
        <dt onClick='showHide("items2_1")'><b>设置考试科目</b></dt>
        <dd style='display:block' class='sitem' id='items2_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!kemulist' target='main'>考试科目列表</a></div>
               
              </div>
            </li>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!kemuadd' target='main'>添加考试科目</a></div>
               
              </div>
            </li>
            
          </ul>
        </dd>
      </dl>
      
      <dl class='bitem'>
        <dt onClick='showHide("items3_1")'><b>设置院系</b></dt>
        <dd style='display:block' class='sitem' id='items3_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!yuanxilist' target='main'>院系列表</a></div>
               
              </div>
            </li>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!yuanxiadd' target='main'>添加新院系</a></div>
               
              </div>
            </li>
            
          </ul>
        </dd>
      </dl>
      
      
      <dl class='bitem'>
        <dt onClick='showHide("items4_1")'><b>院系管理员账户管理</b></dt>
        <dd style='display:block' class='sitem' id='items4_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!userlist' target='main'>管理员账户列表</a></div>
               
              </div>
            </li>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!useradd' target='main'>添加新账户</a></div>
               
              </div>
            </li>
            
          </ul>
        </dd>
      </dl>
      
      
      <dl class='bitem'>
        <dt onClick='showHide("items6_1")'><b>导入导出考生信息</b></dt>
        <dd style='display:block' class='sitem' id='items6_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!userlist2' target='main'>考生信息列表</a></div>
               
              </div>
            </li>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!useradd3' target='main'>导入考生信息</a></div>
               
              </div>
            </li>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!userlist3' target='main'>导出考生信息</a></div>
               
              </div>
            </li>
            
          </ul>
        </dd>
      </dl>
      
      
      <dl class='bitem'>
        <dt onClick='showHide("items7_1")'><b>考生账户密码重置</b></dt>
        <dd style='display:block' class='sitem' id='items7_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!userlist4' target='main'>考生账户列表</a></div>
               
              </div>
            </li>
           
            
          </ul>
        </dd>
      </dl>
      
      
      
     
      
      
      <dl class='bitem'>
        <dt onClick='showHide("items9_1")'><b>查看报考信息</b></dt>
        <dd style='display:block' class='sitem' id='items9_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!baokaolist5' target='main'>查看报考信息</a></div>
               
              </div>
            </li>
           
            
          </ul>
        </dd>
      </dl>
      
      
     </c:if>
     
     
      <c:if test="${user.role==3}">
      
      <dl class='bitem'>
        <dt onClick='showHide("items3_1")'><b>完善个人信息</b></dt>
        <dd style='display:block' class='sitem' id='items3_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!userupdate5' target='main'>完善个人信息</a></div>
               
              </div>
            </li>
           
          </ul>
        </dd>
      </dl>
      
      <dl class='bitem'>
        <dt onClick='showHide("items1_1")'><b>网上报名</b></dt>
        <dd style='display:block' class='sitem' id='items1_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!kemulist2' target='main'>考试科目列表</a></div>
               
              </div>
            </li>
           
          </ul>
        </dd>
      </dl>
      
      
      
      <dl class='bitem'>
        <dt onClick='showHide("items2_1")'><b>查看报名状态</b></dt>
        <dd style='display:block' class='sitem' id='items2_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!baokaolist' target='main'>查看报名状态</a></div>
               
              </div>
            </li>
           
          </ul>
        </dd>
      </dl>
      
      
      
      
      </c:if>
      
      
       <c:if test="${user.role==2}">
      
      <dl class='bitem'>
        <dt onClick='showHide("items1_1")'><b>查看本院系报考信息</b></dt>
        <dd style='display:block' class='sitem' id='items1_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!baokaolist2' target='main'>报考信息列表</a></div>
               
              </div>
            </li>
           
          </ul>
        </dd>
      </dl>
      
      
       <dl class='bitem'>
        <dt onClick='showHide("items2_1")'><b>审核考生上传的照片</b></dt>
        <dd style='display:block' class='sitem' id='items2_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!baokaolist3' target='main'>审核信息列表</a></div>
               
              </div>
            </li>
           
          </ul>
        </dd>
      </dl>
      
       <dl class='bitem'>
        <dt onClick='showHide("items8_1")'><b>确认考生报名</b></dt>
        <dd style='display:block' class='sitem' id='items8_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!baokaolist4' target='main'>确认考生报名</a></div>
               
              </div>
            </li>
           
            
          </ul>
        </dd>
      </dl>
      
      
      <dl class='bitem'>
        <dt onClick='showHide("items6_1")'><b>导入导出本院考生信息</b></dt>
        <dd style='display:block' class='sitem' id='items6_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!userlist5' target='main'>考生信息列表</a></div>
               
              </div>
            </li>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!useradd5' target='main'>导入考生信息</a></div>
               
              </div>
            </li>
            <li>
              <div class='items'>
                <div class='fllct'><a href='method!userlist6' target='main'>导出考生信息</a></div>
               
              </div>
            </li>
            
          </ul>
        </dd>
      </dl>
      
      
      
      
      </c:if>
     
	  </td>
  </tr>
</table>
</body>
</html>