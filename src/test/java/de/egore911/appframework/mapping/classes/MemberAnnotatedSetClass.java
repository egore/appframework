package de.egore911.appframework.mapping.classes;

import java.util.HashSet;
import java.util.Set;

public class MemberAnnotatedSetClass {
	private Set<EntityMemberAnnotated> member = new HashSet<>();

	public Set<EntityMemberAnnotated> getMember() {
		return member;
	}

	public void setMember(Set<EntityMemberAnnotated> member) {
		this.member = member;
	}
}