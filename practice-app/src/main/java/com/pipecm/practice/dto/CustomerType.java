package com.pipecm.practice.dto;

public enum CustomerType {
	UNDEFINED (0),
	PERSON (1),
	BUSINESS (2);
	
	private final int value;
	
	CustomerType(final int newValue) {
		value = newValue;
	}
	
	public int getValue() {
		return value;
	}
	
	public static CustomerType parse(int value) {
		CustomerType customerType = CustomerType.UNDEFINED;
        for (CustomerType item : CustomerType.values()) {
            if (item.getValue() == value) {
            	customerType = item;
                break;
            }
        }
        return customerType;
    }
}