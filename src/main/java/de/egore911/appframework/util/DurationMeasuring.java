package de.egore911.appframework.util;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DurationMeasuring {

	public static long measureStart() {
		return System.currentTimeMillis();
	}

	public static long measurePoint(long measureStart, @Nonnull Class<?> klass, @Nonnull String event) {
		long nowMillis = System.currentTimeMillis();

		Logger logger = LoggerFactory.getLogger("de.egore911.appframework.duration." + klass.getSimpleName());
		if (!logger.isDebugEnabled()) {
			return nowMillis;
		}

		StringBuilder buffer = new StringBuilder();
		buffer.append(String.format("%1$5s", Long.toString(nowMillis - measureStart)));
		buffer.append("ms ");
		buffer.append(klass.getSimpleName());
		buffer.append("::");
		buffer.append(event);
		logger.debug(buffer.toString());
		
		return nowMillis;
	}

}
