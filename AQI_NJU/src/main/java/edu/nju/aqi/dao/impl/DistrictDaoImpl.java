package edu.nju.aqi.dao.impl;

import edu.nju.aqi.dao.DistrictDao;
import edu.nju.aqi.model.District;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lulei on 16/4/21.
 */
public class DistrictDaoImpl implements DistrictDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<District> getRelatedCities(String cityName) {
        String sql = "select t1.id,t1.name,t1.parentid,t1.initial,t1.initials,t1.pinyin,t2.name as parent from district as t1, district as t2 WHERE t1.parentid = (SELECT parentid from district where pinyin='" + cityName + "') and t1.parentid = t2.id";
        Session session = getSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(District.class);
        List<District> districtList = query.list();
        return districtList;
    }

    @Override
    public String getCityProvince(String cityName) {
        String sql = "select name from district where id = (select parentid from district where pinyin ='"+cityName+"')";
        Session session = getSession();
        SQLQuery query = session.createSQLQuery(sql);
        return (String)query.uniqueResult();
    }

    private Session getSession() throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }
}
