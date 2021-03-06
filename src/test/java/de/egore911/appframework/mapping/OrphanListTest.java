package de.egore911.appframework.mapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import de.egore911.appframework.mapping.classes.EntityMemberAnnotated;
import de.egore911.appframework.mapping.classes.EntityMethodAnnotated;
import de.egore911.appframework.mapping.classes.MemberAnnotatedListClass;
import de.egore911.appframework.mapping.classes.MethodAnnotatedListClass;
import de.egore911.appframework.mapping.classes.PlainListClass;
import de.egore911.appframework.util.FactoryHolder;

public class OrphanListTest {

	static {
		//System.setProperty(OrikaSystemProperties.COMPILER_STRATEGY, EclipseJdtCompilerStrategy.class.getName());
	}

	@Test
	public void testPlainList() {
		List<String> expected = Arrays.asList("a", "b", "c");

		PlainListClass source = new PlainListClass();
		source.getMember().addAll(expected);

		PlainListClass destination = new PlainListClass();
		destination.getMember().addAll(Arrays.asList("a", "d"));

		List<String> member = destination.getMember();

		FactoryHolder.MAPPER_FACTORY.getMapperFacade().map(source, destination);

		assertThat(destination.getMember(), sameInstance(member));
		assertThat(destination.getMember(), hasSize(3));
		assertThat(destination.getMember(), equalTo(expected));
	}

	@Test
	public void testEntityMemberList() {
		List<EntityMemberAnnotated> expected = Arrays.asList(new EntityMemberAnnotated(1), new EntityMemberAnnotated(2), new EntityMemberAnnotated(3));

		MemberAnnotatedListClass source = new MemberAnnotatedListClass();
		source.getMember().addAll(expected);

		MemberAnnotatedListClass destination = new MemberAnnotatedListClass();
		destination.getMember().addAll(Arrays.asList(new EntityMemberAnnotated(1), new EntityMemberAnnotated(4)));

		List<EntityMemberAnnotated> member = destination.getMember();

		FactoryHolder.MAPPER_FACTORY.getMapperFacade().map(source, destination);

		assertThat(destination.getMember(), sameInstance(member));
		assertThat(destination.getMember(), hasSize(3));
		assertThat(destination.getMember(), equalTo(expected));
	}

	@Test
	public void testEntityMethodList() {
		List<EntityMethodAnnotated> expected = Arrays.asList(new EntityMethodAnnotated("a"), new EntityMethodAnnotated("b"), new EntityMethodAnnotated("c"));

		MethodAnnotatedListClass source = new MethodAnnotatedListClass();
		source.getMember().addAll(expected);

		MethodAnnotatedListClass destination = new MethodAnnotatedListClass();
		destination.getMember().addAll(Arrays.asList(new EntityMethodAnnotated("a"), new EntityMethodAnnotated("d")));

		List<EntityMethodAnnotated> member = destination.getMember();

		FactoryHolder.MAPPER_FACTORY.getMapperFacade().map(source, destination);

		assertThat(destination.getMember(), sameInstance(member));
		assertThat(destination.getMember(), hasSize(3));
		assertThat(destination.getMember(), equalTo(expected));
	}

	@Test
	public void testEntityMethodList_null() {
		// Given: Our source without any member
		MethodAnnotatedListClass source = new MethodAnnotatedListClass();
		source.setMember(null);

		// Given: Our destination with a single member
		MethodAnnotatedListClass destination = new MethodAnnotatedListClass();
		destination.getMember().addAll(Collections.singleton(new EntityMethodAnnotated("a")));

		// Given: The instance before mapping
		List<EntityMethodAnnotated> member = destination.getMember();

		// When: We map from source to destination
		FactoryHolder.MAPPER_FACTORY.getMapperFacade().map(source, destination);

		// Then: The destination still has its member (necessary for orphan-removal)
		assertThat(destination.getMember(), sameInstance(member));
		// Then: The destination has no content in its member
		assertThat(destination.getMember(), hasSize(0));
	}

}
