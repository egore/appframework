package de.egore911.appframework.ui.rest;

import java.time.LocalDateTime;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import de.egore911.appframework.util.VersionExtractor;

@Path("version")
@Produces(MediaType.APPLICATION_JSON)
public class VersionService {

	@Context
	private ServletContext servletContext;

	public static final class VersionInformation {
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

	@GET
	public VersionInformation getVersion() {
		return new VersionInformation(
				VersionExtractor.getMavenVersion(servletContext, "de.egore911.inout", "inout-web"),
				VersionExtractor.getGitVersion(servletContext, "de.egore911.inout", "inout-web"),
				VersionExtractor.getBuildTimestamp(servletContext, "de.egore911.inout", "inout-web"));
	}

}
