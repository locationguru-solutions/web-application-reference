package com.locationguru.csf.model.support;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum(value = Integer.class)
public enum AuthenticationType
{
	@XmlEnumValue("0") UNKNOWN,
	@XmlEnumValue("1") USER_NAME,
	@XmlEnumValue("2") EMAIL,
	@XmlEnumValue("3") MOBILE_NUMBER,
	@XmlEnumValue("4") SINGLE_SIGN_ON,
	@XmlEnumValue("5") LDAP,
	@XmlEnumValue("6") API_KEY,
	@XmlEnumValue("7") JSON_WEB_TOKEN;

	private static final AuthenticationType[] values = AuthenticationType.values();
	private static final int length = values.length;

	public static AuthenticationType get(final int index)
	{
		return index > 0 && index < length ? values[index] : UNKNOWN;
	}
}