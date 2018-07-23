package baoming.dao.impl;

import java.sql.SQLException;
import java.util.List;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import baoming.dao.ZhuanyeDao;
import baoming.model.Zhuanye;












public class ZhuanyeDaoImpl extends HibernateDaoSupport implements  ZhuanyeDao{


	public void deleteBean(Zhuanye bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Zhuanye bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Zhuanye selectBean(String where) {
		List<Zhuanye> list = this.getHibernateTemplate().find("from Zhuanye " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Zhuanye "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Zhuanye> selectBeanList(final int start,final int limit,final String where) {
		return (List<Zhuanye>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Zhuanye> list = session.createQuery("from Zhuanye "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Zhuanye bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
