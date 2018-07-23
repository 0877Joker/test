package baoming.dao.impl;

import java.sql.SQLException;
import java.util.List;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import baoming.dao.BaokaoDao;
import baoming.model.Baokao;












public class BaokaoDaoImpl extends HibernateDaoSupport implements  BaokaoDao{


	public void deleteBean(Baokao bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Baokao bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Baokao selectBean(String where) {
		List<Baokao> list = this.getHibernateTemplate().find("from Baokao " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Baokao "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Baokao> selectBeanList(final int start,final int limit,final String where) {
		return (List<Baokao>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Baokao> list = session.createQuery("from Baokao "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Baokao bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
