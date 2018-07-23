package baoming.dao;

import java.util.List;

import baoming.model.Shijian;





public interface ShijianDao  {
	
	
	
	public void insertBean(Shijian bean);
	
	public void deleteBean(Shijian bean);
	
	public void updateBean(Shijian bean);

	public Shijian selectBean(String where);
	
	public List<Shijian> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
