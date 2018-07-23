package baoming.dao.impl;

import java.sql.SQLException;
import java.util.List;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import baoming.dao.TongzhiDao;
import baoming.model.Tongzhi;












public class TongzhiDaoImpl extends HibernateDaoSupport implements  TongzhiDao{


	public void deleteBean(Tongzhi bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Tongzhi bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Tongzhi selectBean(String where) {
		List<Tongzhi> list = this.getHibernateTemplate().find("from Tongzhi " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Tongzhi "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Tongzhi> selectBeanList(final int start,final int limit,final String where) {
		return (List<Tongzhi>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Tongzhi> list = session.createQuery("from Tongzhi "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Tongzhi bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
