package de.egore911.appframework.util.listener;

import java.util.concurrent.Executors;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import de.egore911.appframework.util.FactoryHolder;
import de.egore911.appframework.util.Permissions;
import javax.servlet.ServletContextListener;

public abstract class AbstractStartupListener implements ServletContextListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractStartupListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/capacityDS");
			Flyway flyway = new Flyway();
			flyway.setDataSource(dataSource);

			FlywayLocationsDetector.detect(flyway, dataSource);

			flyway.migrate();
		} catch (NamingException e) {
			LOG.error(e.getMessage(), e);
		}

		FactoryHolder.SCHEDULE_EXECUTOR = Executors.newSingleThreadScheduledExecutor();
		FactoryHolder.EXECUTOR = Executors.newSingleThreadExecutor();

		for (Enum<?> permission : getPermissions()) {
			Permissions.PERMISSIONS.add(permission.name());
		}
	}


	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (FactoryHolder.SCHEDULE_EXECUTOR != null) {
			FactoryHolder.SCHEDULE_EXECUTOR.shutdown();
		}
		if (FactoryHolder.EXECUTOR != null) {
			FactoryHolder.EXECUTOR.shutdown();
		}
	}

	protected abstract Enum<?>[] getPermissions();
	protected abstract String getDatasourceJdniName();

}
