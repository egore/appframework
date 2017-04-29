package de.egore911.appframework.ui.rest;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

import de.egore911.appframework.persistence.dao.UserDao;
import de.egore911.appframework.persistence.model.UserEntity;
import de.egore911.appframework.persistence.selector.UserSelector;
import de.egore911.appframework.ui.dto.User;
import de.egore911.appframework.ui.exceptions.BadArgumentException;
import de.egore911.persistence.util.EntityManagerUtil;

@Path("profile")
public class ProfileService extends AbstractService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User get(@Auth Subject subject) {
		UserEntity user = new UserSelector().withLogin(subject.getPrincipal().toString()).find();
		return getMapper().map(user, User.class);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public User update(User user, @Auth Subject subject) {

		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			UserEntity existing = new UserSelector().withLogin(subject.getPrincipal().toString()).find();
			validate(existing.getId(), user.getLogin());

			// Only update some details
			UserService.hashPassword(user);

			existing.setPassword(user.getPassword());
			existing.setEmail(user.getEmail());
			existing.setLogin(user.getLogin());

			em.getTransaction().commit();
			return getMapper().map(user, User.class);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	private void validate(Integer id, String login) {

		UserEntity user = new UserDao().findByLogin(login);
		if (user != null && !id.equals(user.getId())) {
			throw new BadArgumentException("Login already taken by another user");
		}
	}
}
