package de.egore911.appframework.mapping.classes;

import java.util.HashMap;
import java.util.Map;

public class MapClass {
	private Map<String, String> member = new HashMap<>();

	public Map<String, String> getMember() {
		return member;
	}

	public void setMember(Map<String, String> member) {
		this.member = member;
	}
}