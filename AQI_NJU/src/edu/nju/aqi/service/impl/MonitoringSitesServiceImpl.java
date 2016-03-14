package edu.nju.aqi.service.impl;

import java.util.List;

import edu.nju.aqi.dao.MonitoringSitesDao;
import edu.nju.aqi.model.MonitoringSites;
import edu.nju.aqi.service.MonitoringSitesService;

public class MonitoringSitesServiceImpl implements MonitoringSitesService {

	private MonitoringSitesDao monitoringSitesDao;
	
	@Override
	public boolean addMonitoringSites(MonitoringSites monitoringSites) {
		return monitoringSitesDao.addMonitoringSite(monitoringSites);
	}
	
	public MonitoringSitesDao getMonitoringSitesDao() {
		return monitoringSitesDao;
	}
	
	public void setMonitoringSitesDao(MonitoringSitesDao monitoringSitesDao) {
		this.monitoringSitesDao = monitoringSitesDao;
	}

	@Override
	public boolean addMonitoringSiteList(
			List<MonitoringSites> monitoringSiteList) {
		return monitoringSitesDao.addMonitoringSiteList(monitoringSiteList);
	}

	@Override
	public List<MonitoringSites> getCurrentMonitoringSites(String city) {
		return monitoringSitesDao.getCurrentMonitoringSites(city);
	}

}
