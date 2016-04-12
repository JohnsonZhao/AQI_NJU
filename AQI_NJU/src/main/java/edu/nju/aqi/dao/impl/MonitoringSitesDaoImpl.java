package edu.nju.aqi.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.hibernate.cfg.Configuration;

import edu.nju.aqi.dao.MonitoringSitesDao;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.model.MonitoringSites;

public class MonitoringSitesDaoImpl implements MonitoringSitesDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * public SessionFactory getSessionFactory() { return this.sessionFactory; }
	 */

	@Override
	public boolean addMonitoringSite(MonitoringSites sites) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			/*
			 * Configuration config = new Configuration().configure();
			 * 
			 * @SuppressWarnings("deprecation") SessionFactory sessionFactory =
			 * config.buildSessionFactory(); Session
			 * session=sessionFactory.openSession(); Transaction
			 * tx=session.beginTransaction(); session.saveOrUpdate(sites);
			 * tx.commit(); session.close(); sessionFactory.close();
			 */
			tx = session.beginTransaction();
			session.saveOrUpdate(sites);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

	@Override
	public boolean addMonitoringSiteList(List<MonitoringSites> siteList) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			/*
			 * Configuration config = new Configuration().configure();
			 * 
			 * @SuppressWarnings("deprecation") SessionFactory sessionFactory =
			 * config.buildSessionFactory(); Session
			 * session=sessionFactory.openSession(); Transaction
			 * tx=session.beginTransaction(); for (MonitoringSites site :
			 * siteList) { session.saveOrUpdate(site); } tx.commit();
			 * session.close(); sessionFactory.close();
			 */
			for (MonitoringSites site : siteList) {
				tx = session.beginTransaction();
				session.saveOrUpdate(site);
				tx.commit();
				// sessionFactory.getCurrentSession().saveOrUpdate(site);
				// session.flush();
			}
			System.out.println("Add MonitoringSiteList ok!");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MonitoringSites> getCurrentMonitoringSites(String city) {
		// get corresponding id
		String hql = "FROM AirQuality as aq where aq.date = (select max(date) from AirQuality) and aq.id like '"
				+ city + "%'";
		Session session = getSession();
		Query query = session.createQuery(hql);
		List<AirQuality> list = query.list();
		/*
		 * String dateStr = DateUtils.getDateStr(); String hql =
		 * "from AirQuality as aq where aq.id like '"
		 * +city+"_"+dateStr+"_"+"%' order by aq.id desc"; Session session =
		 * getSession(); Query query = session.createQuery(hql);
		 * List<AirQuality> list = query.list();
		 */
		if (list.size() > 0) {
			AirQuality aq = (AirQuality)list.get(0);
			String id = aq.getId();
			String hql2 = "from MonitoringSites as ms where ms.id like '" + id
					+ "_" + "%'";
			Query query2 = session.createQuery(hql2);
			List<MonitoringSites> list2 = query2.list();
			return list2;
		}
		return null;
	}

	private Session getSession() throws HibernateException {
		Session sess = sessionFactory.getCurrentSession();
		if (sess == null) {
			sess = sessionFactory.openSession();
		}
		return sess;
	}

}
