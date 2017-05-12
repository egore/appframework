package de.egore911.appframework.ui.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.egore911.appframework.persistence.dao.UserDao;
import de.egore911.appframework.persistence.model.RoleEntity;
import de.egore911.appframework.persistence.model.UserEntity;
import de.egore911.appframework.persistence.selector.RoleSelector;
import de.egore911.appframework.ui.dto.Credentials;

@Path("login")
public class LoginService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Credentials credentials) {
		UserEntity userEntity = new UserDao().findByLogin(credentials.getUsername());
		if (userEntity != null && userEntity.getPassword().equals(credentials.getPassword())) {
			List<RoleEntity> roles = new RoleSelector().withUserLogin(userEntity.getLogin()).findAll();
			List<String> collect = roles.stream()
					.map(RoleEntity::getPermissions)
					.flatMap(List::stream)
					.distinct()
					.collect(Collectors.toList());
			return Response.ok().entity(collect).build();
		}
		Map<String, String> result = new HashMap<>();
		result.put("message", "invalid_username_or_password");
		return Response.status(Response.Status.UNAUTHORIZED).entity(result).build();
	}
}
