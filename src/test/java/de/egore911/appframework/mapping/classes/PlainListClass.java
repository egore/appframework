package de.egore911.appframework.mapping.classes;

import java.util.ArrayList;
import java.util.List;

public class PlainListClass {
	private List<String> member = new ArrayList<>();

	public List<String> getMember() {
		return member;
	}

	public void setMember(List<String> member) {
		this.member = member;
	}
}