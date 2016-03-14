package edu.nju.aqi.service;

import java.util.List;

import edu.nju.aqi.dao.MonitoringSitesDao;
import edu.nju.aqi.model.MonitoringSites;

public interface MonitoringSitesService {

	public MonitoringSitesDao getMonitoringSitesDao();
	
	public void setMonitoringSitesDao(MonitoringSitesDao monitoringSitesDao);
	
	public boolean addMonitoringSites(MonitoringSites monitoringSites);
	
	public boolean addMonitoringSiteList(List<MonitoringSites> monitoringSiteList);
	
	public List<MonitoringSites> getCurrentMonitoringSites(String city);
	
}
