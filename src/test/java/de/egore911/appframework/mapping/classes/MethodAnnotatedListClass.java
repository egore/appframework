package de.egore911.appframework.mapping.classes;

import java.util.ArrayList;
import java.util.List;

public class MethodAnnotatedListClass {
	private List<EntityMethodAnnotated> member = new ArrayList<>();

	public List<EntityMethodAnnotated> getMember() {
		return member;
	}

	public void setMember(List<EntityMethodAnnotated> member) {
		this.member = member;
	}
}