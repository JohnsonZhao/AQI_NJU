package edu.nju.aqi.dao.impl;

import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.meta.DateUtils;
import edu.nju.aqi.model.AirQuality;
import org.hibernate.*;

import java.util.List;

//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.Configuration;

public class AirQualityDaoImpl implements AirQualityDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	/*
     * public SessionFactory getSessionFactory() { return this.sessionFactory; }
	 */

    @SuppressWarnings("unchecked")
    @Override
    public List<AirQuality> getAllAirQuality() {
        String hql = "from AirQuality";
        Session session = getSession();
        Query query = session.createQuery(hql);
        System.out.println(query.list().size());
        List<AirQuality> list = query.list();
        //session.close();
        return list;
    }

    @Override
    public boolean addAirQuality(AirQuality airQuality) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            /*
			 * Configuration config = new Configuration().configure();
			 * 
			 * @SuppressWarnings("deprecation") SessionFactory sessionFactory =
			 * config.buildSessionFactory(); Session
			 * session=sessionFactory.openSession(); Transaction
			 * tx=session.beginTransaction(); session.saveOrUpdate(airQuality);
			 * tx.commit(); session.close(); sessionFactory.close();
			 */
            tx = session.beginTransaction();
            session.saveOrUpdate(airQuality);
            tx.commit();
            // sessionFactory.getCurrentSession().saveOrUpdate(airQuality);
            System.out.println("Add airQuality ok!");
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean addAirQualityList(List<AirQuality> airQualityList) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (AirQuality airQuality : airQualityList) {
                session.saveOrUpdate(airQuality);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }


    private Session getSession() throws HibernateException {
        Session sess = sessionFactory.getCurrentSession();
        if (sess == null) {
            sess = sessionFactory.openSession();
        }
        return sess;
    }

    @Override
    public List<AirQuality> getTodaysAirQuality(String city) {
        String dateStr = DateUtils.getDateStr();
        String hql = "from AirQuality as aq where aq.id like '" + city + "_" + dateStr + "_" + "%'";
        Session session = getSession();
        Query query = session.createQuery(hql);
        @SuppressWarnings("unchecked")
        List<AirQuality> list = query.list();
        //session.close();
        return list;
    }

    @Override
    public List<AirQuality> getAllTodaysAirQuality() {
        String dateStr = DateUtils.getDateStr();
        String hql = "from AirQuality as aq where aq.id like '" + "%_" + dateStr + "_" + "%' order by aq.id desc";
        Session session = getSession();
        Query query = session.createQuery(hql);
        @SuppressWarnings("unchecked")
        List<AirQuality> list = query.list();
        //session.close();
        return list;
    }

    @Override
    public AirQuality getCurrentAirQuality(String city) {
        String dateStr = DateUtils.getDateStr();
//		AirQuality result = (AirQuality) getSession().createCriteria(AirQuality.class).addOrder(Order.desc("id")).add(Restrictions.like("id", "'"+city+"_"+dateStr+"_"+"%'")).setMaxResults(1).uniqueResult();
        String hql = "from AirQuality as aq where aq.id like '" + city + "_" + dateStr + "_" + "%' order by aq.id desc";
        Session session = getSession();
        Query query = session.createQuery(hql);
        @SuppressWarnings("unchecked")
        List<AirQuality> list = query.list();
        //session.close();
        return list.get(0);
    }

    @Override
    /**
     * select aq1.*
     from air_quality aq1 left join air_quality aq2
     on
     (aq1.city_name = aq2.city_name And aq1.id<aq2.id)
     where aq2.id is null
     */
    public List<AirQuality> getAllCurrentAirQuality() {
        String sql = "SELECT * FROM air_quality where date = (select max(date) from sap.air_quality)";
        Session session = getSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(AirQuality.class);
        List<AirQuality> list = query.list();
        System.out.println(list);
        return list;
    }
}
