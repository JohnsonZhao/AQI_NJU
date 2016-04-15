package edu.nju.aqi.controller;

import edu.nju.aqi.bo.AirQualityBo;
import edu.nju.aqi.model.AirQuality;
import edu.nju.aqi.service.AirQualityService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
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

    @RequestMapping("/getAllTodaysAirQuality")
    public List<AirQuality> getAllTodaysAirQuality(HttpServletRequest request) {
        request.setAttribute("airQualityList", airQualityService.getAllTodaysAirQuality());
        //System.out.println(airQualityService.getAllTodaysAirQuality().size());
        return airQualityService.getAllTodaysAirQuality();
    }

    /**
     * 获取特定城市当前的AirQuality各项指标
     *
     * @param city(拼音)
     * @return ModelAndView
     */
    @ResponseBody
    @RequestMapping("/getCurrentAirQuality")
    public AirQuality getCurrentAirQuality(String city,HttpServletRequest request) {

    	try {
			city=new String(city.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

    	System.out.println("============" + city);
        AirQuality airQuality = airQualityService.getCurrentAirQualityByChinese(city);
        //ModelAndView modelAndView = new ModelAndView("city");
        //System.out.println(airQuality!=null?airQuality.toString():"");
        //modelAndView.getModel().put("airQuality", airQuality);
        return airQuality;
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
        //System.out.println(airQualityService.get24HoursAirQuality(city));
        return airQualityService.get24HoursAirQuality(city);
    }
    
    @ResponseBody
    @RequestMapping("/getPastHoursAirQuality")
    public List<AirQuality> getPastHoursAirQuality(String city,int hourNum, HttpServletRequest request) {
    	//System.out.println(airQualityService.get24HoursAirQuality(city));
        return airQualityService.getPastHoursAirQuality(city, hourNum);
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
        //
        //TODO
        //
//        ArrayList<AirQualityBo> list = new ArrayList();
//        AirQualityBo bo1 = new AirQualityBo();
//        bo1.setAqi("20");
//        bo1.setDate("2016_04_04");
//        AirQualityBo bo2 = new AirQualityBo();
//        bo2.setAqi("21");
//        bo2.setDate("2016_04_05");
//        AirQualityBo bo3 = new AirQualityBo();
//        bo3.setAqi("25");
//        bo3.setDate("2016_04_06");
//        AirQualityBo bo4 = new AirQualityBo();
//        bo4.setAqi("27");
//        bo4.setDate("2016_04_07");
//        AirQualityBo bo5 = new AirQualityBo();
//        bo5.setAqi("20");
//        bo5.setDate("2016_04_08");
//
//        list.add(bo1);
//        list.add(bo2);
//        list.add(bo3);
//        list.add(bo4);
//        list.add(bo5);

        return airQualityService.get24HoursAirQuality(city);
    }

    @RequestMapping("/rank")
    public ModelAndView getRank() {
        ModelAndView modelAndView = new ModelAndView("rank");
        return modelAndView;
    }

}

