package de.egore911.appframework.mapping.classes;

import java.util.HashSet;
import java.util.Set;

public class MethodAnnotatedSetClass {
	private Set<EntityMethodAnnotated> member = new HashSet<>();

	public Set<EntityMethodAnnotated> getMember() {
		return member;
	}

	public void setMember(Set<EntityMethodAnnotated> member) {
		this.member = member;
	}
}