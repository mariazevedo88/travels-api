package io.github.mariazevedo88.tripsapi.factory.impl;

import io.github.mariazevedo88.tripsapi.enumeration.TripTypeEnum;
import io.github.mariazevedo88.tripsapi.factory.TripFactory;
import io.github.mariazevedo88.tripsapi.model.Trip;

/**
 * Factory class for the Trip entity.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public class TripFactoryImpl implements TripFactory {

	@Override
	public Trip createTrip(String type) {
		
		if (TripTypeEnum.ONE_WAY.getValue().equals(type)) {
			return new Trip(TripTypeEnum.ONE_WAY);
		} else if (TripTypeEnum.MULTI_CITY.getValue().equals(type)) {
			return new Trip(TripTypeEnum.MULTI_CITY); 
		}
		
		return new Trip(TripTypeEnum.RETURN);
	}

}
