package de.egore911.appframework.mapping.specification;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.egore911.appframework.mapping.classes.EntityChildMemberAnnotated;
import de.egore911.appframework.mapping.classes.EntityChildMethodAnnotated;
import de.egore911.appframework.mapping.classes.EntityMemberAnnotated;
import de.egore911.appframework.mapping.classes.EntityMethodAnnotated;
import ma.glasnost.orika.impl.generator.VariableRef;
import ma.glasnost.orika.metadata.Property;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;

@SuppressWarnings({"unchecked", "rawtypes"})
public class SpeficicationUtil {

	public static VariableRef getMapVariableRefString(String property) {
		return new VariableRef(TypeFactory.valueOf(Map.class), property);
	}

	public static VariableRef getMapVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(Map.class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isMap()).thenReturn(true);
		return new VariableRef(sourceType, property);
	}

	public static VariableRef getListVariableRefString(String property) {
		return new VariableRef(TypeFactory.valueOf(List.class, String.class), property);
	}

	public static VariableRef getListVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(List.class, String.class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isList()).thenReturn(true);
		when(sourceType.isCollection()).thenReturn(true);
		when(sourceType.getElementType()).thenReturn((Type) TypeFactory.valueOf(String.class));
		return new VariableRef(sourceType, property);
	}

	public static VariableRef getArrayVariableRefString(String property) {
		return new VariableRef(TypeFactory.valueOf(int[].class), property);
	}

	public static VariableRef getArrayVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(int[].class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isArray()).thenReturn(true);
		when(sourceType.getElementType()).thenReturn((Type) TypeFactory.valueOf(int.class));
		return new VariableRef(sourceType, property);
	}

	public static VariableRef getListEntityMemberVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(List.class, EntityMemberAnnotated.class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getSetter()).thenReturn("set" + toUpperCaseFirst(property) + "(%s)");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isList()).thenReturn(true);
		when(sourceType.isCollection()).thenReturn(true);
		when(sourceType.getElementType()).thenReturn((Type) TypeFactory.valueOf(EntityMemberAnnotated.class));
		return new VariableRef(sourceType, property);
	}

	public static VariableRef getListEntityMethodVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(List.class, EntityMethodAnnotated.class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getSetter()).thenReturn("set" + toUpperCaseFirst(property) + "(%s)");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isList()).thenReturn(true);
		when(sourceType.isCollection()).thenReturn(true);
		when(sourceType.getElementType()).thenReturn((Type) TypeFactory.valueOf(EntityMethodAnnotated.class));
		return new VariableRef(sourceType, property);
	}

	public static VariableRef getArrayEntityMemberVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(EntityMemberAnnotated[].class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getSetter()).thenReturn("set" + toUpperCaseFirst(property) + "(%s)");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isArray()).thenReturn(true);
		when(sourceType.getElementType()).thenReturn((Type) TypeFactory.valueOf(EntityMemberAnnotated.class));
		return new VariableRef(sourceType, property);
	}

	public static VariableRef getArrayEntityMethodVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(EntityMethodAnnotated[].class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getSetter()).thenReturn("set" + toUpperCaseFirst(property) + "(%s)");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isArray()).thenReturn(true);
		when(sourceType.getElementType()).thenReturn((Type) TypeFactory.valueOf(EntityMethodAnnotated.class));
		return new VariableRef(sourceType, property);
	}

	public static VariableRef getListChildEntityMemberVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(List.class, EntityChildMemberAnnotated.class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getSetter()).thenReturn("set" + toUpperCaseFirst(property) + "(%s)");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isList()).thenReturn(true);
		when(sourceType.isCollection()).thenReturn(true);
		when(sourceType.getElementType()).thenReturn((Type) TypeFactory.valueOf(EntityChildMemberAnnotated.class));
		return new VariableRef(sourceType, property);
	}

	public static VariableRef getListChildEntityMethodVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(List.class, EntityChildMethodAnnotated.class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getSetter()).thenReturn("set" + toUpperCaseFirst(property) + "(%s)");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isList()).thenReturn(true);
		when(sourceType.isCollection()).thenReturn(true);
		when(sourceType.getElementType()).thenReturn((Type) TypeFactory.valueOf(EntityChildMethodAnnotated.class));
		return new VariableRef(sourceType, property);
	}

	public static VariableRef getArrayChildEntityMemberVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(EntityChildMemberAnnotated[].class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getSetter()).thenReturn("set" + toUpperCaseFirst(property) + "(%s)");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isArray()).thenReturn(true);
		when(sourceType.getElementType()).thenReturn((Type) TypeFactory.valueOf(EntityChildMemberAnnotated.class));
		return new VariableRef(sourceType, property);
	}

	public static VariableRef getArrayChildEntityMethodVariableRefProperty(String property) {
		Property sourceType = mock(Property.class);
		when(sourceType.getType()).thenReturn((Type) TypeFactory.valueOf(EntityChildMethodAnnotated[].class));
		when(sourceType.getGetter()).thenReturn("get" + toUpperCaseFirst(property) + "()");
		when(sourceType.getSetter()).thenReturn("set" + toUpperCaseFirst(property) + "(%s)");
		when(sourceType.getName()).thenReturn(property);
		when(sourceType.isArray()).thenReturn(true);
		when(sourceType.getElementType()).thenReturn((Type) TypeFactory.valueOf(EntityChildMethodAnnotated.class));
		return new VariableRef(sourceType, property);
	}

	private static String toUpperCaseFirst(String property) {
		if (StringUtils.isEmpty(property)) {
			return property;
		}
		if (property.length() == 1) {
			return property.toUpperCase();
		}
		return Character.toUpperCase(property.charAt(0)) + property.substring(1);
	}

}
