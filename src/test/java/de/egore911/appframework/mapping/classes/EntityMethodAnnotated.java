package de.egore911.appframework.mapping.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class EntityMethodAnnotated {
	private String id;

	public EntityMethodAnnotated() {
	}

	public EntityMethodAnnotated(String id) {
		this.id = id;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
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
		EntityMethodAnnotated other = (EntityMethodAnnotated) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}