package de.egore911.appframework.ui.dto;

import java.time.LocalDateTime;

public class VersionInformation {

	private String maven;
	private String git;
	private LocalDateTime buildTimestamp;

	public VersionInformation() {
	}

	public VersionInformation(String maven, String git, LocalDateTime buildTimestamp) {
		this.maven = maven;
		this.git = git;
		this.buildTimestamp = buildTimestamp;
	}

	public String getMaven() {
		return maven;
	}

	public void setMaven(String maven) {
		this.maven = maven;
	}

	public String getGit() {
		return git;
	}

	public void setGit(String git) {
		this.git = git;
	}

	public LocalDateTime getBuildTimestamp() {
		return buildTimestamp;
	}

	public void setBuildTimestamp(LocalDateTime buildTimestamp) {
		this.buildTimestamp = buildTimestamp;
	}

}
