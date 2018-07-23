package baoming.model;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//用户
@Entity
@Table(name="t_User")
public class User {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	private String username;
	
	private String password;
	
	private Date createtime;

	private int role;//1表示系统管理员,2表示院系管理员,3表示考生
	
	private String truename;//姓名
	
	@ManyToOne
	@JoinColumn(name="yuanxiid")
	private Yuanxi yuanxi;//所属院系
	
	@ManyToOne
	@JoinColumn(name="zhuanyeid")
	private Zhuanye zhuanye;//所属专业
	
	private String dianhua;//手机
	
	private String sfz;//身份证
	
	private String dengji;//已考过等级
	
	private String photo;//照片
	

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeletestatus() {
		return deletestatus;
	}

	public void setDeletestatus(int deletestatus) {
		this.deletestatus = deletestatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public Yuanxi getYuanxi() {
		return yuanxi;
	}

	public void setYuanxi(Yuanxi yuanxi) {
		this.yuanxi = yuanxi;
	}

	public String getDianhua() {
		return dianhua;
	}

	public void setDianhua(String dianhua) {
		this.dianhua = dianhua;
	}

	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getDengji() {
		return dengji;
	}

	public void setDengji(String dengji) {
		this.dengji = dengji;
	}

	public Zhuanye getZhuanye() {
		return zhuanye;
	}

	public void setZhuanye(Zhuanye zhuanye) {
		this.zhuanye = zhuanye;
	}
	

	
	
	
	

	

	

	


	
	
	
	
}
