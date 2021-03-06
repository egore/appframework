package de.egore911.appframework.persistence.dao;

import de.egore911.appframework.persistence.model.UserEntity;
import de.egore911.appframework.persistence.selector.UserSelector;
import de.egore911.persistence.dao.AbstractDao;

public class UserDao extends AbstractDao<UserEntity> {

	@Override
	protected Class<UserEntity> getEntityClass() {
		return UserEntity.class;
	}

	@Override
	protected UserSelector createSelector() {
		return new UserSelector();
	}

	public UserEntity findByLogin(String login) {
		return createSelector()
				.withLogin(login)
				.find();
	}

	public UserEntity findByEmail(String email) {
		return createSelector()
				.withEmail(email)
				.find();
	}
}
