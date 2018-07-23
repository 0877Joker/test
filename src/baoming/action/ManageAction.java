package baoming.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.ServletActionContext;

import baoming.dao.BaokaoDao;
import baoming.dao.KemuDao;
import baoming.dao.ShijianDao;
import baoming.dao.TongzhiDao;
import baoming.dao.UserDao;
import baoming.dao.YuanxiDao;
import baoming.dao.ZhuanyeDao;
import baoming.model.Baokao;
import baoming.model.Kemu;
import baoming.model.Shijian;
import baoming.model.Tongzhi;
import baoming.model.User;
import baoming.model.Yuanxi;
import baoming.model.Zhuanye;
import baoming.util.Pager;
import baoming.util.Util;

import com.opensymphony.xwork2.ActionSupport;

public class ManageAction extends ActionSupport {

	private static final long serialVersionUID = -4304509122548259589L;

	private UserDao userDao;

	private String url = "./";

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	
	
	
//登入请求
	public String login() throws IOException {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String  role = request.getParameter("role");
		User user = userDao.selectBean(" where username = '" + username
				+ "' and password= '" + password + "' and role= "+role +" and deletestatus=0 ");
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			this.setUrl("index.jsp");
			return "redirect";
		} else {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");
			response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('用户名或者密码错误');window.location.href='login.jsp';</script>");
		}
		return null;
	}
//用户退出
	public String loginout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		this.setUrl("login.jsp");
		return SUCCESS;
	}
//跳转到修改密码页面
	public String changepwd() {
		this.setUrl("user/user.jsp");
		return SUCCESS;
	}
//修改密码操作
	public void changepwd2() throws IOException {
HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		User bean = userDao.selectBean(" where username= '"+u.getUsername()+"' and password= '"+password1+"' and deletestatus=0");
		if(bean!=null){
			bean.setPassword(password2);
			userDao.updateBean(bean);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('修改成功');window.location.href='method!changepwd';</script>");
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('原密码错误');window.location.href='method!changepwd';</script>");
		}
	}
	
	
	private TongzhiDao tongzhiDao;

	public TongzhiDao getTongzhiDao() {
		return tongzhiDao;
	}

	public void setTongzhiDao(TongzhiDao tongzhiDao) {
		this.tongzhiDao = tongzhiDao;
	}
	
	
	//通知公告列表
	public String tongzhilist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String biaoti = request.getParameter("biaoti");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (biaoti != null && !"".equals(biaoti)) {

			sb.append("biaoti like '%" + biaoti + "%'");
			sb.append(" and ");
			request.setAttribute("biaoti", biaoti);
		}

		sb.append("  deletestatus=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = tongzhiDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", tongzhiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!tongzhilist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!tongzhilist");
		request.setAttribute("url2", "method!tongzhi");
		request.setAttribute("title", "通知公告管理");
		this.setUrl("tongzhi/tongzhilist.jsp");
		return SUCCESS;

	}
//跳转到添加通知公告页面
	public String tongzhiadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!tongzhiadd2");
		request.setAttribute("title", "通知公告添加");
		this.setUrl("tongzhi/tongzhiadd.jsp");
		return SUCCESS;
	}
//添加通知公告操作
	public void tongzhiadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String biaoti = request.getParameter("biaoti");
		String neirong = request.getParameter("neirong");

		Tongzhi bean = new Tongzhi();
		bean.setBiaoti(biaoti);
		bean.setCreatetime(Util.getTime());
		bean.setNeirong(neirong);
		tongzhiDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!tongzhilist';</script>");
		
		
	}
//跳转到更新通知公告页面
	public String tongzhiupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Tongzhi bean = tongzhiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!tongzhiupdate2?id="+bean.getId());
		request.setAttribute("title", "通知公告更新");
		this.setUrl("tongzhi/tongzhiupdate.jsp");
		return SUCCESS;
	}
//更新通知公告操作
	public void tongzhiupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String biaoti = request.getParameter("biaoti");
		String neirong = request.getParameter("neirong");
		Tongzhi bean = tongzhiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setBiaoti(biaoti);
		bean.setNeirong(neirong);
		
		tongzhiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!tongzhilist';</script>");
	}
//删除通知公告操作
	public void tongzhidelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Tongzhi bean = tongzhiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		tongzhiDao.deleteBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!tongzhilist';</script>");
	}
	
	//跳转到查看通知公告页面
	public String tongzhiupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Tongzhi bean = tongzhiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "通知公告查看");
		this.setUrl("tongzhi/tongzhiupdate3.jsp");
		return SUCCESS;
	}
	
	
	private KemuDao kemuDao;

	public KemuDao getKemuDao() {
		return kemuDao;
	}

	public void setKemuDao(KemuDao kemuDao) {
		this.kemuDao = kemuDao;
	}
	
	
	
	//考试科目列表
	public String kemulist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String kname = request.getParameter("kname");
		String dengji = request.getParameter("dengji");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kname != null && !"".equals(kname)) {

			sb.append("kname like '%" + kname + "%'");
			sb.append(" and ");
			request.setAttribute("kname", kname);
		}
		
		if (dengji != null && !"".equals(dengji)) {

			sb.append("dengji like '%" + dengji + "%'");
			sb.append(" and ");
			request.setAttribute("dengji", dengji);
		}

		sb.append("  deletestatus=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = kemuDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", kemuDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!kemulist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!kemulist");
		request.setAttribute("url2", "method!kemu");
		request.setAttribute("title", "考试科目管理");
		this.setUrl("kemu/kemulist.jsp");
		return SUCCESS;

	}
//跳转到添加考试科目页面
	public String kemuadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!kemuadd2");
		request.setAttribute("title", "考试科目添加");
		this.setUrl("kemu/kemuadd.jsp");
		return SUCCESS;
	}
//添加考试科目操作
	public void kemuadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String dengji = request.getParameter("dengji");
		String kname = request.getParameter("kname");

		Kemu bean = kemuDao.selectBean(" where kname='"+kname+"'  and deletestatus=0 ");
		if(bean==null){
			bean = new Kemu();
			bean.setDengji(dengji);
			bean.setKname(kname);
			
			kemuDao.insertBean(bean);
			
			
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!kemulist';</script>");
		}else{
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('操作失败，该科目名已经存在');window.location.href='method!kemulist';</script>");
		}
		
		
		
	}
//跳转到更新考试科目页面
	public String kemuupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Kemu bean = kemuDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!kemuupdate2?id="+bean.getId());
		request.setAttribute("title", "考试科目更新");
		this.setUrl("kemu/kemuupdate.jsp");
		return SUCCESS;
	}
//更新考试科目操作
	public void kemuupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String dengji = request.getParameter("dengji");
		String kname = request.getParameter("kname");
		
		Kemu bean = kemuDao.selectBean(" where kname='"+kname+"' and  id != "+ request.getParameter("id"));
		if(bean!=null){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('操作失败，该科目名已经存在');window.location.href='method!kemulist';</script>");
			return ;
		}else{
			bean = kemuDao.selectBean(" where id= "+ request.getParameter("id"));
			bean.setDengji(dengji);
			bean.setKname(kname);
			
			kemuDao.updateBean(bean);
			
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!kemulist';</script>");
		}
		
		
	}
//删除考试科目操作
	public void kemudelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Kemu bean = kemuDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setDeletestatus(1);
		kemuDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!kemulist';</script>");
	}
	
	
	
	private YuanxiDao yuanxiDao;

	public YuanxiDao getYuanxiDao() {
		return yuanxiDao;
	}

	public void setYuanxiDao(YuanxiDao yuanxiDao) {
		this.yuanxiDao = yuanxiDao;
	}
	
	
	//院系列表
	public String yuanxilist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String yname = request.getParameter("yname");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (yname != null && !"".equals(yname)) {

			sb.append("yname like '%" + yname + "%'");
			sb.append(" and ");
			request.setAttribute("yname", yname);
		}

		sb.append("  deletestatus=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = yuanxiDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", yuanxiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!yuanxilist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!yuanxilist");
		request.setAttribute("url2", "method!yuanxi");
		request.setAttribute("title", "院系管理");
		this.setUrl("yuanxi/yuanxilist.jsp");
		return SUCCESS;

	}
