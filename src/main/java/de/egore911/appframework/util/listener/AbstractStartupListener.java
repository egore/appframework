package de.egore911.appframework.util.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.egore911.appframework.util.FactoryHolder;
import de.egore911.appframework.util.Permissions;

public abstract class AbstractStartupListener implements ServletContextListener {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractStartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource;
			try (InputStream stream = this.getClass().getResourceAsStream("/META-INF/persistence.xml")) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				Document doc = factory.newDocumentBuilder().parse(stream);
				Node documentElement = doc.getDocumentElement();
				NodeList childNodes = documentElement.getChildNodes();
				String dataSourceName = null;
				outer: for (int i = 0; i < childNodes.getLength(); i++) {
					Node persistenceUnitElement = childNodes.item(i);
					if ("persistence-unit".equals(persistenceUnitElement.getNodeName())) {
						NodeList elements = persistenceUnitElement.getChildNodes();
						for (int j = 0; j < elements.getLength(); j++) {
							Node element = elements.item(j);
							if ("non-jta-data-source".equals(element.getNodeName())) {
								dataSourceName = element.getTextContent();
								break outer;
							}
						}
					}
				}
				if (dataSourceName == null) {
					throw new IllegalStateException("Could not find non-jta-data-source in persistence.xml");
				}
				dataSource = (DataSource) initialContext.lookup(dataSourceName);
			} catch (IOException | SAXException | ParserConfigurationException e) {
				throw new IllegalStateException("Could not read persistence.xml");
			}
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

}
