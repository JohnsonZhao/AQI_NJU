package edu.nju.aqi.controller;

import edu.nju.aqi.bo.AirQualityBo;
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
        System.out.println(airQualityService.getAllAirQuality().get(0).getId());
        return "/login";
    }

    @RequestMapping("/getTodaysAirQuality")
    public String getTodaysAirQuality(String city, HttpServletRequest request) {
        request.setAttribute("airQualityList", airQualityService.getTodaysAirQuality(city));
        System.out.println(airQualityService.getTodaysAirQuality(city).size());
        return "/login";
    }

    @RequestMapping("/getAllTodaysAirQuality")
    public String getAllTodaysAirQuality(HttpServletRequest request) {
        request.setAttribute("airQualityList", airQualityService.getAllTodaysAirQuality());
        System.out.println(airQualityService.getAllTodaysAirQuality().size());
        return "/login";
    }

    @RequestMapping("/getCurrentAirQuality")
    public String getCurrentAirQuality(String city) {
        return "/city";
    }

    @ResponseBody
    @RequestMapping("/getAllCurrentAirQuality")
    public List<AirQualityBo> getAllCurrentAirQuality() {
        return airQualityService.getAllCurrentAirQuality();
    }
}
