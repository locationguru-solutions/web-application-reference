package com.locationguru.csf.model.support;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum(value = Integer.class)
public enum Gender
{
	@XmlEnumValue("0") UNKNOWN,
	@XmlEnumValue("1") NOT_SPECIFIED,
	@XmlEnumValue("2") MALE,
	@XmlEnumValue("3") FEMALE;

	private static final Gender[] values = Gender.values();
	private static final int length = values.length;

	public static Gender get(final int index)
	{
		return index > 0 && index < length ? values[index] : UNKNOWN;
	}
}
