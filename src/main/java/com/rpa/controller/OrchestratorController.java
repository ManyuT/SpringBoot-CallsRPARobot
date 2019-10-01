package com.rpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rpa.models.RunProcessDTO;
import com.rpa.services.RPAServices;

/**
 * @author abhimanyu.thite
 * This controller , can add more services to make api as required.
 */
@RestController
public class OrchestratorController {
    
	@Autowired
	RPAServices rpaServices;
	
	/**
	 * @param runProcessDTO
	 * @return
	 * 
	 * Start JOB with provided Object
	 */
	@RequestMapping(value="/", method=RequestMethod.POST)
	String runsProcess(@RequestBody RunProcessDTO runProcessDTO) {
		rpaServices.startJob(runProcessDTO);
		return "hello";
	}
	
}
