package de.egore911.appframework.mapping.specification;

import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getArrayChildEntityMemberVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getArrayChildEntityMethodVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getArrayEntityMemberVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getArrayEntityMethodVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getArrayVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getArrayVariableRefString;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getListChildEntityMemberVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getListChildEntityMethodVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getListEntityMemberVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getListEntityMethodVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getListVariableRefProperty;
import static de.egore911.appframework.mapping.specification.SpeficicationUtil.getListVariableRefString;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import ma.glasnost.orika.impl.generator.SourceCodeContext;
import ma.glasnost.orika.impl.generator.VariableRef;
import ma.glasnost.orika.impl.generator.specification.ArrayOrCollectionToCollection;
import ma.glasnost.orika.metadata.FieldMap;

public class OrphanArrayOrCollectionToCollectionTest {

	@Test
	public void testListToListStringToString() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getListVariableRefString("a");
		VariableRef destination = getListVariableRefString("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testListToListPropertyToProperty() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getListVariableRefProperty("a");
		VariableRef destination = getListVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testArrayToArrayStringToString() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getArrayVariableRefString("a");
		VariableRef destination = getArrayVariableRefString("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testArrayToArrayPropertyToProperty() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getArrayVariableRefProperty("a");
		VariableRef destination = getArrayVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testListEntityMethod() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getListEntityMethodVariableRefProperty("a");
		VariableRef destination = getListEntityMethodVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testArrayEntityMethod() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getArrayEntityMethodVariableRefProperty("a");
		VariableRef destination = getArrayEntityMethodVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testListEntityMember() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getListEntityMemberVariableRefProperty("a");
		VariableRef destination = getListEntityMemberVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testArrayEntityMember() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getArrayEntityMemberVariableRefProperty("a");
		VariableRef destination = getArrayEntityMemberVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testListChildEntityMethod() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getListChildEntityMethodVariableRefProperty("a");
		VariableRef destination = getListChildEntityMethodVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testArrayChildEntityMethod() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getArrayChildEntityMethodVariableRefProperty("a");
		VariableRef destination = getArrayChildEntityMethodVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testListChildEntityMember() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getListChildEntityMemberVariableRefProperty("a");
		VariableRef destination = getListChildEntityMemberVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

	@Test
	public void testArrayChildEntityMember() {
		FieldMap fieldMap = mock(FieldMap.class);
		VariableRef source = getArrayChildEntityMemberVariableRefProperty("a");
		VariableRef destination = getArrayChildEntityMemberVariableRefProperty("b");
		SourceCodeContext code = mock(SourceCodeContext.class);
		System.err.println("---------------------------------");
		System.err.println(new OrphanArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
		System.err.println(new ArrayOrCollectionToCollection().generateMappingCode(fieldMap, source, destination, code));
		System.err.println("---------------------------------");
	}

}
