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

	private String login;
	private String search;
	private Integer pictureId;
	private String name;
	private String email;

	@Nonnull
	@Override
	protected Class<UserEntity> getEntityClass() {
		return UserEntity.class;
	}

	@Nonnull
	@Override
	protected List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder, @Nonnull Root<UserEntity> from,
													@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (StringUtils.isNotEmpty(login)) {
			predicates.add(builder.equal(from.get(UserEntity_.login), login));
		}

		if (StringUtils.isNotEmpty(search)) {
			String likePattern = '%' + search + '%';
			predicates.add(builder.or(
					builder.like(from.get(UserEntity_.login), likePattern),
					builder.like(from.get(UserEntity_.name), likePattern),
					builder.like(from.get(UserEntity_.email), likePattern)
			));
		}

		if (pictureId != null) {
			predicates.add(builder.equal(from.get(UserEntity_.pictureId), pictureId));
		}

		if (StringUtils.isNotEmpty(name)) {
			predicates.add(builder.equal(from.get(UserEntity_.name), name));
		}

		if (StringUtils.isNotEmpty(email)) {
			predicates.add(builder.equal(from.get(UserEntity_.email), email));
		}

		return predicates;
	}

	public UserSelector withLogin(String login) {
		this.login = login;
		return this;
	}

	@Override
	public UserSelector withSearch(String search) {
		this.search = search;
		return this;
	}

	public UserSelector withPictureId(Integer pictureId) {
		this.pictureId = pictureId;
		return this;
	}

	public UserSelector withName(String name) {
		this.name = name;
		return this;
	}

	public UserSelector withEmail(String email) {
		this.email = email;
		return this;
	}
}
