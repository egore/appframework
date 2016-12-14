package de.egore911.appframework.ui.async;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.egore911.appframework.util.FactoryHolder;
import de.egore911.appframework.ui.dto.Progress;

@ServerEndpoint("/async/progress/{progressId}")
public class ProgressEndpoint {

	private static final Logger LOG = LoggerFactory.getLogger(ProgressEndpoint.class);

	public static final Map<String, Progress<?>> CACHE = new HashMap<>();
	public static final Map<String, ScheduledFuture<?>> RUNNING = new HashMap<>();

	@OnOpen
	public void registerProgressListener(@PathParam("progressId") final String id, final Session session) {
		final ObjectMapper mapper = new ObjectMapper();
		ScheduledFuture<?> scheduleAtFixedRate = FactoryHolder.SCHEDULE_EXECUTOR.scheduleAtFixedRate(() -> {
			try {
				Progress<?> progress = CACHE.get(id);
				if (progress.isCompleted()) {
					session.getBasicRemote().sendText(mapper.writeValueAsString(progress), true);
					RUNNING.get(id).cancel(false);
				} else {
					session.getBasicRemote().sendText(mapper.writeValueAsString(progress));
				}
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}, 1, 1, TimeUnit.SECONDS);
		RUNNING.put(id, scheduleAtFixedRate);
	}

}
