package com.coshine.batsys.context;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "batsys")
public class BatsysProperties {

	private String username;
	private String password;
	private String pythonInterpreter;
	private String outputDirectory;
	private String programDirectory;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPythonInterpreter() {
		return pythonInterpreter;
	}

	public void setPythonInterpreter(String pythonInterpreter) {
		this.pythonInterpreter = pythonInterpreter;
	}
	
	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}
	
	public String getProgramDirectory() {
		return programDirectory;
	}

	public void setProgramDirectory(String programDirectory) {
		this.programDirectory = programDirectory;
	}
}
