package baoming.dao;

import java.util.List;

import baoming.model.Baokao;





public interface BaokaoDao  {
	
	
	
	public void insertBean(Baokao bean);
	
	public void deleteBean(Baokao bean);
	
	public void updateBean(Baokao bean);

	public Baokao selectBean(String where);
	
	public List<Baokao> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
