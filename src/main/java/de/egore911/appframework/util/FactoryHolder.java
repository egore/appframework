package de.egore911.appframework.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class FactoryHolder {

	public static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();
	public static ExecutorService EXECUTOR;
	public static ScheduledExecutorService SCHEDULE_EXECUTOR;

}
