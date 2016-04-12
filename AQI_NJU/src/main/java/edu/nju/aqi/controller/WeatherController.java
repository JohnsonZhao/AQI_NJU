package edu.nju.aqi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.service.AnalysisService;
import edu.nju.aqi.service.WeatherService;

@Controller
@RequestMapping("/Weather")
public class WeatherController {
	@Autowired
	private WeatherService weatherService;
	@Autowired
	private AnalysisService analysisService;

	@RequestMapping("/getCurrentWeather")
	public String getCurrentMonitoringSites(String city, HttpServletRequest request) {
		request.setAttribute("weather", weatherService.getCurrentWeather("abazhou"));
		return "/login";
	}

	@RequestMapping("/forecast")
	public String getForcast(String cityName, HttpServletRequest request) {
		List<AirQuality> airQualities = analysisService.predict("abazhou");
		if (airQualities != null) {

			for (AirQuality airQuality : airQualities) {
				System.out.println(airQuality.toString());
			}
		}
		return "/login";
	}
	
	@RequestMapping("/correlation")
	public String getCorrelation(String cityName1, String cityName2, HttpServletRequest request){
		String name1 = "baoji";
		String name2 = "baoji";
		String result = analysisService.getCorrelation(name1, name2);
		System.out.println(result);
		return "/login";
	}
}
