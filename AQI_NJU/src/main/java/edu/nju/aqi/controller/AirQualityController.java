package edu.nju.aqi.controller;

import edu.nju.aqi.analysis.helper.AreaCorrelation;
import edu.nju.aqi.bo.AirQualityBo;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.service.AirQualityService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/getAllTodaysAirQuality")
    public List<AirQuality> getAllTodaysAirQuality(HttpServletRequest request) {
        request.setAttribute("airQualityList", airQualityService.getAllTodaysAirQuality());
        //System.out.println(airQualityService.getAllTodaysAirQuality().size());
        return airQualityService.getAllTodaysAirQuality();
    }

    /**
     * 获取特定城市当前的AirQuality各项指标
     * 同步获取
     * @param city(中文)
     * @return ModelAndView
     */
    @RequestMapping("/getCurrentAirQualityByChinese")
    public ModelAndView getCurrentAirQualityByChinese(String city) {
        ModelAndView modelAndView = new ModelAndView("city");

        AirQuality airQuality = airQualityService.getCurrentAirQualityByChinese(city);
        String province = airQualityService.getCityProvinceByChinese(city);
        modelAndView.getModel().put("province", province);
        modelAndView.getModel().put("airQuality", airQuality);
        return modelAndView;
    }

    /**
     * 获取特定城市当前的AirQuality各项指标
     * @param city(拼音)
     * @return modelandview 同步获取
     */
    @RequestMapping("/getCurrentAirQuality")
    public ModelAndView getCurrentAirQuality(String city) {
        ModelAndView modelAndView = new ModelAndView("city");
        AirQuality airQuality = airQualityService.getCurrentAirQuality(city);
        String province = airQualityService.getCityProvince(city);
        modelAndView.getModel().put("airQuality", airQuality);
        modelAndView.getModel().put("province", province);
        return modelAndView;
    }


    /**
     * 获取当前所有城市的AirQuality各项指标
     * 异步获取
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAllCurrentAirQuality")
    public List<AirQualityBo> getAllCurrentAirQuality() {
        return airQualityService.getAllCurrentAirQuality();
    }

    @ResponseBody
    @RequestMapping("/get24HoursAirQuality")
    public List<AirQuality> get24HoursAirQuality(String city, HttpServletRequest request) {
        return airQualityService.get24HoursAirQuality(city);
    }
    
    @ResponseBody
    @RequestMapping("/getPastHoursAirQuality")
    public List<AirQuality> getPastHoursAirQuality(String city,int hourNum, HttpServletRequest request) {
        return airQualityService.getPastHoursAirQuality(city, hourNum);
    }
    
    @ResponseBody
    @RequestMapping("/getPastDaysAirQuality")
    public List<AirQuality> getPastDaysAirQuality(String city,int dayNum) {
        return airQualityService.getPastDaysAirQuality(city, dayNum);
    }

    /**
     * 获取特定城市的24小时AQI指数,用于展示变化折线图
     *
     * @param city
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCityAQIHistory")
    public List<AirQuality> getCityAQIHistory(String city) {
        return airQualityService.get24HoursAirQuality(city);
    }

    @RequestMapping("/rank")
    public ModelAndView getRank() {
        ModelAndView modelAndView = new ModelAndView("rank");
        return modelAndView;
    }

    /**
     * 获取相关城市的aqi
     * @param cityName
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRelatedCities")
    public List<AirQuality> getRelatedCities(String cityName) {
        return airQualityService.getRelatedCities(cityName);
    }

    @ResponseBody
    @RequestMapping("/getSimilarCities")
    /**
     * 是按照similar属性的降序排列的，第一个是其自身，值为1.0000
     * sorted by desc order of similar attribute, the first element is itself(similar=1.0000)
     * @param cityName
     * @return
     */
    public List<Map.Entry<AirQuality,Double>> getSimilarCities(String cityName){
    	/* 对于这个list中每一项的获取方式
    	 * for(int i=0;i<infoIds2.size();i++){
         *   //infoIds2.get(i).getKey(), infoIds2.get(i).getValue();
         * }
         */
    	return airQualityService.getSimilarCitiesInProvince(cityName);
    }

}

