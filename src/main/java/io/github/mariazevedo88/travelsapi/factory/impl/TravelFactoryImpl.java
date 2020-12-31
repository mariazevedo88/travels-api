package io.github.mariazevedo88.travelsapi.factory.impl;

import io.github.mariazevedo88.travelsapi.enumeration.TravelTypeEnum;
import io.github.mariazevedo88.travelsapi.factory.TravelFactory;
import io.github.mariazevedo88.travelsapi.model.Travel;

/**
 * Factory class for the Travel entity.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public class TravelFactoryImpl implements TravelFactory {

	@Override
	public Travel createTravel (String type) {
		
		if (TravelTypeEnum.ONE_WAY.getValue().equals(type)) {
			return new Travel(TravelTypeEnum.ONE_WAY);
		} else if (TravelTypeEnum.MULTI_CITY.getValue().equals(type)) {
			return new Travel(TravelTypeEnum.MULTI_CITY); 
		}
		
		return new Travel(TravelTypeEnum.RETURN);
	}

}
