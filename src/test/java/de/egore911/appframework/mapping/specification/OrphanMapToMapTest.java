package de.egore911.appframework.mapping.specification;

import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getMapVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getMapVariableRefString;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import ma.glasnost.orika.impl.generator.SourceCodeContext;
import ma.glasnost.orika.impl.generator.VariableRef;
import ma.glasnost.orika.impl.generator.specification.MapToMap;
import ma.glasnost.orika.metadata.FieldMap;

public class OrphanMapToMapTest {

	@Test
	public void testMapToMapStringToString() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getMapVariableRefString("a");
		VariableRef destination = getMapVariableRefString("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanMapToMap().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new MapToMap().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testMapToMapPropertyToProperty() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getMapVariableRefProperty("a");
		VariableRef destination = getMapVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanMapToMap().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new MapToMap().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

}
