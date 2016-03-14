package edu.nju.aqi.quartz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.nju.aqi.meta.CityUtils;
import edu.nju.aqi.meta.IndexTypeUtils;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.model.MonitoringSites;
import edu.nju.aqi.service.AirQualityService;
import edu.nju.aqi.service.MonitoringSitesService;

public class SchedulerJob {

	private AirQualityService airQualityService;
	private MonitoringSitesService monitoringSitesService;

	public AirQualityService getAirQualityService() {
		return airQualityService;
	}

	public void setAirQualityService(AirQualityService airQualityService) {
		this.airQualityService = airQualityService;
	}

	public MonitoringSitesService getMonitoringSitesService() {
		return monitoringSitesService;
	}

	public void setMonitoringSitesService(
			MonitoringSitesService monitoringSitesService) {
		this.monitoringSitesService = monitoringSitesService;
	}

	/*
	 * public String getCity() { return city; } public void setCity(String city)
	 * { this.city = city; }
	 */

	public void work() {
		List<String> cities = CityUtils.getCities();
		int i=1;
		for(String city:cities){
			crawlerCity(city);
			System.out.println("finish "+ (i++)+" cities!");
		}
	}

	private boolean crawlerCity(String city) {
		Document doc;
		try {
			doc = Jsoup.connect("http://www.pm25.in/" + city).get();
			String live_data_time = doc.select("div.live_data_time").text();
			String dateStr = live_data_time.substring(live_data_time
					.indexOf("£º") + 1);
			String date = dateStr.split(" ")[0];
			String time = dateStr.split(" ")[1];
			date = date.split("-")[0] + "_" + date.split("-")[1] + "_"
					+ date.split("-")[2];
			time = date + "_" + time.split(":")[0];
			String prim_pollu = doc
					.select("div.primary_pollutant")
					.text()
					.substring(
							doc.select("div.primary_pollutant").text()
									.indexOf("£º") + 1);
			String cur_AQI = doc.select("div.span1").get(0).text().split(" ")[0];
			String cur_pm25 = doc.select("div.span1").get(1).text().split(" ")[0];
			String cur_pm10 = doc.select("div.span1").get(2).text().split(" ")[0];
			String cur_co = doc.select("div.span1").get(3).text().split(" ")[0];
			String cur_no2 = doc.select("div.span1").get(4).text().split(" ")[0];
			String cur_o3 = doc.select("div.span1").get(5).text().split(" ")[0];
			// String cur_o38 = doc.select("div.span1").get(6).text().split(" ")[0];
			String cur_so2 = doc.select("div.span1").get(7).text().split(" ")[0];

			AirQuality airQuality = new AirQuality();
			airQuality.setId(city + "_" + time);
			airQuality.setAqi(cur_AQI);
			airQuality.setIndex_type(IndexTypeUtils.indexType(cur_AQI));
			airQuality.setPrim_pollu(prim_pollu);
			airQuality.setPm25(cur_pm25);
			airQuality.setPm10(cur_pm10);
			airQuality.setCo(cur_co);
			airQuality.setNo2(cur_no2);
			airQuality.setO3(cur_o3);
			airQuality.setSo2(cur_so2);

			airQualityService.addAirQuality(airQuality);

			Elements sites = doc.select("tbody > tr");
			int size = sites.size();
			System.out.println("site size: "+size);
			List<MonitoringSites> siteList = new ArrayList<MonitoringSites>();
			for (int i = 0; i < size; i++) {
				Element site = sites.get(i);
				Elements content = site.getElementsByTag("td");
				String siteId = city + "_" + time + "_" + i;
				String name = content.get(0).text().trim();
				String AQI = content.get(1).text().trim();
				String type = content.get(2).text().trim();
				String prim = content.get(3).text().trim();
				String pm25 = content.get(4).text().trim();
				String pm10 = content.get(5).text().trim();
				String co = content.get(6).text().trim();
				String no2 = content.get(7).text().trim();
				String o3 = content.get(8).text().trim();
				String so2 = content.get(10).text().trim();

				MonitoringSites mosite = new MonitoringSites();
				mosite.setId(siteId);
				mosite.setName(name);
				mosite.setAqi(AQI);
				mosite.setIndex_type(type);
				mosite.setPrim_pollu(prim);
				mosite.setPm25(pm25);
				mosite.setPm10(pm10);
				mosite.setCo(co);
				mosite.setNo2(no2);
				mosite.setO3(o3);
				mosite.setSo2(so2);
				siteList.add(mosite);
			}
			System.out.println("siteList size: "+siteList.size());
			monitoringSitesService.addMonitoringSiteList(siteList);

		} catch (HttpStatusException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
