package io.github.mariazevedo88.tripsapi.factory;

import io.github.mariazevedo88.tripsapi.model.Trip;

/**
 * Interface that provides method for manipulate a trip.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface TripFactory {

	Trip createTrip(String type);
}
