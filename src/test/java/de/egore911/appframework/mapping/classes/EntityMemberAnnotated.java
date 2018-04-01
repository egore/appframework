package de.egore911.appframework.mapping.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class EntityMemberAnnotated {
	@Id
	private int id;

	public EntityMemberAnnotated() {
	}

	public EntityMemberAnnotated(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityMemberAnnotated other = (EntityMemberAnnotated) obj;
		if (id != other.id)
			return false;
		return true;
	}

}