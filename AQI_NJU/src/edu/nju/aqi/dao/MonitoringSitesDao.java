package edu.nju.aqi.dao;

import java.util.List;

import edu.nju.aqi.model.MonitoringSites;

public interface MonitoringSitesDao {

	public boolean addMonitoringSite(MonitoringSites sites);
	
	public boolean addMonitoringSiteList(List<MonitoringSites> siteList);
	
	public List<MonitoringSites> getCurrentMonitoringSites(String city);
}
