package de.egore911.appframework.persistence.selector;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.appframework.persistence.model.UserEntity;
import de.egore911.appframework.persistence.model.UserEntity_;

public class UserSelector extends AbstractResourceSelector<UserEntity> {

	private static final long serialVersionUID = -2302451622379464057L;

	private String login;

	@Nonnull
	@Override
	protected Class<UserEntity> getEntityClass() {
		return UserEntity.class;
	}
	
	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder, Root<UserEntity> from,
			CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);
		
		if (StringUtils.isNotEmpty(login)) {
			predicates.add(builder.equal(from.get(UserEntity_.login), login));
		}
		
		return predicates;
	}

	public UserSelector withLogin(String login) {
		this.login = login;
		return this;
	}

}
