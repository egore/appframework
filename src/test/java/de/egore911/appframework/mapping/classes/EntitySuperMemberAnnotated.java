package de.egore911.appframework.mapping.classes;

import javax.persistence.Id;

public class EntitySuperMemberAnnotated {

	@Id
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}