package baoming.dao.impl;

import java.sql.SQLException;
import java.util.List;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import baoming.dao.ShijianDao;
import baoming.model.Shijian;












public class ShijianDaoImpl extends HibernateDaoSupport implements  ShijianDao{


	public void deleteBean(Shijian bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Shijian bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Shijian selectBean(String where) {
		List<Shijian> list = this.getHibernateTemplate().find("from Shijian " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Shijian "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Shijian> selectBeanList(final int start,final int limit,final String where) {
		return (List<Shijian>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Shijian> list = session.createQuery("from Shijian "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Shijian bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
