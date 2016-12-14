package de.egore911.appframework.ui.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

import de.egore911.appframework.persistence.model.RoleEntity;
import de.egore911.appframework.persistence.selector.RoleSelector;
import de.egore911.appframework.util.Permissions;

@Path("permissions")
public class PermissionService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPermissions() {
		List<String> permissions = new ArrayList<>(Permissions.PERMISSIONS);
		return permissions;
	}

	@GET
	@Path("my")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getMyPermissions(@Auth Subject subject) {
		List<RoleEntity> roles = new RoleSelector().withUserLogin((String) subject.getPrincipal()).findAll();
		return roles.stream()
				.map(RoleEntity::getPermissions)
				.flatMap(List::stream)
				.distinct()
				.collect(Collectors.toList());
	}

}
