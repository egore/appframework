package de.egore911.appframework.ui.rest;

import java.util.List;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

import de.egore911.appframework.persistence.dao.RoleDao;
import de.egore911.appframework.persistence.model.RoleEntity;
import de.egore911.appframework.persistence.selector.RoleSelector;
import de.egore911.appframework.ui.dto.Role;

@Path("role")
public class RoleService extends AbstractResourceService<Role, RoleEntity> {

	@Override
	protected Class<Role> getDtoClass() {
		return Role.class;
	}

	@Override
	protected Class<RoleEntity> getEntityClass() {
		return RoleEntity.class;
	}

	@Override
	protected RoleSelector getSelector(Subject subject) {
		return new RoleSelector();
	}

	@Override
	protected RoleDao getDao() {
		return new RoleDao();
	}

	@Override
	@RequiresPermissions("SHOW_ROLES")
	public Role getById(Integer id, Subject subject) {
		return super.getById(id, subject);
	}

	@Override
	@RequiresPermissions("SHOW_ROLES")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Role> getByIds(@QueryParam("ids") List<Integer> ids,
			@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit,
			@QueryParam("sortColumn") String sortColumn, @QueryParam("ascending") Boolean ascending,
			@QueryParam("search") String search,
			@Auth Subject subject, @Context HttpServletResponse response) {
		return super.getByIds(ids, offset, limit, sortColumn, ascending, search, subject, response);
	}

	@Override
	@RequiresPermissions("ADMIN_ROLES")
	public Role create(Role role, @Auth Subject subject) {
		return super.create(role, subject);
	}

	@Override
	@RequiresPermissions("ADMIN_ROLES")
	public void update(@Nonnull Integer id, Role role, @Auth Subject subject) {
		super.update(id, role, subject);
	}

	@Override
	@RequiresPermissions("ADMIN_ROLES")
	public void delete(@Nonnull Integer id, Subject subject) {
		super.delete(id, subject);
	}

}
