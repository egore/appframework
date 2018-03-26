package de.egore911.appframework.util;

import org.hibernate.Hibernate;

import java.util.function.Function;

/**
 * @author Christoph Brill &lt;christoph.brill@cgm.com&gt;
 * @since 07.06.2017 21:52
 */
public class HashCodeEqualsBuilder {

	/**
	 * @deprecated Use {@link java.util.Objects#hash(Object...)} instead
	 */
	@Deprecated
	public static <T> int buildHashCode(T t, Function<? super T, ?>... functions) {
		final int prime = 31;
		int result = 1;
		for (Function<? super T, ?> f : functions) {
			Object o = f.apply(t);
			result = prime * result + ((o == null) ? 0 : o.hashCode());
		}
		return result;
	}

	public static <T> boolean isEqual(T t, Object obj, Function<? super T, ?>... functions) {
		if (t == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (Hibernate.getClass(t) != Hibernate.getClass(obj)) {
			return false;
		}
		T other = (T) obj;
		for (Function<? super T, ?> f : functions) {
			Object own = f.apply(t);
			Object foreign = f.apply(other);
			if (own == null) {
				if (foreign != null) {
					return false;
				}
			} else if (!own.equals(foreign)) {
				return false;
			}
		}
		return true;
	}
}
