package com.locationguru.csf.model.support;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum(value = Integer.class)
public enum AuthenticationStatus
{
	@XmlEnumValue("0") UNKNOWN,
	@XmlEnumValue("1") ACTIVE,
	@XmlEnumValue("2") DEPRECATED,
	@XmlEnumValue("3") PENDING_RESET,
	@XmlEnumValue("4") EXPIRED,
	@XmlEnumValue("5") SUSPENDED;

	private static final AuthenticationStatus[] values = AuthenticationStatus.values();
	private static final int length = values.length;

	public static AuthenticationStatus get(final int index)
	{
		return index > 0 && index < length ? values[index] : UNKNOWN;
	}
}
