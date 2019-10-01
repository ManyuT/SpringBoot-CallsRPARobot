package com.rpa.services.Impl;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpa.Utils.Constants;
import com.rpa.models.Authenticate;
import com.rpa.models.LoginDTO;
import com.rpa.models.Releases;
import com.rpa.models.Robots;
import com.rpa.models.RunProcessDTO;
import com.rpa.models.StartJob;
import com.rpa.services.RPAServices;

/**
 * @author abhimanyu.thite
 *
 */
@Service
public class RPAServicesImpl implements RPAServices {

	@Autowired
	RestTemplate restTemplate;
	
	@Value("${uipath.useragent}")
	String userAgent;
	
	@Value("${uipath.uri.auth}")
	String authUri;
	
	@Value("${uipath.uri.releases}")
	String releasesUri;
	
	@Value("${uipath.uri.robots}")
	String robotsUri;
	
	@Value("${uipath.uri.startJob}")
	String startJobUri;
	
	String filterProcessByName;
	
	String filterRobotByName;
	
	/**
	 * @param uri
	 * @param filter
	 * @return
	 */
	private UriComponentsBuilder builderFromUriStringWithFilter(String uri, String filter) {

		return UriComponentsBuilder.fromUriString(uri).queryParam(Constants.QUERYPARAM_KEY_FILTER, filter);

	}
	
	/**
	 * @param authToken
	 * @return
	 */
	private HttpHeaders setDefaultHeaders(String authToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
		headers.set(HttpHeaders.USER_AGENT, userAgent);
		if (authToken != null) {
			headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
		}
		return headers;
	}

	/**
	 * @return Authenticate
	 */
	@Override
	public Authenticate authenticate(LoginDTO loginDTO) {
		System.out.println("Authenticating Credentials...");
		ObjectMapper Obj = new ObjectMapper(); 
		String loginDTOStr = "";
		try {
			loginDTOStr = Obj.writeValueAsString(loginDTO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
		 
		Authenticate authResult = restTemplate.postForObject(authUri,
				new HttpEntity<String>(loginDTOStr, setDefaultHeaders(null)), Authenticate.class);
		
		System.out.println("Authorization:"+authResult.isSuccess());
		return authResult;
	}

	/**
	 * @param authToken
	 * @param filter
	 * @return Releases
	 */
	@Override
	public Releases getReleases(String authToken, String filter) {
		System.out.println("Getting Process Info..");

		ResponseEntity<String> responseReleases = restTemplate.exchange(
				builderFromUriStringWithFilter(releasesUri, filter).toUriString(), HttpMethod.GET,
				new HttpEntity<String>(setDefaultHeaders(authToken)), String.class);

		Releases releases = new Releases();
		try {
			releases = new ObjectMapper().readValue(responseReleases.getBody(), Releases.class);
			System.out.println("Process Info Gathered");
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return releases;
	}

	@Override
	public Robots getRobots(String authToken, String filter) {
		System.out.println("Getting Robots Info..");
		ResponseEntity<String> responseRobots = restTemplate.exchange(
				builderFromUriStringWithFilter(robotsUri, filter).toUriString(), HttpMethod.GET,
				new HttpEntity<String>(setDefaultHeaders(authToken)), String.class);

		Robots robots = new Robots();
		try {
			robots = new ObjectMapper().readValue(responseRobots.getBody(), Robots.class);
			System.out.println("Robots Info Gathered");
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return robots;
	}

	/**
	 * @param businessKey
	 * @param messageName
	 * @return StartJob
	 */
	@Override
	public StartJob startJob(RunProcessDTO runProcessDTO) {
		System.out.println("Executing JOB..");
		
		filterProcessByName = "ProcessKey eq '"+runProcessDTO.getProcessName()+"'";
		filterRobotByName = "Name eq '"+runProcessDTO.getRobotName()+"'";
		
		String authToken = authenticate(runProcessDTO.getLoginCredential()).getResult();
		Releases releases = getReleases(authToken, filterProcessByName);
		Robots robots = getRobots(authToken, filterRobotByName);

		JSONObject startJobBody = new JSONObject();
		JSONObject startInfo = new JSONObject();
		startInfo.put(Constants.RELEASE_KEY, releases.getValue().get(0).getKey());
		startInfo.put(Constants.STRATEGY, Constants.STRATEGY_SPECIFIC);
		startInfo.put(Constants.ROBOT_IDS, new Long[] { robots.getValue().get(0).getId() });
		startInfo.put(Constants.JOBS_COUNT, 0);
		startInfo.put(Constants.SOURCE, Constants.SOURCE_MANUAL);
		
		startInfo.put(Constants.INPUT_ARGUMENTS, runProcessDTO.getInputArgument());
		startJobBody.put(Constants.START_INFO, startInfo);
		
		StartJob responseStartJob = restTemplate.postForObject(startJobUri,
				new HttpEntity<String>(startJobBody.toString(), setDefaultHeaders(authToken)), StartJob.class);
		
		System.out.println("JOB Started");
		return responseStartJob;
		
	}


}
