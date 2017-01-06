package de.egore911.appframework.mapping.classes;

import javax.persistence.Id;

public class EntitySuperMethodAnnotated {

	private String id;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
