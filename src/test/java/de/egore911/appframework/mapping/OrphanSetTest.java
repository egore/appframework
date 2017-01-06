package de.egore911.appframework.mapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsSame.sameInstance;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.egore911.appframework.mapping.classes.EntityMemberAnnotated;
import de.egore911.appframework.mapping.classes.EntityMethodAnnotated;
import de.egore911.appframework.mapping.classes.MemberAnnotatedSetClass;
import de.egore911.appframework.mapping.classes.MethodAnnotatedSetClass;
import de.egore911.appframework.mapping.classes.PlainSetClass;
import de.egore911.appframework.util.FactoryHolder;
import ma.glasnost.orika.OrikaSystemProperties;
import ma.glasnost.orika.impl.generator.EclipseJdtCompilerStrategy;

public class OrphanSetTest {

	static {
		System.setProperty(OrikaSystemProperties.COMPILER_STRATEGY, EclipseJdtCompilerStrategy.class.getName());
	}

	@Test
	public void testPlainSet() {
		List<String> expected = Arrays.asList("a", "b", "c");

		PlainSetClass source = new PlainSetClass();
		source.getMember().addAll(expected);

		PlainSetClass destination = new PlainSetClass();
		destination.getMember().addAll(Arrays.asList("a", "d"));
		Set<String> member = destination.getMember();

		FactoryHolder.MAPPER_FACTORY.getMapperFacade().map(source, destination);

		assertThat(destination.getMember(), sameInstance(member));
		assertThat(destination.getMember(), hasSize(3));
		for (String s : expected) {
			assertThat(destination.getMember(), hasItem(s));
		}
	}

	@Test
	public void testEntityMemberSet() {
		List<EntityMemberAnnotated> expected = Arrays.asList(new EntityMemberAnnotated(1), new EntityMemberAnnotated(2), new EntityMemberAnnotated(3));

		MemberAnnotatedSetClass source = new MemberAnnotatedSetClass();
		source.getMember().addAll(expected);

		MemberAnnotatedSetClass destination = new MemberAnnotatedSetClass();
		destination.getMember().addAll(Arrays.asList(new EntityMemberAnnotated(1), new EntityMemberAnnotated(4)));

		Set<EntityMemberAnnotated> member = destination.getMember();

		FactoryHolder.MAPPER_FACTORY.getMapperFacade().map(source, destination);

		assertThat(destination.getMember(), sameInstance(member));
		assertThat(destination.getMember(), hasSize(3));
		for (EntityMemberAnnotated s : expected) {
			assertThat(destination.getMember(), hasItem(s));
		}
	}

	@Test
	public void testEntityMethodSet() {
		List<EntityMethodAnnotated> expected = Arrays.asList(new EntityMethodAnnotated("a"), new EntityMethodAnnotated("b"), new EntityMethodAnnotated("c"));

		MethodAnnotatedSetClass source = new MethodAnnotatedSetClass();
		source.getMember().addAll(expected);

		MethodAnnotatedSetClass destination = new MethodAnnotatedSetClass();
		destination.getMember().addAll(Arrays.asList(new EntityMethodAnnotated("a"), new EntityMethodAnnotated("d")));

		Set<EntityMethodAnnotated> member = destination.getMember();

		FactoryHolder.MAPPER_FACTORY.getMapperFacade().map(source, destination);

		assertThat(destination.getMember(), sameInstance(member));
		assertThat(destination.getMember(), hasSize(3));
		for (EntityMethodAnnotated s : expected) {
			assertThat(destination.getMember(), hasItem(s));
		}
	}

}
