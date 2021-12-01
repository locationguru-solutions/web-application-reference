package com.locationguru.csf.model.support;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum(value = Integer.class)
public enum TokenStatus
{
	@XmlEnumValue("0") UNKNOWN,
	@XmlEnumValue("1") ACTIVE,
	@XmlEnumValue("2") EXPIRED;

	private static final TokenStatus[] values = TokenStatus.values();
	private static final int length = values.length;

	public static TokenStatus get(final int index)
	{
		return index > 0 && index < length ? values[index] : UNKNOWN;
	}
}
