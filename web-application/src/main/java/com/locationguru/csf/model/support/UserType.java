package com.locationguru.csf.model.support;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum(value = Integer.class)
public enum UserType
{
	@XmlEnumValue("0") UNKNOWN,
	@XmlEnumValue("1") SYSTEM,
	@XmlEnumValue("2") APPLICATION;

	private static final UserType[] values = UserType.values();
	private static final int length = values.length;

	public static UserType get(final int index)
	{
		return index > 0 && index < length ? values[index] : UNKNOWN;
	}
}