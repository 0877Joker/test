package baoming.dao;

import java.util.List;

import baoming.model.Tongzhi;





public interface TongzhiDao  {
	
	
	
	public void insertBean(Tongzhi bean);
	
	public void deleteBean(Tongzhi bean);
	
	public void updateBean(Tongzhi bean);

	public Tongzhi selectBean(String where);
	
	public List<Tongzhi> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
