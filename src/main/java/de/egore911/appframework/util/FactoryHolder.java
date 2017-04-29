package de.egore911.appframework.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import de.egore911.appframework.mapping.specification.OrphanArrayOrCollectionToCollection;
import de.egore911.appframework.mapping.specification.OrphanMapToMap;
import de.egore911.appframework.persistence.dao.RoleDao;
import de.egore911.appframework.persistence.model.RoleEntity;
import de.egore911.appframework.persistence.model.UserEntity;
import de.egore911.appframework.ui.dto.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.generator.CodeGenerationStrategy;
import ma.glasnost.orika.impl.generator.specification.ArrayOrCollectionToCollection;
import ma.glasnost.orika.impl.generator.specification.MapToMap;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.unenhance.HibernateUnenhanceStrategy;

public class FactoryHolder {

	public static final MapperFactory MAPPER_FACTORY;
	public static ExecutorService EXECUTOR;
	public static ScheduledExecutorService SCHEDULE_EXECUTOR;

	static {

		DefaultMapperFactory.Builder builder = new DefaultMapperFactory.Builder();

		CodeGenerationStrategy codeGenerationStrategy = builder.getCodeGenerationStrategy();

		codeGenerationStrategy.addSpecification(
				new OrphanArrayOrCollectionToCollection(),
				CodeGenerationStrategy.Position.IN_PLACE_OF,
				ArrayOrCollectionToCollection.class
		);
		codeGenerationStrategy.addSpecification(
				new OrphanMapToMap(),
				CodeGenerationStrategy.Position.IN_PLACE_OF,
				MapToMap.class
		);

		builder.unenhanceStrategy(new HibernateUnenhanceStrategy());

		// Comment in to generate classes for debugging purposes
		// Files will be generated to .metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\xxx\WEB-INF\classes
		// builder.compilerStrategy(new ma.glasnost.orika.impl.generator.EclipseJdtCompilerStrategy());

		MAPPER_FACTORY = builder.build();

		MAPPER_FACTORY.getConverterFactory()
				.registerConverter(new PassThroughConverter(ZonedDateTime.class));

		MAPPER_FACTORY.getConverterFactory()
				.registerConverter(new PassThroughConverter(LocalDate.class));

		MAPPER_FACTORY.getConverterFactory()
				.registerConverter(new PassThroughConverter(LocalTime.class));

		MAPPER_FACTORY.getConverterFactory()
				.registerConverter(new PassThroughConverter(LocalDateTime.class));

		MAPPER_FACTORY.getConverterFactory()
				.registerConverter(new BidirectionalConverter<DayOfWeek, Integer>() {
					@Override
					public Integer convertTo(DayOfWeek source, Type<Integer> destinationType, MappingContext context) {
						return source == null ? null : source.getValue();
					}

					@Override
					public DayOfWeek convertFrom(Integer source, Type<DayOfWeek> destinationType, MappingContext context) {
						return source == null ? null : DayOfWeek.of(source);
					}
				});

		MAPPER_FACTORY.classMap(User.class, UserEntity.class).byDefault()
				.customize(new CustomMapper<User, UserEntity>() {
					@Override
					public void mapAtoB(User a, UserEntity b, MappingContext context) {
						if (CollectionUtils.isNotEmpty(a.getRoleIds())) {
							b.setRoles(new RoleDao().findByIds(a.getRoleIds()));
						}
					}

					@Override
					public void mapBtoA(UserEntity a, User b, MappingContext context) {
						b.setPassword(null);
						b.setRoleIds(a.getRoles().stream().map(RoleEntity::getId).collect(Collectors.toList()));
					}
				}).register();

	}
}
