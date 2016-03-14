package edu.nju.aqi.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.nju.aqi.service.AirQualityService;

@Controller
@RequestMapping("/AirQuality")
public class AirQualityController {

	@Resource(name="AirQualityManager")
	private AirQualityService airQualityService;

	@RequestMapping("/getAllAirQuality")
	public String getAllAirQuality(HttpServletRequest request){
		request.setAttribute("airQualityList", airQualityService.getAllAirQuality());
		//System.out.println(airQualityService.getAllAirQuality().get(0).getId());
		return "/login";
	}
	
	@RequestMapping("/getTodaysAirQuality")
	public String getTodaysAirQuality(String city, HttpServletRequest request){
		request.setAttribute("airQualityList", airQualityService.getTodaysAirQuality(city));
		System.out.println(airQualityService.getTodaysAirQuality(city).size());
		return "/login";
	}
	
	@RequestMapping("/getAllTodaysAirQuality")
	public String getAllTodaysAirQuality(HttpServletRequest request){
		request.setAttribute("airQualityList", airQualityService.getAllTodaysAirQuality());
		System.out.println(airQualityService.getAllTodaysAirQuality().size());
		return "/login";
	}
	
	@RequestMapping("/getCurrentAirQuality")
	public String getCurrentAirQuality(String city,HttpServletRequest request){
		request.setAttribute("airQuality", airQualityService.getCurrentAirQuality(city));
		System.out.println(airQualityService.getCurrentAirQuality(city).getId());
		return "/login";
	}
}
