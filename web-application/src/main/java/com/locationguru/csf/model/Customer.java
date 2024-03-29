package com.locationguru.csf.model;

import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

import com.locationguru.csf.model.support.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.generator.EventType;

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
	@Generated(event = EventType.INSERT)
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
		return this.id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public UUID getUid()
	{
		return this.uid;
	}

	public void setUid(final UUID uid)
	{
		this.uid = uid;
	}

	public AccessScope getAccessScope()
	{
		return this.accessScope;
	}

	public void setAccessScope(final AccessScope accessScope)
	{
		this.accessScope = accessScope;
	}

	public String getIdentity()
	{
		return this.identity;
	}

	public void setIdentity(final String identity)
	{
		this.identity = identity;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public CustomerStatus getStatus()
	{
		return this.status;
	}

	public void setStatus(final CustomerStatus status)
	{
		this.status = status;
	}
}
