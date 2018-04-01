package de.egore911.appframework.persistence.selector;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.appframework.persistence.model.RoleEntity;
import de.egore911.appframework.persistence.model.RoleEntity_;
import de.egore911.appframework.persistence.model.UserEntity_;

public class RoleSelector extends AbstractResourceSelector<RoleEntity> {

	private String userLogin;
	private String search;

	@Nonnull
	@Override
	protected Class<RoleEntity> getEntityClass() {
		return RoleEntity.class;
	}

	@Nonnull
	@Override
	protected List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder, @Nonnull Root<RoleEntity> from,
													@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (StringUtils.isNotEmpty(userLogin)) {
			predicates.add(builder.equal(from.join(RoleEntity_.users).get(UserEntity_.login), userLogin));
		}

		if (StringUtils.isNotEmpty(search)) {
			String likePattern = '%' + search + '%';
			predicates.add(builder.or(
					builder.like(from.get(RoleEntity_.name), likePattern)
			));
		}

		return predicates;
	}

	public RoleSelector withUserLogin(String userLogin) {
		this.userLogin = userLogin;
		return this;
	}

	@Override
	public RoleSelector withSearch(String search) {
		this.search = search;
		return this;
	}
}
