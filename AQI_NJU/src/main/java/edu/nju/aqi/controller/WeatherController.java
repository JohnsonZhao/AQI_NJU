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

    /**
     * 预测某个城市的aqi (异步获取,用于city中的图表展示)
     * @param city
     * @return
     */
    @ResponseBody
	@RequestMapping("/forecast")
	public List<AirQuality> getForcast(String city) {
		List<AirQuality> airQualities = analysisService.predict(city);
        return airQualities;
	}

    /**
     * 返回两城市之间aqi的关联程度
     * @param cityName1
     * @param cityName2
     * @return
     */
    @ResponseBody
	@RequestMapping("/correlation")
	public AreaCorrelation getCorrelation(String cityName1, String cityName2){
		AreaCorrelation result = (AreaCorrelation) analysisService.getCorrelation(cityName1, cityName2);
        return result;
//		List<IndexCorrelation> indexCorrelations = analysisService.getCorrelation(cityName1);
//		System.err.println(indexCorrelations.toString());
	}

    /**
     * 返回某个城市各种指数之间的关联
     * @param cityName
     * @return
     */
	@ResponseBody
    @RequestMapping("/indexCorrelation")
    public List<IndexCorrelation> getIndexCorrelation(String cityName) {
        return analysisService.getCorrelation(cityName);
    }
}
