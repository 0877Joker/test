package baoming.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//报名时间
@Entity
@Table(name="t_Shijian")
public class Shijian {

	@Id
	@GeneratedValue
	private int id;
	
	private String kaishi;//报名开始时间
	
	private String jieshu;//报名结束时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKaishi() {
		return kaishi;
	}

	public void setKaishi(String kaishi) {
		this.kaishi = kaishi;
	}

	public String getJieshu() {
		return jieshu;
	}

	public void setJieshu(String jieshu) {
		this.jieshu = jieshu;
	}
	
	

	
	
	
	
}
