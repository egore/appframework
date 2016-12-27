package de.egore911.appframework.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

public class FactoryHolder {

	public static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();
	public static ExecutorService EXECUTOR;
	public static ScheduledExecutorService SCHEDULE_EXECUTOR;

	static {
		FactoryHolder.MAPPER_FACTORY.getConverterFactory()
				.registerConverter(new PassThroughConverter(ZonedDateTime.class));

		FactoryHolder.MAPPER_FACTORY.getConverterFactory()
				.registerConverter(new PassThroughConverter(LocalDate.class));

		FactoryHolder.MAPPER_FACTORY.getConverterFactory()
				.registerConverter(new PassThroughConverter(LocalTime.class));

		FactoryHolder.MAPPER_FACTORY.getConverterFactory()
				.registerConverter(new PassThroughConverter(LocalDateTime.class));

		FactoryHolder.MAPPER_FACTORY.getConverterFactory()
				.registerConverter(new BidirectionalConverter<DayOfWeek, Integer>() {
					@Override
					public Integer convertTo(DayOfWeek source, Type<Integer> destinationType) {
						return source == null ? null : source.getValue();
					}

					@Override
					public DayOfWeek convertFrom(Integer source, Type<DayOfWeek> destinationType) {
						return source == null ? null : DayOfWeek.of(source);
					}

				});
	}
}
