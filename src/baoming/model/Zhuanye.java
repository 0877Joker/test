package baoming.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//专业
@Entity
@Table(name="t_Zhuanye")
public class Zhuanye {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	@ManyToOne
	@JoinColumn(name="yuanxiid")
	private Yuanxi yuanxi;
	
	private String zname;//专业名

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

	public Yuanxi getYuanxi() {
		return yuanxi;
	}

	public void setYuanxi(Yuanxi yuanxi) {
		this.yuanxi = yuanxi;
	}

	public String getZname() {
		return zname;
	}

	public void setZname(String zname) {
		this.zname = zname;
	}

	
	
	
	
	
}
