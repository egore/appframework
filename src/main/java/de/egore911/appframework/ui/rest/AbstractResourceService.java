package de.egore911.appframework.ui.rest;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

import de.egore911.appframework.persistence.model.IntegerDbObject;
import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.appframework.ui.dto.AbstractDto;
import de.egore911.appframework.ui.exceptions.BadArgumentException;
import de.egore911.appframework.ui.exceptions.NotFoundException;
import de.egore911.appframework.ui.exceptions.NullArgumentException;
import de.egore911.persistence.dao.AbstractDao;
import de.egore911.persistence.util.EntityManagerUtil;

public abstract class AbstractResourceService<T extends AbstractDto, U extends IntegerDbObject> extends AbstractService {
	
	protected abstract Class<T> getDtoClass();
	protected abstract Class<U> getEntityClass();
	protected abstract AbstractResourceSelector<U> getSelector(Subject subject);
	protected abstract AbstractDao<U> getDao();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<T> getByIds(@QueryParam("ids") List<Integer> ids,
			@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit,
			@QueryParam("sortColumn") String sortColumn, @QueryParam("ascending") Boolean ascending,
			@Auth Subject subject, @Context HttpServletResponse response) {
		AbstractResourceSelector<U> selector = (AbstractResourceSelector<U>) getSelector(subject)
						.withIds(ids)
						.withSortColumn(sortColumn)
						.withAscending(ascending);
		List<U> entities = selector
				.withOffset(offset)
				.withLimit(limit)
				.findAll();
		if (offset != null || limit != null) {
			response.setHeader("Result-Count", Long.toString(selector.count()));
		}
		return getMapper().mapAsList(entities, getDtoClass());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public T create(T t, @Auth Subject subject) {
		if (t == null) {
			throw new NullArgumentException("t");
		}
		if (t.getId() != null) {
			throw new BadArgumentException("Cannot create an entity already having an ID");
		}
		U entity = map(t);
		validate(t, entity);
		return getMapper().map(getDao().save(entity), getDtoClass());
	}

	protected U map(T t) {
		return getMapper().map(t, getEntityClass());
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public T getById(@PathParam("id") Integer id, @Auth Subject subject) {
		if (id == null) {
			throw new NullArgumentException("id");
		}
		return getMapper().map(getSelector(subject).withId(id).find(), getDtoClass());
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") @Nonnull Integer id, T t, @Auth Subject subject) {
		if (t == null) {
			throw new NullArgumentException("t");
		}
		if (t.getId() == null) {
			throw new BadArgumentException("Cannot update an entity without an ID");
		}
		EntityManager em = EntityManagerUtil.getEntityManager();
		U entity = getSelector(subject).withId(id).find();
		if (entity == null) {
			throw new NotFoundException("Could not find t with ID " + id);
		}
		map(t, entity);
		validate(t, entity);
		try {
			em.getTransaction().begin();
			getDao().save(entity);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}

	}

	protected void map(T t, U entity) {
		getMapper().map(t, entity);
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") @Nonnull Integer id, @Auth Subject subject) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			U entity = getSelector(subject).withId(id).find();
			if (entity == null) {
				throw new BadArgumentException("Entity with ID " + id + " does not exist");
			}
			getDao().remove(entity);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	protected void validate(T dto, U entity) {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<U>> constraintVioalations = validator.validate(entity);
		if (!constraintVioalations.isEmpty()) {
			StringBuilder message = new StringBuilder();
			for (ConstraintViolation<U> constraintVioalation : constraintVioalations) {
				if (message.length() > 0) {
					message.append(", ");
				}
				message.append(constraintVioalation.getPropertyPath()).append(' ').append(constraintVioalation.getMessage());
			}
			throw new BadArgumentException(message.toString());
		}
	}

}
