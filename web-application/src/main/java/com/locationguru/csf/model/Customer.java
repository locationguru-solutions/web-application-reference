package com.locationguru.csf.model;

import java.util.UUID;

import com.locationguru.csf.model.support.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "customers")
public class Customer
		extends BaseEntity
{
	@Id
	@XmlTransient
	@Column(name = "id")
	@SequenceGenerator(name = "customers_id_seq", sequenceName = "customers_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_id_seq")
	private Long id;

	@XmlElement
	@Generated(value = GenerationTime.INSERT)
	@Column(name = "uid", unique = true, updatable = false)
	private UUID uid;

	@NotNull
	@XmlElement
	@Column(name = "access_scope_id")
	private AccessScope accessScope;

	@NotEmpty
	@XmlElement
	@Column(name = "identity")
	private String identity;

	@NotEmpty
	@XmlElement
	@Column(name = "name")
	private String name;

	@NotNull
	@XmlElement
	@Column(name = "status_id")
	private CustomerStatus status;

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public UUID getUid()
	{
		return uid;
	}

	public void setUid(final UUID uid)
	{
		this.uid = uid;
	}

	public AccessScope getAccessScope()
	{
		return accessScope;
	}

	public void setAccessScope(final AccessScope accessScope)
	{
		this.accessScope = accessScope;
	}

	public String getIdentity()
	{
		return identity;
	}

	public void setIdentity(final String identity)
	{
		this.identity = identity;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public CustomerStatus getStatus()
	{
		return status;
	}

	public void setStatus(final CustomerStatus status)
	{
		this.status = status;
	}
}
