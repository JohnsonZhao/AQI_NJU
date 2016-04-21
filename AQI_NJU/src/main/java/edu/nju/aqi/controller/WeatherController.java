package edu.nju.aqi.controller;

import edu.nju.aqi.analysis.helper.AreaCorrelation;
import edu.nju.aqi.analysis.helper.IndexCorrelation;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.model.Weather;
import edu.nju.aqi.service.AnalysisService;
import edu.nju.aqi.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class WeatherController {
	@Autowired
	private WeatherService weatherService;
	@Autowired
	private AnalysisService analysisService;

	@ResponseBody
	@RequestMapping("/getCurrentWeather")
	public Weather getCurrentMonitoringSites(String city, HttpServletRequest request) {
		request.setAttribute("weather", weatherService.getCurrentWeather("abazhou"));
		return weatherService.getCurrentWeather(city);
	}

    @ResponseBody
	@RequestMapping("/forecast")
	public List<AirQuality> getForcast(String city) {
		List<AirQuality> airQualities = analysisService.predict(city);
        return airQualities;
	}
	
	@RequestMapping("/correlation")
	public String getCorrelation(String cityName1, String cityName2, HttpServletRequest request){
		String name1 = "baoji";
		String name2 = "baoji";
		AreaCorrelation result = (AreaCorrelation) analysisService.getCorrelation(name1, name2);
		System.out.println(result.toString());
		List<IndexCorrelation> indexCorrelations = analysisService.getCorrelation(name1);
		System.err.println(indexCorrelations.toString());
		return "/login";
	}
}
