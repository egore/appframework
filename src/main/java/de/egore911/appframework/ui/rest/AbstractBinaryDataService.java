package de.egore911.appframework.ui.rest;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.egore911.appframework.persistence.dao.BinaryDataDao;
import de.egore911.appframework.persistence.model.BinaryDataEntity;
import de.egore911.appframework.persistence.model.UserEntity;
import de.egore911.appframework.persistence.selector.UserSelector;
import de.egore911.persistence.util.EntityManagerUtil;

public abstract class AbstractBinaryDataService extends AbstractService {

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] getRaw(@PathParam("id") Integer id) {
		BinaryDataEntity binaryData = new BinaryDataDao().findById(id);
		return binaryData.getData();
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Integer id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			nullAndPersistOwnUsers(em, id);
			nullAndPersistUsers(em, id);
			BinaryDataDao binaryDataDao = new BinaryDataDao();
			BinaryDataEntity binaryData = binaryDataDao.findById(id);
			binaryDataDao.remove(binaryData);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}
	
	private void nullAndPersistOwnUsers(@Nonnull EntityManager em, @Nonnull Integer id) {
		List<UserEntity> users = new UserSelector().withPictureId(id)
				.findAll();
		users.forEach(user -> {
			user.setPicture(null);
			em.persist(user);
		});
	}

	protected abstract void nullAndPersistUsers(@Nonnull EntityManager em, @Nonnull Integer id);

}
