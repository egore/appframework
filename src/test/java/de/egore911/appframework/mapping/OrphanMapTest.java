package de.egore911.appframework.mapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsSame.sameInstance;

import java.util.Map;

import org.junit.Test;

import de.egore911.appframework.mapping.classes.MapClass;
import de.egore911.appframework.util.FactoryHolder;
import ma.glasnost.orika.OrikaSystemProperties;
import ma.glasnost.orika.impl.generator.EclipseJdtCompilerStrategy;

public class OrphanMapTest {

	static {
		System.setProperty(OrikaSystemProperties.COMPILER_STRATEGY, EclipseJdtCompilerStrategy.class.getName());
	}

	@Test
	public void testMap() {
		MapClass source = new MapClass();
		source.getMember().put("a", "A");
		source.getMember().put("b", "B");
		source.getMember().put("c", "C");

		MapClass destination = new MapClass();
		destination.getMember().put("a", "A");
		destination.getMember().put("d", "D");
		Map<String, String> member = destination.getMember();

		FactoryHolder.MAPPER_FACTORY.getMapperFacade().map(source, destination);

		assertThat(destination.getMember(), sameInstance(member));
		assertThat(destination.getMember().entrySet(), hasSize(3));
		assertThat(destination.getMember(), hasEntry("a", "A"));
		assertThat(destination.getMember(), hasEntry("b", "B"));
		assertThat(destination.getMember(), hasEntry("c", "C"));

	}

}
