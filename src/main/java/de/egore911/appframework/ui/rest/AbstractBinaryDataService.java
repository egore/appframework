package de.egore911.appframework.ui.rest;

import de.egore911.appframework.persistence.dao.BinaryDataDao;
import de.egore911.appframework.persistence.model.BinaryDataEntity;
import de.egore911.appframework.persistence.model.UserEntity;
import de.egore911.appframework.persistence.selector.UserSelector;
import de.egore911.persistence.util.EntityManagerUtil;
import org.glassfish.jersey.media.multipart.ContentDisposition;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.sql.Date;
import java.time.ZoneId;
import java.util.List;

public abstract class AbstractBinaryDataService extends AbstractService {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getRaw(@PathParam("id") Integer id) {
        BinaryDataEntity binaryData = new BinaryDataDao().findById(id);

        if (binaryData == null) {
            throw new NotFoundException("No binary data with given ID");
        }

        ContentDisposition contentDisposition = ContentDisposition
                .type("attachment")
                .fileName(binaryData.getFilename())
                .size(binaryData.getSize())
                .creationDate(Date.from(binaryData.getCreated().atZone(ZoneId.systemDefault()).toInstant())).build();

        return Response
                .ok((StreamingOutput) outputStream -> outputStream.write(binaryData.getData()))
                .header("Content-Disposition", contentDisposition)
                .build();

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
