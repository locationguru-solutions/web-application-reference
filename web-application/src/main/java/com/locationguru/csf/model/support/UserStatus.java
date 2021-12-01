package com.locationguru.csf.model.support;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum(value = Integer.class)
public enum UserStatus
{
	@XmlEnumValue("0") UNKNOWN,
	@XmlEnumValue("1") ACTIVE,
	@XmlEnumValue("2") INACTIVE,
	@XmlEnumValue("3") DEACTIVATED,
	@XmlEnumValue("4") SUSPENDED,
	@XmlEnumValue("5") DELETED;

	private static final UserStatus[] values = UserStatus.values();
	private static final int length = values.length;

	public static UserStatus get(final int index)
	{
		return index > 0 && index < length ? values[index] : UNKNOWN;
	}
}