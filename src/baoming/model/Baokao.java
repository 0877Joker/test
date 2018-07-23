package baoming.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//报考记录表
@Entity
@Table(name="t_Baokao")
public class Baokao {

	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	@JoinColumn(name="kemuid")
	private Kemu kemu;//报考科目
	
	private String bianhao;//报考编号
	
	private String shenhe;//审核状态  未审核 完成照片审核  确认报名  照片审核不通过  确认审核不通过
	
	@ManyToOne
	@JoinColumn(name="yuanxiid")
	private Yuanxi yuanxi;//所属院系
	
	@ManyToOne
	@JoinColumn(name="zhuanyeid")
	private Zhuanye zhuanye;//所属专业
	
	private String truename;//考试姓名
	
	private String dianhua;//手机
	
	private String sfz;//身份证
	
	private String dengji;//已考过等级
	
	private String photo;//考生照片
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;//报考考生
	
	private String t1;//报考时间
	
	private String t2;//完成照片审核时间
	
	private String t3;//确认报名时间
	
	private String t4;//审核不通过时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Kemu getKemu() {
		return kemu;
	}

	public void setKemu(Kemu kemu) {
		this.kemu = kemu;
	}

	public String getShenhe() {
		return shenhe;
	}

	public void setShenhe(String shenhe) {
		this.shenhe = shenhe;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getT1() {
		return t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

	public String getT3() {
		return t3;
	}

	public void setT3(String t3) {
		this.t3 = t3;
	}

	public String getT4() {
		return t4;
	}

	public void setT4(String t4) {
		this.t4 = t4;
	}

	public String getBianhao() {
		return bianhao;
	}

	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public Zhuanye getZhuanye() {
		return zhuanye;
	}

	public void setZhuanye(Zhuanye zhuanye) {
		this.zhuanye = zhuanye;
	}
	
	
	
}
