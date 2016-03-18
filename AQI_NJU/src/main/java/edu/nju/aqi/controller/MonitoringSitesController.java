package edu.nju.aqi.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.nju.aqi.service.MonitoringSitesService;

@Controller
@RequestMapping("/MonitoringSites")
public class MonitoringSitesController {

	@Resource(name="MonitoringSitesManager")
	private MonitoringSitesService monitoringSitesService;
	
	@RequestMapping("/getCurrentMonitoringSites")
	public String getCurrentMonitoringSites(String city,HttpServletRequest request){
		request.setAttribute("monitoringSites", monitoringSitesService.getCurrentMonitoringSites(city));
		System.out.println(monitoringSitesService.getCurrentMonitoringSites(city).size());
		return "/login";
	}
	
}