//跳转到添加院系页面
	public String yuanxiadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!yuanxiadd2");
		request.setAttribute("title", "院系添加");
		this.setUrl("yuanxi/yuanxiadd.jsp");
		return SUCCESS;
	}
//添加院系操作
	public void yuanxiadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String yname = request.getParameter("yname");


		Yuanxi bean = yuanxiDao.selectBean(" where yname='"+yname+"' and  deletestatus=0");
		if(bean==null){
			bean = new Yuanxi();
			bean.setYname(yname);	
			yuanxiDao.insertBean(bean);
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!yuanxilist';</script>");
		}else{
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('操作失败，该院系名字已经存在');window.location.href='method!yuanxilist';</script>");
		}
		
		
		
	}
//跳转到更新院系页面
	public String yuanxiupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Yuanxi bean = yuanxiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!yuanxiupdate2?id="+bean.getId());
		request.setAttribute("title", "院系更新");
		this.setUrl("yuanxi/yuanxiupdate.jsp");
		return SUCCESS;
	}
//更新院系操作
	public void yuanxiupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String yname = request.getParameter("yname");
		Yuanxi bean = yuanxiDao.selectBean(" where yname='"+yname+"' and  id!= "+ request.getParameter("id"));
		if(bean!=null){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('操作失败，该院系名已经存在');window.location.href='method!yuanxilist';</script>");
		}else{
			bean = yuanxiDao.selectBean(" where id= "+ request.getParameter("id"));
			bean.setYname(yname);
			
			yuanxiDao.updateBean(bean);
			
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!yuanxilist';</script>");
		}
		
	}
//删除院系操作
	public void yuanxidelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Yuanxi bean = yuanxiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setDeletestatus(1);
		yuanxiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!yuanxilist';</script>");
	}
	
	
	//院系管理员账户列表
	public String userlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String truename = request.getParameter("truename");
		String yuanxi = request.getParameter("yuanxi");
		
		List<Yuanxi> yxlist = yuanxiDao.selectBeanList(0, 9999, " where deletestatus=0 ");
		request.setAttribute("yxlist", yxlist);
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (username != null && !"".equals(username)) {

			sb.append("username like '%" + username + "%'");
			sb.append(" and ");
			request.setAttribute("username", username);
		}
		if (truename != null && !"".equals(truename)) {

			sb.append("truename like '%" + truename + "%'");
			sb.append(" and ");
			request.setAttribute("truename", truename);
		}
		
		if (yuanxi != null && !"".equals(yuanxi)) {

			sb.append("yuanxi.yname like '%" + yuanxi + "%'");
			sb.append(" and ");
			request.setAttribute("yuanxi", yuanxi);
		}

		sb.append("  deletestatus=0 and role=2 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist");
		request.setAttribute("url2", "method!user");
		request.setAttribute("title", "院系管理员账户管理");
		this.setUrl("user/userlist.jsp");
		return SUCCESS;

	}
//跳转到添加院系管理员账户页面
	public String useradd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("list", yuanxiDao.selectBeanList(0, 9999, " where deletestatus=0 "));
		request.setAttribute("url", "method!useradd2");
		request.setAttribute("title", "院系管理员账户添加");
		this.setUrl("user/useradd.jsp");
		return SUCCESS;
	}
//添加院系管理员账户操作
	public void useradd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String username = request.getParameter("username");
		String dianhua = request.getParameter("dianhua");
		String sfz = request.getParameter("sfz");
		String truename = request.getParameter("truename");
		String yuanxi = request.getParameter("yuanxi");

		User bean = userDao.selectBean(" where username='"+username+"' ");
		if(bean==null){
			bean = new User();
			bean.setCreatetime(new Date());
			bean.setDianhua(dianhua);
			bean.setPassword("111111");
			bean.setRole(2);
			bean.setSfz(sfz);
			bean.setTruename(truename);
			bean.setUsername(username);
			bean.setYuanxi(yuanxiDao.selectBean(" where id= "+yuanxi));
			userDao.insertBean(bean);
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!userlist';</script>");
		}else{
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('操作失败，该用户名已经存在');window.location.href='method!userlist';</script>");
			
		}

	}
	
//跳转到更新院系管理员账户页面
	public String userupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("list", yuanxiDao.selectBeanList(0, 9999, " where deletestatus=0 "));
		request.setAttribute("url", "method!userupdate2?id="+bean.getId());
		request.setAttribute("title", "院系管理员账户更新");
		this.setUrl("user/userupdate.jsp");
		return SUCCESS;
	}
//更新院系管理员账户操作
	public void userupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String dianhua = request.getParameter("dianhua");
		String sfz = request.getParameter("sfz");
		String truename = request.getParameter("truename");
		String yuanxi = request.getParameter("yuanxi");
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setDianhua(dianhua);
		bean.setSfz(sfz);
		bean.setTruename(truename);
		bean.setYuanxi(yuanxiDao.selectBean(" where id= "+yuanxi));
		userDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist';</script>");
	}
//删除院系管理员账户操作
	public void userdelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setDeletestatus(1);
		userDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist';</script>");
	}
	
	
	
	private ShijianDao shijianDao;

	public ShijianDao getShijianDao() {
		return shijianDao;
	}

	public void setShijianDao(ShijianDao shijianDao) {
		this.shijianDao = shijianDao;
	}
	
	
	
	//跳转到设置报名时间页面
	public String shijianadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int count = shijianDao.selectBeanCount("");
		if(count==0){
			Shijian bean = new Shijian();
			shijianDao.insertBean(bean);
			
		}
		Shijian bean = shijianDao.selectBean(" where id=1 ");
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!shijianadd2");
		request.setAttribute("title", "设置报名时间");
		this.setUrl("shijian/shijianadd.jsp");
		return SUCCESS;
	}
