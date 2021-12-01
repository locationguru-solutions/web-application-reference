package com.locationguru.csf.model.support;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum(value = Integer.class)
public enum AccessScope
{
	@XmlEnumValue("0") UNKNOWN,
	@XmlEnumValue("1") SYSTEM,
	@XmlEnumValue("2") APPLICATION;

	private static final AccessScope[] values = AccessScope.values();
	private static final int length = values.length;

	public static AccessScope get(final int index)
	{
		return index > 0 && index < length ? values[index] : UNKNOWN;
	}
}