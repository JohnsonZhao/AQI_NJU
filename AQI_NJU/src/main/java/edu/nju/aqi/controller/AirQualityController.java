package edu.nju.aqi.controller;

import edu.nju.aqi.bo.AirQualityBo;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.service.AirQualityService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Controller
@RequestMapping("/AirQuality")
public class AirQualityController {

    @Resource(name = "AirQualityManager")
    private AirQualityService airQualityService;

    @ResponseBody
    @RequestMapping("/getAllAirQuality")
    public String getAllAirQuality() {
        //request.setAttribute("airQualityList", airQualityService.getAllAirQuality());
        //System.out.println(airQualityService.getAllAirQuality().get(0).getId());
        return "/login";
    }

    @ResponseBody
    @RequestMapping("/getTodaysAirQuality")
    public List<AirQuality> getTodaysAirQuality(String city, HttpServletRequest request) {
        request.setAttribute("airQualityList", airQualityService.getTodaysAirQuality(city));
        //System.out.println(airQualityService.getTodaysAirQuality(city).size());
        return airQualityService.getTodaysAirQuality(city);
    }

    @ResponseBody
    @RequestMapping("/getAllTodaysAirQuality")
    public List<AirQuality> getAllTodaysAirQuality(HttpServletRequest request) {
        request.setAttribute("airQualityList", airQualityService.getAllTodaysAirQuality());
        //System.out.println(airQualityService.getAllTodaysAirQuality().size());
        return airQualityService.getAllTodaysAirQuality();
    }

    @ResponseBody
    @RequestMapping("/getCurrentAirQuality")
    public AirQuality getCurrentAirQuality(String city, HttpServletRequest request) {
    	request.setAttribute("airQualityList", airQualityService.getCurrentAirQuality(city));
        //System.out.println(airQualityService.getTodaysAirQuality(city).size());
        return airQualityService.getCurrentAirQuality(city);
    }

    @ResponseBody
    @RequestMapping("/getAllCurrentAirQuality")
    public List<AirQualityBo> getAllCurrentAirQuality() {
    	//System.out.println(airQualityService.getAllCurrentAirQuality().size());
        return airQualityService.getAllCurrentAirQuality();
    }
    
    @ResponseBody
    @RequestMapping("/get24HoursAirQuality")
    public List<AirQuality> get24HoursAirQuality(String city, HttpServletRequest request) {
    	//System.out.println(airQualityService.get24HoursAirQuality(city));
        return airQualityService.get24HoursAirQuality(city);
    }
    
    @ResponseBody
    @RequestMapping("/getPastHoursAirQuality")
    public List<AirQuality> getPastHoursAirQuality(String city,int hourNum, HttpServletRequest request) {
    	//System.out.println(airQualityService.get24HoursAirQuality(city));
        return airQualityService.getPastHoursAirQuality(city, hourNum);
    }
}
