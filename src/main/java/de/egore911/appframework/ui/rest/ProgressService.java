package de.egore911.appframework.ui.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.egore911.appframework.ui.async.ProgressEndpoint;
import de.egore911.appframework.ui.dto.Progress;

@Path("progress")
public class ProgressService extends AbstractService {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProgress(@PathParam("id") String id) {
		Progress<?> progress = ProgressEndpoint.CACHE.get(id);
		if (progress == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(progress).build();
	}

}
