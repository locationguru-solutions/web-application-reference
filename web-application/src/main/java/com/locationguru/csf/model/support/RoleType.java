package com.locationguru.csf.model.support;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum(value = Integer.class)
public enum RoleType
{
	@XmlEnumValue("0") UNKNOWN,
	@XmlEnumValue("1") ADMINISTRATOR,
	@XmlEnumValue("2") SUPPORT,
	@XmlEnumValue("3") USER,
	@XmlEnumValue("4") GUEST;

	private static final RoleType[] values = RoleType.values();
	private static final int length = values.length;

	public static RoleType get(final int index)
	{
		return index > 0 && index < length ? values[index] : UNKNOWN;
	}
}