package baoming.dao.impl;

import java.sql.SQLException;
import java.util.List;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import baoming.dao.YuanxiDao;
import baoming.model.Yuanxi;












public class YuanxiDaoImpl extends HibernateDaoSupport implements  YuanxiDao{


	public void deleteBean(Yuanxi bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Yuanxi bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Yuanxi selectBean(String where) {
		List<Yuanxi> list = this.getHibernateTemplate().find("from Yuanxi " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Yuanxi "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Yuanxi> selectBeanList(final int start,final int limit,final String where) {
		return (List<Yuanxi>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Yuanxi> list = session.createQuery("from Yuanxi "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Yuanxi bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
