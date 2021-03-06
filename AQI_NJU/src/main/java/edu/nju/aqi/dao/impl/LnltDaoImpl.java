package edu.nju.aqi.dao.impl;

import edu.nju.aqi.dao.LnltDao;
import edu.nju.aqi.model.Lnlt;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lulei on 16/3/17.
 */
public class LnltDaoImpl implements LnltDao{
    @Autowired
    private SessionFactory sessionFactory;
    

    @Override
    public List<Lnlt> getAllLnlt() {
        String sql = "select lnlt.* from lnlt";
        Session session = getSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Lnlt.class);
        List<Lnlt> lnltList = query.list();
        return lnltList;
    }


    private Session getSession() throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }
}
