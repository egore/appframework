package de.egore911.appframework.ui.dto;

import java.util.ArrayList;
import java.util.List;

public class Role extends AbstractDto {

	private String name;
	private List<String> permissions = new ArrayList<>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

}
