package de.egore911.appframework.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BinaryDataEntity.class)
public abstract class BinaryDataEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<BinaryDataEntity, String> filename;
	public static volatile SingularAttribute<BinaryDataEntity, Long> size;
	public static volatile SingularAttribute<BinaryDataEntity, byte[]> data;
	public static volatile SingularAttribute<BinaryDataEntity, String> contentType;

}

