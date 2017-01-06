package de.egore911.appframework.mapping.classes;

import java.util.ArrayList;
import java.util.List;

public class MemberAnnotatedListClass {
	private List<EntityMemberAnnotated> member = new ArrayList<>();

	public List<EntityMemberAnnotated> getMember() {
		return member;
	}

	public void setMember(List<EntityMemberAnnotated> member) {
		this.member = member;
	}
}