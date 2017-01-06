package de.egore911.appframework.mapping.classes;

import java.util.HashSet;
import java.util.Set;

public class PlainSetClass {
	private Set<String> member = new HashSet<>();

	public Set<String> getMember() {
		return member;
	}

	public void setMember(Set<String> member) {
		this.member = member;
	}
}