package edu.nju.aqi.controller;

import edu.nju.aqi.model.MonitoringSites;
import edu.nju.aqi.service.MonitoringSitesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/MonitoringSites")
public class MonitoringSitesController {

	@Resource(name="MonitoringSitesManager")
	private MonitoringSitesService monitoringSitesService;
	
	@ResponseBody
	@RequestMapping("/getCurrentMonitoringSites")
	public List<MonitoringSites> getCurrentMonitoringSites(String city,HttpServletRequest request){
		System.out.println(city);
		//request.setAttribute("monitoringSites", monitoringSitesService.getCurrentMonitoringSites(city));
		//System.out.println(monitoringSitesService.getCurrentMonitoringSites(city).size());
		return monitoringSitesService.getCurrentMonitoringSites(city);
	}
	
}
