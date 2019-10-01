package com.rpa.services;

import org.springframework.stereotype.Service;

import com.rpa.models.Authenticate;
import com.rpa.models.LoginDTO;
import com.rpa.models.Releases;
import com.rpa.models.Robots;
import com.rpa.models.RunProcessDTO;
import com.rpa.models.StartJob;

/**
 * @author abhimanyu.thite
 *
 */
@Service
public interface RPAServices {
	
	public Releases getReleases(String authToken, String filter);
	
	public Authenticate authenticate(LoginDTO loginDTO);
		
	public Robots getRobots(String authToken, String filter);
	
	public StartJob startJob(RunProcessDTO runProcessDTO);

}
