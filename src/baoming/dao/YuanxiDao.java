package baoming.dao;

import java.util.List;

import baoming.model.Yuanxi;





public interface YuanxiDao  {
	
	
	
	public void insertBean(Yuanxi bean);
	
	public void deleteBean(Yuanxi bean);
	
	public void updateBean(Yuanxi bean);

	public Yuanxi selectBean(String where);
	
	public List<Yuanxi> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
