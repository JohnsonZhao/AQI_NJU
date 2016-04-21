package edu.nju.aqi.dao.impl;

import edu.nju.aqi.dao.AirQualityDao;
import edu.nju.aqi.meta.DateUtils;
import edu.nju.aqi.model.AirQuality;
import org.hibernate.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
        String dateStr = DateUtils.getDayStr();
        //System.out.println(dateStr);
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
        String dateStr = DateUtils.getDayStr();
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
        //String dateStr = DateUtils.getDateStr();
		//AirQuality result = (AirQuality) getSession().createCriteria(AirQuality.class).addOrder(Order.desc("id")).add(Restrictions.like("id", "'"+city+"_"+dateStr+"_"+"%'")).setMaxResults(1).uniqueResult();
        //String hql = "from AirQuality as aq where aq.id like '" + city + "_" + dateStr + "_" + "%' order by aq.id desc";
    	String hql = "FROM AirQuality as aq where aq.date = (select max(date) from AirQuality) and aq.id like '"
				+ city + "%'";
		Session session = getSession();
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<AirQuality> list = query.list();
		if(list.size()>0)
			return (AirQuality)list.get(0);
		return null;
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
        @SuppressWarnings("unchecked")
		List<AirQuality> list = query.list();
        System.out.println(list);
        return list;
    }

    @Override
    /**
     * get aqi by city name list
     */
    public List<AirQuality> getAirQualityByNameList(List<String> nameList) {
        Session session = getSession();
        Query query = session.createQuery("from AirQuality as aq where city_name in (:nameList) and aq.date= (select max(date) from AirQuality)");
        query.setParameterList("nameList", nameList);
        return  query.list();
    }

    @Override
	public List<AirQuality> get24HoursAirQuality(String city) {
		String sql = "SELECT * FROM air_quality WHERE date > DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 DAY),'%Y_%m_%d_%H') and id LIKE '"+city+"%';";
		Session session = getSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(AirQuality.class);
        @SuppressWarnings("unchecked")
		List<AirQuality> list = query.list();
        System.out.println(list);
        return list;
	}

	@Override
	public List<AirQuality> getPastHoursAirQuality(String city, int hourNum) {
		String sql = "SELECT * FROM air_quality WHERE date > DATE_FORMAT(DATE_SUB(NOW(), INTERVAL "+hourNum+" HOUR),'%Y_%m_%d_%H') and id LIKE '"+city+"%';";
		Session session = getSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(AirQuality.class);
        @SuppressWarnings("unchecked")
		List<AirQuality> list = query.list();
        System.out.println(list);
        return list;
	}

	@Override
	public AirQuality getCurrentAirQualityByChinese(String city) {
		HashMap<String,String> map = getNameMap();
		//System.out.println("==============="+map.size()+"==============");
		String realCity = "";
		if(map.get(city)!=null&&!map.get(city).equals(""))
			realCity = map.get(city);
		else
			realCity = city;
		return getCurrentAirQuality(realCity);
	}
	
	private HashMap<String,String> getNameMap(){
		HashMap<String,String> map = new HashMap<String,String>();
		String encoding = "utf-8";
		String path = this.getClass().getClassLoader().getResource("/datasource/cities.txt").getPath();
		File file = new File(path);
		InputStreamReader read;
		try {
			read = new InputStreamReader(new FileInputStream(file),encoding);
			BufferedReader reader = new BufferedReader(read);
			String lineTxt = "";
			while((lineTxt = reader.readLine())!=null){
				map.put(lineTxt.split(" ")[1], lineTxt.split(" ")[0]);
			}
			read.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}

	@Override
	public List<AirQuality> getPastDaysAirQuality(String city, int dayNum) {
		int hourNum = dayNum*24;
		String sql = "SELECT *, SUBSTRING(date,1,10) as dayStr,AVG(aqi) as avgAqi FROM air_quality WHERE date > DATE_FORMAT(DATE_SUB(NOW(), INTERVAL "+hourNum+" HOUR),'%Y_%m_%d_%H') and id LIKE '"+city+"%' GROUP BY dayStr ORDER BY dayStr ASC;";
        Session session = getSession();
        SQLQuery query = session.createSQLQuery(sql);
        //query.setResultTransformer(Transformers.aliasToBean(DayAqiModel.class));
        @SuppressWarnings("unchecked")
        List<Object[]> list = query.list();

        List<AirQuality> resultList = new ArrayList<>();
        for (Object[] ob: list) {
            String cityName = ob[2].toString();
            String co = ob[3].toString();
            String indexType = ob[5].toString();
            String no2 = ob[6].toString();
            String o3 = ob[7].toString();
            String pm10 = ob[8].toString();
            String pm25 = ob[9].toString();
            String primPollu = ob[10].toString();
            String so2 = ob[11].toString();
            String date = ob[12].toString();
            String aqi = String.valueOf(Math.rint((Double)ob[13]));

            AirQuality airQuality = new AirQuality();
            airQuality.setAqi(aqi);
            airQuality.setDate(date);
            airQuality.setCity_name(cityName);
            airQuality.setCo(co);
            airQuality.setPm25(pm25);
            airQuality.setIndex_type(indexType);
            airQuality.setNo2(no2);
            airQuality.setO3(o3);
            airQuality.setPm10(pm10);
            airQuality.setPrim_pollu(primPollu);
            airQuality.setSo2(so2);

            resultList.add(airQuality);
        }
//		HashMap<String,String> map = new HashMap<String,String>();
//        for(Object[] ob:list){
//        	String day = ob[0].toString();
//        	String aqi = ob[1].toString();
//        	map.put(day, aqi);
//        }
//        return map;
        return resultList;
	}
}
