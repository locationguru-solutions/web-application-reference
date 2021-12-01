package com.locationguru.csf.model.support;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum(value = Integer.class)
public enum CustomerStatus
{
	@XmlEnumValue("0") UNKNOWN,
	@XmlEnumValue("1") ACTIVE,
	@XmlEnumValue("2") SUSPENDED,
	@XmlEnumValue("3") DELETED;

	private static final CustomerStatus[] values = CustomerStatus.values();
	private static final int length = values.length;

	public static CustomerStatus get(final int index)
	{
		return index > 0 && index < length ? values[index] : UNKNOWN;
	}
}