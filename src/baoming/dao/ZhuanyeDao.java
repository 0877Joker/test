package baoming.dao;

import java.util.List;

import baoming.model.Zhuanye;





public interface ZhuanyeDao  {
	
	
	
	public void insertBean(Zhuanye bean);
	
	public void deleteBean(Zhuanye bean);
	
	public void updateBean(Zhuanye bean);

	public Zhuanye selectBean(String where);
	
	public List<Zhuanye> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
