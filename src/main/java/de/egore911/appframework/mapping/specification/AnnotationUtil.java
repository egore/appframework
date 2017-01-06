package de.egore911.appframework.mapping.specification;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AnnotationUtil {

	public static String getIdProperty(Class<?> destinationType) {

		// Only check on entities
		if (!isAnnotated(destinationType, "javax.persistence.Entity")) {
			return null;
		}

		// Check if a getter is annotated with "@Id"
		Class<?> klass = destinationType;
		while (klass != Object.class) {
			for (Method m : klass.getDeclaredMethods()) {
				if (m.getName().startsWith("get") && isAnnotated(m, "javax.persistence.Id")
						&& m.getParameters().length == 0) {
					return m.getName() + "()";
				}
			}
			klass = klass.getSuperclass();
		}

		// Check if a member field is annotated with "@Id"
		klass = destinationType;
		while (klass != Object.class) {
			for (Field f : klass.getDeclaredFields()) {
				if (isAnnotated(f, "javax.persistence.Id")) {
					// If it is publicly accessible, return it
					if (Modifier.isPublic(f.getModifiers())) {
						return f.getName();
					}
					// Try to find the matching getter
					String getterName = "get" + Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
					try {
						Method getter = destinationType.getMethod(getterName, new Class[0]);
						if (getter.getReturnType() == f.getType()) {
							return getter.getName() + "()";
						}
					} catch (Exception exn) {
						// Ok, annotated with @Id but has no getter, let's abort
						return null;
					}
				}
			}

			klass = klass.getSuperclass();
		}

		return null;
	}

	@Nullable
	private static Annotation getAnnotation(@Nonnull AnnotatedElement element, @Nonnull String annotationClassName) {
		for (Annotation annotation : element.getAnnotations()) {
			if (annotationClassName.equals(annotation.annotationType().getName())) {
				return annotation;
			}
		}

		return null;
	}

	@Nullable
	private static boolean isAnnotated(@Nonnull AnnotatedElement element, @Nonnull String annotationClassName) {
		return getAnnotation(element, annotationClassName) != null;
	}

}
