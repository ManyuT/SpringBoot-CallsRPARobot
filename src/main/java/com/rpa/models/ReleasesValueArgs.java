package com.rpa.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author abhimanyu.thite
 *
 */
public class ReleasesValueArgs {
	
	@JsonProperty("Input")
	private String Input;
	@JsonProperty("Output")
	private String Output;
	public String getInput() {
		return Input;
	}
	public void setInput(String input) {
		Input = input;
	}
	public String getOutput() {
		return Output;
	}
	public void setOutput(String output) {
		Output = output;
	}
	
	

}