//设置报名时间操作
	public void shijianadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		String jieshu = request.getParameter("jieshu");
		String kaishi = request.getParameter("kaishi");
		
		int a1 =Integer.parseInt(kaishi.replaceAll("-", ""));
		int a2 =Integer.parseInt(jieshu.replaceAll("-", ""));
		
		if(a1>a2){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('操作失败，开始时间必须比结束时间小');window.location.href='method!shijianadd';</script>");
			return;
		}
		
		
		Shijian bean = shijianDao.selectBean(" where id=1 ");
		bean.setJieshu(jieshu);
		bean.setKaishi(kaishi);
		shijianDao.insertBean(bean);
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shijianadd';</script>");
	
	}
	
	
	//考生信息列表
	public String userlist2() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String truename = request.getParameter("truename");
		String yuanxi = request.getParameter("yuanxi");
		String zhuanye = request.getParameter("zhuanye");
		
		List<Yuanxi> yxlist = yuanxiDao.selectBeanList(0, 9999, " where deletestatus=0 ");
		request.setAttribute("yxlist", yxlist);
		
		List<Zhuanye> zylist = zhuanyeDao.selectBeanList(0, 9999, " where deletestatus=0 ");
		request.setAttribute("zylist", zylist);
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (username != null && !"".equals(username)) {

			sb.append("username like '%" + username + "%'");
			sb.append(" and ");
			request.setAttribute("username", username);
		}
		if (truename != null && !"".equals(truename)) {

			sb.append("truename like '%" + truename + "%'");
			sb.append(" and ");
			request.setAttribute("truename", truename);
		}
		if (yuanxi != null && !"".equals(yuanxi)) {

			sb.append("yuanxi.yname like '%" + yuanxi + "%'");
			sb.append(" and ");
			request.setAttribute("yuanxi", yuanxi);
		}
		if (zhuanye != null && !"".equals(zhuanye)) {

			sb.append("zhuanye.zname like '%" + zhuanye + "%'");
			sb.append(" and ");
			request.setAttribute("zhuanye", zhuanye);
		}

		sb.append("  deletestatus=0 and role=3 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist2", "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist2");
		request.setAttribute("url2", "method!user");
		request.setAttribute("title", "考生信息管理");
		this.setUrl("user/userlist2.jsp");
		return SUCCESS;

	}
	
	
	//跳转到导入考生信息页面
	public String useradd3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!useradd4");
		request.setAttribute("title", "导入考生信息");
		request.setAttribute("list", yuanxiDao.selectBeanList(0, 9999, " where deletestatus=0 "));
		this.setUrl("user/useradd3.jsp");
		return SUCCESS;
	}
	
	private File uploadfile;
	
	public File getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}

	//excel导入考生信息
	public void useradd4() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String yuanxi = request.getParameter("yuanxi");
		String zhuanye = request.getParameter("zhuanye");
		int count = 0;
		try {
			Workbook wb = Workbook.getWorkbook(uploadfile);
			Sheet st = wb.getSheet("Sheet1");//得到工作薄中的第一个工作表 
			int row = st.getRows();
			for (int i = 1; i < row; i++) {
				Cell cell0 = st.getCell(0, i);//用户名
				Cell cell1 = st.getCell(1, i);//姓名
				Cell cell2 = st.getCell(2, i);//身份证
				Cell cell3= st.getCell(3, i);//手机
				Cell cell4= st.getCell(4, i);//已考过等级
				String username = cell0.getContents();
				String truename = cell1.getContents();
				String sfz = cell2.getContents();
				String dianhua = cell3.getContents();
				String dengji = cell4.getContents();
				if (null==username )
					continue;
				if (null==truename )
					continue;
				if(truename.length()>4)
					continue;
				if (null==sfz )
					continue;
				String regEx = "^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$"; 
			
				if(!Util.yanzheng(sfz, regEx))
					continue;
				if (null==dianhua )
					continue;
				 regEx = "^0?1[3,4,5,8][0,1,2,3,4,5,6,7,8,9]\\d{8}$"; 
				
				 if(!Util.yanzheng(dianhua, regEx))
						continue;
				if (null==dengji )
					continue;
				User bean = userDao.selectBean(" where username='"+username+"' and deletestatus=0   and role=3 ");
				if(bean!=null){
					continue;
				}
				User u = userDao.selectBean(" where  sfz='"+sfz+"' and deletestatus=0");
				if(u!=null){
					continue;
				}
				
				
				bean = new User();
				bean.setCreatetime(new Date());
				bean.setDengji(dengji);
				bean.setDianhua(dianhua);
				bean.setPassword("111111");
				bean.setSfz(sfz);
				bean.setTruename(truename);
				bean.setUsername(username);
				bean.setYuanxi(yuanxiDao.selectBean(" where id= "+yuanxi));
				bean.setZhuanye(zhuanyeDao.selectBean(" where id= "+zhuanye));
				bean.setRole(3);		
				userDao.insertBean(bean);
				count++;

			}
			wb.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功,成功导入"+count+"条');window.location.href='method!userlist2';</script>");
	}
	
	
	
	//跳转到更新考生信息页面
		public String userupdate3() {
			HttpServletRequest request = ServletActionContext.getRequest();
			User bean = userDao.selectBean(" where id= "
					+ request.getParameter("id"));
			request.setAttribute("bean", bean);
			request.setAttribute("list", yuanxiDao.selectBeanList(0, 9999, " where deletestatus=0 "));
			request.setAttribute("url", "method!userupdate4?id="+bean.getId());
			request.setAttribute("title", "考生信息更新");
			this.setUrl("user/userupdate3.jsp");
			return SUCCESS;
		}
	//更新考生信息操作
		public void userupdate4() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			String dianhua = request.getParameter("dianhua");
			String sfz = request.getParameter("sfz");
			String truename = request.getParameter("truename");
			String dengji = request.getParameter("dengji");
			String yuanxi = request.getParameter("yuanxi");
			String zhuanye = request.getParameter("zhuanye");
			User u = userDao.selectBean(" where id!= "+ request.getParameter("id")+"  and sfz='"+sfz+"' and  deletestatus=0");
			if(u!=null){
				response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作失败，该身份证已经存在');window.location.href='method!userlist2';</script>");
				return;
			}
			
			User bean = userDao.selectBean(" where id= "
					+ request.getParameter("id"));
			bean.setDianhua(dianhua);
			bean.setSfz(sfz);
			bean.setTruename(truename);
			bean.setDengji(dengji);
			if(!"0".equals(yuanxi)){
				bean.setYuanxi(yuanxiDao.selectBean(" where id= "+yuanxi));
				bean.setZhuanye(zhuanyeDao.selectBean(" where id= "+zhuanye));
			}
			
			userDao.updateBean(bean);
			
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!userlist2';</script>");
		}
		
		
		//删除考生信息操作
		public void userdelete2() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			User bean = userDao.selectBean(" where id= "
					+ request.getParameter("id"));
			bean.setDeletestatus(1);
			userDao.updateBean(bean);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!userlist2';</script>");
		}
		
		
		//导出excel
		public String userlist3() throws Exception{
			HttpServletRequest request = ServletActionContext.getRequest();
			
			
			StringBuffer sb = new StringBuffer();
			sb.append(" where ");
			sb.append("  deletestatus=0 and role=3  order by id desc");

			String where = sb.toString();

			int currentpage = 1;
			int pagesize =10000;
			if(request.getParameter("pagenum")!=null){
				currentpage = Integer.parseInt(request.getParameter("pagenum"));
			}

			List<User> list = userDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where);
			HttpServletResponse response = ServletActionContext.getResponse();

			int columnCount = 8;
			
			
			
			response.setContentType("application/vnd.ms-excel");
			String filename ="考生信息表.xls";
			
			response.setHeader("Content-Disposition","attachment; filename=\""+new String(filename.getBytes("gb18030"),"iso8859-1") + "\"");
			//首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象  
			WritableWorkbook wwb = Workbook.createWorkbook(response.getOutputStream());
			//创建一个可写入的工作表    
	        //Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置    
	        WritableSheet ws = wwb.createSheet("sheet1", 0); 
	        //int lie = Integer.parseInt(request.getParameter("lieming"));
			// 获得导出数据的列数
			String[] title = { "用户名（学号）", "密码","姓名", "身份证","手机", "院系","专业", "已考过等级"};
			//写入字段名
			for (int i = 0; i < columnCount; i++) {
				jxl.write.Label lab = new jxl.write.Label(i,0,title[i]);
				ws.addCell(lab);
			}
			 for(int i=0;i<list.size();i++)  {
				 User  bean =  list.get(i); 

				 	jxl.write.Label lab1 = new jxl.write.Label(0,i+1,bean.getUsername()+"");
				 	
					jxl.write.Label lab2 = new jxl.write.Label(1,i+1,bean.getPassword()+"");
					jxl.write.Label lab3 = new jxl.write.Label(2,i+1,bean.getTruename()+"");
					jxl.write.Label lab4 = new jxl.write.Label(3,i+1,bean.getSfz()+"");
					jxl.write.Label lab5 = new jxl.write.Label(4,i+1,bean.getDianhua()+"");
					jxl.write.Label lab6 = new jxl.write.Label(5,i+1,bean.getYuanxi().getYname()+"");
					jxl.write.Label lab7 = null;
					if(bean.getZhuanye()!=null){
						lab7 = new jxl.write.Label(6,i+1,bean.getZhuanye().getZname()+"");
					}else{
						lab7 = new jxl.write.Label(6,i+1,"");
					}

					jxl.write.Label lab8 = new jxl.write.Label(7,i+1,bean.getDengji()+"");
					


					ws.addCell(lab1);
					ws.addCell(lab2);
					ws.addCell(lab3);
					ws.addCell(lab4);
					ws.addCell(lab5);
					ws.addCell(lab6);
					ws.addCell(lab7);
					ws.addCell(lab8);

			   }
			//从内存中写入文件中    
	        wwb.write();
	        //关闭资源，释放内存    
	        wwb.close(); 
	       // response.getWriter();
		
		return null;
			
		}
		
		
		
		//考生账户列表
		public String userlist4() {
			HttpServletRequest request = ServletActionContext.getRequest();
			String username = request.getParameter("username");
			String truename = request.getParameter("truename");

			
			StringBuffer sb = new StringBuffer();
			sb.append(" where ");

			if (username != null && !"".equals(username)) {

				sb.append("username like '%" + username + "%'");
				sb.append(" and ");
				request.setAttribute("username", username);
			}
			if (truename != null && !"".equals(truename)) {

				sb.append("truename like '%" + truename + "%'");
				sb.append(" and ");
				request.setAttribute("truename", truename);
			}

			sb.append("  deletestatus=0 and role=3 order by id desc ");
			String where = sb.toString();


			int currentpage = 1;
			int pagesize = 15;
			if (request.getParameter("pagenum") != null) {
				currentpage = Integer.parseInt(request.getParameter("pagenum"));
			}
			int total = userDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
			request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
					* pagesize, pagesize, where));
			request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
					currentpage, "method!userlist4", "共有" + total + "条记录"));
			request.setAttribute("url", "method!userlist4");
			request.setAttribute("url2", "method!user");
			request.setAttribute("title", "考生账户管理");
			this.setUrl("user/userlist4.jsp");
			return SUCCESS;

		}
		
		//重置密码操作
		public void userdelete3() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			User bean = userDao.selectBean(" where id= "
					+ request.getParameter("id"));
			bean.setPassword("111111");
			userDao.updateBean(bean);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功,密码重置为111111，请妥善保管');window.location.href='method!userlist4';</script>");
		}
		
		
		//跳转到考生注册页面
		public String register() {
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("list", yuanxiDao.selectBeanList(0, 9999, " where deletestatus=0 "));
			this.setUrl("register.jsp");
			return SUCCESS;
		}
	//考生注册操作
		public void register2() throws Exception{
			HttpServletRequest request = ServletActionContext.getRequest();
			
			HttpServletResponse response = ServletActionContext.getResponse();
			
			String username = java.net.URLDecoder.decode(request.getParameter("username"), "utf-8");
			
			String sfz = java.net.URLDecoder.decode(request.getParameter("sfz"), "utf-8");
			
			User  u = userDao.selectBean(" where sfz='"+sfz+"' and  deletestatus=0");
			if(u!=null){
				response.setCharacterEncoding("utf-8");
				response.getWriter().write("该身份证号码已经存在，请重新注册！");
				return;
			}
			
			User bean = userDao.selectBean(" where username='"+username+"' and  deletestatus=0 ");
			if(bean==null){
				bean = new User();
				bean.setUsername(username);
				
				bean.setPassword(request.getParameter("password"));
				String truename = java.net.URLDecoder.decode(request.getParameter("truename"), "utf-8");
				String dengji = java.net.URLDecoder.decode(request.getParameter("dengji"), "utf-8");
				String dianhua = java.net.URLDecoder.decode(request.getParameter("dianhua"), "utf-8");
				
				String yuanxi = java.net.URLDecoder.decode(request.getParameter("yuanxi"), "utf-8");
				String zhuanye = java.net.URLDecoder.decode(request.getParameter("zhuanye"), "utf-8");
				bean.setTruename(truename);
				bean.setCreatetime(new Date());
				bean.setDengji(dengji);
				bean.setDianhua(dianhua);
				bean.setRole(3);
				bean.setSfz(sfz);
				bean.setYuanxi(yuanxiDao.selectBean(" where id= "+yuanxi));
				bean.setZhuanye(zhuanyeDao.selectBean(" where  id="+zhuanye));
				userDao.insertBean(bean);
				
				response.setCharacterEncoding("utf-8");
				
				response.getWriter().write("注册新用户成功！请妥善保管！");
			}else{
				response.setCharacterEncoding("utf-8");
				response.getWriter().write("该用户名已经存在，请重新注册！");
			}
		}
		
		
		//考试科目列表
		public String kemulist2() {
			HttpServletRequest request = ServletActionContext.getRequest();
			String kname = request.getParameter("kname");
			String dengji = request.getParameter("dengji");

			
			StringBuffer sb = new StringBuffer();
			sb.append(" where ");

			if (kname != null && !"".equals(kname)) {

				sb.append("kname like '%" + kname + "%'");
				sb.append(" and ");
				request.setAttribute("kname", kname);
			}
			
			if (dengji != null && !"".equals(dengji)) {

				sb.append("dengji like '%" + dengji + "%'");
				sb.append(" and ");
				request.setAttribute("dengji", dengji);
			}
			
			sb.append("  deletestatus=0 order by id desc ");
			String where = sb.toString();


			int currentpage = 1;
			int pagesize = 15;
			if (request.getParameter("pagenum") != null) {
				currentpage = Integer.parseInt(request.getParameter("pagenum"));
			}
			int total = kemuDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
			request.setAttribute("list", kemuDao.selectBeanList((currentpage - 1)
					* pagesize, pagesize, where));
			request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
					currentpage, "method!kemulist2", "共有" + total + "条记录"));
			request.setAttribute("url", "method!kemulist2");
			request.setAttribute("url2", "method!kemu");
			request.setAttribute("title", "报考科目列表");
			this.setUrl("kemu/kemulist2.jsp");
			return SUCCESS;

		}
		
		private BaokaoDao baokaoDao;

		public BaokaoDao getBaokaoDao() {
			return baokaoDao;
		}

		public void setBaokaoDao(BaokaoDao baokaoDao) {
			this.baokaoDao = baokaoDao;
		}
		
		
		
		//跳转到报考科目页面
		public String baokaoadd() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			
			String riqi = Util.getRiqi();
			
			Shijian sj = shijianDao.selectBean(" where kaishi<='"+riqi+"' and  '"+riqi+"'<=jieshu ");
			if(sj==null){
				sj = shijianDao.selectBean(" where id=1 ");
				response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作失败，未在报考日期范围内，报考日期为"+sj.getKaishi()+"到"+sj.getJieshu()+"');window.location.href='method!kemulist2';</script>");
				return null;
			}
			
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			
			
			
			Kemu kemu = kemuDao.selectBean(" where id= "+request.getParameter("id"));
			
			Baokao baokao = baokaoDao.selectBean(" where shenhe!='审核不通过' and shenhe!='取消报名' and kemu.id="+kemu.getId()+" and user.id= "+user.getId());
			if(baokao!=null){
				response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作失败，您已经报考该科目，且正在审核中');window.location.href='method!kemulist2';</script>");
				return null;
			}
			
			
			
			int dengji1 = ManageAction.dengji(kemu.getDengji());//报考的等级
			int dengji2 = ManageAction.dengji(user.getDengji());//已经取得的等级
			if(dengji1-dengji2!=1){
				response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作失败，您已经取得的等级为"+user.getDengji()+",请报考比你等级高一级的科目');window.location.href='method!kemulist2';</script>");
				return null;
			}
			
			request.setAttribute("kaosheng", userDao.selectBean(" where id= "+user.getId()));
			request.setAttribute("kemu", kemu);
			request.setAttribute("list", yuanxiDao.selectBeanList(0, 9999, " where deletestatus=0 "));
			request.setAttribute("url", "method!baokaoadd2");
			request.setAttribute("title", "报考科目");
			this.setUrl("baokao/baokaoadd.jsp");
			return SUCCESS;
		}
	//报考科目操作
		public void baokaoadd2() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();

			String dianhua = request.getParameter("dianhua");
			String kemu = request.getParameter("kemu");
			
			String truename = request.getParameter("truename");
			HttpSession session = request.getSession();
			User u = (User)session.getAttribute("user");
			
			User user = userDao.selectBean(" where id= "+u.getId());
			
		
			Baokao bean = new Baokao();
			bean.setTruename(truename);

			bean.setBianhao(Util.getTime2());
			
			
			bean.setDengji(user.getDengji());
			bean.setDianhua(dianhua);
			bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
			if(uploadfile!=null){
				String savaPath = ServletActionContext.getServletContext().getRealPath(
				"/")
				+ "/uploadfile/";

				String time = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()).toString();

				String path1 = time + ".jpg";
				File file1 = new File(savaPath + path1);
				Util.copyFile(uploadfile, file1);
				bean.setPhoto(path1);
			}else{
				bean.setPhoto(user.getPhoto());
			}
			
			
			bean.setSfz(u.getSfz());
			bean.setShenhe("未审核");
			bean.setT1(Util.getTime());
			bean.setUser(user);
			bean.setYuanxi(user.getYuanxi());
			bean.setZhuanye(user.getZhuanye());
			
			bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
			
			baokaoDao.insertBean(bean);
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!baokaolist';</script>");
			
			
		}
		
		private static int dengji(String str){
			int dengji = 0;
			if("无".equals(str)){
				dengji = 0;
			}else if("一级".equals(str)){
				dengji = 1;
			}else if("二级".equals(str)){
				dengji = 2;
			}else if("三级".equals(str)){
				dengji = 3;
			}else if("四级".equals(str)){
				dengji = 4;
			}
			
			return dengji;
		}
	
		
		
		//查看报名状态
		public String baokaolist() {
			HttpServletRequest request = ServletActionContext.getRequest();
			String bianhao = request.getParameter("bianhao");
			String shenhe = request.getParameter("shenhe");
			String kname = request.getParameter("kname");

			
			StringBuffer sb = new StringBuffer();
			sb.append(" where ");

			if (shenhe != null && !"".equals(shenhe)) {

				sb.append("shenhe like '%" + shenhe + "%'");
				sb.append(" and ");
				request.setAttribute("shenhe", shenhe);
			}
			
			if (bianhao != null && !"".equals(bianhao)) {

				sb.append("bianhao like '%" + bianhao + "%'");
				sb.append(" and ");
				request.setAttribute("bianhao", bianhao);
			}
			
			if (kname != null && !"".equals(kname)) {

				sb.append("kemu.kname like '%" + kname + "%'");
				sb.append(" and ");
				request.setAttribute("kname", kname);
			}
			
			
			
			
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");

			sb.append("  user.id="+user.getId()+" order by id desc ");
			String where = sb.toString();


			int currentpage = 1;
			int pagesize = 15;
			if (request.getParameter("pagenum") != null) {
				currentpage = Integer.parseInt(request.getParameter("pagenum"));
			}
			int total = baokaoDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
			request.setAttribute("list", baokaoDao.selectBeanList((currentpage - 1)
					* pagesize, pagesize, where));
			request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
					currentpage, "method!baokaolist", "共有" + total + "条记录"));
			request.setAttribute("url", "method!baokaolist");
			request.setAttribute("url2", "method!baokao");
			request.setAttribute("title", "查看报名状态");
			this.setUrl("baokao/baokaolist.jsp");
			return SUCCESS;

		}
		
		
		//跳转到查看详情页面
		public String baokaoupdate3() {
			HttpServletRequest request = ServletActionContext.getRequest();
			Baokao bean = baokaoDao.selectBean(" where id= "
					+ request.getParameter("id"));
			request.setAttribute("bean", bean);
			request.setAttribute("title", "查看报名详情");
			this.setUrl("baokao/baokaoupdate3.jsp");
			return SUCCESS;
		}
		
		
		
		
		//跳转到更新个人信息页面
			public String userupdate5() {
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				User bean = userDao.selectBean(" where id= " + user.getId() );
				request.setAttribute("bean", bean);
				request.setAttribute("list", yuanxiDao.selectBeanList(0, 9999, " where deletestatus=0 "));
				request.setAttribute("url", "method!userupdate6?id="+bean.getId());
				request.setAttribute("title", "个人信息修改");
				this.setUrl("user/userupdate5.jsp");
				return SUCCESS;
			}
		//更新个人信息操作
			public void userupdate6() throws IOException {
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
				String dianhua = request.getParameter("dianhua");
				String sfz = request.getParameter("sfz");
				String truename = request.getParameter("truename");
				String yuanxi = request.getParameter("yuanxi");
				String zhuanye = request.getParameter("zhuanye");
				User u = userDao.selectBean(" where id!= "+ request.getParameter("id")+"  and sfz='"+sfz+"' and  deletestatus=0");
				if(u!=null){
					response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，该身份证已经存在');window.location.href='method!userupdate5';</script>");
					return;
				}
				User bean = userDao.selectBean(" where id= "
						+ request.getParameter("id"));
				bean.setDianhua(dianhua);
				bean.setSfz(sfz);
				bean.setTruename(truename);
				if(!"0".equals(yuanxi)){
					bean.setYuanxi(yuanxiDao.selectBean(" where id= "+yuanxi));
					bean.setZhuanye(zhuanyeDao.selectBean(" where id= "+zhuanye));
				}
				
				if(uploadfile!=null){
					String savaPath = ServletActionContext.getServletContext().getRealPath(
					"/")
					+ "/uploadfile/";

					String time = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
					.format(new Date()).toString();

					String path1 = time + ".jpg";
					File file1 = new File(savaPath + path1);
					Util.copyFile(uploadfile, file1);
					bean.setPhoto(path1);
				}
				
				
				userDao.updateBean(bean);
				
				response
						.getWriter()
						.print(
								"<script language=javascript>alert('操作成功');window.location.href='method!userupdate5';</script>");
			}
		
			
			
			//查看本院系报考情况
			public String baokaolist2() {
				HttpServletRequest request = ServletActionContext.getRequest();
				String bianhao = request.getParameter("bianhao");
				String shenhe = request.getParameter("shenhe");
				String kname = request.getParameter("kname");
				String truename = request.getParameter("truename");
				String zhuanye = request.getParameter("zhuanye");
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				List<Zhuanye> zylist = zhuanyeDao.selectBeanList(0, 9999, " where deletestatus=0 and yuanxi.id="+user.getYuanxi().getId());
				request.setAttribute("zylist", zylist);
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (shenhe != null && !"".equals(shenhe)) {

					sb.append("shenhe like '%" + shenhe + "%'");
					sb.append(" and ");
					request.setAttribute("shenhe", shenhe);
				}
				if (zhuanye != null && !"".equals(zhuanye)) {

					sb.append("zhuanye.zname like '%" + zhuanye + "%'");
					sb.append(" and ");
					request.setAttribute("zhuanye", zhuanye);
				}
				
				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				if (kname != null && !"".equals(kname)) {

					sb.append("kemu.kname like '%" + kname + "%'");
					sb.append(" and ");
					request.setAttribute("kname", kname);
				}
				
				if (truename != null && !"".equals(truename)) {

					sb.append("user.truename like '%" + truename + "%'");
					sb.append(" and ");
					request.setAttribute("truename", truename);
				}
				
			

				sb.append("  yuanxi.id="+user.getYuanxi().getId()+" order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 15;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = baokaoDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
				request.setAttribute("list", baokaoDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!baokaolist2", "共有" + total + "条记录"));
				request.setAttribute("url", "method!baokaolist2");
				request.setAttribute("url2", "method!baokao");
				request.setAttribute("title", "查看本院系报考情况");
				this.setUrl("baokao/baokaolist2.jsp");
				return SUCCESS;

			}
			
			
			
			//审核考生上传的照片
			public String baokaolist3() {
				HttpServletRequest request = ServletActionContext.getRequest();
				String bianhao = request.getParameter("bianhao");
				String shenhe = request.getParameter("shenhe");
				String kname = request.getParameter("kname");
				String truename = request.getParameter("truename");
				
				StringBuffer sb = new StringBuffer();
				sb.append(" where ");

				if (shenhe != null && !"".equals(shenhe)) {

					sb.append("shenhe like '%" + shenhe + "%'");
					sb.append(" and ");
					request.setAttribute("shenhe", shenhe);
				}
				
				if (bianhao != null && !"".equals(bianhao)) {

					sb.append("bianhao like '%" + bianhao + "%'");
					sb.append(" and ");
					request.setAttribute("bianhao", bianhao);
				}
				
				if (kname != null && !"".equals(kname)) {

					sb.append("kemu.kname like '%" + kname + "%'");
					sb.append(" and ");
					request.setAttribute("kname", kname);
				}
				
				if (truename != null && !"".equals(truename)) {

					sb.append("user.truename like '%" + truename + "%'");
					sb.append(" and ");
					request.setAttribute("truename", truename);
				}
				
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");

				sb.append("  yuanxi.id="+user.getYuanxi().getId()+" order by id desc ");
				String where = sb.toString();


				int currentpage = 1;
				int pagesize = 15;
				if (request.getParameter("pagenum") != null) {
					currentpage = Integer.parseInt(request.getParameter("pagenum"));
				}
				int total = baokaoDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
				request.setAttribute("list", baokaoDao.selectBeanList((currentpage - 1)
						* pagesize, pagesize, where));
				request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
						currentpage, "method!baokaolist3", "共有" + total + "条记录"));
				request.setAttribute("url", "method!baokaolist3");
				request.setAttribute("url2", "method!baokao");
				request.setAttribute("title", "审核考生上传的照片");
				this.setUrl("baokao/baokaolist3.jsp");
				return SUCCESS;

			}
			
			

			//跳转到照片审核页面
				public String baokaoupdate() {
					HttpServletRequest request = ServletActionContext.getRequest();
					Baokao bean = baokaoDao.selectBean(" where id= "
							+ request.getParameter("id"));
					request.setAttribute("bean", bean);
					request.setAttribute("url", "method!baokaoupdate2?id="+bean.getId());
					request.setAttribute("title", "考生照片审核");
					this.setUrl("baokao/baokaoupdate.jsp");
					return SUCCESS;
				}
			//照片审核操作
				public void baokaoupdate2() throws IOException {
					HttpServletRequest request = ServletActionContext.getRequest();
					String shenhe = request.getParameter("shenhe");
					Baokao bean = baokaoDao.selectBean(" where id= "
							+ request.getParameter("id"));
					bean.setShenhe(shenhe);
					if("完成照片审核".equals(shenhe)){
						bean.setT2(Util.getTime());
					}else{
						bean.setT4(Util.getTime());
					}
					
					baokaoDao.updateBean(bean);
					HttpServletResponse response = ServletActionContext.getResponse();
					response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
					response
							.getWriter()
							.print(
									"<script language=javascript>alert('操作成功');window.location.href='method!baokaolist3';</script>");
				}
				
				
				
				//考生信息列表
				public String userlist5() {
					HttpServletRequest request = ServletActionContext.getRequest();
					String username = request.getParameter("username");
					String truename = request.getParameter("truename");
					String zhuanye = request.getParameter("zhuanye");
					HttpSession session = request.getSession();
					User user = (User)session.getAttribute("user");
					List<Zhuanye> zylist = zhuanyeDao.selectBeanList(0, 9999, " where deletestatus=0 and yuanxi.id="+user.getYuanxi().getId());
					request.setAttribute("zylist", zylist);
					
					StringBuffer sb = new StringBuffer();
					sb.append(" where ");

					if (username != null && !"".equals(username)) {

						sb.append("username like '%" + username + "%'");
						sb.append(" and ");
						request.setAttribute("username", username);
					}
					if (truename != null && !"".equals(truename)) {

						sb.append("truename like '%" + truename + "%'");
						sb.append(" and ");
						request.setAttribute("truename", truename);
					}
					if (zhuanye != null && !"".equals(zhuanye)) {

						sb.append("zhuanye.zname like '%" + zhuanye + "%'");
						sb.append(" and ");
						request.setAttribute("zhuanye", zhuanye);
					}

					
					
					sb.append("  deletestatus=0 and role=3 and yuanxi.id="+user.getYuanxi().getId()+" order by id desc ");
					String where = sb.toString();


					int currentpage = 1;
					int pagesize = 15;
					if (request.getParameter("pagenum") != null) {
						currentpage = Integer.parseInt(request.getParameter("pagenum"));
					}
					int total = userDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
					request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
							* pagesize, pagesize, where));
					request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
							currentpage, "method!userlist5", "共有" + total + "条记录"));
					request.setAttribute("url", "method!userlist5");
					request.setAttribute("url2", "method!user");
					request.setAttribute("title", "考生信息管理");
					this.setUrl("user/userlist5.jsp");
					return SUCCESS;

				}
				
				
				
				//导出excel
				public String userlist6() throws Exception{
					HttpServletRequest request = ServletActionContext.getRequest();
					HttpSession session = request.getSession();
					User user = (User)session.getAttribute("user");
					
					StringBuffer sb = new StringBuffer();
					sb.append(" where ");
					sb.append("  deletestatus=0 and role=3  and yuanxi.id="+user.getYuanxi().getId()+" order by id desc");

					String where = sb.toString();

					int currentpage = 1;
					int pagesize =10000;
					if(request.getParameter("pagenum")!=null){
						currentpage = Integer.parseInt(request.getParameter("pagenum"));
					}

					List<User> list = userDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where);
					HttpServletResponse response = ServletActionContext.getResponse();

					int columnCount = 8;
					
					
					
					response.setContentType("application/vnd.ms-excel");
					String filename ="考生信息表.xls";
					
					response.setHeader("Content-Disposition","attachment; filename=\""+new String(filename.getBytes("gb18030"),"iso8859-1") + "\"");
					//首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象  
					WritableWorkbook wwb = Workbook.createWorkbook(response.getOutputStream());
					//创建一个可写入的工作表    
			        //Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置    
			        WritableSheet ws = wwb.createSheet("sheet1", 0); 
			        //int lie = Integer.parseInt(request.getParameter("lieming"));
					// 获得导出数据的列数
			        String[] title = { "用户名（学号）", "密码","姓名", "身份证","手机", "院系","专业", "已考过等级"};
					//写入字段名
					for (int i = 0; i < columnCount; i++) {
						jxl.write.Label lab = new jxl.write.Label(i,0,title[i]);
						ws.addCell(lab);
					}
					 for(int i=0;i<list.size();i++)  {
						 User  bean =  list.get(i); 

						 	jxl.write.Label lab1 = new jxl.write.Label(0,i+1,bean.getUsername()+"");
						 	
							jxl.write.Label lab2 = new jxl.write.Label(1,i+1,bean.getPassword()+"");
							jxl.write.Label lab3 = new jxl.write.Label(2,i+1,bean.getTruename()+"");
							jxl.write.Label lab4 = new jxl.write.Label(3,i+1,bean.getSfz()+"");
							jxl.write.Label lab5 = new jxl.write.Label(4,i+1,bean.getDianhua()+"");
							jxl.write.Label lab6 = new jxl.write.Label(5,i+1,bean.getYuanxi().getYname()+"");
							jxl.write.Label lab7 = null;
							if(bean.getZhuanye()!=null){
								lab7 = new jxl.write.Label(6,i+1,bean.getZhuanye().getZname()+"");
							}else{
								lab7 = new jxl.write.Label(6,i+1,"");
							}

							jxl.write.Label lab8 = new jxl.write.Label(7,i+1,bean.getDengji()+"");
							


							ws.addCell(lab1);
							ws.addCell(lab2);
							ws.addCell(lab3);
							ws.addCell(lab4);
							ws.addCell(lab5);
							ws.addCell(lab6);
							ws.addCell(lab7);
							ws.addCell(lab8);

					   }
					//从内存中写入文件中    
			        wwb.write();
			        //关闭资源，释放内存    
			        wwb.close(); 
			       // response.getWriter();
				
				return null;
					
				}
				
				
				
				//跳转到导入考生信息页面
				public String useradd5() {
					HttpServletRequest request = ServletActionContext.getRequest();
					HttpSession session = request.getSession();
					User user = (User)session.getAttribute("user");
					request.setAttribute("zhuanyelist", zhuanyeDao.selectBeanList(0, 9999, " where yuanxi.id="+user.getYuanxi().getId()+"  and deletestatus=0 order by id desc"));
					
					request.setAttribute("url", "method!useradd6");
					request.setAttribute("title", "导入考生信息");
					this.setUrl("user/useradd5.jsp");
					return SUCCESS;
				}
				
			

				//excel导入考生信息
				public void useradd6() throws IOException {
					HttpServletRequest request = ServletActionContext.getRequest();
					HttpSession session = request.getSession();
					User user = (User)session.getAttribute("user");
					String zhuanye = request.getParameter("zhuanye");
					int count = 0;
					try {
						Workbook wb = Workbook.getWorkbook(uploadfile);
						Sheet st = wb.getSheet("Sheet1");//得到工作薄中的第一个工作表 
						int row = st.getRows();
						for (int i = 1; i < row; i++) {
							Cell cell0 = st.getCell(0, i);//用户名
							Cell cell1 = st.getCell(1, i);//姓名
							Cell cell2 = st.getCell(2, i);//身份证
							Cell cell3= st.getCell(3, i);//手机
							Cell cell4= st.getCell(4, i);//已考过等级
							String username = cell0.getContents();
							String truename = cell1.getContents();
							String sfz = cell2.getContents();
							String dianhua = cell3.getContents();
							String dengji = cell4.getContents();
							if (null==username )
								continue;
							if (null==truename )
								continue;
							if(truename.length()>4)
								continue;
							if (null==sfz )
								continue;
							String regEx = "^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$"; 
						
							if(!Util.yanzheng(sfz, regEx))
								continue;
							if (null==dianhua )
								continue;
							 regEx = "^0?1[3,4,5,8][0,1,2,3,4,5,6,7,8,9]\\d{8}$"; 
							
							 if(!Util.yanzheng(dianhua, regEx))
									continue;
							if (null==dengji )
								continue;
							
							User bean = userDao.selectBean(" where username='"+username+"' and deletestatus=0   and role=3 ");
							if(bean!=null){
								continue;
							}
							User u = userDao.selectBean(" where  sfz='"+sfz+"' and deletestatus=0");
							if(u!=null){
								continue;
							}
							
							bean = new User();
							bean.setCreatetime(new Date());
							bean.setDengji(dengji);
							bean.setDianhua(dianhua);
							bean.setPassword("111111");
							bean.setSfz(sfz);
							bean.setTruename(truename);
							bean.setUsername(username);
							bean.setYuanxi(yuanxiDao.selectBean(" where id= "+user.getYuanxi().getId()));
							bean.setZhuanye(zhuanyeDao.selectBean(" where id= "+zhuanye));
							bean.setRole(3);		
							userDao.insertBean(bean);
							count++;

						}
						wb.close();
					} catch (Exception e) {
						e.getStackTrace();
					}
						HttpServletResponse response = ServletActionContext.getResponse();
						response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
						response
								.getWriter()
								.print(
										"<script language=javascript>alert('操作成功,成功导入"+count+"条');window.location.href='method!userlist5';</script>");
				}
				
				
				
				//确认考生报名
				public String baokaolist4() {
					HttpServletRequest request = ServletActionContext.getRequest();
					String bianhao = request.getParameter("bianhao");
					String shenhe = request.getParameter("shenhe");
					String kname = request.getParameter("kname");
					String truename = request.getParameter("truename");
					
					StringBuffer sb = new StringBuffer();
					sb.append(" where ");

					if (shenhe != null && !"".equals(shenhe)) {

						sb.append("shenhe like '%" + shenhe + "%'");
						sb.append(" and ");
						request.setAttribute("shenhe", shenhe);
					}
					
					if (bianhao != null && !"".equals(bianhao)) {

						sb.append("bianhao like '%" + bianhao + "%'");
						sb.append(" and ");
						request.setAttribute("bianhao", bianhao);
					}
					
					if (kname != null && !"".equals(kname)) {

						sb.append("kemu.kname like '%" + kname + "%'");
						sb.append(" and ");
						request.setAttribute("kname", kname);
					}
					
					if (truename != null && !"".equals(truename)) {

						sb.append("user.truename like '%" + truename + "%'");
						sb.append(" and ");
						request.setAttribute("truename", truename);
					}
					
				
					HttpSession session = request.getSession();
					User user = (User)session.getAttribute("user");

					sb.append("  yuanxi.id="+user.getYuanxi().getId()+" order by id desc ");
					String where = sb.toString();


					int currentpage = 1;
					int pagesize = 15;
					if (request.getParameter("pagenum") != null) {
						currentpage = Integer.parseInt(request.getParameter("pagenum"));
					}
					int total = baokaoDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
					request.setAttribute("list", baokaoDao.selectBeanList((currentpage - 1)
							* pagesize, pagesize, where));
					request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
							currentpage, "method!baokaolist4", "共有" + total + "条记录"));
					request.setAttribute("url", "method!baokaolist4");
					request.setAttribute("url2", "method!baokao");
					request.setAttribute("title", "确认考生报名");
					this.setUrl("baokao/baokaolist4.jsp");
					return SUCCESS;

				}
				
				

				//跳转到确认考生报名页面
					public String baokaoupdate5() {
						HttpServletRequest request = ServletActionContext.getRequest();
						Baokao bean = baokaoDao.selectBean(" where id= "
								+ request.getParameter("id"));
						request.setAttribute("bean", bean);
						request.setAttribute("url", "method!baokaoupdate6?id="+bean.getId());
						request.setAttribute("title", "确认考生报名");
						this.setUrl("baokao/baokaoupdate5.jsp");
						return SUCCESS;
					}
				//确认考生报名操作
					public void baokaoupdate6() throws IOException {
						HttpServletRequest request = ServletActionContext.getRequest();
						String shenhe = request.getParameter("shenhe");
						Baokao bean = baokaoDao.selectBean(" where id= "
								+ request.getParameter("id"));
						bean.setShenhe(shenhe);
						if("确认报名".equals(shenhe)){
							bean.setT3(Util.getTime());
						}else{
							bean.setT4(Util.getTime());
						}
						
						baokaoDao.updateBean(bean);
						HttpServletResponse response = ServletActionContext.getResponse();
						response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
						response
								.getWriter()
								.print(
										"<script language=javascript>alert('操作成功');window.location.href='method!baokaolist4';</script>");
					}
					
					
					
					//查看报考信息
					public String baokaolist5() {
						HttpServletRequest request = ServletActionContext.getRequest();
						String bianhao = request.getParameter("bianhao");
						String shenhe = request.getParameter("shenhe");
						String kname = request.getParameter("kname");
						String truename = request.getParameter("truename");
						
						StringBuffer sb = new StringBuffer();
						sb.append(" where ");

						if (shenhe != null && !"".equals(shenhe)) {

							sb.append("shenhe like '%" + shenhe + "%'");
							sb.append(" and ");
							request.setAttribute("shenhe", shenhe);
						}
						
						if (bianhao != null && !"".equals(bianhao)) {

							sb.append("bianhao like '%" + bianhao + "%'");
							sb.append(" and ");
							request.setAttribute("bianhao", bianhao);
						}
						
						if (kname != null && !"".equals(kname)) {

							sb.append("kemu.kname like '%" + kname + "%'");
							sb.append(" and ");
							request.setAttribute("kname", kname);
						}
						
						if (truename != null && !"".equals(truename)) {

							sb.append("user.truename like '%" + truename + "%'");
							sb.append(" and ");
							request.setAttribute("truename", truename);
						}
						
					
					

						sb.append("  1=1 order by id desc ");
						String where = sb.toString();


						int currentpage = 1;
						int pagesize = 15;
						if (request.getParameter("pagenum") != null) {
							currentpage = Integer.parseInt(request.getParameter("pagenum"));
						}
						int total = baokaoDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
						request.setAttribute("list", baokaoDao.selectBeanList((currentpage - 1)
								* pagesize, pagesize, where));
						request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
								currentpage, "method!baokaolist5", "共有" + total + "条记录"));
						request.setAttribute("url", "method!baokaolist5");
						request.setAttribute("url2", "method!baokao");
						request.setAttribute("title", "查看报考信息");
						this.setUrl("baokao/baokaolist5.jsp");
						return SUCCESS;

					}
					
					
		private ZhuanyeDao zhuanyeDao;

		public ZhuanyeDao getZhuanyeDao() {
			return zhuanyeDao;
		}

		public void setZhuanyeDao(ZhuanyeDao zhuanyeDao) {
			this.zhuanyeDao = zhuanyeDao;
		}	
		
		
		//专业列表
		public String zhuanyelist() {
			HttpServletRequest request = ServletActionContext.getRequest();
			String yid = request.getParameter("yid");
			String zname = request.getParameter("zname");
			request.setAttribute("yid", yid);
			
			StringBuffer sb = new StringBuffer();
			sb.append(" where ");
			
			if (zname != null && !"".equals(zname)) {

				sb.append("zname like '%" + zname + "%'");
				sb.append(" and ");
				request.setAttribute("zname", zname);
			}
			
			sb.append("  yuanxi.id= "+yid +" and  deletestatus=0 order by id desc ");
			String where = sb.toString();

			int currentpage = 1;
			int pagesize = 15;
			if (request.getParameter("pagenum") != null) {
				currentpage = Integer.parseInt(request.getParameter("pagenum"));
			}
			int total = zhuanyeDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
			request.setAttribute("list", zhuanyeDao.selectBeanList((currentpage - 1)
					* pagesize, pagesize, where));
			request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
					currentpage, "method!zhuanyelist", "共有" + total + "条记录"));
			request.setAttribute("url", "method!zhuanyelist");
			request.setAttribute("url2", "method!zhuanye");
			request.setAttribute("title", "专业管理");
			this.setUrl("zhuanye/zhuanyelist.jsp");
			return SUCCESS;

		}
	//跳转到添加专业页面
		public String zhuanyeadd() {
			HttpServletRequest request = ServletActionContext.getRequest();
			String yid = request.getParameter("yid");
			request.setAttribute("yid", yid);
			request.setAttribute("url", "method!zhuanyeadd2?yid="+yid);
			request.setAttribute("title", "专业添加");
			this.setUrl("zhuanye/zhuanyeadd.jsp");
			return SUCCESS;
		}
	//添加专业操作
		public void zhuanyeadd2() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			String zname = request.getParameter("zname");
			String yid = request.getParameter("yid");

			Zhuanye bean = zhuanyeDao.selectBean(" where zname='"+zname+"' and  deletestatus=0");
			if(bean==null){
				bean = new Zhuanye();
				bean.setZname(zname);
				bean.setYuanxi(yuanxiDao.selectBean("  where id= "+yid));
				zhuanyeDao.insertBean(bean);
				response
						.getWriter()
						.print(
								"<script language=javascript>alert('操作成功');window.location.href='method!zhuanyelist?yid="+yid+"';</script>");
			}else{
				response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作失败，该专业名字已经存在');window.location.href='method!zhuanyelist?yid="+yid+"';</script>");
			}
			
			
			
		}
	//跳转到更新专业页面
		public String zhuanyeupdate() {
			HttpServletRequest request = ServletActionContext.getRequest();
			Zhuanye bean = zhuanyeDao.selectBean(" where id= "
					+ request.getParameter("id"));
			request.setAttribute("bean", bean);
			request.setAttribute("url", "method!zhuanyeupdate2?id="+bean.getId());
			request.setAttribute("title", "专业更新");
			this.setUrl("zhuanye/zhuanyeupdate.jsp");
			return SUCCESS;
		}
	//更新专业操作
		public void zhuanyeupdate2() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			String zname = request.getParameter("zname");
			Zhuanye bean2 = zhuanyeDao.selectBean(" where id= "+ request.getParameter("id"));
			Zhuanye bean = zhuanyeDao.selectBean(" where zname='"+zname+"' and  id!= "+ request.getParameter("id"));
			if(bean!=null){
				response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作失败，该专业名已经存在');window.location.href='method!zhuanyelist?yid="+bean2.getYuanxi().getId()+"';</script>");
			}else{
				bean = zhuanyeDao.selectBean(" where id= "+ request.getParameter("id"));
				bean.setZname(zname);
				
				zhuanyeDao.updateBean(bean);
				
				response
						.getWriter()
						.print(
								"<script language=javascript>alert('操作成功');window.location.href='method!zhuanyelist?yid="+bean2.getYuanxi().getId()+"';</script>");
			}
			
		}
	//删除专业操作
		public void zhuanyedelete() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			Zhuanye bean = zhuanyeDao.selectBean(" where id= "
					+ request.getParameter("id"));
			bean.setDeletestatus(1);
			zhuanyeDao.updateBean(bean);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!zhuanyelist?yid="+bean.getYuanxi().getId()+"';</script>");
		}
		
		
		//获得专业操作
		public String getcate() throws Exception{
	    	HttpServletRequest request = ServletActionContext.getRequest();
	    	HttpServletResponse response = ServletActionContext.getResponse();
	    	response.setContentType("text/xml");
	    	response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
		    response.setCharacterEncoding("UTF-8");
	

	    	String where = " where deletestatus=0 and yuanxi.id= "+request.getParameter("pid");
	    	List<Zhuanye> list = zhuanyeDao.selectBeanList(0, 9999, where);
	    	String xml_start = "<selects>";
	        String xml_end = "</selects>";
	        String xml = "";
	        for(int i=0;i<list.size();i++){
	        	xml+="<select><value>"+list.get(i).getId()+"</value><text>"+list.get(i).getZname()+"</text></select>";
	        }
	        String last_xml = xml_start + xml + xml_end;
	        response.getWriter().write(last_xml);
	        return null;
	    }
		
		
		//跳转到更新考生信息页面
		public String userupdate7() {
			HttpServletRequest request = ServletActionContext.getRequest();
			User bean = userDao.selectBean(" where id= "
					+ request.getParameter("id"));
			request.setAttribute("bean", bean);
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			request.setAttribute("list", zhuanyeDao.selectBeanList(0, 9999, " where deletestatus=0 and yuanxi.id="+user.getYuanxi().getId()));
			request.setAttribute("url", "method!userupdate8?id="+bean.getId());
			request.setAttribute("title", "考生信息更新");
			this.setUrl("user/userupdate7.jsp");
			return SUCCESS;
		}
	//更新考生信息操作
		public void userupdate8() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			String dianhua = request.getParameter("dianhua");
			String sfz = request.getParameter("sfz");
			String truename = request.getParameter("truename");
			String dengji = request.getParameter("dengji");
			String zhuanye = request.getParameter("zhuanye");
			User u = userDao.selectBean(" where id!= "+ request.getParameter("id")+"  and sfz='"+sfz+"' and  deletestatus=0");
			if(u!=null){
				response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作失败，该身份证已经存在');window.location.href='method!userlist5';</script>");
				return;
			}
			User bean = userDao.selectBean(" where id= "
					+ request.getParameter("id"));
			bean.setDianhua(dianhua);
			bean.setSfz(sfz);
			bean.setTruename(truename);
			bean.setDengji(dengji);
			bean.setZhuanye(zhuanyeDao.selectBean(" where id= "+zhuanye));
			
			userDao.updateBean(bean);
			
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!userlist5';</script>");
		}
		
		
		//删除考生信息操作
		public void userdelete7() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			User bean = userDao.selectBean(" where id= "
					+ request.getParameter("id"));
			bean.setDeletestatus(1);
			userDao.updateBean(bean);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!userlist5';</script>");
		}
		
		
		//取消报名操作
		public void baokaodelete() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			Baokao bean = baokaoDao.selectBean(" where id= "
					+ request.getParameter("id"));
			bean.setShenhe("取消报名");
			baokaoDao.updateBean(bean);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!baokaolist';</script>");
		}
		
		
		//跳转到重新审核页面
		public String baokaoupdate11() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			
			String riqi = Util.getRiqi();
			
			Shijian sj = shijianDao.selectBean(" where kaishi<='"+riqi+"' and  '"+riqi+"'<=jieshu ");
			if(sj==null){
				sj = shijianDao.selectBean(" where id=1 ");
				response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作失败，未在报考日期范围内，报考日期为"+sj.getKaishi()+"到"+sj.getJieshu()+"');window.location.href='method!baokaolist';</script>");
				return null;
			}
			
			
			Baokao bean = baokaoDao.selectBean(" where id= "+request.getParameter("id"));
			
			
			request.setAttribute("bean", bean);
			request.setAttribute("url", "method!baokaoupdate22?id="+bean.getId());
			request.setAttribute("title", "重新审核");
			this.setUrl("baokao/baokaoupdate11.jsp");
			return SUCCESS;
		}
	//重新审核操作
		public void baokaoupdate22() throws IOException {
			HttpServletRequest request = ServletActionContext.getRequest();

			String dianhua = request.getParameter("dianhua");
			
			
			String truename = request.getParameter("truename");
	
			
		
			Baokao bean = baokaoDao.selectBean(" where id= "+request.getParameter("id"));
			bean.setTruename(truename);

			bean.setDianhua(dianhua);

			if(uploadfile!=null){
				String savaPath = ServletActionContext.getServletContext().getRealPath(
				"/")
				+ "/uploadfile/";

				String time = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()).toString();

				String path1 = time + ".jpg";
				File file1 = new File(savaPath + path1);
				Util.copyFile(uploadfile, file1);
				bean.setPhoto(path1);
			}

			bean.setShenhe("未审核");
			bean.setT1(Util.getTime());

			baokaoDao.insertBean(bean);
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!baokaolist';</script>");
			
			
		}
			
}
