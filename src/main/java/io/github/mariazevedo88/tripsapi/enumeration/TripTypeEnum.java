package io.github.mariazevedo88.tripsapi.enumeration;

/**
 * Enum that classifies the trip's type.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 *
 */
public enum TripTypeEnum {
	
	RETURN("RETURN"), ONE_WAY("ONE-WAY"), MULTI_CITY("MULTI-CITY");
	
	private String value;
	
	private TripTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	/**
	 * Method that returns the value in the Enum.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 * 
	 * @param value
	 * @return a TripTypeEnum
	 */
	public static TripTypeEnum getEnum(String value) {
		
		for(TripTypeEnum t : values()) {
			if(value.equals(t.getValue())) {
				return t;
			}
		}
		
		throw new RuntimeException("Type not found.");
	}

}
