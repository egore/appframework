package de.egore911.appframework.ui.rest;

import de.egore911.appframework.util.FactoryHolder;
import ma.glasnost.orika.MapperFacade;

abstract class AbstractService {

	protected final MapperFacade getMapper() {
		return FactoryHolder.MAPPER_FACTORY.getMapperFacade();
	}

}
