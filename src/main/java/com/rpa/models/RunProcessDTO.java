package com.rpa.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author abhimanyu.thite
 *
 */
public class RunProcessDTO {
	
	private String processName;
	private String  robotName;
	@JsonProperty("inputArgument")
	private String InputArgument;
	private LoginDTO loginCredential;
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getRobotName() {
		return robotName;
	}
	public void setRobotName(String robotName) {
		this.robotName = robotName;
	}
	public String getInputArgument() {
		return InputArgument;
	}
	public void setInputArgument(String inputArgument) {
		InputArgument = inputArgument;
	}
	public LoginDTO getLoginCredential() {
		return loginCredential;
	}
	public void setLoginCredential(LoginDTO loginCredential) {
		this.loginCredential = loginCredential;
	}
	
	
	

}
