package edu.nju.aqi.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.nju.aqi.analysis.impl.WeatherFactory.WeatherType;
import edu.nju.aqi.dao.WeatherDao;
import edu.nju.aqi.meta.DateUtils;
import edu.nju.aqi.model.Weather;
import edu.nju.aqi.model.WeatherVoAirQuality;

@Transactional
@Component("WeatherManager")
public class WeatherDaoImpl implements WeatherDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean addWeather(Weather weather) {
		if (weather == null) {
			System.out.println("add failed cause weather is null!");
			return false;
		}
		Session session = getSession();
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
			session.saveOrUpdate(weather);
			tx.commit();
			System.out.println("Add weather ok!");
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
	public boolean addWeatherList(List<Weather> weathers) {
		Session session = getSession();
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
			for (Weather weather : weathers) {
				session.saveOrUpdate(weather);
			}
			tx.commit();
			System.out.println("Add weathers ok!");

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
	public List<Weather> getHistoryWeathers(Date startTime, Date endTime) {
		java.sql.Date startDate = new java.sql.Date(startTime.getTime());
		java.sql.Date endDate = new java.sql.Date(endTime.getTime());
		String hql = "from Weather as w where w.time between unix_timestamp('" + startDate + "') and unix_timestamp('"
				+ endDate + "')";
		Session session = getSession();
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Weather> weathers = query.list();
		return weathers;
	}

	@Override
	public List<Weather> getHistoryWeather(String cityName, String startTime, String endTime) {
		String hql = "from Weather as w where w.cityName= " + cityName + " and w.date between '"
				+ startTime + "' and '" + endTime + "')";
		Session session = getSession();
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Weather> weathers = query.list();
		return weathers;
	}

	@Override
	public List<Weather> getCurrentWeathers() {
		String currentDate = DateUtils.getDateStr("yyyy-MM-dd HH:mm:ss");
		String sql = "select w.* from weather w where TIMESTAMPDIFF(HOUR, w.date, '" + currentDate
				+ "') < 1";
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Weather.class);
		@SuppressWarnings("unchecked")
		List<Weather> list = query.list();
		return list;
	}

	@Override
	public Weather getCurrentWeather(String cityName) {
		String currentDate = DateUtils.getDateStr("yyyy-MM-dd HH:mm:ss");
		String sql = "select w.* from weather w where w.cityName='" + cityName
				+ "' and TIMESTAMPDIFF(HOUR, w.date, '" + currentDate + "') <= 1";
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setMaxResults(1);
		query.addEntity(Weather.class);
		Weather weather = (Weather) query.uniqueResult();
		return weather;
	}

	@Override
	public List<Weather> getForcastWeather(String cityName) {
		String hql = "from Weather as w where w.cityName=" + cityName + " and w.type="
				+ WeatherType.TYPE_FORCASR.getDesc();
		Session session = getSession();
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Weather> weathers = query.list();
		return weathers;
	}

	@Override
	public List<WeatherVoAirQuality> getWeatherVoAirQuality(String cityName) {
		String hql = "select new edu.nju.aqi.model.WeatherVoAirQualtiy(a.city_name, a.date, w.temp, w.pressure, w.humidity, w.clouds, w.rain, a.aqi, a.pm25, a.pm10, a.co, a.no2, a.o3, a.so2) from weather w, air_quality where w.qualityId = a.id and a.cityName = "
				+ cityName;
		String sql = "select a.city_name as cityName, a.date as date, a.aqi as aqi, w.temp as temp, w.pressure as pressure, w.humidity as humidity, w.clouds as clouds, w.windSpeed as windSpeed, a.pm25 as pm25, a.pm10 as pm10, a.co as co, a.o3 as o3, a.so2 as so2 from weather w, air_quality a where w.qualityId=a.id and w.cityName = '"
				+ cityName + "'";
		Session session = getSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(edu.nju.aqi.model.WeatherVoAirQuality.class));
		@SuppressWarnings("unchecked")
		List<WeatherVoAirQuality> result = query.list();
		return result;
	}

	@Override
	public List<WeatherVoAirQuality> getWeatherVoAirQuality() {
		String hql = "select new edu.nju.aqi.model.WeatherVoAirQualtiy(a.city_name, a.date, w.temp, w.pressure, w.huimidity, w.clouds, w.rain, a.aqi, a.pm25, a.pm10, a.co, a.no2, a.o3, a.so2) from weather w, air_quality where w.qualityId = a.id";
		String sql = "select a.city_name as cityName, a.date as date, a.aqi as aqi, w.temp as temp, w.pressure as pressure, w.humidity as humidity, w.clouds as clouds, w.windSpeed as windSpeed, a.pm25 as pm25, a.pm10 as pm10, a.co as co, a.o3 as o3, a.so2 as so2 from weather w, air_quality a where w.qualityId=a.id";
		
		Session session = getSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(edu.nju.aqi.model.WeatherVoAirQuality.class));
		@SuppressWarnings("unchecked")
		List<WeatherVoAirQuality> result = query.list();
		return result;
	}

	private Session getSession() {
		Session sess = null;
		try {
			sess = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
		}
		if (sess == null) {
			sess = sessionFactory.openSession();
		}
		return sess;
	}

}
