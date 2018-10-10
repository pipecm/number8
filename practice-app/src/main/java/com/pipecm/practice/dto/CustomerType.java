package com.pipecm.practice.dto;

public enum CustomerType {
	PERSON (1),
	BUSINESS (2);
	
	private final int value;
	
	CustomerType(final int newValue) {
		value = newValue;
	}
	
	public int getValue() {
		return value;
	}
}