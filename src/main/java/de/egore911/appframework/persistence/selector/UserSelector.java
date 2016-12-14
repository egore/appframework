package de.egore911.appframework.persistence.selector;

import javax.annotation.Nonnull;

import de.egore911.appframework.persistence.model.UserEntity;

public class UserSelector extends AbstractResourceSelector<UserEntity> {

	private static final long serialVersionUID = -2302451622379464057L;

	@Nonnull
	@Override
	protected Class<UserEntity> getEntityClass() {
		return UserEntity.class;
	}

}
